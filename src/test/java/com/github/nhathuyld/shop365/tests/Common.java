package com.github.nhathuyld.shop365.tests;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;


public class Common {
	
	private static final String JavascriptExecutor = null;
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
	
	public WebElement findXpath(String idButton) throws InterruptedException {
		try {
			WebElement itemElement = driver.findElement(By.xpath(idButton));
			return itemElement;
		} catch (Exception ex) {
			System.out.println("Not found for find button:" + idButton);
			System.exit(0);
			return null;
		}
	}
	

	public WebElement findCss(String idButton) throws InterruptedException {
		try {
			WebElement itemElement = driver.findElement(By.cssSelector(idButton));
			return itemElement;
		} catch (Exception ex) {
			System.out.println("Not found for find button:" + idButton);
			//System.exit(0);
			return null;
		}
	}
	
	public String getAttributeElement(String s, String attb) throws InterruptedException {
		WebElement e = driver.findElement(By.cssSelector(s));
		String result = e.getAttribute(attb);
		return result;
	}
	
	public Boolean clickButtonXpath(String idButton) {
		try {
			WebElement itemElement = driver.findElement(By.xpath((idButton)));
			itemElement.click();
			Thread.sleep(200);
			return true;
		} catch (Exception ex) {
			System.out.println("Not found for click button:" + idButton);
			System.exit(0);
			return false;
		}
	}
	
	public void waitForElementAppear(String s) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(s)));
	}

	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}

	}

	public boolean assertDate() throws InterruptedException {

		try {
			Alert alertDate = driver.switchTo().alert();
			alertDate.accept();
			Thread.sleep(400);
			return true;
		} catch (NoAlertPresentException Ex) {
			Thread.sleep(400);
			return false;
		}

	}
	
	public boolean equalString(String s1, String s2) throws InterruptedException {
	
		if(s1.equals(s2)) return true;
		return false;
	}
	
	public boolean compareDouble(Double d1,Double d2) throws InterruptedException {
		
		if(d1 == d2 ) return true;
		return false;
	}
	
	public boolean compareInt(int i1,int i2) throws InterruptedException {
		
		if(i1 == i2) return true;
		return false;
	}
	
	public int getPointProduct(String s) throws InterruptedException {
		
		int i = s.indexOf("pts");
		String c = s.substring(0,i);
		return Integer.parseInt(c.replace(" ", ""));
		
	}
	
	public int getNumeric(String s) throws InterruptedException {
		
	
		  s = s.replaceAll("[^0-9]", "");
          int a = Integer.parseInt(s);
          return a;
         
	}
	public Double getNumericDouble(String s) throws InterruptedException {
		
		
		  s = s.replaceAll("[^0-9.]", "");
        Double a = Double.parseDouble(s);
        return a;
       
	}
	
	public int findAndGetNumeric(String css) throws InterruptedException{
		String s = findCss(css).getText();
		int a = getNumeric(s);
		return a;
	}
   
	public Double findAndGetNumericDouble(String css) throws InterruptedException{
		String s = findCss(css).getText();
		Double a = getNumericDouble(s);
		return a;
	}
	public Double  findDiscount(String idSeller) throws InterruptedException {
		Double fee = (double) 0;
		if(findCss(idSeller+" .discount_value.summary-discount") == null)
			return fee;
			return findAndGetNumericDouble(".show-shipping-fee");
	}
	public Double  findShippingFee() throws InterruptedException {
		Double fee = (double) 0;
		if(findCss(".show-shipping-fee") == null)
			return fee;
			return findAndGetNumericDouble(".show-shipping-fee");
	}
	
	public Double  findPointShop() throws InterruptedException {
		
		Double point = (double) 0;
		if(findCss(".label-point") == null)
			return point;
			return findAndGetNumericDouble(".label-point");
	}

	public int getRowTable(String s) throws InterruptedException {
		List<WebElement> rows = driver.findElements(By.cssSelector(s));
		int r = rows.size();
		return r;
	}
	
	public int getNumberProduct(String s) throws InterruptedException {
		return getRowTable(s+" tbody tr")-1;
	}
	
	public void logInSellerPage(String name,String pass) throws InterruptedException {
		driver.get("http://shop365.qrmartdemo.info/login/seller");
		waitForPageLoaded();
		findCss("#email").sendKeys(name);
		findCss("#password").sendKeys(pass);
		clickButtonCss(".actions input");
	}
	
	public String getCurrentDate() throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return (dateFormat.format(cal.getTime()));
	}
	
	public String getEndDate() throws InterruptedException, ParseException {
		String dt = getCurrentDate(); 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE,10); 
		dt = sdf.format(c.getTime()); 
		return dt;
	}
	
	public void CreatCartCoupon(String value) throws InterruptedException, ParseException {
		logInSellerPage("rosa.linky@gmail.com","123456");
		waitForPageLoaded();
		driver.get("http://shop365.qrmartdemo.info/seller/coupon_sellers/new");
		waitForPageLoaded();
		findCss("#seller_coupon_seller_coupon_name").sendKeys("KM Seller Thuy");
		Select select = new Select(driver.findElement(By.cssSelector("#seller_coupon_seller_coupon_type")));
		select.selectByIndex(3);
		findCss("#seller_coupon_seller_coupon_value").sendKeys(value);
		findCss(".input-group.half #datepicker-start-coupon").sendKeys(getCurrentDate());
		findCss(".input-group.half #datepicker-end-coupon").sendKeys(getEndDate());
		findCss("#seller_coupon_seller_number_apply").sendKeys("10");
		findCss("#seller_coupon_seller_price").sendKeys("100");
		//clickButtonCss(".cke_wysiwyg_frame.cke_reset");
		
		 ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"
	                + driver.findElement(
	                        By.cssSelector(".cke_wysiwyg_frame.cke_reset")).getLocation().y + ")");
	    WebElement descriptionElement = findCss((".cke_wysiwyg_frame.cke_reset"));
	    driver.switchTo().frame(descriptionElement);
	    WebElement editable = driver.switchTo().activeElement();
	    editable.sendKeys("Discount cart");
	    Thread.sleep(3000);    
//	    clickButtonCss("[type='submit']");
//	    clickButtonCss("[type='submit']");
	    
	    findCss("[type='submit']").submit();
	
	   

	
	}
	


}
