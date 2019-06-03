package sk.garwan.animal.shop.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.garwan.animal.shop.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  Optional<User> findByUsernameAndPassword(String username, String password);

  Optional<User> findByUsernameAndIsAdmin(String username, Boolean isAdmin);

}
