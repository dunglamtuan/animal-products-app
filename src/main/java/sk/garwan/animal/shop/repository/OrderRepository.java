package sk.garwan.animal.shop.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.Order;
import sk.garwan.animal.shop.model.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>,
    PagingAndSortingRepository<Order, Integer> {

  List<Order> findAllByUser(User user);

  @Override
  Page<Order> findAll(Pageable pageable);
}
