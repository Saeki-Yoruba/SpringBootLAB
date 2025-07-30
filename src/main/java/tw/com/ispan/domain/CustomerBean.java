package tw.com.ispan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "customer")
public class CustomerBean {
	@Id
	@Column(name = "custid")
	private String custid;

	@Column(name = "password")
	private byte[] password;

	@Column(name = "email")
	private String email;

	@Column(name = "birth")
	private java.util.Date birth;
}
