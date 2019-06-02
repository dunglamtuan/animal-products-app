package sk.garwan.animal.shop.controller;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.service.DefaultUserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final DefaultUserService userService;

  @PostMapping("/reg")
  public ResponseEntity<String> handleUserRegistrationRequest(@RequestBody User user) {

    Optional<String> registeredUser = userService.registerNewUser(user);

    return registeredUser.map(token -> new ResponseEntity<>(token, HttpStatus.CREATED))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.CONFLICT));
  }

  @GetMapping("/auth/login")
  @ResponseBody
  public ResponseEntity<String> handleLoginRequest(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {

    Optional<String> token = userService.logInUser(username, password);
    return token.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED));
  }

}
