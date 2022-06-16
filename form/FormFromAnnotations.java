package vporel.form;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import vporel.form.annotations.FormUsableEntity;
import vporel.form.annotations.FormUsableEntity.DEFAULT_VALIDATOR;
import vporel.javafx.form.FormPredicate;

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
			if(field.isAnnotationPresent(vporel.form.annotations.Field.class)) {
				Field<?> formField = null;
				if(field.isAnnotationPresent(vporel.form.annotations.TextField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.TextField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.PasswordField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.PasswordField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.EmailField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.EmailField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.IntegerField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.IntegerField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.LongField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.LongField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.DoubleField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.DoubleField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.DateField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.DateField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.TextAreaField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.TextAreaField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.FileField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.FileField.class));
				}else if(field.isAnnotationPresent(vporel.form.annotations.SelectField.class)) {
					try {
						formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.SelectField.class));
					} catch (NoOptionException e) {e.printStackTrace();}
				}else if(field.isAnnotationPresent(vporel.form.annotations.RelationField.class)) {
					formField = FieldFactory.getField(field.getName(), field.getAnnotation(vporel.form.annotations.RelationField.class));
				}
				
				if(formField != null) {
					formField.setLabel(field.getAnnotation(vporel.form.annotations.Field.class).label());
					formField.setTooltip(field.getAnnotation(vporel.form.annotations.Field.class).tooltip());
					//Nullability
					if(field.isAnnotationPresent(Column.class)) {
						formField.setRequired(!field.getAnnotation(Column.class).nullable());
					}else if(field.isAnnotationPresent(JoinColumn.class)) {
						formField.setRequired(!field.getAnnotation(JoinColumn.class).nullable());
					}
					
				}else {
					formField = new Field<Object>(field.getAnnotation(vporel.form.annotations.Field.class).label(), field.getName()) {

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
