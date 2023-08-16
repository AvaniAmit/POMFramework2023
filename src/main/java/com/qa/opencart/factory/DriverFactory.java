package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
 

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

/**
 * 
 * @author sutar
 *
 */

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This is used to initia;ize the driver
	 * 
	 * @param browserName
	 * @return it returns WebDriver
	 */
	// public WebDriver initDriver(String browserName)
	// we dont need to pass multipel value using somma so we use prop
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");

		//String browserName=System.getProperty("browser");//we are passing browser through maven not through config.properties files 
		System.out.println("BrowserName is : " + browserName);

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase()) {
		case "chrome":
			// driver=new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			// driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			// driver=new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Plz enter the right browser...... " + browserName);
			break;
		}

		//getDriver()- why this method bcz it returns ThreadLocal driver not a normal driver
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}
	
	public static WebDriver getDriver()
	{
		return tlDriver.get();
		//it will return ThreadLocal driver not a normal driver
	}

	/**
	 * This is used to initialize the properties
	 * 
	 * @return it returns Properties
	 */

	public Properties initProp() {
		
		FileInputStream ip=null;
		prop = new Properties();
		
	
		//mvn clean install -Denv="qa"
		
		//-D means environment variable that i really want to supply 
		//env- it is a variable name we can give any name like -Dnaveen also
		//we have to read this environment , where - inside initProp()
		
		String envName=System.getProperty("env");
		System.out.println("Env name is: " + envName);
		
		try
		{
		if(envName==null)
		{
			System.out.println("No env is given.......hence running it on QA environment..");
		 
				ip = new FileInputStream("./src/resources/config/config.properties");
			 
				
  		}
		else {
			 
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/resources/config/stage.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/resources/config/uat.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/resources/config/config.properties");
				break;
			default:
				System.out.println("Please pass the right env name: " + envName);
				break;
			}
		}
	}	
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
		 

		return prop;

	}
	
	/**
	 * take a screenshot
	 * @return 
	 */
	public static String getScreenshot(String methodName)
	{
		//TakesScreenshot ts=(TakesScreenshot)tlDriver();it is also allow
		//there is no connection between WebDriver and TakesScreenshot interface so we need to
		//convert driver into TakesScreenshot interface 
		
//		TakesScreenshot ts=(TakesScreenshot)getDriver();
//		ts.getScreenshotAs(OutputType.FILE); instead of this we can write as per below
		
		File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);

		String path=System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
	
	    File destination=new File(path);
	    
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	      
		return path;
	
	}
}
