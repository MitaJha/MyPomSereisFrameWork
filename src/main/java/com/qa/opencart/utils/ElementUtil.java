package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
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

//import customException.MyException1;

public class ElementUtil {
	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	@Step("getting element for locator: {0}")
	public WebElement getElement(By locator) {		
		WebElement element = driver.findElement(locator);	
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	@Step("Entering value: {1} in locator: {0}")
	public void doSendKeys(By locator, String value) {
		if (value == null) {
			System.out.println("values can not be null while using sendKeys method");
			//throw new MyException1("VALUECANNOTBENULL");
		}
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text=== " + eleText);
		return eleText;
	}

	public boolean checkElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getElementAttribute(By loactor, String atrName) {
		return getElement(loactor).getAttribute(atrName);
	}

	public boolean checkElementDisabled(By locator) {
		String disabledValue = getElement(locator).getAttribute("disabled");
		if (disabledValue.equals("true")) {
			return true;
		}
		return false;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);

		List<String> eleTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public void clickOnElement(By locator, String linkText) {

		List<WebElement> linksList = getElements(locator);
		System.out.println("total links in this list = " + linksList.size());
		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(linkText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchLocator, By searchSuggLocator, String searchKeys, String suggName)
			throws InterruptedException {
		;
		doSendKeys(searchLocator, searchKeys);
		Thread.sleep(4000);
		List<WebElement> suggList = getElements(searchSuggLocator);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}

		}

	}

//**************************Drop Down Utils*****************************//
	public void doSelectDropDownByIndex(By locator, int index) {
		if (index < 0) {
			System.out.println("please pass the right(+ve) index");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	// ******************************************************************
	public void doSelectDropDownByVisibleText(By locator, String visibletext) {
		if (visibletext == null) {
			System.out.println("please pass the right Visibletext and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibletext);
	}

	// ******************************************************************
	public void doSelectDropDownByValue(By locator, String attrValue) {
		if (attrValue == null) {
			System.out.println("please pass the right Visibletext and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByValue(attrValue);
	}

	public List<String> getDropDownTextList(By locator) {
		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions();
		System.out.println(optionsList);
		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();

	}

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

	public void doSelectDropDownValueUsingLocator(By locator, String dropDownValue) {
		List<WebElement> optionList = getElements(locator);

		for (WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}
//*******************************Action Utils***************************//	
	public void selectRightClickOption(By contextMenuLocator, String optionValue) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(contextMenuLocator)).perform();
		By optionLocator = By.xpath("//*[text()='"+optionValue+"']");  //* will ignore the tagname
		doClick(optionLocator);
		
	}
	
	public void twoLevelMenuHandling(By level1MenuLocator, By level2MenuLocator){
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1MenuLocator)).perform();
		doClick(level2MenuLocator);
	}
	public WebElement getLinkEleByText(String linkText) {
		return driver.findElement(By.linkText(linkText));
	}
	
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4) throws InterruptedException {
		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(1000);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getLinkEleByText(level3)).perform();
		Thread.sleep(1000);
		getLinkEleByText(level4).click();

	}
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException {
		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(1000);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1000);		
		getLinkEleByText(level3).click();

	}
	
	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();		
	}
	
	public void doActionsSendKeys(By locator, String value ) {
		act.sendKeys(getElement(locator), value).perform();		
	}
	//*************************************WAIT UTIL******************************************
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public WebElement waitForElementPresence(By locator, int timeOut) {		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	
	public WebElement waitForElementPresence(By locator, int timeOut,int pollingTime) {		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed 
	 * but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	@Step("waiting for element is visible for locator: {0} with timeout: {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	
	public String waitForTheTiltleContains(String titleFraction, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			} else {
				System.out.println(titleFraction + " title value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(titleFraction + " title value is not present...");
			return null;
		}
	}

	@Step("Waiting for the title and capture title")
	public String waitForTiltleIs(String titleValue, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle();
			} else {
				System.out.println(titleValue + " title value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(titleValue + " title value is not present...");
			return null;
		}
	}
	
	@Step("Waiting for Url of the page and capture it")
	public String waitForUrlContains(String urlFraction, int timeOut) {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	try {
		if (wait.until(ExpectedConditions.titleContains(urlFraction))) {
			return driver.getCurrentUrl();
		} else {
			System.out.println(urlFraction + " url value is not present...");
			return null;
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(urlFraction + " url value is not present...");
		return null;
	}
}

	public String waitForUrlToBe(String urlValue, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(urlValue))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlValue + " url value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlValue + " url value is not present...");
			return null;
		}
	}
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForJSConfirm(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForJSPrompt(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public boolean waitForNumberOfBrowserWindow(int timeOut, int numberOfWindow) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindow));
	}
	
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameByGrameNameOrID(String frameNameOrID, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	
	public void waitForFrameByFrameElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	//*****************************Fluent Wait********************************//
	
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver>  wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.withMessage("---timeOut is done....Element is not found..." +locator);
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver>  wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.withMessage("---timeOut is done....Element is not found..." +locator);
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver>  wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("---timeOut is done....alert is not found..." );
		
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForFrameWithFluentWait(String frameNameOrID, int timeOut, int pollingTime) {
		Wait<WebDriver>  wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchFrameException.class)
				.withMessage("---timeOut is done....Frame is not found..." + frameNameOrID);
		
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	

	public  void waitForElementAndEnterValue(By locator, int timeOut, int pollingTime, String value) {
		WebDriverWait  wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
		.pollingEvery(Duration.ofSeconds(pollingTime))
		  .ignoring(NoSuchElementException.class)
		    .withMessage("---timeOut is done....Element is not found..." +locator)
		      .until(ExpectedConditions.presenceOfElementLocated(locator))
		        .sendKeys(value);
	}
	
	public  void waitForElementAndClick(By locator, int timeOut, int pollingTime ) {
		WebDriverWait  wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
		.pollingEvery(Duration.ofSeconds(pollingTime))
		  .ignoring(NoSuchElementException.class)
		    .withMessage("---timeOut is done....Element is not found..." +locator)
		      .until(ExpectedConditions.presenceOfElementLocated(locator))
		        .click();
	}


}
