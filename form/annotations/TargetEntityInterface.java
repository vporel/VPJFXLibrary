package vplibrary.form.annotations;

import javafx.collections.ObservableList;

public interface TargetEntityInterface<T> {
	public ObservableList<T> getElements();
}
