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
public @interface EmailField {
	public String label();
	
	/**
	 * Texte à afficher pour aider l'utilisateur à comprendre à quoi sert le champ
	 * @return
	 */
	public String tooltip() default "";
	public String pattern() default vplibrary.form.EmailField.PATTERN;
	
	/**
	 * Message to show if the value doesn't match the pattern
	 * @return
	 */
	public String errorMessage() default "";
}
