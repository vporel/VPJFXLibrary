package vplibrary.form;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import vplibrary.util.Predicate;


public abstract class Form {
	
    protected Map<String, Field<?>> fields = new HashMap<>();
    /**
	 * Prédicats sur les données du formulaire
	 */
	private ObservableMap<String, Predicate<HashMap<String, Object>>> predicates = FXCollections.observableHashMap();

    protected StringProperty errorProperty = new SimpleStringProperty();    
    protected BooleanProperty validProperty = new SimpleBooleanProperty();
    
    protected HashMap<String, Object> initialData;
	public Form(HashMap<String, Object> initialData) {
		this.initialData = initialData;
		 predicates.addListener(new MapChangeListener<String, Predicate<HashMap<String, Object>>>(){
			public void onChanged(Change<? extends String, ? extends Predicate<HashMap<String, Object>>> change) {
				testValidity();
			}
        });
	}
	
	public Form() {
		this(null);
	}
	
	/**
	 * Les données initiales du formulaire
	 * @return
	 */
	public HashMap<String, Object> getInitialData(){
		return initialData;
	}
	
	private void testValidity() {
		boolean valid = true;
    	HashMap<String, Object> data = getData();
    	for(Predicate<HashMap<String, Object>> predicate:predicates.values()) {
    		if(!predicate.test(data)) {
    			setError(predicate.getMessage());
    			valid = false;
    			break;
    		}
    	}
    	if(valid)
    		setError("");
    	validProperty.setValue(valid);;
	}
	
	/**
	 * 
	 * @param name
	 * @param predicate HashMap<String, Object> Les données du formulaire
	 * @return
	 */
	public Form addPredicate(String name, Predicate<HashMap<String, Object>> predicate){
    	this.predicates.put(name, predicate);
    	return this;
    }
	
	public Predicate<HashMap<String, Object>> getPredicate(String name){
    	if(predicates.containsKey(name)) {
    		return predicates.get(name);
    	}else {
    		throw new IllegalArgumentException("Aucun prédicat portant le nom : "+name);
    	}
    }
	    
	
    public Map<String, Field<?>> getFields()
    {
        return fields;
    }

    /**
     * Ajout d'un champ au formulaire
     * @param field
     * @return
     */
    protected Form addField(Field<?> field)
    {
        fields.put(field.getName(), field);
        if(initialData != null && initialData.containsKey(field.getName())) {
        	try {
        		field.setRawValue(initialData.get(field.getName()));
        	}catch(ClassCastException e) {
        		//Do nothing
        	}
        }
        
        field.valueProperty().addListener((val) -> {
        	testValidity();
        });
        return this;
    }
    
    public String getError()
    {
        return errorProperty.getValue();
    }
    
    private Form setError(String error) {
    	errorProperty.setValue(error);
    	return this;
    }
    
    public StringProperty errorProperty() {
    	return errorProperty;
    }
    
    /**
     * Vérifie la validité de chaque champ et la validité du formulaire tout entier
     * @return
     */
    public boolean isValid() {
    	boolean fieldsValid = true;
    	for(Field<?> field:fields.values()) {
    		if(!field.isValid()) {
    			fieldsValid = false;
    			break;
    		}
    	}
    	testValidity();
    	return fieldsValid && validProperty.getValue();
    }
    
    public ReadOnlyBooleanProperty validProperty() {
		return validProperty;
	}
    
    /**
     * @return Les données actuelles du formulaire
     */
    public HashMap<String, Object> getData(){
    	HashMap<String, Object> data = new HashMap<>();
    	for(Field<?> field:fields.values()) {
    		data.put(field.getName(), field.getValue());
    	}
    	return data;
    }
    
    /**
     * Remplir tous les champs avec les données initiales. S'il n'y a pas de données initiales, les champs sont vidés
     * @return
     */
    public Form reset() {
    	for(Field<?> field:fields.values()) {
			if(initialData != null && initialData.containsKey(field.getName())) {
				try {
					field.setRawValue(initialData.get(field.getName()));
				}catch(ClassCastException e) {
					// Do nothing
				}
			}else {
				field.setValue(null);
			}
    	}
    	return this;
    }
    
    /**
     * Vider tous les champs
     * @return
     */
    public Form empty() {
    	for(Field<?> field:fields.values()) {
    		field.setValue(null);
    	}
    	return this;
    }

}
