package org.example.secondhandclothes.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.secondhandclothes.dto.request.GarmentCreationDto;
import org.example.secondhandclothes.dto.request.GarmentFilterDto;
import org.example.secondhandclothes.dto.request.GarmentPaginatedResponseDto;
import org.example.secondhandclothes.dto.request.GarmentUpdateDto;
import org.example.secondhandclothes.dto.request.SortOrder;
import org.example.secondhandclothes.dto.response.GarmentDto;
import org.example.secondhandclothes.entity.GarmentCategoryEntity;
import org.example.secondhandclothes.entity.GarmentEntity;
import org.example.secondhandclothes.entity.PublisherEntity;
import org.example.secondhandclothes.error.AuthorizationException;
import org.example.secondhandclothes.error.EntityNotFoundException;
import org.example.secondhandclothes.mapper.GarmentMapper;
import org.example.secondhandclothes.repository.GarmentRepository;
import org.example.secondhandclothes.security.User;
import org.example.secondhandclothes.service.GarmentCategoryService;
import org.example.secondhandclothes.service.GarmentService;
import org.example.secondhandclothes.service.PublisherService;
import org.example.secondhandclothes.util.BooleanUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.example.secondhandclothes.entity.Role.ADMIN;
import static org.example.secondhandclothes.error.ApplicationError.*;
import static org.example.secondhandclothes.error.ErrorMessage.ACCESS_DENIED_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.GARMENT_CATEGORY_NOT_FOUND_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.GARMENT_NOT_FOUND_MESSAGE;
import static org.example.secondhandclothes.error.ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GarmentServiceImpl implements GarmentService {

  public static final String LIKE_PATTERN = "%%%s%%";

  private final GarmentRepository repository;
  private final GarmentMapper mapper;
  private final GarmentCategoryService categoryService;
  private final PublisherService publisherService;

  @PersistenceContext private EntityManager entityManager;

  @Override
  public GarmentPaginatedResponseDto find(GarmentFilterDto dto) {
    log.debug("Finding garments with the filtering..");
    List<GarmentDto> garments = findResults(dto);
    Long count = findCount(dto);
    int limit = dto.getLimit();

    return new GarmentPaginatedResponseDto(
        (dto.getOffset() + limit) / limit, Math.ceilDiv(count, limit), garments);
  }

  @Override
  public GarmentDto create(final GarmentCreationDto dto, final User user) {
    final UUID categoryId = dto.getCategoryId();
    final UUID publisherId = user.getPublisherId();
    final String userEmail = user.getEmail();
    log.debug("Attempt to create new garment by the user with email: {}", userEmail);
    GarmentCategoryEntity garmentCategoryEntity = categoryService
        .findEntityById(categoryId)
        .orElseThrow(() -> new EntityNotFoundException(GARMENT_CATEGORY_NOT_FOUND,
            String.format(GARMENT_CATEGORY_NOT_FOUND_MESSAGE, categoryId)));

    GarmentEntity entity = mapper.toEntity(dto)
        .withCategory(garmentCategoryEntity)
        .withPublisher(publisherService.findEntityById(publisherId)
            .orElseThrow(() -> new EntityNotFoundException(INTERNAL_ERROR,
                String.format(PUBLISHER_NOT_FOUND_MESSAGE, publisherId))));
    GarmentEntity savedEntity = repository.saveAndFlush(entity);
    log.info("Created new garment with id: {} by the user with email: {}",
        savedEntity.getId(), userEmail);

    return mapper.toDto(savedEntity);
  }

  @Override
  public GarmentDto update(final GarmentUpdateDto dto, final User user) {
    final String userEmail = user.getEmail();
    final UUID garmentId = dto.getId();
    final UUID newCategory = dto.getCategoryId();
    log.debug("Attempt to update the garment with id: {} by the user with email: {}",
        garmentId, userEmail);
    GarmentEntity garment = repository.findById(garmentId)
        .orElseThrow(() -> new EntityNotFoundException(ADMIN.equals(user.getRole()) ? GARMENT_NOT_FOUND : ACCESS_DENIED,
                String.format(GARMENT_NOT_FOUND_MESSAGE, garmentId)));
    checkIfUserIsAdminOrThePublisher(user, garment);

    GarmentCategoryEntity category = categoryService.findEntityById(Optional.ofNullable(newCategory).orElse(garment.getCategory().getId()))
        .orElseThrow(() -> new EntityNotFoundException(GARMENT_CATEGORY_NOT_FOUND,
            String.format(GARMENT_CATEGORY_NOT_FOUND_MESSAGE, newCategory)));
    GarmentEntity updatedGarment = repository.saveAndFlush(mapper.toUpdatedEntity(garment, dto)
        .withCategory(category));

    log.info("Updated the garment with id: {} by the user with email: {}", garmentId, userEmail);
    return mapper.toDto(updatedGarment);
  }

  @Override
  public void delete(UUID id, User user) {
    final String userEmail = user.getEmail();
    log.debug("Attempt to delete a garment with id: {} by the user with email: {}", id, userEmail);
    GarmentEntity garment = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ADMIN.equals(user.getRole()) ? GARMENT_NOT_FOUND : ACCESS_DENIED,
            String.format(GARMENT_NOT_FOUND_MESSAGE, id)));
    checkIfUserIsAdminOrThePublisher(user, garment);
    repository.delete(garment);
    log.info("The garment with id: {} has been deleted by the user: {}", id, userEmail);
  }

  private void checkIfUserIsAdminOrThePublisher(User user, GarmentEntity garment) {
    BooleanUtils.throwIfFalse(Objects.equals(user.getPublisherId(), Optional.ofNullable(garment.getPublisher())
                    .map(PublisherEntity::getId)
                    .orElse(null))
            || ADMIN.equals(user.getRole()),
        () -> new AuthorizationException(ACCESS_DENIED, String.format(ACCESS_DENIED_MESSAGE, user.getEmail())));
  }

  private Long findCount(GarmentFilterDto dto) {
    CriteriaBuilder countCriteriaBuilder = entityManager.unwrap(Session.class).getCriteriaBuilder();
    CriteriaQuery<Long> countQuery = countCriteriaBuilder.createQuery(Long.class);
    Root<GarmentEntity> counRoot = countQuery.from(GarmentEntity.class);
    countQuery.select(countCriteriaBuilder.count(counRoot));
    fillWhereClaueses(dto, countCriteriaBuilder, counRoot, countQuery);

    return entityManager.createQuery(countQuery).getSingleResult();
  }

  private List<GarmentDto> findResults(GarmentFilterDto dto) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GarmentEntity> criteriaQuery = criteriaBuilder.createQuery(GarmentEntity.class);
    Root<GarmentEntity> root = criteriaQuery.from(GarmentEntity.class);
    CriteriaQuery<GarmentEntity> selectQuery = criteriaQuery.select(root);
    fillWhereClaueses(dto, criteriaBuilder, root, selectQuery);

    Path<Object> objectPath = root.get(dto.getSortBy().getFieldName());
    selectQuery.orderBy(SortOrder.ASC.equals(dto.getSortOrder()) ?
        criteriaBuilder.asc(objectPath) : criteriaBuilder.desc(objectPath));

    return entityManager
        .createQuery(criteriaQuery)
        .setFirstResult(dto.getOffset())
        .setMaxResults(dto.getLimit())
        .getResultList()
        .stream()
        .map(mapper::toDto)
        .toList();
  }

  private void fillWhereClaueses(
      GarmentFilterDto dto,
      CriteriaBuilder criteriaBuilder,
      Root<GarmentEntity> root,
      CriteriaQuery<?> query) {
    Optional.ofNullable(dto.getName()).ifPresent(
            name -> query.where(criteriaBuilder.like(root.get("name"), String.format(LIKE_PATTERN, name))));
    Optional.ofNullable(dto.getDescription())
        .ifPresent(description ->
            query.where(criteriaBuilder.like(root.get("description"), String.format(LIKE_PATTERN, description))));
    Optional.ofNullable(dto.getPriceGte())
        .ifPresent(price -> query.where(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price)));
    Optional.ofNullable(dto.getPriceLte())
        .ifPresent(price -> query.where(criteriaBuilder.lessThanOrEqualTo(root.get("price"), price)));
    Optional.ofNullable(dto.getSize())
        .ifPresent(size ->
            query.where(criteriaBuilder.equal(criteriaBuilder.upper(root.get("size")), size.toUpperCase())));
    Optional.ofNullable(dto.getIsAvailable())
        .ifPresent(available -> query.where(criteriaBuilder.equal(root.get("isAvailable"), available)));
    Optional.ofNullable(dto.getPublisherName())
        .ifPresent(name ->
            query.where(criteriaBuilder.like(root.get("publisher").get("fullName"), String.format(LIKE_PATTERN, name))));
    Optional.ofNullable(dto.getCategoryName())
        .ifPresent(name ->
            query.where(criteriaBuilder.like(criteriaBuilder.upper(root.get("category").get("name")), name.toUpperCase())));
    Optional.ofNullable(dto.getUpdatedAtLte())
        .ifPresent(date ->
            query.where(criteriaBuilder.lessThanOrEqualTo(root.get("updatedAt"), date)));
    Optional.ofNullable(dto.getUpdatedAtGte())
        .ifPresent(date -> query.where(criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), date)));
  }
}
