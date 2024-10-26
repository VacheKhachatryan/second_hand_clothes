package org.example.secondhandclothes.mapper;

import lombok.RequiredArgsConstructor;
import org.example.secondhandclothes.dto.request.UserCreationDto;
import org.example.secondhandclothes.dto.response.UserDto;
import org.example.secondhandclothes.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.example.secondhandclothes.entity.Role.USER;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;
  private final PublisherMapper publisherMapper;

  public UserEntity toEntity(UserCreationDto dto) {
    return UserEntity.builder()
        .firstname(dto.getFirstname())
        .lastname(dto.getLastname())
        .email(dto.getEmail())
        .role(USER)
        .password(passwordEncoder.encode(dto.getPassword()))
        .publisher(publisherMapper.toEntity(dto.getPublisher()))
        .build();
  }

  public UserDto toDto(UserEntity userEntity) {
    return UserDto.builder()
        .id(userEntity.getId())
        .firstname(userEntity.getFirstname())
        .lastname(userEntity.getLastname())
        .email(userEntity.getEmail())
        .role(userEntity.getRole())
        .createdAt(userEntity.getCreatedAt())
        .updatedAt(userEntity.getUpdatedAt())
        .publisher(Optional.ofNullable(userEntity.getPublisher()).map(publisherMapper::toDto)
            .orElse(null))
        .build();
  }
}
