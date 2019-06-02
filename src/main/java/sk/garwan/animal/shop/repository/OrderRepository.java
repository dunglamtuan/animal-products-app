package sk.garwan.animal.shop.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.Order;
import sk.garwan.animal.shop.model.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

  List<Order> findAllByUser(User user);

}
