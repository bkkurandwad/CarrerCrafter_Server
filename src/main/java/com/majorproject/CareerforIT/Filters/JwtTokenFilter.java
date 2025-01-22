package com.majorproject.CareerforIT.Filters;

import com.majorproject.CareerforIT.Context.RequestContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter implements Filter, jakarta.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Extract the 'Token' header from the request
        String token = httpRequest.getHeader("Token");
        System.out.println("Filtering token: " + token);

        if (token != null) {
            // Store the token in RequestContext (ThreadLocal)
            RequestContext.setJwtToken(token);
        }

        // Continue processing the request
        chain.doFilter(request, response);

        // After request processing, clear the ThreadLocal data
        RequestContext.clear();
    }

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws jakarta.servlet.ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
