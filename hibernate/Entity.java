package vplibrary.hibernate;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import vplibrary.form.Field;
import vplibrary.form.FieldFactory;
import vplibrary.form.NoOptionException;

@MappedSuperclass
public abstract class Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	} 
	/*
	public static ArrayList<Field<?>> getFields(Class<? extends Entity> entityClass){
		ArrayList<Field<?>> fields = new ArrayList<>();
		for(java.lang.reflect.Field field: entityClass.getDeclaredFields()) {
			field.setAccessible(true);
			String name = field.getName(),
					label = name;
			if(field.isAnnotationPresent(vplibrary.form.annotations.Field.class)) {
				Field<?> formField = null;
				if(field.isAnnotationPresent(vplibrary.form.annotations.TextField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.TextField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.PasswordField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.PasswordField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.EmailField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.EmailField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.IntegerField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.IntegerField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.LongField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.LongField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.DoubleField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.DoubleField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.DateField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.DateField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.TextAreaField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.TextAreaField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.FileField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.FileField.class));
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.SelectField.class)) {
					try {
						formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.SelectField.class));
					} catch (NoOptionException e) {e.printStackTrace();}
				}else if(field.isAnnotationPresent(vplibrary.form.annotations.RelationField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vplibrary.form.annotations.RelationField.class));
				}
				
				if(formField == null) {
					 if(field.isAnnotationPresent(vplibrary.form.annotations.Field.class)) {
						formField = new Field<Object>(field.getAnnotation(vplibrary.form.annotations.Field.class).label(), field.getName()) {

							@Override
							public boolean test(Object value) {
								// TODO Auto-generated method stub
								return true;
							}
							
						};
					}else {
						formField = new Field<Object>(field.getName(), field.getName()) {
							@Override
							public boolean test(Object value) {
								// TODO Auto-generated method stub
								return true;
							}
							
						};
					}
				}
				
				if(formField != null) {
					formField.setLabel(field.getAnnotation(vplibrary.form.annotations.Field.class).label());
					formField.setTooltip(field.getAnnotation(vplibrary.form.annotations.Field.class).tooltip());
					//Nullability
					if(field.isAnnotationPresent(Column.class)) {
						formField.setRequired(!field.getAnnotation(Column.class).nullable());
					}else if(field.isAnnotationPresent(JoinColumn.class)) {
						formField.setRequired(!field.getAnnotation(JoinColumn.class).nullable());
					}
					
				}
				this.addField(formField);
			}
		}
		return fields;
	}*/
	
}
