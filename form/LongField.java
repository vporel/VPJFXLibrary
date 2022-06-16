package vplibrary.form;


public class LongField extends NumberField<Long>{

	public LongField(String label, String name, long min, long max) {
		super(label, name, min, max, 0l);
	}
	
	public LongField(String label, String name, long min) {
		this(label, name, min, Long.MAX_VALUE);
	}
	
	public LongField(String label, String name) {
		this(label, name, Long.MIN_VALUE);
	}

	@Override
	protected void changeErrorMessage(Long min, Long max) {
		// TODO Auto-generated method stub
		if(min > Long.MIN_VALUE && max == Long.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas sup�rieur � "+min);
		else if(min == Long.MIN_VALUE && max <= Long.MAX_VALUE)
			this.setErrorMessage( "Le nombre entr� n'est pas inf�rieur � "+max);
		else
			this.setErrorMessage( "Le nombre entr� n'est pas dans l'intervalle ["+min+","+max+"]");
	}

}
