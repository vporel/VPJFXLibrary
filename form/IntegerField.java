package vplibrary.form;


public class IntegerField extends NumberField<Integer>{

	public IntegerField(String label, String name, Integer min, Integer max) {
		super(label, name, min, max);
	}
	
	public IntegerField(String label, String name, Integer min) {
		this(label, name, min, null);
	}
	
	public IntegerField(String label, String name) {
		this(label, name, null);
	}
	
}
