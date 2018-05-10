package com.warungikan.webapp.manager;

import com.google.gson.Gson;
import com.warungikan.webapp.service.ITransactionService;
import com.warungikan.webapp.service.IUserService;
import com.warungikan.webapp.service.TransactionService;
import com.warungikan.webapp.service.UserService;

public class ServiceInitator {
	
	private static  IUserService userService ;
	private static  ITransactionService transactionService ;
	public static IUserService getUserService(){
		if(userService == null){
			userService = new UserService();
		}
		return userService;
	}
	
	public static ITransactionService getTransactionService(){
		if(transactionService == null){
			transactionService = new TransactionService();
		}
		return transactionService;
	}
	

}
