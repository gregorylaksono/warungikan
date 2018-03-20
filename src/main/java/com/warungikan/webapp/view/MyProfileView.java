package com.warungikan.webapp.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.warungikan.webapp.util.Factory;

public class MyProfileView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8555772573595397967L;

	public MyProfileView() {
		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setWidth(100, Unit.PERCENTAGE);
		bottomLayout.setSpacing(true);
		setSpacing(true);
		setMargin(true);
		setWidth(100, Unit.PERCENTAGE);
		
		VerticalLayout walletLayout = createWalletLayout();
		FormLayout authForm = createAuthForm();
		FormLayout profileForm = createProfileForm();
		
		bottomLayout.addComponent(authForm);
		bottomLayout.addComponent(profileForm);
		bottomLayout.setComponentAlignment(authForm, Alignment.TOP_LEFT);
		bottomLayout.setComponentAlignment(profileForm, Alignment.TOP_RIGHT);
		
		addComponent(walletLayout);
		addComponent(bottomLayout);
	}

	private VerticalLayout createWalletLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout.addStyleName("product-container");
		Label header = Factory.createLabelHeaderNormal("Saldo sekarang : Rp. 450.000");
		Button topupButton = Factory.createButtonOk("Top up saldo");
		
		layout.addComponent(header);
		layout.addComponent(topupButton);
		layout.setExpandRatio(header, 1.0f);
		layout.setExpandRatio(topupButton, 0.0f);
		
		return layout;
	}

	private FormLayout createProfileForm() {
		FormLayout profileForm = new FormLayout();
		profileForm.setMargin(true);
		profileForm.addStyleName("product-container");
		profileForm.setCaption("Data profile anda");
		TextField name = new TextField("Nama");
		TextArea address = new TextArea("Alamat");
		TextField telpNo = new TextField("No. Telp");
		TextField email = new TextField("Email");
		Button updateProfile = Factory.createButtonOk("Update"); 

		profileForm.addComponent(name);
		profileForm.addComponent(address);
		profileForm.addComponent(telpNo);
		profileForm.addComponent(email);
		profileForm.addComponent(name);
		profileForm.addComponent(updateProfile);
		return profileForm;
	}

	private FormLayout createAuthForm() {
		FormLayout authForm = new FormLayout();
		authForm.setMargin(true);
		authForm.addStyleName("product-container");
		authForm.setCaption("Ubah password");
		TextField username = new TextField("Username");
		PasswordField pwdField = new PasswordField("Password");
		PasswordField cnfPwdField = new PasswordField("Password confimation");
		Button authPwdButton = Factory.createButtonOk("Update");
		authForm.addComponent(username);
		authForm.addComponent(pwdField);
		authForm.addComponent(cnfPwdField);
		authForm.addComponent(authPwdButton);
		return authForm;
	}

	@Override
	public void enter(ViewChangeEvent event) {

		
	}
}
