package com.warungikan.webapp.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.warungikan.webapp.model.AgentProduct;
import com.warungikan.webapp.util.Factory;

public class AgentProductComponent extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4166910060056375633L;
	private AgentProduct agetProduct;

	public AgentProductComponent(AgentProduct agentProduct) {
		this.agetProduct = agentProduct;
		addStyleName("fitem-component");
		setWidth(189, Unit.PIXELS);
		setSpacing(true);
		Label nameL = Factory.createLabel("Nama: "+agentProduct.getName());
		Label addressL = Factory.createLabel("Alamat: "+agentProduct.getAddress());
		Label availabilityL = null;
		Button choose = Factory.createButtonOk("Pilih");
		if(agentProduct.getAvailability().equals(AgentProduct.AVAILABILITY.EMPTY)) {
			availabilityL = Factory.createLabel("Stok: Kosong");
			availabilityL.addStyleName("color-red");
			choose.setEnabled(false);
		}
		else if(agentProduct.getAvailability().equals(AgentProduct.AVAILABILITY.FULL)) {
			availabilityL = Factory.createLabel("Stok: Tersedia");
			availabilityL.addStyleName("color-green");
		}else {
			availabilityL = Factory.createLabel("Stok: Sebagian");
			availabilityL.addStyleName("color-orange");
		}
		
		Label telpNo = Factory.createLabel("Telp: "+agentProduct.getTelpNo());
		addComponent(nameL);
		addComponent(addressL);
		addComponent(availabilityL);
		addComponent(telpNo);
		addComponent(choose);
		setComponentAlignment(choose, Alignment.BOTTOM_RIGHT);
	}

}
