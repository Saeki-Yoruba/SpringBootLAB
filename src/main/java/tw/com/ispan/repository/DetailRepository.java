package tw.com.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.ispan.domain.DetailBean;

public interface DetailRepository extends JpaRepository<DetailBean, Integer>{

}
