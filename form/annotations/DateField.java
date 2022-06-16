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
public @interface DateField {
	/**
	 * If you want to use the current date, you can put NOW
	 * @return
	 */
	public String defaultValue() default "";
}
