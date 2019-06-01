package sk.garwan.animal.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private DefaultUserService userService;

  @Test
  public void registrationTest() {
    //setup
    String testPassword = Hashing.sha256().hashString("testPassword", StandardCharsets.UTF_8)
        .toString();
    User user = User.builder().username("username").email("email1").password(testPassword).build();
    when(userRepository.save(any())).thenReturn(user);
    when(userRepository.findByUsername(anyString())).thenReturn(null);

    //execute
    Optional<User> persistedUser = userService
        .registerNewUser(User.builder().password("any").build());

    //verify
    assertThat(persistedUser).isPresent();
    assertThat(persistedUser.get().getUsername()).isEqualToIgnoringCase("username");
    assertThat(persistedUser.get().getEmail()).isEqualToIgnoringCase("email1");
    assertThat(persistedUser.get().getPassword()).isEqualToIgnoringCase(testPassword);
  }

}
