package org.example.secondhandclothes.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.api.GarmentCategoryApi;
import org.example.secondhandclothes.dto.request.GarmentCategoryCreationDto;
import org.example.secondhandclothes.dto.response.GarmentCategoryDto;
import org.example.secondhandclothes.service.GarmentCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/garment-categories")
@RequiredArgsConstructor
public class GarmentCategoryController implements GarmentCategoryApi {

  private final GarmentCategoryService service;

  @GetMapping
  public ResponseEntity<List<GarmentCategoryDto>> getAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<GarmentCategoryDto> create(
      @Valid @RequestBody GarmentCategoryCreationDto dto) {
    return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
