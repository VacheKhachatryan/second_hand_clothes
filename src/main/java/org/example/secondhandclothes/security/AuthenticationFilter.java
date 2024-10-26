package org.example.secondhandclothes.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.config.properties.SecurityConfigProperties;
import org.example.secondhandclothes.error.ApiError;
import org.example.secondhandclothes.error.ApplicationError;
import org.example.secondhandclothes.error.AuthenticationException;
import org.example.secondhandclothes.error.ErrorMessage;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.example.secondhandclothes.error.ApplicationError.ACCESS_TOKEN_IS_EXPIRED;
import static org.example.secondhandclothes.error.ApplicationError.AUTH_HEADER_IS_MISSING;
import static org.example.secondhandclothes.error.ApplicationError.BEARER_IS_MISSING;
import static org.example.secondhandclothes.error.ApplicationError.INTERNAL_ERROR;
import static org.example.secondhandclothes.error.ApplicationError.INVALID_ACCESS_TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

  private final AuthManager authManager;
  private final ObjectMapper objectMapper;
  private final SecurityConfigProperties securityConfigProperties;
  private final AntPathMatcher matcher;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return switch (request.getMethod()) {
      case "GET" ->
          securityConfigProperties.getPermittedUris().getGet().stream()
              .anyMatch(uri -> matcher.match(uri, request.getRequestURI()));
      case "POST" ->
          securityConfigProperties.getPermittedUris().getPost().stream()
              .anyMatch(uri -> matcher.match(uri, request.getRequestURI()));
      default -> super.shouldNotFilter(request);
    };
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      final String authHeader = request.getHeader("Authorization");
      if (authHeader == null) {
        throw new AuthenticationException(
            AUTH_HEADER_IS_MISSING, AUTH_HEADER_IS_MISSING.getMessage());
      }
      if (!authHeader.startsWith("Bearer ")) {
        throw new AuthenticationException(BEARER_IS_MISSING, BEARER_IS_MISSING.getMessage());
      }

      User user = authManager.extractUser(validateAndExtractClaims(authHeader.substring(7)));
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
      filterChain.doFilter(request, response);
    } catch (AuthenticationException exception) {
      constructErrorResponse(response, exception);
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

  private Claims validateAndExtractClaims(String jwt) {
    try {
      return authManager.extractAllClaims(jwt);
    } catch (ExpiredJwtException e) {
      throw new AuthenticationException(
          ACCESS_TOKEN_IS_EXPIRED, ACCESS_TOKEN_IS_EXPIRED.getMessage());
    } catch (UnsupportedJwtException
        | MalformedJwtException
        | SignatureException
        | IllegalArgumentException e) {
      throw new AuthenticationException(INVALID_ACCESS_TOKEN, INVALID_ACCESS_TOKEN.getMessage());
    }
  }

  private void constructErrorResponse(HttpServletResponse response, AuthenticationException ex) {
    ApplicationError error = ex.getError();
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(error.getHttpStatus().value());

    try {response
          .getWriter()
          .write(objectMapper.writeValueAsString(
                  ApiError.builder()
                      .errorCode(error.getCode())
                      .message(error.getMessage())
                      .build()));
    } catch (IOException exception) {
      throw new AuthenticationException(
          INTERNAL_ERROR, ErrorMessage.JSON_SERIALIZATION_FAILURE_MESSAGE);
    }
  }
}
