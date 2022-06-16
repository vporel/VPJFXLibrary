package vporel.javafx.util;

import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.StringConverter;

public class ControlFX {
	// Fonction non utilisé et remplacée par une fonctionnalité (bindAutoCompletion) de controlsFX
	/**/
	public static void handleEditableComboBox(ComboBox<Object> comboBoxControl) {
		ObservableList<Object> options = comboBoxControl.getItems();
		//Add a string converter
		comboBoxControl.setConverter(new StringConverter<Object>() {
			
			@Override
			public String toString(Object obj) {
				return obj == null ? "" : obj.toString();
			}
			
			@Override
			public Object fromString(String str) {
				Object obj = null;
				if(str != null && !str.isBlank()) {
					for(Object item:options) {
						if(item.toString().equals(str))
							obj = item;
					}
				}
					
				return obj;
			}
		});
		comboBoxControl.setEditable(true);
        comboBoxControl.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){//mean onfocus
                comboBoxControl.show();
            }
        });

        comboBoxControl.getEditor().setOnMouseClicked(event ->{
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    return;
                }
            }
            comboBoxControl.show();
        });

        comboBoxControl.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            comboBoxControl.getEditor().positionCaret(comboBoxControl.getEditor().getText().length());
        });

        comboBoxControl.setOnKeyPressed(t -> comboBoxControl.hide());

        comboBoxControl.setOnKeyReleased(event -> {
        	String editorText = comboBoxControl.getEditor().getText();
        	 if ( event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN
                     || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                     || event.getCode() == KeyCode.HOME
                     || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB
             ) {
                 return;
             }

             if(event.getCode() == KeyCode.BACK_SPACE){
                 
                 if (editorText != null && editorText.length() > 0) {
                	 editorText = editorText.substring(0, editorText.length() - 1);
                 }
                 comboBoxControl.getSelectionModel().clearSelection();
                 
             }

             if(event.getCode() == KeyCode.ENTER && comboBoxControl.getSelectionModel().getSelectedIndex()>-1)
                 return;

             ObservableList<Object> filteredList = FXCollections.observableArrayList();

             for (Object opt : options) {
                 if (Pattern.compile(comboBoxControl.getEditor().getText(), Pattern.CASE_INSENSITIVE).matcher(opt.toString()).find()) {
                	 filteredList.add(opt);
                 }
             }

             comboBoxControl.setItems(filteredList);
             if(editorText != null){
                 comboBoxControl.getEditor().setText(editorText);
                 comboBoxControl.getEditor().positionCaret(editorText.length());
                 
             }
             comboBoxControl.show();
        });
    }
	/**/
}
