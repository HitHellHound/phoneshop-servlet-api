package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private List<Order> orderList;
    private long maxId;
    private final Object lock = new Object();

    private ArrayListOrderDao() {
        this.orderList = new ArrayList<>();
    }

    private static class SingletonHelper {
        private static final ArrayListOrderDao INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance() {
        return ArrayListOrderDao.SingletonHelper.INSTANCE;
    }

    @Override
    public Order getOrder(Long id) throws OrderNotFoundException {
        return orderList.stream()
                .filter(order -> id.equals(order.getId()))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " is not found."));
    }

    @Override
    public Order getOrderBySecureId(String secureId) throws OrderNotFoundException {
        return orderList.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + secureId + " is not found."));
    }

    @Override
    public void save(Order order) {
        synchronized (lock) {
            if (order.getId() != null) {
                orderList.set(orderList.indexOf(getOrder(order.getId())), order);
            } else {
                order.setId(maxId++);
                orderList.add(order);
            }
        }
    }

    @Override
    public void clear() {
        orderList.clear();
        maxId = 0;
    }
}
