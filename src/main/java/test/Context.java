package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.gs.pp.orm.User;
import com.gs.pp.web.UserController;

public class Context {
	
	public static void main(String[] args) {
		
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-servlet.xml");
//		UserController bean = context.getBean(UserController.class);
//		User test = bean.test();
//		System.out.println(test);
		
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring-servlet.xml");
		UserController users = (UserController) context.getBean("userController");
		System.out.println(users.test());
		
		
	}
}
