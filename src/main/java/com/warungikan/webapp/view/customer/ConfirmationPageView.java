package com.warungikan.webapp.view.customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.dialog.ConfirmPayment;
import com.warungikan.webapp.manager.ServiceInitator;
import com.warungikan.webapp.model.AgentProduct;
import com.warungikan.webapp.model.ShopItemCart;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.util.Util;
import com.warungikan.webapp.window.ConfirmDialog;

public class ConfirmationPageView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7025130788406305781L;
	private AgentProduct agent;
	private String jwt;
	private User customer;
	private Long transportPrice;

	public ConfirmationPageView() {
		this.jwt = ((MyUI)UI.getCurrent()).getJwt();
		this.customer = ServiceInitator.getUserService().getUser(jwt);
		this.agent = ((MyUI)UI.getCurrent()).getAgentProduct();
		transportPrice = ServiceInitator.getTransactionService().calculateTransportPrice(jwt, agent.getUser().getEmail(), agent.getDistance());
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);

		VerticalLayout parent = new VerticalLayout();
		parent.setWidth(60, Unit.PERCENTAGE);
		parent.addStyleName("product-container");
		parent.setMargin(true);
		parent.setSpacing(true);

		setMargin(true);
		setSpacing(true);
		//		setWidth(80, Unit.PERCENTAGE);
		GridLayout items = createItemsGrid();
		HorizontalLayout header = createHeaderLayout();
		HorizontalLayout buttonLayout = createButtonLayout();

		parent.addComponent(header);
		parent.addComponent(items);
		parent.addComponent(buttonLayout);

		parent.setComponentAlignment(header, Alignment.TOP_CENTER);
		parent.setComponentAlignment(items, Alignment.MIDDLE_CENTER);
		parent.setComponentAlignment(buttonLayout, Alignment.BOTTOM_CENTER);
		addComponent(parent);
		setComponentAlignment(parent, Alignment.MIDDLE_CENTER);
	}

	private HorizontalLayout createButtonLayout() {
		HorizontalLayout l = new HorizontalLayout();
		l.setWidth(100, Unit.PERCENTAGE);
		l.setSpacing(true);
		Button cont = Factory.createButtonOk("Lanjutkan");
		Button cancel = Factory.createButtonDanger("Batalkan");

		ClickListener c = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				AgentProduct p = ((MyUI)UI.getCurrent()).getAgentProduct();
				Long transportPrice = p.getDistance() * Long.parseLong(p.getPricePerKM());
				List<ShopItemCart> carts = ((MyUI)UI.getCurrent()).getItemsCart();
				Set<TransactionDetail> details = convertObject(carts);
				Transaction result = ServiceInitator.getTransactionService().addTransaction(jwt, p.getUser().getEmail(), transportPrice,p.getDistance(), details);
				if(result != null){
					UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_MY_TRANSACTION);
					Notification.show("Transaksi berhasil", Type.TRAY_NOTIFICATION);
				}else{
					UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_SHOP);
					Notification.show("Terjadi kesalahan saat melakukan transaksi", Type.ERROR_MESSAGE);
				}
				((MyUI)UI.getCurrent()).closeWindow();
			}
		};


		cont.addClickListener(e ->{			
			ConfirmPayment t = new ConfirmPayment(c, null);
			ConfirmDialog d = new ConfirmDialog(t);
			d.show();
		});


		l.addComponent(cancel);
		l.addComponent(cont);

		l.setExpandRatio(cancel, 1.0f);
		l.setExpandRatio(cont, 0.0f);

		l.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		l.setComponentAlignment(cont, Alignment.BOTTOM_RIGHT);
		return l;
	}

	protected Set<TransactionDetail> convertObject(List<ShopItemCart> carts) {
		Set<TransactionDetail> details = new HashSet<>();
		for(ShopItemCart c: carts){
			TransactionDetail d = new TransactionDetail();
			d.setAmount(c.getCount());
			d.setItem(c.getFish());
			details.add(d);
		}
		return details;
	}

	private HorizontalLayout createHeaderLayout() {
		HorizontalLayout l = new HorizontalLayout();
		l.setWidth(100, Unit.PERCENTAGE);

		VerticalLayout left = new VerticalLayout();
		
		left.addComponent(Factory.createLabelHeader("Pembeli"));
		left.addComponent(Factory.createLabel(customer.getName()));
		left.addComponent(Factory.createLabel(customer.getAddress()));
		left.addComponent(Factory.createLabel(customer.getTelpNo()));

		VerticalLayout right = new VerticalLayout();
		//		right.setSpacing(true);
		Label l1 = Factory.createLabelHeader("Penjual (Agen)");
		Label l2 = Factory.createLabel(agent.getUser().getName());
		Label l3 = Factory.createLabel(agent.getUser().getAddress());
		Label l4 = Factory.createLabel(agent.getUser().getTelpNo());
		l1.setWidth(100, Unit.PERCENTAGE);
		l1.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		l2.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		l3.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		l4.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);

		right.addComponent(l1);
		right.addComponent(l2);
		right.addComponent(l3);
		right.addComponent(l4);

		l.addComponent(left);
		l.addComponent(right);
		l.setComponentAlignment(right, Alignment.TOP_RIGHT);
		return l;
	}

	private GridLayout createItemsGrid() {
		List<ShopItemCart> items = ((MyUI)UI.getCurrent()).getItemsCart();
		GridLayout l = new GridLayout(3, items.size()+2);
		l.setWidth(500, Unit.PIXELS);
		Long totalAll = new Long(0);
		Label hItem = Factory.createLabelHeader("Jenis ikan");
		Label hAmount = Factory.createLabelHeader("Jumlah");
		Label hPrice = Factory.createLabelHeader("Harga");

		hItem.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		hAmount.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		hPrice.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);

		hItem.setWidth(100, Unit.PERCENTAGE);
		hAmount.setWidth(100, Unit.PERCENTAGE);
		hPrice.setWidth(100, Unit.PERCENTAGE);

		l.addComponent(hItem);
		l.addComponent(hAmount);
		l.addComponent(hPrice);

		for(ShopItemCart i : items) {
			int number = i.getCount();
			Long total = i.getCount() * i.getFish().getPrice();
			totalAll = totalAll + total;
			Label amount = Factory.createLabel(String.valueOf(number));
			Label fishName = Factory.createLabel(i.getFish().getName());
			Label totalPrice = Factory.createLabel("Rp. "+Util.formatLocalAmount(total));

			amount.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
			fishName.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
			totalPrice.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);

			l.addComponent(fishName);
			l.addComponent(amount);
			l.addComponent(totalPrice);
		}
		Double d = new Double(this.agent.getDistance()) / 1000;
		Label distance = Factory.createLabel(String.valueOf(Math.ceil(d))+" km");
		Label priceName = Factory.createLabel("Biaya transport");
		Label transportPrice = Factory.createLabel("Rp. "+Util.formatLocalAmount(this.transportPrice));
		
		distance.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		priceName.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		transportPrice.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		
		l.addComponent(priceName);
		l.addComponent(distance);
		l.addComponent(transportPrice);
		
		totalAll = totalAll + this.transportPrice;
		Label bottTotal = Factory.createLabelHeader("Rp. "+Util.formatLocalAmount(totalAll));
		bottTotal.setWidth(100, Unit.PERCENTAGE);
		l.addComponent(new Label());
		l.addComponent(new Label());
		l.addComponent(bottTotal);
		bottTotal.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		return l;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		String param = event.getParameters();
	}

}
