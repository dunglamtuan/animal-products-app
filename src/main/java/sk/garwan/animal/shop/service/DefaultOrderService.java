package sk.garwan.animal.shop.service;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.garwan.animal.shop.entity.CartItem;
import sk.garwan.animal.shop.model.Order;
import sk.garwan.animal.shop.model.OrderProductDetail;
import sk.garwan.animal.shop.model.OrderStatus;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.OrderDetailRepository;
import sk.garwan.animal.shop.repository.OrderRepository;
import sk.garwan.animal.shop.repository.ProductRepository;
import sk.garwan.animal.shop.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultOrderService implements OrderService{

  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final OrderDetailRepository orderDetailRepository;

  @Override
  public List<Order> findOrderByUserName(String username) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Principal: {}", authentication.getPrincipal().toString());
    return orderRepository.findAllByUser(userRepository.findByUsername(username));
  }

  @Transactional(rollbackFor = RuntimeException.class)
  @Override
  public boolean confirmNewOrder(List<CartItem> items, String username) {
    BigDecimal totalPrice = getTotalPrice(items);
    Timestamp created = Timestamp.from(Instant.now());
    User userByUsername = userRepository.findByUsername(username);
    log.info("new Order: totalPrice={}, created={}, user={}", totalPrice, created, userByUsername);

    Order savedOrder = orderRepository.save(
        Order.builder().createdAt(created).orderStatus(OrderStatus.RECEIVED).totalPrice(totalPrice)
            .user(userByUsername).build());

    List<OrderProductDetail> orderProductDetails = makeOrderProductDetails(items, savedOrder);
    orderDetailRepository.saveAll(orderProductDetails);

    return true;
  }

  private BigDecimal getTotalPrice(List<CartItem> items) {
    Function<CartItem, BigDecimal> productTotalPrice = cartItem -> cartItem.getProductPrice()
        .multiply(BigDecimal.valueOf(cartItem.getAmount()));
    return items.stream().map(productTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private List<OrderProductDetail> makeOrderProductDetails(List<CartItem> items, Order order) {
    return items.stream()
        .map(cartItem ->
            OrderProductDetail.builder()
                .order(order)
                .product(productRepository.findByName(cartItem.getProductName()))
                .amount(cartItem.getAmount()).actualPrice(cartItem.getProductPrice())
                .build())
        .collect(toList());
  }

}
