package vplibrary.javafx.control;

public class LongField extends NumberField<Long> {
   
   public LongField(long val) {
		super(val);
		// TODO Auto-generated constructor stub
	}
   
   public LongField() {
		super();
   }
   
   protected Long castString(String str) {
   		return Long.parseLong(str);
   }
}
