package com.warungikan.webapp.view.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;

public class AdminTransaction extends HorizontalLayout implements View{
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

	public AdminTransaction() {
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSpacing(true);
		setSizeFull();
		Table trxTable = createTrxTable();
		Table detailTable = createDetailTable();

		addComponent(detailTable);
		addComponent(trxTable);

		setExpandRatio(trxTable, 0.3f);
		setExpandRatio(detailTable, 0.7f);
	}


	private Table createDetailTable() {
		Table t = new Table();
		t.setSizeFull();
		t.addContainerProperty(CUSTOMER , String.class, null, "Customer", null, Align.LEFT);
		t.addContainerProperty(TRX_DATE, String.class, null, "Transaction on", null, Align.LEFT);
		t.addContainerProperty(AGENT , String.class, null, "Agent", null, Align.LEFT);
		t.addContainerProperty(TOTAL_PRICE , String.class, null, "Total price", null, Align.LEFT);
		t.addContainerProperty(SETTLEMENT_DATE , String.class, null, "Settlement date", null, Align.LEFT);
		t.addContainerProperty(VIEW_ITEM , Button.class, null, "Item", null, Align.LEFT);
		t.addContainerProperty(CURRENT_STATE , String.class, null, "Current state", null, Align.LEFT);
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
