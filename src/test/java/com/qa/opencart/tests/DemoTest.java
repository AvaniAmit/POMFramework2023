package com.qa.opencart.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ElementUtil;

public class DemoTest extends BaseTest{
	
	ElementUtil eleUtil;
	
	@BeforeMethod
	public void demoSetup()
	{
		driver.get("https://classic.freecrm.com/");
		eleUtil=new ElementUtil(driver);
	}
	@Test
	public void testdemo()
	{
		//eleutil.getElement(By.name("username")).sendKeys("avani");
		 
	    eleUtil.doSendkeys(By.name("username"),"avani");
	   eleUtil.doSendkeys(By.name("password"),"avani");
	   eleUtil.doClick(By.xpath("//input[@value='Login']"));    
	}
	
	

}
