package VPLibrary.form.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import VPLibrary.javafx.form.FormPredicate;

@Retention(RUNTIME)
@Target(TYPE)
public @interface FormUsableEntity {
	public Class<? extends FormPredicate<?>> validator() default DEFAULT_VALIDATOR.class;
	
	public static abstract class DEFAULT_VALIDATOR extends FormPredicate<Object>{} 
}
