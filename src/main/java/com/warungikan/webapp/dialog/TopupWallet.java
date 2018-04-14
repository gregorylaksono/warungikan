package com.warungikan.webapp.dialog;

import com.vaadin.data.validator.RegexpValidator;import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.util.Constant;

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
		
		b.setNullSelectionAllowed(false);
		
		b.setRequired(true);
		amount.setRequired(true);
		
		b.setImmediate(true);
		amount.setImmediate(true);
		description.setImmediate(true);
		
		amount.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Amount muss be numeric"));
		
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_TINY);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		f.addComponent(b);
		f.addComponent(amount);
		f.addComponent(description);
		f.addComponent(submit);
		
		submit.addClickListener( e->{
			try{
				amount.validate();				
			}catch(Exception ex){
				Notification.show("Input is not valid", Type.ERROR_MESSAGE);
			}
		});
		
		return f;
	}

}
