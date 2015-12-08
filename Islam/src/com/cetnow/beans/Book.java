package com.cetnow.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.constraints.Size;

import org.springframework.jdbc.core.RowMapper;

public class Book {

	private int Id;
	 @Size(min=5, max=30)
	private String Name;
	private String Cat;
	private String ArName;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCat() {
		return Cat;
	}
	public void setCat(String cat) {
		Cat = cat;
	}
	public String getArName() {
		return ArName;
	}
	public void setArName(String arName) {
		ArName = arName;
	}
	
	
}

