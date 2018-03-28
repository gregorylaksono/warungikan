package com.warungikan.webapp.view;

import java.util.Date;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AdminTransactionStatusView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480699005062736943L;
	private static final String STATUS = "status";
	private static final String ADDRESS = "addr";
	private static final String DETAILS = "detail_trx";
	private static final String AMOUNT = "amount";
	private static final String CUSTOMER = "cust";
	private static final String TRANSACTION_ON = "trxOn";


	@Override
	public void enter(ViewChangeEvent event) {
		setSpacing(true);
		setMargin(true);

	}

	public AdminTransactionStatusView() {
		Table t = createTabel();
		addStyleName("product-container");
		addComponent(t);
		setComponentAlignment(t, Alignment.MIDDLE_CENTER);
		
	}


	private Table createTabel() {
		Table t = new Table();
		t.setWidth(650, Unit.PIXELS);
		t.setHeight(500, Unit.PIXELS);

		t.addContainerProperty(TRANSACTION_ON, Date.class,null);
		t.addContainerProperty(CUSTOMER, String.class,null);
		t.addContainerProperty(AMOUNT, String.class,null);
		t.addContainerProperty(DETAILS, Button.class,null);
		t.addContainerProperty(ADDRESS, Button.class,null);
		t.addContainerProperty(STATUS, Label.class,null);
		
		
		t.setColumnHeader(TRANSACTION_ON, "Tanggal");
		t.setColumnHeader(CUSTOMER, "Customer");
		t.setColumnHeader(AMOUNT, "Jumlah");
		t.setColumnHeader(DETAILS, "Pesanan");
		t.setColumnHeader(ADDRESS, "Alamat");
		t.setColumnHeader(STATUS, "Status");
		
		return t;
	}

}
