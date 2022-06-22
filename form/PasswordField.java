package vplibrary.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.util.Callback;
import vplibrary.util.HashLib;

public class PasswordField extends TextField{

	protected Callback<String, String> hashFunction;
	
	public PasswordField(String label, String name, Callback<String, String> hashFunction) {
		super(label, name);
		this.hashFunction = hashFunction;
	}
	
	/**
	 * 
	 * @param label
	 * @param name
	 * @param hashFunction La méthode correspondant à la function sera cherchée dans la classe utilisaire vplibrary.HashLib
	 */
	public PasswordField(String label, String name, String hashFunction) {
		super(label, name);
		this.hashFunction = val -> {
			try {
				Method method = HashLib.class.getMethod(hashFunction, String.class);
				return String.valueOf(method.invoke(null, val));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return val;
		};
	}
	
	public PasswordField(String label, String name) {
		this(label, name, val -> val);
	}
	
	public Callback<String, String> getHashFunction(){ 
        return hashFunction;
    }
	
	public String getHashedValue() {
		return this.hashFunction.call(getValue());
	}
   

}
