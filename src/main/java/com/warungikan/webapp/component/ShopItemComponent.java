package com.warungikan.webapp.component;


import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.model.FishShopItem;


public class ShopItemComponent extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -674091625958181008L;
	private FishShopItem fish;

	public ShopItemComponent(FishShopItem fish) {
		addStyleName("fitem-component");
		setHeight(300, Unit.PIXELS);
		setWidth(280, Unit.PIXELS);
		this.fish = fish;
		setMargin(false);
		setSpacing(false);
		
		
		Image img = new Image();
		img.setWidth(100,Unit.PERCENTAGE);
		img.setHeight(140,Unit.PIXELS);
		img.setSource(new ExternalResource(fish.getImgUrl()));
		
		addComponent(img);
		
		Label name = new Label(fish.getName());
		name.addStyleName(ValoTheme.LABEL_LARGE);
		name.addStyleName(ValoTheme.LABEL_BOLD);
		name.setWidth(null);
		addComponent(name);
		
		
		Label weight = new Label(fish.getWeight());
		weight.addStyleName(ValoTheme.LABEL_SMALL);
		weight.setWidth(null);
		addComponent(weight);
		
		
		Label price = new Label(fish.getPrice());
		price.addStyleName(ValoTheme.LABEL_SMALL);
		price.setWidth(null);
		addComponent(price);
		
		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setSpacing(true);
		bottomLayout.setWidth(100, Unit.PERCENTAGE);
		bottomLayout.setHeight(45,Unit.PIXELS);

		TextField numberTf = new TextField();
		numberTf.setWidth(60, Unit.PIXELS);
		numberTf.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		numberTf.setValue("0");
		
        Button addToCartButton = new Button("Add to cart");
//        addToCartButton.setIcon(VaadinIcons.CART);
        addToCartButton.addStyleName(ValoTheme.BUTTON_SMALL);
        addToCartButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
       
        
        bottomLayout.addComponent(numberTf);
        bottomLayout.addComponent(addToCartButton);
        bottomLayout.setComponentAlignment(numberTf, Alignment.TOP_LEFT);
        bottomLayout.setComponentAlignment(addToCartButton, Alignment.TOP_LEFT);
        bottomLayout.setExpandRatio(numberTf, 0.0f);
        bottomLayout.setExpandRatio(addToCartButton, 1.0f);
        
        addComponent(bottomLayout);
        setComponentAlignment(bottomLayout, Alignment.BOTTOM_CENTER);
        
        setComponentAlignment(name, Alignment.TOP_LEFT);
        setExpandRatio(img, 1.0f);
        setExpandRatio(name, 0.0f);
        setExpandRatio(weight, 0.0f);
        setExpandRatio(price, 0.0f);
        setExpandRatio(bottomLayout, 0.0f);
	}
}
