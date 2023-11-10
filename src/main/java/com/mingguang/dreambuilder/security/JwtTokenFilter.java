package com.mingguang.dreambuilder.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final MyJwtTokenProvider myJwtTokenProvider;
    public JwtTokenFilter(MyJwtTokenProvider myJwtTokenProvider){
        this.myJwtTokenProvider = myJwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null && myJwtTokenProvider.validateToken(token)){
            Authentication authentication = myJwtTokenProvider.getUserDetailsFromToken(token);
            if (authentication != null){
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
    private String extractToken(HttpServletRequest request) {
        // Implement logic to extract the token from the request
        // Example: String bearerToken = request.getHeader("Authorization");
        // Extract token from bearerToken
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
