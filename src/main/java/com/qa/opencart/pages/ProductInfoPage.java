package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage  {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader=By.cssSelector("div#content h1");
    private By imageCount=By.cssSelector("ul.thumbnails img");
    private By quantity=By.name("quantity");
    private By addToCartBtn=By.id("button-cart");
    private By productMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
    private By productPricingData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
    private Map<String,String> productMap;
    
    
    
	public ProductInfoPage(WebDriver driver) {
		 this.driver=driver;
		 eleUtil=new ElementUtil(driver);
		 
	}
	
	public String getProductHeader()
	{
		return eleUtil.doElementGetText(productHeader);
	}
	
	public int getProductImagesCount()
	{
		int accProductImagecount =eleUtil.waitForAllElementsVisible(imageCount, AppConstants.MEDIUM_TIME_OUT).size();
	    System.out.println("Total product images for: " +getProductHeader() + "===========>" + accProductImagecount);
	    return accProductImagecount;
	}
	
	//Brand: Apple
	//Product Code: Product 18
	//Reward Points: 800
	//Availability: In Stock
	
	//public void getProductMetaData()
	private void getProductMetaData()
	{
		List<WebElement> metaList=eleUtil.waitForElementsVisible(productMetaData,AppConstants.MEDIUM_TIME_OUT);
		 
		//Map<String,String> metaMap=new HashMap<String,String>(); 
		
		  
		for(WebElement e:metaList)
		{
			String metaText=e.getText();
			//split() return string[] array
			//but here we are fetching 0th index value so we can store in string 
//			String[]  text=metaText.split(":");
//			System.out.println(text[0]);
			//instead abouve we can write as per below
			
			String key=metaText.split(":")[0].trim();
			String value=metaText.split(":")[1].trim();
			productMap.put(key, value);
		}
		
		//return metaMap;
	}
	
	//$2,000.00
	//Ex Tax: $2,000.00

	//if someone call this method then productMap.put("priceKey", actPrice); productMap.put(exTaxKey, exTaxValue);
	//this productMAp will give us null Pointer bcz we are initializing productMAp ref into getProductData()
	//productMap=new HashMap<String,String>(); 
	//so what we can do is we use encapsulation concept
	//we will restrict access of getProductpricingData() and getProductMetaData() 
	//this two method is internal method use to fectch some basic info 
	
	//public void getProductpricingData()
	private void getProductpricingData()
	
	
	{
		List<WebElement> priceList=eleUtil.waitForElementsVisible(productPricingData,AppConstants.MEDIUM_TIME_OUT);
		 
		 //Map<String,String> priceMap=new HashMap<String,String>();

			//$2,000.00 
		 String actPrice=priceList.get(0).getText().trim();
		//Ex Tax: $2,000.00
		 String exTaxKey=priceList.get(1).getText().split(":")[0].trim();
		 String exTaxValue=priceList.get(1).getText().split(":")[1].trim();
			 
		 productMap.put("priceKey", actPrice);// if we dont have key in datat then we can define our own key
		 productMap.put(exTaxKey, exTaxValue);
		 
		 //return priceMap;
	
	}
	
	//master method to get entier data about product including metadat, pricing data, image count everything altogether
	
	public Map<String, String> getProductData()
	{ 
		productMap=new HashMap<String,String>();
		//productMap=new LinkedHashMap<String,String>();
		//productMap=new TreeMap<String,String>();
		
//		/productMap=new HashMap<String,String>();
		//HashMap doesnt not store the value on index/order based so
		//we can use LinkedHashMap bcz it stores the value on order based
		
		//productMap=new LinkedHashMap<String,String>();
		
		//LinkedHashMap- maintain the values the way we insert it will display the value in same order
		
		//if we really want to maintain the alfabetic order or sorted order
		// i want capital A first then we have to use TreeMAp collection
		//it doesn't bother about the value, the sorting will happen on the basis of Keys
		
		//productMap=new TreeMap<String,String>();
		
		productMap.put("productheader", getProductHeader());
		productMap.put("productimages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductpricingData();
		
		return productMap;
		
	}

}
