package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.secondhandclothes.entity.Country;

@Data
public class AddressCreationDto {

  @NotNull
  @Schema(description = "The country of the publisher", example = "UNITED_STATES")
  private Country country;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The state of the publisher", example = "Delawere")
  private String state;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The city of the publisher", example = "Wilmington")
  private String city;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The street of the publisher", example = "Ayre Street")
  private String street;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The zip code of the publisher", example = "19804")
  private String zipCode;

  @NotBlank
  @Size(min = 1, max = 255)
  @Schema(description = "The address1 of the publisher", example = "1623 E")
  private String address1;

  @Size(min = 1, max = 255)
  @Schema(description = "The address2 of the publisher", example = "ARM589216")
  private String address2;
}
