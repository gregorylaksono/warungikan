package com.warungikan.webapp.view.customer;

import java.util.Map;

import org.warungikan.api.model.request.VLatLng;
import org.warungikan.db.model.AgentData;
import org.warungikan.db.model.User;

import com.google.gson.Gson;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.MapPage;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.RSAddName;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;

public class MyProfileView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8555772573595397967L;
	private Boolean isAgent = false;
	private String sessionId;
	private String formatedBalance;
	private Button updateProfileBtn;
	private TextField price_per_km;
	private TextField email;
	private TextField telpNo;
	private TextArea address;
	private TextField name;
	private Label status;
	private User user;
	private AgentData agent;
	private Button changeLocationBtn;
	private ClickListener updateLocationListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			VerticalLayout l = new VerticalLayout();
			Button save = Factory.createButtonOk("Save location");
			MapPage map = new MapPage("My location", true, new LatLon(user.getLatitude(), user.getLongitude()));
			l.setSpacing(true);
			l.setMargin(true);
			l.addComponent(map);
			l.addComponent(save);
			
			l.setExpandRatio(map, 1.0f);
			l.setExpandRatio(save, 0.0f);
			l.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
			
			Window w = new Window();
			w.setContent(l);
			w.setDraggable(false);
			w.setModal(true);
			w.setResizable(true);
			
			save.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					RSAddName result = map.getResult();
					Boolean isSaved = ServiceInitator.getUserService().updateCoordinate(sessionId, new VLatLng(result.getLatitude(), result.getLongitude()));
					if(isSaved){
						UI.getCurrent().removeWindow(w);
						Notification.show("Lokasi anda berhasil diubah", Type.TRAY_NOTIFICATION);
					}else{
						Notification.show("Lokasi anda gagal diubah", Type.ERROR_MESSAGE);
					}
				}
			});
			UI.getCurrent().addWindow(w);
		}
	};
	private ClickListener changeDataListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			validate();
			Boolean result = ServiceInitator.getUserService().updateUser(sessionId, name.getValue(), email.getValue(), telpNo.getValue(), address.getValue(), city.getValue());
			if(result){
				Notification.show("Perubahan berhasil dilakukan", Type.HUMANIZED_MESSAGE);
				user = ServiceInitator.getUserService().getUser(sessionId);
				initUserData();
			}else{
				Notification.show("Terjadi kesalahan ketika melakukan perubahan", Type.ERROR_MESSAGE);
			}
		}
	};
	private VerticalLayout walletLayout;
	private TextField city;
	private Label agentDataTransportPriceLbl;
	public MyProfileView() {
		sessionId = ((MyUI)UI.getCurrent()).getJwt();
		Long balance = new Long(0);
		if(((MyUI)UI.getCurrent()).getRole().equals("AGENT")){
			isAgent = true;
			balance = ServiceInitator.getTransactionService().getBalanceAgent(sessionId);
			agent = ServiceInitator.getUserService().getAgentData(sessionId);
			user = agent.getAgent();
		}else{
			user = ServiceInitator.getUserService().getUser(sessionId);
			balance = ServiceInitator.getTransactionService().getBalanceCustomer(sessionId);
		}
		
		formatedBalance = Util.formatLocalAmount(balance);
		
		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setWidth(100, Unit.PERCENTAGE);
		bottomLayout.setSpacing(true);
		setSpacing(true);
		setMargin(true);
		setWidth(100, Unit.PERCENTAGE);
		
		walletLayout = createWalletLayout();
		FormLayout authForm = createAuthForm();
		FormLayout profileForm = createProfileForm();
		
		bottomLayout.addComponent(authForm);
		bottomLayout.addComponent(profileForm);
		bottomLayout.setComponentAlignment(authForm, Alignment.TOP_LEFT);
		bottomLayout.setComponentAlignment(profileForm, Alignment.TOP_RIGHT);
		
		addComponent(walletLayout);
		addComponent(bottomLayout);
		
		initData();
	}

	private void initData() {
		String statValue = null;
		if(((MyUI)UI.getCurrent()).getRole().equals("USER")){
			statValue = "CUSTOMER";
			initUserData();
		}else if(((MyUI)UI.getCurrent()).getRole().equals("AGENT")){
			statValue = "AGENT";
			initAgentData();
		}else if(((MyUI)UI.getCurrent()).getRole().equals("ADMIN")){
			statValue = "ADMIN";
			initUserData();
			removeComponent(walletLayout);
		}
		
		status.setValue(statValue);
		
	}
	
	private void validate(){
		name.validate();
		address.validate();
		city.validate();
		telpNo.validate();
		email.validate();
	}

	private void initUserData() {
		name.setValue(user.getName());
		address.setValue(user.getAddress());
		telpNo.setValue(user.getTelpNo());
		email.setValue(user.getEmail());
		city.setValue(user.getCity());
	}

	private void initAgentData() {
		initUserData();
		Map data = new Gson().fromJson(agent.getData(), Map.class);
		String priceJson = (String) data.get(Constant.AGENT_DATA_KEY_PRICE_PER_KM);
		price_per_km.setValue(priceJson);
		price_per_km.setReadOnly(true);
		agentDataTransportPriceLbl.setVisible(true);
	}

	private VerticalLayout createWalletLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout.addStyleName("product-container");
		Label header = Factory.createLabelHeaderNormal("Saldo sekarang : Rp. "+formatedBalance);
//		Button topupButton = Factory.createButtonOk("Top up saldo");
		Button topupHistory = Factory.createButtonOk("Top up history");
		topupHistory.addClickListener(e->{
			((MyUI)UI.getCurrent()).getNavigator().navigateTo(Constant.VIEW_MY_WALLET_HISTORY);
		});
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
//		buttonLayout.addComponent(topupButton);
		buttonLayout.addComponent(topupHistory);
		
		layout.addComponent(header);
		layout.addComponent(buttonLayout);
		layout.setExpandRatio(header, 1.0f);
		layout.setExpandRatio(buttonLayout, 0.0f);
		
		return layout;
	}

	private FormLayout createProfileForm() {
		FormLayout profileForm = new FormLayout();
		profileForm.setMargin(true);
		profileForm.addStyleName("product-container");
		profileForm.setCaption("Data profile anda");
		status = new Label("Status");
		name = new TextField("Nama");
		address = new TextArea("Alamat");
		city = new TextField("City");
		telpNo = new TextField("No. Telp");
		email = new TextField("Email");
		price_per_km = new TextField("Price per km");
		updateProfileBtn = Factory.createButtonOk("Update");
		changeLocationBtn = Factory.createButtonOk("Ubah lokasi"); 
		
		name.addValidator(new StringLengthValidator("Nama harus diisi. Minimal char 3 - 30", 3, 30, false));
		address.addValidator(new StringLengthValidator("Alamat harus diisi. Minimal char 10 - 50", 10, 50, false));
		city.addValidator(new StringLengthValidator("Kota harus diisi. Minimal char 3 - 20", 3, 20, false));
		telpNo.addValidator(new StringLengthValidator("telpNo harus diisi. Minimal char 7 - 20", 7, 20, false));
		email.addValidator(new StringLengthValidator("Email harus diisi. Minimal char 6 - 50", 6, 50, false));
		
		name.setRequired(true);
		address.setRequired(true);
		city.setRequired(true);
		telpNo.setRequired(true);
		email.setRequired(true);
		
		name.setValidationVisible(true);
		address.setValidationVisible(true);
		city.setValidationVisible(true);
		telpNo.setValidationVisible(true);
		email.setValidationVisible(true);
		
		agentDataTransportPriceLbl = new Label("Harga transport hanya dapat diubah oleh admin");
		agentDataTransportPriceLbl.setVisible(false);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(updateProfileBtn);
		buttonLayout.addComponent(changeLocationBtn);
		
		profileForm.addComponent(status);
		profileForm.addComponent(name);
		profileForm.addComponent(address);
		profileForm.addComponent(city);
		profileForm.addComponent(telpNo);
		profileForm.addComponent(email);
		profileForm.addComponent(price_per_km);
		profileForm.addComponent(agentDataTransportPriceLbl);
		profileForm.addComponent(buttonLayout);
		price_per_km.setVisible(false);
		
		if(isAgent){
			price_per_km.setVisible(true);
		}
		
		changeLocationBtn.addClickListener(updateLocationListener );
		updateProfileBtn.addClickListener(changeDataListener );
		return profileForm;
	}

	private FormLayout createAuthForm() {
		FormLayout authForm = new FormLayout();
		authForm.setMargin(true);
		authForm.addStyleName("product-container");
		authForm.setCaption("Ubah password");
		PasswordField oldPasswordField = new PasswordField("Password lama");
		PasswordField pwdField = new PasswordField("Password baru");
		PasswordField cnfPwdField = new PasswordField("Password baru confimation");
		Button authPwdButton = Factory.createButtonOk("Update");
		authPwdButton.addClickListener(e->{
			if(pwdField.getValue().equals(cnfPwdField.getValue())){
				Boolean result = ServiceInitator.getUserService().changePassword(sessionId, oldPasswordField.getValue(), pwdField.getValue());
				if(result){
					Notification.show("Password berhasil diubah. Kami sarankan untuk logout dan login kembali", Type.TRAY_NOTIFICATION);
				}else{
					Notification.show("Perubahan password tidak dapat dilakukan, mohon kontak admin kami. Terima kasih", Type.ERROR_MESSAGE);
				}
				
			}else{
				Notification.show("Password tidak sama", Type.ERROR_MESSAGE);
			}
		});
		authForm.addComponent(oldPasswordField);
		authForm.addComponent(pwdField);
		authForm.addComponent(cnfPwdField);
		authForm.addComponent(authPwdButton);
		return authForm;
	}

	@Override
	public void enter(ViewChangeEvent event) {

		
	}
}
