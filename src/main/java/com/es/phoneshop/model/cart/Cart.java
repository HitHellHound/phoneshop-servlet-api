package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> items;

    private int totalQuantity;
    private BigDecimal totalCost;
    private Currency currency;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalCost = new BigDecimal(0);
        this.currency = Currency.getInstance("USD");
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public CartItem findItem(Product product) {
        return items.stream()
                .filter(item -> product.getId().equals(item.getProduct().getId()))
                .findAny()
                .orElse(null);
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Cart:{" + items + "}" +
                " Total quantity: " + totalQuantity +
                " Total cost: " + totalCost;
    }
}
