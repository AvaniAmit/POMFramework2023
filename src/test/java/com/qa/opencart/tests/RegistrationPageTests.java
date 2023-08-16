package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTests extends BaseTest{
	
	 
	
	@BeforeClass
	public void registerationSetup()
	{
		regPage=loginpage.navigateToRegisterPage();
	}
	
	@Test(priority=1)
	public void registrationPageTitleTest()
	{
		String accTitle=regPage.regPageTitle();
		Assert.assertEquals(accTitle, AppConstants.REGISTRATION_PAGE_TITLE);
	}
	
	public String getRandomEmailId()
	{
		return "openauto"+System.currentTimeMillis()+"@open.com";
	}
	
	@DataProvider
	public Object[][] getUserRegData()
	{
		return new Object[][] { //3X5 three by five matrix
			{"Pooja","Agrwal","1234567899","pooja@123","yes"},
			{"shubham","gupta","5234567899","shubham@123","no"},
			{"mitaj","kumar","8234567899","mitaj@123","yes"},
			
		};
	}
	
	@DataProvider
	public Object[][] getUserRegSheetData()
	{
		 return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Test(priority=2,dataProvider="getUserRegSheetData")
	public void registerUserTest(String firstname,String lastName,String telephone, String password, String subscribe)   
	{
		//registerUser- return true so we use assertTrue for asssertion
		Assert.assertTrue(regPage.registerUser(firstname, lastName,getRandomEmailId(),telephone,password,subscribe));
	}

}
 