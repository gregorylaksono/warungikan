package com.warungikan.webapp.component;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AdminUserTab extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String LAST_LOGIN 		= "last_login";
	private static final String REGISTERED_ON 	= "registered_on";
	private static final String NAME			= "name";
	private static final String USER_ID 		= "user_id";
	private static final String DETAIL 			= "detail";
	private static final String ROLE 			= "role";
	private TextField balanceF;
	private TextField lastLoginF;
	private TextField cityF;
	private TextField addressInfoF;
	private TextField addressF;
	private TextField telNoF;
	private TextField emailF;
	private TextField nameF;
	
	public AdminUserTab() {
		setSizeFull();
		setSpacing(true);
		setMargin(new MarginInfo(true, false));
		VerticalLayout table = generateTable();
		addComponent(table);
	}

	private FormLayout generateForm() {
		FormLayout f = new FormLayout();
		nameF = new TextField("Name");
		emailF = new TextField("Email");
		telNoF = new TextField("Telp no");
		addressF = new TextField("Address");
		addressInfoF = new TextField("Address info");
		cityF = new TextField("City");
		lastLoginF = new TextField("Last login");
		balanceF = new TextField("Balance");
		
		Button viewMap = new Button("View location");
		viewMap.addStyleName(ValoTheme.BUTTON_SMALL);
		viewMap.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED );
		
		
		f.addComponent(nameF);
		f.addComponent(emailF);
		f.addComponent(telNoF);
		f.addComponent(addressF);
		f.addComponent(addressInfoF);
		f.addComponent(cityF);
		f.addComponent(lastLoginF);
		f.addComponent(balanceF);
		f.addComponent(viewMap);
		
		return f;
	}

	private VerticalLayout generateTable() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		
		Button createAgentButton = new Button("Create agent");
		createAgentButton.addStyleName(ValoTheme.BUTTON_TINY);
		createAgentButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		Table t = new Table();
		t.setHeight(500, Unit.PIXELS);
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(USER_ID, String.class, null);
		t.addContainerProperty(NAME, String.class, null);
		t.addContainerProperty(REGISTERED_ON, String.class, null);
		t.addContainerProperty(LAST_LOGIN, String.class, null);
		t.addContainerProperty(ROLE, String.class, null, "Role", null, Align.LEFT);
		t.addContainerProperty(DETAIL, Button.class, null, "Detail", null, Align.LEFT);
		
		t.setColumnHeader(LAST_LOGIN, "Last login");
		t.setColumnHeader(REGISTERED_ON, "Register on");
		t.setColumnHeader(NAME, "Name");
		t.setColumnHeader(USER_ID, "User");
		
		l.addComponent(t);
		l.addComponent(createAgentButton);
		l.setExpandRatio(t, 1.0f);
		l.setExpandRatio(createAgentButton, 0.0f);
		return l;
	}

}
