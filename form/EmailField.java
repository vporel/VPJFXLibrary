package vplibrary.form;

import java.util.regex.Pattern;

public class EmailField extends TextField{

	final public static String PATTERN = "^([a-z0-9]+(\\.[a-z0-9]+)*@[a-z0-9]{2,}(\\.[a-z0-9]{2,})+)?$";

	public EmailField(String label, String name) {
		super(label, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pattern getDefaultPattern() {
		return Pattern.compile(EmailField.PATTERN, Pattern.CASE_INSENSITIVE);
	}

}
