package sk.garwan.animal.shop.service;

import java.util.List;
import sk.garwan.animal.shop.entity.CartItem;
import sk.garwan.animal.shop.model.Order;

public interface OrderService {

  List<Order> findOrderByUserName(String username);
  boolean confirmNewOrder(List<CartItem> items);

}
