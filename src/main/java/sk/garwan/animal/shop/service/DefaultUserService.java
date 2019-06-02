package sk.garwan.animal.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.garwan.animal.shop.model.ApplicationRole;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.UserRepository;
import sk.garwan.animal.shop.security.jwt.JwtTokenProvider;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  @Override
  public Optional<String> registerNewUser(User user) {
    log.info("registerNewUser({})", user);

    if (isUserExisted(user.getUsername())) {
      return Optional.empty();
    } else {
      user = preparePersistedUser(user);
      userRepository.save(user);
      return Optional.of(jwtTokenProvider.createToken(user.getUsername(), grantRoles(user)));
    }
  }

  @Override
  public User findUserByUserName(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<String> logInUser(String username, String password) {
    log.info("logInUser({},{})", username, password);

    User userByUsername = userRepository.findByUsername(username);
    boolean passwordMatched = passwordEncoder.matches(password, userByUsername.getPassword());

    return passwordMatched ? Optional
        .of(jwtTokenProvider.createToken(username, grantRoles(userByUsername))) : Optional.empty();
  }

  private User preparePersistedUser(User user) {
    User persistedUser = new User();
    persistedUser.setEmail(user.getEmail());
    persistedUser.setUsername(user.getUsername());
    persistedUser.setIsAdmin(user.getIsAdmin());
    persistedUser.setPassword(passwordEncoder.encode(user.getPassword()));

    log.info("persisted user: {}", persistedUser);
    return persistedUser;
  }

  private List<ApplicationRole> grantRoles(User user) {

    List<ApplicationRole> grantList = new ArrayList<>();
    grantList.add(ApplicationRole.ROLE_USER);
    if (user.getIsAdmin()) {
      grantList.add(ApplicationRole.ROLE_ADMIN);
    }
    return grantList;
  }

  private boolean isUserExisted(String username) {
    return userRepository.findByUsername(username) != null;
  }
}
