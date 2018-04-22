package com.warungikan.webapp;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.warungikan.webapp.component.MapPage;
import com.warungikan.webapp.dialog.CreateUserForm;
import com.warungikan.webapp.model.ShopItem;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.view.LoginView;
import com.warungikan.webapp.view.customer.ShippingAddressView;
import com.warungikan.webapp.view.customer.ShopView;
import com.warungikan.webapp.view.customer.ShoppingCartView;



/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("warungikan")
@Push
public class MyUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8766267531481479298L;

	private Navigator navigator;
	
	private String jwt;
	
	private String role;
	
	private List<ShopItem> items;
	@Override
	protected void init(VaadinRequest request) {
		setContent(new LoginView());
	
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
    public List<ShopItem> getItems() {
		return items;
	}

	public void setItems(List<ShopItem> items) {
		this.items = items;
	}
	
	public void closeWindow() {
		for(Window w: getWindows()) {
			w.close();
		}
	}
	
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
