package org.example.secondhandclothes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refreshTokens")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class RefreshTokenEntity {

    @Id
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Instant expiresAt;
}
