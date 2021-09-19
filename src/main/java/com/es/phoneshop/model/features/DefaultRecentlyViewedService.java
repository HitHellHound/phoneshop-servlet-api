package com.es.phoneshop.model.features;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class DefaultRecentlyViewedService implements RecentlyViewedService {
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recently_viewed";
    private final int MAX_SIZE_OF_RECENTLY_VIEWED_LIST = 3;

    private ProductDao productDao;

    private DefaultRecentlyViewedService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static class SingletonHelper {
        private static final DefaultRecentlyViewedService INSTANCE = new DefaultRecentlyViewedService();
    }

    public static DefaultRecentlyViewedService getInstance() {
        return DefaultRecentlyViewedService.SingletonHelper.INSTANCE;
    }


    @Override
    public synchronized RecentlyViewedProducts getRecentlyViewedProducts(HttpServletRequest request) {
        RecentlyViewedProducts viewedProducts = (RecentlyViewedProducts) request.getSession()
                .getAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (viewedProducts == null) {
            request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, viewedProducts = new RecentlyViewedProducts());
        }
        return viewedProducts;
    }

    @Override
    public synchronized void add(RecentlyViewedProducts recentlyViewedProducts, Long productId) {
        Product product = productDao.getProduct(productId);
        LinkedList<Product> products = recentlyViewedProducts.getProducts();
        if (products.contains(product)) {
            products.remove(products.indexOf(product));
        }
        products.addFirst(product);
        if (products.size() > MAX_SIZE_OF_RECENTLY_VIEWED_LIST) {
            products.removeLast();
        }
    }
}
