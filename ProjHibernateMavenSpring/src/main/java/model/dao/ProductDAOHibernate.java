package model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import model.ProductBean;
import model.ProductDAO;

@Repository
public class ProductDAOHibernate implements ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public static void main(String[] args) {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("beans.config.xml");
		
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();

		ProductDAO productDao = (ProductDAO) context.getBean("productDAOHibernate");

//select
		ProductBean select = productDao.select(10);
		System.out.println("select="+select);
		
//update	
		ProductBean update = productDao.update(
				"HotDog XX", 123.456, new java.util.Date(0), 200, 10);
		System.out.println("update="+update);

//select			
		List<ProductBean> selects = productDao.select();
		System.out.println("selects="+selects);

//insert
//		ProductBean bean = new ProductBean();
//		bean.setId(100);
//		bean.setName("xxxxx");
//		bean.setPrice(123.456);
//		bean.setMake(new java.util.Date());
//		bean.setExpire(1000);
//		productDao.insert(bean);
		
//delete			
		boolean delete = productDao.delete(100);
		System.out.println("delete="+delete);
		
		sessionFactory.getCurrentSession().getTransaction().commit();
		((ConfigurableApplicationContext) context).close();
	}
	@Override
	public ProductBean select(int id) {
		return this.getSession().get(ProductBean.class, id);
	}
	@Override
	public List<ProductBean> select() {
		Query<ProductBean> query = this.getSession().createQuery("FROM ProductBean", ProductBean.class);
		return query.getResultList();
	}
	@Override
	public ProductBean insert(ProductBean bean) {
		if(bean!=null) {
			ProductBean select = this.select(bean.getId());
			if(select==null) {
				this.getSession().save(bean);
				return bean;
			}
		}
		return null;
	}
	@Override
	public ProductBean update(String name, double price, Date make, int expire, int id) {
		ProductBean select = this.select(id);
		if(select!=null) {
			select.setName(name);
			select.setPrice(price);
			select.setMake(make);
			select.setExpire(expire);
		}
		return null;
	}
	@Override
	public boolean delete(int id) {
		ProductBean select = this.select(id);
		if(select!=null) {
			this.getSession().delete(select);
			return true;
		}
		return false;
	}
}
