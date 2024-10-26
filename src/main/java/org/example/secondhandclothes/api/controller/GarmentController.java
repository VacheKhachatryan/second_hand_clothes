package org.example.secondhandclothes.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.api.GarmentApi;
import org.example.secondhandclothes.dto.request.GarmentCreationDto;
import org.example.secondhandclothes.dto.request.GarmentFilterDto;
import org.example.secondhandclothes.dto.request.GarmentPaginatedResponseDto;
import org.example.secondhandclothes.dto.request.GarmentUpdateDto;
import org.example.secondhandclothes.dto.response.GarmentDto;
import org.example.secondhandclothes.security.User;
import org.example.secondhandclothes.service.GarmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/garments")
@RequiredArgsConstructor
public class GarmentController implements GarmentApi {

  public final GarmentService service;

  @GetMapping
  public ResponseEntity<GarmentPaginatedResponseDto> find(@Valid GarmentFilterDto dto) {
    return ResponseEntity.ok(service.find(dto));
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<GarmentDto> create(
      @Valid @RequestBody GarmentCreationDto dto, @AuthenticationPrincipal User user) {
    return new ResponseEntity<>(service.create(dto, user), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<GarmentDto> update(
      @Valid @RequestBody GarmentUpdateDto dto, @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(service.update(dto, user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
    service.delete(id, user);
    return ResponseEntity.ok().build();
  }
}
