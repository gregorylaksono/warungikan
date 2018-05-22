package com.warungikan.webapp.view.admin;

import java.util.List;

import org.warungikan.db.model.ShopItem;

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

public class ShopItemView extends VerticalLayout implements View, IParentWindowService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2617788425589408200L;
	private static final String DESCRIPTION = "description";
	private static final String PRICE = "price";
	private static final String URL = "url";
	private static final String NAME = "name";
	private static final String WEIGHT = "weight";
	private String jwt;
	private List<ShopItem> items;
	private Table t;
	public ShopItemView() {
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		
		VerticalLayout l = createContent();
		addComponent(l);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		
//		initItems();
	}
	
	private void initItems() {
		if(items!=null) items.clear();
		items = ServiceInitator.getShopItemService().getAllShopItem(jwt);
		t.removeAllItems();
		for(ShopItem i: items){
			Item item = t.addItem(i.getId());
			item.getItemProperty(NAME).setValue(i.getName());
			item.getItemProperty(URL).setValue(i.getUrl());
			item.getItemProperty(PRICE).setValue("Rp. "+Util.formatLocalAmount(i.getPrice()));
			item.getItemProperty(WEIGHT).setValue(i.getWeight());
			item.getItemProperty(DESCRIPTION).setValue(i.getDescription());
		}
	}

	private VerticalLayout createContent() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setWidth(70, Unit.PERCENTAGE);
		Button createNewItemBtn = new Button("Tambah baru");
		createNewItemBtn.addStyleName(ValoTheme.BUTTON_TINY);
		createNewItemBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		createNewItemBtn.addClickListener( e ->{
			Window w = new Window();
			w.setContent(new ShopItemForm(ShopItemView.this));
			w.setClosable(true);
			w.setResizable(false);
			w.setDraggable(false);
			w.setModal(true);
			UI.getCurrent().addWindow(w);
		});
		
		ProgressBar pb3 = new ProgressBar();
        pb3.setIndeterminate(true);
        pb3.setCaption("Menangkap ikan dulu...");
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
							l.setComponentAlignment(createNewItemBtn, Alignment.MIDDLE_LEFT);
							createNewItemBtn.setEnabled(true);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		createNewItemBtn.setEnabled(false);
		l.addComponent(pb3);
		l.addComponent(createNewItemBtn);
		
		l.setExpandRatio(pb3, 1.0f);
		l.setExpandRatio(createNewItemBtn, 0.0f);
		
		l.setComponentAlignment(pb3, Alignment.MIDDLE_CENTER);
		l.setComponentAlignment(createNewItemBtn, Alignment.MIDDLE_CENTER);
		Thread t = new Thread(r);
		t.start();
		return l;
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(NAME, String.class, null, "Name", null, Align.LEFT);
		t.addContainerProperty(URL , String.class, null, "URL", null, Align.LEFT);
		t.addContainerProperty(PRICE , String.class, null, "Price", null, Align.LEFT);
		t.addContainerProperty(WEIGHT , String.class, null, "Weight", null, Align.LEFT);
		t.addContainerProperty(DESCRIPTION , String.class, null, "Description", null, Align.LEFT);

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
