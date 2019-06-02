package sk.garwan.animal.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements Serializable {

  @Id
  @GeneratedValue(generator = "order_id_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence")
  private Integer id;

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @JsonIgnoreProperties("order")
  @OneToMany(orphanRemoval = true, mappedBy = "order", fetch = FetchType.EAGER)
  private List<OrderProductDetail> orderProductDetails;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "current_order_status")
  private OrderStatus orderStatus;

}
