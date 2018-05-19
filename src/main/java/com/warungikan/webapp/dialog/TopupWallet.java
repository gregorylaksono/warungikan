package com.warungikan.webapp.dialog;

import java.util.Date;
import java.util.List;

import org.warungikan.db.model.TopupWalletHistory;
import org.warungikan.db.model.User;

import com.vaadin.data.Item;
import com.vaadin.data.validator.RegexpValidator;import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
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
import com.warungikan.webapp.util.Util;

public class TopupWallet extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5263086168594525385L;
	private TopupWalletHistory t;
	private String jwt;
	private ComboBox comboboxUser;
	private IParentWindowService parent;
	
	public TopupWallet(TopupWalletHistory t, IParentWindowService parent) {
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
		List<User> users = ServiceInitator.getUserService().getAllUsers(jwt);
		for(User u: users){
			comboboxUser.addItem(u.getEmail());
			comboboxUser.setItemCaption(u.getEmail(), u.getName());
		}
		
	}

	private FormLayout createForm() {
		FormLayout f = new FormLayout();
		
		comboboxUser = new ComboBox("User");
		TextField amountField = new TextField("Amount");
		TextField refNoBankField = new TextField("Ref. bank no");
		DateField creditDateField = new DateField("Uang masuk");
		creditDateField.setWidth(185, Unit.PIXELS);
		comboboxUser.setNullSelectionAllowed(false);
		
		comboboxUser.setRequired(true);
		amountField.setRequired(true);
		creditDateField.setRequired(true);
		refNoBankField.setRequired(true);
		
		comboboxUser.setImmediate(true);
		amountField.setImmediate(true);
		creditDateField.setImmediate(true);
		
		amountField.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Amount muss be numeric"));
		refNoBankField.addValidator(new StringLengthValidator("Reference bank no harus diisi", 5, 30, false));
		creditDateField.setResolution(Resolution.MINUTE);
		creditDateField.setDateFormat("dd-MM-yyyy HH:mm");
		
		Button submit = new Button("Submit");
		submit.addStyleName(ValoTheme.BUTTON_TINY);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		f.addComponent(comboboxUser);
		f.addComponent(amountField);
		f.addComponent(creditDateField);
		f.addComponent(refNoBankField);
		f.addComponent(submit);
		
		submit.addClickListener( e->{
			try{
				amountField.validate();
				String user_id = (String) comboboxUser.getValue();
				String amount = amountField.getValue();
				Date creditOn = creditDateField.getValue();
				String refNoBank = refNoBankField.getValue();
				
				Boolean result = ServiceInitator.getTransactionService().addBalanceUser(jwt, user_id, amount, Util.parseDate(creditOn), refNoBank);
				if(result){
					Notification.show("Topup berhasil dilakukan", Type.TRAY_NOTIFICATION);
				}else{
					Notification.show("Topup gagal dilakukan", Type.ERROR_MESSAGE);
				}
				parent.update();
				((MyUI)UI.getCurrent()).closeWindow();
			}catch(Exception ex){
				Notification.show("Input tidak valid", Type.ERROR_MESSAGE);
			}
		});
		
		return f;
	}

}
