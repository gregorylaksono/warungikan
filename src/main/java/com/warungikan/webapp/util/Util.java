package com.warungikan.webapp.util;

import java.text.DecimalFormat;

public class Util {

	public static String formatLocalAmount(Long number){
		DecimalFormat format = new DecimalFormat("#,###,###");
		return format.format(number);
	}
}
