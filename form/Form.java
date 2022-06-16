package vporel.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import vporel.util.VPString;


public abstract class Form<Entity> {
	
    protected ArrayList<Field<?>> fields = new ArrayList<>();

    protected String errorMessage = "";
    
    protected Entity object;
	
    /**
     * 
     * @param object
     * @throws NullFormObjectException
     */
	public Form(Entity object) throws NullFormObjectException {
		if(object != null)
			this.object = object;
		else
			throw new NullFormObjectException("L'object passé au constructeur du formulaire est null.");

        build();
	}
	
	public Form(Class entityClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		this.object = (Entity) entityClass.getDeclaredConstructor().newInstance();
        build();
	}
	
	public Entity getObject() {
		return object;
	}
	
	public void setObject(Object obj) {
		object = (Entity) obj;
	}
    
    public ArrayList<Field<?>> getFields()
    {
        return fields;
    }

    protected Form<Entity> addField(Field<?> field)
    {
        fields.add(field);
        return this;
    }


    public boolean hasError()
    {
        return !errorMessage.isBlank();
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
 
    public void updateObject(HashMap<String, Object> objectData){
    	updateObject(objectData, object);
	
	}
    
    /**
     * Peut être appelée pour hydrater l'objet en paramètre en lui passant les données (aussi en paramètre)
     * @param objectData
     * @param obj
     */
    public void updateObject(HashMap<String, Object> objectData, Entity obj) {
    	Class<?> objectClass = object.getClass();
    	objectData.forEach((key, value) -> {
    		try {
    			if(value != null) {
	    			if(value instanceof Option)
						value = ((Option) value).getValue();
					Method m = objectClass.getMethod("set"+VPString.ucfirst(key), value.getClass());
					Field<?> field = fields.get(0);
					for(Field<?> f : fields) {
						if(f.getName() == key) {
							field = f;
						}
					}
					if(field instanceof PasswordField)
						m.invoke(obj, ((PasswordField) field).getRealValue(String.valueOf(value)));
					else {
						m.invoke(obj, value);
					}
    			}
			} catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    }
    
    public Entity newObjectClassInstance() {
    	try {
			return (Entity) object.getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    
    public abstract boolean isValid(Entity testObject);
    public boolean isValid() {
    	return isValid(object);
    }

    /**
     * In this method, the user(developer) is asked to add the fields of the form.
     */
    abstract public void build();

    

}
