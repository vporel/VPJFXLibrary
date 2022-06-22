package vplibrary.form;


public class LongField extends NumberField<Long>{

	public LongField(String label, String name, Long min, Long max) {
		super(label, name, min, max);
	}
	
	public LongField(String label, String name, Long min) {
		this(label, name, min, null);
	}
	
	public LongField(String label, String name) {
		this(label, name, null);
	}

}
