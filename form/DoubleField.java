package vplibrary.form;

public class DoubleField extends NumberField<Double>{
	
	public DoubleField(String label, String name, Double min, Double max) {
		super(label, name, min, max);
	}
	
	public DoubleField(String label, String name, Double min) {
		this(label, name, min, null);
	}
	
	public DoubleField(String label, String name) {
		this(label, name, null);
	}


	
}
