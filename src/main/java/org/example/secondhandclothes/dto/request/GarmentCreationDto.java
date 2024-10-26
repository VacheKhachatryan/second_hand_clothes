package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GarmentCreationDto {

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The garment name", example = "Hanes Men's Ultimate Crewneck T-Shirt")
  private String name;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The garment description", example = "Worn once")
  private String description;

  @NotNull
  @Schema(description = "The garment category id", example = "18efae41-4d37-4ecf-8203-b8c58b6d15d9")
  private UUID categoryId;

  @NotNull
  @PositiveOrZero
  @Schema(description = "The garment price")
  private BigDecimal price;

  @NotBlank
  @Size(max = 255)
  @Schema(description = "The garment size", example = "L")
  private String size;
}
