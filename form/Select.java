package VPLibrary.form;

import java.util.List;

public class Select extends Field<Object> {
	
	private List<Object> options;
	private boolean editable;

	public Select(String label, String name, List<Object> options) {
		super(label, name);
		this.options = options;
		errorMessage = "Selectionnez un �lement";
	}
	
	public List<Object> getOptions(){
		return options;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public boolean test(Object value) {
		// TODO Auto-generated method stub
		return (!required || value != null);
	}

}
