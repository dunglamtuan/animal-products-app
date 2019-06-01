package sk.garwan.animal.shop.controller;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.service.DefaultUserService;

@RestController
@AllArgsConstructor
public class UserController {

  private final DefaultUserService userService;

  @PostMapping("/users/reg")
  public ResponseEntity<User> handleUserRegistrationRequest(@RequestBody User user) {

    Optional<User> registeredUser = userService.registerNewUser(user);

    return registeredUser.map(rUser -> new ResponseEntity<>(rUser, HttpStatus.CREATED))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.CONFLICT));

  }

}
