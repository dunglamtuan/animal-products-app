package sk.garwan.animal.shop.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.UserRepository;
import sk.garwan.animal.shop.service.registration.DefaultPasswordProvider;
import sk.garwan.animal.shop.service.registration.PasswordProvider;

@Slf4j
@Service
public class DefaultUserService implements UserService {

  private final UserRepository userRepository;
  private final PasswordProvider passwordProvider;

  @Autowired
  public DefaultUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    passwordProvider = new DefaultPasswordProvider(userRepository);
  }

  @Transactional
  @Override
  public Optional<User> registerNewUser(User user) {
    log.info("registerNewUser({})", user);

    if (isUserExisted(user.getUsername())) {
      return Optional.empty();
    } else {
      user = preparePersistedUser(user);
      return Optional.of(userRepository.save(user));
    }
  }

  @Override
  public User findUserByUserName(String username) {
    return userRepository.findByUsername(username);
  }

  private User preparePersistedUser(User user) {
    User persistedUser = new User();
    persistedUser.setEmail(user.getEmail());
    persistedUser.setUsername(user.getUsername());
    persistedUser.setPassword(passwordProvider.hashPassword(user.getPassword()));

    log.info("persisted user: {}", persistedUser);
    return persistedUser;
  }

  private boolean isUserExisted(String username) {
    return userRepository.findByUsername(username) != null;
  }
}
