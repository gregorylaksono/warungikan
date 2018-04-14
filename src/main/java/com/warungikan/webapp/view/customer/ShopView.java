package com.warungikan.webapp.view.customer;

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
		
		List<FishShopItem> l = new ArrayList<>();
		l.add(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Gindara", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		l.add(new FishShopItem("http://warungikan.com/images/produk/WIN002.jpg", "Cobia", "Netto : 200 Gr", "Rp. 30.000", "blabla"));
		l.add(new FishShopItem("http://warungikan.com/images/produk/WIN003.jpg", "Kakap Merah Chinaman", "Netto : 150 Gr", "Rp. 30.000", "blabla"));
		l.add(new FishShopItem("http://warungikan.com/images/produk/WIN004.jpg", "Sunu Ekor Bulan (Utuh)", "Netto : 300-500 Gr", "Rp. 60.000", "blabla"));
		l.add(new FishShopItem("http://warungikan.com/images/produk/WIN005.jpg", "Sunu Lodi (Utuh)", "Netto : 300-500 Gr", "Rp. 60.000", "blabla"));
		l.add(new FishShopItem("http://warungikan.com/images/produk/WIN001.jpg", "Tuna Sirip Kuning", "Netto : 100 Gr", "Rp. 25.000", "blabla"));
		
		int in =0;
		List<ShopItem> it = new ArrayList<>();
		for(FishShopItem i : l){
			in++;
			if(in<4){
				it.add(new ShopItem(i, Integer.parseInt(String.valueOf(Math.round(Math.random()*10)))));				
			}
		}
		((MyUI)UI.getCurrent()).setItems(it);
		for(FishShopItem i : l){
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
