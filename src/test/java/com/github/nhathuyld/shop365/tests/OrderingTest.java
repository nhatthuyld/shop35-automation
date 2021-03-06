package com.github.nhathuyld.shop365.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.nhathuyld.shop365.model.Product;
import com.github.nhathuyld.shop365.model.Seller;
import com.github.nhatthuyld.shop365.utils.PropertyReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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

	 @After
	 public void tearDown() throws Exception {
	 	driver.quit();
	 }
	
	 
	 ArrayList<Seller> listSeller = new ArrayList<Seller>();
	 
	 

	@Test
	public void BookingCasual() throws InterruptedException, ParseException {
		//this.common.CreatCartCouponExceptSomeProduct("15","thuoc doc");
		//this.common.CreatCartCoupon("14");
		//this.common.CreatProductCouponPercent("15","thuoc doc");
		
		Login();
		Seller SellerA = new Seller();
		SellerA.idSeller = "#block-seller-95";
		addProductOneSeller(SellerA,"2","products/du-du-0001.html");
		
		
		listSeller.add(SellerA);		
		driver.get(PropertyReader.getValue("webUrl").concat("checkout-finish.html"));
		
//		getCoupon("#block-seller-95");
//		
		checkOut();
		
		
		checkCheckOutCart();
		
		//PaybyPaypal();
		PaybyCreditcard();
		
	//	getInfoInvoice();
		System.out.println("done");
		
//		
	}
	public void Login() throws InterruptedException {
		
		driver.get(PropertyReader.getValue("webUrl").concat("login.html"));
		this.common.findCss("#emmail_login").sendKeys(PropertyReader.getValue("userEmail"));
		this.common.findCss("#password_login").sendKeys(PropertyReader.getValue("userPassword"));
		this.common.clickButtonCss(".fa.fa-lock");
	}
	
	public void Book(String quantity,Seller SellerX) throws InterruptedException {

		this.common.findCss("[type='number']").clear();
		this.common.findCss("[type='number']").sendKeys(quantity);
		this.common.clickButtonCss(".shipping_options div [type='radio']");	
		this.common.waitForElementAppear(".select2-selection__arrow");
		this.common.clickButtonCss(".select2-selection__arrow");
		this.common.clickButtonCss(".select2-results__options li:nth-child(2)");
		getInfoProduct(SellerX);
		this.common.clickButtonCss("#add-to-cart");
	}
	
	
	public void addProductOneSeller(Seller SellerX,String quantity,String linkProduct) throws InterruptedException {
		driver.get(PropertyReader.getValue("webUrl").concat(linkProduct));
		this.common.waitForPageLoaded();
		Book(quantity,SellerX);	
		SellerX.sellerName = this.common.findCss(".seller-content div h2").getText();
	}
	public void checkOut() throws InterruptedException {
		
		this.common.clickButtonCss(".col-md-6.col-md-offset-3.update-address");
		this.common.findCss("#unitno").sendKeys("3434");
		this.common.findCss("#postal_code").sendKeys("11122333");
		this.common.clickButtonCss(".btn-style-2.pull-right");
		
	}
	
	public void getInfoProduct(Seller SellerX) throws InterruptedException {
		
		Product p = new Product();
		p.name = this.common.findCss(".page-title").getText();
		p.sku = this.common.findCss(".product-code").getText();
		p.price = Double.parseDouble(this.common.findCss("[itemprop='price']").getText().replace("$",""));
		p.xPointShop = this.common.findPointShop();
		p.shippingfee = this.common.findShippingFee();
		p.quantity = Double.parseDouble(this.common.getAttributeElement("#qty1","value"));
		p.shippingName = this.common.findShippingMethod();
		SellerX.products.add(p);
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
	
	public void checkOneProductSellerCheckOutCart(int i, int orderSeller) throws InterruptedException {
		String idSeller = listSeller.get(orderSeller).idSeller;
		String order = Integer.toString(i+2);
		
		String name = this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(2) a").getText();
		
		String sku =  this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(2) small").getText();
		
		String shippingname = this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(2) .shipping-fee").getText();
		
		Double price = Double.parseDouble(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(3) span").getText().replace("$",""));
		
		 
		Double quantity = Double.parseDouble(this.common.getAttributeElement(idSeller+" tr:nth-child("+order+") td:nth-child(4) input", "value"));
		
		int pointProduct = this.common.getPointProduct(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(5)").getText());	
	
		Double xPoint =  Double.parseDouble(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(5) span").getText().replace("pts","").replace("x",""));
		
		Double totalProductPrice =  Double.parseDouble(this.common.findCss(idSeller+" tr:nth-child("+order+") td:nth-child(6) span").getText().replace("$",""));
		
		this.common.equalString(name, listSeller.get(orderSeller).products.get(i).name);	
		this.common.equalString(sku, listSeller.get(orderSeller).products.get(i).sku);
		this.common.equalString(shippingname, listSeller.get(orderSeller).products.get(i).shippingName);
		this.common.compareDouble(price, listSeller.get(orderSeller).products.get(i).price);
		this.common.compareDouble(quantity, listSeller.get(orderSeller).products.get(i).quantity);
		this.common.compareInt(pointProduct,(int)listSeller.get(orderSeller).products.get(i).price);
		this.common.compareDouble(xPoint-1, listSeller.get(orderSeller).products.get(i).xPointShop);
		this.common.compareDouble(price*quantity,totalProductPrice);
		
		listSeller.get(orderSeller).sellerName = this.common.findCss(idSeller+" span").getText();
		listSeller.get(orderSeller).subtotal += totalProductPrice;
		listSeller.get(orderSeller).shippingFee += listSeller.get(orderSeller).products.get(i).shippingfee;
		listSeller.get(orderSeller).pointEarnSellerandShop += pointProduct * xPoint * quantity; 
		
	}
	
	public void checkProductSeller( int orderSeller) throws InterruptedException {
		String idSeller = listSeller.get(orderSeller).idSeller;
		for(int i = 0; i < listSeller.get(orderSeller).products.size(); i++)
		{
			checkOneProductSellerCheckOutCart(i,orderSeller);
		}
	
		Assert.assertEquals("Fail-check items of seller ", Boolean.TRUE,
				listSeller.get(orderSeller).products.size() == this.common.findAndGetNumeric(idSeller+" .count-item-seller"));
		
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
	
	public void checkCheckOutCart() throws InterruptedException {
		for(int i=0; i<listSeller.size(); i++)
		{
			listSeller.get(i).subtotal = 0.0;
			listSeller.get(i).shippingFee = 0.0;
			listSeller.get(i).pointEarnSellerandShop = 0;
			listSeller.get(i).discount = this.common.findDiscount(listSeller.get(i).idSeller);
			checkProductSeller(i);
		}
		checkTotalInvoice();
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
//	public void checkDetailpage() throws InterruptedException {
//		this.common.clickButtonCss(".btn.btn-style-3");
//		this.common.waitForPageLoaded();
//		Assert.assertEquals("Fail-id invoice -detail page ",
//				Boolean.TRUE,this.common.findCss(".invoice-col div:nth-child(1) span").getText().replace("#", "").equals(idInvoice));
//		Assert.assertEquals("Fail-date -detail page ",
//				Boolean.TRUE,this.common.findCss(".invoice-col div:nth-child(2) span").getText().equals(this.common.getCurrentDate()));
//		Assert.assertEquals("Fail-payment -detail page ",
//				Boolean.TRUE,this.common.findCss(".invoice-col div:nth-child(3) span").getText().equals(paymentMethod));
//		
//		///detail abc
//		
//		int k = 2;
//		Product p;
//		String s;
//		for(int i = 0; i <= listSeller.size();i++)
//			for(int j = 0;j <= listSeller.get(i).numberproduct;j++)
//			{	
//				Seller X = listSeller.get(i);
//				s = this.common.findCss("tbody tr:nth-child("+Integer.toString(k)+") td:nth-child(2)").getAttribute("innerHTML");
//				Assert.assertEquals("Fail-name product -detail page ",
//						Boolean.TRUE,s.split("<br>")[0].trim().equals());
//				
//				k++;
//			}
//		k++;	
//	}
	
	public void checkDetailpage() {
		List<Seller> sellers = new ArrayList<Seller>();
		
	}
	
//	public List<Seller> getSellersDetailInvoie(){
//		List <Seller> listSeller = new ArrayList<Seller>();
//		// parse HTML to product
//		List<WebElement> listTR = this.common.findElementsByCss("table.invoice-table tr");
//		for(WebElement tr : listTR) {
//			Seller seller = null;
//			if(tr.getAttribute("class").contains("text-left membership-points")) {
//				seller = new Seller();
//				seller.sellerName = tr.getText().trim();
//			}else {
//				Product product = new Product();
//				seller.products.add(product);
//			}
//			
//		}
//		listSeller.add(seller);
//		return listSeller;
//	}
	
	
}