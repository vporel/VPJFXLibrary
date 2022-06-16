package vporel.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Doit toujours être utilisée avec l'annotation Field
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface IntegerField {
	public int defaultValue() default 0;
	public int min() default Integer.MIN_VALUE;
	public int max() default Integer.MAX_VALUE;
	public String errorMessage() default "";
}
