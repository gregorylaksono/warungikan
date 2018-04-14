package com.warungikan.webapp.view.customer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.model.ShopItem;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;


public class ShoppingCartView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8894159587988538925L;
	private DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
	private List<ShopItem> items;
	public static DecimalFormat decimalFormat = new DecimalFormat("###,###");
	
	public ShoppingCartView() {
		setSpacing(true);
		setMargin(true);
		addStyleName("product-container");
		this.items = ((MyUI)UI.getCurrent()).getItems();
		decimalSymbols.setGroupingSeparator('.');
		decimalFormat.setDecimalFormatSymbols(decimalSymbols);
		GridLayout grid = new GridLayout(5, items.size()+1);
		grid.setSizeUndefined();
		grid.setSpacing(true);
		grid.setMargin(true);
		HorizontalLayout buttonLayout = createButtonLayout();
		
		Label product = Factory.createLabelHeader("Product");
		addComponentHeader(grid, product);
		
		
		Label amount = Factory.createLabelHeader("Jumlah");
		addComponentHeader(grid, amount);
		
		
		Label unitPrice = Factory.createLabelHeader("Harga satuan");
		addComponentHeader(grid, unitPrice);
		
		
		Label totalPrice = Factory.createLabelHeader("Total harga");
		addComponentHeader(grid, totalPrice);
		
		
		Label delete = Factory.createLabelHeader("Hapus");
		addComponentHeader(grid, delete);
				
		addShoppingItem( grid, items);
		
		addComponent(grid);
		addComponent(buttonLayout);
		
		setExpandRatio(grid, 1.0f);
		setExpandRatio(buttonLayout, 0.0f);
		
		setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
	}
	
	private HorizontalLayout createButtonLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setSpacing(true);
		layout.setMargin(new MarginInfo(false, true, false, false));
		
		Button next = new Button("Pilih Agen");
		next.addStyleName(ValoTheme.BUTTON_SMALL);
		next.addStyleName(ValoTheme.BUTTON_FRIENDLY);
//		next.setIcon(VaadinIcons.ANGLE_RIGHT);
		
		next.addClickListener( e ->{
			((MyUI)UI.getCurrent()).setItems(items);
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_AGENT_SHIPMENT);
		});
		
		Button close = new Button("Kembali belanja");
		close.addClickListener(e ->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_SHOP);
		});
		close.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		
		layout.addComponent(close);
		layout.addComponent(next);
		
		layout.setComponentAlignment(next, Alignment.BOTTOM_RIGHT);
		layout.setComponentAlignment(close, Alignment.BOTTOM_RIGHT);
		layout.setExpandRatio(next, 0.0f);
		layout.setExpandRatio(close,1.0f);
		return layout;
	}

	private void addShoppingItem(GridLayout grid, List<ShopItem> items) {
		
		for(int j=0; j<items.size();j++) {
			int index = j;
			ShopItem i = items.get(j);
			addProductItemToGrid(grid, i);
			addLabelItemToGrid(grid,  String.valueOf(i.getCount()));
			addLabelItemToGrid(grid, i.getFish().getPrice());
			String price = i.getFish().getPrice().replace("Rp", "").replace(".", "").replace(" ", "");
			
			addLabelItemToGrid(grid, "Rp. "+decimalFormat.format(Long.parseLong(price) * i.getCount()));
			
			Button removeButton = new Button();
			removeButton.addClickListener(e ->{
				grid.removeRow(index+1);
				items.remove(index);
			});
			removeButton.setIcon(FontAwesome.REMOVE);
			removeButton.addStyleName(ValoTheme.BUTTON_TINY);
			removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
			grid.addComponent(removeButton);
			grid.setComponentAlignment(removeButton, Alignment.MIDDLE_CENTER);
		}
		
	}

	

	private void addLabelItemToGrid(GridLayout grid,  String caption) {
		Label l = new Label(caption);
		l.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		grid.addComponent(l);
		grid.setComponentAlignment(l, Alignment.MIDDLE_CENTER);
		l.addStyleName(ValoTheme.LABEL_LIGHT);
		
	}

	private void addProductItemToGrid(GridLayout grid, ShopItem i) {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(false);
		l.setSpacing(true);
		
		Image img = new Image();
		img.setHeight(80, Unit.PIXELS);
		img.setWidth(100, Unit.PIXELS);
		img.setSource(new ExternalResource(i.getFish().getImgUrl()));
		
		l.addComponent(img);
		l.setComponentAlignment(img, Alignment.TOP_CENTER);
		
		Label name = new Label(i.getFish().getName());
		name.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		l.addComponent(name);
		l.setComponentAlignment(name, Alignment.BOTTOM_CENTER);
		
		grid.addComponent(l);
		
	}

	private void addComponentHeader(GridLayout grid, Label label) {
		grid.addComponent(label);
		label.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		grid.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}


//	public Window getWindow() {
//		w = new Window();
//		w.setWidth(830, Unit.PIXELS);
//		w.setHeight(600, Unit.PIXELS);
//		w.setContent(this);
//		w.setClosable(true);
//		w.setModal(true);
//		w.setResizable(false);
//		return w;
//	}
	


}
