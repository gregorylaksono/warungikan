package com.warungikan.webapp.view.admin;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AdminRoleTab extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800151430117287617L;
	
	public AdminRoleTab(){
		setMargin(new MarginInfo(true, false));
		setSpacing(true);
		
		Table roleTable = createRoleTable();
	}

	private Table createRoleTable() {
		Table roleTable = new Table();
//		roleTable.addContainerProperty(propertyId, type, defaultValue, columnHeader, columnIcon, columnAlignment)
		return null;
	}

}
