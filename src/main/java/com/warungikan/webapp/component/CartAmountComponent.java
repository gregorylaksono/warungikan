package com.warungikan.webapp.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.warungikan.db.model.ShopItem;

import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.model.ShopItemCart;
import com.warungikan.webapp.util.Factory;

public class CartAmountComponent extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686081738398582908L;
	private ShopItem fish;

	private ClickListener updateCartListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			amount.validate();
			List<ShopItemCart> cart = ((MyUI)UI.getCurrent()).getItemsCart();
			if(cart == null) cart = new ArrayList();
			List<ShopItemCart> single = cart.stream().filter(f -> f.getFish().getName().equals(fish.getName())).collect(Collectors.toList());
			Integer num = Integer.parseInt(amount.getValue());
			if(single.size() == 1) {
				ShopItemCart item = single.get(0);
				item.setCount(item.getCount()+num);
				cart.remove(item);
				cart.add(item);
			}else{
				cart.add(new ShopItemCart(fish, num));
			}
			((MyUI)UI.getCurrent()).setItemsCart(cart);
			Notification.show("Berhasil dimasukan ke keranjang", Type.HUMANIZED_MESSAGE);
			((MyUI)UI.getCurrent()).updateNotifLabel();
			((MyUI)UI.getCurrent()).closeWindow();
		}
		
	};
	private TextField amount;
	public CartAmountComponent(ShopItem fish) {
		this.fish = fish;
		
		initComponent();
	}

	private void initComponent() {
		setSpacing(true);
		setMargin(true);
		
		Image i = new Image();
		i.setWidth(400, Unit.PIXELS);
		i.setSource(new ExternalResource(fish.getUrl()));
		
		HorizontalLayout l = new HorizontalLayout();
		l.setSpacing(true);
		
		amount = new TextField("Jumlah");
		amount.setWidth(80, Unit.PIXELS);
		amount.addValidator((new RegexpValidator("[-]?[0-9]*\\.?,?[0-9]+","Input tidak valid")));
		amount.setValidationVisible(true);
		amount.setRequired(true);
		
		Button submit = Factory.createButtonOk("Masukan");
		submit.addClickListener(updateCartListener);
		l.addComponent(amount);
		l.addComponent(submit);
		l.setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
		
		addComponent(i);
		addComponent(l);
	}
}
