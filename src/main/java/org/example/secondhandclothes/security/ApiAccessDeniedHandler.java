package org.example.secondhandclothes.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.error.ApiError;
import org.example.secondhandclothes.error.ApplicationError;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.example.secondhandclothes.error.ApplicationError.ACCESS_DENIED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    ApplicationError error = ACCESS_DENIED;
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(error.getHttpStatus().value());
    response
        .getWriter()
        .write(
            objectMapper.writeValueAsString(
                ApiError.builder()
                    .errorCode(error.getCode())
                    .message(error.getMessage())
                    .build()));
  }
}