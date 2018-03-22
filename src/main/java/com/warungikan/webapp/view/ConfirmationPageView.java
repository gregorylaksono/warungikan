package com.warungikan.webapp.view;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.dialog.ConfirmPayment;
import com.warungikan.webapp.model.ShopItem;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.window.ConfirmDialog;

public class ConfirmationPageView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7025130788406305781L;

	public ConfirmationPageView() {
		addStyleName("grreg");
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
		
		cont.addClickListener(e ->{
			ConfirmPayment t = new ConfirmPayment(null, null);
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

	private HorizontalLayout createHeaderLayout() {
		HorizontalLayout l = new HorizontalLayout();
		l.setWidth(100, Unit.PERCENTAGE);
		
		VerticalLayout left = new VerticalLayout();
//		left.setSpacing(true);
		left.addComponent(Factory.createLabelHeader("Pembeli"));
		left.addComponent(Factory.createLabel("Gregory L"));
		left.addComponent(Factory.createLabel("Pesona Depok Blok AY No. 6"));
		left.addComponent(Factory.createLabel("081280142404"));
		
		VerticalLayout right = new VerticalLayout();
//		right.setSpacing(true);
		Label l1 = Factory.createLabelHeader("Penjual (Agen)");
		Label l2 = Factory.createLabel("Anto setyo");
		Label l3 = Factory.createLabel("Jl. Raya pamulang");
		Label l4 = Factory.createLabel("0832148392");
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
		List<ShopItem> items = ((MyUI)UI.getCurrent()).getItems();
		GridLayout l = new GridLayout(3, items.size()+1);
		l.setWidth(500, Unit.PIXELS);
		int totalAll = 0;
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
		
		for(ShopItem i : items) {
			int number = i.getCount();
			int total = i.getCount() * Integer.parseInt(i.getFish().getPrice().replace("Rp", "").replace(".", "").replace(" ", ""));
			totalAll = totalAll + total;
			Label amount = Factory.createLabel(String.valueOf(number));
			Label fishName = Factory.createLabel(i.getFish().getName());
			Label totalPrice = Factory.createLabel("Rp. "+String.valueOf(total));
			
			amount.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
			fishName.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
			totalPrice.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
			
			l.addComponent(fishName);
			l.addComponent(amount);
			l.addComponent(totalPrice);
		}
		
		Label bottTotal = Factory.createLabelHeader("Rp. "+String.valueOf(totalAll));
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
