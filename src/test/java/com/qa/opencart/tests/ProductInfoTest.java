package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.consatant.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;


@Epic("EPIC-103: Design of the Product Info page for open CArt App ")
@Story("US- 203: implement Product Info page feature for open cart app")
public class ProductInfoTest extends BaseTest {
	
	
	@BeforeClass
	public void productInfoSetup()
	{
		 
		accPage=loginpage.doLogin("jaykrishna@gmail.com","123#123");
		 
	}
	
	@DataProvider
	public Object[][] productTestData()
	{ 
		return new Object[][] {
	    
			{"macbook","MacBook Pro"},
			{"macbook","MacBook Air"},
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@DataProvider
	public Object[][] productExcelData()
	{
		return ExcelUtil.getTestData(AppConstants.PRODUCTINFO_SHEET_NAME);
		 
		
	}
	
	@Test(dataProvider="productExcelData")
	public void productHeaderTest(String searchKey, String productName)
	{
		accSearchPage=accPage.doSearch(searchKey);//this is not a pre-condition it is a actual test step
		productInfoPage=accSearchPage.selectProduct(productName);
		String accProductHeader=productInfoPage.getProductHeader();
		Assert.assertEquals(accProductHeader, productName);
	
	}
	
	@DataProvider
	//this are our expected data 
	public Object[][] productData()
	{ 
		return new Object[][] {
	    //[][]- is for row and column doesnt matter hownmany column we have  
			{"macbook","MacBook Pro",4},
			{"macbook","MacBook Air",4},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Samsung","Samsung Galaxy Tab 10.1",7}
		};
		
	}
	
	
	
	@Test(dataProvider="productData")
	public void productImageCountTest(String searchKey, String productName, int expProductImageCount )
	{
		accSearchPage=accPage.doSearch(searchKey);
		productInfoPage=accSearchPage.selectProduct(productName);
		int accProductImageCount=productInfoPage.getProductImagesCount();
	     Assert.assertEquals(accProductImageCount,expProductImageCount);
	}
	
	@Test
	public void productInfoTest()
	{
		accSearchPage=accPage.doSearch("MacBook");
		productInfoPage=accSearchPage.selectProduct("MacBook Pro");
		Map<String,String> productActData=productInfoPage.getProductData();
		System.out.println(productActData);
		softAssert.assertEquals(productActData.get("Brand"), "Apple");
		softAssert.assertEquals(productActData.get("Availability"), "In Stock");
		softAssert.assertEquals(productActData.get("productheader"), "MacBook Pro");
		softAssert.assertEquals(productActData.get("priceKey"), "$2,000.00");
		softAssert.assertEquals(productActData.get("Reward Points"), "800");
		softAssert.assertAll();
	
	}
	
	

}
