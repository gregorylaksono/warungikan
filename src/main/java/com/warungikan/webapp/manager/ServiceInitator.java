package com.warungikan.webapp.manager;

import com.google.gson.Gson;
import com.warungikan.webapp.servie.IUserService;
import com.warungikan.webapp.servie.UserService;

public class ServiceInitator {
	
	private static  IUserService userService ;
	public static IUserService getUserService(){
		if(userService == null){
			userService = new UserService();
		}
		return userService;
	}
	

}
