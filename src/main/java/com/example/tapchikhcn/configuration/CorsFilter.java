package com.example.tapchikhcn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Value("${endpoints.cors.allowed-headers}")
    String allowedHeaders;
    @Value("${endpoints.cors.allowed-methods}")
    String allowedMethods;
    @Value("${endpoints.cors.allowed-origins}")
    String allowedOrigins;
    @Value("${endpoints.cors.allowed-credentials}")
    String allowedCredentials;
    @Value("${endpoints.cors.max-age}")
    String maxAge;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Methods", allowedMethods);
        response.setHeader("Access-Control-Allow-Headers", allowedHeaders);
        response.setHeader("Access-Control-Allow-Credentials", allowedCredentials);
        response.setHeader("Access-Control-Max-Age", maxAge);
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(200);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
