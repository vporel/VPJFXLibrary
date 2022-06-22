package vplibrary.hibernate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import vplibrary.form.Field;
import vplibrary.form.FieldFactory;
import vplibrary.form.NoOptionException;
import vplibrary.form.annotations.*;

public abstract class Entity {
	


    /**
     * La propriété de l'entité qui est utilisée comme clée primaire
     * Les clés composites ne sont pas gérées par le framework
     * @return String
     */
    public abstract String getKeyProperty();
    
	 /**
	 * Le champ utilisé naturellement pour ordoner les éléments de l'entité
	 * Pour un ordre décroissant il faut ajouter un tiret devant le nom de l'entité (ex : -id)
	 */
	public String getNaturalOrderField() {
		return this.getKeyProperty();
	}
	
	public static String getEntityKeyProperty(Class<? extends Entity> entityClass){
		try {
			return ((Entity) entityClass.getDeclaredConstructor().newInstance()).getKeyProperty();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    public static String getEntityNaturalOrderField(Class<? extends Entity> entityClass){
    	try {
			return ((Entity) entityClass.getDeclaredConstructor().newInstance()).getNaturalOrderField();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

	
	/**
	 * @param entityClass
	 * @return Des instances de vplibrary.form.Field
	 */
	public static ArrayList<Field<?>> getFields(Class<? extends Entity> entityClass){
		ArrayList<Field<?>> fields = new ArrayList<>();
		for(java.lang.reflect.Field field: entityClass.getDeclaredFields()) {
			field.setAccessible(true);
			String name = field.getName(),
					label = name;
			Field<?> formField = null;
			ArrayList<Class<? extends Annotation>> annotationsClasses = new ArrayList<Class<? extends Annotation>>(){{
				add(TextField.class); add(PasswordField.class); add(EmailField.class); add(IntegerField.class);
				add(LongField.class); add(DoubleField.class); add(DateField.class); add(TextAreaField.class);
				add(FileField.class); add(SelectField.class); add(RelationField.class);
			}};
			for(Class<? extends Annotation> annotationClass:annotationsClasses) {
				if(field.isAnnotationPresent(annotationClass)) {
					try {
						formField = FieldFactory.getField(field.getName(), field.getAnnotation(annotationClass));
					} catch (NoOptionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
			
			if(formField == null) { // Aucune des annotations spécifiques trouvée
				 if(field.isAnnotationPresent(vplibrary.form.annotations.Field.class)) {
					label = field.getAnnotation(vplibrary.form.annotations.Field.class).label();
				}
				 formField = new vplibrary.form.TextField(label, name);
			}
			
			//Nullability
			if(field.isAnnotationPresent(Column.class)) {
				formField.setRequired(!field.getAnnotation(Column.class).nullable());
			}else if(field.isAnnotationPresent(JoinColumn.class)) {
				formField.setRequired(!field.getAnnotation(JoinColumn.class).nullable());
			}
			fields.add(formField);
		}
		return fields;
	}
	
}
