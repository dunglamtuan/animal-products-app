package sk.garwan.animal.shop.controller;

import java.util.List;
import java.util.Optional;
import javax.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.garwan.animal.shop.model.Order;
import sk.garwan.animal.shop.response.JwtTokenResponse;
import sk.garwan.animal.shop.service.DefaultOrderService;
import sk.garwan.animal.shop.service.DefaultUserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final DefaultUserService userService;
  private final DefaultOrderService orderService;

  @GetMapping("/login")
  public ResponseEntity<JwtTokenResponse> handleAdminLogInRequest(
      @PathParam("username") String username, @PathParam("password") String password) {

    Optional<String> token = userService.logInAdmin(username, password);
    return token.map(t -> new ResponseEntity<>(new JwtTokenResponse(t), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(new JwtTokenResponse(null), HttpStatus.UNAUTHORIZED));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/orders")
  public ResponseEntity<List<Order>> handleAdminAllOrdersRequest(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam("page") Integer page,
      @RequestParam("pSize") Integer pageSize) {

    log.info("Username {}", userDetails.getUsername());
    List<Order> orders = orderService.findOrdersWithPagination(page, pageSize);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

}