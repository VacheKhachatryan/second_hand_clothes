package org.example.secondhandclothes.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.secondhandclothes.entity.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

  private UUID id;
  private String email;
  private UUID publisherId;
  private Role role;

  public List<SimpleGrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", role.name())));
  }
}
