package vplibrary.javafx.form;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import vplibrary.form.DateField;
import vplibrary.form.DoubleField;
import vplibrary.form.Field;
import vplibrary.form.InputField;
import vplibrary.form.IntegerField;
import vplibrary.form.LongField;
import vplibrary.form.NumberField;
import vplibrary.form.Option;
import vplibrary.form.SelectField;
import vplibrary.form.TextAreaField;
import vplibrary.javafx.control.DateTimePicker;
import vplibrary.javafx.util.ControlFX;

public class FormControl{
	private Field<?> field;
	private Control control;
	/**
	 * Dans le constructeur, le texte d'erreur est liï¿½ au texte entrï¿½ par l'utilisateur
	 */
	private Text errorText = new Text("");
	private Label label;
	private boolean showLabel;
	
	public FormControl(Field<?> field, boolean showLabel) {
		this.field = field;
		
		//Création du control
		control = new TextField(); // Contrôle par défaut
		if(field instanceof TextAreaField) {
			control = new javafx.scene.control.TextArea();
		}else if(field instanceof vplibrary.form.PasswordField) {
			control = new PasswordField();
		}else if(field instanceof IntegerField) {
			control = new vplibrary.javafx.control.IntegerField();
		}else if(field instanceof LongField) {
			control = new vplibrary.javafx.control.LongField();
		}else if(field instanceof DoubleField) {
			control = new vplibrary.javafx.control.DoubleField();
		}else if(field instanceof DateField) {
			control = new DateTimePicker();
		}else if(field instanceof SelectField) {
			control = new ComboBox<Option>();
			ComboBox<Object> comboBoxControl = (ComboBox<Object>) control;
			List<Object> list = ((SelectField) field).getOptions();
			if(list instanceof ObservableList)
				comboBoxControl.setItems((ObservableList<Object>) list);
			else 
				comboBoxControl.setItems(FXCollections.observableArrayList(list));
			comboBoxControl.setEditable(true);
			ControlFX.handleEditableComboBox(comboBoxControl);
			
		}
		
		control.setTooltip(new Tooltip(field.getTooltip()));
		this.showLabel = showLabel;

		label = new Label(field.getLabel());
		label.getStyleClass().add("form-label");
		this.control.getStyleClass().add("form-control");
		errorText.getStyleClass().addAll("error-text", "text-bad");
		
		if(field instanceof NumberField) {
			vplibrary.javafx.control.NumberField<Number> inputControl = (vplibrary.javafx.control.NumberField<Number>) control;
			if(((NumberField<Number>) field).getValue() != null) {
				inputControl.setValue(((NumberField<Number>) field).getValue());
			}
			((NumberField<Number>) field).valueProperty().bindBidirectional(inputControl.valueProperty());
			inputControl.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				//Affichage de l"erreur uniquement lorsque l'utilisateur commence à modifier le champ
	            if(!errorText.textProperty().isBound())
	        		errorText.textProperty().bind(field.errorProperty());
		    });
		}else if(field instanceof InputField) {
			TextInputControl inputControl = (TextInputControl) control;
			if(((InputField<String>) field).getValue() != null) {
				inputControl.setText(((InputField<String>) field).getValue());
			}
			((InputField<String>) field).valueProperty().bindBidirectional(inputControl.textProperty());
			inputControl.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				//Affichage de l"erreur uniquement lorsque l'utilisateur commence à modifier le champ
	            if(!errorText.textProperty().isBound())
	        		errorText.textProperty().bind(field.errorProperty());
		    });
		}else if(control instanceof ComboBox) {
			bindAutoCompletion();
			((SelectField) field).valueProperty().bind( ((ComboBox<Object>) control).getSelectionModel().selectedItemProperty());
			((ComboBox<Object>) control).getSelectionModel().selectedItemProperty().addListener((opts, oldVal, newVal) -> {
				//Affichage de l"erreur uniquement lorsque l'utilisateur commence à modifier le champ
	            if(!errorText.textProperty().isBound())
	        		errorText.textProperty().bind(field.errorProperty());
		    });
		}
	}
	public FormControl(Field<?> field) {
		this(field, true);
	}
	public Field<?> getField() {
		return field;
	}
	
	public Label getLabel() {
		return label;
	}
	
	public boolean isValid() {
		return field.isValid();
	}
	
	public Control getControl() {
		return control;
	}
	
	public Text getErrorText() {
		return errorText;
	}

	/*
	public void newValueFromObject(Object object) {
		Object value = field.getDefaultValue();
		if(object != null) {
			Class<?> objectClass = object.getClass();
			try {
				Method m = objectClass.getMethod("get"+VPString.ucfirst(field.getName()));
				value = m.invoke(object);
				
			} catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(control instanceof TextInputControl && !(control instanceof PasswordField)) {
			((TextInputControl) control).setText(String.valueOf(value));
		}else if(control instanceof DateTimePicker) {
			((DateTimePicker) control).setDateTimeValue((LocalDateTime) value);
		}else if(control instanceof ComboBox) {
			List<Object> items = ((ComboBox<Object>) control).getItems();
			if(items.size() > 0) {
				if(items.get(0) instanceof Option) {
					for(Object obj : items) {
						if(((Option) obj).getValue().equals(value)) {
							((ComboBox<Object>) control).setValue(obj);
							break;
						}
					}
				}else {
					((ComboBox<Object>) control).setValue(value);
				}
			}
			
		}
		
	}
	*/
	public Object getValue() {
		return field.getValue();
	}

	public Pane getPane() {
		Pane pane = new VBox(5);
		VBox controlLayout = new VBox();
		control.setPrefWidth(Double.MAX_VALUE);
		controlLayout.getChildren().add(control);
		controlLayout.getChildren().add(errorText);
		errorText.managedProperty().bind(errorText.visibleProperty());
		errorText.visibleProperty().bind(Bindings.createBooleanBinding(() -> !errorText.getText().isBlank(), errorText.textProperty()));
		
		if(showLabel)
			pane.getChildren().add(label);
		pane.getChildren().add(controlLayout);
		return pane;
		
	}
	
	/**
	 * This functions must be called only if the control is a ComboBox
	 */
	private void bindAutoCompletion(){
		//TextFields.bindAutoCompletion(((ComboBox) control).getEditor(), ((ComboBox) control).getItems());
	}
	public void add(Object object) throws Exception{
		if(control instanceof ComboBox) {
			((ComboBox<Object>) control).getItems().add(object);
			bindAutoCompletion();
		}else
			throw new Exception("Le control n'est pas une ComboBox");
	}
}
