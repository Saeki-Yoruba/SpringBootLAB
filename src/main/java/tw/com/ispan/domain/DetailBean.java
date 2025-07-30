package tw.com.ispan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "detail")
public class DetailBean {
	@Id
    @Column(name = "photoid")
    private Integer photoid;

    @Column(name = "photo")
    private byte[] photo;

    @OneToOne
    @JoinColumn(
        name = "photoid",
        referencedColumnName = "id"
    )
    private ProductBean product;
}
