package com.warungikan.webapp.dialog;

import org.warungikan.db.model.User;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.util.Factory;

public class AgentDetailComponent extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6432415196574537157L;
	private User agent;
	
	public AgentDetailComponent(User agent) {
		setMargin(true);
		setSpacing(true);
		this.agent = agent;
		
		Label name = new Label("<b>"+agent.getName()+"</b>");
		name.setContentMode(ContentMode.HTML);
		Label address = new Label(agent.getAddress());
		Label telpNo = new Label(agent.getTelpNo());
		Label email = new Label(agent.getEmail());
		Button btn = Factory.createButtonOk("Tutup");
		
		addComponent(name);
		addComponent(address);
		addComponent(telpNo);
		addComponent(email);
		addComponent(btn);
		
	}

}
