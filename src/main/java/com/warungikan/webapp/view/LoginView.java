package com.warungikan.webapp.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;
import org.warungikan.db.model.User;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.listener._ButtonListener;
import com.warungikan.webapp.listener._IWIButtonListener;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.FishShopItem;
import com.warungikan.webapp.model.ShopItemCart;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.view.admin.AdminTransactionView;
import com.warungikan.webapp.view.admin.AdminUserManagementView;
import com.warungikan.webapp.view.admin.ShopItemView;
import com.warungikan.webapp.view.admin.WalletTransactionView;
import com.warungikan.webapp.view.agent.AgentTransactionStatusView;
import com.warungikan.webapp.view.customer.ConfirmationPageView;
import com.warungikan.webapp.view.customer.MyProfileView;
import com.warungikan.webapp.view.customer.MyTransaction;
import com.warungikan.webapp.view.customer.ShippingAddressView;
import com.warungikan.webapp.view.customer.ShopView;
import com.warungikan.webapp.view.customer.ShoppingCartView;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class LoginView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8076026182734554951L;
	Command logout = new Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			ServiceInitator.getUserService().logout();
		}
	};
	public LoginView() {
		addStyleName("white-background");
		setSizeFull();
		VerticalLayout form = loginForm();
		addComponent(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	}


	private Image createLogo() {
		Image i = new Image();
		i.setWidth(185, Unit.PIXELS);
		i.setSource(new ThemeResource("images/warungikanlogo.png"));
		return i;
	}


	private VerticalLayout loginForm() {
		Image logo = createLogo();
		
		VerticalLayout form = new VerticalLayout();
		form.setSpacing(true);
		TextField usernameTf = new TextField();
		PasswordField passwordTf = new PasswordField();
		
		usernameTf.setInputPrompt("Username");
		passwordTf.setInputPrompt("Password");
		
		usernameTf.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		passwordTf.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		
		Button loginButton = new Button("Login");
		Button regiterButton = new Button("Register");
		
		loginButton.addStyleName(ValoTheme.BUTTON_SMALL);
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		regiterButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(regiterButton);
		buttonLayout.addComponent(loginButton);
		buttonLayout.setWidth(100, Unit.PERCENTAGE);
		buttonLayout.setComponentAlignment(regiterButton, Alignment.MIDDLE_LEFT);
		buttonLayout.setComponentAlignment(loginButton, Alignment.MIDDLE_RIGHT);
		
//		usernameTf.addValidator(new StringLengthValidator("Can not authenticate", 3, 30, false));
//		passwordTf.addValidator(new StringLengthValidator("Can not authenticate", 3, 30, false));
		form.addComponent(logo);
		form.addComponent(usernameTf);
		form.addComponent(passwordTf);
		form.addComponent(buttonLayout);
		form.setMargin(true);
		form.setSizeUndefined();
		
		form.setComponentAlignment(logo, Alignment.BOTTOM_CENTER);
		form.setExpandRatio(logo, 1.0f);
		form.setExpandRatio(usernameTf, 0.0f);
		form.setExpandRatio(passwordTf, 0.0f);
		form.setExpandRatio(buttonLayout, 0.0f);
		
		regiterButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				((MyUI)UI.getCurrent()).getNavigator().navigateTo(Constant.VIEW_REGISTER);
			}
		});
		loginButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				usernameTf.validate();
				passwordTf.validate();
				String text = usernameTf.getValue();
				String password = passwordTf.getValue();
				String jwt = ServiceInitator.getUserService().login(text, password);
				
				if(jwt == null) return;
				parseJwt(jwt);
				String role = ((MyUI)UI.getCurrent()).getRole();
				if(!role.isEmpty() && role.equalsIgnoreCase("admin")) {
					initAdminData();
				}
				else if(!role.isEmpty() && role.equalsIgnoreCase("agent")) {
					initAgentData();
				}
				else {
					initUserData();
				}
				
			}
		});
		return form;
	}

	private void parseJwt(String jwt) {
		Claims result = Jwts.parser()
        .setSigningKey(Constant.SECRET)
        .parseClaimsJws(jwt.replace(Constant.TOKEN_PREFIX, ""))
        .getBody();	
		result.getExpiration();
		List<String> roles =  (List) result.get("ROLE");
		((MyUI)UI.getCurrent()).setRole(roles.get(0).replace("ROLE_", ""));
		((MyUI)UI.getCurrent()).setJwt(jwt);
	}


	private void initAdminData() {
		HorizontalLayout header = createHeader("ADMIN");
		((MyUI)UI.getCurrent()).setHeader(header);
		
		Navigator n = ((MyUI)UI.getCurrent()).getNavigator();
		
		n.addView(Constant.VIEW_MY_PROFILE, MyProfileView.class);
		n.addView(Constant.VIEW_USERS_ADMIN, AdminUserManagementView.class);
		n.addView(Constant.VIEW_USERS_TRANSACTION, AdminTransactionView.class);
		n.addView(Constant.VIEW_WALLET_TRANSACTION, WalletTransactionView.class);
		n.addView(Constant.VIEW_SHOP_ITEM, ShopItemView.class);
		n.navigateTo(Constant.VIEW_USERS_ADMIN);		
	}
	
	private void initAgentData() {
		HorizontalLayout header = createHeader("AGENT");
		((MyUI)UI.getCurrent()).setHeader(header);
		
		Navigator n = ((MyUI)UI.getCurrent()).getNavigator();
		n.addView(Constant.ADMIN_TRX_STATS, AgentTransactionStatusView.class);
		n.addView(Constant.VIEW_MY_PROFILE, MyProfileView.class);
		n.addView(Constant.VIEW_ITEM_STOCK, MyProfileView.class);
		n.navigateTo(Constant.ADMIN_TRX_STATS);
	}


	private void initUserData() {
		HorizontalLayout header = createHeader("USER");
		((MyUI)UI.getCurrent()).setHeader(header);
		
		Navigator n = ((MyUI)UI.getCurrent()).getNavigator();
		n.addView(Constant.VIEW_SHOP, ShopView.class);
		n.addView(Constant.VIEW_CART_DETAIL, ShoppingCartView.class);
		n.addView(Constant.VIEW_AGENT_SHIPMENT, ShippingAddressView.class);
		n.addView(Constant.VIEW_SHOP, ShopView.class);
		n.addView(Constant.VIEW_MY_PROFILE, MyProfileView.class);
		n.addView(Constant.VIEW_CONFIRM_PAGE, ConfirmationPageView.class);
		n.addView(Constant.VIEW_MY_TRANSACTION, MyTransaction.class);
		n.navigateTo(Constant.VIEW_SHOP);
	}

	private HorizontalLayout createHeader(String role) {
		HorizontalLayout header = new HorizontalLayout();
		header.setWidth(100, Unit.PERCENTAGE);
		header.setHeight(70, Unit.PIXELS);
		header.setSpacing(true);
		MenuBar menuBar = getMenuButton(role);
		
		CssLayout buttonContainer = new CssLayout();
		Button shoppingCart = new Button("");
		shoppingCart.setIcon(FontAwesome.SHOPPING_BASKET);
		shoppingCart.addStyleName("fa-shopping-cart");
		shoppingCart.addClickListener( e-> {
					UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_CART_DETAIL);
				});
		shoppingCart.addStyleName("cart-custom");
		shoppingCart.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//		shoppingCart.setIcon(VaadinIcons.CART_O);
		Label cartItem = new Label("2");
		cartItem.setWidth(null);
		cartItem.addStyleName("cincrement-label");
		buttonContainer.addComponent(shoppingCart);
//		buttonContainer.addComponent(cartItem);
		
		header.addComponent(buttonContainer);
		header.addComponent(menuBar);
		header.setComponentAlignment(buttonContainer, Alignment.MIDDLE_RIGHT);
		header.setComponentAlignment( menuBar, Alignment.MIDDLE_RIGHT);
		header.setExpandRatio(buttonContainer, 1.0f);
		header.setExpandRatio(menuBar, 0.0f);
		
		return header;
	}
	
    MenuBar getMenuButton(String role) {
        MenuBar split = new MenuBar();
        if(role.equalsIgnoreCase("ADMIN")){
        	split = generateAdminHeader(split);
        }
        else if(role.equalsIgnoreCase("AGENT")){
        	split = generateAgentHeader(split);
        }else{
        	split = generateCustomerHeader(split);
        }
        return split;
    }

	private MenuBar generateCustomerHeader(MenuBar split) {
		MenuBar.MenuItem dropdown = split.addItem("Profile",  e->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_PROFILE);
		});
        
        dropdown = split.addItem("", null);
        dropdown.addItem("Belanja",  e->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_SHOP);
        });
        dropdown.addItem("Transaksi saya", e->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_TRANSACTION);
        });
        dropdown.addItem("Info", e ->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_PROFILE);
        });
        dropdown.addSeparator();
        dropdown.addItem("Logout", logout);

        split.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        split.addStyleName(ValoTheme.MENUBAR_SMALL);
        return split;
	}
	
	private MenuBar generateAdminHeader(MenuBar split) {
		MenuBar.MenuItem dropdown = split.addItem("Profile",  e->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_PROFILE);
		});
        
        dropdown = split.addItem("", null);
       
        dropdown.addItem("Users management", e->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_USERS_ADMIN);
        });
        dropdown.addItem("Wallet transaction", e ->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_WALLET_TRANSACTION);
        });
        dropdown.addItem("Transaction", e ->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_USERS_TRANSACTION);
        });
        dropdown.addItem("Shop item", e ->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_SHOP_ITEM);
        });
        dropdown.addSeparator();
        dropdown.addItem("Logout", logout);

        split.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        split.addStyleName(ValoTheme.MENUBAR_SMALL);
        return split;
	}	

	private MenuBar generateAgentHeader(MenuBar split) {
		MenuBar.MenuItem dropdown = split.addItem("Profile", e->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_PROFILE);
		});
        
        dropdown = split.addItem("", null);
        
        dropdown.addItem("Transaction", e ->{
        	UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_TRANSACTION);
        });
        dropdown.addSeparator();
        dropdown.addItem("Logout", logout);

        split.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        split.addStyleName(ValoTheme.MENUBAR_SMALL);
        return split;
	}	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
