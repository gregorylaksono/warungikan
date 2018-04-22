package com.warungikan.webapp.listener;

import java.net.NoRouteToHostException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.client.ResourceAccessException;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Notification;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;

public class _ButtonListener implements ClickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4385313436254059737L;
	private _IWIButtonListener buttonListener;
	public _ButtonListener(_IWIButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		doit(event);
	}
	
	public void doit(ClickEvent event){
		try {			
			buttonListener.doIt(event);
		}catch (UserSessionException e) {
			Notification.show("Your login data has been altered", Type.ERROR_MESSAGE);
			Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
			VaadinSession.getCurrent().close();
		}catch(ResourceAccessException e){
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
			Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
			VaadinSession.getCurrent().close();
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
			Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
			VaadinSession.getCurrent().close();
		}
	}

}

