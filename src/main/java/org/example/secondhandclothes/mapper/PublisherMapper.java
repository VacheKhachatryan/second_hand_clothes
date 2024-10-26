package org.example.secondhandclothes.mapper;

import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.dto.request.PublisherCreationDto;
import org.example.secondhandclothes.dto.response.PublisherDto;
import org.example.secondhandclothes.entity.PublisherEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherMapper {

  private final AddressMapper addressMapper;

  public PublisherEntity toEntity(PublisherCreationDto dto) {
    return PublisherEntity.builder()
        .fullName(dto.getName())
        .address(addressMapper.toEntity(dto.getAddress()))
        .build();
  }

  public PublisherDto toDto(PublisherEntity entity) {
    return PublisherDto.builder()
        .id(entity.getId())
        .fullName(entity.getFullName())
        .address(addressMapper.toDto(entity.getAddress()))
        .build();
  }
}
