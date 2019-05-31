package sk.garwan.animal.shop.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "product")
@AllArgsConstructor
@Data
public class Product {

  @Id
  private Integer id;

  private String name;
  private AnimalCategory animalCategory;
  private BigDecimal price;
  private String description;
  private String[] gallery;

}
