package org.example.secondhandclothes.repository;

import org.example.secondhandclothes.entity.GarmentCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GarmentCategoryRepository extends JpaRepository<GarmentCategoryEntity, UUID> {

  boolean existsByNameIgnoreCase(String name);
}
