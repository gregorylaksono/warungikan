package com.warungikan.webapp.view.agent;

import java.util.List;

import org.warungikan.db.model.ShopItemStock;
import org.warungikan.db.model.TopupWalletHistory;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
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
import com.warungikan.webapp.view.admin.WalletTransactionView;

public class ItemStockView extends VerticalLayout implements View, IParentWindowService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3979994373250130294L;
	private static final String AMOUNT = "amount";
	private static final String ITEM = "item";
	private static final String PRICE = "price";
	private String jwt;
	private Table shopItem;

	public ItemStockView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		
		update();
	}
	
	private void initWalletHistoryTable() {
		List<ShopItemStock> stoks = ServiceInitator.getShopItemService().getStockByAgent(jwt);
		for(ShopItemStock h: stoks){
			Item i = shopItem.addItem(h.getOid());
			i.getItemProperty(AMOUNT).setValue(h.getAmount());
			i.getItemProperty(ITEM).setValue(h.getItem().getName());
			i.getItemProperty(PRICE).setValue("Rp. "+Util.formatLocalAmount(h.getItem().getPrice()));
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
        Button updateButton = new Button("Update stock");
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					UI.getCurrent().access(new Runnable() {
						
						@Override
						public void run() {
							shopItem = createTrxTable();
							initWalletHistoryTable();
							l.replaceComponent(pb3, shopItem);
							l.setComponentAlignment(updateButton, Alignment.BOTTOM_LEFT);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		
		
		updateButton.addStyleName(ValoTheme.BUTTON_TINY);
		updateButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		updateButton.addClickListener( e ->{
			Window w = new Window();
//			w.setContent(new TopupWallet(null, WalletTransactionView.this));
			w.setClosable(true);
			w.setResizable(false);
			w.setDraggable(false);
			w.setModal(true);
			UI.getCurrent().addWindow(w);
		});
		
		l.addComponent(pb3);
		l.addComponent(updateButton);
		
		l.setExpandRatio(pb3, 1.0f);
		l.setExpandRatio(updateButton, 0.0f);
		
		l.setComponentAlignment(pb3, Alignment.MIDDLE_CENTER);
		l.setComponentAlignment(updateButton, Alignment.MIDDLE_CENTER);
		Thread as = new Thread(r);
		as.start();
		return l;
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(ITEM , String.class, null, "Item", null, Align.LEFT);
		t.addContainerProperty(AMOUNT, String.class, null, "Jumah tersedia", null, Align.LEFT);
		t.addContainerProperty(PRICE, String.class, null, "Harga satuan", null, Align.LEFT);
		
		return t;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
