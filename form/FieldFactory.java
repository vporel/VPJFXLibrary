package vporel.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @author VPOREL-DEV
 *
 *Classe à léthodes statiques permettant de créer des instances de la classe vporel.form.Field à partir des annotations correspondantes
 */

public class FieldFactory {
	
	public static Field<?> getField(String name, vporel.form.annotations.TextField fieldAnnotation){
		TextField field = new TextField("", name);
		field.setDefaultValue(fieldAnnotation.defaultValue());
		field.setPattern(fieldAnnotation.pattern());
		field.setErrorMessage(fieldAnnotation.errorMessage());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.PasswordField fieldAnnotation){
		PasswordField field = new PasswordField("", name, fieldAnnotation.hashFunction());
		field.setPattern(fieldAnnotation.pattern());
		field.setErrorMessage(fieldAnnotation.errorMessage());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.TextAreaField fieldAnnotation){
		return new TextArea("", name);
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.DateField fieldAnnotation){
		DateField field = new DateField("", name);
		field.setDefaultValue(fieldAnnotation.defaultValue());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.FileField fieldAnnotation){
		return new FileField("", name);
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.EmailField fieldAnnotation){
		EmailField field = new EmailField("", name);
		field.setPattern(fieldAnnotation.pattern());
		field.setErrorMessage(fieldAnnotation.errorMessage());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.IntegerField fieldAnnotation){
		Input<?> field = new IntegerField("", name, fieldAnnotation.min(), fieldAnnotation.max());
		field.setDefaultValue(fieldAnnotation.defaultValue());
		field.setErrorMessage(fieldAnnotation.errorMessage());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.LongField fieldAnnotation){
		Input<?> field = new LongField("", name, fieldAnnotation.min(), fieldAnnotation.max());
		field.setDefaultValue(fieldAnnotation.defaultValue());
		field.setErrorMessage(fieldAnnotation.errorMessage());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.DoubleField fieldAnnotation){
		Input<?> field = new DoubleField("", name, fieldAnnotation.min(), fieldAnnotation.max());
		field.setDefaultValue(fieldAnnotation.defaultValue());
		field.setErrorMessage(fieldAnnotation.errorMessage());
		return field;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.SelectField fieldAnnotation) throws NoOptionException{
		Class<?> optionsClass = fieldAnnotation.optionsClass();
		Method m;
		try {
			m = optionsClass.getMethod("getOptions");// From the interface SelectFieldOptions
			List<Object> options = (List<Object>) m.invoke(null);
			if(options == null) {
				throw new NoOptionException("Aucune option définie pour le champ select");
			}
			return new Select("", name, options);
			
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field<?> getField(String name, vporel.form.annotations.RelationField fieldAnnotation){
		Class<?> targetEntity =  fieldAnnotation.targetEntity();
		Method getElements;
		try {
			getElements = targetEntity.getMethod("getElements");
			Select field = new Select("", name, (List<Object>) getElements.invoke(targetEntity.getConstructor().newInstance()));
			field.setEditable(fieldAnnotation.editable());
			return field;
			
		} catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
