package vplibrary.form.annotations;

import javafx.collections.ObservableList;

/**
 * Interface pour les classes dont les instances peuvent être les options d'un champ Select
 * @author VPOREL-DEV
 *
 * @param <T>
 */
public interface TargetEntityInterface<T> {
	public ObservableList<T> getElements();
}
