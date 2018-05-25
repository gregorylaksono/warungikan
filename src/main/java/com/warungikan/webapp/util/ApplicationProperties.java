package com.warungikan.webapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

	private static InputStream input;
	private static Properties prop;
	private static boolean isLoaded;

	public static String getWsURL(){
		init();
		return prop.getProperty("ws.url");
	}
	public static String getAdminEmail(){
		init();
		return prop.getProperty("admin.email");
	}
	
	private static void init(){
		if(prop == null){
			prop = new Properties();
		}
		try {
			if(input == null){
				input = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties");
			}
			if(!isLoaded){
				prop.load(input);
				isLoaded = true;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
