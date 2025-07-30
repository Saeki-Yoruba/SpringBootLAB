package tw.com.ispan.repository;

import java.util.List;

import org.json.JSONObject;

import tw.com.ispan.domain.ProductBean;

public interface ProductCustomRepository {
	List<ProductBean> find(org.json.JSONObject obj);
	long count(JSONObject obj);
}
