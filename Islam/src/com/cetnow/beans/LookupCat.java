package com.cetnow.beans;

public class LookupCat extends BaseBean{

	@Override
	public int tableId() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	private String Name;
	private boolean IsEditable;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public boolean isIsEditable() {
		return IsEditable;
	}
	public void setIsEditable(boolean isEditable) {
		IsEditable = isEditable;
	}
	
	

}
