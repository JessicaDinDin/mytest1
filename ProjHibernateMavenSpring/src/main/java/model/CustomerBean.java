package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import model.spring.SpringJavaConfiguration;

@Entity
@Table(name="CUSTOMER")
public class CustomerBean {
	@Id
	private String custid;
	private byte[] password;
	private String email;
	private java.util.Date birth;
	public static void main(String[] args) {
		ApplicationContext context =
//				new ClassPathXmlApplicationContext("beans.config.xml");
				new AnnotationConfigApplicationContext(SpringJavaConfiguration.class);
		
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();

		Session session = sessionFactory.getCurrentSession();
		CustomerBean select = session.get(CustomerBean.class, "Alex");
		System.out.println(select);

		
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		((ConfigurableApplicationContext) context).close();
	}
	@Override
	public String toString() {
		return "CustomerBean ["+ custid+ ","+ email+ ","+ birth+ "]";
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.util.Date getBirth() {
		return birth;
	}
	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}
}
