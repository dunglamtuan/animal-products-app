package sk.garwan.animal.shop.model;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MinimalProduct {

  @Id
  private Integer id;

  @Column
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "animalcategory")
  private AnimalCategory animalCategory;

  @Column
  private BigDecimal price;

}
