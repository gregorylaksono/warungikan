package com.warungikan.webapp.view.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.warungikan.db.model.Transaction;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Table.Align;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;
import com.vaadin.ui.VerticalLayout;

public class AdminTransactionView extends HorizontalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1411782232880164987L;
	private static final String SETTLEMENT_DATE = "settlement_date";
	private static final String TOTAL_PRICE 	= "total_price";
	private static final String AGENT 			= "agent";
	private static final String TRX_DATE 		= "trx_date";
	private static final String CUSTOMER 		= "customer";
	private static final String VIEW_ITEM 		= "view_item";
	private static final String CURRENT_STATE 	= "current_state";
	private static final String STATE_DATE 		= "state_date";
	private static final String STATE_NAME		= "state_name";
	private static final String TRX_ID 			= "trx_id";
	
	private String jwt;
	private Table trxTable;
	
	public AdminTransactionView() {
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSpacing(true);
		setSizeFull();
		
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Mendapatkan transaksi...");
        pb3.addStyleName("pb-center-align");
		Runnable l = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					UI.getCurrent().access(new Runnable() {
						
						@Override
						public void run() {
							trxTable = createTrxTable();
							initTransactionTable();
							replaceComponent(pb3, trxTable);
							
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		Table detailTable = createDetailTable();

		addComponent(detailTable);
		addComponent(pb3);

		setExpandRatio(pb3, 0.3f);
		setExpandRatio(detailTable, 0.7f);
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		Thread as = new Thread(l);
		as.start();
		
	}


	private void initTransactionTable() {
		List<Transaction> allTrx = ServiceInitator.getTransactionService().getAllTransaction(jwt);
		for(Transaction t : allTrx){
			Button viewDetailBtn = Factory.createButtonNormal("Lihat detail");
			
			Item i = trxTable.addItem(t.getTransactionId());
			i.getItemProperty(TRX_ID).setValue(t.getTransactionId());
			i.getItemProperty(CUSTOMER).setValue(t.getCustomer().getName());
			i.getItemProperty(TRX_DATE).setValue(Util.parseDate(t.getCreationDate()));
			i.getItemProperty(AGENT).setValue(t.getAgent().getName());
			i.getItemProperty(TOTAL_PRICE).setValue("Rp. "+Util.formatLocalAmount(t.getTotalPrice()));
			i.getItemProperty(VIEW_ITEM).setValue(viewDetailBtn);
			
			if (t.getSettlementDate() == null) i.getItemProperty(SETTLEMENT_DATE).setValue("");
			else i.getItemProperty(SETTLEMENT_DATE).setValue(Util.parseDate(t.getSettlementDate()));
			
		}
	}


	private Table createDetailTable() {
		Table t = new Table();
		t.setSizeFull();
		t.addContainerProperty(TRX_ID , String.class, null, "Transaction id", null, Align.LEFT);
		t.addContainerProperty(CUSTOMER , String.class, null, "Customer", null, Align.LEFT);
		t.addContainerProperty(TRX_DATE, String.class, null, "Transaction on", null, Align.LEFT);
		t.addContainerProperty(AGENT , String.class, null, "Agent", null, Align.LEFT);
		t.addContainerProperty(TOTAL_PRICE , String.class, null, "Total price", null, Align.LEFT);
		t.addContainerProperty(SETTLEMENT_DATE , String.class, null, "Settlement date", null, Align.LEFT);
		t.addContainerProperty(VIEW_ITEM , Button.class, null, "Item", null, Align.LEFT);
		return t;
	}


	private Table createTrxTable() {
		Table t = new Table();
		t.setSizeFull();
		t.addContainerProperty(STATE_DATE , String.class, null, "Date", null, Align.LEFT);
		t.addContainerProperty(STATE_NAME , String.class, null, "State", null, Align.LEFT);

		return t;
	}


	@Override
	public void enter(ViewChangeEvent event) {

	}

}
