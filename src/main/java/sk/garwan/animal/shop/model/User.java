package sk.garwan.animal.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_account")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

  @Id
  @GeneratedValue(generator = "user_id_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence")
  private Integer id;

  @Column(nullable = false)
  private String username;

  @Column
  private String email;

  @Column(nullable = false)
  private String password;
}
