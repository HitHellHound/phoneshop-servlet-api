package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Product testProduct1;
    private Product testProduct2;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        testProduct1 = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct2 = new Product("test2", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
    }

    @After
    public void teardown() {
        productDao.clear();
    }

    @Test
    public void testSaveAndGetProduct() {
        productDao.save(testProduct1);
        assertNotNull(testProduct1.getId());
        Product result = productDao.getProduct(testProduct1.getId());
        assertNotNull(result);
        assertEquals(testProduct1.getCode(), result.getCode());
    }

    @Test
    public void testFindProductsIsNotEmpty() {
        productDao.save(testProduct1);
        assertFalse(productDao.findProducts("", null, null).isEmpty());
    }

    @Test
    public void testFindProductsReturnsRightProducts() {
        saveSampleProducts();
        List<Product> products = productDao.findProducts("", null, null);
        long result = products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .count();
        assertEquals(products.size(), result);
    }

    @Test
    public void testFindProductsSortsByDescriptionAscOrder() {
        saveSampleProducts();
        List<Product> products = productDao.findProducts("", SortField.description, SortOrder.asc);
        Iterator<Product> iterator = products.iterator();
        Product previousProduct = iterator.next();
        boolean result = true;
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.getDescription().compareTo(previousProduct.getDescription()) < 0) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testFindProductsSortsByDescriptionDescOrder() {
        saveSampleProducts();
        List<Product> products = productDao.findProducts("", SortField.description, SortOrder.desc);
        Iterator<Product> iterator = products.iterator();
        Product previousProduct = iterator.next();
        boolean result = true;
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.getDescription().compareTo(previousProduct.getDescription()) > 0) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testFindProductsSortsByPriceAscOrder() {
        saveSampleProducts();
        List<Product> products = productDao.findProducts("", SortField.price, SortOrder.asc);
        Iterator<Product> iterator = products.iterator();
        Product previousProduct = iterator.next();
        boolean result = true;
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.getPrice().compareTo(previousProduct.getPrice()) < 0) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testFindProductsSortsByPriceDescOrder() {
        saveSampleProducts();
        List<Product> products = productDao.findProducts("", SortField.price, SortOrder.desc);
        Iterator<Product> iterator = products.iterator();
        Product previousProduct = iterator.next();
        boolean result = true;
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.getPrice().compareTo(previousProduct.getPrice()) > 0) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testDeleteProduct() {
        productDao.save(testProduct1);
        assertTrue(productDao.findProducts("", null, null).contains(testProduct1));
        productDao.delete(testProduct1.getId());
        assertFalse(productDao.findProducts("", null, null).contains(testProduct1));
    }

    @Test
    public void testSaveReplacesProduct() {
        productDao.save(testProduct1);
        testProduct2.setId(testProduct1.getId());
        productDao.save(testProduct2);
        Product result = productDao.getProduct(testProduct1.getId());
        assertEquals(testProduct2.getCode(), result.getCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductThrowsException() {
        productDao.getProduct(-1L);
    }

    private void saveSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "manufacturer/Apple/Apple%20iPhone%206.jpg"));
    }
}
