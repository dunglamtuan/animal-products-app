package sk.garwan.animal.shop.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.garwan.animal.shop.entity.CartItem;
import sk.garwan.animal.shop.model.Order;
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

  @Override
  public List<Order> findOrderByUserName(String username) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Principal: {}", authentication.getPrincipal().toString());
    return orderRepository.findAllByUser(userRepository.findByUsername(username));
  }

  @Override
  public boolean confirmNewOrder(List<CartItem> items) {
    return false;
  }
}
