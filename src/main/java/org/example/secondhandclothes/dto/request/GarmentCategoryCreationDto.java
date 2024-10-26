package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GarmentCategoryCreationDto {

  @NotBlank
  @Schema(description = "The garment category name", example = "Trousers")
  private String name;
}
