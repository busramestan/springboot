package com.busramestan.springboot.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${spring.security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token).getBody();
        return  claimsTFunction.apply(claims);
    }

    public String getUsernameByToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public boolean isTokenValid(String token) {
        String usernameFromToken = getUsernameByToken(token);
        return usernameFromToken != null && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
        Date expiredDate = extractClaims(token, Claims::getExpiration);
        return expiredDate.before(new Date());
    }

    public Key getSigninKey() {
       byte[] keyBytes =  Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);

    }
}
