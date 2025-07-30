package tw.com.ispan.repository;

import java.util.List;

import tw.com.ispan.domain.ProductBean;

public interface ProductRepositoryCustom {
	List<ProductBean> find(org.json.JSONObject obj);
}
