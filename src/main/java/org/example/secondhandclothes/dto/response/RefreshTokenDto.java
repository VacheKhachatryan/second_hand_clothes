package org.example.secondhandclothes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class RefreshTokenDto {

  private String token;
  private UUID userId;
  private Instant expiresAt;
}
