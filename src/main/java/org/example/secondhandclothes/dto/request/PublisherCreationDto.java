package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PublisherCreationDto {

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The publisher name", example = "John Doe Org.")
  private String name;

  @Valid @NotNull private AddressCreationDto address;
}
