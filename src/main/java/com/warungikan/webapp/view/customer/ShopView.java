package com.warungikan.webapp.view.customer;

import java.util.ArrayList;
import java.util.List;

import org.warungikan.db.model.ShopItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.ShopItemComponent;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.FishShopItem;
import com.warungikan.webapp.util.Constant;


public class ShopView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373223413841687505L;
	private String jwt;
	private List<ShopItem> items;
	
	public ShopView() {
		setWidth(100, Unit.PERCENTAGE);
		setMargin(new MarginInfo(false, true, false, true));
		setSpacing(true);
		jwt = ((MyUI)UI.getCurrent()).getJwt();
		items = ServiceInitator.getShopItemService().getAllShopItem(jwt);
		
		GridLayout content = itemContent(); 
		addComponent(content);
		setComponentAlignment(content, Alignment.BOTTOM_CENTER);
	}

	private GridLayout itemContent() {
		
		GridLayout grid = new GridLayout(4, 6);
		grid.addStyleName("product-container");
		grid.setMargin(true);
		grid.setSpacing(true);
		
		
		((MyUI)UI.getCurrent()).setItems(items);
		for(ShopItem i : items){
			ShopItemComponent item1 = new ShopItemComponent(i);
			grid.addComponent(item1);
			grid.setComponentAlignment(item1, Alignment.MIDDLE_CENTER);
		}

		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setSpacing(true);
		
		return grid;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
