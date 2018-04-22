package com.github.nhatthuyld.shop365.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static Properties prop;
    private PropertyReader() {
    }

	public static String getValue(String key) {
        String filePath = "src" + File.separator + "config.properties";
		InputStream input = null;
		try {
			if(PropertyReader.prop == null){
				System.out.println("Load properties again and again");
				PropertyReader.prop = new Properties();
				input = new FileInputStream(filePath);
				prop.load(input);
			}
			String value = PropertyReader.prop.getProperty(key);
			if(key == null || value == null) {
				System.err.println("Existing values are : " +  prop.values().toString());
			}
			return value;

		} catch (IOException ex) {
			System.out.println("Can not find :" + filePath + ".Please create the file from config.properties.example");
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
		return null;
	}
}
