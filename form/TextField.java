package vplibrary.form;

import java.util.regex.Pattern;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import vplibrary.util.Predicate;

public class TextField extends InputField<String> {
	
	final public static String PATTERN = "^(.[\s]*.*)*$";
	protected ObjectProperty<Pattern> patternProperty = new SimpleObjectProperty<>();

	public TextField(String label, String name) {
		super(label, name);
		addPredicate("pattern", new Predicate<>(value -> { 
			String text = (value != null) ? String.valueOf(value) : "";
			return getPattern().matcher(text).find();
		}, "Le motif n'est pas respecté"));
	}
	
	public ObjectProperty<Pattern> patternProperty() {
		return patternProperty;
	}
	
	public Pattern getPattern() {
		if(patternProperty.getValue() == null) {
			return getDefaultPattern();
		}
		return patternProperty.getValue();
	}
	
	public TextField setPattern(Pattern p) {
		if(p.pattern().isBlank()) {
			patternProperty.setValue(getDefaultPattern());
			return this;
		}
		patternProperty.setValue(p);
		return this;
	}
	public TextField setPattern(String p, boolean caseSensitive) {
		if(!caseSensitive)
			setPattern(Pattern.compile(p, Pattern.CASE_INSENSITIVE));
		return this;
	}
	public TextField setPattern(String p) {
		setPattern(Pattern.compile(p));
		return this;
	}
	
	public Pattern getDefaultPattern() {
		return Pattern.compile(TextField.PATTERN, Pattern.CASE_INSENSITIVE);
	}
	

}
