package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver)
	{
		this.driver=driver;
		js=(JavascriptExecutor)this.driver;
	}
	
	public String getTitleByJS()
	{
		return js.executeScript("return document.title;").toString();
		
	}
//******************************Go Back and Forward***********************
	
	public void goBackWithJS()
	{
		js.executeScript("history.go(-1)");
	}
	
	public void goForwardWithJS()
	{
		js.executeScript("history.go(1)");
	}
	
	public void doPageRefreshWithJS()
	{
		js.executeScript("history.go(0)");
	}
	
//*****************************JAvaScript Alert Pop UP**********************************
	public void generateJSAlert(String msg)
	{
		js.executeScript("alert('"+msg+"')");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept(); 
	}
	
	public void generateJSAlertPrompt(String msg, String value)
	{
		js.executeScript("prompt('"+msg+"')");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alert alert= driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}
	
	public void generateJSAlertConfirm(String msg)
	{
		js.executeScript("confirm('"+msg+"')");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept(); 
	}
	
	public String getEntierPageText()
	{
		//for content check 
		//it will return text of enteir page
		// this is vary use full for those manual tester whos doing lots of content testing
		//bca manual it is very time taking so automation make it quick
		return js.executeScript("return document.documentElement.innerText;").toString();
		  
	}
//******************************************Scrolling*******************************
	public void scrollPageUP()
	{
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}
	
	public void scrollPageDown()
	{
		js.executeScript("window.scrollTo(document.body.scrollHeight,0);");
	}

	//to scroll page until spacific height
	
	public void scrollPageDown(String height)
	{
		js.executeScript("window.scrollTo(0,'"+height+"');");
	}
	
	//scroll until middle of the page
	
	public void scrollPageDownMiddlepage()
	{
		js.executeScript("window.scrollTo(0,documnet.body.scrollHeight/2)");
	}

	public void scrollIntoView(WebElement element)
	{ 
		js.executeScript("arguments[0].scrollIntoView(true);",element);
		
	}
	
	public void scrollHorizontally()
	{
		js.executeScript("window.scrollBy(0,1000);");
	}
	
/**
 *  Zoom in and Zoom out using JSE
 *  example: "document.body.style.zoom ='3000.0%' "
 *  @param zoomPercentage 
 */
 
	public void zoomChromeEdge(String zoomPercentage)
	{
		String zoom="document.body.style.zoom= '"+zoomPercentage+"%'";
		
		js.executeScript(zoom);
		
	}
 
/**
*  Zoom in and Zoom out using JSE
*  example: "document.body.style.MozTransform ='scale(0.5)' " means 50% zoom
*  @param zoomPercentage 
*/
	 
	public void zoomFireFox(String zoomPercentage)
	{
		String zoom="document.body.style.MozTransform= 'scale("+zoomPercentage+")'  ";
		
		js.executeScript(zoom);
		
	}		
	
//****************************Draw Borde Around Element*****************

	public void drawBorder(WebElement element)
	{
		js.executeScript("arguments[0].style.border= '3px red solid'", element);
	}
	 
//*************************Highlight / Flashing Element***************8
	//to identifying where is our driver/ on which element driver is present 
	
	public void flashElement(WebElement element)
	{
		String bgColor=element.getCssValue("backgr oundColor");
		String color="rgb(0,200,0)";// it is color code for green
		
		for(int i=0;i<10;i++)
		{
			
			changeColor(color,element);// first green
			changeColor(bgColor,element);// then purple
		}
		
		
	}
	
	private void changeColor(String color, WebElement element)
	{
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",element);
	    
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	  
    
	
	
	
}
 