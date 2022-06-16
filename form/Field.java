package vplibrary.form;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Field<T> implements Serializable{
	protected String label;
    protected String name;
    protected T defaultValue;
    protected boolean required = false;
    protected boolean ignored = false;
	protected String errorMessage = "Valeur invalide";
	protected String tooltip = "";
    

    /**
     * Constructeur.
     *
     * @param label
     * @param name
     */
    public Field(String label, String name)
    {
        this.label = label;
        this.name = name;
    }
    
    public String getLabel() {
		return label;
	}

	public Field<T> setLabel(String label) {
		this.label = label;
		return this;
	}

	public String getName() {
		return name;
	}

	public Field<T> setName(String name) {
		this.name = name;
		return this;
	}
	
	

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public T getDefaultValue() {
		return defaultValue;
	}
	
	@SuppressWarnings("unchecked")
	public Field<T> setDefaultValue(Object value) {
		this.defaultValue = (T) value;
		return this;
	}

	public boolean isRequired() {
		return required;
	}
	
	public abstract boolean test(T value);

	public Field<T> setRequired(boolean required) {
		this.required = required;
		return this;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public Field<T> setIgnored(boolean ignored) {
		this.ignored = ignored;
		return this;
	}
    

    public T getRealValue(T value)
    {
        return value;
    }

    public HashMap<String, Object> serialize()
    {
        String className = getClass().getName();
        String[] nameParts = className.split(".");

        return new HashMap<String, Object>() {{
            put("label", getLabel());
            put("name", getName());
    		put("default", getDefaultValue());
			put("required", isRequired());
			put("class", nameParts[nameParts.length-1]);
        }};
    }
    
    public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		if(!errorMessage.isEmpty())
			this.errorMessage = errorMessage;
	}

	
}

	
