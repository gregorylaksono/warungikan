package com.warungikan.webapp.view;

import java.util.ArrayList;
import java.util.List;

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
import com.warungikan.webapp.model.FishShopItem;
import com.warungikan.webapp.model.ShopItem;
import com.warungikan.webapp.util.Constant;


public class ShopView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373223413841687505L;
	
	public ShopView() {
		setWidth(100, Unit.PERCENTAGE);
		setMargin(new MarginInfo(false, true, false, true));
		setSpacing(true);
		
		GridLayout content = itemContent(); 
		addComponent(content);
		setComponentAlignment(content, Alignment.BOTTOM_CENTER);
	}

	private GridLayout itemContent() {
		
		GridLayout grid = new GridLayout(4, 6);
		grid.addStyleName("product-container");
		grid.setMargin(true);
		grid.setSpacing(true);
		
		ShopItemComponent item1 = new ShopItemComponent(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		ShopItemComponent item2 = new ShopItemComponent(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		ShopItemComponent item3 = new ShopItemComponent(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		ShopItemComponent item4 = new ShopItemComponent(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		ShopItemComponent item5 = new ShopItemComponent(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		ShopItemComponent item6 = new ShopItemComponent(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		grid.addComponent(item1);
		grid.addComponent(item2);
		grid.addComponent(item3);
		grid.addComponent(item4);
		grid.addComponent(item5);
		grid.addComponent(item6);
		
		grid.setComponentAlignment(item1, Alignment.MIDDLE_CENTER);
		grid.setComponentAlignment(item2, Alignment.MIDDLE_CENTER);
		grid.setComponentAlignment(item3, Alignment.MIDDLE_CENTER);
		grid.setComponentAlignment(item4, Alignment.MIDDLE_CENTER);
		grid.setComponentAlignment(item5, Alignment.MIDDLE_CENTER);
		grid.setComponentAlignment(item6, Alignment.MIDDLE_CENTER);
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setSpacing(true);
		
		return grid;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
