package com.springboot.school.security;

import com.springboot.school.exception.SchoolApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        }catch(SignatureException ex) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");

        }catch(MalformedJwtException ex) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Invalid Jwt token");

        }catch(ExpiredJwtException ex) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Expired Jwt token");

        }catch(UnsupportedJwtException ex) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Unsupported Jwt token");

        }catch(IllegalArgumentException ex) {
            throw new SchoolApiException(HttpStatus.BAD_REQUEST, "Jwt claims string is empty");
        }
    }
}
