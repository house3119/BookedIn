package backend25.bookedin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Countries")
public class Country {
  
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id", nullable = false, updatable = false)
	private Long country_id;

  @Column(name = "name", nullable = false, unique = true)
  @NotEmpty(message = "Country name is mandatory")
  @Size(max = 100)
  private String name;

  public Country() { }

  public Country(@NotEmpty @Size(max = 100) String name) {
    this.name = name;
  }

  public Long getCountry_id() {
    return country_id;
  }

  public void setCountry_id(Long country_id) {
    this.country_id = country_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Country [country_id=" + country_id + ", name=" + name + "]";
  }

}
