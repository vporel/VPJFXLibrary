package VPLibrary.javafx.control;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public abstract class NumberField<T extends Number> extends TextField {
	
	public NumberField() {
		this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	         public void handle( KeyEvent t ) {
	            char ar[] = t.getCharacter().toCharArray();
	            char ch = ar[t.getCharacter().toCharArray().length - 1];
	            if (!acceptsChar(ch)) 
	            		t.consume();
	         }
	      });
	}
	public NumberField(T val) {
		this();
		setValue(val);
		
	}
	
	public abstract T getValue();
	
	public void setValue(T val) {
		this.setText(String.valueOf(val));
	}
	
	public boolean acceptsChar(char ch) {
		return (ch >= '0' && ch <= '9');
	}
	

}
