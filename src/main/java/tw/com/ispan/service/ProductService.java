package tw.com.ispan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.repository.ProductRepository;
import tw.com.ispan.util.DatetimeConverter;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			long count = productRepository.count(obj);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<ProductBean> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			List<ProductBean> list = productRepository.find(obj);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProductBean findById(Integer id) {
		ProductBean result = productRepository.findById(id).get();
		return result;
	}

	public boolean exists(Integer id) {

		return productRepository.findById(id).isPresent();
	}

	public List<ProductBean> select(ProductBean bean) {
		List<ProductBean> result = null;
		if (bean != null && bean.getId() != null && !bean.getId().equals(0)) {
			ProductBean temp = productRepository.findById(bean.getId()).get();
			if (temp != null) {
				result = new ArrayList<ProductBean>();
				result.add(temp);
			}
		} else {
			return result;
		}
		return result;
	}

	public ProductBean insert(ProductBean bean) {
		ProductBean result = null;
		if (bean != null && bean.getId() != null) {
			result = productRepository.save(bean);
		}
		return result;
	}

	public ProductBean create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			String name = obj.isNull("name") ? null : obj.getString("name");
			Double price = obj.isNull("price") ? null : obj.getDouble("price");
			String make = obj.isNull("make") ? null : obj.getString("make");
			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");

			if (id != null) {
				ProductBean insert = new ProductBean();
				insert.setId(id);
				insert.setName(name);
				insert.setPrice(price);
				insert.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
				insert.setExpire(expire);

				return productRepository.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProductBean update(ProductBean bean) {
		Optional<ProductBean> opt = productRepository.findById(bean.getId());
		ProductBean updateBean = opt.get();
		if (bean != null && bean.getId() != null) {
			updateBean = productRepository.save(updateBean);
		}
		return updateBean;
	}

	public ProductBean modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			String name = obj.isNull("name") ? null : obj.getString("name");
			Double price = obj.isNull("price") ? null : obj.getDouble("price");
			String make = obj.isNull("make") ? null : obj.getString("make");
			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");

			Optional<ProductBean> optional = productRepository.findById(id);
			if (optional.isPresent()) {
				ProductBean update = optional.get();
				update.setName(name);
				update.setPrice(price);
				update.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
				update.setExpire(expire);
				return productRepository.save(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(ProductBean bean) {
		boolean result = false;
		Optional<ProductBean> deleteBean = productRepository.findById(bean.getId());
		if (deleteBean.isPresent()) {
			productRepository.delete(deleteBean.get());
			result = true;
		}
		return result;
	}

	public boolean remove(Integer id) {
		try {
			Optional<ProductBean> deleteBean = productRepository.findById(id);
			if (deleteBean.isPresent()) {
	            productRepository.delete(deleteBean.get());
	            return true;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return false;
	}
}
