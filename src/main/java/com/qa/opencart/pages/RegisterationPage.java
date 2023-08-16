package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterationPage {

	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName=By.id("input-firstname");
	private By lastName=By.id("input-lastname");
	private By email=By.id("input-email");
    private By telephone=By.id("input-telephone");
    private By password=By.name("password");
    private By confirmPwd=By.name("confirm");
    
    private By  subScribeYes=By.xpath("//label[normalize-space()='Yes']");
    private By  subScribeNo  =By.xpath("//label[normalize-space()='No']");
    
    private By agreeCheckBox=By.name("agree");
    private By continuBtn=By.cssSelector("input[type='submit']");
	
    private By successMsg=By.cssSelector("div#content h1");
    private By logoutLink=By.linkText("Logout");//after register one user we need another user to register so for that first we have to logout and click on register link to go registration page 
    private By registerLink=By.linkText("Register");
    
    public RegisterationPage(WebDriver driver) {

		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}

	 
	public String regPageTitle()
	{
		return eleUtil.waitForTitleIs(AppConstants.REGISTRATION_PAGE_TITLE, AppConstants.MEDIUM_TIME_OUT);
	}
	
	public boolean registerUser(String firstName,String lastName,String email, String telephone,
			String password, String subsctribe)
	{
	        eleUtil.waitForElementVisible(this.firstName, AppConstants.MEDIUM_TIME_OUT).sendKeys(firstName);
	        //now we dont need to wait for rest of elements so we are using simple sendkeys method od ElementUtil class
	        eleUtil.doSendkeys(this.lastName, lastName);
	        eleUtil.doSendkeys(this.email,email);
	        eleUtil.doSendkeys(this.telephone, telephone);
	        eleUtil.doSendkeys(this.password, password);
	        eleUtil.doSendkeys(this.confirmPwd, password);
	        
      	
	        if(subsctribe.equalsIgnoreCase("yes"))
	        {
	        	eleUtil.doClick(subScribeYes);
	        }else {
	        	eleUtil.doClick(subScribeNo);
	        }
	
	        eleUtil.doClick(agreeCheckBox);
	        eleUtil.doClick(continuBtn);
	        
	       String successMsg= eleUtil.waitForElementVisible(this.successMsg, AppConstants.MEDIUM_TIME_OUT).getText();
	       System.out.println(successMsg);
	       
	       if(successMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MSG))
	       {
	    	 //after register one user we need another user to register so for that first we have to logout and click on register link to go registration page 
	    	   
	    	   eleUtil.doClick(logoutLink);
	    	   eleUtil.doClick(registerLink);
	    	   
	    	   return true;
	       }
	       return false;
	       //we can return successmsg also instead true but we can do either of it
	       
	}
	
	

}
