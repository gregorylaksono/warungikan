package com.warungikan.webapp.dialog;

import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.util.Factory;

public class ConfirmPayment extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7134803285392489748L;
	private ClickListener cancel;
	private ClickListener ok;
	
	public ConfirmPayment(ClickListener ok, ClickListener cancel) {
		this.ok = ok;
		this.cancel = cancel;
		setSpacing(true);
		setMargin(true);
		setHeight(200, Unit.PIXELS);
		Label l = Factory.createLabel("Lanjutkan pembayaran? Saldo anda akan dikurangi");
		HorizontalLayout buttonLayout = createButtonLayout();
		addComponent(l);
		addComponent(buttonLayout);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
	}

	private HorizontalLayout createButtonLayout() {
		HorizontalLayout l = new HorizontalLayout();
		l.setWidth(100, Unit.PERCENTAGE);
		l.setSpacing(true);
		Button cancel = new Button("Batalkan");
		cancel.addStyleName(ValoTheme.BUTTON_SMALL);
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		l.addComponent(cancel);
		
		Button submit = new Button("Bayar");
		submit.addStyleName(ValoTheme.BUTTON_SMALL);
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		l.addComponent(cancel);
		l.addComponent(submit);
		
		l.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		l.setComponentAlignment(submit, Alignment.BOTTOM_LEFT);
		return l;
	}

}
