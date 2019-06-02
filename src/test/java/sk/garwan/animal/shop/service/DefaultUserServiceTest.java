package sk.garwan.animal.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    String testPassword = "password";
    User user = User.builder().username("username").email("email1").password(testPassword).build();
    when(userRepository.save(any())).thenReturn(user);
    when(userRepository.findByUsername(anyString())).thenReturn(null);

    //execute
    Optional<String> token = userService
        .registerNewUser(User.builder().password("any").build());

    //verify
    assertThat(token).isPresent();
  }

}
