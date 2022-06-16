package vplibrary.form;

import java.util.HashMap;
import java.util.regex.Pattern;

public class TextField extends Input<String> {
	
	final public static String PATTERN = "^(.[\s]*.*)*$";
	protected Pattern pattern;

	public TextField(String label, String name) {
		super(label, name);
	}	
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public void setPattern(Pattern p) {
		if(p.pattern().isBlank()) {
			pattern = getDefaultPattern();
			return;
		}
		pattern = p;
	}
	public void setPattern(String p) {
		if(p.isBlank()) {
			pattern = this.getDefaultPattern();
			return;
		}
		pattern = Pattern.compile(p, Pattern.CASE_INSENSITIVE);
	}
	
	public Pattern getDefaultPattern() {
		return Pattern.compile(TextField.PATTERN, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public boolean test(String value) {
		return pattern.matcher(value).find();
	}
	
	public HashMap<String, Object> serialize()
    {
    	HashMap<String, Object> serial = super.serialize();
    	serial.put("pattern", pattern);
    	return serial;
    }

}
