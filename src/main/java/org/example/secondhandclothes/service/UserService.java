package org.example.secondhandclothes.service;

import org.example.secondhandclothes.dto.request.TokenRefreshRequestDto;
import org.example.secondhandclothes.dto.request.UserCreationDto;
import org.example.secondhandclothes.dto.request.UserLoginRequestDto;
import org.example.secondhandclothes.dto.response.UserAuthResponseDto;
import org.example.secondhandclothes.dto.response.UserDto;

public interface UserService {

  UserDto create(UserCreationDto dto);

  UserAuthResponseDto login(UserLoginRequestDto dto);

  UserAuthResponseDto refresh(TokenRefreshRequestDto dto);
}
