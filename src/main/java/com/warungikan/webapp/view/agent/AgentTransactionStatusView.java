package com.warungikan.webapp.view.agent;

import java.util.Date;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AgentTransactionStatusView extends VerticalLayout implements View {

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

	public AgentTransactionStatusView() {
		Table t = createTabel();
		addStyleName("product-container");
		addComponent(t);
		setComponentAlignment(t, Alignment.MIDDLE_CENTER);
	}


	private Table createTabel() {
		Table t = new Table();
		t.setCaption("Pesanan hari ini");
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
		
		
		Button detailsButton = new Button("Alamat");
		detailsButton.addStyleName(ValoTheme.BUTTON_TINY);
		detailsButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		Button trxButton = new Button("Items");
		trxButton.addStyleName(ValoTheme.BUTTON_TINY);
		trxButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		Label pendingLabel = new Label("Pending");
		pendingLabel.addStyleName(ValoTheme.LABEL_TINY);
		
		Item i = t.addItem(1);
		i.getItemProperty(TRANSACTION_ON).setValue(new Date());
		i.getItemProperty(CUSTOMER).setValue("Pandji");
		i.getItemProperty(AMOUNT).setValue("130.000");
		i.getItemProperty(DETAILS).setValue(trxButton);
		i.getItemProperty(ADDRESS).setValue(detailsButton);
		i.getItemProperty(STATUS).setValue(pendingLabel);
		
		return t;
	}

}
