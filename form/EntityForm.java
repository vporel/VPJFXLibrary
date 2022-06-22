package vplibrary.form;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import vplibrary.form.annotations.FormUsableEntity;
import vplibrary.form.annotations.FormUsableEntity.DEFAULT_VALIDATOR;
import vplibrary.hibernate.Entity;
import vplibrary.util.ObjectToHashMap;
import vplibrary.util.Predicate;

/**
 * Formulaire dont les champs sont les propritétés d'une classe entité
 * @author VPOREL-DEV
 *
 * @param <T> La classe entité
 */
public class EntityForm<T extends Entity> extends Form{
	private Class<T> entityClass;
	private T object;
	/**
	 * 
	 * @param entityClass
	 * @param object l'instance gérée par le formulaire
	 */
	public EntityForm(Class<T> entityClass, Entity object){
		super((object != null) ? ObjectToHashMap.convert(object) : null);
		this.entityClass = entityClass;
		if(object == null)
			this.object =  newEntityClassInstance();
		else
			this.object = (T) object;
		build();
		
	}
	
	/**
	 * Une instance de la classe entité est crée automatiquement
	 * @param entityClass
	 */
	public EntityForm(Class<T> entityClass){
		this(entityClass, null);
		
	}
	
	
	/**
	 * Construction du formulaire, principalement la création des champs en fonction des propriétés de la classe entité
	 */
	private void build() {
		if(this.entityClass.isAnnotationPresent(FormUsableEntity.class)) {
			try {
				Class<?> validatorClass = this.object.getClass().getAnnotation(FormUsableEntity.class).validator();
				
				if(validatorClass != DEFAULT_VALIDATOR.class) {
					Predicate<HashMap<String, Object>> validator = (Predicate<HashMap<String, Object>>) validatorClass.getConstructor().newInstance();
					addPredicate("ENTITY_VALIDATOR", validator);
				}
			} catch (InstantiationException | IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Field<?> field:Entity.getFields(this.entityClass)) {
			addField(field);
		}
	}
	
	
	
	 /**
     * Hydrate l'instance gérée par le formulaire avec les valeurs des différents champs
     */
    public void updateObject() {
    	getData().forEach((key, value) -> {
    		try {
    			if(value instanceof Option)
					value = ((Option) value).getValue();
    			java.lang.reflect.Field reflectField = entityClass.getDeclaredField(key);
    			reflectField.setAccessible(true);
				Field<?> field = null;
				for(Field<?> f : fields.values()) {
					if(f.getName() == key) {
						field = f;
						break;
					}
				}
				if(field instanceof PasswordField)
					value = ((PasswordField) field).getHashedValue();

    			reflectField.set(object, value);
			} catch (NoSuchFieldException|SecurityException|IllegalAccessException|IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    }
	

    
    public T newEntityClassInstance() {
    	try {
			return (T) entityClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

	public T getObject() {
		// TODO Auto-generated method stub
		return object;
	}

	/**
	 * Une nouvelle instance de la classe entité sera créée
	 */
	@Override
	public EntityForm<T> empty() {
		this.object = newEntityClassInstance();
		initialData = ObjectToHashMap.convert(object);
		super.empty();
		return this;
	}
	
	
	
}
