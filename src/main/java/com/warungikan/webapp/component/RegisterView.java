package com.warungikan.webapp.component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.RSAddName;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.view.LoginView;

public class RegisterView extends VerticalLayout implements View{

	private MapPage map;
	private PasswordField pwd2F;
	private PasswordField pwdF;
	private TextArea addressF;
	private TextField telpF;
	private TextField nameF;
	private TextField emailF;
	private TextField cityF;

	public RegisterView() {
		setSizeFull();
		FormLayout form = createForm();

		addComponent(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	}

	private FormLayout createForm() {

		FormLayout l = new FormLayout();
		l.setSizeUndefined();
		l.setSpacing(true);
		l.setMargin(true);
		Label cap = Factory.createLabelHeader("Pendaftaran");
		cap.addStyleName(ValoTheme.LABEL_H3);
		cap.addStyleName(ValoTheme.LABEL_BOLD);

		emailF = new TextField("Email");
		nameF  = new TextField("Nama");
		telpF  = new TextField("No HP");
		addressF = new TextArea("Alamat");
		cityF  = new TextField("Kota");
		pwdF = new PasswordField("Passoword");
		pwd2F = new PasswordField("Passoword sekali lagi");
		map = new MapPage("Tentukan lokasi tempat anda", true);

		l.addComponent(cap);
		l.addComponent(emailF);
		l.addComponent(nameF);
		l.addComponent(telpF);
		l.addComponent(addressF);
		l.addComponent(pwdF);
		l.addComponent(pwd2F);
		l.addComponent(map);

//		l.setExpandRatio(map, 1.0f);
//		l.setExpandRatio(cap, 0.0f);
//		l.setExpandRatio(emailF, 0.0f);
//		l.setExpandRatio(nameF, 0.0f);
//		l.setExpandRatio(telpF, 0.0f);
//		l.setExpandRatio(addressF, 0.0f);
//		l.setExpandRatio(pwdF, 0.0f);
//		l.setExpandRatio(pwd2F, 0.0f);

		HorizontalLayout bl = new HorizontalLayout();
		bl.setWidth(100, Unit.PERCENTAGE);
		bl.setSpacing(true);
		Button cancel = Factory.createButtonDanger("Batalkan");
		Button ok = Factory.createButtonOk("Daftar");
		bl.addComponent(cancel);
		bl.addComponent(ok);
		bl.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		bl.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
		l.addComponent(bl);

		cancel.addClickListener(e->{
			UI.getCurrent().setContent(new LoginView());
		});
		ok.addClickListener(e->{

			if(!pwdF.getValue().equals(pwd2F.getValue())){
				Notification.show("Password tidak sama", Type.ERROR_MESSAGE);
				return;
			}
			RSAddName add = map.getResult();
			emailF.getValue();
			Boolean result = ServiceInitator.getUserService().createUserCustomer("", nameF.getValue(), emailF.getValue(), telpF.getValue(), addressF.getValue(), cityF.getValue(), add.getLatitude(), add.getLongitude(), pwdF.getValue());
			if(result){
				createSuccessMessage();
			}else{
				Notification.show("Terjadi kesalahan saat mendaftar. Mohon perhatikan agar email yang ada kirim belum pernah terdaftar", Type.ERROR_MESSAGE);
			}
		});
		return l;
	}

	private void createSuccessMessage() {
		removeAllComponents();
		Label l = new Label();
		l.setValue("<h2 style='text-align:center'>Terima kasih telah mendaftar. Kami telah mengirim email konfirmasi. Mohon mengaktifkan account anda.</h2>");
		l.setContentMode(ContentMode.HTML);
		
		Button back = Factory.createButtonNormal("Kembali");
		back.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
				VaadinSession.getCurrent().close();				
			}
		});
		addComponent(l);
		addComponent(back);
		
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		setComponentAlignment(back, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
