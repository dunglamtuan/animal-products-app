package sk.garwan.animal.shop.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product implements Serializable {

  @Id
  @GeneratedValue(generator = "product_id_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence")
  private Integer id;

  @Column
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "animalcategory")
  @Type(type = "pgsql_enum")
  private AnimalCategory animalCategory;

  @Column
  private BigDecimal price;

  @Column
  private String description;

  @Type(type = "string-array")
  @Column(name = "gallery", columnDefinition = "text[]")
  private String[] gallery;

}
