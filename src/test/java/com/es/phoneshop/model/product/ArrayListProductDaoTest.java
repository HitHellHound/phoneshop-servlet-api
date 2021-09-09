package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

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
        assertFalse(productDao.findProducts("", null, null).isEmpty());
    }

    @Test
    public void testFindProductsReturnsRightProducts() {
        List<Product> products = productDao.findProducts("", null, null);
        long result = products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .count();
        assertEquals(products.size(), result);
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
}
