package com.es.phoneshop.model.features.recently.viewed;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;

public class RecentlyViewedProducts {
    private LinkedList<Product> products;

    public RecentlyViewedProducts() {
        this.products = new LinkedList<>();
    }

    public LinkedList<Product> getProducts() {
        return products;
    }
}
