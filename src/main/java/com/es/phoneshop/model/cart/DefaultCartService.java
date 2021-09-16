package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

public class DefaultCartService implements CartService {
    private static DefaultCartService instance;

    private Cart cart = new Cart();
    private ProductDao productDao;

    public static DefaultCartService getInstance() {
        synchronized (DefaultCartService.class) {
            if (instance == null) {
                instance = new DefaultCartService();
            }
        }
        return instance;
    }

    private DefaultCartService(){
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        CartItem cartItem = cart.findItemInCart(product);
        if (cartItem != null) {
            if (product.getStock() < quantity + cartItem.getQuantity()) {
                throw new OutOfStockException(product, quantity, product.getStock() - cartItem.getQuantity());
            }
            cartItem.setQuantity(quantity + cartItem.getQuantity());
        }
        else {
            if (product.getStock() < quantity) {
                throw new OutOfStockException(product, quantity, product.getStock());
            }
            cart.getItems().add(new CartItem(product, quantity));
        }
    }
}
