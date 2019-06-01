package sk.garwan.animal.shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  User findByUsername(String username);

}
