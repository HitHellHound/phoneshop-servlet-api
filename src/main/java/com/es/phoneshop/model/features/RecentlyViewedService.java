package com.es.phoneshop.model.features;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewedService {
    RecentlyViewedProducts getRecentlyViewedProducts(HttpServletRequest request);

    void add(RecentlyViewedProducts recentlyViewedProducts, Long productId);
}
