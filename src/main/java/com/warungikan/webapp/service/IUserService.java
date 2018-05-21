package com.warungikan.webapp.service;

import java.net.NoRouteToHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.warungikan.api.model.request.VLatLng;
import org.warungikan.db.model.AgentData;
import org.warungikan.db.model.User;

import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.vaadin.ui.PasswordField;
import com.warungikan.webapp.exception.UserSessionException;

public interface IUserService {
	public Boolean createUserCustomer(String sessionId,String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password);
	public String login(String username, String password) ;
	public List<User> getAllUsers(String sessionId);
	public void logout();
	public Boolean createUserAgent(String sessionId,String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password, String pricePerKm);
	
	public Boolean updateUser(String jwt, String name, String email, String telNo, String address, String city);
	public Boolean updateAgentAsAdmin(String jwt, String name, String email, String telNo, String address, String city,String latitude,
			String longitude, String pricePerKm);
	public User getUser(String jwt);
	public AgentData getAgentData(String sessionId);
	public Boolean updateCoordinate(String jwt,VLatLng coordinate);
	public Boolean verifyUser(String code);
	public Boolean changePassword(String jwt, String oldPwd, String newPwd);
}
