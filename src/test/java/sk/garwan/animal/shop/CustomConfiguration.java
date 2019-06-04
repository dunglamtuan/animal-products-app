package sk.garwan.animal.shop;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

  @Bean
  public TestRestTemplate restTemplate() {
    return new TestRestTemplate();
  }

}
