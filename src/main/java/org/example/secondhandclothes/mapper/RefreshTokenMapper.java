package org.example.secondhandclothes.mapper;

import org.example.secondhandclothes.dto.response.RefreshTokenDto;
import org.example.secondhandclothes.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

  public RefreshTokenDto toDto(RefreshTokenEntity entity) {
    return RefreshTokenDto.builder()
        .token(entity.getToken())
        .userId(entity.getUser().getId())
        .expiresAt(entity.getExpiresAt())
        .build();
  }
}
