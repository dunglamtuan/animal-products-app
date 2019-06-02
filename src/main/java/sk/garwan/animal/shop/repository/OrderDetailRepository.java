package sk.garwan.animal.shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.OrderProductDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderProductDetail, Integer> {

}
