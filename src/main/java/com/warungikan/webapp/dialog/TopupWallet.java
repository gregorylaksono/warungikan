package com.warungikan.webapp.dialog;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TopupWallet extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5263086168594525385L;
	
	public TopupWallet() {
		setMargin(true);
		setSpacing(true);
		setSizeUndefined();
		FormLayout f = createForm();
		addComponent(f);
	}

	private FormLayout createForm() {
		FormLayout f = new FormLayout();
		ComboBox b = new ComboBox("User");
		TextField amount = new TextField("Amount");
		TextArea description = new TextArea("Description");
		
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_TINY);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		f.addComponent(b);
		f.addComponent(amount);
		f.addComponent(description);
		f.addComponent(submit);
		return f;
	}

}
