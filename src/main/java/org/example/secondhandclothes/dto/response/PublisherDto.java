package org.example.secondhandclothes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PublisherDto {

  @Schema(
      example = "cd4f7109-74f3-4a68-b382-9312685cf1c7",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private UUID id;

  @Schema(example = "John Doe Org.", requiredMode = Schema.RequiredMode.REQUIRED)
  private String fullName;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private AddressDto address;
}
