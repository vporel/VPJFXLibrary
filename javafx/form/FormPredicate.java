package vporel.javafx.form;

import java.util.function.Predicate;

/**
 * Ceci est une classe abstraite implémentant l'interface  Predicate
 * Elle permet de définir un message d'erreur qui pourra être affiché si le prédicat est faux
 * Il est important de définir ce message car s'il est null, l'utilisateur ne pourra pas savoir ce qui l'empêche d'avancer
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
