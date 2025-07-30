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
		Optional<ProductBean> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	public boolean exists(Integer id) {

		return productRepository.findById(id).isPresent();
	}

	public List<ProductBean> select(ProductBean bean) {
		List<ProductBean> result = new ArrayList<>();

		if (bean != null && bean.getId() != null && !bean.getId().equals(0)) {
			Optional<ProductBean> opt = productRepository.findById(bean.getId());
			opt.ifPresent(result::add); // 若有資料就加入 result
		} else {
			result = productRepository.findAll(); // 查詢所有資料
		}

		return result;
	}

	public ProductBean insert(ProductBean bean) {
		if (bean != null && bean.getId() != null) {
			Optional<ProductBean> opt = productRepository.findById(bean.getId());
			if (opt.isEmpty()) {
				return productRepository.save(bean);
			}
		}
		return null;
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
		if (bean != null && bean.getId() != null) {
			Optional<ProductBean> opt = productRepository.findById(bean.getId());
			if (opt.isPresent()) {
				return productRepository.save(bean);
			}
		}
		return null;
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
		Optional<ProductBean> opt = productRepository.findById(bean.getId());
		if (opt.isPresent()) {
			productRepository.delete(opt.get());
			return true;
		}
		return false;
	}

	public boolean remove(Integer id) {
		try {
			Optional<ProductBean> opt = productRepository.findById(id);
			if (opt.isPresent()) {
				productRepository.delete(opt.get());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
