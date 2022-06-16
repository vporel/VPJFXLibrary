package VPLibrary.javafx.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.text.Text;
import VPLibrary.form.Form;
import VPLibrary.hibernate.HasId;

public class FormPane<Entity> extends GridPane{
	private Form<Entity> form;
	private int columnsCount;
	private Orientation orientation;
	private	List<FormControl> formControls = new ArrayList<>();
	
	private Button resetButton = new Button("Reset");
	private Button submitButton = new Button("Submit");
	private Label caption = new Label("Form");
	private final String defaultErrorText = "V�rifiez tous les champs";
	private Text errorText = new Text("");
	
	private GridPane elementsGrid;
	private HBox btnsLayout;
	
	private int row, column, elementsGridRow, elementsGridColumn;
	
	private ObservableList<FormPredicate<Entity>> predicates = FXCollections.observableArrayList();
	
	public FormPane(Form<Entity> form) {
		this(form, 1);
	}
	
	public FormPane(Form<Entity> form, int columnsCount) {
		this(form, columnsCount, Orientation.VERTICAL);
	}
	
	public FormPane(Form<Entity> form, int columnsCount, Orientation orientation) {
		this.form = form;
		this.columnsCount = columnsCount;
		this.orientation = orientation;
		this.setHgap(5);
		this.setVgap(5);
		this.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		btnsLayout = new HBox();
		btnsLayout.setAlignment(Pos.CENTER_RIGHT);
		btnsLayout.setSpacing(5);
		
		createFormControls();
		elementsGrid = new GridPane(); //Avant la construction
		elementsGrid.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		elementsGrid.setHgap(5);
		elementsGrid.setVgap(5);
		elementsGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		build();
		addClasses();
		
		
		
	}
	
	/**
	 * Le pr�dicat est test� lors de la v�rification du formulaire tout entier
	 * La v�rification se fait sur l'objet et non sur des attributs particuli�s
	 * @param predicate
	 */
	public void addPredicate(FormPredicate<Entity> predicate) {
		predicates.add(predicate);
	}
	
	public void addPredicate(String controlName, FormPredicate<?> predicate) throws NoSuchControlException {
		getFormControl(controlName).addPredicate(predicate);
	}
	
	public Form<?> getForm() {
		return form;
	}
	
	/**
	 * Appelle la methode getObject du formulaire
	 * @return
	 */
	public Entity getObject() {
		return form.getObject();
	}
	
	public FormControl getFormControl(String name) throws NoSuchControlException {
		for(FormControl fc:formControls) {
			if(fc.getField().getName().equals(name)) {
				return fc;
			}
		}
		throw new NoSuchControlException("Contr�le ["+name+"] non trouv�e");
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

	public Label getCaption() {
		return caption;
	}

	public Text getErrorText() {
		return errorText;
	}
		
	/**
	 * Retorune les valeurs des diff�rents controls, qu'elles soient valides ou pas
	 * @return HashMap
	 */
	public HashMap<String, Object> getData(){
		
		HashMap<String, Object> data = new HashMap<>();
		for(FormControl formControl: formControls) {
			data.put(formControl.getField().getName(), formControl.getValue());
		}
		return data;
		
	}
	
	public void setObject(Entity object) {
		empty();
		form.setObject(object);
		for(FormControl fc:formControls) {
			fc.newValueFromObject(object);
		}
	}
	
	/**
	 * If the returned value is false, it propably means some fields in the form are not valid
	 * @return boolean
	 */
	public boolean updateObject() {
		if(this.isValid()) {
			form.updateObject(getData());
			return true;
		}else {
			return false;
		}
	}
	
	public void showButtons() {
		btnsLayout.setVisible(true);
		btnsLayout.setManaged(true);
	}
	
	public void hideButtons() {
		btnsLayout.setVisible(false);
		btnsLayout.setManaged(false);
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
	 * Le test est d'abord fait sur les champs individuellement
	 * Ensuite le formulaire tout entier (g�n�ralement pour s'assurer que les champs mis ensemble ont des informations coh�rentes)
	 * Enfin on teste les autres pr�dicats ajout�s
	 * @return true si les donn�es sont valides, false sinon
	 */
	public boolean isValid() {
		boolean valid = true;
		for(FormControl formControl: formControls) {
			if(!formControl.isValid()) {
				valid = false;
				break;
			}
		}

		Entity testObject = form.newObjectClassInstance();
		if(form.getObject() instanceof HasId && ((HasId) form.getObject()).getId() != null) {
			((HasId) testObject).setId(((HasId) form.getObject()).getId());
		}
		form.updateObject(getData(), testObject);
		
		if(valid) {
			if(!form.isValid(testObject)) {
				valid = false;
				errorText.setText(form.getErrorMessage());
			}
			if(valid) {
				for(FormPredicate<Entity> predicate:predicates) {
					if(!predicate.test(testObject)) {
						valid = false;
						errorText.setText(predicate.getErrorMessage());
						break;
					}
				}
			}
		}else {
			errorText.setText(defaultErrorText);
		}
		
		if(valid)
			errorText.setVisible(false);
		else
			errorText.setVisible(true);
		return valid;
	}

	private void createFormControls() {
		form.getFields().forEach(field -> {
			formControls.add(new FormControl(field, form.getObject()));
		});
	}

	public void listAdd(String listName, Object object) throws Exception{
		for(FormControl fc:formControls) {
			if(fc.getField().getName().equals(listName)) {
				try {
					fc.add(object);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		throw new Exception("Liste ["+listName+"] non trouv�e");
	}
	
	public void reset() {
		for(FormControl fc:formControls) {
			fc.reset();
		}
	}
	public void empty() {
		for(FormControl fc:formControls) {
			fc.empty();
		}
	}
	
	private void build() {
		//Add caption
		this.add(caption, 0, 0);
		caption.setPrefWidth(Double.MAX_VALUE);
		
		caption.managedProperty().bind(caption.visibleProperty());
		
		row = 1;
		column = 0;
		/**
		 * Create form body
		 * If there is only one object, a simple form is created
		 * But if there is more than one, we use a tableView
		 */
		this.add(elementsGrid, column, row);
		row++;
		elementsGridRow = 0;
		elementsGridColumn = 0;
		for(FormControl control : formControls) {
			elementsGrid.add(control.getPane(orientation), elementsGridColumn, elementsGridRow);
			elementsGridColumn++;
			if(elementsGridColumn >= this.columnsCount) { // Si on est � la fin de la ligne, on passe � la premiere colonne de la ligne suivante
				elementsGridColumn = 0;
				elementsGridRow++;
			}
		}		
		//Constraints
		ColumnConstraints columnContraints = new ColumnConstraints();
		columnContraints.setPercentWidth(100d/columnsCount);
		for(int j = 0;j<columnsCount;j++)
			elementsGrid.getColumnConstraints().add(j, columnContraints);
		
		//Put errorText
		row++;
		column = 0;
		this.add(errorText, column, row);
		GridPane.setHalignment(errorText, HPos.CENTER);
		GridPane.setValignment(errorText, VPos.CENTER);
		errorText.managedProperty().bind(errorText.visibleProperty());
		errorText.setVisible(false);
		//Create form buttons
		row++;
		column = 0;
		
		
		btnsLayout.getChildren().addAll(resetButton, submitButton);
		this.add(btnsLayout, column, row);
		GridPane.setHalignment(btnsLayout, HPos.RIGHT);
		GridPane.setValignment(btnsLayout, VPos.CENTER);
		
		ColumnConstraints columnConstraint = new ColumnConstraints();
		columnConstraint.setPercentWidth(100d);
		this.getColumnConstraints().add(columnConstraint);
		
	}
	
	private void addClasses() {
		resetButton.getStyleClass().addAll("form-button", "reset-btn");
		submitButton.getStyleClass().addAll("form-button", "submit-btn");
		errorText.getStyleClass().addAll("error-text", "text-bad");
		caption.getStyleClass().add("form-caption");
	}

}
