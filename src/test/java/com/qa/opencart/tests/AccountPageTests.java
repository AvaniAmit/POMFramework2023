package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.consatant.AppConstants;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
 
 
@Epic("EPIC-101: Design of the Accounts page for open CArt App ")
@Story("US- 201: implement Accounts page feature for open cart app")
public class AccountPageTests extends BaseTest{
	
	
	@BeforeClass
	public void accPageSetup()
	{
		accPage=loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		//if we dont want to expose our password in our framework then we can supply pass from command line
		// -DAppPassword="123@123"
		//here instead prop.getProperty() we have to write System.getProperty("AppPassword")
		//accPage=loginpage.doLogin(prop.getProperty("username"),System.getProperty("Apppassword"));
		
	} 
	
	@Test(priority=1)
	public void accPageTitleIs()
	{
		//to call accountpage class method we need to create object of accountpage classs
		//if we create object here then let say we have 5 test case for account page then we have to
		//create accountpage class object 5 times in all test cases
		//AccontsPage accPage=new AccountsPage(driver);
		//we can create object under @BeforeClass of this class
		//but we have another approach
		String accPageTitle=accPage.getAccPageTitle();
		Assert.assertEquals(accPageTitle, AppConstants.ACCOUNT_PAGE_TITLE);
		
	}
	
	@Test(priority=3)
	public void logoutLinkExistTest()
	{
		 Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test(priority=2)
	public void accPageHeadersCountTest()
	{
		int accAccountPageHeaderCounts=accPage.getAccountsPageHeaderCount();
		System.out.println("Acctual Account Page Header Count = " + accAccountPageHeaderCounts);
		Assert.assertEquals(accAccountPageHeaderCounts, AppConstants.ACCOUNTS_PAGE_HEADER_COUNTS);	
	}
	
	@Test(priority=4)
	public void accPageHeaderTest()
	{
		List<String> actAccPageHeaderList =accPage.getAccountsPageHeader();
	    Assert.assertEquals(actAccPageHeaderList,  AppConstants.EXPECTED_ACCOUNT_PAGE_HEADER_LIST);
	
	}
	
	@DataProvider
	public Object[][] getSearchKey()
	{
		return new Object[][] {
			{"macbook",3},
			{"apple",1},
			{"canon",1},
			{"samsung",1} 
			 
		};
		
	}
	
	@Test(priority=5,dataProvider="getSearchKey")
	public void accSearchTest(String searchKey,int expResultCount)
	{
		accSearchPage=accPage.doSearch(searchKey);
		int actResultsCount=accSearchPage.getSearchProductResultCount();
	     Assert.assertEquals(actResultsCount, expResultCount);
	}
	
}
 