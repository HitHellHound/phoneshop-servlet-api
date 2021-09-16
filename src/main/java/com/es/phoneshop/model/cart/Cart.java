package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public CartItem findItemInCart(Product product) {
        return items.stream()
                .filter(item -> item.getProduct() == product)
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Cart:{" + items + "}";
    }
}
