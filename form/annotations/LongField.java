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
public @interface LongField {

	public long defaultValue() default 0l;

	public long min() default Long.MIN_VALUE;
	public long max() default Long.MAX_VALUE;
	public String errorMessage() default "";
}
