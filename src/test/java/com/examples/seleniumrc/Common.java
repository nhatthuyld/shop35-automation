package com.examples.seleniumrc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Common {
	//static ;
	public static Boolean clickButtonCss(WebDriver driver ,String idButton) {
		try {
			
			WebElement itemElement = driver.findElement(By.cssSelector((idButton)));
			itemElement.click();
			Thread.sleep(200);
			return true;
		} catch (Exception ex) {
			System.out.println("Not found for click button:" + idButton);
			//System.exit(0);
			return false;
		}
	}

}
