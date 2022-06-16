package vplibrary.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Cette annotation doit toujours �tre utilis� avec une autre d�finissant le v�ritable champ
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Field {
	public String label();
	
	/**
	 * Texte � afficher pour aider l'utilisateur � comprendre � quoi sert le champ
	 * @return
	 */
	public String tooltip() default "";
}
