package sk.garwan.animal.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "order_product_detail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderProductDetail{

  @Id
  @GeneratedValue(generator = "order_product_detail_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "order_product_detail_sequence", sequenceName = "order_product_detail_sequence")
  private Integer id;

  @JsonIgnoreProperties("orderProductDetails")
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column
  private Integer amount;

  @Column
  private BigDecimal actualPrice;

}
