package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.cart.OutOfStockException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher(PageMappings.CART_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        Map<Long, String> errors = new HashMap<>();
        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                Long productId = Long.valueOf(productIds[i]);
                Cart cart = cartService.getCart(request);
                int quantity;
                try {
                    quantity = parseQuantity(quantities[i], request);
                    cartService.update(cart, productId, quantity);
                } catch (ParseException ex) {
                    errors.put(productId, "Not a number");
                } catch (OutOfStockException e) {
                    errors.put(productId, "Out of stock, available " + e.getStockAvailable());
                }
            }
        }

        if (errors.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?message=Cart updated successfully");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }

    private int parseQuantity(String quantity, HttpServletRequest request) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        return format.parse(quantity).intValue();
    }
}
