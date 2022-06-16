package vplibrary.hibernate;


public class Order{
	
	private boolean ascending;
	private String propertyName;
	
	private Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}
	
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}


	public boolean isAscending() {
		// TODO Auto-generated method stub
		return ascending;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

}
