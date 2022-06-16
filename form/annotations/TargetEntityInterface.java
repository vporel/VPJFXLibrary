package vporel.form.annotations;

import javafx.collections.ObservableList;

public interface TargetEntityInterface<T> {
	public ObservableList<T> getElements();
}
