package com.es.phoneshop.model.product;

import java.util.Arrays;

public enum SortOrder {
    asc,
    desc;

    public static SortOrder getValue(String name) {
        return Arrays.stream(SortOrder.values())
                .filter(value -> value.name().equals(name))
                .findAny()
                .orElse(null);
    }
}
