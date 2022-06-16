package VPLibrary.form;

import java.util.List;

public class Option {
	private Object value;
	private String label;
	
	
	
	public Option(Object value, String label) {
		this.value = value;
		this.label = label;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		
		return label;
	}
	
	
	public static Option getFromValue(List<Option> options, Object value) {
		for(Option opt: options) {
			if(opt.getValue().equals(value))
				return opt;
		}
		return null;
	}
	
}
