package com.warungikan.webapp.view.customer;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.warungikan.api.model.response.AgentStock;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.AgentProductComponent;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.AgentProduct;
import com.warungikan.webapp.model.ShopItemCart;
import com.warungikan.webapp.service.ITransactionService;
import com.warungikan.webapp.service.TransactionService;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;

public class ShippingAddressView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 854928347753429074L;
	//	private GoogleMap addressMap;
	private List<ShopItemCart> items;
	private AbstractLayout agentsLayout;
	ITransactionService trxService = new TransactionService();
	private String jwt;
	private User user;

	public ShippingAddressView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		this.user = ServiceInitator.getUserService().getUser(jwt);
		this.items = ((MyUI) UI.getCurrent()).getItemsCart();
		setHeight(800, Unit.PIXELS);
		//		addressMap = createGoogleMap();
		Set<TransactionDetail> details = convertObject(items);
		List<AgentStock> result = trxService.getAgentBasedCustomerLocation(jwt, details);
		List<AgentProduct> agents = new ArrayList<>();
		for(AgentStock a : result) {
			AgentProduct p = new AgentProduct( Long.parseLong(a.getTotal_distance()), a.getTransport_price_per_km(), a.getUser());
			agents.add(p);
		}


		ProgressBar pb3 = new ProgressBar();
		pb3.setIndeterminate(true);
		pb3.setCaption("Mencari agent terdekat...");
		pb3.addStyleName("pb-center-align");
		Runnable l = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					UI.getCurrent().access(new Runnable() {

						@Override
						public void run() {
							agentsLayout = createAgentsLayout(agents);
							replaceComponent(pb3, agentsLayout);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		HorizontalLayout myData = createMyData();


		addComponent(myData);
		addComponent(pb3);


		setExpandRatio(myData, 0.0f);
		setExpandRatio(pb3, 1.0f);
		setComponentAlignment(myData, Alignment.TOP_CENTER);
		setComponentAlignment(pb3, Alignment.TOP_CENTER);

		setMargin(false);
		setSpacing(true);
		Thread as = new Thread(l);
		as.start();
	}

	private Set<TransactionDetail> convertObject(List<ShopItemCart> items2) {
		Set<TransactionDetail> details = new HashSet<>();
		for(ShopItemCart item: items2) {
			TransactionDetail d = new TransactionDetail();
			d.setAmount(item.getCount());
			d.setItem(item.getFish());
			details.add(d);
		}
		return details;
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
		Label nameL = Factory.createLabelHeaderNormal(user.getName());
		Label fullAddressL = Factory.createLabel(user.getAddress());
		Label telpNo = Factory.createLabel(user.getTelpNo());
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

	private AbstractLayout createAgentsLayout(List<AgentProduct> agents) {
		agents = agents.stream().sorted((o1, o2) -> o1.getDistance().compareTo(o2.getDistance())).collect(Collectors.toList());
		if(agents.size() > 0){
			GridLayout g = new GridLayout(3, 6);
			g.setWidth(780, Unit.PIXELS);
			g.setSpacing(true);
			g.setMargin(true);
			g.addStyleName("product-container");
			g.setMargin(true);
			for(AgentProduct a: agents) {
				AgentProductComponent comp = new AgentProductComponent(a);
				g.addComponent(comp);
			}
			return g;
		}else{
			VerticalLayout l = new VerticalLayout();
			l.setWidth(780, Unit.PIXELS);
			l.setSpacing(true);
			l.setMargin(true);
			l.addStyleName("product-container");
			Label la = new Label("Tidak ditemukan agent");
			la.setWidth(null);
			l.addComponent(la);
			l.setComponentAlignment(la, Alignment.MIDDLE_CENTER);
			return l;
		}
	}

	public static void main(String[] args) {
		Double a = 0.134435345;
		System.out.println(Math.ceil(a));


	}

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
		Long totalAll = new Long(0);
		for(ShopItemCart i : items) {
			String product = i.getFish().getName() + " ("+i.getCount()+")";
			Long total = i.getCount() * i.getFish().getPrice();
			totalAll = total + totalAll;
			Label productL = Factory.createLabel(product);
			Label totalL = Factory.createLabel("Rp. "+Util.formatLocalAmount(total));
			t.addComponent(productL);
			t.addComponent(totalL);
		}
		Label empty = new Label("");
		Label totalAllL = Factory.createLabel("Rp. "+Util.formatLocalAmount(totalAll));
		t.addComponent(empty);
		t.addComponent(totalAllL);
		return t;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
