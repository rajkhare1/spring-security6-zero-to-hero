package com.rajkhare.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

                byte[] decoded;
                try {
                    // decoding of the base 64 token
                    decoded = Base64.getDecoder().decode(base64Token);
                    //convert the decoded byte[] into String
                    String token = new String(decoded, StandardCharsets.UTF_8);//un:pwd
                    // We need to extract username and password from delimiter format token
                    int delim = token.indexOf(":");
                    if(delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    // extract the email from delim
                    String email = token.substring(0, delim);
                    if(email.toLowerCase().contains("test")) {
                        //set the status in respone
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                } catch (IllegalArgumentException exception) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        // we need to make sure that after perfoming businees logic we must need to call the next filter in filter chain
        // Note: if we don't have below line of code then next filter will never be called and our authentication will fail.
         chain.doFilter(request,response);
    }
}
