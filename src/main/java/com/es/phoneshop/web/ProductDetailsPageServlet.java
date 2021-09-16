package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", productDao.getProduct(parseProductId(request)));
        request.setAttribute("cart", cartService.getCart());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = parseProductId(request);
        String quantityString = request.getParameter("quantity");

        int quantity;
        try {
            quantity = Integer.valueOf(quantityString);
            cartService.add(productId, quantity);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Out of stock, available " + e.getStockAvailable());
            doGet(request, response);
            return;
        }
        request.setAttribute("message", "Product added to cart");

        doGet(request, response);
    }

    private Long parseProductId(HttpServletRequest request) {
        String productId = request.getPathInfo();
        return Long.valueOf(productId.substring(1));
    }
}
