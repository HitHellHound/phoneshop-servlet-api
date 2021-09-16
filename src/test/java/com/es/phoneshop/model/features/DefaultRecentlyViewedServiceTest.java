package com.es.phoneshop.model.features;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private RecentlyViewedService recentlyViewedService;
    private RecentlyViewedProducts recentlyViewedProducts;

    @Before
    public void setup() {
        recentlyViewedService = DefaultRecentlyViewedService.getInstance();
        recentlyViewedProducts = new RecentlyViewedProducts();
    }

    @Test
    public void testGetRecentlyViewedProductsExistingProducts() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any())).thenReturn(recentlyViewedProducts);
        RecentlyViewedProducts result = recentlyViewedService.getRecentlyViewedProducts(request);
        assertSame(recentlyViewedProducts, result);
    }

    @Test
    public void testGetRecentlyViewedProductsNotExistingProducts() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any())).thenReturn(null);
        RecentlyViewedProducts result = recentlyViewedService.getRecentlyViewedProducts(request);
        assertNotNull(result);
    }
}