package vplibrary.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import vplibrary.form.SelectFieldOptions;

/**
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface SelectField {	
	public String label();
	
	/**
	 * Texte à afficher pour aider l'utilisateur à comprendre à quoi sert le champ
	 * @return
	 */
	public String tooltip() default "";
	/**
	 * This class must provide a method called List<Option> getOptions()
	 * @return
	 */
	public Class<? extends SelectFieldOptions> optionsClass();
}
