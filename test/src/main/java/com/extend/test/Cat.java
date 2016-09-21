package com.extend.test;

public class Cat extends Tiger {

	public void eat() {
		System.out.println("cat eat eat ...");
	}
	
	public void tiger_eat() {
		super.eat();
	}
}
