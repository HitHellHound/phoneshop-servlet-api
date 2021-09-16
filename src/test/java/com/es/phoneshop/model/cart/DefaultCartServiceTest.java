package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private CartService cartService;
    private Cart cart;
    private Product testProduct;

    @Before
    public void setup() {
        cartService = DefaultCartService.getInstance();
        cart = new Cart();
        testProduct = new Product("test", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
    }

    @After
    public void teardown() {
        ArrayListProductDao.getInstance().clear();
    }

    @Test
    public void testGetCartReturnExistingCart() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any())).thenReturn(cart);
        Cart result = cartService.getCart(request);
        assertSame(cart, result);
    }

    @Test
    public void testGetCartReturnNewCart() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any())).thenReturn(null);
        Cart result = cartService.getCart(request);
        assertNotNull(result);
    }

    @Test
    public void testAdd() throws OutOfStockException {
        Product testProduct = new Product("test", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        ArrayListProductDao.getInstance().save(testProduct);
        cartService.add(cart, testProduct.getId(), 2);
        Product result = cart.findItem(testProduct).getProduct();
        assertSame(testProduct, result);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddThrowsOutOfStockException() throws OutOfStockException {
        ArrayListProductDao.getInstance().save(testProduct);
        cartService.add(cart, testProduct.getId(), 102);
    }
}