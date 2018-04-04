package com.examples.seleniumrc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.examples.seleniumrc.util.PropertyReader;

import org.openqa.selenium.JavascriptExecutor;

public class OrderingTest {

	WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		String driverUrl = PropertyReader.getValue("chromedriver");
		System.setProperty("webdriver.chrome.driver", driverUrl);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		

	}

	// @After
	// public void tearDown() throws Exception {
	// driver.quit();
	// }
	
	 ArrayList<Product> listProductSellerA = new ArrayList<Product>();

	@Test
	public void BookingCasual() throws InterruptedException {
		
		Login();
		
		driver.get("http://shop365.qrmartdemo.info/products/buoi-nam-roi-111111-2222.html");
		Thread.sleep(4000);
		Book();
//		driver.get("http://shop365.qrmartdemo.info/products/nhan-tim-352.html");
//		Thread.sleep(4000);
//		Book();
		driver.get("http://shop365.qrmartdemo.info/checkout-finish.html");
		
		checkOut();
		
		//PaybyPaypal();
		PaybyCreditcard();
		
//		
	}
	
	public void Login() throws InterruptedException {
		driver.get("http://shop365.qrmartdemo.info/login.html");
		findCss("#emmail_login").sendKeys("nhatthuyadvn@gmail.com");
		findCss("#password_login").sendKeys("123456");
		clickButtonCss(".fa.fa-lock");
	}
	
	public void Book() throws InterruptedException {
		String quantity="10";

		findCss("[type='number']").clear();
		findCss("[type='number']").sendKeys(quantity);
		clickButtonCss(".shipping_options div [type='radio']");
		//Thread.sleep(2000);
//		Select select = new Select(driver.findElement(By.cssSelector(".select2-results__options")));
//		select.selectByIndex(2);
		
		getInfoProduct();
		
		waitForElementAppear(".select2-selection__arrow");
		clickButtonCss(".select2-selection__arrow");
		clickButtonCss(".select2-results__options li:nth-child(2)");
		clickButtonCss("#add-to-cart");
	}
	public void checkOut() throws InterruptedException {
		clickButtonCss(".col-md-6.col-md-offset-3.update-address");
		findCss("#unitno").sendKeys("3434");
		findCss("#postal_code").sendKeys("11122333");
		clickButtonCss(".btn-style-2.pull-right");
		
	}
	
	public void getInfoProduct() throws InterruptedException {
		Product p = new Product();
		p.name = findCss(".page-title").getText();
		p.sku = findCss(".product-code").getText();
		p.price = Integer.parseInt(findCss(".price").getText());
		p.pointshop = Integer.parseInt(findCss(".label-point").getText());
		
	}
	
	public void PaybyPaypal() throws InterruptedException {
		
		clickButtonCss("#radio_button_7");
		clickButtonCss(".pull-right.btn-style-1");
		
		Alert alertDate = driver.switchTo().alert();
		alertDate.accept();
		
		findCss(".fieldWrapper #email").sendKeys("shop365@shop365.com");
		clickButtonCss(".actions #btnNext");
		//Thread.sleep(4000);
		waitForPageLoaded();
		findCss(".fieldWrapper #password").sendKeys("shop365@shop365.com");
		clickButtonCss(".actions #btnLogin");
		//Thread.sleep(4000);
		waitForPageLoaded();
		clickButtonCss("#confirmButtonTop");
	}
	
	public void PaybyCreditcard() throws InterruptedException {
		clickButtonCss("#radio_button_6");
		
		findCss("[name='__privateStripeFrame3']").sendKeys("424242424242424242424242424");
		Thread.sleep(2000);
		clickButtonCss(".pull-right.btn-style-1");
		
		Alert alertDate = driver.switchTo().alert();
		alertDate.accept();
		
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

	public Boolean clickButtonCss(String idButton) {
		try {
			WebElement itemElement = driver.findElement(By.cssSelector((idButton)));
			itemElement.click();
			Thread.sleep(200);
			return true;
		} catch (Exception ex) {
			System.out.println("Not found for click button:" + idButton);
			System.exit(0);
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
			System.exit(0);
			return null;
		}
	}
	
	public String getAttributeElement(String s, String attb) throws InterruptedException {
		WebElement e = driver.findElement(By.cssSelector(s));
		String result = e.getAttribute(attb);
		return result;
	}


}