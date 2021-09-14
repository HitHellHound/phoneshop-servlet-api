package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

public class DemoDataServletContextListener implements ServletContextListener {
    private ProductDao productDao;

    public DemoDataServletContextListener() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean insertDemoData = Boolean.valueOf(servletContextEvent.getServletContext().getInitParameter("insertDemoData"));
        if (insertDemoData) {
            getSampleProducts().stream().forEach(product -> productDao.save(product));
            new Thread(() -> {
                Product product = productDao.getProduct(0L);
                Random random = new Random();
                for (int i = 0; i < 30; i++) {
                    try {
                        Thread.sleep(10000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    product.setPrice(BigDecimal.valueOf(50 + random.nextInt(100)));
                }
            }).start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        result.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        result.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        result.add(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "manufacturer/Apple/Apple%20iPhone.jpg"));
        result.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "manufacturer/Apple/Apple%20iPhone%206.jpg"));
        result.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        result.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        result.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        result.add(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "manufacturer/Nokia/Nokia%203310.jpg"));
        result.add(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "manufacturer/Palm/Palm%20Pixi.jpg"));
        result.add(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "manufacturer/Siemens/Siemens%20C56.jpg"));
        result.add(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "manufacturer/Siemens/Siemens%20C61.jpg"));
        result.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "manufacturer/Siemens/Siemens%20SXG75.jpg"));
        return result;
    }
}
