package org.example.secondhandclothes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.secondhandclothes.entity.Role;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
public class UserDto {

  @Schema(
      description = "The user id",
      example = "4d2b45d6-a855-4739-a63d-843b610dcd90",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private UUID id;

  @Schema(example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
  private String firstname;

  @Schema(example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
  private String lastname;

  @Schema(example = "John.Doe@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;

  @Schema(example = "USER", requiredMode = Schema.RequiredMode.REQUIRED)
  private Role role;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private PublisherDto publisher;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Instant createdAt;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Instant updatedAt;
}
