package com.asa.cafe.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String secret = "secret";

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        System.out.println("Token empty from extractClaims()"+token.isEmpty());
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        //System.out.println("Token empty from extractAllClaims()"+token.isEmpty());
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return body;
    }

    public String extractUsername(String token) {
        //System.out.println("Token empty from extractUsername()"+token.isEmpty());

        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        //System.out.println("Token empty from extractExpiration()"+token.isEmpty());

        return extractClaims(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token){
        //System.out.println("Token empty from isTokenExpired()"+token.isEmpty());

        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        //System.out.println("Token empty from validateToken()"+token.isEmpty());

        final String username = extractUsername(token);
        return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }



    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }
}
