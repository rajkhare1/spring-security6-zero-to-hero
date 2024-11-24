package com.rajkhare.filter;

import com.rajkhare.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // fetch the jwt from Authorization header 
        String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
        if(null != jwt) {
            try {
                Environment env = getEnvironment();
                if(null != env) {
                    //get the secret key from the envirionment
                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                    // get the claims from the jwt
                    Claims claims = Jwts.parser().verifyWith(secretKey).build()
                            .parseSignedClaims(jwt)
                            .getPayload();

                    //from the claims get read the username and authority
                    String username = String.valueOf(claims.get("username"));
                    String authorities = String.valueOf(claims.get("authorities"));

                    //with the help of username and authorities we will create the Authentication
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                    //store authentication in sercurity context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (RuntimeException e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/user");
    }

}
