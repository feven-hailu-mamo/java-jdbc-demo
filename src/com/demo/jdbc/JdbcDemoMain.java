package com.demo.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.jdbc.dao.JdbcDaoImpl;
import com.demo.jdbc.model.Circle;

public class JdbcDemoMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		JdbcDaoImpl dao = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
//		Circle circle = dao.getCircle(1);
//		System.out.println(circle.getName());
		
//		Circle circle = dao.getCircleSpring(1);
//		System.out.println(circle.getName());
		
//		System.out.println("The name of circle: "+dao.getCircleName(1));
//		
//		System.out.println("Circle: "+dao.getCircleForId(1).getName());
		
		dao.insertCirclenNamedParam(new Circle(3, "third circle"));
		System.out.println("The number of circle: "+dao.getCircleCount());
		

	}

}
