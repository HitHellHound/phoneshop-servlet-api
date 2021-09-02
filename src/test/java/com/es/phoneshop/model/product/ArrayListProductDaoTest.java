package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product testProduct;
    private Product anotherTestProduct;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        Currency usd = Currency.getInstance("USD");
        testProduct = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        anotherTestProduct = new Product("test2", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
    }

    @Test
    public void testSaveAndGetProduct() {
        productDao.save(testProduct);
        assertTrue(testProduct.getId() != null);
        Product result = productDao.getProduct(testProduct.getId());
        assertNotNull(result);
        assertEquals(testProduct.getCode(), result.getCode());
    }

    @Test
    public void testFindProductsIsNotEmpty() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsReturnsRightProducts() {
        List<Product> products = productDao.findProducts();
        long result = products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .count();
        assertTrue(result == products.size());
    }

    @Test
    public void testDeleteProduct() {
        productDao.save(testProduct);
        assertTrue(productDao.findProducts().contains(testProduct));
        productDao.delete(testProduct.getId());
        assertFalse(productDao.findProducts().contains(testProduct));
    }

    @Test
    public void testSaveReplacesProduct() {
        productDao.save(testProduct);
        anotherTestProduct.setId(testProduct.getId());
        productDao.save(anotherTestProduct);
        Product result = productDao.getProduct(testProduct.getId());
        assertEquals(result.getCode(), anotherTestProduct.getCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductThrowsException() {
        productDao.getProduct(-1L);
    }
}
