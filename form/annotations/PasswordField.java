package vplibrary.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface PasswordField {
	public String label();
	
	/**
	 * Texte � afficher pour aider l'utilisateur � comprendre � quoi sert le champ
	 * @return
	 */
	public String tooltip() default "";
	public String hashFunction() default "";
	
	public String pattern() default vplibrary.form.TextField.PATTERN;
	
	/**
	 * Message to show if the value doesn't match the pattern
	 * @return
	 */
	public String errorMessage() default "";
}
