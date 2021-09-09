package com.es.phoneshop.model.product;

import java.util.Arrays;

public enum SortField {
    description,
    price;

    public static SortField getValue(String name) {
        return Arrays.stream(SortField.values())
                .filter(value -> value.name().equals(name))
                .findAny()
                .orElse(null);
    }
}
