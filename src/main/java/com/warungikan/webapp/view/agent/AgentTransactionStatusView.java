package com.warungikan.webapp.view.agent;

import java.util.Date;
import java.util.List;

import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionState;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Util;

public class AgentTransactionStatusView extends VerticalLayout implements View, IParentWindowService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480699005062736943L;
	private static final String STATUS = "status";
	private static final String DETAILS = "detail_trx";
	private static final String AMOUNT = "amount";
	private static final String CUSTOMER = "cust";
	private static final String TRANSACTION_ON = "trxOn";
	private static final String TRANSACTION_ID = "trx_id";
	private static final String TRANSPORT_PRICE = "transport_price";
	private String jwt;
	private Table t;

	@Override
	public void enter(ViewChangeEvent event) {
		setSpacing(true);
		setMargin(true);
	}

	public AgentTransactionStatusView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		t = createTabel();
		addStyleName("product-container");
		addComponent(t);
		setComponentAlignment(t, Alignment.MIDDLE_CENTER);
		update();
	}


	private Table createTabel() {
		Table t = new Table();
		t.setCaption("Pesanan hari ini");
		t.setWidth(850, Unit.PIXELS);
		t.setHeight(500, Unit.PIXELS);

		t.addContainerProperty(TRANSACTION_ID, String.class,null);
		t.addContainerProperty(TRANSACTION_ON, Date.class,null);
		t.addContainerProperty(CUSTOMER, String.class,null);
		t.addContainerProperty(AMOUNT, String.class,null);
		t.addContainerProperty(TRANSPORT_PRICE, String.class,null);
		t.addContainerProperty(DETAILS, Button.class,null);
		t.addContainerProperty(STATUS, String.class,null);
		
		t.setColumnHeader(TRANSPORT_PRICE, "Biaya transport");
		t.setColumnHeader(TRANSACTION_ID, "ID Transaksi");
		t.setColumnHeader(TRANSACTION_ON, "Tanggal");
		t.setColumnHeader(CUSTOMER, "Customer");
		t.setColumnHeader(AMOUNT, "Total");
		t.setColumnHeader(DETAILS, "Pesanan");
		t.setColumnHeader(STATUS, "Status");
		
		Button trxButton = new Button("Items");
		trxButton.addStyleName(ValoTheme.BUTTON_TINY);
		trxButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		Label pendingLabel = new Label("Pending");
		pendingLabel.addStyleName(ValoTheme.LABEL_TINY);
		return t;
	}

	@Override
	public void update() {
		t.removeAllItems();
		List<Transaction> trxs = ServiceInitator.getTransactionService().getTransactionAgent(jwt);
		for(final Transaction trx: trxs) {
			Button detailsButton = new Button("Detail");
			detailsButton.addStyleName(ValoTheme.BUTTON_TINY);
			detailsButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			detailsButton.addClickListener(e->{
				Window w = new Window();
				w.setContent(new TransactionDetailMap(trx, trx.getCustomer(), TransactionState.TransactionStateEnum.getStateCode(trx.getStatus()), AgentTransactionStatusView.this));
				w.setModal(true);
				w.setDraggable(false);
				w.setResizable(false);
				w.setClosable(true);
				UI.getCurrent().addWindow(w);
			});
			Item i = t.addItem(trx.getTransactionId());
			i.getItemProperty(TRANSACTION_ID).setValue(trx.getTransactionId());
			i.getItemProperty(TRANSACTION_ON).setValue(trx.getCreationDate());
			i.getItemProperty(CUSTOMER).setValue(trx.getCustomer().getName());
			i.getItemProperty(AMOUNT).setValue("Rp. "+Util.formatLocalAmount(trx.getTotalPrice()));
			i.getItemProperty(TRANSPORT_PRICE).setValue("Rp. "+Util.formatLocalAmount(trx.getTransportPrice()));
			i.getItemProperty(DETAILS).setValue(detailsButton);
			i.getItemProperty(STATUS).setValue(trx.getStatus());
		}
	}

}

