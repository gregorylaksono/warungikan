package com.warungikan.webapp.view.agent;

import org.warungikan.db.model.User;

import com.google.gwt.maps.client.base.LatLng;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;

public class TransactionDetailMap extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630045303751526074L;
	private String jwt;
	private User customer;
	private Short status;

	public TransactionDetailMap(User customer, Short status){
		this.customer = customer;
		this.status = status;
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		setSpacing(true);
		HorizontalLayout addressBox = createHeaderBox();
		GoogleMap map = createMap();
		
		addComponent(addressBox);
		addComponent(map);
		setExpandRatio(addressBox, 0.0f);
		setExpandRatio(map, 1.0f);
	}

	private GoogleMap createMap() {
		GoogleMap addressMap = new GoogleMap(Constant.GMAP_API_KEY, null, "english");
		addressMap.setSizeFull();
		addressMap.setMinZoom(4);
		addressMap.setMaxZoom(16);
		addressMap.setZoom(16);
		LatLon latLng = new LatLon(customer.getLatitude(), customer.getLongitude());
		addressMap.addMarker(customer.getName(), latLng, false, null);
		return addressMap;
	}

	private HorizontalLayout createHeaderBox() {
		HorizontalLayout main = new HorizontalLayout();
		main.setWidth(100, Unit.PERCENTAGE);
		main.setSpacing(true);
		
		VerticalLayout addressBox = new VerticalLayout();
		addressBox.setSpacing(true);
		
		Label name = Factory.createLabel(customer.getName());
		Label mobileNo = Factory.createLabel(customer.getTelpNo());
		Label email = Factory.createLabel(customer.getAddress());
		addressBox.addComponent(name);
		addressBox.addComponent(mobileNo);
		addressBox.addComponent(email);
		if(customer.getAddressInfo() != null){
			Label addInfo = Factory.createLabel(customer.getAddressInfo());
			addressBox.addComponent(addInfo);	
		}
		
		ComboBox currentStatus = new ComboBox("Status transaksi");
		currentStatus.addItem(1);
		currentStatus.addItem(2);
		currentStatus.addItem(3);
		currentStatus.addItem(4);
		currentStatus.addItem(5);
		currentStatus.addItem(6);
		
		currentStatus.setItemCaption(1,"SENT" );
		currentStatus.setItemCaption(2, "PAID");
		currentStatus.setItemCaption(3, "PROCESSING");
		currentStatus.setItemCaption(4, "DELIVERING");
		currentStatus.setItemCaption(5, "RECEIVED");
		currentStatus.setItemCaption(6, "CANCELED");
		currentStatus.setNullSelectionAllowed(false);
		
		main.addComponent(addressBox);
		main.addComponent(currentStatus);
		
		main.setExpandRatio(addressBox, 1.0f);
		main.setExpandRatio(currentStatus, 0.0f);
		
		main.setComponentAlignment(addressBox, Alignment.MIDDLE_LEFT);
		main.setComponentAlignment(currentStatus, Alignment.MIDDLE_RIGHT);
		return main;
	}
}
