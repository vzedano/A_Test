package com.test;

import java.util.Arrays;

public class LambdaTest {

	public static void main(String[] args) {
		new LambdaTest();
	}

	public LambdaTest() {
		
		AnAction action = (object) -> System.out.println(object);
		
		Arrays.asList(new MySimpleObject[] {new MySimpleObject("name1", 1), new MySimpleObject("name2",2)})
				.stream()
				.forEach(action::act);
		
		
		
	}
	
	@FunctionalInterface
	public interface AnAction {
		public void act(Object o);
	}
	
}