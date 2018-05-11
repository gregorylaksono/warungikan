package com.warungikan.webapp.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
			((MyUI)UI.getCurrent()).setAgent(agentProduct.getUser());
		}
	};

	public AgentProductComponent(AgentProduct agentProduct) {
		this.agentProduct = agentProduct;
		addStyleName("fitem-component");
		setWidth(189, Unit.PIXELS);
		setSpacing(true);
		Label nameL = Factory.createLabel("Nama: "+agentProduct.getUser().getName());
		Label addressL = Factory.createLabel("Alamat: "+agentProduct.getUser().getAddress());
		Label distanceL = Factory.createLabel("Jarak "+agentProduct.getKm());;
		Button choose = Factory.createButtonOk("Pilih");
		choose.addClickListener(e->{
			UI.getCurrent().getNavigator().navigateTo(Constant.VIEW_CONFIRM_PAGE);
		});
		
		
		Label telpNo = Factory.createLabel("Telp: "+agentProduct.getUser().getTelpNo());
		addComponent(nameL);
		addComponent(addressL);
		addComponent(distanceL);
		addComponent(telpNo);
		addComponent(choose);
		setComponentAlignment(choose, Alignment.BOTTOM_RIGHT);
		choose.addClickListener(chooseListener );
	}

}
