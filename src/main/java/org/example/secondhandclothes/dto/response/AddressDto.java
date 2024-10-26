package org.example.secondhandclothes.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

  @Schema(
      example = "23194473-079c-4f5c-9523-adf995b07cf1",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private UUID id;

  @Schema(example = "United States", requiredMode = Schema.RequiredMode.REQUIRED)
  private String country;

  @Schema(example = "Delaware", requiredMode = Schema.RequiredMode.REQUIRED)
  private String state;

  @Schema(example = "Wilmington", requiredMode = Schema.RequiredMode.REQUIRED)
  private String city;

  @Schema(example = "Ayre Street", requiredMode = Schema.RequiredMode.REQUIRED)
  private String street;

  @Schema(example = "19804", requiredMode = Schema.RequiredMode.REQUIRED)
  private String zipCode;

  @Schema(example = "1623 E", requiredMode = Schema.RequiredMode.REQUIRED)
  private String address1;

  @Schema(example = "ARM589216", requiredMode = Schema.RequiredMode.REQUIRED)
  private String address2;
}
