package VPLibrary.javafx.dialog;

public class IntegerInputDialog extends NumberInputDialog<Integer>{
	public IntegerInputDialog(int i) {
		super(i);
	}

	@Override
	public Integer convertValue(String val) {
		// TODO Auto-generated method stub
		return Integer.valueOf(val);
	}

}
