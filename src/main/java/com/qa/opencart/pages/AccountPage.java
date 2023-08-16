package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage {
	private WebDriver driver;
	
	private ElementUtil eleUtil;
	
	//object repository
	//private by locators
	
	private By logoutLink=By.linkText("Logout");
	
	private By accHeaders=By.cssSelector("div#content h2");
	
	private By  search=By.name("search");
	
	private By searchIcon=By.cssSelector("div#search button");
	
	public AccountPage(WebDriver driver)
	{
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	//3.Public Page Actions
	public String getAccPageTitle()
	{
		return eleUtil.waitForTitleIs(AppConstants.ACCOUNT_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
	}
	
	public String getAccPageUrl()
	{
		return eleUtil.waitForURLContains(AppConstants.ACCOUNT_PAGE_URL_FRACTION,AppConstants.SHORT_TIME_OUT);
	}
	
	public boolean isLogoutLinkExist()
	{
		return eleUtil.waitForElementPresence(logoutLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeader()
	{
		 List<WebElement> headerList= eleUtil.waitForElementsVisible(accHeaders, AppConstants.LONG_TIME_OUT);
	
		  List<String> headerTextList=new ArrayList<String>();
	     for(WebElement e:headerList)
	     {
	    	 String headers=e.getText();
	    	 headerTextList.add(headers);
	     }
	     
	     return headerTextList;
	}
	
	public int getAccountsPageHeaderCount()
	{
		return eleUtil.waitForElementsVisible(accHeaders, AppConstants.LONG_TIME_OUT).size();
	}
	
	public AccountSearchResultPage doSearch(String searchKey)
	{
		WebElement searchField=eleUtil.waitForElementVisible(search,AppConstants.MEDIUM_TIME_OUT);
		searchField.clear();
		searchField.sendKeys(searchKey);
	    eleUtil.doClick(searchIcon);
	    
	    return new AccountSearchResultPage(driver);
	}
	
	
}