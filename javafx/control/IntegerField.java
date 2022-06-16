package VPLibrary.javafx.control;


public class IntegerField extends NumberField<Integer>{
   
   
    public IntegerField(int i) {
		super(i);
	}
    public IntegerField() {
		super();
	}

    public Integer getValue() {
	   return (this.getText() == null || this.getText().trim() =="") ? 0 : Integer.parseInt(this.getText());
   }
}
