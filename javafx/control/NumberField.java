package vplibrary.javafx.control;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public abstract class NumberField<T extends Number> extends TextField {
	protected ObjectProperty<T> valueProperty = new SimpleObjectProperty<>();
	private boolean valueTextChanging = false;
	public NumberField() {
		this.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            char ar[] = event.getCharacter().toCharArray();
            char ch = ar[event.getCharacter().toCharArray().length - 1];
            if (!acceptsChar(ch)) 
            	event.consume();
	    });
		this.textProperty().addListener((opts, oldText, text) -> {
			if(!valueTextChanging) {
				valueTextChanging = true;
				valueProperty.setValue((text == null || text.isBlank()) ? null : castString(text));
			}else {
				valueTextChanging = false;
			}
		});
		valueProperty.addListener((opts, oldVal, val) -> {
			if(!valueTextChanging) {
				valueTextChanging = true;
				this.setText((val != null) ? String.valueOf(val) : "");
			}else {
				valueTextChanging = false;
			}
		});
	}
	public NumberField(T val) {
		this();
		setValue(val);
		
	}
	
	/**
	 * Convertit une chaine avec le type T
	 * @param str
	 * @return
	 */
	protected abstract T castString(String str);
	
	public T getValue() {
		return valueProperty.getValue();
	}
	
	
	
	public ObjectProperty<T> valueProperty() {
		return valueProperty;
	}
	public void setValue(T val) {
		this.setText(String.valueOf(val));
	}
	
	public boolean acceptsChar(char ch) {
		return (ch >= '0' && ch <= '9');
	}
	

}
