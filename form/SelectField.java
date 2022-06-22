package vplibrary.form;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectField extends Field<Object> {
	
	private ObservableList<Object> options = FXCollections.observableArrayList();
	private BooleanProperty editableProperty = new SimpleBooleanProperty();

	public SelectField(String label, String name, ObservableList<Object> options) {
		super(label, name);
		this.options = options;
		
		this.getPredicate(Field.REQUIRED_PREDICATE).setMessage("Sélectionnez un élément");
	}
	
	public ObservableList<Object> getOptions(){
		return options;
	}

	public boolean isEditable() {
		return editableProperty.getValue();
	}

	public SelectField setEditable(boolean editable) {
		this.editableProperty.setValue(editable);
		return this;
	}

	public BooleanProperty getEditableProperty() {
		return editableProperty;
	}
	
	

}
