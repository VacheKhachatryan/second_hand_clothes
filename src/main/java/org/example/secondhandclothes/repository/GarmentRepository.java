package org.example.secondhandclothes.repository;

import org.example.secondhandclothes.entity.GarmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GarmentRepository extends JpaRepository<GarmentEntity, UUID> {}
