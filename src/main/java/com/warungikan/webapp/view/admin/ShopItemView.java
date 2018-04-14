package com.warungikan.webapp.view.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.dialog.ShopItemForm;

public class ShopItemView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2617788425589408200L;
	private static final String DESCRIPTION = "description";
	private static final String PRICE = "price";
	private static final String URL = "url";
	private static final String NAME = "name";
	
	public ShopItemView() {
		addStyleName("product-container");
		setMargin(new MarginInfo(true, false));
		setSizeFull();
		
		VerticalLayout l = createContent();
		addComponent(l);
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
	}
	
	private VerticalLayout createContent() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setWidth(70, Unit.PERCENTAGE);
		Table trxTable = createTrxTable();
		Button topupButton = new Button("Top up");
		topupButton.addStyleName(ValoTheme.BUTTON_TINY);
		topupButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		topupButton.addClickListener( e ->{
			Window w = new Window();
			w.setContent(new ShopItemForm());
			w.setClosable(true);
			w.setResizable(false);
			w.setDraggable(false);
			w.setModal(true);
			UI.getCurrent().addWindow(w);
		});
		
		l.addComponent(trxTable);
		l.addComponent(topupButton);
		
		l.setExpandRatio(trxTable, 1.0f);
		l.setExpandRatio(topupButton, 0.0f);
		
		l.setComponentAlignment(trxTable, Alignment.MIDDLE_LEFT);
		l.setComponentAlignment(topupButton, Alignment.MIDDLE_LEFT);
		return l;
	}

	private Table createTrxTable() {
		Table t = new Table();
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(NAME, String.class, null, "Name", null, Align.LEFT);
		t.addContainerProperty(URL , String.class, null, "URL", null, Align.LEFT);
		t.addContainerProperty(PRICE , String.class, null, "Price", null, Align.LEFT);
		t.addContainerProperty(DESCRIPTION , String.class, null, "Description", null, Align.LEFT);

		return t;
	}
	@Override
	public void enter(ViewChangeEvent event) {

	}

}
