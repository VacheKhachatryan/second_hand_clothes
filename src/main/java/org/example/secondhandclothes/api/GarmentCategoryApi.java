package org.example.secondhandclothes.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.secondhandclothes.dto.request.GarmentCategoryCreationDto;
import org.example.secondhandclothes.dto.response.GarmentCategoryDto;
import org.example.secondhandclothes.error.ApiError;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Garment Category", description = "Garment Category management APIs")
public interface GarmentCategoryApi {

  @Operation(
      summary = "Get all garment categories",
      description = "Returns all the garment categories sorted by th name.)",
      security = @SecurityRequirement(name = "auth"))
  @ApiResponse(
      responseCode = "200",
      description = "The Garment categories are successfully returned.",
      content =
          @Content(
              array = @ArraySchema(schema = @Schema(implementation = GarmentCategoryDto.class)),
              mediaType = "application/json"))
  @ApiResponse(
      responseCode = "401",
      description = "User is not authenticated.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<List<GarmentCategoryDto>> getAll();

  @Operation(
      summary = "Create a new garment category",
      description =
          """
      Create a new garment category which should have unique name against existing categories.
      Only admins can create a new garment category.""",
      security = @SecurityRequirement(name = "auth"))
  @ApiResponse(
      responseCode = "201",
      description = "Garment category is successfully created.",
      content = {
        @Content(
            schema = @Schema(implementation = GarmentCategoryDto.class),
            mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "400",
      description = "There is an invalid value in the user input",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "401",
      description = "User is not authenticated.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "403",
      description =
          "User is not authorized to do this operation (Only admins can create a new garment category).",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "409",
      description = "There is a garment category created with such name.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<GarmentCategoryDto> create(GarmentCategoryCreationDto dto);

  @Operation(
      summary = "Delete the garment category",
      description =
          """
      Delete the garment category by a given id.
      Only admins can delete a garment category.""",
      security = @SecurityRequirement(name = "auth"))
  @ApiResponse(responseCode = "200", description = "Garment category is successfully deleted.")
  @ApiResponse(
      responseCode = "401",
      description = "User is not authenticated.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "403",
      description =
          "User is not authorized to do this operation (Only admins can delete a garment category).",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "404",
      description = "There is a garment category found with such id.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<Void> delete(UUID id);
}
