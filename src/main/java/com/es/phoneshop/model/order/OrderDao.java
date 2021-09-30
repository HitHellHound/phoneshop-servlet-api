package com.es.phoneshop.model.order;

public interface OrderDao {
    Order getOrder(Long id) throws OrderNotFoundException;

    Order getOrderBySecureId(String secureId);

    void save(Order order);

    void clear();
}
