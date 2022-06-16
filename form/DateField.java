package vporel.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateField extends Field<java.util.Date> {

	public DateField(String label, String name) {
		
		super(label, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean test(Date value) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setDefaultValue(String date) {
		if(date.equals("NOW"))
			this.defaultValue = new Date();
		else
			try {
				this.defaultValue = new SimpleDateFormat("dd/MM/yyy").parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
