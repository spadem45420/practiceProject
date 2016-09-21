package com.design.singleton.test;

public class SingletonTest {
	
	private static SingletonTest singletonTest;

	private SingletonTest() {
		System.out.println("construct start");
	}
	
	public static SingletonTest getInstance() {
		if(singletonTest == null){
			singletonTest = new SingletonTest();
		}
		return singletonTest;
	}
}
