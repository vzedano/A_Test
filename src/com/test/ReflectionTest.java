package com.test;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectionTest {
	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		
		AClassWithProperties<String> anObject = new AClassWithProperties<String>();

		anObject.getMyType();
		
		Class aClassWithProps = anObject.getClass();

				
		Field aField = aClassWithProps.getDeclaredField("maStringUnmodifiedProperty");
		
		Arrays.asList(aClassWithProps.getDeclaredFields()).forEach(field -> System.out.println(field));
		
	}
	
	static class AClassWithProperties<T> {
		T maGeneric;
		Class clazz;
		
		
		public void getMyType(){
			System.out.println(maGeneric.getClass().getName());
		}
	}
}
