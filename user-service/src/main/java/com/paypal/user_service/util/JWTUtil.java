package com.paypal.user_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {


     private static  final String SECRETKEY= "0e0a4890cd870f32986789e05c5f8060";

     //used for sign key this key is a symmetric because of hnac
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRETKEY.getBytes());
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean validateToken(String token,String userName){
        try{
          extractUserName(token);
          return true;
        }catch(Exception e){
        return  false;
        }
    }

    public String extractUserName(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    public  String generateToken(Map<String,Object> claims,String email){
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }






}
