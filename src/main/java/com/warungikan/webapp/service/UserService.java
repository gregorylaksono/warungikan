package com.warungikan.webapp.service;

import java.util.List;

import org.springframework.web.client.ResourceAccessException;
import org.warungikan.api.model.request.VLatLng;
import org.warungikan.db.model.AgentData;
import org.warungikan.db.model.TopupWalletHistory;
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
	
	public List<User> getAllUsers(String sessionId){
		try {
			return userManager.getAllUsers(sessionId);
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		}catch(ResourceAccessException e){
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	public String login(String username, String password){
		try {
			return userManager.login(username, password);
		}catch (UserSessionException e) {
		}catch(ResourceAccessException e){
			Notification.show("Username / password is wrong", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Username / password is wrong", Type.ERROR_MESSAGE);
		}
		return null;
	}


	@Override
	public Boolean createUserAgent(String sessionId,String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password, String pricePerKm) {
		
		try {
			String result = userManager.createUserAgent(sessionId, name, email, telNo, address, city, latitude, longitude, password, pricePerKm);
			if(result != null) return true;
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		}catch(ResourceAccessException e){
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
			
		}
		return false;
	}

	@Override
	public User getUser(String jwt) {
		try {
			return userManager.getUser(jwt);
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}
	@Override
	public Boolean updateUser(String jwt, String name, String email, String telNo, String address, String city) {
		try {
			int status = userManager.updateUserData(jwt, name, email, telNo, address, city);
			if(status == 202)return true;
			else return false;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	
	public AgentData getAgentData(String jwt){
		try {
			return userManager.getAgentData(jwt);
		} catch (UserSessionException e) {
			logout();
			Notification.show("Can not get agent data", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}
	
	public void logout(){
		Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
		VaadinSession.getCurrent().close();
	}

	@Override
	public Boolean updateCoordinate(String jwt, VLatLng coordinate) {
		try {
			return userManager.updateCoordinate(jwt, coordinate);
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Boolean updateAgentAsAdmin(String jwt, String name, String email, String telNo, String address, String city,
			String latitude, String longitude, String pricePerKm) {
		try {
			int status = userManager.updateAgentAsAdmin(jwt,name,email,telNo,address,city,latitude,longitude,pricePerKm);
			if(status == 202)return true;
			else return false;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Boolean verifyUser(String code) {
		try {
			return	userManager.verify(code);
		} catch (UserSessionException e) {
			return false;
		} catch (WarungIkanNetworkException e) {
			return false;
		}
	}

	@Override
	public Boolean createUserCustomer(String sessionId, String name, String email, String telNo, String address,
			String city, String latitude, String longitude, String password) {
		try {
			return userManager.createUserCustomer(sessionId, name, email, telNo, address, city, latitude, longitude, password);
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return false;
	}

	@Override
	public Boolean changePassword(String jwt, String oldPwd, String newPwd) {
		try {
			return userManager.changePassword(jwt, newPwd, oldPwd);
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return false;
	}

	@Override
	public List<TopupWalletHistory> getTopupHistory(String jwt) {
		try {
			return userManager.getWalletHistoryUser(jwt);
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}


}
