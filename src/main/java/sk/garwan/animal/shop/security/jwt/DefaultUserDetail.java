package sk.garwan.animal.shop.security.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.UserRepository;

@AllArgsConstructor
@Slf4j
@Service
public class DefaultUserDetail implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User userByUserName = userRepository.findByUsername(s);

    if (userByUserName == null) {
      log.warn("User not found for username {}", s);
      throw new UsernameNotFoundException("No user found for username " + s);
    }

    return org.springframework.security.core.userdetails.User
        .withUsername(userByUserName.getUsername())
        .password(userByUserName.getPassword())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
