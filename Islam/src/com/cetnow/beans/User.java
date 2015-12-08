package com.cetnow.beans;

import org.hibernate.validator.constraints.*;

import com.cetnow.db.annotations.*;

public class User extends BaseBean{
	
	@Email()
	@NotBlank()
	@Unique(column = "Email", table = "Users")
	private String Email;
	@NotBlank()
	@Length(min=6,max=50)
	private String Password;
	private String FirstName;
	private String LastName;
	private int Gender;
	private int TypeFK;
	private boolean Admin;
	
	@Override
	public int tableId() {
		return 6;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}

	public int getTypeFK() {
		return TypeFK;
	}

	public void setTypeFK(int typeFK) {
		TypeFK = typeFK;
	}

	public boolean isAdmin() {
		return Admin;
	}

	public void setAdmin(boolean admin) {
		Admin = admin;
	}

	
	
	
	
}