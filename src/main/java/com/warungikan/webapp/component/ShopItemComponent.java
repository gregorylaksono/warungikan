package com.warungikan.webapp.component;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.warungikan.db.model.ShopItem;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.model.FishShopItem;
import com.warungikan.webapp.model.ShopItemCart;
import com.warungikan.webapp.util.Util;


public class ShopItemComponent extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -674091625958181008L;
	private ShopItem fish;
	private ClickListener addToCartListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			Window w = new Window();
			w.setContent(new CartAmountComponent(fish));
			w.setModal(true);
			w.setClosable(true);
			w.setDraggable(false);
			w.setResizable(false);
			UI.getCurrent().addWindow(w);
		}
		
	};

	private TextField numberTf;

	public ShopItemComponent(ShopItem fish) {
//		addStyleName("fitem-component");
		setHeight(345, Unit.PIXELS);
		setWidth(250, Unit.PIXELS);
		this.fish = fish;
		setMargin(new MarginInfo(false, false, true, false));
		setSpacing(true);
		
		Image img = new Image();
		img.setWidth(100,Unit.PERCENTAGE);
		img.setSource(new ExternalResource(fish.getUrl()));
		
		Label name = new Label(fish.getName());
		name.addStyleName(ValoTheme.LABEL_LARGE);
		name.addStyleName(ValoTheme.LABEL_BOLD);
		name.setWidth(null);
		
		Label weight = new Label(String.valueOf(fish.getWeight()));
		weight.addStyleName(ValoTheme.LABEL_SMALL);
		weight.setWidth(null);
		
		Label price = new Label("<b>Rp. "+String.valueOf(Util.formatLocalAmount(fish.getPrice()))+"</b>");
		price.setContentMode(ContentMode.HTML);
		price.addStyleName(ValoTheme.LABEL_SMALL);
		price.setWidth(null);
		
		VerticalLayout bottomLayout = new VerticalLayout();
		bottomLayout.setSpacing(true);
		bottomLayout.setWidth(100, Unit.PERCENTAGE);
		bottomLayout.setHeight(40,Unit.PIXELS);
		
        Button addToCartButton = new Button("ADD TO CART");
        addToCartButton.setPrimaryStyleName("cart-button");
        addToCartButton.setWidth(100, Unit.PERCENTAGE);
        addToCartButton.addClickListener(addToCartListener );
       
        bottomLayout.addComponent(weight);
        bottomLayout.addComponent(price);
        bottomLayout.setComponentAlignment(weight, Alignment.BOTTOM_CENTER);
        bottomLayout.setComponentAlignment(price, Alignment.BOTTOM_CENTER);
        
        addComponent(name);
		addComponent(img);
        addComponent(bottomLayout);
        addComponent(addToCartButton);   
        
        setComponentAlignment(name, Alignment.TOP_CENTER);
        setExpandRatio(img, 1.0f);
        setExpandRatio(bottomLayout, 0.0f);
        setExpandRatio(name, 0.0f);
        setExpandRatio(addToCartButton, 0.0f);
        setComponentAlignment(addToCartButton, Alignment.BOTTOM_CENTER);
	}
}
