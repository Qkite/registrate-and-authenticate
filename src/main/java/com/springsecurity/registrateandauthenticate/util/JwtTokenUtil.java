package com.springsecurity.registrateandauthenticate.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.Callable;


public class JwtTokenUtil {

    // 토큰 만드는 메소드
    public static String createToken(String userName, String key, long expireTime){

        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // 토큰 까는 메소드
    public static String getUserName(String token, String key){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        return claims.get("userName").toString();
    }

    public static boolean isExpired(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }
}


