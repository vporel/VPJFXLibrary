package vplibrary.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import vplibrary.util.PasswordSecure;

public class PasswordField extends TextField{

	protected String hashFunction;
	public PasswordField(String label, String name, String hashFunction) {
		super(label, name);
		this.hashFunction = hashFunction;
	}
	public PasswordField(String label, String name) {
		this(label, name, "");
		// TODO Auto-generated constructor stub
	}
	
	

   
	
	public String getHashFunction(){ 
        return hashFunction;
    }
	
	@Override
	public String getRealValue(String value) {
		// TODO Auto-generated method stub
		if(!hashFunction.isEmpty()){
			Method function;
			try {
				function = PasswordSecure.class.getMethod(hashFunction, String.class);
				return String.valueOf(function.invoke(null, value));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}
   

}
