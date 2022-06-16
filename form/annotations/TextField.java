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
public @interface TextField {
	
	public String pattern() default VPLibrary.form.TextField.PATTERN;
	public String defaultValue() default "";
	
	/**
	 * Message to show if the value doesn't match the pattern
	 * @return
	 */
	public String errorMessage() default "";
}
