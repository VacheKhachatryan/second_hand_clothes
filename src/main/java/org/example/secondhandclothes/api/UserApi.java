package org.example.secondhandclothes.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.secondhandclothes.dto.request.TokenRefreshRequestDto;
import org.example.secondhandclothes.dto.request.UserCreationDto;
import org.example.secondhandclothes.dto.request.UserLoginRequestDto;
import org.example.secondhandclothes.dto.response.UserAuthResponseDto;
import org.example.secondhandclothes.dto.response.UserDto;
import org.example.secondhandclothes.error.ApiError;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "User management APIs")
public interface UserApi {

  @Operation(
      summary = "Create a new user",
      description = "Register a new user with credentials, main information and address.")
  @ApiResponse(
      responseCode = "201",
      description = "User is successfully created.",
      content = {
        @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "400",
      description = "There is an invalid value in the user input",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "409",
      description = "There is already another user created with an email provided.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<UserDto> create(UserCreationDto dto);

  @Operation(
      summary = "Login using email and password",
      description = "Login into the API by getting access and refresh  jwt tokens")
  @ApiResponse(
      responseCode = "200",
      description = "User is successfully logged in.",
      content = {
        @Content(
            schema = @Schema(implementation = UserAuthResponseDto.class),
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
      description = "Invalid credentials are provided.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<UserAuthResponseDto> login(UserLoginRequestDto dto);

  @Operation(
      summary = "Refresh access and refresh tokens",
      description =
          """
            Refresh tokens by providing the refresh token user already have.
            A single refresh token can be used only once.""")
  @ApiResponse(
      responseCode = "200",
      description = "User gets a new pair of access and refresh tokens..",
      content = {
        @Content(
            schema = @Schema(implementation = UserAuthResponseDto.class),
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
      description = "Invalid or expired token is provided.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  @ApiResponse(
      responseCode = "500",
      description = "Internal server error occurred.",
      content = {
        @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")
      })
  ResponseEntity<UserAuthResponseDto> refresh(TokenRefreshRequestDto dto);
}
