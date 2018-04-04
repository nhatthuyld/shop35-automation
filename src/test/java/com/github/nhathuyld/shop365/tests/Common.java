package com.github.nhathuyld.shop365.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Common {
	
	WebDriver driver;
	public Common(WebDriver driver){
		this.driver = driver;
	}

	//static ;
	public Boolean clickButtonCss(String idButton) {
		try {
			
			WebElement itemElement = this.driver.findElement(By.cssSelector((idButton)));
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
