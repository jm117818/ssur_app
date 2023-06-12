package pl.urz.ssur_app.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JwtClaims {
  USERNAME("username");

  @Getter
  private final String claimName;
}
