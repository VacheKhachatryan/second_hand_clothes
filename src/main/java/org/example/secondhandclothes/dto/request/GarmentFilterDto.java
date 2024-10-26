package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.example.secondhandclothes.validator.constraint.NullOrNotBlank;
import org.springdoc.core.annotations.ParameterObject;

import java.math.BigDecimal;
import java.time.Instant;

import static org.example.secondhandclothes.dto.request.GarmentSortField.NAME;
import static org.example.secondhandclothes.dto.request.SortOrder.ASC;

@Data
@ParameterObject
public class GarmentFilterDto {

  @NullOrNotBlank
  @Parameter(description = "Filter by a word that exists in the garment name", example = "Polo")
  private String name;

  @NullOrNotBlank
  @Parameter(
      description = "Filter by a word that exists in the garment description",
      example = "like new")
  private String description;

  @PositiveOrZero
  @Parameter(
      description = "Filter the garments which price is greater than or equals to the given value",
      example = "100")
  private BigDecimal priceGte;

  @PositiveOrZero
  @Parameter(
      description = "Filter the garments which price is smaller than or equals to the given value",
      example = "200")
  private BigDecimal priceLte;

  @NullOrNotBlank
  @Parameter(
      description = "Filter the garments which size equals (ignore case) to the given value",
      example = "L")
  private String size;

  @Parameter(
      description = "Filter the garments which availability is the same as the given value",
      example = "true")
  private Boolean isAvailable;

  @NullOrNotBlank
  @Parameter(
      description = "Filter the garments which publisher name contains given value",
      example = "George")
  private String publisherName;

  @NullOrNotBlank
  @Parameter(
      description = "Filter the garments which category's name equals (ignore case) to given value",
      example = "Trousers")
  private String categoryName;

  @Parameter(
      description =
          "Filter the garments which update date is greater than or equals to the given value",
      example = "2024-10-25T21:08:42.679Z")
  private Instant updatedAtGte;

  @Parameter(
      description =
          "Filter the garments which update date is smaller than or equals to the given value",
      example = "2024-10-25T21:08:42.679Z")
  private Instant updatedAtLte;

  @Schema(
      description =
          "The pagination parameter indicating how many records should be ignored from start. (Default: 0)",
      example = "10")
  private int offset = 0;

  @Parameter(
      description =
          "The pagination parameter indicating how many records at maximum the response should include. (Default: 10)",
      example = "20")
  private int limit = 10;

  @Parameter(
      description = "Records sorting by the given property name. (Default: 'NAME')",
      example = "UPDATED_AT")
  private GarmentSortField sortBy = NAME;

  @Parameter(description = "Records sorting by the given order. (Default: 'ASC')", example = "DESC")
  private SortOrder sortOrder = ASC;
}
