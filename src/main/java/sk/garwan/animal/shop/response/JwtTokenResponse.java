package sk.garwan.animal.shop.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class JwtTokenResponse {

  private String token;

}
