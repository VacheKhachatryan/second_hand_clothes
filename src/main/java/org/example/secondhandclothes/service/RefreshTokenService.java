package org.example.secondhandclothes.service;

import org.example.secondhandclothes.dto.response.RefreshTokenDto;
import org.example.secondhandclothes.entity.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenService {

  RefreshTokenDto create(RefreshTokenEntity entity);

  Optional<RefreshTokenDto> findByToken(String token);

  void deleteByToken(String token);
}
