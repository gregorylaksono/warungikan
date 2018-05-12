package com.warungikan.webapp.model;

import java.io.Serializable;

import org.warungikan.db.model.User;

public class AgentProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3670859857685271432L;
	private Long distance;
	private String price_per_km;
	private User user;

	public AgentProduct( Long km,String price_per_km, User user) {
		this.user = user;
		this.distance= km;
		this.price_per_km = price_per_km;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPricePerKM() {
		return price_per_km;
	}

	public void setPricePerKM(String price_per_km) {
		this.price_per_km = price_per_km;
	}


}
