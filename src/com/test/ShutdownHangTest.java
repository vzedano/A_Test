package com.test;

import java.io.IOException;
import java.net.UnknownHostException;

public class ShutdownHangTest {

	private static final Object lock = new Object();
	private static final int ITERATION_COUNT = 30;
	
	public static void main(String[] args) throws ClassNotFoundException, UnknownHostException, IOException {
		
		
		/*String lalala = "/lang/common.xml";
				
		String pathWithoutExtension = lalala.substring(0, lalala.indexOf('.'));
		String ext = lalala.substring((lalala.indexOf('.')+1));
		
		System.out.println(pathWithoutExtension);

		System.out.println(ext);
		
		System.out.println(pathWithoutExtension+"."+ext);*/
		//System.out.println(String.format("/%s/%s/", "1", "2"));	
		
/*		System.out.println("Im alive!");
		
		Runnable undestroyable = () -> {
			int c = 0;
			while(c++ < ITERATION_COUNT){
				System.out.println("I'm a undestroyable thread!@@!@!");
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread(undestroyable));
		*/
		
		Runnable locked = () -> {
			System.out.println("Starting the shutdown locked hook.");
			synchronized(lock) {
				System.out.println("In the lock");
			}
		};
		Runtime.getRuntime().addShutdownHook(new Thread(locked));
				
		
		
		
		MyUndestroyableClass clazz = new MyUndestroyableClass();
		
		//Runtime.runFinalizersOnExit(true);
		
		System.out.println("I'm about to die...");
		
		synchronized(lock){
			System.exit(0);
		}
	}
	
	private static class MyUndestroyableClass {
		
		@Override
		protected void finalize(){
			int c = 0;
			while(c++ < ITERATION_COUNT){
				System.out.println("I'm a undestroyable class!@@!@!");
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
			}
		}
		
	}
	
}
