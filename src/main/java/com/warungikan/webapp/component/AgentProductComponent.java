package com.warungikan.webapp.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.dialog.ConfirmPayment;
import com.warungikan.webapp.model.AgentProduct;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.util.Factory;
import com.warungikan.webapp.window.ConfirmDialog;

public class AgentProductComponent extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4166910060056375633L;
	private AgentProduct agentProduct;
	private ClickListener chooseListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			((MyUI)UI.getCurrent()).setAgentProduct(agentProduct);
			Window w = createDialogConfirmation();
			UI.getCurrent().addWindow(w);
		}

		
	};

	public AgentProductComponent(AgentProduct agentProduct) {
		this.agentProduct = agentProduct;
		addStyleName("fitem-component");
		setWidth(189, Unit.PIXELS);
		setSpacing(true);
		Double distance = new Double(agentProduct.getDistance()) / 1000;
		Double distanceAbs = Math.ceil(distance);
		
		Label nameL = Factory.createLabel("Nama: "+agentProduct.getUser().getName());
		Label addressL = Factory.createLabel("Alamat: "+agentProduct.getUser().getAddress());
		Label distanceL = Factory.createLabel("Jarak "+distanceAbs+" km");;
		Button choose = Factory.createButtonOk("Pilih");
		choose.addClickListener(e->{
			((MyUI)UI.getCurrent()).setAgentProduct(agentProduct);
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_CONFIRM_PAGE);
		});
		
		
		Label telpNo = Factory.createLabel("Telp: "+agentProduct.getUser().getTelpNo());
		addComponent(nameL);
		addComponent(addressL);
		addComponent(distanceL);
		addComponent(telpNo);
		addComponent(choose);
		setComponentAlignment(choose, Alignment.BOTTOM_RIGHT);
//		choose.addClickListener(chooseListener );
	}

	private Window createDialogConfirmation() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setMargin(true);
		HorizontalLayout h = new HorizontalLayout();
		h.setWidth(100, Unit.PERCENTAGE);
		h.setSpacing(true);
		Label caption = Factory.createLabel("Agent berhasil dipilih. ");
		Button continueShop = Factory.createButtonOk("Lanjut belanja");
		Button payNow = Factory.createButtonNormal("Ke pembayaran");
		h.addComponent(continueShop);
		h.addComponent(payNow);
		h.setComponentAlignment(continueShop, Alignment.BOTTOM_CENTER);
		h.setComponentAlignment(payNow, Alignment.BOTTOM_CENTER);
		
		continueShop.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_SHOP_ITEM);
				((MyUI)UI.getCurrent()).closeWindow();
			}
		});
		
		payNow.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_CONFIRM_PAGE);
				((MyUI)UI.getCurrent()).closeWindow();
			}
		});
		
		l.addComponent(caption);
		l.addComponent(h);
		
		Window w = new Window();
		w.setModal(true);
		w.setResizable(false);
		w.setDraggable(false);
		w.setContent(l);
		return w;
	}
}
