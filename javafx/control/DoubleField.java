package vplibrary.javafx.control;

public class DoubleField extends NumberField<Double> {
   
	public DoubleField(double i) {
		super(i);
	}
	public DoubleField() {
		super();
	}

	@Override
	public boolean acceptsChar(char ch) {
		return ((ch >= '0' && ch <= '9') || ch == '.' &&  !this.getText().contains("."));
	}
	protected Double castString(String str) {
    	return Double.parseDouble(str);
    }
}
