package com.github.nhathuyld.shop365.tests;

import java.text.ParseException;
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
import org.openqa.selenium.internal.FindsByCssSelector;
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
	int pointInvoice;
	double subtotalInvoice = 0, TotalInvoice = 0, shippingFeeInvoice = 0, discountInvoice = 0;
	String idInvoice, transactionInvoice, paymentMethod;
	
	
	
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
	
	 ArrayList<Product> listProductSellerX = new ArrayList<Product>();
	 ArrayList<Seller> listSeller = new ArrayList<Seller>();

	@Test
	public void BookingCasual() throws InterruptedException, ParseException {
		//this.common.CreatCartCouponExceptSomeProduct("15","thuoc doc");
		//this.common.CreatCartCoupon("14");
		this.common.CreatProductCouponPercent("15","thuoc doc");
		
//		Login();
//		
//		addProduct();
//		getCoupon("#block-seller-95");
//		
//		checkOut();
		
//		checkProductSeller("#block-seller-83",0);
//		checkProductSeller("#block-seller-65",1);
//		checkTotalInvoice();
		
		//PaybyPaypal();
	//	PaybyCreditcard();
		
	//	getInfoInvoice();
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
		getInfoProduct(listProductSellerX);
		this.common.clickButtonCss("#add-to-cart");
	}
	
	public void addProduct() throws InterruptedException {
		
		driver.get("http://shop365.qrmartdemo.info/products/du-du-0001.html");
		Thread.sleep(4000);
		Book("10");
		driver.get("http://shop365.qrmartdemo.info/products/tao-xanh-0006.html");
		Thread.sleep(4000);
		Book("5");
		
		driver.get("http://shop365.qrmartdemo.info/products/tao-0005.html");
		Thread.sleep(4000);
		Book("3");
		
		driver.get("http://shop365.qrmartdemo.info/checkout-finish.html");
			
		
	}
	public void checkOut() throws InterruptedException {
		
		this.common.clickButtonCss(".col-md-6.col-md-offset-3.update-address");
		this.common.findCss("#unitno").sendKeys("3434");
		this.common.findCss("#postal_code").sendKeys("11122333");
		this.common.clickButtonCss(".btn-style-2.pull-right");
		
	}
	
	public void getInfoProduct(ArrayList<Product> listProductSellerX) throws InterruptedException {
		
		Product p = new Product();
		p.name = this.common.findCss(".page-title").getText();
		p.sku = this.common.findCss(".product-code").getText();
		p.price = Double.parseDouble(this.common.findCss("[itemprop='price']").getText().replace("$",""));
		p.xPointShop = this.common.findPointShop();
		p.shippingfee = this.common.findShippingFee();
		p.quantity = Double.parseDouble(this.common.getAttributeElement("#qty1","value"));
		
		listProductSellerX.add(p);
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
	public void getInfoOneProductSellerCheckOutCart(String idSeller,int i, int orderSeller) throws InterruptedException {
		
	}
	
	public void checkOneProductSellerCheckOutCart(String idSeller,int i, int orderSeller) throws InterruptedException {
		String order = Integer.toString(i+2);
		
		String name = this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(2) a").getText();
		
		String sku =  this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(2) small").getText();
		
		String shippingname = this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(2) .shipping-fee").getText();
		
		Double price = Double.parseDouble(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(3) span").getText().replace("$",""));
		
		 
		Double quantity = Double.parseDouble(this.common.getAttributeElement(idSeller+" tr:nth-child("+order+") td:nth-child(4) input", "value"));
		
		int pointProduct = this.common.getPointProduct(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(5)").getText());	
	
		Double xPoint =  Double.parseDouble(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(5) span").getText().replace("pts","").replace("x",""));
		
		Double totalProductPrice =  Double.parseDouble(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(6) span").getText().replace("$",""));
		
		this.common.equalString(name, listProductSellerX.get(i).name);	
		this.common.equalString(sku, listProductSellerX.get(i).sku);
		this.common.equalString(shippingname, listProductSellerX.get(i).shippingName);
		this.common.compareDouble(price, listProductSellerX.get(i).price);
		this.common.compareDouble(quantity, listProductSellerX.get(i).quantity);
		this.common.compareInt(pointProduct,(int)listProductSellerX.get(i).price);
		this.common.compareDouble(xPoint-1, listProductSellerX.get(i).xPointShop);
		this.common.compareDouble(price*quantity,totalProductPrice);
		
		listSeller.get(orderSeller).sellerName = this.common.findCss(idSeller+" span").getText();
		listSeller.get(orderSeller).subtotal += totalProductPrice;
		listSeller.get(orderSeller).shippingFee += listProductSellerX.get(i).shippingfee;
		listSeller.get(orderSeller).pointEarnSellerandShop += pointProduct * xPoint * quantity; 
		
	}
	
	public void checkProductSeller(String idSeller, int orderSeller) throws InterruptedException {
		Seller X = new Seller();
		X.subtotal = 0.0;
		X.shippingFee = 0.0;
		X.pointEarnSellerandShop = 0;
		X.discount = this.common.findDiscount(idSeller);
		
		listSeller.add(X);
		listSeller.get(orderSeller).numberproduct = this.common.getNumberProduct(idSeller);
		for(int i = 0; i <= listSeller.get(orderSeller).numberproduct-1; i++)
		{
			checkOneProductSellerCheckOutCart(idSeller, i, orderSeller);
		}
	
		Assert.assertEquals("Fail-check items of seller ", Boolean.TRUE,
				listSeller.get(orderSeller).numberproduct == this.common.findAndGetNumeric(idSeller+" .count-item-seller"));
		
		Assert.assertEquals("Fail-check point earn of seller ", Boolean.TRUE,
				listSeller.get(orderSeller).pointEarnSellerandShop == this.common.findAndGetNumeric(idSeller+"  .text-right"));
		//k co discount
	
		Assert.assertEquals("Fail-check subtotal of seller ", Boolean.TRUE,
				listSeller.get(orderSeller).subtotal.equals(this.common.findAndGetNumericDouble(idSeller+" tfoot tr td:nth-child(3)")));
		
		
		Assert.assertEquals("Fail-check shiping fee of seller ", Boolean.TRUE,
				listSeller.get(orderSeller).shippingFee.equals(this.common.findAndGetNumericDouble(idSeller+" tfoot tr:nth-child(2) td:nth-child(2)")));
		
		listSeller.get(orderSeller).total = listSeller.get(orderSeller).subtotal + listSeller.get(orderSeller).shippingFee - listSeller.get(orderSeller).discount;
		
		Assert.assertEquals("Fail-check total of seller ", Boolean.TRUE,
				listSeller.get(orderSeller).total.equals(this.common.findAndGetNumericDouble(idSeller+" tfoot tr:nth-child(3) td:nth-child(2)")));	
		
	}
	
	public void checkTotalInvoice() throws InterruptedException {
		for(int i=0; i<listSeller.size(); i++)
		{
			subtotalInvoice += listSeller.get(i).subtotal;
			TotalInvoice += listSeller.get(i).total;
			shippingFeeInvoice += listSeller.get(i).shippingFee;
			discountInvoice += listSeller.get(i).discount;
		}
		Assert.assertEquals("Fail-subtotal invoice ", Boolean.TRUE,this.common.findAndGetNumericDouble(".cart-subtotal.no-before-text.td-half").equals(subtotalInvoice) );
		Assert.assertEquals("Fail-shipping fee invoice ", Boolean.TRUE,this.common.findAndGetNumericDouble("tbody #mask-shipping-total").equals(shippingFeeInvoice));
		Assert.assertEquals("Fail-total invoice ", Boolean.TRUE,this.common.findAndGetNumericDouble("tbody .cart-total").equals(TotalInvoice));
		Assert.assertEquals("Fail-dicount invoice ", Boolean.TRUE,this.common.findAndGetNumericDouble("#total-discount").equals(discountInvoice));
				
	}
	
	public void getInfoInvoice() throws InterruptedException {
		idInvoice = this.common.findCss(".invoice-col strong").getText();
		transactionInvoice = this.common.findCss(".invoice-col div:nth-child(2) span").getText();
		paymentMethod = this.common.findCss(".invoice-col div:nth-child(3) span").getText();
		
		Assert.assertEquals("Fail-total invoice -thank you page ", Boolean.TRUE,this.common.findAndGetNumericDouble(".invoice-col div:nth-child(4) span").equals(TotalInvoice));
	}
	
	public void getCoupon(String idSeller) throws InterruptedException {
	
		this.common.clickButtonCss(idSeller+" .btn.btn-sm.open-modal-coupon");
		this.common.waitForElementAppear(".modal-body tbody tr:last-child input");
		this.common.clickButtonCss(".modal-body tbody tr:last-child input");
		this.common.clickButtonCss(".modal-footer button");
		Thread.sleep(1000);
	
	}
	
	
}