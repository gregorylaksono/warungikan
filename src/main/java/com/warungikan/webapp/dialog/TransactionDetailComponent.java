package com.warungikan.webapp.dialog;

import java.util.List;

import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.User;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;

public class TransactionDetailComponent extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3297934006073333897L;
	private static final String NAME = "name";
	private static final String AMOUNT = "amount";
	private static final String PRICE = "price";
	private String jwt;
	private String trx_id;
	private List<TransactionDetail> details;
	private Transaction transaction;

	public TransactionDetailComponent(Transaction tr){
		setMargin(true);
		setSpacing(true);
		setWidth(400, Unit.PIXELS);
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		this.transaction = tr;
		this.trx_id = tr.getTransactionId();
		this.details = ServiceInitator.getTransactionService().getTransactionDetail(jwt, trx_id);
		VerticalLayout headerLayout = createHeaderLayout();
		Table t = createTable();
		Button close = Factory.createButtonDanger("Tutup");
		close.addClickListener(e->{
			((MyUI)UI.getCurrent()).closeWindow();
		});
		addComponent(headerLayout);
		addComponent(t);
		addComponent(close);
		setComponentAlignment(t, Alignment.BOTTOM_CENTER);
		setComponentAlignment(close, Alignment.BOTTOM_RIGHT);
	}

	private VerticalLayout createHeaderLayout() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		VerticalLayout left = new VerticalLayout();
		VerticalLayout right = new VerticalLayout();

		User agent = transaction.getAgent();
		User customer = transaction.getCustomer();

		Label customerCaption = new Label("<b>Pembeli</b>");
		customerCaption.setContentMode(ContentMode.HTML);
		left.addComponent(customerCaption);
		left.addComponent(new Label(customer.getName()));
		left.addComponent(new Label(customer.getAddress()));

		Label agentCaption = new Label("<b>Agent</b>");
		agentCaption.setContentMode(ContentMode.HTML);
		right.addComponent(agentCaption);
		right.addComponent(new Label(agent.getName()));
		right.addComponent(new Label(agent.getAddress()));
		right.addComponent(new Label(agent.getTelpNo()));
		l.addComponent(left);
		l.addComponent(right);
		return l;
	}

	private Table createTable() {
		Table t = new Table();
		t.setHeight(250, Unit.PIXELS);
		t.addContainerProperty(NAME, String.class, null, "Item", null, Align.LEFT);
		t.addContainerProperty(AMOUNT, String.class, null, "Jumlah", null, Align.LEFT);
		t.addContainerProperty(PRICE, String.class, null, "Total harga", null, Align.LEFT);

		long total = 0;
		for(TransactionDetail d: details){
			Item i = t.addItem(d.getOid());
			i.getItemProperty(NAME).setValue(d.getItem().getName());
			i.getItemProperty(AMOUNT).setValue(String.valueOf(d.getAmount()));
			Long price = d.getAmount() * d.getItem().getPrice();
			total = price + total;
			i.getItemProperty(PRICE).setValue("Rp."+(Util.formatLocalAmount(price)));
		}

		Double d = Util.calculateDistance(transaction.getDistance());
		Item trxItem = t.addItem(transaction.getTransactionId());
		trxItem.getItemProperty(NAME).setValue("Biaya transport");
		trxItem.getItemProperty(AMOUNT).setValue(String.valueOf(d)+" Km");
		trxItem.getItemProperty(PRICE).setValue("Rp. "+Util.formatLocalAmount(transaction.getTransportPrice()));

		t.setFooterVisible(true);
		t.setColumnFooter(NAME, "Total semua");
		t.setColumnFooter(PRICE, "Rp. "+Util.formatLocalAmount(total + transaction.getTransportPrice()));

		return t;
	}

}
