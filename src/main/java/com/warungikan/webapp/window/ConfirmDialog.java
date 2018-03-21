package com.warungikan.webapp.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class ConfirmDialog extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2242740676719729978L;

	public ConfirmDialog(Component content) {
		setContent(content);
		setModal(true);
		setResizable(false);
		setDraggable(false);
		setClosable(true);
		
	}
	
	public void show() {
		UI.getCurrent().addWindow(this);
	}
	
	public void close() {
		UI.getCurrent().removeWindow(this);
	}
}
