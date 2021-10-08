package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.QueryOption;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductDao productDao;

    private static final String QUERY_PARAM = "query";
    private static final String QUERY_OPTION_PARAM = "queryOption";
    private static final String MIN_PRICE_PARAM = "minPrice";
    private static final String MAX_PRICE_PARAM = "maxPrice";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(PageMappings.ADVANCED_SEARCH_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY_PARAM);
        String queryOption = request.getParameter(QUERY_OPTION_PARAM);
        String minPriceString = request.getParameter(MIN_PRICE_PARAM);
        String maxPriceString = request.getParameter(MAX_PRICE_PARAM);
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;

        Map<String, String> errors = new HashMap<>();
        if (minPriceString != null && !minPriceString.equals("")) {
            try {
                minPrice = new BigDecimal(Long.parseLong(minPriceString));
            } catch (NumberFormatException e) {
                errors.put("minPrice", "Wrong format");
            }
        }
        if (maxPriceString != null && !maxPriceString.equals("")) {
            try {
                maxPrice = new BigDecimal(Long.parseLong(maxPriceString));
            } catch (NumberFormatException e) {
                errors.put("maxPrice", "Wrong format");
            }
        }

        if (errors.isEmpty()) {
            request.setAttribute("products",
                    productDao.advancedFindProducts(query, QueryOption.getValue(queryOption), minPrice, maxPrice));
        } else {
            request.setAttribute("errors", errors);
        }
        doGet(request, response);
    }
}
