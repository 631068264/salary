package test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDataSource {
	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	@Test
	public void getConn() throws Exception {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("simple");
		factory.close();
	}

}
