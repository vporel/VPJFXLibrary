package VPLibrary.form.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import VPLibrary.form.SelectFieldOptions;

/**
 * Doit toujours �tre utilis�e avec Field
 * @author VPOREL-DEV
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface SelectField {	
	/**
	 * This class must provide a method called List<Option> getOptions()
	 * @return
	 */
	public Class<? extends SelectFieldOptions> optionsClass();
}
