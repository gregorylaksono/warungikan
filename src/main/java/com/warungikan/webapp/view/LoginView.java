package com.warungikan.webapp.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.RegisterComponent;
import com.warungikan.webapp.model.FishShopItem;
import com.warungikan.webapp.model.ShopItem;
import com.warungikan.webapp.util.Constant;


public class LoginView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8076026182734554951L;
	
	
	public LoginView() {
		setSizeFull();
		VerticalLayout form = loginForm();
		addComponent(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	}


	private VerticalLayout loginForm() {
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
		
		form.addComponent(usernameTf);
		form.addComponent(passwordTf);
		form.addComponent(buttonLayout);
		form.setMargin(true);
		form.setSizeUndefined();
		
		loginButton.addClickListener( e -> {
			initData();
			
		});
		
		regiterButton.addClickListener(e ->{
			UI.getCurrent().setContent(new RegisterComponent());
		});
		return form;
	}


	private void initData() {
		VerticalLayout root = new VerticalLayout();
		VerticalLayout navigatorContent = new VerticalLayout();
		
		navigatorContent.setSizeFull();
		
		HorizontalLayout header = createHeader();
		
		root.addComponent(header);
		root.addComponent(navigatorContent);
		root.setExpandRatio(header, 0.0f);
		root.setExpandRatio(navigatorContent, 1.0f);
		root.setComponentAlignment(header, Alignment.TOP_RIGHT);
		root.setComponentAlignment(navigatorContent, Alignment.BOTTOM_CENTER);
		Navigator n = new Navigator (MyUI.getCurrent(), navigatorContent);
		n.addView(Constant.VIEW_SHOP, ShopView.class);
		n.addView(Constant.VIEW_CART_DETAIL, ShoppingCartView.class);
		n.addView(Constant.VIEW_AGENT_SHIPMENT, ShippingAddressView.class);
		n.addView(Constant.VIEW_SHOP, ShopView.class);
		n.addView(Constant.VIEW_MY_PROFILE, MyProfileView.class);
		n.addView(Constant.VIEW_CONFIRM_PAGE, ConfirmationPageView.class);
		n.addView(Constant.VIEW_MY_TRANSACTION, MyTransaction.class);
		UI.getCurrent().setNavigator(n);
		UI.getCurrent().setContent(root);
		n.navigateTo(Constant.VIEW_SHOP);
	}


	private HorizontalLayout createHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.setWidth(100, Unit.PERCENTAGE);
		header.setHeight(70, Unit.PIXELS);
		header.setSpacing(true);
		MenuBar menuBar = getMenuButton("Profile", false);
		
		List<ShopItem> items = new ArrayList<ShopItem>();
		items.add(new ShopItem(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"),4));
		items.add(new ShopItem(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"),4));
		items.add(new ShopItem(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"),4));
		CssLayout buttonContainer = new CssLayout();
		Button shoppingCart = new Button("");
		shoppingCart.setIcon(FontAwesome.SHOPPING_BASKET);
		shoppingCart.addStyleName("fa-shopping-cart");
		shoppingCart.addClickListener( e-> {
//					((MyUI) UI.getCurrent()).setItems(items);
					UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_CART_DETAIL);
				});
		shoppingCart.addStyleName("cart-custom");
		shoppingCart.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//		shoppingCart.setIcon(VaadinIcons.CART_O);
		Label cartItem = new Label("2");
		cartItem.setWidth(null);
		cartItem.addStyleName("cincrement-label");
		buttonContainer.addComponent(shoppingCart);
		buttonContainer.addComponent(cartItem);
		
		header.addComponent(buttonContainer);
		header.addComponent(menuBar);
		header.setComponentAlignment(buttonContainer, Alignment.MIDDLE_RIGHT);
		header.setComponentAlignment( menuBar, Alignment.MIDDLE_RIGHT);
		header.setExpandRatio(buttonContainer, 1.0f);
		header.setExpandRatio(menuBar, 0.0f);
		
		return header;
	}
	
    MenuBar getMenuButton(String caption, boolean splitButton) {
        MenuBar split = new MenuBar();
        MenuBar.MenuItem dropdown = split.addItem(caption, null);
        if (splitButton) {
            dropdown = split.addItem("", null);
        }
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
        dropdown.addItem("Logout", e->{
        	UI.getCurrent().getSession().close();
        	UI.getCurrent().getPage().setLocation("/");
//            getUI().getPage().setLocation("/");

        });

        split.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        split.addStyleName(ValoTheme.MENUBAR_SMALL);
        return split;
    }

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
