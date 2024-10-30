package org.example.secondhandclothes.repository;

import org.example.secondhandclothes.entity.GarmentCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GarmentCategoryRepository extends JpaRepository<GarmentCategoryEntity, UUID> {

  boolean existsByNameIgnoreCase(String name);

  @Query("""
        SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END
        FROM GarmentEntity g WHERE g.category.id = :categoryId""")
  boolean existsByCategoryIdWithGarments(@Param("categoryId") UUID categoryId);
}
