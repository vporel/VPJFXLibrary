package vporel.javafx.control;

public class DoubleField extends NumberField<Double> {
   
	public DoubleField(double i) {
		super(i);
	}
	public DoubleField() {
		super();
	}
    

   	public Double getValue() {
   		return (this.getText() == null || this.getText().trim() =="") ? 0d : Double.parseDouble(this.getText());
   	}

	@Override
	public boolean acceptsChar(char ch) {
		return ((ch >= '0' && ch <= '9') || ch == '.' &&  !this.getText().contains("."));
	}
}
