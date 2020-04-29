package com.demo.jdbc.model;

public class Circle {

	private int id;
	private String name;
	
	public Circle(int circleId, String name) {
		this.setId(circleId);
		this.setName(name);
	}
	public Circle() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
