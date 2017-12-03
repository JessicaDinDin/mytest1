package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO productDao;

	public static void main(String[] args) {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("beans.config.xml");
		
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();

		ProductService service = (ProductService) context.getBean("productService");
		List<Object[]> beans = service.select(null);
		System.out.println("beans="+beans);

		sessionFactory.getCurrentSession().getTransaction().commit();
		((ConfigurableApplicationContext) context).close();
	}
	public List<Object[]> select(ProductBean bean) {
		List<Object[]> result = null;
		if(bean!=null && bean.getId()!=0) {
			ProductBean temp = productDao.select(bean.getId());
			if(temp!=null) {
				result = new ArrayList<>();
				Object[] array = new Object[5];
				array[0] = temp.getId();
				array[1] = temp.getName();
				array[2] = temp.getPrice();
				array[3] = temp.getMake();
				array[4] = temp.getExpire();
				result.add(array);
			}
		} else {
			result = new ArrayList<>();
			List<ProductBean> beans = productDao.select();
			for(ProductBean temp : beans) {
				Object[] array = new Object[5];
				array[0] = temp.getId();
				array[1] = temp.getName();
				array[2] = temp.getPrice();
				array[3] = temp.getMake();
				array[4] = temp.getExpire();
				result.add(array);
			}
		}
		return result;
	}
	public ProductBean insert(ProductBean bean) {
		ProductBean result = null;
		if(bean!=null) {
			result = productDao.insert(bean);
		}
		return result;
	}
	public ProductBean update(ProductBean bean) {
		ProductBean result = null;
		if(bean!=null) {
			result = productDao.update(bean.getName(), bean.getPrice(),
					bean.getMake(), bean.getExpire(), bean.getId());
		}
		return result;
	}
	public boolean delete(ProductBean bean) {
		boolean result = false;
		if(bean!=null) {
			result = productDao.delete(bean.getId());
		}
		return result;
	}

}
