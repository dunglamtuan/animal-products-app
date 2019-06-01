package sk.garwan.animal.shop.service;

import java.util.Optional;
import sk.garwan.animal.shop.exception.UserAlreadyExistsException;
import sk.garwan.animal.shop.model.User;

public interface UserService {

  Optional<User> registerNewUser(User user) throws UserAlreadyExistsException;
  User findUserByUserName(String username);

}
