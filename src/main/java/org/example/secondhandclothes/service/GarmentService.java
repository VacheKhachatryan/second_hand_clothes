package org.example.secondhandclothes.service;

import org.example.secondhandclothes.dto.request.GarmentCreationDto;
import org.example.secondhandclothes.dto.request.GarmentFilterDto;
import org.example.secondhandclothes.dto.request.GarmentPaginatedResponseDto;
import org.example.secondhandclothes.dto.request.GarmentUpdateDto;
import org.example.secondhandclothes.dto.response.GarmentDto;
import org.example.secondhandclothes.security.User;

import java.util.UUID;

public interface GarmentService {

  GarmentPaginatedResponseDto find(GarmentFilterDto dto);

  GarmentDto create(GarmentCreationDto dto, User user);

  GarmentDto update(GarmentUpdateDto dto, User user);

  void delete(UUID id, User user);
}
