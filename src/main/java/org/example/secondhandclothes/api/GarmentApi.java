package org.example.secondhandclothes.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.secondhandclothes.dto.request.GarmentCreationDto;
import org.example.secondhandclothes.dto.request.GarmentFilterDto;
import org.example.secondhandclothes.dto.request.GarmentPaginatedResponseDto;
import org.example.secondhandclothes.dto.request.GarmentUpdateDto;
import org.example.secondhandclothes.dto.response.GarmentDto;
import org.example.secondhandclothes.error.ApiError;
import org.example.secondhandclothes.security.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Garment", description = "Garment management APIs")
public interface GarmentApi {

  @Operation(
      summary = "Find garments",
      description = " Find garments paginated and with filter.")
  @ApiResponse(
      responseCode = "201",
      description = "Garments are returned.",
      content = {
        @Content(
            schema = @Schema(implementation = GarmentPaginatedResponseDto.class),
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
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<GarmentPaginatedResponseDto> find(GarmentFilterDto dto);

  @Operation(
      summary = "Create a new garment",
      description =
          """
      Create new garment for authenticated user.
      Admins can't create a new garment.""",
      security = @SecurityRequirement(name = "auth"))
  @ApiResponse(
      responseCode = "201",
      description = "Garment is successfully created.",
      content = {
        @Content(
            schema = @Schema(implementation = GarmentDto.class),
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
      description = "User is not authorized to do this operation (Admins can't create a garment).",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<GarmentDto> create(GarmentCreationDto dto, User user);

  @Operation(
      summary = "Update the garment",
      description =
          """
      Update the properties that is given in the request body.
      Apart from id, all other properties are optional.
      Pass the properties that need to be updated""",
      security = @SecurityRequirement(name = "auth"))
  @ApiResponse(
      responseCode = "200",
      description = "Garment is successfully updated.",
      content = {
        @Content(
            schema = @Schema(implementation = GarmentDto.class),
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
          "User is not authorized to do this operation (Only admin and the garment publisher can update the garment).",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "404",
      description = "The garment admin want to update is not found.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<GarmentDto> update(GarmentUpdateDto dto, User user);

  @Operation(
      summary = "Delete the garment",
      description = "Deletes the garment by a given id",
      security = @SecurityRequirement(name = "auth"))
  @ApiResponse(responseCode = "200", description = "Garment is successfully deleted.")
  @ApiResponse(
      responseCode = "401",
      description = "User is not authenticated.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "403",
      description =
          "User is not authorized to do this operation (Only admin and the garment publisher can delete the garment).",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "404",
      description = "The garment admin want to delete is not found.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<Void> delete(
      @Parameter(description = "The garment id", example = "1b7e1940-3599-425b-b5c3-952d7345543d")
          UUID id,
      User user);
}
