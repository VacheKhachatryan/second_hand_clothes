package org.example.secondhandclothes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GarmentCategoryDto {

  @Schema(
      example = "18efae41-4d37-4ecf-8203-b8c58b6d15d9",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private UUID id;

  @Schema(example = "Trousers", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
}
