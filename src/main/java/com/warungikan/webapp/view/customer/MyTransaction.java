package com.warungikan.webapp.view.customer;

import java.text.DecimalFormat;
import java.util.List;

import org.warungikan.db.model.Transaction;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.dialog.AgentDetailComponent;
import com.warungikan.webapp.dialog.TransactionDetailComponent;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;

public class MyTransaction extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6185841848027736338L;
	private static final String CANCEL = "cancel";
	private static final String VIEW_DETAIL = "view_detail";
	private static final String PROGRESS = "progress";
	private static final String TOTAL_PRICE = "total_price";
	private static final String AGENT_NAME = "agent_name";
	private static final String ORDER_ON = "order_on";
	private static final String TRANASCTION_ID = "trx_id";
	private Long balance = new Long(0);
	private String jwt;
	protected List<Transaction> transactions;
	private Label header;
	public static DecimalFormat decimalFormat = new DecimalFormat("###,###");
	public MyTransaction() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		((MyUI)UI.getCurrent()).updateBalance();
		setMargin(true);
		setSpacing(true);
		
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);
		VerticalLayout walletLayout = createWalletLayout();
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Mengambil data transaksi...");
        pb3.addStyleName("pb-center-align");
		
        VerticalLayout parent = new VerticalLayout();
		
		Runnable l = new Runnable() {
			
			@Override
			public void run() {
				balance = ServiceInitator.getTransactionService().getBalanceCustomer(jwt);
				transactions = ServiceInitator.getTransactionService().getTransactionCustomer(jwt);
				header.setValue("Saldo sekarang : Rp. "+decimalFormat.format(balance));
				UI.getCurrent().access(new Runnable() {
					
					@Override
					public void run() {
						
						Table t = createTable();
						parent.replaceComponent(pb3, t);
					}
				});
			}
		};
		
		
		parent.setWidth(85, Unit.PERCENTAGE);
		parent.addStyleName("product-container");
		parent.setMargin(true);
		parent.setSpacing(true);
		parent.addComponent(pb3);
		parent.setComponentAlignment(pb3, Alignment.MIDDLE_CENTER);
		
		Button back = new Button("Kembali belanja");
		back.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		back.addStyleName(ValoTheme.BUTTON_SMALL);
		
		parent.addComponent(back);
		parent.setExpandRatio(pb3, 1.0f);
		parent.setExpandRatio(back, 0.0f);
		parent.setComponentAlignment(back, Alignment.BOTTOM_RIGHT);
		
		back.addClickListener(e->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_SHOP);
		});
		
		addComponent(walletLayout);
		addComponent(parent);
		setComponentAlignment(parent, Alignment.MIDDLE_CENTER);
		setComponentAlignment(walletLayout, Alignment.MIDDLE_CENTER);
		
		setExpandRatio(walletLayout, 0.0f);
		setExpandRatio(parent, 1.0f);
		
		Thread as = new Thread(l);
		as.start();
	}
	
	private VerticalLayout createWalletLayout() {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth(85, Unit.PERCENTAGE);
		layout.setMargin(true);
		layout.setSpacing(true);
		
		layout.addStyleName("product-container");
		header = new Label();
		Button topupButton = Factory.createButtonOk("Top up saldo");
		
		layout.addComponent(header);
		layout.addComponent(topupButton);
		layout.setExpandRatio(header, 1.0f);
		layout.setExpandRatio(topupButton, 0.0f);
		
		return layout;
	}

	private Table createTable() {
		Table t = new Table();
		t.setCaption("Pesanan saya");
		t.setHeight(300, Unit.PIXELS);
		t.addContainerProperty(TRANASCTION_ID, String.class, null);
		t.addContainerProperty(ORDER_ON, String.class, null);
		t.addContainerProperty(AGENT_NAME, String.class, null);
		t.addContainerProperty(TOTAL_PRICE, String.class, null);
		t.addContainerProperty(PROGRESS, String.class, null);
		t.addContainerProperty(VIEW_DETAIL, Button.class, null);
		
		t.setColumnHeader(TRANASCTION_ID, "No pemesanan");
		t.setColumnHeader(ORDER_ON, "Tanggal");
		t.setColumnHeader(AGENT_NAME, "Agen");
		t.setColumnHeader(TOTAL_PRICE, "Harga");
		t.setColumnHeader(PROGRESS, "Progress");
		t.setColumnHeader(VIEW_DETAIL, "Detail");
		
		t.setColumnWidth(ORDER_ON, 130);
		t.setColumnWidth(AGENT_NAME, 130);
		t.setColumnWidth(TOTAL_PRICE, 130);
		t.setColumnWidth(PROGRESS, 130);
		
//		t.setColumnWidth(CANCEL, 130);
		t.setColumnWidth(VIEW_DETAIL, 130);
		
//		t.setColumnAlignment(CANCEL, Align.CENTER);
		t.setColumnAlignment(VIEW_DETAIL, Align.CENTER);
		
		for(Transaction trx : transactions){
			Item i = t.addItem(trx.getOid());
			Button viewDetailButton = Factory.createButtonOk("Detail");
			viewDetailButton.addClickListener(e ->{
				Window w = new Window();
				w.setModal(true);
				w.setResizable(false);
				w.setClosable(true);
				w.setContent(new TransactionDetailComponent(trx));
				UI.getCurrent().addWindow(w);
			});
		
			i.getItemProperty(TRANASCTION_ID).setValue(trx.getTransactionId());
			i.getItemProperty(ORDER_ON).setValue(Util.parseDate(trx.getCreationDate()));
			i.getItemProperty(AGENT_NAME).setValue(trx.getAgent().getName());
			i.getItemProperty(TOTAL_PRICE).setValue("Rp. "+Util.formatLocalAmount(trx.getTotalPrice()));
			i.getItemProperty(PROGRESS).setValue(trx.getStatus());
			i.getItemProperty(VIEW_DETAIL).setValue(viewDetailButton);
		}
		return t;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
