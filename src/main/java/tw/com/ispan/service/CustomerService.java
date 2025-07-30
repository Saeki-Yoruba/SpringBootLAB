package tw.com.ispan.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.CustomerBean;
import tw.com.ispan.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public CustomerBean login(String username, String password) {
		if (username != null && username.length() > 0 && password != null && password.length() > 0) {
			Optional<CustomerBean> opt = customerRepository.findById(username);
			if (opt.isPresent()) {
				CustomerBean customerBean = opt.get();
				byte[] pass = opt.get().getPassword();
				byte[] temp = password.getBytes();
				if (Arrays.equals(pass, temp)) {
					return customerBean;
				}
			}
		}
		return null;
	}

	public boolean changePassword(String username, String oldPass, String newPass) {
		// null 或 空
		if (newPass == null || newPass.length() != 0) {
			return false;
		}
		// 呼叫 login 方法
		CustomerBean bean = this.login(username, oldPass);
		if (bean != null) {
			bean.setPassword(newPass.getBytes());
			CustomerBean saved = customerRepository.save(bean);
			return true;
		}
		return false;
	}
}
