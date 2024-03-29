package sk.garwan.animal.shop.security.jwt;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.garwan.animal.shop.model.ApplicationRole;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.UserRepository;

@AllArgsConstructor
@Slf4j
@Service
public class DefaultUserDetail implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> userByUserName = userRepository.findByUsername(s);

    if (!userByUserName.isPresent()) {
      log.warn("User not found for username {}", s);
      throw new UsernameNotFoundException("No user found for username " + s);
    }
    ApplicationRole[] roles = userByUserName.get().getIsAdmin() ?
        new ApplicationRole[] {ApplicationRole.ROLE_USER, ApplicationRole.ROLE_ADMIN} :
        new ApplicationRole[] {ApplicationRole.ROLE_USER};

    return org.springframework.security.core.userdetails.User
        .withUsername(userByUserName.get().getUsername())
        .password(userByUserName.get().getPassword())
        .authorities(roles)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
