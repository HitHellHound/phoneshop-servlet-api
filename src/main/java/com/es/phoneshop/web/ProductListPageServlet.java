package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    private static final String QUERY_PARAM = "query";
    private static final String SORT_PARAM = "sort";
    private static final String ORDER_PARAM = "order";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY_PARAM);
        String sortField = request.getParameter(SORT_PARAM);
        String sortOrder = request.getParameter(ORDER_PARAM);
        request.setAttribute("products", productDao.findProducts(query,
                SortField.getValue(sortField),
                SortOrder.getValue(sortOrder)
        ));
        request.getRequestDispatcher(PageMappings.PRODUCT_LIST_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdString = request.getParameter("id");
        String quantityString = request.getParameter("quantity");

        Cart cart = cartService.getCart(request);
        int quantity;
        try {
            Long productId = Long.valueOf(productIdString);
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            quantity = format.parse(quantityString).intValue();
            cartService.add(cart, productId, quantity);
        } catch (ParseException | NumberFormatException ex) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Out of stock, available " + e.getStockAvailable());
            doGet(request, response);
            return;
        }

        response.sendRedirect(makeRedirectPath(request));
    }

    private String makeRedirectPath(HttpServletRequest request) {
        StringBuilder redirectPath = new StringBuilder(request.getContextPath() + "/products?");
        if (request.getParameter(QUERY_PARAM) != null) {
            redirectPath.append(QUERY_PARAM)
                    .append("=")
                    .append(request.getParameter(QUERY_PARAM))
                    .append("&");
        }
        if (request.getParameter(SORT_PARAM) != null) {
            redirectPath.append(SORT_PARAM)
                    .append("=")
                    .append(request.getParameter(SORT_PARAM))
                    .append("&");
        }
        if (request.getParameter(ORDER_PARAM) != null) {
            redirectPath.append(ORDER_PARAM)
                    .append("=")
                    .append(request.getParameter(ORDER_PARAM))
                    .append("&");
        }
        redirectPath.append("message=Product added to cart");
        return redirectPath.toString();
    }
}
