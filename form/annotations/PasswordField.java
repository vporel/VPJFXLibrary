package vporel.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Doit toujours être utilisée avec l'annotation Field
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface PasswordField {
	
	public String hashFunction() default "";
	
	public String pattern() default vporel.form.TextField.PATTERN;
	
	/**
	 * Message to show if the value doesn't match the pattern
	 * @return
	 */
	public String errorMessage() default "";
}
