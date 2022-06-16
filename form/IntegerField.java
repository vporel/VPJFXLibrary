package vplibrary.form;


public class IntegerField extends NumberField<Integer>{

	public IntegerField(String label, String name, int min, int max) {
		super(label, name, min, max, 0);
	}
	
	public IntegerField(String label, String name, int min) {
		this(label, name, min, Integer.MAX_VALUE);
	}
	
	public IntegerField(String label, String name) {
		this(label, name, Integer.MIN_VALUE);
	}

	@Override
	protected void changeErrorMessage(Integer min, Integer max) {
		if(min > Integer.MIN_VALUE && max == Integer.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas sup�rieur � "+min);
		else if(min == Integer.MIN_VALUE && max <= Integer.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas inf�rieur � "+max);
		else
			this.setErrorMessage( "Le nombre entr� n'est pas dans l'intervalle ["+min+","+max+"]");
	}
	
}
