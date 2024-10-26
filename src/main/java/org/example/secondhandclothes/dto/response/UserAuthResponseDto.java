package org.example.secondhandclothes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class UserAuthResponseDto {

  @Schema(
      description = "The user access token",
      example =
          "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6IjlhZWJlOTY1LTE3ZDktNDMzNS1iYWNjLWI5MTYyYzkyOTUxNiIsInN1YiI6IkpvaG4uRG9lQGdtYWlsLmNvbSIsImlhdCI6MTcyOTg2NzQzNywiZXhwIjoxNzI5ODY4NjM3fQ.cThpn_MzUZkvcoKQ0AwYerebglVlNnEZiwrOwhA3j04")
  private String accessToken;

  @Schema(
      description = "The user refresh token",
      example =
          "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huLkRvZUBnbWFpbC5jb20iLCJpYXQiOjE3Mjk4Njc0MzcsImV4cCI6MTczMDQ3MjIzN30.-vRecKk64_5DI4T_Jqo-uUK6OhpUEXW-5GJ61TSgkhY")
  private String refreshToken;
}
