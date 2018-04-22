package com.warungikan.webapp.servie;

import java.net.NoRouteToHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.warungikan.db.model.User;

import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.exception.UserSessionException;

public interface IUserService {

	public String login(String username, String password) ;
	public List<User> getAllUsers();
	public void logout();
	public Boolean checkUserId(String userId);
	public Boolean createUser(String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password);
	
	public Boolean updateUser(String name, String email, String telNo, String address, String city, String latitude,
			String longitude);
}
