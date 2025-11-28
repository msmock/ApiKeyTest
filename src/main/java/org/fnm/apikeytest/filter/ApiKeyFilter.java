package org.fnm.apikeytest.filter;

import jakarta.servlet.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
public class ApiKeyFilter implements Filter {

    @Value( "${security.api-keys}" )
    private Set<String> registeredKeys;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method =  req.getMethod();

        String apiKey = req.getHeader("X-API-KEY");
        System.out.println("API Key : " + apiKey);

        if (method.equals("GET")){
            chain.doFilter(request, response);
        } else if (apiKey != null && registeredKeys.contains(apiKey)){
            chain.doFilter(request, response);
        } else {
            res.setStatus(405);
            res.getWriter().write("Method call not authorized!");
        }
    }
}