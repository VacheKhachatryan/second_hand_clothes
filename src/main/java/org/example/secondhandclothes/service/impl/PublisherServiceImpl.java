package org.example.secondhandclothes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.secondhandclothes.entity.PublisherEntity;
import org.example.secondhandclothes.repository.PublisherRepository;
import org.example.secondhandclothes.service.PublisherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

  private final PublisherRepository repository;

  @Override
  public Optional<PublisherEntity> findEntityById(UUID id) {
    log.debug("Attempt to find a publisher by id: {}", id);
    return repository.findById(id);
  }
}
