package sk.garwan.animal.shop.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  User findByUsername(String username);
  Optional<User> findByUsernameAndPassword(String username, String password);

}
