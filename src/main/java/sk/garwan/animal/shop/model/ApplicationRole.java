package sk.garwan.animal.shop.model;

import org.springframework.security.core.GrantedAuthority;

public enum ApplicationRole implements GrantedAuthority {

  ROLE_USER, ROLE_ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
