package VPLibrary.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Doit toujours �tre utilis�e avec l'annotation Field
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface RelationField {
	public boolean editable() default false;
	
	/**
	 * targetEntity class
	 * @return
	 */
	public Class<? extends TargetEntityInterface<? extends Object>> targetEntity();
	
}
