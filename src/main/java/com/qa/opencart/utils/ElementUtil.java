 package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//import CustomException.FrameworkExcetion;

//why we creating custom utility ?
//so that we can provide our own custom checks 
//we cn achieve reuseability- we can use this utility methods for any applications and anyone can use but along with it we can do error handling , exception handling.checks handling 
public class ElementUtil {

	// why we are making webDriver driver private?
	// bcz if we make it public then tomorrow anyone can create an object of this
	// class and satrat accessing thius driver refrence variable
	// so privent this thing we have created private

	// why we are not making it static?
	// if we create static webdriver then it will store in CMA means we can not do
	// parallel execution
	// let say i want to run 5 testcase and this static driver will be used by first
	// testcase then at the same time secondtestcase will not able to used it
	// why? bcz static property means only create one copy in CMA
	// here i want that every testcase or every thread has seperate copy of
	// WebDriver driver not a static copy so we avoid static here.
	// static will hold the common copy, it will never be distributed among the all
	// other objects so it will create a sequencial execution not the parallel
	// execution

	private WebDriver driver;
	// we just create the refrence of WebDriver interface
	// to initialize class variable we have to create class constructor

	private Actions act;
	private JavaScriptUtil jsUtil;
	

	public ElementUtil(WebDriver driver) {
		// public ElelementUtil(WebDriver driver)-who is supplying the driver to this
		// constructor bcz right now -
		// value of private WebDriver driver; drvier is null;
		// whenever we create the object of this class we have to pass the driver ref
		// bacz we can not create default object as we dont
		// while we pass the driver in this class object we are actually passing session
		// id of webdriver driver of Locators class
		// have default constructor of this class so driver refrence will come from the
		// class in whic we create an object of thid class to call methods

		this.driver = driver;
		act = new Actions(driver);
		jsUtil=new JavaScriptUtil(driver);
	}

	// do not supply the driver in method then we have to supply driver for all 100
	// or 1000 methods so it is better to suppply the driver to constructor
	@Step("getting element for locator:{0}")
	public WebElement getElement(By locator) {
		// How will you get the driver here in this class?
		// we can use concept of constructor
		// here first we will create one private WebDriver driver refrence
		// if selenium unable to find element then it will throw nosuchelement exception
		// so we need to handlle it here
		// with try catch

		//WebElement element = null;

		 WebElement element=driver.findElement(locator);
		
		 if(Boolean.parseBoolean(DriverFactory.highlight))
		 {
			 jsUtil.flashElement(element);
			 
		 }
		
//		try {
//				element = driver.findElement(locator);// if selenium unable to find element due to incorrect locator has
//													// supply and we
//			// try to create webelement so this line is a culprit
//
//		} catch (NoSuchElementException e) {
//			System.out.println("getting element exception....plz check your locator again");
//			e.printStackTrace();
//
//			// let sya after 2 sec element is visible then we can provide wait here to wait
//			// before throw exception
//
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException ex) {
//				ex.printStackTrace();
//			}
//			// after 2 second wait element is visible then we want selenium can try to find
//			// element so again we have to write driver.findelemnt
//			element = driver.findElement(locator);
//		}

		return element;

	}

	@Step("....entering value:{1} in locator: {0}")
	public void doSendkeys(By locator, String value) {

		if (value == null) {
			System.out.println("Value can not be null while using sendkeys method");
			//throw new FrameworkExcetion("VALUECANNOTBENULL");
		}

		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public boolean chekcElemenetIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public boolean checkElementIsDisabled(By locator) {
		String disabled_Val = getElement(locator).getAttribute("disabled");// we just have to pass disabled bcz
																			// attribute will remain same for all
																			// disabled element so no need to pass as
																			// argument
		if (disabled_Val.equals("true")) {
			return true;
		}
		return false;

		// OR
//			boolean flag=getElement(locator).isEnabled();
//			if(flag==false)
//			{
//				return true;
//			}
//			return false;
	}

	public int getWebElementsConut(By locator) {
		return getWebElements(locator).size();
	}

	public List<WebElement> getWebElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getWebElements(locator);
		// ArrayList<String>eleTextList=new ArrayList<String>();//we can write this way
		// also but through below line we achieve top casting as List is a parent
		// interface of ArrayList
		List<String> eleTextList = new ArrayList<String>(); // if we carete arraylist inside loop then it will create
															// arraylist for each time loop will execute

		for (WebElement e : eleList) {
			String text = e.getText();
			// we dont want to print bcz it doesnt resolve the issue bcz if we are printing
			// here then how we will validate
			// to validte the link texr we need to store the link list into some array or
			// arrayist
			// we can not use array as it is static so use arraylist of string bcz we will
			// get text for all links

			if (!text.isEmpty()) {
				eleTextList.add(text);
			}

		}
		return eleTextList;

	}

	public void clickOnElement(By locator, String linkText) {
		List<WebElement> linksList = getWebElements(locator);

		System.out.println(linksList.size());

		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(linkText)) {
				e.click();
				break;
			}

		}

	}

	public void doSearch(By searchBoxLocator, By suggestListLocator, String searchText, String suggestText)
			throws InterruptedException {
		// driver.findElement(searchBoxLocator).sendKeys(searchText);
		// instead of above line we write as per below
		doSendkeys(searchBoxLocator, searchText);
		Thread.sleep(2000);

		// List<WebElement> suggestList=driver.findElements(suggestListLocator);
		// instead of above line we write as per below
		List<WebElement> suggestList = getWebElements(suggestListLocator);
		Thread.sleep(4000);
		System.out.println(suggestList.size());
		for (WebElement e : suggestList) {
			String text = e.getText();
			if (text.contains(suggestText)) {
				e.click();
				break;
			}
		}

	}

	public WebElement getLinkElementByText(String linkText) {
		return driver.findElement(By.linkText(linkText));
	}

//******************************DropDowns Utilis*********************************

	public void doSelectDropDownByIndex(By locator, int index) {
		if (index < 0) {
			System.out.println("please pass the right (+ve ) index");
			return; // it return nothing but it stop execution if user pass negative index
		}

		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		if (text == null) {
			System.out.println("Please pass right visible text and it cannot be null");
			return; // it return nothing but it stop execution if user pass negative index
		}

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		if (value == null) {
			System.out.println("Please pass right visible text and it cannot be null");
			return; // it return nothing but it stop execution if user pass negative index
		}

		Select select = new Select(getElement(locator));
		select.selectByValue(value);

	}

	// get count of options available in dropdown
	public int getDropDownOtionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();

	}

	// give me all otions text
	public List<String> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions();

		List<String> optionTextList = new ArrayList<>();

		for (WebElement e : optionsList) {
			String text = e.getText();
			optionTextList.add(text);
		}

		return optionTextList;

	}

	// select dropdown options without three methods of Select class
	public void doSelectDropDownValue(By locator, String dropDownValue) {
		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions();

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}

		}

	}

	// select dropdown without select class

	public void doSelectDropDownVaueWithoutSelectClass(By locator) {
		List<WebElement> optionList = getWebElements(locator);

		for (WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains("India")) {
				e.click();
				break;
			}
		}
	}

//******************************Actions MEthod*********************************

	public void selectRightClickOptions(By contextMenuLocator, String optionValue) {
		WebElement rightClickEle = getElement(contextMenuLocator);

		// Actions act=new Actions(driver);

		act.contextClick(rightClickEle).build().perform();

		// locator for options of context menu
		// By optionLocator= By.xpath("//span[text()= '" + optionValue+ "']");//here
		// span is a hard code value what if tomorrow there is a link so we will *
		// instead tag name
		By optionLocator = By.xpath("//*[text()= '" + optionValue + "']");
		doClick(optionLocator);

	}

	// menu and submenu : Two level menu

	public void multiLevelMenuHandling(By parentMenuLocator, By childMenuLocator) {
		// Actions act=new Actions(driver);

		act.moveToElement(getElement(parentMenuLocator)).build().perform();

		doClick(childMenuLocator);

	}

	// menu and submenu : Three level menu
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException {

		// Actions act=new Actions(driver);

		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(3000);

		act.moveToElement(getLinkElementByText(level2)).build().perform();
		Thread.sleep(1500);

		getLinkElementByText(level3).click();
		Thread.sleep(1500);

	}

	// menu and submenu : four level menu
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4)
			throws InterruptedException {

		// Actions act=new Actions(driver);

		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(3000);

		act.moveToElement(getLinkElementByText(level2)).build().perform();
		Thread.sleep(1500);

		act.moveToElement(getLinkElementByText(level3)).build().perform();
		Thread.sleep(1500);

		getLinkElementByText(level4).click();
		Thread.sleep(1500);

	}

	// Action class sendKeys() and click()
	public void doActionsClicks(By locator) {
		// Actions act=new Actions(driver);
		act.click(getElement(locator)).perform();

	}

	public void doActionsSendKeys(By locator, String value) {
		// Actions act=new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();

	}

	// **********************************Wait Utility********************
	/*
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does notnecessarily mean that the element is visible on the webpage
	 * 
	 * @Param:locator
	 * 
	 * @param:timeout Returns:the WebElement once it is located
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresence(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(pollingTime));
		WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	    if(Boolean.parseBoolean(DriverFactory.highlight))
	    {
	    	jsUtil.flashElement(element);
	    }
		
		return element;
	}
	
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public void clickElementWhenReady(By locator, int timeOut)
	{
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that isgreater than 0.
	 * 
	 * @param locator
	 * @param timeOut Returns : WebElement
	 * @return
	 */
   @Step("...waitning for element is visble for locator:{0} with the timeout:{1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight))
	    {
	    	jsUtil.flashElement(element);
	    }
		return element;
	
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}
	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}
	
	
	

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForAllElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForAllElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	// ***************Title IS present or not***********

	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			} else {
				System.out.println(titleFraction + "Tile value is not present...not.");
				return null;
			}

		} catch (Exception e) {
			System.out.println(titleFraction + "Tile value is not present...not.");
			return null;

		}

	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param titleValue
	 * @param timeOut
	 * @return
	 */
	@Step("Waiting for title and getting title")
	public String waitForTitleIs(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			if (wait.until(ExpectedConditions.titleContains(titleValue))) {
				return driver.getTitle();
			} else {
				System.out.println(titleValue + "Title is not present...not.");
				return null;
			}

		} catch (Exception e) {
			System.out.println(titleValue + "Tile value is not present...not.");
			return null;

		}

	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * 
	 * @param urlFraction
	 * @param timeOut
	 * @return
	 */
	@Step("...Waititng for Page URL and fetching it......url fraaction : {0}")
	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlFraction + "URL value is not present...not.");
				return null;
			}

		} catch (Exception e) {
			System.out.println(urlFraction + "URL value is not present...not.");
			return null;

		}
	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * 
	 * @param urlValue
	 * @param timeOut
	 * @return
	 */
	public String waitForURLToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlValue + "URL value is not present...not.");
				return null;
			}

		} catch (Exception e) {
			System.out.println(urlValue + "URL value is not present...not.");
			return null;

		}
	}
//*************************************Alert Utility**************

	/**
	 * An Expectation for the alert(JS) to be appeared on the page
	 * 
	 * @param timeOut
	 * @return
	 */
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public Alert waitForJSAlert(int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.alertIsPresent());

	}
	

//******************Number of Window Present********

	/**
	 * 
	 * @param timeOut
	 * @param numberOfWindowsToBe
	 * @return
	 */
	public Boolean waitForBrowserWindows(int timeOut, int numberOfWindowsToBe) {
		// numberOfWindowsToBe : how many window we are expecting
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));

	}

	// ***********************Frame Utility with Wait ******************************

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frameis available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public void waitForFrameByLocator(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));

	}

	public void waitForFrameByIndex(String FrameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameIndex));

	}

	public void waitForFrameByFrameNameOrId(String FrameNameOrId, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameNameOrId));

	}

	public void waitForFrameByFrameElement(WebElement FrameWebElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameWebElement));

	}
	
	//*****************************Fluent Wait Utylity**********************
	
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollinTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("----Time out is done...element is not found...." + locator);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public WebElement waitForElementPresentWithFluentWait(By locator, int timeOut, int pollinTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("----Time out is done...element is not found...." + locator);

		return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator, locator));

	}

	public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTimeOut) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver) 
					.withTimeout(Duration.ofSeconds(10))
						.pollingEvery(Duration.ofSeconds(2)).ignoring(NoAlertPresentException.class)
							.withMessage("----Time out is done...Alert is not found....");

		return wait.until(ExpectedConditions.alertIsPresent());

	}
	//*****************WebDriverWait with FluentWait Features************
	
	public  void waitForElementAndEntrValue(By locator,int timeOut, int pollingTime,String value) 
	{

		 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 
		 wait.pollingEvery(Duration.ofSeconds(timeOut))
		       	.ignoring(NoSuchElementException.class)
		       		.withMessage("Time out is done.....Element is not Found..."+locator)
		       		   .until(ExpectedConditions.presenceOfElementLocated(locator))
		       		      .sendKeys(value); 
	}
	
	public  void waitForElementAndClick(By locator,int timeOut, int pollingTime) 
	{

		 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 
		 wait.pollingEvery(Duration.ofSeconds(timeOut))
		       	.ignoring(NoSuchElementException.class)
		       		.withMessage("Time out is done.....Element is not Found..."+locator)
		       		   .until(ExpectedConditions.presenceOfElementLocated(locator))
		       		      .click(); 
	}
	
	public  void waitForElementAndClick(By locator,int timeOut) 
	{

		 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 
		 wait.pollingEvery(Duration.ofSeconds(timeOut))
		       	.ignoring(NoSuchElementException.class)
		       		.withMessage("Time out is done.....Element is not Found..."+locator)
		       		   .until(ExpectedConditions.presenceOfElementLocated(locator))
		       		      .click(); 
	}
	
	//**************************************************Custome Wait Utility******************************************
	
	public  WebElement retryingElement(By locator, int timeOut)
	{
		 WebElement element=null;
		 
		 int attempts=0;//how many attempts you want to perform
		 
		 while(attempts<timeOut){
			try { 
					element= getElement(locator);//if element is not found then this line will throw exception so we can write in try...catch
					System.out.println("Element is found....."+locator+"In attempt : " +attempts);
				     break;
			}catch(NoSuchElementException e){
				//if element is not found at 0th sec then next attempt i want to perform after how many seconds so we have to implement some wait before perform another attempts
				 
				//let say after 500 ms. so we have to use Thread.sleep
				//then i have to increase the attempts counter
				System.out.println("Element is not found....."+locator+"In attempt : " +attempts);
			    try {
						  Thread.sleep(500);// this 500 milli sec is a intervaltime between attempts
				} catch (InterruptedException e1) {
					 
					e1.printStackTrace();
				}
			}
			attempts++;
			
			
		 }
		 
		 if(element == null)
		 {
			 System.out.println("Element is not found....."+locator+"In attempt : " +attempts);
		     
		 }
		 return element;
		 
		}
	
	public WebElement retryingElement(By locator, int timeOut, long pollingTime)
	{
		 WebElement element=null;
		 
		 int attempts=0;//how many attempts you want to perform
		 
		 while(attempts<timeOut){
			try { 
					element= getElement(locator);//if element is not found then this line will throw exception so we can write in try...catch
					System.out.println("Element is found....."+locator+"In attempt : " +attempts);
				     break;
			}catch(NoSuchElementException e){
				//if element is not found at 0th sec then next attempt i want to perform after how many seconds so we have to implement some wait before perform another attempts
				 
				//let say after 500 ms. so we have to use Thread.sleep
				//then i have to increase the attempts counter
				System.out.println("Element is not found....."+locator+"In attempt : " +attempts);
			    try {
			    	//this concept also called converting static wait into dynamic wait without using selenium library
						  Thread.sleep(pollingTime);// this custom pollingtime or intervaltime between attempts
				} catch (InterruptedException e1) {
					 
					e1.printStackTrace();
				}
			}
			attempts++;
			
			
		 }
		 
		 if(element == null)
		 {
			 System.out.println("Element is not found....."+locator+"In attempt : " +attempts);
		     
		 }
		 return element;
		 
		}
	
		// **********************************Page Load time//Dynamic  Time*********************

		public boolean isPageLoadedOrNot(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'"))
					.toString();

			return Boolean.parseBoolean(flag);

		}

}

 
