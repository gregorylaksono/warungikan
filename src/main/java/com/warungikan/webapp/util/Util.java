package com.warungikan.webapp.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String formatLocalAmount(Long number){
		DecimalFormat format = new DecimalFormat("#,###,###");
		return format.format(number);
	}
	
	public static String parseDate(Date d){
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return format.format(d);
	}
	
	public static Double calculateDistance(Long distance){
		return Math.ceil(distance / 1000);
	}
}
