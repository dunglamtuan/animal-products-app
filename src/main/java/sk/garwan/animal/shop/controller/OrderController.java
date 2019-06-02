package sk.garwan.animal.shop.controller;

import java.util.List;
import javax.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.garwan.animal.shop.entity.CartItem;
import sk.garwan.animal.shop.model.Order;
import sk.garwan.animal.shop.service.DefaultOrderService;

@RestController
@RequestMapping("/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

  private final DefaultOrderService orderService;

  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping("/auth")
  public ResponseEntity<List<Order>> handleOrdersRequestByUser(
      @PathParam("username") String username) {
    log.info("handleOrdersRequestByUser -> {}", username);

    return new ResponseEntity<>(orderService.findOrderByUserName(username), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @PostMapping("/auth")
  public ResponseEntity<String> handleNewOrderRequestByUser(
      @RequestBody List<CartItem> items,
      @PathParam("username") String username) {

    try {
      orderService.confirmNewOrder(items, username);
      return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    } catch (RuntimeException ex) {
      log.error("Error when creating a new order ", ex);
      return new ResponseEntity<>("Order created", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
