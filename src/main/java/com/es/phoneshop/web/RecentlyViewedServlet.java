package com.es.phoneshop.web;

import com.es.phoneshop.model.features.DefaultRecentlyViewedService;
import com.es.phoneshop.model.features.RecentlyViewedService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecentlyViewedServlet extends HttpServlet {
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recentlyViewedService = DefaultRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("recentlyViewed", recentlyViewedService.getRecentlyViewedProducts(request));
        request.getRequestDispatcher(PageMappings.RECENTLY_VIEWED_JSP).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
