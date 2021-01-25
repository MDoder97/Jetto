package com.markododer.webproject.util;

import com.markododer.webproject.model.MyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "MY JWT SECRET";

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        if(claims != null)
            return claims.getSubject();
        return null;
    }

    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String generateToken(MyUser user){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public boolean validateToken(String token, MyUser user) {
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
