package com.es.phoneshop.model.product;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private long maxId;
    private final Object lock = new Object();

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    private static class SingletonHelper {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return ArrayListProductDao.SingletonHelper.INSTANCE;
    }

    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Product with id: " + id + " is not found."));
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        return products.stream()
                .filter(product -> query == null || query.equals("") || matchesWithQuery(query, product.getDescription()) > 0)
                .sorted(queryComparator(query, sortField, sortOrder))
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        synchronized (lock) {
            if (product.getId() != null) {
                products.set(products.indexOf(getProduct(product.getId())), product);
            } else {
                product.setId(maxId++);
                products.add(product);
            }
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (lock) {
            products.removeIf(product -> id.equals(product.getId()));
        }
    }

    @Override
    public void clear() {
        products.clear();
        maxId = 0;
    }

    private long matchesWithQuery(String query, String name) {
        String lcName = name.toLowerCase();
        return Arrays.stream(query.toLowerCase().split("[\\p{Blank}\\p{Punct}]+"))
                .filter(queryWord -> {
                    Pattern pattern = Pattern.compile("(^|[\\p{Blank}\\p{Punct}])" + queryWord + "([\\p{Blank}\\p{Punct}]|$)");
                    Matcher matcher = pattern.matcher(lcName);
                    return !queryWord.equals("") && matcher.find();
                })
                .count();
    }

    private Comparator<Product> queryComparator(String query, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = Comparator.comparing(product -> {
            if (sortField == SortField.description) {
                return (Comparable) product.getDescription();
            } else if (sortField == SortField.price) {
                return (Comparable) product.getPrice();
            }
            if (query != null && !query.equals("")) {
                return (Comparable) matchesWithQuery(query, product.getDescription());
            } else {
                return (Comparable) 0;
            }
        });
        if (sortOrder != SortOrder.asc) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
