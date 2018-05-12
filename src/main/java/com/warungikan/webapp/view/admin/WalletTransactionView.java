package com.warungikan.webapp.view.admin;

import java.util.List;

import org.warungikan.db.model.TopupWalletHistory;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.dialog.TopupWallet;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Util;

public class WalletTransactionView extends VerticalLayout implements View, IParentWindowService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926077373993667685L;
	private static final String AMOUNT = "amount";
	private static final String USER = "user";
	private static final String TRX_DATE = "topup-date";
	private static final String REF_BANK_NO = "ref_bank_no";
	private static final String TOPUP_ID = "top_up_id";
	private Table trxTable;
	private String jwt;
	private VerticalLayout contentLayout;
	public WalletTransactionView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		
		update();
	}
	
	private void initWalletHistoryTable() {
		List<TopupWalletHistory> topups = ServiceInitator.getTransactionService().getAllTopupHistory(jwt);
		for(TopupWalletHistory h: topups){
			Item i = trxTable.addItem(h.getOid());
			i.getItemProperty(AMOUNT).setValue("Rp. "+Util.formatLocalAmount(h.getAmount()));
			i.getItemProperty(USER).setValue(h.getUser().getName());
			i.getItemProperty(TRX_DATE).setValue(Util.parseDate(h.getTopupDate()));
			i.getItemProperty(REF_BANK_NO).setValue(h.getReferenceBankNo());
			i.getItemProperty(TOPUP_ID).setValue(h.getTop_up_id());
		}
	}
	
	private VerticalLayout createMainContent() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setWidth(70, Unit.PERCENTAGE);
		
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Mendapatkan transaksi...");
        pb3.addStyleName("pb-center-align");
        Button topupButton = new Button("Top up");
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					UI.getCurrent().access(new Runnable() {
						
						@Override
						public void run() {
							trxTable = createTrxTable();
							initWalletHistoryTable();
							l.replaceComponent(pb3, trxTable);
							l.setComponentAlignment(topupButton, Alignment.BOTTOM_LEFT);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		
		topupButton.addStyleName(ValoTheme.BUTTON_TINY);
		topupButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		topupButton.addClickListener( e ->{
			Window w = new Window();
			w.setContent(new TopupWallet(null, WalletTransactionView.this));
			w.setClosable(true);
			w.setResizable(false);
			w.setDraggable(false);
			w.setModal(true);
			UI.getCurrent().addWindow(w);
		});
		
		l.addComponent(pb3);
		l.addComponent(topupButton);
		
		l.setExpandRatio(pb3, 1.0f);
		l.setExpandRatio(topupButton, 0.0f);
		
		l.setComponentAlignment(pb3, Alignment.MIDDLE_CENTER);
		l.setComponentAlignment(topupButton, Alignment.MIDDLE_CENTER);
		Thread as = new Thread(r);
		as.start();
		return l;
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(TOPUP_ID , String.class, null, "Topup id", null, Align.LEFT);
		t.addContainerProperty(TRX_DATE, String.class, null, "Topup date", null, Align.LEFT);
		t.addContainerProperty(USER , String.class, null, "User", null, Align.LEFT);
		t.addContainerProperty(AMOUNT , String.class, null, "Amount", null, Align.LEFT);
		t.addContainerProperty(REF_BANK_NO , String.class, null, "Ref bank no", null, Align.LEFT);

		return t;
	}
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	@Override
	public void update() {
		
		
		removeAllComponents();
		contentLayout = createMainContent();
		addComponent(contentLayout);
		setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
	}

}
