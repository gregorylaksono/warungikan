package com.warungikan.webapp.ws.request;

import java.io.Serializable;

public class Login implements Serializable{

	private String username;
	private String password;
	
	public Login(){}
	
	public Login(String username, String password){
		setUsername(username);
		setPassword(password);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
