package com.warungikan.webapp.component;

import java.net.NoRouteToHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.warungikan.db.model.Role;
import org.warungikan.db.model.User;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.dialog.CreateUserForm;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Factory;

public class AdminUserTab extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String LAST_LOGIN 		= "last_login";
	private static final String REGISTERED_ON 	= "registered_on";
	private static final String NAME			= "name";
	private static final String EMAIL 			= "user_id";
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
	private Table t;
	
	public AdminUserTab() {
		setSizeFull();
		setSpacing(true);
		setMargin(new MarginInfo(true, false));
		VerticalLayout table = generateTable();
		addComponent(table);
		initDataTable();
	}
	
	

	public void initDataTable(){
		t.removeAllItems();
		List<User> users = null;
		users = ServiceInitator.getUserService().getAllUsers();
		DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		if(users == null)return;
		for(User u : users){
			Item i = t.addItem(u.getId());
			List<Role> roles = (List<Role>) u.getRoles();
			
			Button button = Factory.createButtonOk("Detail");
			button.addClickListener(e ->{
				
			});
			i.getItemProperty(NAME).setValue(u.getName());
			i.getItemProperty(REGISTERED_ON).setValue(f.format(u.getCreationDate()));
			i.getItemProperty(LAST_LOGIN).setValue(u.getLastLogin() != null ? f.format(u.getLastLogin()) : "");
			i.getItemProperty(ROLE).setValue(roles.get(0).getName());
			i.getItemProperty(DETAIL).setValue(button);
			i.getItemProperty(EMAIL).setValue(u.getEmail());
		}
		
	}

	private VerticalLayout generateTable() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		
		Button createAgentButton = new Button("Create agent");
		createAgentButton.addStyleName(ValoTheme.BUTTON_TINY);
		createAgentButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		createAgentButton.addClickListener( e->{
			
		});
		
		t = new Table();
		t.setHeight(500, Unit.PIXELS);
		t.setWidth(100, Unit.PERCENTAGE);
		
		t.addContainerProperty(NAME, String.class, null);
		t.addContainerProperty(REGISTERED_ON, String.class, null);
		t.addContainerProperty(LAST_LOGIN, String.class, null);
		t.addContainerProperty(EMAIL, String.class, null);
		t.addContainerProperty(ROLE, String.class, null, "Role", null, Align.LEFT);
		t.addContainerProperty(DETAIL, Button.class, null, "Detail", null, Align.LEFT);
		
		t.setColumnHeader(LAST_LOGIN, "Last login");
		t.setColumnHeader(REGISTERED_ON, "Register on");
		t.setColumnHeader(NAME, "Name");
		t.setColumnHeader(EMAIL, "Email");
		
		l.addComponent(t);
		l.addComponent(createAgentButton);
		l.setExpandRatio(t, 1.0f);
		l.setExpandRatio(createAgentButton, 0.0f);
		return l;
	}
	
	public void agentWindow(User user){
		Window w = new Window();
		w.setResizable(false);
		w.setModal(true);
		w.setDraggable(false);
		w.setContent(new CreateUserForm(AdminUserTab.this, user));
		UI.getCurrent().addWindow(w);
	}

}
