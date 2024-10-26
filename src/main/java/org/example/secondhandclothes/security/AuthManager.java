package org.example.secondhandclothes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.config.properties.SecurityConfigProperties;
import org.example.secondhandclothes.entity.PublisherEntity;
import org.example.secondhandclothes.entity.Role;
import org.example.secondhandclothes.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManager {

  public static final String ID = "id";
  public static final String ROLE = "role";
  public static final String PUBLISHER_ID = "publisherId";

  private final SecurityConfigProperties properties;

  public User extractUser(Claims claims) {
    return new User(
        UUID.fromString(claims.get(ID, String.class)),
        claims.getSubject(),
        Optional.ofNullable(claims.get(PUBLISHER_ID, String.class))
            .map(UUID::fromString)
            .orElse(null),
        Role.valueOf(claims.get(ROLE, String.class)));
  }

  public String generateAccessToken(UserEntity user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(ID, user.getId());
    claims.put(ROLE, user.getRole());
    claims.put(
        PUBLISHER_ID,
        Optional.ofNullable(user.getPublisher()).map(PublisherEntity::getId).orElse(null));
    return buildToken(claims, user.getEmail(), properties.getAccessTokenExpiration());
  }

  public String generateRefreshToken(String email) {
    return buildToken(Map.of(), email, properties.getRefreshTokenExpiration());
  }

  private String buildToken(Map<String, ?> claims, String email, long expiration) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims extractAllClaims(String token)
      throws ExpiredJwtException,
          UnsupportedJwtException,
          MalformedJwtException,
          SignatureException,
          IllegalArgumentException {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(properties.getSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
