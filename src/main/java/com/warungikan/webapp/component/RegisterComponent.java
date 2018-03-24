package com.warungikan.webapp.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.view.LoginView;

public class RegisterComponent extends VerticalLayout{

	public RegisterComponent() {
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
		
		TextField email = new TextField("Email");
		TextField name  = new TextField("Nama");
		TextField telp  = new TextField("No HP");
		TextArea  address = new TextArea("Alamat");
		PasswordField pwd = new PasswordField("Passoword");
		PasswordField pwd2 = new PasswordField("Passoword sekali lagi");
		
		l.addComponent(cap);
		l.addComponent(email);
		l.addComponent(name);
		l.addComponent(telp);
		l.addComponent(address);
		l.addComponent(pwd);
		l.addComponent(pwd2);
		
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
			UI.getCurrent().setContent(new LoginView());
		});
		return l;
	}
}
