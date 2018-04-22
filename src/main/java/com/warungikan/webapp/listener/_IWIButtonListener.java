package com.warungikan.webapp.listener;

import java.net.NoRouteToHostException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.client.ResourceAccessException;

import com.vaadin.ui.Button.ClickEvent;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;

public interface _IWIButtonListener{

	public void doIt(ClickEvent even) throws UserSessionException,WarungIkanNetworkException;
}
