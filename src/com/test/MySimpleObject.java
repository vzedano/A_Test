package com.test;

	public class MySimpleObject extends MySuperSimpleObject {

		public String name;
		public int intVal;

		public MySimpleObject(String name, int intVal) {
			super();
			this.name = name;
			this.intVal = intVal;
		}

		@Override
		public String toString() {

			return "[Name: " + name + ", Value: " + intVal + "]";

		}
	}