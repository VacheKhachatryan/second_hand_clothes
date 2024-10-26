package org.example.secondhandclothes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.secondhandclothes.dto.request.GarmentCategoryCreationDto;
import org.example.secondhandclothes.dto.response.GarmentCategoryDto;
import org.example.secondhandclothes.entity.GarmentCategoryEntity;
import org.example.secondhandclothes.error.EntityAlreadyExistsException;
import org.example.secondhandclothes.error.EntityNotFoundException;
import org.example.secondhandclothes.mapper.GarmentCategoryMapper;
import org.example.secondhandclothes.repository.GarmentCategoryRepository;
import org.example.secondhandclothes.service.GarmentCategoryService;
import org.example.secondhandclothes.util.BooleanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.secondhandclothes.error.ApplicationError.GARMENT_CATEGORY_ALREADY_EXISTS_WITH_NAME;
import static org.example.secondhandclothes.error.ApplicationError.GARMENT_CATEGORY_NOT_FOUND;
import static org.example.secondhandclothes.error.ErrorMessage.GARMENT_CATEGORY_EXISTS_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.GARMENT_CATEGORY_NOT_FOUND_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GarmentCategoryServiceImpl implements GarmentCategoryService {

  private final GarmentCategoryRepository repository;
  private final GarmentCategoryMapper mapper;

  @Override
  public GarmentCategoryDto create(GarmentCategoryCreationDto dto) {
    String name = dto.getName();
    log.debug("Attempt to create new garment category with name: {}", name);
    BooleanUtils.throwIfTrue(repository.existsByNameIgnoreCase(name),
        () -> new EntityAlreadyExistsException(GARMENT_CATEGORY_ALREADY_EXISTS_WITH_NAME, String.format(GARMENT_CATEGORY_EXISTS_MESSAGE, name)));

    GarmentCategoryEntity entity = repository.saveAndFlush(mapper.toEntity(dto));
    log.info("Created new garment category with name: {}", name);
    return mapper.toDto(entity);
  }

  @Override
  public List<GarmentCategoryDto> findAll() {
    log.debug("Attempt to find all garment categories");
    return repository.findAll(Sort.by("name")).stream()
        .map(mapper::toDto)
        .toList();
  }

  @Override
  public Optional<GarmentCategoryEntity> findEntityById(UUID id) {
    log.debug("Attempt to find garment category with id: {}", id);
    return repository.findById(id);
  }

  @Override
  public void deleteById(UUID id) {
    log.debug("Attempt to delete a garment category with id: {}", id);
    GarmentCategoryEntity entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(GARMENT_CATEGORY_NOT_FOUND, String.format(GARMENT_CATEGORY_NOT_FOUND_MESSAGE, id)));

    repository.delete(entity);
    log.info("Deleted garment category with id: {}", id);
  }
}
