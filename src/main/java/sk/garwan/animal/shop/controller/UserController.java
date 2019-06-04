package sk.garwan.animal.shop.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import sk.garwan.animal.shop.response.JwtTokenResponse;
import sk.garwan.animal.shop.service.DefaultUserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final DefaultUserService userService;

  @ApiOperation(value = "${UserController.handleUserRegistrationRequest}")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "New user is created"),
      @ApiResponse(code = 409, message = "User is already existed")})
  @PostMapping("/reg")
  public ResponseEntity<JwtTokenResponse> handleUserRegistrationRequest(@RequestBody User user) {

    Optional<String> registeredUser = userService.registerNewUser(user);

    return registeredUser.map(token -> new ResponseEntity<>(new JwtTokenResponse(token), HttpStatus.CREATED))
        .orElseGet(() -> new ResponseEntity<>(new JwtTokenResponse(null), HttpStatus.CONFLICT));
  }

  @ApiOperation(value = "${UserController.handleLoginRequest}")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Token is created"),
      @ApiResponse(code = 403, message = "Invalid username/password")})
  @GetMapping("/login")
  @ResponseBody
  public ResponseEntity<JwtTokenResponse> handleLoginRequest(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {

    Optional<String> token = userService.logInUser(username, password);
    return token.map(t -> new ResponseEntity<>(new JwtTokenResponse(t), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(new JwtTokenResponse(null), HttpStatus.UNAUTHORIZED));
  }

}
