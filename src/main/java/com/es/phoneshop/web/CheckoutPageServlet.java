package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yy");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("order", orderService.getOrder(cart));
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());
        request.getRequestDispatcher(PageMappings.CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        Map<String, String> errors = new HashMap<>();
        setRequiredTextParameter(request, "firstName", errors, order::setFirstName);
        setRequiredTextParameter(request, "lastName", errors, order::setLastName);
        setRequiredTextParameter(request, "phone", errors, order::setPhone);
        setDeliveryDate(request, order, errors);
        setRequiredTextParameter(request, "deliveryAddress", errors, order::setDeliveryAddress);
        setPaymentMethod(request, order, errors);

        if (errors.isEmpty()) {
            orderService.placeOrder(order);
            cartService.clear(cart);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }

    private void setRequiredTextParameter(HttpServletRequest request, String parameter, Map<String, String> errors,
                                          Consumer<String> consumer) {
        String value = request.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            consumer.accept(value);
        }
    }

    private void setDeliveryDate(HttpServletRequest request, Order order, Map<String, String> errors) {
        String value = request.getParameter("deliveryDate");
        if (value == null || value.isEmpty()) {
            errors.put("deliveryDate", "Value is required");
            return;
        }
        try {
            LocalDate deliveryDate = LocalDate.parse(value, dateTimeFormatter);
            if (!deliveryDate.isAfter(LocalDate.now())) {
                errors.put("deliveryDate", "Print date after today");
                return;
            }
            order.setDeliveryDate(deliveryDate);
        } catch (DateTimeParseException e) {
            errors.put("deliveryDate", "Wrong date format");
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Order order, Map<String, String> errors) {
        String value = request.getParameter("paymentMethod");
        if (value == null || value.isEmpty()) {
            errors.put("paymentMethod", "Value is required");
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }
}
