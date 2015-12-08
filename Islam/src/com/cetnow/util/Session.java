package com.cetnow.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.cetnow.beans.User;

public class Session {

	@Autowired
	private Html html;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isLogedin(){
		return user!=null && user.getId()>0;
	}
	
	public boolean isAdmin(){
		return isLogedin()&&getUser().isAdmin();
	}
	
}
