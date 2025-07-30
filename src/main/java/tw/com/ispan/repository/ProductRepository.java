package tw.com.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.ispan.domain.ProductBean;

public interface ProductRepository extends JpaRepository<ProductBean, Integer>, ProductCustomRepository{

}
