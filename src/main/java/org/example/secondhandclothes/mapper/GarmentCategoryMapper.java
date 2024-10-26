package org.example.secondhandclothes.mapper;

import org.example.secondhandclothes.dto.request.GarmentCategoryCreationDto;
import org.example.secondhandclothes.dto.response.GarmentCategoryDto;
import org.example.secondhandclothes.entity.GarmentCategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class GarmentCategoryMapper {

  public GarmentCategoryEntity toEntity(GarmentCategoryCreationDto dto) {
    GarmentCategoryEntity entity = new GarmentCategoryEntity();
    entity.setName(dto.getName());

    return entity;
  }

  public GarmentCategoryDto toDto(GarmentCategoryEntity entity) {
    return new GarmentCategoryDto(entity.getId(), entity.getName());
  }
}
