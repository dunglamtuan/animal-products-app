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
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.repository.UserRepository;
import sk.garwan.animal.shop.security.jwt.JwtTokenProvider;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private DefaultUserService userService;

  @Spy
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtTokenProvider jwtTokenProvider;

  @Test
  public void registrationTest() {
    //setup
    String testPassword = "password";
    User user = User.builder().username("username").email("email1").password(testPassword).build();
    when(userRepository.save(any())).thenReturn(user);
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    when(jwtTokenProvider.createToken(anyString(), any())).thenReturn("token");

    //execute
    Optional<String> token = userService.registerNewUser(
        User.builder().password("any").username("username").isAdmin(false).email("email").build());

    //verify
    assertThat(token).isPresent();
  }

}
