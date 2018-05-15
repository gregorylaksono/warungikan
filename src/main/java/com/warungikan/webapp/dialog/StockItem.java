package com.warungikan.webapp.dialog;

import java.util.Date;
import java.util.List;

import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.ShopItemStock;
import org.warungikan.db.model.TopupWalletHistory;
import org.warungikan.db.model.User;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Util;

public class StockItem extends VerticalLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5263086168594525385L;
	private ShopItemStock t;
	private String jwt;
	private ComboBox comboboxItem;
	private IParentWindowService parent;
	
	public StockItem(ShopItemStock t, IParentWindowService parent) {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		this.t = t;
		this.parent = parent;
		
		setMargin(true);
		setSpacing(true);
		setSizeUndefined();
		FormLayout f = createForm();
		addComponent(f);
		initData();
	}

	private void initData() {
		List<ShopItem> items = ServiceInitator.getShopItemService().getAllShopItem(jwt);
		for(ShopItem u: items){
			comboboxItem.addItem(u.getId());
			comboboxItem.setItemCaption(u.getId(), u.getName());
		}
		
		if(t!=null) {
			comboboxItem.select(t.getItem().getId());
			comboboxItem.setReadOnly(true);
		}
		
	}

	private FormLayout createForm() {
		FormLayout f = new FormLayout();
		
		comboboxItem = new ComboBox("Item");
		TextField amountField = new TextField("Amount");

		comboboxItem.setNullSelectionAllowed(false);
		
		comboboxItem.setRequired(true);
		amountField.setRequired(true);
		
		comboboxItem.setImmediate(true);
		amountField.setImmediate(true);
		
		amountField.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Amount muss be numeric"));
		
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_TINY);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		f.addComponent(comboboxItem);
		f.addComponent(amountField);
		f.addComponent(submit);
		
		submit.addClickListener( new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					try{
						amountField.validate();
						String shopid = String.valueOf(comboboxItem.getValue());
						String amount = amountField.getValue();
						Boolean	result = ServiceInitator.getShopItemService().addStock(jwt, shopid, Integer.parseInt(amount));
						if(result){
							Notification.show("Update stock berhasil dilakukan", Type.TRAY_NOTIFICATION);
							((MyUI)UI.getCurrent()).closeWindow();
						}else{
							Notification.show("Update stock gagal dilakukan", Type.ERROR_MESSAGE);
						}
						parent.update();
					}catch(Exception ex){
						Notification.show("Input tidak valid", Type.ERROR_MESSAGE);
					}
	
				}
		
			});
		
		return f;
	}
}
