package vporel.javafx.control;

import java.util.Collection;
import java.util.Optional;

import javafx.stage.StageStyle;

public class ChoiceDialog<T> extends javafx.scene.control.ChoiceDialog<T>{
	public ChoiceDialog(T defaultElement, Collection<T> elements, String title, String headerText, String contentText) {
		super(defaultElement, elements);
		this.setTitle(title);
		this.setHeaderText(headerText);
		this.setContentText(contentText);
	}
	public ChoiceDialog(Collection<T> elements, String title, String headerText, String contentText) {
		this(null, elements, title, headerText, contentText);
	}
	public ChoiceDialog(Collection<T> elements, String title, String headerText) {
		this(null, elements, title, headerText, "");
	}
	
	public T get() {
		Optional<T> element = this.showAndWait();
		if(element != null && element.isPresent() && element.get() != null) {
			return element.get();
		}else{
			return null;
		}
		
	}
}
