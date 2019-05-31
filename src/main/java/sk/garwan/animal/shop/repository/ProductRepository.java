package sk.garwan.animal.shop.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

  Optional<Product> findById(Integer integer);
}
