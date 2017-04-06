package com.test;

public class ThreadLocalMain {
	
	static ThreadLocal<String> string = new ThreadLocal<String>();

	public static void main(String[] args) {
		
		string.set("String1");
		
		System.out.println(string.get());
		
		string.set("String2");
		
		System.out.println(string.get());

	}

}
