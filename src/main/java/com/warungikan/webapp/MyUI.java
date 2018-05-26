package com.warungikan.webapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.warungikan.db.model.AgentData;
import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.User;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.warungikan.webapp.component.MapPage;
import com.warungikan.webapp.component.RegisterView;
import com.warungikan.webapp.dialog.CreateUserForm;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.AgentProduct;
import com.warungikan.webapp.model.ShopItemCart;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;
import com.warungikan.webapp.view.LoginView;
import com.warungikan.webapp.view.VerificationView;
import com.warungikan.webapp.view.customer.ShippingAddressView;
import com.warungikan.webapp.view.customer.ShopView;
import com.warungikan.webapp.view.customer.ShoppingCartView;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;



/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("warungikan")
@Push
@PreserveOnRefresh
public class MyUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8766267531481479298L;

	private Navigator navigator;
	
	private String jwt;
	
	private String role;
	
	private List<ShopItem> items;

	private List<ShopItemCart> cartItems;
	
	private AgentProduct agentProduct;

	private VerticalLayout navigatorContent;

	private HorizontalLayout header;

	private VerticalLayout root;

	private Label cartNumberNotifLabel;
	
	private Button buttonBalance;

	@Override
	protected void init(VaadinRequest request) {
		root = new VerticalLayout();
		navigatorContent = new VerticalLayout();
		navigatorContent.setSizeFull();
		header = new HorizontalLayout();
		
		root.addComponent(header);
		root.addComponent(navigatorContent);
		root.setExpandRatio(header, 0.0f);
		root.setExpandRatio(navigatorContent, 1.0f);
		root.setComponentAlignment(header, Alignment.TOP_RIGHT);
		root.setComponentAlignment(navigatorContent, Alignment.BOTTOM_CENTER);
		setContent(root);
		
		Navigator n = new Navigator(this, navigatorContent);

		n.addView(Constant.VIEW_CONFIRM_USER_PAGE, VerificationView.class);
		n.addView(Constant.VIEW_LOGIN, LoginView.class);
		n.addView(Constant.VIEW_REGISTER, RegisterView.class);
		setNavigator(n);
		
		
		cartNumberNotifLabel = new Label();
		cartNumberNotifLabel.setWidth(null);
		cartNumberNotifLabel.addStyleName("cincrement-label");
		
//		initLoggingConfiguration();
	}

	private void initLoggingConfiguration() {
		Properties preferences = new Properties();
		try {
		    FileInputStream configFile = (FileInputStream) MyUI.class.getClassLoader().getResourceAsStream("application.properties");
		    preferences.load(configFile);
		    LogManager.getLogManager().readConfiguration(configFile);
		    
		} catch (IOException ex)
		{
		    System.out.println("WARNING: Could not open configuration file");
		    System.out.println("WARNING: Logging not configured (console output only)");
		}		
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

	public void setItems(List<ShopItem> items2) {
		this.items = items2;
	}
	
	public void closeWindow() {
		for(Window w: getWindows()) {
			w.close();
		}
	}
	
	

	public VerticalLayout getNavigatorContent() {
		return navigatorContent;
	}

	public void setNavigatorContent(VerticalLayout navigatorContent) {
		this.navigatorContent = navigatorContent;
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
	
	@WebListener
	public static class MyLogbackConfigListener extends LogbackConfigListener {
	}
	public void setItemsCart(List<ShopItemCart> items2) {
		this.cartItems = items2;
	}
	
	public List<ShopItemCart> getItemsCart(){
		return this.cartItems;
	}

	public AgentProduct getAgentProduct() {
		return agentProduct;
	}

	public void setAgentProduct(AgentProduct agentProduct) {
		this.agentProduct = agentProduct;
	}

	public HorizontalLayout getHeader() {
		return header;
	}

	public void setHeader(HorizontalLayout header) {
		this.header = header;
		root.removeComponent(this.header);
		root.addComponent(header, 0);
	}

	public void clearShopingCart() {
		getItemsCart().clear();
	}

	public Label getCartNumberNotifLabel() {
		return cartNumberNotifLabel;
	}

	public void updateNotifLabel() {
		int cartItemNum = 0;
		for(ShopItemCart c: cartItems){
			cartItemNum = c.getCount() + cartItemNum;
		}
		if(cartItemNum < 1){
			cartNumberNotifLabel.setVisible(false);
		}else{
			cartNumberNotifLabel.setVisible(true);
		}
		cartNumberNotifLabel.setValue(String.valueOf(cartItemNum));
		cartNumberNotifLabel.markAsDirty();
	}

	public Button updateBalance() {
		if(buttonBalance == null){
			buttonBalance = Factory.createButtonBorderless("");
		}
		buttonBalance.addClickListener(e->{
    		Navigator n = ((MyUI)UI.getCurrent()).getNavigator();
    		n.navigateTo(Constant.VIEW_MY_PROFILE);
    	});
		Long balance = ServiceInitator.getTransactionService().getBalanceCustomer(jwt);
		buttonBalance.setCaption("Saldo anda Rp. "+Util.formatLocalAmount(balance));
		buttonBalance.markAsDirty();
		return buttonBalance;
	}
	
}
