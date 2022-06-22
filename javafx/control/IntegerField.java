package vplibrary.javafx.control;


public class IntegerField extends NumberField<Integer>{
   
   
    public IntegerField(int i) {
		super(i);
	}
    public IntegerField() {
		super();
	}

    protected Integer castString(String str) {
    	return Integer.parseInt(str);
    }
}
