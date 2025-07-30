package tw.com.ispan.service;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.repository.ProductRepository;
import tw.com.ispan.util.DatetimeConverter;

@SpringBootTest
@Transactional
public class ProductServiceTests {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	JSONObject obj = new JSONObject()
			.put("start", 1)
			.put("rows", 3)
			.put("order", "expire")
			.put("dir", true)
			.put("name", "a");
	String json = obj.toString();

	ProductBean productBean = new ProductBean();

//	@Test
	public void testCount() {
		System.out.println("count result: " + productService.count(json));
	}

//	@Test
	public void testFind() {
		List<ProductBean> list = productService.find(json);
		System.out.println("find :" + list);
	}

//	@Test
	public void testFindById() {
		ProductBean result = productService.findById(1);
		System.out.println("findById :" + result);
	}

//	@Test
	public void testExists() {
		System.out.println("exist :" + productService.exists(100));
	}

//	@Test
	public void testSelect() {
		List<ProductBean> selects = productService.select(null);
		System.out.println("selects=" + selects);
	}

//	@Test
	public void testInsert() {
		productBean.setId(12);
		productBean.setName("hehehe");
		productBean.setPrice(12.34);
		productBean.setMake(new java.util.Date());
		productBean.setExpire(56);
		System.out.println(productService.insert(productBean));
	}

//	@Test
	public void testCreate() {
		String json = """
				    {
				        "id": 100,
				        "name": "測試商品",
				        "price": 199.99,
				        "make": "2025-07-24",
				        "expire": 60
				    }
				""";

		ProductBean result = productService.create(json);
		System.out.println("create result = " + result);
	}

//	@Test
	public void testUpdate() {
		productBean.setId(10);
		productBean.setName("hehehe");
		productBean.setPrice(12.34);
		productBean.setMake(new java.util.Date());
		productBean.setExpire(56);
		System.out.println(productService.update(productBean));
	}

//	@Test
	public void testModify() {
		String json = """
				    {
				        "id": 1,
				        "name": "修改後名稱",
				        "price": 888.88,
				        "make": "2025-07-24",
				        "expire": 99
				    }
				""";

		ProductBean result = productService.modify(json);
		System.out.println("modify result = " + result);
	}
	
//	@Test
	public void testDelete() {
		 // 先建立一筆測試資料
        ProductBean bean = new ProductBean();
        bean.setId(999);
        bean.setName("待刪除商品");
        bean.setPrice(100.0);
        bean.setMake(DatetimeConverter.parse("2025-07-24", "yyyy-MM-dd"));
        bean.setExpire(30);

        productRepository.save(bean); // 先存入

        boolean deleted = productService.delete(bean); // 測試 delete
        System.out.println("delete result = " + deleted);

        boolean exists = productRepository.findById(999).isPresent();
        System.out.println("exists after delete = " + exists); // 應該為 false
	}
	
	@Test
	public void testRemove() {
		ProductBean bean = new ProductBean();
        bean.setId(998);
        bean.setName("測試刪除");
        bean.setPrice(123.45);
        bean.setMake(DatetimeConverter.parse("2025-07-24", "yyyy-MM-dd"));
        bean.setExpire(10);
        productRepository.save(bean);

        // act：呼叫 remove 方法
        boolean removed = productService.remove(998);

        // 檢查是否真的刪除
        boolean exists = productRepository.findById(998).isPresent();

        System.out.println("remove result = " + removed);
        System.out.println("exists after remove = " + exists);
	}
}
