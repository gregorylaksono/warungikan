package com.warungikan.webapp.dialog;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Constant;

public class ShopItemForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jwt;
	private IParentWindowService parent;
	
	public ShopItemForm(IParentWindowService p) {
		this.parent = p;
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		setSizeUndefined();
		FormLayout f = createForm();
		
		addComponent(f);
	}

	private FormLayout createForm() {
		FormLayout f = new FormLayout();
		f.setSizeUndefined();
		f.setMargin(true);
		TextField nameF = new TextField("Nama");
		TextField urlF = new TextField("URL");
		TextField priceF = new TextField("Harga");
		TextField weightF = new TextField("Berat");
		TextArea descriptionF = new TextArea("Deskripsi");
		
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_TINY);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		nameF.setRequired(true);
		urlF.setRequired(true);
		priceF.setRequired(true);
		descriptionF.setRequired(true);
		weightF.setRequired(true);
		
		nameF.setValidationVisible(true);
		urlF.setValidationVisible(true);
		priceF.setValidationVisible(true);
		descriptionF.setValidationVisible(true);
		weightF.setValidationVisible(true);
		
		nameF.setImmediate(true);
		urlF.setImmediate(true);
		descriptionF.setImmediate(true);
		priceF.setImmediate(true);
		weightF.setImmediate(true);
		
		
		nameF.addValidator(new StringLengthValidator("Name muss be 3-15 character (was {0}). ", 3, 15, false));
		urlF.addValidator(new StringLengthValidator("URL muss be 5-15 character (was {0}). ", 3, 70, false));
		urlF.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_URL, "URL muss be with format http://.. or https://.." ));
		priceF.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Price muss be numeric"));
		weightF.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Weight muss be numeric"));
		descriptionF.addValidator(new StringLengthValidator("Description muss be 10-200 character (was {0}). ", 10, 200, false));
		
		f.addComponent(nameF);
		f.addComponent(urlF);
		f.addComponent(priceF);
		f.addComponent(weightF);
		f.addComponent(descriptionF);
		f.addComponent(submit);
		
		submit.addClickListener(e->{
			try{
				nameF.validate();
				urlF.validate();
				priceF.validate();
				descriptionF.validate();
				weightF.validate();
				Boolean result = ServiceInitator.getShopItemService().createShopItem(jwt, nameF.getValue(), descriptionF.getValue(), urlF.getValue(), priceF.getValue(), weightF.getValue());
				if(result){
					Notification.show("Item berhasil dimasukan", Type.TRAY_NOTIFICATION);
				}
			}catch(Exception ex){
				Notification.show("Input is not valid", Type.ERROR_MESSAGE);
			}finally{
				((MyUI)UI.getCurrent()).closeWindow();
				parent.update();
			}
		});
		
		return f;
	}

}
