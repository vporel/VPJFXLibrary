package vplibrary.form;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import vplibrary.util.Predicate;

/**
 * Un champ du formulaire
 * @author VPOREL-DEV
 *
 * @param <T> Le type de la valeur
 */
public abstract class Field<T>{
	public static String REQUIRED_PREDICATE = "required";
	
	/**
	 * Texte affiché comme label dans les formulaires
	 */
	protected StringProperty labelProperty = new SimpleStringProperty();
	/**
	 * Nom du champ
	 */
    protected String name;
    /**
     * Défini si le champ doit obligatoirement être renseigné ou pas
     */
    protected BooleanProperty requiredProperty = new SimpleBooleanProperty();
    /**
     * Défini si le champ peut être modifié par l'utilisateur ou pas
     */
    protected BooleanProperty readOnlyProperty = new SimpleBooleanProperty();
    /**
     * Message d'erreur en cas de valeur non valide
     */
    private StringProperty errorProperty = new SimpleStringProperty();
    /**
     * Message indicatif sur la valeur à mettre
     */
    protected StringProperty tooltipProperty = new SimpleStringProperty("");
    /**
     * Valeur du champ
     */
    protected ObjectProperty<T> valueProperty = new SimpleObjectProperty<>();
    
    private ObservableMap<String, Predicate<T>> predicates = FXCollections.observableHashMap();
    
    private BooleanProperty validProperty = new SimpleBooleanProperty();
    /**
     * Constructeur.
     *
     * @param label
     * @param name
     */
    public Field(String label, String name)
    {
        setLabel(label);
        this.name = name;
        valueProperty.addListener((opts, oldVal, newVal) -> {
        	testValidity();
        });
        predicates.addListener(new MapChangeListener<String, Predicate<T>>(){
			public void onChanged(Change<? extends String, ? extends Predicate<T>> change) {
				testValidity();
			}
        });
        
        //Ajout du prédicate pour la propriété required
       addPredicate(REQUIRED_PREDICATE, new Predicate<T>(value -> {
    	   return !(isRequired() && (value == null || String.valueOf(value).isBlank()));
       }, "Remplissez ce champ"));
    }
    
    private void testValidity() {
    	boolean valid = true;
    	for(Predicate<T> predicate:predicates.values()) {
    		if(!predicate.test(getValue())) {
    			setError(predicate.getMessage());
    			valid = false;
    			break;
    		}
    	}
    	if(valid)
    		setError(""); 
    	validProperty.setValue(valid);
    }
    
    public Field<T> addPredicate(String name, Predicate<T> predicate){
    	this.predicates.put(name, predicate);
    	return this;
    }
    
    public Predicate<T> getPredicate(String name){
    	if(predicates.containsKey(name)) {
    		return predicates.get(name);
    	}else {
    		throw new IllegalArgumentException("Aucun prédicat portant le nom : "+name);
    	}
    }
    
	public final String getLabel() {
		return labelProperty.getValue();
	}

	public Field<T> setLabel(String label) {
		this.labelProperty.setValue(label);
		return this;
	}

	public final String getName() {
		return name;
	}

	public Field<T> setName(String name) {
		this.name = name;
		return this;
	}
	
	

	public final String getTooltip() {
		return tooltipProperty.getValue();
	}

	public Field<T> setTooltip(String tooltip) {
		this.tooltipProperty.setValue(tooltip);
		return this;
	}

	public final boolean isRequired() {
		return requiredProperty.getValue();
	}

	public Field<T> setRequired(boolean required) {
		this.requiredProperty.setValue(required);
		return this;
	}

	public ObjectProperty<T> valueProperty() {
		return this.valueProperty;
	}
	
	public Field<T> setValue(T value){
		valueProperty.setValue(value);
		return this;
	}
    
    public T getValue() {
    	return valueProperty.getValue();
    }
    
    public final String getError() {
		return errorProperty.getValue();
	}
    
    private Field<T> setError(String error){
    	errorProperty.setValue(error);
    	return this;
    }
    
    public final ReadOnlyStringProperty errorProperty() {
    	return errorProperty;
    }

	public final BooleanProperty requiredProperty() {
		return requiredProperty;
	}

	public final BooleanProperty readOnlyProperty() {
		return readOnlyProperty;
	}
	
	public final boolean isReadOnly() {
		return readOnlyProperty.getValue();
	}
	
	public final Field<T> setReadOnly(boolean readOnly){
		readOnlyProperty.setValue(readOnly);
		return this;
	}

	public final StringProperty tooltipProperty() {
		return tooltipProperty;
	}
    
	public final boolean isValid() {
		testValidity();
		return validProperty.getValue();
	}
	

    public final ReadOnlyBooleanProperty validProperty() {
		return validProperty;
	}

    /**
     * Utiliser une valeur d'un type non précisé
     * Attention cette méthode peut lancer une exception car on va essayer de faire un cast sur l'object 
     * @param object
     */
	public void setRawValue(Object object) throws ClassCastException{
		setValue((T) object);
	}
	
}

	
