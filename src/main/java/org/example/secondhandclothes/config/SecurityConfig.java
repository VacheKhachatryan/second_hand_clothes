package org.example.secondhandclothes.config;

import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.config.properties.SecurityConfigProperties;
import org.example.secondhandclothes.security.ApiAccessDeniedHandler;
import org.example.secondhandclothes.security.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      SecurityConfigProperties properties,
      AuthenticationFilter authenticationFilter,
      ApiAccessDeniedHandler apiAccessDeniedHandler)
      throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(SessionManagementConfigurer::disable)
        .authorizeHttpRequests(auth ->
            auth.requestMatchers(HttpMethod.POST,
                    properties.getPermittedUris().getPost().toArray(String[]::new)).permitAll()
                .requestMatchers(HttpMethod.GET,
                    properties.getPermittedUris().getGet().toArray(String[]::new)).permitAll()
                .anyRequest().authenticated())
        .formLogin(FormLoginConfigurer::disable)
        .logout(LogoutConfigurer::disable)
        .httpBasic(HttpBasicConfigurer::disable)
        .exceptionHandling(configurer -> configurer.accessDeniedHandler(apiAccessDeniedHandler))
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AntPathMatcher antPathMatcher() {
    return new AntPathMatcher();
  }
}
