package sk.garwan.animal.shop.service.registration;


import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import sk.garwan.animal.shop.repository.UserRepository;

/**
 * This class implements default password provider
 * This provides hash for password using SHA256 algorithm
 */
@AllArgsConstructor
public class DefaultPasswordProvider implements PasswordProvider {

  private final UserRepository userRepository;

  @Override
  public boolean verifyUserPassword(String username, String password) {
    return false;
  }

  @Override
  public String hashPassword(String inputPass) {
    return Hashing.sha256().hashString(inputPass, StandardCharsets.UTF_8).toString();
  }
}
