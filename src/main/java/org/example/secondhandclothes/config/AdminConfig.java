package org.example.secondhandclothes.config;

import org.example.secondhandclothes.config.properties.SecurityConfigProperties;
import org.example.secondhandclothes.entity.UserEntity;
import org.example.secondhandclothes.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.example.secondhandclothes.entity.Role.ADMIN;

@Configuration
public class AdminConfig {

  @Bean
  public CommandLineRunner fillDatabase(
      UserRepository userRepository,
      SecurityConfigProperties securityConfigProperties,
      PasswordEncoder passwordEncoder) {
    SecurityConfigProperties.Admin admin = securityConfigProperties.getAdmin();
    String username = admin.getUsername();
    return a -> {
      if (!userRepository.existsByEmailIgnoreCase(username)) {
        userRepository.save(
            UserEntity.builder()
                .firstname("App")
                .lastname("Admin")
                .role(ADMIN)
                .password(passwordEncoder.encode(admin.getPassword()))
                .email(username)
                .build());
      }
    };
  }
}
