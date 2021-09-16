package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class CartTest {
    private Cart cart;
    private CartItem cartItem;

    @Before
    public void setup() {
        cart = new Cart();
        cartItem = new CartItem(new Product("test", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"), 10);
    }

    @Test
    public void findItemInCartExistingItem() {
        cart.getItems().add(cartItem);
        CartItem result = cart.findItem(cartItem.getProduct());
        assertSame(cartItem, result);
    }

    @Test
    public void findItemInCartNotExistingItem() {
        CartItem result = cart.findItem(cartItem.getProduct());
        assertNull(result);
    }
}