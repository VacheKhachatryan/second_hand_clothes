package org.example.secondhandclothes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.secondhandclothes.dto.response.RefreshTokenDto;
import org.example.secondhandclothes.entity.RefreshTokenEntity;
import org.example.secondhandclothes.mapper.RefreshTokenMapper;
import org.example.secondhandclothes.repository.RefreshTokenRepository;
import org.example.secondhandclothes.service.RefreshTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository repository;
  private final RefreshTokenMapper mapper;

  @Override
  public RefreshTokenDto create(RefreshTokenEntity entity) {
    String email = entity.getUser().getEmail();
    log.debug("Attempt to save new refresh token for a user with email: {}", email);

    RefreshTokenEntity refreshToken = repository.save(entity);
    log.debug("Saved new refresh token for the user with email: {}", email);

    return mapper.toDto(refreshToken);
  }

  @Override
  public Optional<RefreshTokenDto> findByToken(String token) {
    return repository.findById(token).map(mapper::toDto);
  }

  @Override
  public void deleteByToken(String token) {
    repository.deleteById(token);
  }
}
