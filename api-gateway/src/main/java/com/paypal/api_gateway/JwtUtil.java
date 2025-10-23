package com.paypal.api_gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private static final String SECRET="0e0a4890cd870f32986789e05c5f8060";

    public  static Key getSigninKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public  static Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
