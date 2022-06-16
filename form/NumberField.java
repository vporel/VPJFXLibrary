package vplibrary.form;

public abstract class NumberField<T extends Number> extends Input<T> {
	
	private T min, max;
	public NumberField(String label, String name, T min, T max, T defaultValue) {
		super(label, name);
		setMin(min);
		setMax(max);
		this.setDefaultValue(defaultValue);
	}

	public T getMin() {
		return min;
	}

	public void setMin(T min) {
		this.min = min;
		if(max != null)
			changeErrorMessage(min, max);
	}

	public T getMax() {
		return max;
	}

	public void setMax(T max) {
		this.max = max;
		if(min != null)
			changeErrorMessage(min, max);
	}
	
	public boolean test(T value) {
		return (value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue());
	}
	/**
	 * Structure
	 * if(min > T.MIN_VALUE && max == T.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas sup�rieur � "+min);
		else if(min == T.MIN_VALUE && max <= T.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas inf�rieur � "+max);
		else
			this.setErrorMessage( "Le nombre entr� n'est pas dans l'intervalle ["+min+","+max+"]");
	 * @param min
	 * @param max
	 */
	protected abstract void changeErrorMessage(T min, T max);
	
	
}
