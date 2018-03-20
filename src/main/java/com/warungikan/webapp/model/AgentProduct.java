package com.warungikan.webapp.model;

import java.io.Serializable;

public class AgentProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3670859857685271432L;
	private String name;
	private AVAILABILITY availability;
	private String address;
	private String telpNo;
	
	public enum AVAILABILITY{
		PARTLY, FULL, EMPTY
	}

	public AgentProduct(String name, AVAILABILITY availability, String address, String telpNo) {
		this.name = name;
		this.availability = availability;
		this.address = address;
		this.telpNo = telpNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AVAILABILITY getAvailability() {
		return availability;
	}

	public void setAvailability(AVAILABILITY availability) {
		this.availability = availability;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelpNo() {
		return telpNo;
	}

	public void setTelpNo(String telpNo) {
		this.telpNo = telpNo;
	}
	
	
}
