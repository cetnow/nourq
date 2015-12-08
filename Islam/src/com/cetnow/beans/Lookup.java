package com.cetnow.beans;

public class Lookup extends BaseBean{

	@Override
	public int tableId() {
		return 4;
	}
	
	private String Name;
	private int CatFK;
	private boolean IsApproved;
	private boolean IsDeleted;
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getCatFK() {
		return CatFK;
	}
	public void setCatFK(int catFK) {
		CatFK = catFK;
	}
	public boolean isIsApproved() {
		return IsApproved;
	}
	public void setIsApproved(boolean isApproved) {
		IsApproved = isApproved;
	}
	public boolean isIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}
	
	
	
	

}
