package com.warungikan.webapp.view.customer;

import java.util.List;

import org.warungikan.db.model.TopupWalletHistory;

import com.vaadin.data.Item;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.dialog.TopupWallet;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;
import com.warungikan.webapp.window.ConfirmDialog;

public class MyWalletTransactionView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926077373993667685L;
	private static final String AMOUNT = "amount";
	private static final String TRX_DATE = "topup-date";
	private static final String REF_BANK_NO = "ref_bank_no";
	private static final String TOPUP_ID = "top_up_id";
	private Table trxTable;
	private String jwt;
	private VerticalLayout contentLayout;
	public MyWalletTransactionView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		createMainContent();
	}
	
	private void initWalletHistoryTable() {
		trxTable.removeAllItems();
		List<TopupWalletHistory> topups = ServiceInitator.getTransactionService().getTopupHistorySingleUser(jwt);
		for(TopupWalletHistory h: topups){
			Item i = trxTable.addItem(h.getOid());
			i.getItemProperty(AMOUNT).setValue("Rp. "+Util.formatLocalAmount(h.getAmount()));
			i.getItemProperty(TRX_DATE).setValue(Util.parseDate(h.getTopupDate()));
			i.getItemProperty(REF_BANK_NO).setValue(h.getReferenceBankNo());
			i.getItemProperty(TOPUP_ID).setValue(h.getTop_up_id());
		}
	}
	
	private void createMainContent() {
		VerticalLayout l = new VerticalLayout();
		l.setSizeUndefined();
		l.setSpacing(true);
		Button topup = Factory.createButtonNormal("Konfirmasi topup");
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Mendapatkan transaksi...");
        pb3.addStyleName("pb-center-align");
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
							l.setComponentAlignment(topup, Alignment.MIDDLE_LEFT);
							topup.setEnabled(true);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		topup.addClickListener(e->{
			ConfirmDialog d = new ConfirmDialog(confirmComponent());
			d.show();
		});
		topup.setEnabled(false);
		l.addComponent(pb3);
		l.addComponent(topup);
		l.setExpandRatio(pb3, 1.0f);
		l.setExpandRatio(topup, 0.0f);
		l.setComponentAlignment(pb3, Alignment.MIDDLE_CENTER);
		l.setComponentAlignment(topup, Alignment.MIDDLE_LEFT);
		Thread as = new Thread(r);
		as.start();
		
		addComponent(l);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(500, Unit.PIXELS);
		t.addContainerProperty(TOPUP_ID, String.class, null, "Topup id", null, Align.LEFT);
		t.addContainerProperty(TRX_DATE, String.class, null, "Topup date", null, Align.LEFT);
		t.addContainerProperty(AMOUNT , String.class, null, "Amount", null, Align.LEFT);
		t.addContainerProperty(REF_BANK_NO , String.class, null, "Ref bank no", null, Align.LEFT);

		return t;
	}
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
	private FormLayout confirmComponent(){
		FormLayout f = new FormLayout();
		f.setWidth(350, Unit.PIXELS);
		TextField bankRefNoTxt = new TextField("Bank ref no.");
		DateField dateF = new DateField("Waktu");
		TextField amountT = new TextField("Jumlah");
		HorizontalLayout l = new HorizontalLayout();
		l.setSpacing(true);
		
		dateF.setResolution(Resolution.HOUR);
		dateF.setDateFormat("dd-MM-yyyy HH:mm");
		
		Button confirm = Factory.createButtonNormal("Lakukan konfirmasi");
		Button cancel = Factory.createButtonDanger("Batalkan");
		l.addComponent(cancel);
		l.addComponent(confirm);
		
		f.addComponent(bankRefNoTxt);
		f.addComponent(dateF);
		f.addComponent(amountT);
		f.addComponent(l);
		
		bankRefNoTxt.setRequired(true);
		dateF.setRequired(true);
		amountT.setRequired(true);
		
		bankRefNoTxt.setValidationVisible(true);
		dateF.setValidationVisible(true);
		amountT.setValidationVisible(true);
		
		amountT.addValidator(new RegexpValidator(Constant.VALIDATOR_REGEX_AMOUNT, "Jumlah musti format numeric"));
		bankRefNoTxt.addValidator(new StringLengthValidator("Minimal character 3. Max 10", 3, 10, false));
		
		cancel.addClickListener( e->{
			((MyUI)UI.getCurrent()).closeWindow();
		});
		
		confirm.addClickListener( e->{
			
			amountT.validate();
			bankRefNoTxt.validate();
			dateF.validate();
			
			TopupWalletHistory h = new TopupWalletHistory();
			h.setAmount(Long.parseLong(amountT.getValue()));
			h.setReferenceBankNo(bankRefNoTxt.getValue());
			h.setTopupDate(dateF.getValue());
			
			Boolean result = ServiceInitator.getTransactionService().doTopup(jwt, h);
			((MyUI)UI.getCurrent()).closeWindow();
			if(result){
				initWalletHistoryTable();
				Notification.show("Konfirmasi berhasil dikirim.", Type.HUMANIZED_MESSAGE);
			}else{
				Notification.show("Konfirmasi tidak berhasil dilakukan. Mohon hub admin kami", Type.HUMANIZED_MESSAGE);
			}
		});
		return f;
	}


}
