package org.example.secondhandclothes.mapper;

import org.example.secondhandclothes.dto.request.AddressCreationDto;
import org.example.secondhandclothes.dto.response.AddressDto;
import org.example.secondhandclothes.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

  public AddressEntity toEntity(AddressCreationDto dto) {
    return AddressEntity.builder()
        .country(dto.getCountry())
        .state(dto.getState())
        .city(dto.getCity())
        .zipCode(dto.getZipCode())
        .street(dto.getStreet())
        .address1(dto.getAddress1())
        .address2(dto.getAddress2())
        .build();
  }

  public AddressDto toDto(AddressEntity entity) {
    return AddressDto.builder()
        .id(entity.getId())
        .country(entity.getCountry().getName())
        .state(entity.getState())
        .city(entity.getCity())
        .zipCode(entity.getZipCode())
        .street(entity.getStreet())
        .address1(entity.getAddress1())
        .address2(entity.getAddress2())
        .build();
  }
}
