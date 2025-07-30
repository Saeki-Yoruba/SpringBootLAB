package tw.com.ispan.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.util.DatetimeConverter;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ProductBean> find(JSONObject obj) {
		Integer id = obj.isNull("id") ? null : obj.getInt("id");
		String name = obj.isNull("name") ? null : obj.getString("name");
		Double minPrice = obj.isNull("minPrice") ? null : obj.getDouble("minPrice");
		Double maxPrice = obj.isNull("maxPrice") ? null : obj.getDouble("maxPrice");
		String minMake = obj.isNull("minMake") ? null : obj.getString("minMake");
		String maxMake = obj.isNull("maxMake") ? null : obj.getString("maxMake");
		Integer minExpire = obj.isNull("minExpire") ? null : obj.getInt("minExpire");
		Integer maxExpire = obj.isNull("maxExpire") ? null : obj.getInt("maxExpire");

		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 5 : obj.getInt("rows");
		String order = obj.isNull("order") ? "id" : obj.getString("order");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductBean> criteriaQuery = criteriaBuilder.createQuery(ProductBean.class);
		// FROM product
		Root<ProductBean> table = criteriaQuery.from(ProductBean.class);
		// where 條件
		List<Predicate> predicates = new ArrayList<>();
		if (id != null) {
			predicates.add(criteriaBuilder.equal(table.get("id"), id));
		}
		if (name != null && name.length() != 0) {
			predicates.add(criteriaBuilder.like(table.get("name"), "%" + name + "%"));
		}
		if (minPrice != null) {
			predicates.add(criteriaBuilder.greaterThan(table.get("price"), minPrice));
		}
		if (maxPrice != null) {
			predicates.add(criteriaBuilder.lessThan(table.get("price"), maxPrice));
		}
		if (minMake != null && minMake.length() != 0) {
			Date make = DatetimeConverter.parse(minMake, "yyyy=MM=dd");
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("make"), make));
		}
		if (maxMake != null && maxMake.length() != 0) {
			Date make = DatetimeConverter.parse(maxMake, "yyyy=MM=dd");
			predicates.add(criteriaBuilder.lessThanOrEqualTo(table.get("make"), make));
		}
		if (minExpire != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("expire"), minExpire));
		}
		if (maxExpire != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(table.get("expire"), maxExpire));
		}

		if (predicates != null && !predicates.isEmpty()) {
			criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}

		// order by 條件

		if (dir) {
			criteriaQuery.orderBy(criteriaBuilder.desc(table.get(order)));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.asc(table.get(order)));
		}

		TypedQuery<ProductBean> typedQuery = entityManager.createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(rows);
		List<ProductBean> result = typedQuery.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		}

		return null;
	}
	
	@Override
	public long count(JSONObject obj) {
		List<ProductBean> list = this.find(obj);
		long result = list.size();
		return result;
	}
}
