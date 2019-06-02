package sk.garwan.animal.shop.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItem {

  private String productName;
  private BigDecimal productPrice;
  private Integer amount;

}
