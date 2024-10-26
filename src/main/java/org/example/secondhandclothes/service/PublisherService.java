package org.example.secondhandclothes.service;

import org.example.secondhandclothes.entity.PublisherEntity;

import java.util.Optional;
import java.util.UUID;

public interface PublisherService {

  Optional<PublisherEntity> findEntityById(UUID id);
}
