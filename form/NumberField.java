package vplibrary.form;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import vplibrary.util.Predicate;

public abstract class NumberField<T extends Number> extends InputField<T> {
	public static String INTERVAL_PREDICATE = "interval";
	private ObjectProperty<T> 
		minProperty = new SimpleObjectProperty<>(), 
		maxProperty = new SimpleObjectProperty<>();
	private Predicate<T> intervalPredicate;
	public NumberField(String label, String name, T minValue, T maxValue) {
		super(label, name);
		intervalPredicate = new Predicate<>(value -> {
			if(value == null)
				return true;
			T min = getMin(), max = getMax();
			if(min == null && max == null) {
				return true;
			}else if(min == null && max != null) {
				return value.doubleValue() <= max.doubleValue();
			}else {
				return value.doubleValue() >= min.doubleValue();
			}
		}, "Interval non respecté");
		addPredicate(INTERVAL_PREDICATE, intervalPredicate);
		minProperty.addListener((opts, oldVal, newVal) -> {
			changeErrorMessage(); 
		});
		maxProperty.addListener((opts, oldVal, newVal) -> {
			changeErrorMessage(); 
		});
		setMin(minValue);
		setMax(maxValue);
	}
	public ObjectProperty<T> minProperty() {
		return minProperty;
	}
	public ObjectProperty<T> maxProperty() {
		return maxProperty;
	}

	public T getMin() {
		return minProperty.getValue();
	}

	public void setMin(T min) {
		this.minProperty.setValue(min);
	}

	public T getMax() {
		return maxProperty.getValue();
	}

	public void setMax(T max) {
		this.maxProperty.setValue(max);
	}
	
	
	/**
	 * Changer le message d'erreur selon les valeurs de min et max
	 */
	private void changeErrorMessage() {
		T min = getMin(), max = getMax();
		if(min != null && max == null)
			intervalPredicate.setMessage("Doit être supérieur à "+min);
		else if(min == null && max != null)
			intervalPredicate.setMessage("Doit être inférieur à "+max);
		else
			intervalPredicate.setMessage("Doit être entre "+min+" et "+max);
	}
	
	
}
