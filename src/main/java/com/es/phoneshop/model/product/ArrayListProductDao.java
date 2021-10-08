package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    public Product getProduct(Long id) throws ProductNotFoundException {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " is not found."));
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        return products.stream()
                .filter(product -> query == null || query.equals("") ||
                        matchesWithQuery(query, product.getDescription(), QueryOption.anyWords) > 0)
                .sorted(queryComparator(query, sortField, sortOrder))
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> advancedFindProducts(String query, QueryOption queryOption, BigDecimal minPrice, BigDecimal maxPrice) {
        return products.stream()
                .filter(product -> query == null || query.equals("") ||
                        matchesWithQuery(query, product.getDescription(), queryOption) > 0)
                .sorted(queryComparator(query, null, null))
                .filter(product -> product.getPrice() != null)
                .filter(product -> {
                    if (minPrice != null) {
                        if (product.getPrice().compareTo(minPrice) >= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return true;
                })
                .filter(product -> {
                    if (maxPrice != null) {
                        if (maxPrice.compareTo(product.getPrice()) >= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return true;
                })
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

    private long matchesWithQuery(String query, String name, QueryOption queryOption) {
        String lcName = name.toLowerCase();
        long count = Arrays.stream(query.toLowerCase().split("[\\p{Blank}\\p{Punct}]+"))
                .filter(queryWord -> {
                    Pattern pattern = Pattern.compile("(^|[\\p{Blank}\\p{Punct}])" + queryWord + "([\\p{Blank}\\p{Punct}]|$)");
                    Matcher matcher = pattern.matcher(lcName);
                    return !queryWord.equals("") && matcher.find();
                })
                .count();
        long countOfQueryWords = query.toLowerCase().split("[\\p{Blank}\\p{Punct}]+").length;
        if (queryOption == QueryOption.allWords && count < countOfQueryWords) {
            count = 0;
        }
        return count;
    }

    private Comparator<Product> queryComparator(String query, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = Comparator.comparing(product -> {
            if (sortField == SortField.description) {
                return (Comparable) product.getDescription();
            } else if (sortField == SortField.price) {
                return (Comparable) product.getPrice();
            }
            if (query != null && !query.equals("")) {
                return (Comparable) matchesWithQuery(query, product.getDescription(), QueryOption.anyWords);
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
