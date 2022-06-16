package vplibrary.form;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import vplibrary.form.annotations.FormUsableEntity;
import vplibrary.form.annotations.FormUsableEntity.DEFAULT_VALIDATOR;
import vplibrary.javafx.form.FormPredicate;

public class FormFromAnnotations<Entity> extends Form<Entity>{
	private FormPredicate<Entity> validator;
	public FormFromAnnotations(Entity object) throws NullFormObjectException {
		super(object);
	}
	
	public FormFromAnnotations(Class entityClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super(entityClass);
	}

	@Override
	public void build() {
		if(this.object.getClass().isAnnotationPresent(FormUsableEntity.class)) {
			try {
				Class<?> validatorClass = this.object.getClass().getAnnotation(FormUsableEntity.class).validator();
				
				if(validatorClass != DEFAULT_VALIDATOR.class)
					validator = (FormPredicate<Entity>) validatorClass.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(java.lang.reflect.Field field: this.object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
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
				
				if(formField != null) {
					formField.setLabel(field.getAnnotation(vplibrary.form.annotations.Field.class).label());
					formField.setTooltip(field.getAnnotation(vplibrary.form.annotations.Field.class).tooltip());
					//Nullability
					if(field.isAnnotationPresent(Column.class)) {
						formField.setRequired(!field.getAnnotation(Column.class).nullable());
					}else if(field.isAnnotationPresent(JoinColumn.class)) {
						formField.setRequired(!field.getAnnotation(JoinColumn.class).nullable());
					}
					
				}else {
					formField = new Field<Object>(field.getAnnotation(vplibrary.form.annotations.Field.class).label(), field.getName()) {

						@Override
						public boolean test(Object value) {
							// TODO Auto-generated method stub
							return true;
						}
						
					};
				}
				this.addField(formField);
			}
		}
	}

	@Override
	public boolean isValid(Entity testObject) {
		errorMessage = "";
		if(validator != null && !validator.test(testObject)) {
			errorMessage = validator.getErrorMessage();
			return false;
		}
		return true;
	}
	
}
