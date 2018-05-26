package com.warungikan.webapp.view.admin;

import java.util.List;

import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.ShopItemStock;

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
import com.warungikan.webapp.dialog.ShopItemForm;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.util.Util;

public class AgentStocksView extends VerticalLayout implements View, IParentWindowService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2617788425589408200L;
	private static final String AMOUNT = "amount";
	private static final String AGENT = "agent";
	private static final String ITEM = "item";
	private static final String ID = "email";
	
	private String jwt;
	private List<ShopItemStock> items;
	private Table t;
	public AgentStocksView() {
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		
		VerticalLayout l = createContent();
		addComponent(l);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		
	}
	
	private void initItems() {
		if(items!=null) items.clear();
		items = ServiceInitator.getShopItemService().getAllStocks(jwt);
		t.removeAllItems();
		for(ShopItemStock i: items){
			Item item = t.addItem(i.getOid());
			item.getItemProperty(ID).setValue(i.getAgent().getEmail());
			item.getItemProperty(AGENT).setValue(i.getAgent().getName());
			item.getItemProperty(AMOUNT).setValue(String.valueOf(i.getAmount()));
			item.getItemProperty(ITEM).setValue(i.getItem().getName());
		}
	}

	private VerticalLayout createContent() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setWidth(65, Unit.PERCENTAGE);
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Mendapatkan data...");
        pb3.addStyleName("pb-center-align");
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					UI.getCurrent().access(new Runnable() {

						@Override
						public void run() {
							t = createTrxTable();
							initItems();
							l.replaceComponent(pb3, t);
							l.setComponentAlignment(t, Alignment.MIDDLE_LEFT);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		l.addComponent(pb3);
		l.setExpandRatio(pb3, 1.0f);
		l.setComponentAlignment(pb3, Alignment.MIDDLE_CENTER);
		Thread t = new Thread(r);
		t.start();
		return l;
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(ID, String.class, null, "Email", null, Align.LEFT);
		t.addContainerProperty(AGENT, String.class, null, "Name", null, Align.LEFT);
		t.addContainerProperty(AMOUNT , String.class, null, "Amount", null, Align.LEFT);
		t.addContainerProperty(ITEM , String.class, null, "Item", null, Align.LEFT);

		return t;
	}
	@Override
	public void enter(ViewChangeEvent event) {

	}

	@Override
	public void update() {
		initItems();
	}

}
