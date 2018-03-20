package com.warungikan.webapp.view;


import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.AgentProductComponent;
import com.warungikan.webapp.model.AgentProduct;
import com.warungikan.webapp.model.ShopItem;
import com.warungikan.webapp.model.AgentProduct.AVAILABILITY;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;

public class ShippingAddressView extends VerticalLayout implements View{
	//	private GoogleMap addressMap;
	private List<ShopItem> items;
	private GridLayout agentsLayout;

	public ShippingAddressView() {
		
		this.items = ((MyUI) UI.getCurrent()).getItems();
		setHeight(800, Unit.PIXELS);
		//		addressMap = createGoogleMap();
		
		List<AgentProduct> agents = new ArrayList<>();
		agents.add(new AgentProduct("Greg", AVAILABILITY.EMPTY, "Pesona Depok", "00928237379"));
		agents.add(new AgentProduct("Tomi", AVAILABILITY.FULL, "Beji", "0228235349"));
		agents.add(new AgentProduct("Anggi", AVAILABILITY.FULL, "Sawangan", "9473622"));
		agents.add(new AgentProduct("Toto", AVAILABILITY.PARTLY, "Tanjung barat", "66432233"));
		agents.add(new AgentProduct("Sara", AVAILABILITY.FULL, "Pejaten", "774354322"));
		agents.add(new AgentProduct("Andi", AVAILABILITY.PARTLY, "Pasar Minggu", "66880032"));
		agents.add(new AgentProduct("Mei", AVAILABILITY.EMPTY, "Cilandak", "335689003"));
		agents.add(new AgentProduct("Dodo", AVAILABILITY.PARTLY, "Pluit", "466722011"));
		
		agentsLayout = createAgentsLayout(agents);

		HorizontalLayout myData = createMyData();
		
		
		addComponent(myData);
		addComponent(agentsLayout);


		setExpandRatio(myData, 0.0f);
		setExpandRatio(agentsLayout, 1.0f);
		setComponentAlignment(myData, Alignment.TOP_CENTER);
		setComponentAlignment(agentsLayout, Alignment.MIDDLE_CENTER);

		setMargin(false);
		setSpacing(true);
	}

	private HorizontalLayout createMyData() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		GridLayout grid = createItemsGrid();
		layout.addComponent(grid);
		
		VerticalLayout addressLayout = new VerticalLayout();
		addressLayout.setMargin(true);
		addressLayout.addStyleName("product-container");
		addressLayout.setSpacing(true);
		addressLayout.setHeight(100, Unit.PERCENTAGE);
		addressLayout.setWidth(300, Unit.PERCENTAGE);
		Label nameL = Factory.createLabelHeaderNormal("Alfonso Herman");
		Label fullAddressL = Factory.createLabel("Jl. Kavling timur no. 34a Cilandak barat, Jakarta selatan DKI Jakarta 15311");
		Label telpNo = Factory.createLabel("0812 9938270");
		Button changeButton = Factory.createButtonOk("Ubah");
		changeButton.addClickListener(e ->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_PROFILE);
		});
		
		addressLayout.addComponent(nameL);
		addressLayout.addComponent(fullAddressL);
		addressLayout.addComponent(telpNo);
		addressLayout.addComponent(changeButton);
		addressLayout.setComponentAlignment(changeButton, Alignment.BOTTOM_LEFT);
		
		layout.addComponent(addressLayout);
		layout.setComponentAlignment(grid, Alignment.TOP_LEFT);
		layout.setComponentAlignment(addressLayout, Alignment.TOP_RIGHT);
		layout.setExpandRatio(grid, 0.0f);
		layout.setExpandRatio(addressLayout, 1.0f);
		return layout;
	}

	private GridLayout createAgentsLayout(List<AgentProduct> agents) {
		GridLayout g = new GridLayout(4, 6);
		g.setSpacing(true);
		g.setMargin(true);
		g.addStyleName("product-container");
		g.setMargin(true);
		for(AgentProduct a: agents) {
			AgentProductComponent comp = new AgentProductComponent(a);
			g.addComponent(comp);
		}
		
		return g;
	}

	//	private GoogleMap createGoogleMap() {
	//		GoogleMap map = new GoogleMap(Constant.GMAP_API_KEY, null, "english");
	//		LatLon l = new LatLon(-6.393239, 106.824532);
	//		GoogleMapMarker marker = map.addMarker("DRAGGABLE: "+"RUMAHKU", l, true, "VAADIN/fish.png");
	//		map.addMarkerDragListener((e,v)->{
	//			
	//		});
	//		
	//		marker.setCaption("GREG");
	//		marker.setAnimationEnabled(true);
	//		map.setSizeFull();
	//		map.setMinZoom(4);
	//		map.setMaxZoom(16);
	//		map.setCenter(l);
	//		map.setZoom(16);
	//		return map;
	//	}

	private GridLayout createItemsGrid() {
		GridLayout t = new GridLayout(2, items.size() + 2);
		t.setSpacing(true);
		t.setMargin(true);
		t.setWidth(280, Unit.PIXELS);
		t.setHeight(100, Unit.PERCENTAGE);
		Label productLabel = Factory.createLabelHeader("Keranjang saya");
		Label priceItem = Factory.createLabelHeader("Total harga");
		t.addComponent(productLabel);
		t.addComponent(priceItem);
		int totalAll = 0;
		for(ShopItem i : items) {
			String product = i.getFish().getName() + " ("+i.getCount()+")";
			Integer total = i.getCount() * Integer.parseInt(i.getFish().getPrice().replace("Rp", "").replace(".", "").replace(" ", ""));
			totalAll = total + totalAll;
			Label productL = Factory.createLabel(product);
			Label totalL = Factory.createLabel("Rp. "+ShoppingCartView.decimalFormat.format(total));
			t.addComponent(productL);
			t.addComponent(totalL);
		}
		Label empty = new Label("");
		Label totalAllL = Factory.createLabel("Rp. "+ShoppingCartView.decimalFormat.format(totalAll));
		t.addComponent(empty);
		t.addComponent(totalAllL);
		return t;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
