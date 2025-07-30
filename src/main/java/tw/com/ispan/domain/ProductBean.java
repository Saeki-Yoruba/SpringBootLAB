package tw.com.ispan.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "product")
public class ProductBean {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Double price;

	@Column(name = "make")
	private java.util.Date make;

	@Column(name = "expire")
	private Integer expire;

	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	private DetailBean detail;
}
