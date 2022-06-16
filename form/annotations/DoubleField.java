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
public @interface DoubleField {
	public double defaultValue() default 0d;
	
	public double min() default Double.MIN_VALUE;
	public double max() default Double.MAX_VALUE;
	public String errorMessage() default "";
}
