package VPLibrary.javafx.dialog;

public class DoubleInputDialog extends NumberInputDialog<Double>{
	public DoubleInputDialog(double i) {
		super(i);
	}
	

	@Override
	public Double convertValue(String val) {
		// TODO Auto-generated method stub
		return Double.valueOf(val);
	}

	@Override
	public boolean acceptsChar(char ch) {
		return ((ch >= '0' && ch <= '9') || ch == '.' &&  !this.getEditor().getText().contains("."));
	}
}
