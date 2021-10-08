package com.es.phoneshop.model.product;

import java.util.Arrays;

public enum QueryOption {
    allWords,
    anyWords;

    public static QueryOption getValue(String name) {
        return Arrays.stream(QueryOption.values())
                .filter(value -> value.name().equals(name))
                .findAny()
                .orElse(null);
    }
}
