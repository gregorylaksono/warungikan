package com.warungikan.webapp;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinServlet;

public class MySystemMessagesProvider implements SystemMessagesProvider {

	   private CustomizedSystemMessages customizedSystemMessages;

	   /**
	    * Creates a new {@code SystemMessageProvider} with customized system messages.
	    */
	   public MySystemMessagesProvider() {
	      customizedSystemMessages = new CustomizedSystemMessages();
	      customizedSystemMessages.setSessionExpiredNotificationEnabled(false);
	      customizedSystemMessages.setSessionExpiredURL(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());

	   }

	   @Override
	   public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
	      /* Normally here decision has to be made which system messages to provide
	       * depending on information in the systemMessageInfo - for example Locale.
	       * But for the demonstration purposes just return the 
	       * customizedSystemMessages. */
	      return customizedSystemMessages;
	   }
	}