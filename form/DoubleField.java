package vplibrary.form;

public class DoubleField extends NumberField<Double>{
	
	public DoubleField(String label, String name, double min, double max) {
		super(label, name, min, max, 0d);
	}
	
	public DoubleField(String label, String name, double min) {
		this(label, name, min, Double.MAX_VALUE);
	}
	
	public DoubleField(String label, String name) {
		this(label, name, Double.MIN_VALUE);
	}

	@Override
	protected void changeErrorMessage(Double min, Double max) {
		if(min > Double.MIN_VALUE && max == Double.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas sup�rieur ou �gal � "+min);
		else if(min == Double.MIN_VALUE && max <= Double.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas inf�rieur ou egal � "+max);
		else
			this.setErrorMessage( "Le nombre entr� n'est pas dans l'intervalle ["+min+", "+max+"]");
		
	}

	
}
