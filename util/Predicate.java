package vplibrary.util;


public class Predicate<T> implements java.util.function.Predicate<T>{
	private String message = "";
	private Callback<T, Boolean> testCallback;
	
	/**
	 * 
	 * @param testCallback
	 * @param message Message en cas de test non valide
	 */
	public Predicate(Callback<T, Boolean> testCallback, String message) {
		this.testCallback = testCallback;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean test(T value) {
		return this.testCallback.call(value);
	}
}
