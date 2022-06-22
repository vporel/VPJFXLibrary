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
public @interface DateField {
	public String label();
	
	/**
	 * Texte à afficher pour aider l'utilisateur à comprendre à quoi sert le champ
	 * @return
	 */
	public String tooltip() default "";
	/**
	 * If you want to use the current date, you can put NOW
	 * @return
	 */
	public String defaultValue() default "";
}
