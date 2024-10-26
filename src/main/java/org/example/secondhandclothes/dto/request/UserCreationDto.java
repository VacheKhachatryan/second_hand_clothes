package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.secondhandclothes.validator.constraint.Password;

@Data
public class UserCreationDto {

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The user firstname", example = "John")
  private String firstname;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The user lastname", example = "Doe")
  private String lastname;

  @NotBlank
  @Email
  @Size(min = 1, max = 320)
  @Schema(
      description = "The user email which later will be used to login alongside with the password",
      example = "John.Doe@gmail.com")
  private String email;

  @NotBlank
  @Password
  @Schema(
      description =
          "The user password (the length should be from 8 to 128, the password should contain at least one lowercase and uppercase characters, one digit and one special character.)",
      example = "John3567/")
  private String password;

  @Valid @NotNull private PublisherCreationDto publisher;
}
