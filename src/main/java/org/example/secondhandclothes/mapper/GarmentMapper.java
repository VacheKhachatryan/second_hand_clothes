package org.example.secondhandclothes.mapper;

import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.dto.request.GarmentCreationDto;
import org.example.secondhandclothes.dto.request.GarmentUpdateDto;
import org.example.secondhandclothes.dto.response.GarmentDto;
import org.example.secondhandclothes.entity.GarmentEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Component
@RequiredArgsConstructor
public class GarmentMapper {

  private final PublisherMapper publisherMapper;
  private final GarmentCategoryMapper garmentCategoryMapper;

  public GarmentEntity toEntity(GarmentCreationDto dto) {
    return new GarmentEntity()
        .withName(dto.getName())
        .withDescription(dto.getDescription())
        .withPrice(dto.getPrice())
        .withAvailable(TRUE)
        .withSize(dto.getSize());
  }

  public GarmentEntity toUpdatedEntity(GarmentEntity entity, GarmentUpdateDto dto) {
    Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
    Optional.ofNullable(dto.getDescription()).ifPresent(entity::setDescription);
    Optional.ofNullable(dto.getPrice()).ifPresent(entity::setPrice);
    Optional.ofNullable(dto.getSize()).ifPresent(entity::setSize);
    Optional.ofNullable(dto.getIsAvailable()).ifPresent(entity::setAvailable);
    return entity;
  }

  public GarmentDto toDto(GarmentEntity entity) {
    return GarmentDto.builder()
        .id(entity.getId())
        .category(garmentCategoryMapper.toDto(entity.getCategory()))
        .name(entity.getName())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .size(entity.getSize())
        .isAvailable(entity.isAvailable())
        .publisher(publisherMapper.toDto(entity.getPublisher()))
        .build();
  }
}
