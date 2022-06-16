package vporel.javafx.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import com.stockappro.entity.Category;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import vporel.form.DateField;
import vporel.form.DoubleField;
import vporel.form.Field;
import vporel.form.Input;
import vporel.form.IntegerField;
import vporel.form.LongField;
import vporel.form.Option;
import vporel.form.Select;
import vporel.form.TextArea;
import vporel.javafx.control.DateTimePicker;
import vporel.javafx.control.NumberField;
import vporel.javafx.util.ControlFX;
import vporel.util.VPString;

public class FormControl{
	private Field<?> field;
	private Control control;
	/**
	 * Dans le constructeur, le texte d'erreur est lié au texte entré par l'utilisateur
	 */
	private Text errorText = new Text("");
	private Label label;
	private boolean showLabel;
	private ObservableList<FormPredicate<?>> predicates = FXCollections.observableArrayList();
	
	public FormControl(Field<?> field, Object object, boolean showLabel) {
		this.field = field;
		createControl();
		control.setTooltip(new Tooltip(field.getTooltip()));
		this.showLabel = showLabel;
		
		newValueFromObject(object);

		label = new Label(field.getLabel());
		label.getStyleClass().add("form-label");
		this.control.getStyleClass().add("form-control");
		errorText.getStyleClass().addAll("error-text", "text-bad");
		
		if(field instanceof Input) {
			TextInputControl inputControl = (TextInputControl) control;
			errorText.textProperty().bind(Bindings.createStringBinding(() -> {
				if(field.isRequired() && inputControl.getText().isBlank()) {
					return "Remplissez ce champ";
				}
				if(
					field instanceof vporel.form.NumberField && ((vporel.form.NumberField) field).test(((NumberField) control).getValue())
					|| field instanceof vporel.form.TextField && ((vporel.form.TextField) field).test(((TextField) control).getText())
					|| field instanceof vporel.form.TextArea
				) {
					if(!(field instanceof vporel.form.TextArea)) {
						for(FormPredicate predicate:predicates) {
							if(
								(field instanceof vporel.form.NumberField && !predicate.test(((NumberField) control).getValue()))
								|| (field instanceof vporel.form.TextField && !predicate.test(((TextField) control).getText()))
							)
								return predicate.getErrorMessage();
						}
					}
					return "";
				}else
					return field.getErrorMessage();
			}, inputControl.textProperty(), predicates));
		}else if(control instanceof ComboBox) {
			bindAutoCompletion();
			errorText.textProperty().bind(Bindings.createStringBinding(() -> {
				if(field.isRequired() && ((ComboBox<Object>) control).getSelectionModel().getSelectedItem() == null) {
					return "Choisissez un élément";
				}
				if(((Select) field).test(((ComboBox<Object>) control).getSelectionModel().getSelectedItem())) {
					for(FormPredicate predicate:predicates) {
						if(
							!predicate.test(((Select) field).test(((ComboBox<Object>) control).getSelectionModel().getSelectedItem()))
						)
							return predicate.getErrorMessage();
					}
					return "";
				}else
					return field.getErrorMessage();
			}, ((ComboBox<Object>) control).getSelectionModel().selectedItemProperty(), predicates));
		}
	}
	public FormControl(Field<?> field, Object object) {
		this(field, object, true);
	}
	
	private void createControl() {
		control = new TextField(); // Default if field instanceof TextLine
		if(field instanceof TextArea) {
			control = new javafx.scene.control.TextArea();
		}else if(field instanceof vporel.form.PasswordField) {
			control = new PasswordField();
		}else if(field instanceof IntegerField) {
			control = new vporel.javafx.control.IntegerField();
		}else if(field instanceof LongField) {
			control = new vporel.javafx.control.LongField();
		}else if(field instanceof DoubleField) {
			control = new vporel.javafx.control.DoubleField();
		}else if(field instanceof DateField) {
			control = new DateTimePicker();
		}else if(field instanceof Select) {
			control = new ComboBox<Option>();
			ComboBox<Object> comboBoxControl = (ComboBox<Object>) control;
			List<Object> list = ((Select) field).getOptions();
			if(list instanceof ObservableList)
				comboBoxControl.setItems((ObservableList<Object>) list);
			else 
				comboBoxControl.setItems(FXCollections.observableArrayList(list));
			comboBoxControl.setEditable(true);
			ControlFX.handleEditableComboBox(comboBoxControl);
			
		}
	}
	public void addPredicate(FormPredicate<?> predicate) {
		predicates.add(predicate);
	}
	public Field<?> getField() {
		return field;
	}
	
	public Label getLabel() {
		return label;
	}
	
	public boolean isValid() {
		return errorText.getText().isEmpty();
	}
	
	public void reset() {
		if(control instanceof TextInputControl && !(control instanceof PasswordField) && field.getDefaultValue() != null) {
			((TextInputControl) control).setText(String.valueOf(field.getDefaultValue()));
		}else if(control instanceof ComboBox && ((ComboBox<Object>) control).getItems().size() > 0 && field.getDefaultValue() != null) {
				((ComboBox<Object>) control).setValue(field.getDefaultValue());
		}
	}
	
	public void empty() {
		if(control instanceof TextInputControl) {
			((TextInputControl) control).setText("");
		}else if(control instanceof ComboBox) {
			((ComboBox) control).getSelectionModel().clearSelection();
			((ComboBox) control).getEditor().setText("");
		}
	}

	
	public Control getControl() {
		return control;
	}
	
	public Text getErrorText() {
		return errorText;
	}

	
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
	
	public Object getValue() {
		if(control instanceof NumberField) { // The test for NumberField is the first because NumberField extends from TextField
			return ((NumberField<?>) control).getValue();
		}else if(control instanceof TextInputControl) { // TextField, TextArea, PasswordField
			return ((TextInputControl) control).getText();
		}else if(control instanceof ComboBox){
			return ((ComboBox<Object>) control).getSelectionModel().getSelectedItem();
		}else if(control instanceof DateTimePicker){
			return ((DateTimePicker) control).getDateTimeValue();
		}else {
			return null;
		}
	}

	public Pane getPane(Orientation orientation) {
		Pane pane = (orientation == Orientation.HORIZONTAL) ? new HBox(5) : new VBox(5);
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
