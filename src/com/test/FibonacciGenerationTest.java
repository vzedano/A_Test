package com.test;

import java.math.BigInteger;

public class FibonacciGenerationTest {
	
	public static final int NUMBER_OF_FIBONACCI = 10;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Calculating fibonacci number of: " + NUMBER_OF_FIBONACCI + "...");
		
		System.out.println("Final number is: " + getFibonacci(NUMBER_OF_FIBONACCI));
		
		Object[] array = new Object[]{1,54,"Hello",};
		
		System.out.println(array);
		
	}
	
	public static BigInteger getFibonacci(int quantity){
		BigInteger prevFibonacci = BigInteger.valueOf(1);
		BigInteger actualFibonacci = BigInteger.valueOf(1);
		BigInteger fibonacci = BigInteger.valueOf(1);
		
		for(int i=0; i<quantity-1; i++){
			prevFibonacci = actualFibonacci;
			actualFibonacci = fibonacci;
			
			System.out.println("Prev fib: " + prevFibonacci);
			System.out.println("Actual fib: " + actualFibonacci);
			System.out.println();

			fibonacci = calculateFibonacci(prevFibonacci, actualFibonacci);
		}
		
		return fibonacci;
	}
	
	public static BigInteger calculateFibonacci(BigInteger prevFibonacci, BigInteger actualFibonacci){
		return prevFibonacci.add(actualFibonacci);
	}
	
	
}