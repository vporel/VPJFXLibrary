package vporel.javafx.form;

import java.util.function.Predicate;

/**
 * Ceci est une classe abstraite impl�mentant l'interface  Predicate
 * Elle permet de d�finir un message d'erreur qui pourra �tre affich� si le pr�dicat est faux
 * Il est important de d�finir ce message car s'il est null, l'utilisateur ne pourra pas savoir ce qui l'emp�che d'avancer
 * @author VPOREL-DEV
 *
 * @param <Entity>
 */
public abstract class FormPredicate<T> implements Predicate<T>{
	private String errorMessage = "";
	
	public void setErrorMessage(String msg) {
		errorMessage = msg;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
