package com.mingguang.dreambuilder.security;

import com.mingguang.dreambuilder.service.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class MyJwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long validityInMilliseconds;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    public String generateToken(UserDetails userDetails) {
        // Implement token generation logic using the userDetails
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public Authentication getUserDetailsFromToken(String token) {
        // Implement logic to extract user details from the token
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        // Extract user details from claims
        // Example: String username = claims.getSubject();
        // Create UserDetails object based on extracted information
        String userName = claims.getSubject();
        UserDetails details = userDetailService.loadUserByUsername(
                userName);
        if (details == null){
            throw new UsernameNotFoundException("未找到Token对应User对象");
        }
        Authentication authentication=new UsernamePasswordAuthenticationToken(
                details,details.getPassword(),details.getAuthorities());
        return authentication;
    }
    public boolean validateToken(String token) {
        // Implement logic to validate the token
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

