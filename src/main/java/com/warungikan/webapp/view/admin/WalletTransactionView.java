package com.warungikan.webapp.view.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.dialog.TopupWallet;

public class WalletTransactionView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926077373993667685L;
	private static final String AMOUNT = "amount";
	private static final String USER = "user";
	private static final String TRX_DATE = "topup-date";
	public WalletTransactionView() {
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		
		VerticalLayout l = createMainContent();
		addComponent(l);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
	}
	
	private VerticalLayout createMainContent() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setWidth(70, Unit.PERCENTAGE);
		Table trxTable = createTrxTable();
		Button topupButton = new Button("Top up");
		topupButton.addStyleName(ValoTheme.BUTTON_TINY);
		topupButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		topupButton.addClickListener( e ->{
			Window w = new Window();
			w.setContent(new TopupWallet());
			w.setClosable(true);
			w.setResizable(false);
			w.setDraggable(false);
			w.setModal(true);
			UI.getCurrent().addWindow(w);
		});
		
		l.addComponent(trxTable);
		l.addComponent(topupButton);
		
		l.setExpandRatio(trxTable, 1.0f);
		l.setExpandRatio(topupButton, 0.0f);
		
		l.setComponentAlignment(trxTable, Alignment.MIDDLE_LEFT);
		l.setComponentAlignment(topupButton, Alignment.MIDDLE_LEFT);
		return l;
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(TRX_DATE, String.class, null, "Transaction on", null, Align.LEFT);
		t.addContainerProperty(USER , String.class, null, "User", null, Align.LEFT);
		t.addContainerProperty(AMOUNT , String.class, null, "Amount", null, Align.LEFT);

		return t;
	}
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
