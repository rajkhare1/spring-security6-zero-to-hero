package com.rajkhare.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestValidationBeforeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // do the typecasting because we are going to use the HTTP request and response
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // read the headers becuase authoriztion is there
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if(null != header) {
            header = header.trim();
            // check if the credentials start with the prefix Basic
            if(StringUtils.startsWithIgnoreCase(header, "Basic ")) {
                // remove the prefix Basic from the value and convert it into byte
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
            }
        }

    }
}
