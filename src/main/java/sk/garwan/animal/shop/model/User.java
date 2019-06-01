package sk.garwan.animal.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "user_account")
@AllArgsConstructor
@Data
public class User {

  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String username;

  @Column
  private String email;
}
