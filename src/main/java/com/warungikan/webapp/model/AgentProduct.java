package com.warungikan.webapp.model;

import java.io.Serializable;

import org.warungikan.db.model.User;

public class AgentProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3670859857685271432L;
	private String km;
	private User user;

	public AgentProduct( String km, User user) {
		this.user = user;
		this.km= km;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
