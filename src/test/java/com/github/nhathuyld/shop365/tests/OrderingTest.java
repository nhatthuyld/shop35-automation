package com.github.nhathuyld.shop365.tests;

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

import com.github.nhathuyld.shop365.model.Product;
import com.github.nhatthuyld.shop365.utils.PropertyReader;

//import com.examples.seleniumrc.util.PropertyReader;

import org.openqa.selenium.JavascriptExecutor;

public class OrderingTest {

	private static final WebElement NULL = null;
	WebDriver driver;
	Common common;
	
	Double subTotal,shippingFee,total,pointShop;
	
	@Before
	public void setUp() throws Exception {
		String driverUrl = PropertyReader.getValue("chromedriver");
		System.setProperty("webdriver.chrome.driver", driverUrl);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		this.common = new Common(driver);
		
		

	}

	// @After
	// public void tearDown() throws Exception {
	// driver.quit();
	// }
	
	 ArrayList<Product> listProductSellerA = new ArrayList<Product>();

	@Test
	public void BookingCasual() throws InterruptedException {
		
		Login();
		
		addProduct();
		
		checkOut();
		
		//PaybyPaypal();
		PaybyCreditcard();
		
//		
	}
	
	public void Login() throws InterruptedException {
		
		driver.get("http://shop365.qrmartdemo.info/login.html");
		this.common.findCss("#emmail_login").sendKeys("nhatthuyadvn@gmail.com");
		this.common.findCss("#password_login").sendKeys("123456");
		this.common.clickButtonCss(".fa.fa-lock");
	}
	
	public void Book(String quantity) throws InterruptedException {

		this.common.findCss("[type='number']").clear();
		this.common.findCss("[type='number']").sendKeys(quantity);
		this.common.clickButtonCss(".shipping_options div [type='radio']");
		//Thread.sleep(2000);
//		Select select = new Select(driver.findElement(By.cssSelector(".select2-results__options")));
//		select.selectByIndex(2);
		
		
		
		this.common.waitForElementAppear(".select2-selection__arrow");
		this.common.clickButtonCss(".select2-selection__arrow");
		this.common.clickButtonCss(".select2-results__options li:nth-child(2)");
		getInfoProduct();
		this.common.clickButtonCss("#add-to-cart");
	}
	
	public void addProduct() throws InterruptedException {
		
		driver.get("http://shop365.qrmartdemo.info/products/buoi-nam-roi-111111-2222.html");
		Thread.sleep(4000);
		Book("10");
		driver.get("http://shop365.qrmartdemo.info/products/nhan-tim-352.html");
		Thread.sleep(4000);
		Book("5");
		driver.get("http://shop365.qrmartdemo.info/checkout-finish.html");
		
	}
	public void checkOut() throws InterruptedException {
		
		this.common.clickButtonCss(".col-md-6.col-md-offset-3.update-address");
		this.common.findCss("#unitno").sendKeys("3434");
		this.common.findCss("#postal_code").sendKeys("11122333");
		this.common.clickButtonCss(".btn-style-2.pull-right");
		
	}
	
	public void getInfoProduct() throws InterruptedException {
		
		Product p = new Product();
		p.name = this.common.findCss(".page-title").getText();
		p.sku = this.common.findCss(".product-code").getText();
		p.price = Double.parseDouble(this.common.findCss("[itemprop='price']").getText().replace("$",""));
		p.pointshop = findPointShop();
		p.shippingfee = findShippingFee();
		p.quantity = Double.parseDouble(this.common.getAttributeElement("#qty1","value"));
		
		listProductSellerA.add(p);
	}
	
	public Double  findPointShop() throws InterruptedException {
		
		Double point = (double) 0;
		if(this.common.findCss(".label-point") == null)
			return point;
			return Double.parseDouble(this.common.findCss(".label-point").getText().replace("x","").replace("pts", "").replace(" ",""));
	}
	
	public Double  findShippingFee() throws InterruptedException {
		Double fee = (double) 0;
		if(this.common.findCss(".show-shipping-fee") == null)
			return fee;
			return Double.parseDouble(this.common.findCss(".show-shipping-fee").getText().replace("$","").replace("+",""));
	}
	public void PaybyPaypal() throws InterruptedException {
		
		this.common.clickButtonCss("#radio_button_7");
		this.common.clickButtonCss(".pull-right.btn-style-1");
		
		Alert alertDate = driver.switchTo().alert();
		alertDate.accept();
		
		this.common.findCss(".fieldWrapper #email").sendKeys("shop365@shop365.com");
		this.common.clickButtonCss(".actions #btnNext");
		//Thread.sleep(4000);
		this.common.waitForPageLoaded();
		this.common.findCss(".fieldWrapper #password").sendKeys("shop365@shop365.com");
		this.common.clickButtonCss(".actions #btnLogin");
		//Thread.sleep(4000);
		this.common.waitForPageLoaded();
		this.common.clickButtonCss("#confirmButtonTop");
	}
	
	public void PaybyCreditcard() throws InterruptedException {
		
		this.common.clickButtonCss("#radio_button_6");
		
		this.common.findCss("[name='__privateStripeFrame3']").sendKeys("424242424242424242424242424");
		Thread.sleep(2000);
		this.common.clickButtonCss(".pull-right.btn-style-1");
		
		Alert alertDate = driver.switchTo().alert();
		alertDate.accept();
		
	}
	
	

	




}