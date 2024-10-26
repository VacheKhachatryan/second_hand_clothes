package org.example.secondhandclothes.service;

import org.example.secondhandclothes.dto.request.GarmentCategoryCreationDto;
import org.example.secondhandclothes.dto.response.GarmentCategoryDto;
import org.example.secondhandclothes.entity.GarmentCategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GarmentCategoryService {

  GarmentCategoryDto create(GarmentCategoryCreationDto dto);

  List<GarmentCategoryDto> findAll();

  Optional<GarmentCategoryEntity> findEntityById(UUID id);

  void deleteById(UUID id);
}
