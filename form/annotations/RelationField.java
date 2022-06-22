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
public @interface RelationField{
	public String label();
	
	/**
	 * Texte à afficher pour aider l'utilisateur à comprendre à quoi sert le champ
	 * @return
	 */
	public String tooltip() default "";
	public boolean editable() default false;
	
	/**
	 * targetEntity class
	 * @return
	 */
	public Class<? extends TargetEntityInterface<? extends Object>> targetEntity();
	
}
