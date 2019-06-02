package sk.garwan.animal.shop.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import sk.garwan.animal.shop.security.jwt.JwtTokenFilterConfigurer;
import sk.garwan.animal.shop.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class EndpointSecurityConfiguration extends WebSecurityConfigurerAdapter {

  //private final DefaultUserService userService;
  //private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider provider;

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    //No use of session
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers("**/users/auth/login").authenticated()
        .anyRequest().permitAll();

    http.apply(new JwtTokenFilterConfigurer(provider));
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.parentAuthenticationManager(authenticationManagerBean())
//        .userDetailsService(userService);
//  }

}
