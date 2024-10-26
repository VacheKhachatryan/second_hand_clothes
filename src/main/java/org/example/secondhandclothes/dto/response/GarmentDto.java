package org.example.secondhandclothes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GarmentDto {

  @Schema(
      example = "eb3fbf35-7f32-4262-afd9-e4d41fb3ef59",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private UUID id;

  @Schema(
      example = "Hanes Men's Ultimate Crewneck T-Shirt",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  @Schema(example = "Worn once", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private GarmentCategoryDto category;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private BigDecimal price;

  @Schema(example = "L", requiredMode = Schema.RequiredMode.REQUIRED)
  private String size;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private boolean isAvailable;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private PublisherDto publisher;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Instant createdAt;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private Instant updatedAt;
}
