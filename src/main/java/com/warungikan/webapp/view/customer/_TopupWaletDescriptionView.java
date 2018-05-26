package com.warungikan.webapp.view.customer;

import java.text.DecimalFormat;
import java.util.List;

import org.warungikan.db.model.TopupWalletHistory;
import org.warungikan.db.model.Transaction;

import com.vaadin.data.Item;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
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
import com.warungikan.webapp.window.ConfirmDialog;

public class _TopupWaletDescriptionView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6185841848027736338L;

	private static final String DATE = null;
	private static final String AMOUNT = null;
	private static final String REF_NO = null;
	
	private String jwt;
	private List<TopupWalletHistory> topups;
	public _TopupWaletDescriptionView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		((MyUI)UI.getCurrent()).updateBalance();
		setMargin(true);
		setSpacing(true);
		
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);
		VerticalLayout walletLayout = createWalletLayout();
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Mengambil data topup...");
        pb3.addStyleName("pb-center-align");
		
        VerticalLayout parent = new VerticalLayout();
		
		Runnable l = new Runnable() {
			
			@Override
			public void run() {
				topups = ServiceInitator.getTransactionService().getTopupHistorySingleUser(jwt);
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
		
		Button confirmButton = new Button("Konfirmasi topup");
		confirmButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		confirmButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
		parent.addComponent(confirmButton);
		parent.setExpandRatio(pb3, 1.0f);
		parent.setExpandRatio(confirmButton, 0.0f);
		parent.setComponentAlignment(confirmButton, Alignment.BOTTOM_RIGHT);
		
		confirmButton.addClickListener(e->{
			ConfirmDialog d = new ConfirmDialog(confirmComponent());
			d.show();
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
		Button topupButton = Factory.createButtonOk("Top up saldo");
		
		layout.addComponent(topupButton);
		layout.setExpandRatio(topupButton, 0.0f);
		
		return layout;
	}

	private Table createTable() {
		Table t = new Table();
		t.setCaption("Pesanan saya");
		t.setHeight(300, Unit.PIXELS);
		t.addContainerProperty(DATE, String.class, null);
		t.addContainerProperty(AMOUNT, String.class, null);
		t.addContainerProperty(REF_NO, String.class, null);
				
		t.setColumnHeader(DATE, "Tanggal");
		t.setColumnHeader(AMOUNT, "Jumlah");
		t.setColumnHeader(REF_NO, "Ref no");
		
		for(TopupWalletHistory trx : topups){
			Item i = t.addItem(trx.getOid());
			i.getItemProperty(DATE).setValue(Util.parseDate(trx.getCreationDate()));
			i.getItemProperty(AMOUNT).setValue("Rp. "+Util.formatLocalAmount(trx.getAmount()));
			i.getItemProperty(REF_NO).setValue(trx.getReferenceBankNo());
		}
		return t;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
	
	private FormLayout confirmComponent(){
		FormLayout f = new FormLayout();
		TextField bankRefNoTxt = new TextField("Bank ref no.");
		DateField dateF = new DateField("Waktu");
		TextField amountT = new TextField("Jumlah");
		HorizontalLayout l = new HorizontalLayout();
		l.setSpacing(true);
		
		dateF.setResolution(Resolution.HOUR);
		dateF.setDateFormat("dd-MM-yyyy HH:mm");
		
		Button confirm = Factory.createButtonNormal("Lakukan konfirmasi");
		Button cancel = Factory.createButtonDanger("Batalkan");
		l.addComponent(confirm);
		l.addComponent(cancel);
		
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
			TopupWalletHistory h = new TopupWalletHistory();
			h.setAmount(Long.parseLong(amountT.getValue()));
			h.setReferenceBankNo(bankRefNoTxt.getValue());
			h.setTopupDate(dateF.getValue());
			
			Boolean result = ServiceInitator.getTransactionService().doTopup(jwt, h);
			if(result){
				
			}
		});
		return f;
	}

}
