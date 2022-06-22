package vplibrary.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author VPOREL-DEV
 *
 *Classe � l�thodes statiques permettant de cr�er des instances de la cvplibrary.porel.form.Field � partir des annotations correspondantes
 */

public class FieldFactory {
	public static Field<?> getField(String name, Annotation annotation) throws NoOptionException{
		Field<?> field = null;
		if(annotation instanceof vplibrary.form.annotations.TextField){
			vplibrary.form.annotations.TextField fieldAnnotation = (vplibrary.form.annotations.TextField) annotation;
			field = new TextField(fieldAnnotation.label(), name)
				.setPattern(fieldAnnotation.pattern())
				.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.PasswordField){
			vplibrary.form.annotations.PasswordField fieldAnnotation = (vplibrary.form.annotations.PasswordField) annotation;
			field = new PasswordField(fieldAnnotation.label(), name, fieldAnnotation.hashFunction())
				.setPattern(fieldAnnotation.pattern())
				.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.TextAreaField){
			vplibrary.form.annotations.TextAreaField fieldAnnotation = (vplibrary.form.annotations.TextAreaField) annotation;
			field = new TextAreaField(fieldAnnotation.label(), name)
					.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.DateField){
			vplibrary.form.annotations.DateField fieldAnnotation = (vplibrary.form.annotations.DateField) annotation;
			field = new DateField(fieldAnnotation.label(), name)
					.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.FileField){
			vplibrary.form.annotations.FileField fieldAnnotation = (vplibrary.form.annotations.FileField) annotation;
			field = new FileField(fieldAnnotation.label(), name, fieldAnnotation.destFolder(), fieldAnnotation.extensions())
					.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.EmailField){
			vplibrary.form.annotations.EmailField fieldAnnotation = (vplibrary.form.annotations.EmailField) annotation;
			field = new EmailField(fieldAnnotation.label(), name)
				.setPattern(fieldAnnotation.pattern())
				.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.IntegerField){
			vplibrary.form.annotations.IntegerField fieldAnnotation = (vplibrary.form.annotations.IntegerField) annotation;
			field = new IntegerField(fieldAnnotation.label(), name, fieldAnnotation.min(), fieldAnnotation.max())
				.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.LongField){
			vplibrary.form.annotations.LongField fieldAnnotation = (vplibrary.form.annotations.LongField) annotation;
			field = new LongField(fieldAnnotation.label(), name, fieldAnnotation.min(), fieldAnnotation.max())
				.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.DoubleField){
			vplibrary.form.annotations.DoubleField fieldAnnotation = (vplibrary.form.annotations.DoubleField) annotation;
			field = new DoubleField(fieldAnnotation.label(), name, fieldAnnotation.min(), fieldAnnotation.max())
				.setTooltip(fieldAnnotation.tooltip());
		}else if(annotation instanceof vplibrary.form.annotations.SelectField){
			vplibrary.form.annotations.SelectField fieldAnnotation = (vplibrary.form.annotations.SelectField) annotation;
			Class<?> optionsClass = fieldAnnotation.optionsClass();
			Method m;
			try {
				m = optionsClass.getMethod("getOptions");// From the interface SelectFieldOptions
				ObservableList<Object> options = FXCollections.observableArrayList((List<Object>) m.invoke(null));
				if(options == null) {
					throw new NoOptionException("Aucune option d�finie pour le champ select");
				}

				field = new SelectField(fieldAnnotation.label(), name,options)
						.setTooltip(fieldAnnotation.tooltip());
				
			} catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(annotation instanceof vplibrary.form.annotations.SelectField){
			vplibrary.form.annotations.RelationField fieldAnnotation = (vplibrary.form.annotations.RelationField) annotation;
			Class<?> targetEntity =  fieldAnnotation.targetEntity();
			Method getElements;
			try {
				getElements = targetEntity.getMethod("getElements");
				field = new SelectField("", name, FXCollections.observableArrayList((List<Object>) getElements.invoke(targetEntity.getConstructor().newInstance())))
					.setEditable(fieldAnnotation.editable())
					.setTooltip(fieldAnnotation.tooltip());
				
			} catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return field;
	}
}
