package com.warungikan.webapp.servie;

import java.util.List;

import org.springframework.web.client.ResourceAccessException;
import org.warungikan.db.model.User;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.manager.UserManagerImpl;

public class UserService implements IUserService{

	private UserManagerImpl userManager = new UserManagerImpl();
	
	public List<User> getAllUsers(){
		try {
			return userManager.getAllUsers();
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.ERROR_MESSAGE);
		}catch(ResourceAccessException e){
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	public String login(String username, String password){
		try {
//			return userManager.login(username, password);
			return userManager.login("greg.laksono@gmail.com", "gregory1234");
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.ERROR_MESSAGE);
		}catch(ResourceAccessException e){
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}


	@Override
	public Boolean createUser(String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password) {
		
		try {
			Integer result = userManager.createUser(name,  email,  telNo,  address,  city,  latitude, longitude, password);
			if(result.intValue() == 200) return true;
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.ERROR_MESSAGE);
		}catch(ResourceAccessException e){
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
			
		}
		return false;
	}

	@Override
	public Boolean updateUser(String name, String email, String telNo, String address, String city, String latitude,
			String longitude) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean checkUserId(String userId) {
		try {
			return userManager.checkUserIdExist(userId);
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.ERROR_MESSAGE);
		}catch(ResourceAccessException e){
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			logout();
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}
	
	public void logout(){
		Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
		VaadinSession.getCurrent().close();
	}

}
