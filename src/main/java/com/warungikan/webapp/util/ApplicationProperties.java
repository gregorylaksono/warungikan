package com.warungikan.webapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

	private static InputStream input;
	private static Properties prop;

	public static String getWsURL(){
		init();
		return prop.getProperty("ws.url");
	}
	
	private static void init(){
		if(prop == null){
			prop = new Properties();
		}
		try {
			if(input == null){
				input = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties");
			}
			
			prop.load(input);
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
