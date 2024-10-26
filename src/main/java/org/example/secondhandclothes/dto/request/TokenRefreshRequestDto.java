package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRefreshRequestDto {

  @NotBlank
  @Schema(
      description = "The user refresh token",
      example =
          "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huLkRvZUBnbWFpbC5jb20iLCJpYXQiOjE3Mjk4Njc0MzcsImV4cCI6MTczMDQ3MjIzN30.-vRecKk64_5DI4T_Jqo-uUK6OhpUEXW-5GJ61TSgkhY")
  private String refreshToken;
}
