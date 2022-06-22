package vplibrary.javafx.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import vplibrary.form.Form;
import vplibrary.hibernate.HasId;
import vplibrary.javafx.util.VPNode;
import vplibrary.util.Callback;
import vplibrary.util.Predicate;
import vplibrary.util.SimpleCallable;
import vplibrary.util.SimpleCallback;

public class FormPane extends VBox{
	private Form form;
	private Pane controlsPane;
	private int columnsCount;
	private Orientation orientation;
	private	List<FormControl> formControls = new ArrayList<>();
	
	private Button resetButton = new Button("Reset");
	private Button submitButton = new Button("Submit");
	private HBox btnsLayout;
	private Label errorLabel = new Label("");
	
	private SimpleCallable onSubmit;
	private SimpleCallback<FormEvent> onReset;
	
	
	public FormPane(Form form) {
		this(form, 1);
	}
	
	public FormPane(Form form, int columnsCount) {
		this(form, columnsCount, Orientation.VERTICAL);
	}
	
	public FormPane(Form form, int columnsCount, Orientation orientation) {
		this.form = form;
		this.columnsCount = columnsCount;
		this.orientation = orientation;
		this.setPrefSize(Pane.USE_COMPUTED_SIZE, Pane.USE_COMPUTED_SIZE);
		this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.controlsPane = (orientation == Orientation.VERTICAL) ? new HBox(3) : new VBox(3);
		this.controlsPane.setPrefSize(Pane.USE_COMPUTED_SIZE, Pane.USE_COMPUTED_SIZE);
		this.controlsPane.setMaxWidth(Double.MAX_VALUE);
		
		btnsLayout = new HBox();
		btnsLayout.setAlignment(Pos.CENTER_RIGHT);
		btnsLayout.setSpacing(5);
		
		//Création des contrôles en fonction des champs du formulaire
		form.getFields().forEach((fielName, field) -> {
			formControls.add(new FormControl(field));
		});
		
		resetButton.setOnAction(e -> {
			FormEvent event = new FormEvent();
			if(onReset != null)
				onReset.call(event);
			if(!event.isCanceled()) {
				form.reset();
			}
			
		});
		submitButton.setOnAction(e -> {

			if(getForm().isValid()) {
				if(onSubmit != null)
					try {
						onSubmit.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			
		});
		
		build();
		addClasses();
		
		
		
	}
	
	public Form getForm() {
		return form;
	}
	
	public FormControl getFormControl(String name) throws NoSuchControlException {
		for(FormControl fc:formControls) {
			if(fc.getField().getName().equals(name)) {
				return fc;
			}
		}
		throw new NoSuchControlException("Contrï¿½le ["+name+"] non trouvï¿½e");
	}
	
	public void controlsSetDisable(boolean disable, String... names) throws NoSuchControlException {
		for(String name:names) {
			this.getFormControl(name).getControl().setDisable(disable);
			
		}
	}
	
	public int getColumnsCount() {
		return columnsCount;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public Button getResetButton() {
		return resetButton;
	}

	public Button getSubmitButton() {
		return submitButton;
	}
	public Label getErrorTextLabel() {
		return errorLabel;
	}
	
	public Pane getButtonsLayout() {
		return btnsLayout;
	}
	
	public void addButtons(boolean before, Button ...btns) {
		if(before)
			for(Button btn:btns) {
				btnsLayout.getChildren().add(0, btn);
				btn.getStyleClass().add("form-button");
			}
		else
			for(Button btn:btns) {
				btnsLayout.getChildren().add(btn);
				btn.getStyleClass().add("form-button");
			}
	}
	
	/**
	 * 
	 * @param onSubmit
	 */
	public void setOnSubmit(SimpleCallable onSubmit) {
		this.onSubmit = onSubmit;
	}
	public void setOnReset(SimpleCallback<FormEvent> onReset) {
		this.onReset = onReset;
	}
	
	private void build() {
		int elementsCountPerBox = Double.valueOf(this.formControls.size() / this.columnsCount).intValue(); // si orientation = Vertical
		if(this.orientation == Orientation.HORIZONTAL) {
			elementsCountPerBox = this.columnsCount;
		}
		Pane currentBox = null;
		for(int i = 0;i<formControls.size();i++) {
			FormControl fc = formControls.get(i);
			if(i % elementsCountPerBox == 0) {
				if(currentBox != null) {
					controlsPane.getChildren().add(currentBox);
				}
				currentBox = this.orientation == Orientation.VERTICAL ? new VBox(3) : new HBox(3);
				if(currentBox instanceof VBox) {
					currentBox.minWidthProperty().bind(Bindings.createDoubleBinding(() -> {
						return controlsPane.getWidth()/columnsCount;
					}, controlsPane.widthProperty()));
				}else {
					currentBox.setMaxWidth(Double.MAX_VALUE);
				}
			}
			currentBox.getChildren().add(fc.getPane());
			if((i+1) % elementsCountPerBox == 0) {
				controlsPane.getChildren().add(currentBox);
			}
		}		
		
		errorLabel.managedProperty().bind(errorLabel.visibleProperty());
		errorLabel.textProperty().bind(form.errorProperty());
		errorLabel.visibleProperty().bind(form.validProperty());
		
		btnsLayout.getChildren().addAll(resetButton, submitButton);
		this.getChildren().addAll(controlsPane, errorLabel, btnsLayout);
		
	}
	
	private void addClasses() {
		resetButton.getStyleClass().addAll("form-button", "reset-btn", "btn", "btn-bad");
		submitButton.getStyleClass().addAll("form-button", "submit-btn", "btn", "btn-primary");
		errorLabel.getStyleClass().addAll("error-text", "text-bad");
	}
	
	public static class FormEvent{
		private boolean canceled = false;
		
		public void preventDefault() {
			this.canceled = true;
		}
		
		public boolean isCanceled() {
			return canceled;
		}
	}

}
