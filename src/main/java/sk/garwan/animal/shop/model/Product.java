package sk.garwan.animal.shop.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "product")
@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "animalcategory")
  private AnimalCategory animalCategory;

  @Column
  private BigDecimal price;

  @Column
  private String description;

  @Type(type = "string-array")
  @Column(name = "gallery", columnDefinition = "text[]")
  private String[] gallery;

}
