package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.secondhandclothes.validator.constraint.NullOrNotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GarmentUpdateDto {

  @NotNull
  @Schema(
      description = "The garment id that should be updated",
      example = "8d943c89-34c1-44ab-9a6c-2f8fc052ef7d")
  private UUID id;

  @NullOrNotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The new name of the garment", example = "Hanes Men's Cool Dri Polo Shirt,")
  private String name;

  @NullOrNotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The new description of the garment", example = "Worn twice")
  private String description;

  @Schema(
      description = "The new category id of the garment",
      example = "6ab5b0d9-f38d-4d1a-bdca-a776271459c7")
  private UUID categoryId;

  @Schema(description = "The new availability of the garment")
  private Boolean isAvailable;

  @PositiveOrZero
  @Schema(description = "The new price of the garment", example = "10")
  private BigDecimal price;

  @NullOrNotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The new size of the garment", example = "XL")
  private String size;
}
