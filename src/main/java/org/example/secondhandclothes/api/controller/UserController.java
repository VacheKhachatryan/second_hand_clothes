package org.example.secondhandclothes.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.api.UserApi;
import org.example.secondhandclothes.dto.request.TokenRefreshRequestDto;
import org.example.secondhandclothes.dto.request.UserCreationDto;
import org.example.secondhandclothes.dto.request.UserLoginRequestDto;
import org.example.secondhandclothes.dto.response.UserAuthResponseDto;
import org.example.secondhandclothes.dto.response.UserDto;
import org.example.secondhandclothes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController implements UserApi {

  private final UserService service;

  @PostMapping
  public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreationDto dto) {
    return new ResponseEntity<>(service.create(dto), CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<UserAuthResponseDto> login(@Valid @RequestBody UserLoginRequestDto dto) {
    return ResponseEntity.ok(service.login(dto));
  }

  @PostMapping("/refresh")
  public ResponseEntity<UserAuthResponseDto> refresh(
      @Valid @RequestBody TokenRefreshRequestDto dto) {
    return ResponseEntity.ok(service.refresh(dto));
  }
}
