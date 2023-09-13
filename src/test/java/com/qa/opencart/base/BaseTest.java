package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

 
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;

import com.qa.opencart.pages.AccountSearchResultPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterationPage;
import com.qa.opencart.pages.RegistrationPage;

public class BaseTest {

	protected WebDriver driver;
	protected LoginPage loginpage;
	// default access modifier is not allowed outside of package
	// public will access by anyone
	// private- we can not access outside of this class
	// protected- it will allow me in the same package and outside of the package
	// for the child class also

	protected AccountPage accPage;// its a good practise to write all class ref variable in one palce
	// so we can focus on testcases on pagetest class
	// and other people also can able to use ref variable from here

	protected AccountSearchResultPage accSearchPage;
	protected ProductInfoPage productInfoPage;
	protected DriverFactory df;
	protected RegisterationPage regPage;
	protected Properties prop;
	
	protected SoftAssert softAssert;
	

     @Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();

		prop = df.initProp();
		
		if(browserName!=null)//it means passing browser from testng.xml
		{
			prop.setProperty("browser", browserName);
		}
		driver = df.initDriver(prop);// it is called call by refrence
		//this above webdriver is getting Threadloacl WebDriver so this driver will automatically become ThreadLoacl driver  
		 
		loginpage = new LoginPage(driver);// if we dont initialize loginpage refrence vari here then inside
											// loginpagetest class we will get NullPointerException when we try to call
											// methods of lopginpage class by using loginpage ref variable
		softAssert=new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
