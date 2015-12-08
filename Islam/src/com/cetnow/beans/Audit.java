package com.cetnow.beans;

import java.util.Date;

public class Audit extends BaseBean{
	private int TableFK;
	private int ElementFK;
	private boolean IsUpdate;
	private Date TimeStamp;
	private int UserFK;
	private String JsonData;
	
	public int getTableFK() {
		return TableFK;
	}
	public void setTableFK(int tableFK) {
		TableFK = tableFK;
	}
	public int getElementFK() {
		return ElementFK;
	}
	public void setElementFK(int elementFK) {
		ElementFK = elementFK;
	}
	public boolean isIsUpdate() {
		return IsUpdate;
	}
	public void setIsUpdate(boolean isUpdate) {
		IsUpdate = isUpdate;
	}
	public Date getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		TimeStamp = timeStamp;
	}
	public int getUserFK() {
		return UserFK;
	}
	public void setUserFK(int userFK) {
		UserFK = userFK;
	}
	@Override
	public int tableId() {
		return 1;
	}
	public String getJsonData() {
		return JsonData;
	}
	public void setJsonData(String jsonData) {
		JsonData = jsonData;
	}
	
	
}
