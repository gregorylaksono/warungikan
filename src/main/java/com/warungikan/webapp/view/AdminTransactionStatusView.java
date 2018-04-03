package com.warungikan.webapp.view;

import java.util.Date;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.util.Constant;

public class AdminTransactionStatusView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480699005062736943L;
	private static final String STATUS = "status";
	private static final String ADDRESS = "addr";
	private static final String DETAILS = "detail_trx";
	private static final String AMOUNT = "amount";
	private static final String CUSTOMER = "cust";
	private static final String TRANSACTION_ON = "trxOn";


	@Override
	public void enter(ViewChangeEvent event) {
		setSpacing(true);
		setMargin(true);

	}

	public AdminTransactionStatusView() {
		Table t = createTabel();
		FormLayout form = createFormLayout();
		addStyleName("product-container");
		HorizontalLayout l = new HorizontalLayout();
		l.setSpacing(true);
		l.setMargin(true);
		l.setSizeFull();
		l.addComponent(t);
		l.addComponent(form);
		l.setExpandRatio(t, 0.0f);
		l.setExpandRatio(form, 1.0f);
		
		addComponent(l);
		
		setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		
	}


	private Table createTabel() {
		Table t = new Table();
		t.setWidth(650, Unit.PIXELS);
		t.setHeight(500, Unit.PIXELS);

		t.addContainerProperty(TRANSACTION_ON, Date.class,null);
		t.addContainerProperty(CUSTOMER, String.class,null);
		t.addContainerProperty(AMOUNT, String.class,null);
		t.addContainerProperty(DETAILS, Button.class,null);
		t.addContainerProperty(ADDRESS, Button.class,null);
		t.addContainerProperty(STATUS, Label.class,null);
		
		t.setColumnHeader(TRANSACTION_ON, "Tanggal");
		t.setColumnHeader(CUSTOMER, "Customer");
		t.setColumnHeader(AMOUNT, "Jumlah");
		t.setColumnHeader(DETAILS, "Pesanan");
		t.setColumnHeader(ADDRESS, "Alamat");
		t.setColumnHeader(STATUS, "Status");
		
		return t;
	}
	
	private FormLayout createFormLayout() {
		FormLayout l = new FormLayout();
		l.setMargin(true);
		l.setSpacing(true);
		
		ComboBox statusCb = new ComboBox("Status");
		statusCb.addItem("processing");
		statusCb.addItem("finished");
		statusCb.addItem("on delivery");
		statusCb.select("processing");
		statusCb.setNullSelectionAllowed(false);
		
		TextField costTf = new TextField("Biaya");
		costTf.setValue("Rp. 300.000");
		
		TextArea address = new TextArea("Alamat");
		
		Button locationClick = new Button("Lihat lokasi");
		locationClick.addStyleName(ValoTheme.BUTTON_TINY);
		locationClick.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		locationClick.addClickListener(e ->{
			Window w = new Window();
			w.setContent(createGoogleMap("Lokasi"));
			w.setModal(true);
			w.setClosable(true);
			w.setResizable(true);
			UI.getCurrent().addWindow(w);
			
		});
		
		Table t = new Table("Pesanan");
		t.addContainerProperty("ikan", String.class, null);
		t.addContainerProperty("jumlah", String.class, null);
		t.addContainerProperty("harga saturan", String.class, null);
		t.setHeight(200, Unit.PIXELS);
		
		Button cancel = new Button("Cancel pesanan");
		cancel.addStyleName(ValoTheme.BUTTON_TINY);
		cancel.addStyleName(ValoTheme.BUTTON_DANGER);
		
		Button update = new Button("Update");
		update.addStyleName(ValoTheme.BUTTON_TINY);
		update.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		HorizontalLayout hor = new HorizontalLayout();
		hor.setWidth(100, Unit.PERCENTAGE);
		hor.addComponent(cancel);
		hor.addComponent(update);
	
		l.addComponent(statusCb);
		l.addComponent(costTf);
		l.addComponent(address);
		l.addComponent(locationClick);
		l.addComponent(t);
		l.addComponent(hor);
		return l;
	}
	
	private  GoogleMap createGoogleMap(String caption) {
		 GoogleMap addressMap = new GoogleMap(Constant.GMAP_API_KEY, null, "english");
		 addressMap.setCaption(caption);
		 LatLon point = new LatLon(-6.256965, 106.828700);
		 addressMap.addMarker("NOT DRAGGABLE: TEST", point, false, null);
		 addressMap.setCenter(point);
		 return addressMap;
	}

}
