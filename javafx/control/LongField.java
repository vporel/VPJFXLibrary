package vplibrary.javafx.control;

public class LongField extends NumberField<Long> {
   
   public LongField(long val) {
		super(val);
		// TODO Auto-generated constructor stub
	}
   
   public LongField() {
		super();
	}
   
   public Long getValue() {
	   return (this.getText() == null || this.getText().trim() =="") ? 0l : Long.parseLong(this.getText());
   }
}
