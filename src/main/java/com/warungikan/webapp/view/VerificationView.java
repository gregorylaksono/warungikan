package com.warungikan.webapp.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.ApplicationProperties;
import com.warungikan.webapp.util.Factory;

public class VerificationView extends VerticalLayout implements View {

	private String verification_id;
	private Label l;
	public VerificationView() {
		setSizeFull();
		
		l = new Label();
		l.setContentMode(ContentMode.HTML);

		addComponent(l);
		
		Button b = Factory.createButtonOk("Kembali ke login");
		b.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
					Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
					VaadinSession.getCurrent().close();
			}
		});
		addComponent(b);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		setComponentAlignment(b, Alignment.TOP_CENTER);
		setExpandRatio(l, 1.0f);
		setExpandRatio(b, 0.0f);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		String[] arr = event.getParameters().split("/");
		this.verification_id = arr[0];
		Boolean result = ServiceInitator.getUserService().verifyUser(verification_id);
		if(result){
			l.setValue("<h1 style='text-align:center'>Registrasi berhasil silahkan kembali ke halaman login</h>");
			
		}else{
			l.setValue("<h1 style='text-align:center'>Registrasi tidak berhasil. Silahkan hubungi admin kami "+ApplicationProperties.getAdminEmail()+"</h>");
		}
	}

}
