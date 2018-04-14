package com.warungikan.webapp.view.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.component.AdminUserTab;

public class AdminUserManagementView extends VerticalLayout implements View{

	public AdminUserManagementView() {
		addStyleName("product-container");
		setSizeFull();
		setMargin(true);
		setSpacing(true);

		TabSheet tab = addTab();
		addComponent(tab);
	}

	private TabSheet addTab() {
		TabSheet tab = new TabSheet();
		tab.setSizeFull();
		tab.addTab(new AdminUserTab(), "User");
//		tab.addTab(new AdminRoleTab(), "Role");
		return tab;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
