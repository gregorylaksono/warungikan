package com.warungikan.webapp.view.agent;

import java.util.Date;
import java.util.List;

import org.warungikan.db.model.ShopItemStock;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.dialog.StockItem;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;
import com.warungikan.webapp.window.ConfirmDialog;

public class AgentStockView extends VerticalLayout implements View, IParentWindowService {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9038235091447670937L;
	
	/**
	 * 
	 */
	private static final String ITEM = "name";
	private static final String AMOUNT = "amount";
	private static final String LAST_UDPATE = "last_update";
	private static final String UPDATE = "update";
	private static final String PRICE = "harga";

	private Table t;

	private String jwt;

	private ClickListener addStockListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			ConfirmDialog d = new ConfirmDialog(new StockItem(null, AgentStockView.this));
			d.show();
		}
	};


	@Override
	public void enter(ViewChangeEvent event) {
		setSpacing(true);
		setMargin(true);

	}

	public AgentStockView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		VerticalLayout content = new VerticalLayout();
		content.setSpacing(true);
		content.setSizeUndefined();
		t = createTabel();
		
		addStyleName("product-container");
		content.addComponent(t);

		Button createBtn = Factory.createButtonOk("Tambah stok");
		content.addComponent(createBtn);
		
		content.setExpandRatio(t, 1.0f);
		content.setExpandRatio(createBtn, 0.0f);
		
		addComponent(content);
		setComponentAlignment(content, Alignment.MIDDLE_CENTER);
		createBtn.addClickListener(addStockListener );
		update();
	}


	private Table createTabel() {
		Table t = new Table();
		t.setCaption("Stock anda saat ini");
		t.setWidth(650, Unit.PIXELS);
		t.setHeight(500, Unit.PIXELS);

		t.addContainerProperty(UPDATE, Button.class,null);
		t.addContainerProperty(LAST_UDPATE, String.class,null);
		t.addContainerProperty(AMOUNT, String.class,null);
		t.addContainerProperty(ITEM, String.class,null);
		t.addContainerProperty(PRICE, String.class,null);
		
		
		t.setColumnHeader(UPDATE, "Ubah");
		t.setColumnHeader(LAST_UDPATE, "Terakhir kali update");
		t.setColumnHeader(AMOUNT, "Jumlah saat ini");
		t.setColumnHeader(ITEM, "Item");
		t.setColumnHeader(PRICE, "Harga");
		
		return t;
	}

	@Override
	public void update() {
		t.removeAllItems();
		List<ShopItemStock> stocks = ServiceInitator.getShopItemService().getAllStocks(jwt);
		for(ShopItemStock s: stocks) {
			Item i = t.addItem(s.getOid());
			Date d = s.getLastModifiedDate() == null ? s.getCreationDate() : s.getLastModifiedDate();
			i.getItemProperty(ITEM).setValue(s.getItem().getName());
			i.getItemProperty(LAST_UDPATE).setValue(Util.parseDate(d));
			i.getItemProperty(AMOUNT).setValue(String.valueOf(s.getAmount()));
			i.getItemProperty(PRICE).setValue("Rp."+Util.formatLocalAmount(s.getItem().getPrice()));
			
			Button updateButton = Factory.createButtonNormal("Ubah");
			i.getItemProperty(UPDATE).setValue(updateButton);
			updateButton.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					ConfirmDialog d = new ConfirmDialog(new StockItem(s, AgentStockView.this));
					d.show();
				}
			});
		}
	}

}
