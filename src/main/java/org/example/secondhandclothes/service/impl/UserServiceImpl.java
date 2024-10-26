package org.example.secondhandclothes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.secondhandclothes.config.properties.SecurityConfigProperties;
import org.example.secondhandclothes.dto.request.TokenRefreshRequestDto;
import org.example.secondhandclothes.dto.request.UserCreationDto;
import org.example.secondhandclothes.dto.request.UserLoginRequestDto;
import org.example.secondhandclothes.dto.response.RefreshTokenDto;
import org.example.secondhandclothes.dto.response.UserAuthResponseDto;
import org.example.secondhandclothes.dto.response.UserDto;
import org.example.secondhandclothes.entity.RefreshTokenEntity;
import org.example.secondhandclothes.entity.UserEntity;
import org.example.secondhandclothes.error.ApplicationError;
import org.example.secondhandclothes.error.AuthenticationException;
import org.example.secondhandclothes.error.EntityAlreadyExistsException;
import org.example.secondhandclothes.mapper.UserMapper;
import org.example.secondhandclothes.repository.UserRepository;
import org.example.secondhandclothes.security.AuthManager;
import org.example.secondhandclothes.service.RefreshTokenService;
import org.example.secondhandclothes.service.UserService;
import org.example.secondhandclothes.util.BooleanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.example.secondhandclothes.error.ApplicationError.INVALID_REFRESH_TOKEN;
import static org.example.secondhandclothes.error.ErrorMessage.INCORRECT_PASSWORD_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.INVALID_REFRESH_TOKEN_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.REFRESH_TOKEN_NOT_FOUND_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.USER_ALREADY_EXISTS_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.USER_NOT_FOUND_IN_AUTHENTICATION_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final UserMapper mapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthManager authManager;
  private final RefreshTokenService refreshTokenService;
  private final SecurityConfigProperties securityConfigProperties;

  @Override
  public UserDto create(UserCreationDto dto) {
    String email = dto.getEmail();

    log.debug("Attempt to create a new user with email: {}", email);
    BooleanUtils.throwIfTrue(
        repository.existsByEmailIgnoreCase(email),
        () ->
            new EntityAlreadyExistsException(
                ApplicationError.USER_ALREADY_EXISTS_WITH_EMAIL, USER_ALREADY_EXISTS_MESSAGE));

    UserEntity userEntity = repository.saveAndFlush(mapper.toEntity(dto));
    log.info("Created user with email: {}", email);

    return mapper.toDto(userEntity);
  }

  @Override
  public UserAuthResponseDto login(UserLoginRequestDto dto) {
    String email = dto.getEmail();
    log.debug("Attempt to login by the user with email: {}", email);
    UserEntity userEntity = repository.findByEmailIgnoreCase(email)
        .orElseThrow(() ->
            new AuthenticationException(ApplicationError.INVALID_USER_CREDENTIALS,
                USER_NOT_FOUND_IN_AUTHENTICATION_MESSAGE));
    BooleanUtils.throwIfFalse(passwordEncoder.matches(dto.getPassword(), userEntity.getPassword()),
        () -> new AuthenticationException(
                ApplicationError.INVALID_USER_CREDENTIALS, String.format(INCORRECT_PASSWORD_MESSAGE, dto.getPassword())));
    String refreshToken = authManager.generateRefreshToken(email);
    refreshTokenService.create(new RefreshTokenEntity(refreshToken, userEntity, Instant.now().plusMillis(securityConfigProperties.getRefreshTokenExpiration())));

    return new UserAuthResponseDto(authManager.generateAccessToken(userEntity), refreshToken);
  }

  @Override
  public UserAuthResponseDto refresh(TokenRefreshRequestDto dto) {
    String refreshToken = dto.getRefreshToken();
    RefreshTokenDto refreshTokenDto = refreshTokenService
        .findByToken(refreshToken)
        .orElseThrow(() -> new AuthenticationException(INVALID_REFRESH_TOKEN, String.format(REFRESH_TOKEN_NOT_FOUND_MESSAGE, refreshToken)));
    UserEntity userEntity = repository.findById(refreshTokenDto.getUserId()).orElseThrow();
    String email = userEntity.getEmail();
    refreshTokenService.deleteByToken(refreshToken);

    try {
      authManager.extractAllClaims(refreshToken);
    } catch (Exception e) {
      throw new AuthenticationException(INVALID_REFRESH_TOKEN, String.format(INVALID_REFRESH_TOKEN_MESSAGE, refreshToken));
    }
      String newRefreshToken = authManager.generateRefreshToken(email);
      refreshTokenService.create(new RefreshTokenEntity(newRefreshToken, userEntity,
          Instant.now().plusMillis(securityConfigProperties.getRefreshTokenExpiration())));

      return new UserAuthResponseDto(authManager.generateAccessToken(userEntity), newRefreshToken);
  }
}
