package org.example.secondhandclothes.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties("application.security")
@Data
public class SecurityConfigProperties {

  private String secretKey;
  private Long accessTokenExpiration;
  private Long refreshTokenExpiration;
  private Admin admin;
  private PermittedUrls permittedUris;

  @Data
  public static class Admin {

    private String username;
    private String password;
  }

  @Data
  public static class PermittedUrls {

    private Set<String> post;
    private Set<String> get;
  }
}
