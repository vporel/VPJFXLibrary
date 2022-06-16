package VPLibrary.javafx.dialog;

public class LongInputDialog extends NumberInputDialog<Long>{
	public LongInputDialog(long i) {
		super(i);
	}
	

	@Override
	public Long convertValue(String val) {
		// TODO Auto-generated method stub
		return Long.valueOf(val);
	}
}
