package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;
 

public class LoginPage {
	
	private WebDriver driver;
    private ElementUtil eleutil;
    private AccountPage accPage;
  
	//1.private By locators -- also called as page locators, page eleements
	
	//2.public Page Constructor  ---- to get my driver
	
	//3.public Page Actions--Page methodds, here we define page behaviour
	
	
	//1.private By locators -- also called as page locators, page eleements
	private By emailId=By.id("input-email");
	private By password=By.id("input-password");
	private By loginBtn=By.cssSelector("input.btn");
	private By forgotPWdLink=By.linkText("Forgotten Password");
	private By RegisterLink=By.cssSelector("div.list-group a[href*='register']");
	private By home=By.linkText("homepage");//dummy link 
	//2.public Page Constructor  ---- to get my driver
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		eleutil=new ElementUtil(driver);
	}
	
	//3.public Page Actions--Page methodds, here we define page behaviour
	@Step("........getting login page title....")
	public String getLoginPageTitle()
	{
		//String title= driver.getTitle(); instead of this line we will use element utility methods
		String title=eleutil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page title is: " +title);
		return title;
	}
	@Step("........getting login page URL....")
	public String getLoginPageUrl()
	{
		//String url= driver.getCurrentUrl();
		String url=	eleutil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page url is: " +url);
		return url;
	}
	
	// to check forgot PwD link is displayed or not
	@Step("........is forgot pwd link exist or not....")
	public boolean isForgotPwdLinkExist()
	{
		//return driver.findElement(forgotPWdLink).isDisplayed();
	    return eleutil.waitForElementVisible(forgotPWdLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("login to app with username:{0} and password: {1}")//paramete 0 means username and 1 is password
	public AccountPage doLogin(String username, String pwd)
	{
		System.out.println("App creds are: " + username + ":"+ pwd);
//	    driver.findElement(emailId).sendKeys(username);
//	    driver.findElement(password).sendKeys(pwd);
//	    driver.findElement(loginBtn).click();
		
		eleutil.waitForElementVisible(emailId, 10).sendKeys(username);
		eleutil.doSendkeys(password, pwd);
		eleutil.doClick(loginBtn);
	    //to validate that we sucessfully login and land on account page
	    //return driver.getTitle(); //get account page title 
	    
		//return eleutil.waitForTitleIs(AppConstants.ACCOUNT_PAGE_TITLE,AppConstants.SHORT_TIME_OUT);
	   
		return new AccountPage(driver);
	
	}
	 
	@Step("Navigating to Registeration page.........")
	public RegisterationPage navigateToRegisterPage()
	{
		eleutil.waitForElementVisible(RegisterLink, AppConstants.SHORT_TIME_OUT).click();
		return new RegisterationPage(driver);
	}
	
	
	
}

 