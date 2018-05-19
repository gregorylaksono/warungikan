package com.warungikan.webapp.util;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class Factory {


	public static Label createLabelHeader(String caption) {
		Label label = new Label(caption);
		label.setWidth(130, Unit.PIXELS);
		label.addStyleName(ValoTheme.LABEL_BOLD);
		return label;
	}
	
	
	
	public static Label createLabelHeaderNormal(String caption) {
		Label label = new Label(caption);
		label.setWidth(100, Unit.PERCENTAGE);
		label.addStyleName(ValoTheme.LABEL_BOLD);
		return label;
	}
	
	public static Label createLabel(String caption) {
		Label label = new Label(caption);
		label.addStyleName(ValoTheme.LABEL_SMALL);
		return label;
	}

	public static Button createButtonOk(String string) {
		Button b = new Button(string);
		b.addStyleName(ValoTheme.BUTTON_TINY);
		b.addStyleName(ValoTheme.BUTTON_PRIMARY);
		return b;
	}
	
	public static Button createButtonDanger(String string) {
		Button b = new Button(string);
		b.addStyleName(ValoTheme.BUTTON_TINY);
		b.addStyleName(ValoTheme.BUTTON_DANGER);
		return b;
	}
	
	public static Button createButtonNormal(String string) {
		Button b = new Button(string);
		b.addStyleName(ValoTheme.BUTTON_TINY);
		b.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		return b;
	}
	
	public static TextField createStandardTextField(String caption){
		TextField d = new TextField(caption);
		d.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		return d;
	}
	
	public static TextField createFormatedTextField(String caption, int min, int max, String errorMessage){
		TextField d = new TextField(caption);
		d.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		d.setRequired(true);
		d.addValidator(new StringLengthValidator(errorMessage, min, max, false));
		return d;
	}
	
	public static PasswordField createPasswordField(String caption, int min, int max, String errorMessage){
		PasswordField d = new PasswordField(caption);
		d.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		d.setRequired(true);
		d.addValidator(new StringLengthValidator(errorMessage, min, max, false));
		return d;
	}



	public static Button createButtonBorderless(String name) {
		Button b = new Button(name);
		b.addStyleName(ValoTheme.BUTTON_TINY);
		b.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		return b;
	}
	
}
