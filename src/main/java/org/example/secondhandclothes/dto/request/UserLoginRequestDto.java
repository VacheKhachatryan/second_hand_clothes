package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {

  @NotBlank
  @Schema(description = "The user email", example = "John.Doe@gmail.com")
  private String email;

  @NotBlank
  @Schema(description = "The user email", example = "John3567/")
  private String password;
}
