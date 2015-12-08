package com.cetnow.db;

public class BeanExpression {

	private String updateQuery;
	private Object[] data;
	private int[] sqlTypes;
	
	public String getUpdateQuery() {
		return updateQuery;
	}
	public void setUpdateQuery(String updateQuery) {
		this.updateQuery = updateQuery;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	public int[] getSqlTypes() {
		return sqlTypes;
	}
	public void setSqlTypes(int[] sqlTypes) {
		this.sqlTypes = sqlTypes;
	}
	
	
}
