package sk.garwan.animal.shop.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class JwtTokenFilterConfigurer extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private JwtTokenProvider provider;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(new JwtTokenFilter(provider), UsernamePasswordAuthenticationFilter.class);
  }

}
