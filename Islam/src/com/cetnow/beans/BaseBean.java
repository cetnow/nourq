package com.cetnow.beans;

public abstract class BaseBean {

	private int Id;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	
	public abstract int tableId();
	
}
