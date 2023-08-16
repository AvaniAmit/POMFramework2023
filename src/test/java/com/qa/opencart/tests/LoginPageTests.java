package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.consatant.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
//import com.qa.opencart.listeners.ExtentReportListener;


//@Listeners(ExtentReportListener.class)
@Epic("EPIC-100: Design of the login page for open CArt App ")
@Story("US- 200: implement login page feature for open cart app")
public class LoginPageTests extends BaseTest{
	
	@Description("login page title test.....")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=1)
	public void loginPageTitleTest()
	{
		String actTitle=loginpage.getLoginPageTitle();
		Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description("login page URL test.....")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void loginPageUrlTest()
	{
		String actUrl=loginpage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("check Forgot password link test.....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void isForgotPwdLinkExistTest()
	{
		Assert.assertTrue(loginpage.isForgotPwdLinkExist());
	}
	
	@Description("check user is able to login to opncart app with valid credentials.....")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=4)
	public void loginTest()
	{
//		String accountPageTitle=loginpage.doLogin("jaykrishna@gmail.com", "123#123");
//	    Assert.assertEquals(accountPageTitle, AppConstants.ACCOUNT_PAGE_TITLE);
	
		accPage =loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
        Assert.assertEquals(accPage.isLogoutLinkExist(), true);
	}
	
	 
 
}
