package com.warungikan.webapp.dialog;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.util.Constant;

public class ShopItemForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShopItemForm() {
		setSizeUndefined();
		FormLayout f = createForm();
		
		addComponent(f);
	}

	private FormLayout createForm() {
		FormLayout f = new FormLayout();
		f.setSizeUndefined();
		f.setMargin(true);
		TextField name = new TextField("Name");
		TextField url = new TextField("URL");
		TextField price = new TextField("Price");
		TextArea description = new TextArea("Description");
		
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_TINY);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		name.setRequired(true);
		url.setRequired(true);
		price.setRequired(true);
		description.setRequired(true);
		
		name.setValidationVisible(true);
		url.setValidationVisible(true);
		price.setValidationVisible(true);
		description.setValidationVisible(true);
		
		name.setImmediate(true);
		url.setImmediate(true);
		description.setImmediate(true);
		price.setImmediate(true);
		
		
		name.addValidator(new StringLengthValidator("Name muss be 3-15 character (was {0}). ", 3, 15, false));
		url.addValidator(new StringLengthValidator("URL muss be 5-15 character (was {0}). ", 3, 15, false));
		url.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_URL, "URL muss be with format http://.. or https://.." ));
		price.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Price muss be numeric"));
		description.addValidator(new StringLengthValidator("Description muss be 10-200 character (was {0}). ", 10, 200, false));
		
		f.addComponent(name);
		f.addComponent(url);
		f.addComponent(price);
		f.addComponent(description);
		f.addComponent(submit);
		
		submit.addClickListener(e->{
			try{
				name.validate();
				url.validate();
				price.validate();
				description.validate();				
			}catch(Exception ex){
				Notification.show("Input is not valid", Type.ERROR_MESSAGE);
			}
		});
		
		return f;
	}

}
