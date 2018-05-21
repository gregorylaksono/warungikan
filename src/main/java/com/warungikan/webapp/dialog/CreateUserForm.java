package com.warungikan.webapp.dialog;

import org.warungikan.db.model.User;

import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.AdminUserTab;
import com.warungikan.webapp.component.MapPage;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.RSAddName;
import com.warungikan.webapp.util.Factory;

public class CreateUserForm extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4563779943640781871L;
	private TextField nameF;
	private TextField emailF;
	private TextField telNoF;
	private TextField addressF;
	private TextField addressInfoF;
	private TextField cityF;
	private TextField pricePerKm;
	//	private TextField balanceF;
	private ClickListener createUserListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			if(!validatFields()) return;
			String name = nameF.getValue();
			String email = emailF.getValue();
			String telNo = telNoF.getValue();
			String address = addressF.getValue();
			String city = cityF.getValue();
//			String password = defPasswordF.getValue();
			String price_per_km = pricePerKm.getValue();
			RSAddName result = map.getResult();
			Integer pricePerKm = null;
			try{
				pricePerKm = Integer.parseInt(price_per_km);
			}catch(Exception e){
				Notification.show("Price per km musti angka", Type.ERROR_MESSAGE);
				return;
			}
			
			Boolean createUserResult = ServiceInitator.getUserService().createUserAgent(((MyUI)UI.getCurrent()).getJwt(),name, email, telNo, address, city, result.getLatitude(), result.getLongitude(),null, price_per_km);
			if(createUserResult){
				((MyUI)UI.getCurrent()).closeWindow();
				Notification.show("Agen berhasil dibuat", Type.TRAY_NOTIFICATION);
			}else{
				Notification.show("Terjadi kesalahan ketika membuat agent", Type.ERROR_MESSAGE);
			}
			adminUserTab.update();
		}
	};
	private ClickListener updateUserListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			if(!validatFields()) return;
			String name = nameF.getValue();
			String email = emailF.getValue();
			String telNo = telNoF.getValue();
			String address = addressF.getValue();
			String city = cityF.getValue();
			RSAddName result = map.getResult();
			String price_per_km = pricePerKm.getValue();
			Integer pricePerKm = null;
			try{
				pricePerKm = Integer.parseInt(price_per_km);
			}catch(Exception e){
				Notification.show("Price per km musti angka", Type.ERROR_MESSAGE);
				return;
			}
			Boolean createUserResult = ServiceInitator.getUserService().updateAgentAsAdmin(((MyUI)UI.getCurrent()).getJwt(), name, email, telNo, address, city, result.getLatitude(), result.getLongitude(), price_per_km);
			if(createUserResult){
				((MyUI)UI.getCurrent()).closeWindow();
				Notification.show("Data berhasil diubah", Type.TRAY_NOTIFICATION);
			}else{
				Notification.show("Terjadi kesalahan saat mengubah data", Type.ERROR_MESSAGE);
			}
			adminUserTab.update();
		}
	};
	private MapPage map;
//	private PasswordField defPasswordF;
//	private PasswordField defPasswordConfF;
	private IParentWindowService adminUserTab;
	private User user;
	private Button createUser;

	public CreateUserForm(IParentWindowService adminUserTab, User user) {
		this.user = user;
		setSpacing(true);
		setMargin(true);
		this.adminUserTab = adminUserTab;
		VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.setSpacing(true);
		createUser = Factory.createButtonOk("Save user");
		
		FormLayout f = createContent();
		map = new MapPage("Tentukan letak alamat", true, null);
		
		addComponent(f);

		rightLayout.addComponent(map);
		rightLayout.addComponent(createUser);
		
		addComponent(rightLayout);
		createUser.addClickListener(createUserListener);
		initModeWindow();
	}

	private void initModeWindow() {
		//Update mode
		if(user != null) {
			if(user.getId() != null){
				nameF.setValue(user.getName());
				emailF.setValue(user.getEmail());
				telNoF.setValue(user.getTelpNo());
				addressF.setValue(user.getAddress());
				if(user.getAddressInfo()!=null){
					user.setAddressInfo(user.getAddressInfo());  
				}else{
					user.setAddressInfo("");
				}
				cityF.setValue(user.getCity());
//				this.removeComponent(defPasswordF);
//				this.removeComponent(defPasswordConfF);
				
				map.setCoordinate(user.getName(), user.getLatitude(), user.getLongitude());
				createUser.addClickListener(updateUserListener);
			}
		}
		
	}

	private boolean validatFields() {
		nameF.validate();
		emailF.validate();
		telNoF.validate();
		addressF.validate();
		cityF.validate();
//		defPasswordF.validate();
//		defPasswordConfF.validate();
		pricePerKm.validate();
		map.validate();
		
//		if(!defPasswordConfF.getValue().equals(defPasswordF.getValue())){
//			Notification.show("Password tidak sesuai", Type.ERROR_MESSAGE);
//			return false;
//		}
		return true;
	}

	private FormLayout createContent() {
		FormLayout f = new FormLayout();
		nameF = Factory.createFormatedTextField("Name", 3, 30, "Nama tidak sesuai format. Min 3 max 30" );
		emailF =  Factory.createFormatedTextField("Email", 3, 50, "Email tidak sesuai format. Min 3 max 50");
		telNoF =  Factory.createFormatedTextField("Telp no",5, 50, "Telp no tidak sesuai format. Min 5 max 50" );
		addressF =  Factory.createFormatedTextField("Address",5,50, "Alamat tidak sesuai format. Min 5 max 50");
		addressInfoF =  Factory.createStandardTextField("Address info");
		cityF =  Factory.createFormatedTextField("City", 5,20, "Kota tidak sesuai format. Min 3 max 20");
		pricePerKm = Factory.createStandardTextField("Price per km");
//		defPasswordF = Factory.createPasswordField("Default password", 3, 20, "Password tidak sesuai format. Min 3 max 20");
//		defPasswordConfF = Factory.createPasswordField("Konfirmasi password", 3, 20, "Password tidak sesuai format. Min 3 max 20");
		
//		pricePerKm.addValidator(new IntegerRangeValidator("Angka tidak sesuai", 100, 10000));
		f.setSizeUndefined();
		

		f.addComponent(nameF);
		f.addComponent(emailF);
		f.addComponent(telNoF);
		f.addComponent(addressF);
		f.addComponent(addressInfoF);
		f.addComponent(cityF);
		f.addComponent(pricePerKm);

		return f;
	}
}
