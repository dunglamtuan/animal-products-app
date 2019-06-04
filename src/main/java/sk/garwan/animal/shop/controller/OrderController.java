package sk.garwan.animal.shop.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

  @ApiOperation(value = "${OrderController.handleOrdersRequestByUser}")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Unauthorized"),
      @ApiResponse(code = 200, message = "List of orders by user")})
  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping("/auth")
  public ResponseEntity<List<Order>> handleOrdersRequestByUser(
      @PathParam("username") String username) {
    log.debug("handleOrdersRequestByUser -> {}", username);

    return new ResponseEntity<>(orderService.findOrderByUserName(username), HttpStatus.OK);
  }

  @ApiOperation(value = "${OrderController.handleNewOrderRequestByUser}")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Unauthorized"),
      @ApiResponse(code = 201, message = "New order is created"),
      @ApiResponse(code = 409, message = "Username in params is not as in token"),
      @ApiResponse(code = 500, message = "Error when creating a new order")})
  @PreAuthorize("hasRole('ROLE_USER')")
  @PostMapping("/auth")
  public ResponseEntity<String> handleNewOrderRequestByUser(
      @RequestBody List<CartItem> items,
      @PathParam("username") String username) {

    try {
      boolean isCreated = orderService.confirmNewOrder(items, username);
      if (isCreated) {
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
      }
      return new ResponseEntity<>("Order not created", HttpStatus.CONFLICT);
    } catch (RuntimeException ex) {
      log.error("Error when creating a new order ", ex);
      return new ResponseEntity<>("Order can not be created", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
