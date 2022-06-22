package vplibrary.form.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.HashMap;

import vplibrary.util.Callback;
import vplibrary.util.Predicate;



/**
 * Annotation pour les classes font les instances peuvent être manipulées par vplibrary.form.EntityForm
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface FormUsableEntity {
	public Class<? extends Predicate<HashMap<String, Object>>> validator() default DEFAULT_VALIDATOR.class;
	
	public static abstract class DEFAULT_VALIDATOR extends Predicate<HashMap<String, Object>>{

		public DEFAULT_VALIDATOR(Callback<HashMap<String, Object>, Boolean> testCallback, String errorMessage) {
			super(testCallback, errorMessage);
			// TODO Auto-generated constructor stub
		}} 
}
