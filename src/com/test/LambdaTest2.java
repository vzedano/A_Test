package com.test;

public class LambdaTest2 {

	public static void main(String[] args) {
		new LambdaTest2();
	}

	public LambdaTest2() {
		sayHi(true); 		// Hello!
		sayHi(false); 		// Hi.
		sayThanks(true);	// Thank you!
		sayThanks(false);	// Thanks.
	}

	public void sayHi(boolean gentle) {
		beGentle(gentle, Hello.NONE, System.out::println);
	}

	public void sayThanks(boolean gentle) {
		beGentle(gentle, Thanks.NONE, System.out::println);
	}

	public void beGentle(boolean gentle, GentlePhrase gentlePhrase, SaySomethingGentle somethingGentle) {
		if(gentle){
			somethingGentle.gentleOrNotGentleAction(gentlePhrase.getGentle());
		} else {
			somethingGentle.gentleOrNotGentleAction(gentlePhrase.getNoGentle());
		}
	}

	@FunctionalInterface
	public interface SaySomethingGentle {
		public void gentleOrNotGentleAction(Object gentlePhrase);
	}
	
	
	/* We have to implement an interface in order to use the Generic Lambda for the phrases. */
	public interface GentlePhrase {
		public GentlePhrase getGentle();
		public GentlePhrase getNoGentle();
	}

	public enum Thanks implements GentlePhrase {
		GENTLE("Thank you!"), NO_GENTLE("Thanks."), NONE("");
		
		private String name;
		
		Thanks(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}

		public String toString(){
			return getName();
		}

		@Override
		public GentlePhrase getGentle() {
			return GENTLE;
		}

		@Override
		public GentlePhrase getNoGentle() {
			return NO_GENTLE;
		}
	}
	
	public enum Hello implements GentlePhrase {
		GENTLE("Hello!"), NO_GENTLE("Hi."), NONE("");
		
		private String name;
		
		Hello(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}

		public String toString(){
			return getName();
		}
		
		@Override
		public GentlePhrase getGentle() {
			return GENTLE;
		}

		@Override
		public GentlePhrase getNoGentle() {
			return NO_GENTLE;
		}
	}
}