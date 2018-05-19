package com.warungikan.webapp.view.agent;

import java.util.List;

import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.TransactionState;
import org.warungikan.db.model.User;

import com.google.gwt.maps.client.base.LatLng;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.component.service.IParentWindowService;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.manager.TransactionManagerImpl;
import com.warungikan.webapp.manager.TransactionManagerImpl.TrxState;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;

public class TransactionDetailMap extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630045303751526074L;
	private static final String NAME = "name";
	private static final String AMOUNT = "amount";
	private static final String PRICE = "price";
	private String jwt;
	private User customer;
	private Short status;
	private ComboBox currentStatus;
	private Transaction trx;
	private IParentWindowService parent;
	private List<TransactionDetail> details;

	public TransactionDetailMap(Transaction trxId, User customer, Short status, IParentWindowService parent){
		this.customer = customer;
		this.status = status;
		this.trx = trxId;
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		setSpacing(true);
		setMargin(true);
		setWidth(600, Unit.PIXELS);
		setWidth(500, Unit.PIXELS);
		details = ServiceInitator.getTransactionService().getTransactionDetail(jwt, trx.getTransactionId());
		HorizontalLayout addressBox = createHeaderBox();
		GoogleMap map = createMap();
		this.parent = parent;
		Button b = Factory.createButtonOk("Simpan");
		Table t = createTable();
		b.addClickListener(e->{
			Short value = (Short) currentStatus.getValue();
			TrxState state = null;
			switch(value){
			case 3: state = TransactionManagerImpl.TrxState.PROCESSING; break;
			case 4: state = TransactionManagerImpl.TrxState.DELIVERING; break;
			case 5: state = TransactionManagerImpl.TrxState.RECEIVING; break;
			case 6: state = TransactionManagerImpl.TrxState.CANCEL; break;
			default: state = null;
			}
			Boolean isSuccess = ServiceInitator.getTransactionService().markTransaction(jwt, String.valueOf(trxId), state);
			if(isSuccess == null){
				Notification.show("Status transaksi tidak dapat diubah", Type.HUMANIZED_MESSAGE);
				((MyUI)UI.getCurrent()).closeWindow();
				return;
			}
			if(isSuccess){
				Notification.show("Status transaksi berhasil diubah", Type.HUMANIZED_MESSAGE);
				((MyUI)UI.getCurrent()).closeWindow();
				parent.update();
			}
		});
		addComponent(addressBox);
		addComponent(t);
		addComponent(map);
		addComponent(b);
		setComponentAlignment(t, Alignment.MIDDLE_CENTER);
		setExpandRatio(addressBox, 0.0f);
		setExpandRatio(map, 1.0f);
		setExpandRatio(b, 0.0f);
		setComponentAlignment(b, Alignment.BOTTOM_RIGHT);
	}

	private GoogleMap createMap() {
		GoogleMap addressMap = new GoogleMap(Constant.GMAP_API_KEY, null, "english");
		addressMap.setSizeFull();
		addressMap.setMinZoom(4);
		addressMap.setMaxZoom(16);
		addressMap.setZoom(16);
		LatLon latLng = new LatLon(customer.getLatitude(), customer.getLongitude());
		addressMap.addMarker(customer.getName(), latLng, false, null);
		addressMap.setCenter(latLng);
		
		return addressMap;
	}
	
	private Table createTable() {
		Table t = new Table();
		t.setHeight(200, Unit.PIXELS);
		t.addContainerProperty(NAME, String.class, null, "Item", null, Align.LEFT);
		t.addContainerProperty(AMOUNT, String.class, null, "Jumlah", null, Align.LEFT);
		t.addContainerProperty(PRICE, String.class, null, "Total harga", null, Align.LEFT);

		long total = 0;
		for(TransactionDetail d: details){
			Item i = t.addItem(d.getOid());
			i.getItemProperty(NAME).setValue(d.getItem().getName());
			i.getItemProperty(AMOUNT).setValue(String.valueOf(d.getAmount()));
			Long price = d.getAmount() * d.getItem().getPrice();
			total = price + total;
			i.getItemProperty(PRICE).setValue("Rp."+(Util.formatLocalAmount(price)));
		}

		Double d = Util.calculateDistance(trx.getDistance());
		Item trxItem = t.addItem(trx.getTransactionId());
		trxItem.getItemProperty(NAME).setValue("Biaya transport");
		trxItem.getItemProperty(AMOUNT).setValue(String.valueOf(d)+" Km");
		trxItem.getItemProperty(PRICE).setValue("Rp. "+Util.formatLocalAmount(trx.getTransportPrice()));

		t.setFooterVisible(true);
		t.setColumnFooter(NAME, "Total semua");
		t.setColumnFooter(PRICE, "Rp. "+Util.formatLocalAmount(total + trx.getTransportPrice()));

		return t;
	}

	private HorizontalLayout createHeaderBox() {
		HorizontalLayout main = new HorizontalLayout();
		main.setWidth(100, Unit.PERCENTAGE);

		VerticalLayout addressBox = new VerticalLayout();

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

		currentStatus = new ComboBox("Status transaksi");
		currentStatus.addItem(new Short("1").shortValue());
		currentStatus.addItem(new Short("2").shortValue());
		currentStatus.addItem(new Short("3").shortValue());
		currentStatus.addItem(new Short("4").shortValue());
		currentStatus.addItem(new Short("5").shortValue());
		currentStatus.addItem(new Short("6").shortValue());

		currentStatus.setItemCaption(new Short("1").shortValue(), "SENT" );
		currentStatus.setItemCaption(new Short("2").shortValue(), "PAID");
		currentStatus.setItemCaption(new Short("3").shortValue(), "PROCESSING");
		currentStatus.setItemCaption(new Short("4").shortValue(), "DELIVERING");
		currentStatus.setItemCaption(new Short("5").shortValue(), "RECEIVED");
		currentStatus.setItemCaption(new Short("6").shortValue(), "CANCELED");
		currentStatus.setNullSelectionAllowed(false);
		
		currentStatus.select(status);

		main.addComponent(addressBox);
		main.addComponent(currentStatus);

		main.setExpandRatio(addressBox, 1.0f);
		main.setExpandRatio(currentStatus, 0.0f);

		main.setComponentAlignment(addressBox, Alignment.MIDDLE_LEFT);
		main.setComponentAlignment(currentStatus, Alignment.MIDDLE_RIGHT);
		return main;
	}
}
