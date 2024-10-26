package org.example.secondhandclothes.repository;

import org.example.secondhandclothes.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  boolean existsByEmailIgnoreCase(String email);

  Optional<UserEntity> findByEmailIgnoreCase(String email);
}
