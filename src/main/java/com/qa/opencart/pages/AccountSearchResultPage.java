package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountSearchResultPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productResult=By.cssSelector("div.product-layout");
    
	
	
	public AccountSearchResultPage(WebDriver driver)
	{
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public int getSearchProductResultCount()
	{
		return eleUtil.waitForElementsVisible(productResult,AppConstants.SHORT_TIME_OUT ).size();
	}

	
	public ProductInfoPage selectProduct(String productName)
	{
		 //By.linkText(productName) - to create locaotr dynamic instead of createing by locator at class level 
		//bcz whatever productname we pass we want that locator ahould br create and click on that spcefi product
		eleUtil.clickElementWhenReady(By.linkText(productName),AppConstants.MEDIUM_TIME_OUT);
	    return new ProductInfoPage(driver);
	}
	
 
}
