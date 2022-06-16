package VPLibrary.javafx.dialog;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import VPLibrary.javafx.form.FormPredicate;

public abstract class NumberInputDialog<T> extends TextInputDialog{
	private ObservableList<FormPredicate<T>> predicates = FXCollections.observableArrayList();

	public NumberInputDialog() {
		this.getEditor().addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	         public void handle( KeyEvent t ) {
	            char ar[] = t.getCharacter().toCharArray();
	            char ch = ar[t.getCharacter().toCharArray().length - 1];
	            if (!acceptsChar(ch))
	            		t.consume();
				
	         }
	      });
		this.getEditor().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			for(FormPredicate<T> p:predicates) {
				if(!p.test(convertValue(NumberInputDialog.this.getEditor().getText()))) {
					NumberInputDialog.this.setContentText(p.getErrorMessage());
					event.consume();
					return;
				}
			}
		});
	}
	public NumberInputDialog(T defaultValue) {
		this.setValue(defaultValue);
	}
	
	public T get() {
		Optional<String> result = this.showAndWait();
		if(result != null && result.isPresent() && result.get() != null) {
			return convertValue(result.get());
		}else{
			return null;
		}
		
	}
	public abstract T convertValue(String val);
	public void setValue(T val) {
		
		this.getEditor().setText(String.valueOf(val));
	}
	
	public boolean acceptsChar(char ch) {
		return (ch >= '0' && ch <= '9');
	}
	/**
	 * Le pr�dicat est test� lors de la modification de la valeur du champ texte
	 * LE message du pr�dicat sera affich� dans la zone ContentText
	 * @param predicate
	 */
	public void addPredicate(FormPredicate<T> predicate) {
		predicates.add(predicate);
	
	}
	
}
