package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private Long productId;

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        Product product = new Product();
        ArrayListProductDao.getInstance().save(product);
        productId = product.getId();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @After
    public void teardown() {
        ArrayListProductDao.getInstance().clear();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + productId);
        servlet.doGet(request, response);

        InOrder inOrder = Mockito.inOrder(request, requestDispatcher);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(request).setAttribute(eq("product"), any());
        inOrder.verify(requestDispatcher).forward(request, response);
    }
}