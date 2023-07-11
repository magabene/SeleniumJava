package genericMethods;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

@SuppressWarnings("deprecation")
public class WebUI {
	//public static WebDriver driver = WebD.getInstance();
		static final String messageNoSuchElementException 			= "Unable to locate element by name: ";
		static final String messageNoSuchElementExceptionSelector	= "Unable to locate element by ";
		static final String messageElementNotInteractableException 	= "Element is Hidden and can't be found: ";
		static final String messageInvalidSelectorException 		= "Incorrect Locator used to locate element: ";
		//static final String messageElementNotInteractableException 	= "WebDriver found that this element was not selectable: ";
		static final String messageWebDriverException  				= "Exceptional case";
		static final String messageNoAlertPresentException  		= "No alert Present";
		static final String messageAssertionError  					= "Unable to verify";
		static final String messageNoSuchWindowException  			= "Unable to change windows - window not present";
		static final String messageNoSuchFrameException 			= "Unable to change frames - frame not present";
		static final String messageTimeoutException 				= "Timeout when waiting for element: ";
		static final String messagElementNotInteractableException  = "The following element is not interactable: ";
		static final String msgElementNotInteractableException 		= "The following element is not interactable: ";
		static final String messageTimeoutWindows					= "Timeout when waiting for only one window to be open.";
		static final String MenuNotAvailable						= "Menu was not present on the page and could not interact with it.";
		static final String TempMenuNotAvailable					= "Template menu was not present on the page and could not interact with it.";
		static final int 	explicitWait							= 30;
		String curPage;
		String elementVal;
		
		/**
		 * Captures screenshots onTestFailure()
		 *@param driver - The Webdriver object
		 *@param screenshotname - The screenshot name as a String 
		 *@author Justice Mohuba
		 */
		public String getScreenshot (String screenShotName) throws IOException {
			String reportPath = "C:\\Selenium_Reports\\SeleniumJavaBDD\\Screenshots\\";
			String dateName = new SimpleDateFormat ("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot)WebDriverFactoryStaticThreadLocal.getDriver();
			File source = ts.getScreenshotAs(OutputType.FILE);
			
			//Create destination folder for Screenshots in src/target folder
			String destination = reportPath + screenShotName + dateName + ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
			return destination;
		}
		
		/**
		  * Changes web driver to specified web driver. The default Driver is Chrome
		  * @param driver - The web driver which must be set as the current wed drive
		  * @author Justice Mohuba
		  */
		 public void setDriver(WebDriver driver) {
		    //WebUI.driver = driver;
		 }  
		 
		 public void updateCurPage(String page) {
			 	curPage = page;
			 } 
		 /**
		  * Opens browser to a specified URL
		  * @param url - The URL which will be navigated to upon opening the browser
		  * @author Justice Mohuba
		  */
		 public void openBrowser(String url) {
			//if there is an active driver but the browser is closed open new driver instance
			 /*try
	         {
	             driver.getPageSource();
	         }
	         catch(Exception e)
	         {
	        	 driver = WebD.getInstance();
	         }*/

			 WebDriverFactoryStaticThreadLocal.getDriver().get(url);
		 }
		 /**
		  * Changes the URL of an open web driver
		  * @param url - The URL which will be navigated to
		  * @author Justice Mohuba
		  */
		public void navigateToUrl(String url) {
			WebDriverFactoryStaticThreadLocal.getDriver().navigate().to(url);
		}
		/**
		  * Maximizes the in-focus window of an open web driver
		  * @author Justice Mohuba
		  */
		public void maximizeWindow() {
			WebDriverFactoryStaticThreadLocal.getDriver().manage().window().maximize();
			
		}
		/**
		  * Set the text of a given web element
		  * @param element - Web element on which text must be set
		  * @param text - Text which will be added to the element
		  * @author Justice Mohuba
		  */
		public void setText(WebElement element, String text) {
			try {
				element.clear();
				
				element.sendKeys(text);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
		}
		/**
		  * Set the text of a web element by finding the element by a given name
		  * @param fieldName - the name attribute of the element whose text must be set
		  * @param text - Text which will be added to the element
		  * @author Justice Mohuba
		  */
		
		public void setText(String fieldName, String text) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				element.clear();
				
				element.sendKeys(text);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		 /**
		  * Add text to a web element by finding the element by a given name
		  * This method will not clear the element's text before adding the input text
		  * @param fieldName - the name attribute of the element whose text must be set
		  * @param text - Text which will be added to the element Can be action keys like Tab or Enter
		  * @author Justice Mohuba
		  */

		public void sendKeys(String fieldName, String text) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				element.sendKeys(text);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Clear text on a specified web element
		  * @param element - the element which must be cleared
		  * @author Justice Mohuba
		  */
		public void clearText(WebElement element) {
			try {	
				element.clear();
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Clear text on a web element by finding the element by a given name
		  * @param fieldName - the name attribute of the element which must be cleared
		  * @author Justice Mohuba
		  */
		public void clearText(String fieldName) {
			try {
				waitForElement(fieldName, explicitWait);
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				element.clear();
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Click on a given web element
		  * @param element - Web element which must be clicked on
		  * @author Justice Mohuba
		  */
		public void click(WebElement element) {	
			try {
				element.click();
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
		}
		/**
		  * Click on a web element by finding the element by a given name
		  * @param fieldName - the name attribute of the element which must be clicked on
		  * @author Justice Mohuba
		  * Edited by @author Justice Mohuba - Caught ElementNotInteractableException
		  */
		
		public void click(String fieldName) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				element.click();
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			catch (ElementNotInteractableException x) {
				throw new ElementNotInteractableException (msgElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Check given web element which must be a check-box. This will only set the 
		  * check-box to checked and will not uncheck it if it is already checked
		  * @param element - Web element which must be checked(must be check-box)
		  * @author Justice Mohuba
		  */
		public void check(WebElement element) {	
			try {
				boolean isChecked = element.isSelected();
				if(!isChecked) {
					element.click();
				}
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
		}
		/**
		  * Check a web element by finding the element by a given name(must be check-box). 
		  * This will only set the check-box to checked and will not uncheck 
		  * it if it is already checked
		  * @param fieldName - the name attribute of the element which must be checked(must be check-box)
		  * @author Justice Mohuba
		  */
		public void check(String fieldName) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				boolean isChecked = element.isSelected();
				if(!isChecked) {
					element.click();
				}
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
		}
		/**
		  * Uncheck given web element which must be a check-box. This will only set the 
		  * check-box to unchecked and will not check it if it is not checked
		  * @param element - Web element which must be unchecked(must be check-box)
		  * @author Justice Mohuba
		  */
		public void uncheck(WebElement element) {	
			try {
				boolean isChecked = element.isSelected();
				if(isChecked) {
					element.click();
				}
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messagElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Uncheck a web element by finding the element by a given name(must be check-box). 
		  * This will only set the check-box to unchecked and will not check 
		  * it if it is not checked
		  * @param fieldName - the name attribute of the element which must be unchecked(must be check-box)
		  * @author Justice Mohuba
		  */
		public void uncheck(String fieldName) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				boolean isChecked = element.isSelected();
				if(isChecked) {
					element.click();
				}
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messagElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Delays script for a specified number of seconds
		  * @param delay - the number of seconds that must be waited for
		  * @author Justice Mohuba
		  */
		public void delay(int delay) {
			/*int delayInMillis = delay*1000;
//			driver.manage().timeouts().implicitlyWait(delay, TimeUnit.SECONDS);
			try
			{
				Thread.sleep(delayInMillis);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}*/
		}
		/**
		  * Deselect the option of a specified web element based on a provided label. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be deselected
		  * @param label - Label which is deselected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void deselectOptionByLabel(WebElement element, String label) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.deselectByVisibleText(label);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Deselect all options of a web element by finding the element by a given name 
		  * The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which all options must be deselected
		  * @author Justice Mohuba
		  */
		public void deselectAll(String fieldName) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.deselectAll();
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Deselect all options of a specified web element. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be deselected
		  * @author Justice Mohuba
		  */
		public void deselectAll(WebElement element) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.deselectAll();;
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Deselect the option of a web element by finding the element by a given name 
		  * based on a provided label. The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which an option must be deselected
		  * @param label - Label which is deselected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void deselectOptionByLabel(String fieldName, String label) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.deselectByVisibleText(label);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Deselect the option of a specified web element base on a provided value. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be deselected
		  * @param value - Value which is deselected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void deselectOptionByValue(WebElement element, String value) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.deselectByValue(value);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Deselect the option of a web element by finding the element by a given name 
		  * based on a provided value. The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which an option must be deselected
		  * @param value - Value which is deselected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void deselectOptionByValue(String fieldName, String value) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.deselectByValue(value);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Deselect the option of a specified web element base on a provided index. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be deselected
		  * @param index - Index which is deselected, must be between 0 and the number of options available.
		  * @author Justice Mohuba
		  */
		public void deselectOptionByIndex(WebElement element, int index) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.deselectByIndex(index);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
		}
		/**
		  * Deselect the option of a web element by finding the element by a given name 
		  * based on a provided index. The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which an option must be deselected
		  * @param index - Index which is deselected, must be between 0 and the number of options available.
		  * @author Justice Mohuba
		  */
		public void deselectOptionByIndex(String fieldName, int index) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.deselectByIndex(index);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
		}
		/**
		  * Select the option of a specified web element based on a provided label. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be selected
		  * @param label - Label which is selected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void selectOptionByLabel(WebElement element, String label) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.selectByVisibleText(label);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Select the option of a web element by finding the element by a given name 
		  * based on a provided label. The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which an option must be selected
		  * @param label - Label which is selected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void selectOptionByLabel(String fieldName, String label) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.selectByVisibleText(label);
			}
			catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
		}
		
		public void selectOptionByLabelLogin(String fieldName, String label) {
			try {
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.selectByVisibleText(label);
			}
			catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
		}
		/**
		  * Select the option of a specified web element base on a provided value. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be selected
		  * @param value - Value which is selected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void selectOptionByValue(WebElement element, String value) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.selectByValue(value);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}
		}
		/**
		  * Select the option of a web element by finding the element by a given name 
		  * based on a provided value. The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which an option must be selected
		  * @param value - Value which is selected, must be in list of options.
		  * @author Justice Mohuba
		  */
		public void selectOptionByValue(String fieldName, String value) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.selectByValue(value);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}
		}
		/**
		  * Select the option of a specified web element base on a provided index. 
		  * The web element must be a Select.
		  * @param element - Web element for which an option must be selected
		  * @param index - Index which is selected, must be between 0 and the number of options available.
		  * @author Justice Mohuba
		  */
		public void selectOptionByIndex(WebElement element, int index) {
			try {
				Select dropdown= new Select(element);
				
				dropdown.selectByIndex(index);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + element.toString());
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
		}
		/**
		  * Select the option of a web element by finding the element by a given name 
		  * based on a provided index. The web element must be a Select.
		  * @param fieldName - the name attribute of the element for which an option must be selected
		  * @param index - Index which is selected, must be between 0 and the number of options available.
		  * @author Justice Mohuba
		  */
		public void selectOptionByIndex(String fieldName, int index) {
			try {
				waitForElement(fieldName, explicitWait);
				
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select dropdown= new Select(element);
				
				dropdown.selectByIndex(index);
			}catch(ElementNotInteractableException  e) {
				throw new ElementNotInteractableException(messageElementNotInteractableException + fieldName);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
		}
		/**
		  * Gets the text of the first selected option in a specified drop-down menu
		  * @param fieldName 	The name attribute of the element to find selected option of
		  * @return 		 	The text of the first selected option.
		  * @author Justice Mohuba
		  */
		public String getFirstSelectedOption(String fieldName) {
			waitForElement(fieldName, explicitWait);
			
			WebElement firstOption;
			
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select select = new Select(element);
				
				firstOption = select.getFirstSelectedOption();		
				
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return firstOption.getText();
		}
		/**
		  * Gets the text of the first selected option in a specified drop-down menu
		  * @param element	 	The element to find selected option of
		  * @return 		 	The text of the first selected option.
		  * @author Justice Mohuba
		  */
		public String getFirstSelectedOption(WebElement element) {
			WebElement firstOption;
			
			try {
				Select select = new Select(element);
				
				firstOption = select.getFirstSelectedOption();		
				
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return firstOption.getText();
		}
		/**
		  * Accepts an alert which is present on screen, will throw an error if there is no alert present
		  * @throws UnhandledAlertException
		  * @author Justice Mohuba
		  */
		public void acceptAlert() throws UnhandledAlertException {
			try {
				waitForAlert(explicitWait);
				Alert alert = WebDriverFactoryStaticThreadLocal.getDriver().switchTo().alert();
				alert.accept();
			}catch(NoAlertPresentException e) {
				throw new NoAlertPresentException(messageNoAlertPresentException);
			}
		}
		/**
		  * Dismisses an alert which is present on screen, will throw an error if there is no alert present
		  * @throws UnhandledAlertException
		  * @author Justice Mohuba
		  */
		public void dismissAlert() throws UnhandledAlertException {
			try {
				waitForAlert(explicitWait);
				Alert alert = WebDriverFactoryStaticThreadLocal.getDriver().switchTo().alert();
				alert.dismiss();
			}catch(NoAlertPresentException e) {
				throw new NoAlertPresentException(messageNoAlertPresentException);
			}
		}
		/**
		  * Returns the text present on an alert
		  * @throws UnhandledAlertException
		  * @return Alert Text
		  * @author Justice Mohuba
		  */
		public String getAlertText() throws UnhandledAlertException {
			try {
				waitForAlert(explicitWait);
				Alert alert = WebDriverFactoryStaticThreadLocal.getDriver().switchTo().alert();
				return alert.getText();
			}catch(NoAlertPresentException e) {
				throw new NoAlertPresentException(messageNoAlertPresentException);
			}
		}
		/**
		  * Determines if an alert is present on screen
		  * @return true if alert is present
		  * @author Justice Mohuba
		  */
		public boolean isAlertPresent() {
	        try {
	            WebDriverFactoryStaticThreadLocal.getDriver().switchTo().alert();
	            return true;
	        }
	        catch (NoAlertPresentException Ex) {
	            return false;
	        }
	    }
		/**
		  * Closes the current open web browser.
		  * @author Justice Mohuba
		  * edited by @author Justice Mohuba
		  * Changed method to close Browser instead quitting
		  */
		public void closeBrowser() {
			
			WebDriverFactoryStaticThreadLocal.getDriver().quit();
		}
		/**
		 * Quits web driver session and kills the chrome driver
		 * @author Justice Mohuba
		 */
		public void closeWebDriver() {
			WebDriverFactoryStaticThreadLocal.getDriver().quit();
		}
		
		/**
		 * Closes the current active window 
		 * @author Justice Mohuba
		 */
		public void closeWindow() {
			WebDriverFactoryStaticThreadLocal.getDriver().close();
		}
		
		
		/**
		  * Get the visible text of a specified web element base.
		  * @param element - Web element to get text from
		  * @author Justice Mohuba
		  */
		public String getText(WebElement element) {
			String elementText = "";
			try {
				elementText = element.getText();
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			
			return elementText;
		}
		/**
		  * Get the visible text of a web element by finding the element by a given name 
		  * @param fieldName - the name attribute of the element to get text from
		  * @author Justice Mohuba
		  */
		public String getText(String fieldName) {
			waitForElement(fieldName, explicitWait);
			
			WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
			
			String elementText = "";
			try {
				elementText = element.getText();
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			
			return elementText;
		} 
		/**
		  * Get the visible text of a specified web element base. Attribute
		  * @param element - Web element to get text from
		  * @author Justice Mohuba
		  */
		public String getAttributeValue(WebElement element, String attribute) {
			String attributeValue = "";
			try {
				attributeValue = element.getAttribute(attribute);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messagElementNotInteractableException + element.toString());
			}
			
			return attributeValue;
		}
		/**
		  * Get the visible text of a web element by finding the element by a given name 
		  * @param fieldName - the name attribute of the element to get text from
		  * @author Justice Mohuba
		  */
		public String getAttributeValue(String fieldName, String attribute) {
			waitForElement(fieldName, explicitWait);
			
			WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
			
			String attributeValue = "";
			try {
				attributeValue = element.getAttribute(attribute);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}catch(ElementNotInteractableException e) {
				throw new ElementNotInteractableException(messagElementNotInteractableException + fieldName);
			}
			
			return attributeValue;
		} 
		/**
		  * Switch to a specified window
		  * @param index - Index of window to switch to where 0 is main window
		  * @author Justice Mohuba
		  */
		public void switchToWindowIndex(int index) {
			try {
				int count = 0;
				for (String handle : WebDriverFactoryStaticThreadLocal.getDriver().getWindowHandles()){			
					if(count == index) {					
						WebDriverFactoryStaticThreadLocal.getDriver().switchTo().window(handle);
						break;
					}
					count ++;	
				}
			}catch(NoSuchWindowException e) {
				throw new NoSuchWindowException(messageNoSuchWindowException);
			}
			
		}
		/**
		  * Switch to main window
		  * @author Justice Mohuba
		  */
		public void switchToDefaultContent() {
			try {
				//switch to first handle it set of handles
				for (String handle : WebDriverFactoryStaticThreadLocal.getDriver().getWindowHandles()){
					WebDriverFactoryStaticThreadLocal.getDriver().switchTo().window(handle);
					break;
				}
				checkOpenWindows();
			}catch(NoSuchWindowException e) {
				throw new NoSuchWindowException(messageNoSuchWindowException);
			}
		}
		/**
		  * Switch to a frame specified by id
		  * @param frameDescription - The Id of the frame to be switched to
		  * @author Justice Mohuba
		  */
		public void switchToFrame(String frameDescription) {
			try {
				WebDriverFactoryStaticThreadLocal.getDriver().switchTo().frame(frameDescription);
			}catch(NoSuchFrameException e) {
				throw new NoSuchWindowException(messageNoSuchFrameException);
			}
		}
		/**
		  * Switch to a frame specified by index
		  * @param index - The index of the frame to be switched to
		  * @author Justice Mohuba
		  */
		public void switchToFrame(int index) {
			try {
				WebDriverFactoryStaticThreadLocal.getDriver().switchTo().frame(index);
			}catch(NoSuchFrameException e) {
				throw new NoSuchWindowException(messageNoSuchFrameException);
			}
			
		}
		/**
		  * Switch to a frame specified by Web Element
		  * @param element - The frame to be switched to represented by a web element
		  * @author Justice Mohuba
		  */
		public void switchToFrame(WebElement element) {
			try {
				WebDriverFactoryStaticThreadLocal.getDriver().switchTo().frame(element);
			}catch(NoSuchFrameException e) {
				throw new NoSuchWindowException(messageNoSuchFrameException);
			}
			
		}
		/**
		  * Scroll until a specified web element is in the middle of the screen.
		  * @param element - Web element to scroll to, should not be visible already
		  * @param delay - number of seconds to wait until element is visible
		  * @author Justice Mohuba
		  */
		public void scrollToElement(WebElement element, int delay) {
			((JavascriptExecutor) WebDriverFactoryStaticThreadLocal.getDriver()).executeScript("arguments[0].scrollIntoView({block: \"center\"});", element);

			delay(delay);
		}
		/**
		  * Scroll until a web element found by a given name is in the middle of the screen
		  * @param fieldName - the name attribute of the element to scroll to, should not be visible already
		  * @param delay - number of seconds to wait until element is visible
		  * @author Justice Mohuba
		  */
		public void scrollToElement(String fieldName, int delay) {
			waitForElement(fieldName, explicitWait);
			
			WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));	

//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);	

			
			String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
	                + "var elementTop = arguments[0].getBoundingClientRect().top;"
	                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

			((JavascriptExecutor) WebDriverFactoryStaticThreadLocal.getDriver()).executeScript(scrollElementIntoMiddle, element);

			delay(delay);
		}
		/**
		  * Scroll to a specified position.
		  * @param x - x coordinate to scroll to
		  * @param y - y coordinate to scroll to
		  * @author Justice Mohuba
		  */
		public void scrollToPosition(int x, int y) {
			JavascriptExecutor js = (JavascriptExecutor) WebDriverFactoryStaticThreadLocal.getDriver();
			
	        js.executeScript("javascript:window.scrollBy("+x+","+y+")");
		}
		
		/**
		 * overloaded method for verifyTextPresent, i has extent logging functionality
		 * @param text				Text which must be verified
		 * @param isRegex			True if the text to verify is a regular expression
		 * @param checkPointType	The scenario to be tested
		 * @param test			    The ExtentBase object
		 * @return 					True if text is present
		 * @author Justice Mohuba
		 */
		public boolean verifyTextPresent(String text, boolean isRegex,String checkPointType, ExtentTest test) {
			
			boolean textPresent = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			textPresent =  verifyTextPresent(text, isRegex);
			
			test.log(Status.PASS, "Expected text: " + text + ", is present on page.");
			
			return textPresent;
		}
		
		/**
		  * Verifies if specified text is present on page.
		  * @param text			Text which must be verified
		  * @param isRegex		True if the text to verify is a regular expression
		  * @return 			True if text is present false otherwise
		  * @author Justice Mohuba
		  */
		public boolean verifyTextPresent(String text, boolean isRegex) {
			boolean textPresent = false;
			/*WebDriverWait wait = new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(),  Duration.ofSeconds(explicitWait));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = '" + text + "']")));*/
			if(isRegex) {
				if(WebDriverFactoryStaticThreadLocal.getDriver().getPageSource().matches(text)) {
					textPresent= true;
					System.out.println("text Present");
				}
			}else {
				if(WebDriverFactoryStaticThreadLocal.getDriver().getPageSource().contains(text)) {
					textPresent= true;
					System.out.println("text Present");
				}else {
					System.out.println("text Not Present");
				}
			}
			try {
				Assert.assertEquals(true, textPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError + " text: " + text);
			}
			return textPresent;
			
		}
		/**
		 * overloaded method for verifyTextNotPresent, it has extent logging functionality
		 * @param text				Text which must be verified
		 * @param isRegex			True if the text to verify is a regular expression
		 * @param checkPointType	The scenario to be tested
		 * @param test			    The ExtentBase object
		 * @return 					True if text is not present
		 * @author Justice Mohuba
		 */
		public boolean verifyTextNotPresent(String text, boolean isRegex,String checkPointType, ExtentTest test) {
			
			boolean textNotPresent = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			textNotPresent =  verifyTextNotPresent(text, isRegex);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected text: " + text + ", is not present on page.");
			
			return textNotPresent;
		}
		/**
		  * Verifies if specified text is not present on page.
		  * @param text 	Text which must be verified
		  * @param isRegex 	True if the text to verify is a regular expression
		  * @return 		True if text is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyTextNotPresent(String text, boolean isRegex) {
			boolean textPresent = false;
			if(isRegex) {
				if(WebDriverFactoryStaticThreadLocal.getDriver().getPageSource().matches(text)) {
					textPresent= true;
					System.out.println("text Present");
				}
			}else {
				if(WebDriverFactoryStaticThreadLocal.getDriver().getPageSource().contains(text)) {
					textPresent= true;
					System.out.println("text Present");
				}else {
					System.out.println("text Not Present");
				}
			}
			try {
				Assert.assertEquals(false, textPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError  + " text: " + text);
			}
			return !textPresent;
			
		}

		/**
		 * Verifies if a web element found by a given name has a specified attribute.
		 * @param fieldName 		The name attribute of the element to verify
		 * @param attribute 		The attribute to be verified
		 * @param checkPoint 		The expected value of the attribute
		 * @param checkPointType 	The scenario being tested
		 * @param test				The ExtendBase Object
		 * @return 					True if the elements specified attribute matches the supplied text.
		 * @author Justice Mohuba
		 * 
		 */
		public boolean verifyElementAttributeValue(WebElement element, String attribute, String checkPointValue,String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementAttributeValue(element, attribute, checkPointValue);
			
			test.log(Status.PASS, "Expected value: " + checkPointValue + "," + " is present on element: " +   element.toString() + "'s " + attribute); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element has a specified attribute.
		  * @param element 		Web element to verify
		  * @param attribute 	The attribute to be verified
		  * @param checkPoint 	The expected value of the attribute
		  * @return				True if the elements specified attribute matches the supplied text.
		  * @author Justice Mohuba
		  */
		public boolean verifyElementAttributeValue(WebElement element, String attribute, String checkPoint) {
			String attributeValue = "";
			try {
				attributeValue = element.getAttribute(attribute);

				Assert.assertEquals(attributeValue, checkPoint);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return attributeValue.equals(checkPoint);
			
		}
		
		/**
		 * Verifies if a web element found by a given name has a specified attribute.
		 * @param fieldName 		The name attribute of the element to verify
		 * @param attribute 		The attribute to be verified
		 * @param checkPoint 		The expected value of the attribute
		 * @param checkPointType	The scenario being tested
		 * @param test				The ExtendBase Object
		 * @return					True if the elements specified attribute matches the supplied text.
		 * @author Justice Mohuba
		 * 
		 */
		public boolean verifyElementAttributeValue(String fieldName, String attribute, String checkPointValue,String checkPointType, ExtentTest test) {
			
			waitForElement(fieldName, explicitWait);
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementAttributeValue(fieldName, attribute, checkPointValue);
			
			test.log(Status.PASS, "Expected value: " + checkPointValue + "," + " is present on element: " +   fieldName + "'s " + attribute); 
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name has a specified attribute.
		  * @param fieldName 	The name attribute of the element to verify
		  * @param attribute 	The attribute to be verified
		  * @param checkPoint	The expected value of the attribute
		  * @return				True if the elements specified attribute matches the supplied text.
		  * @author Justice Mohuba
		  */
		public boolean verifyElementAttributeValue(String fieldName, String attribute, String checkPoint) {
			//waitForElement(fieldName, explicitWait);
			
			String attributeValue = "";
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				attributeValue = element.getAttribute(attribute);

				Assert.assertEquals(attributeValue, checkPoint);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return attributeValue.equals(checkPoint);
			
		}
		
		/**
		  * Verifies if specified element has a specified text.
		  * @param element			Web element to verify
		  * @param checkPoint 		The expected text of the attribute
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if Text is present on element
		  * @author Justice Mohuba
		  */
		public boolean verifyElementText(WebElement element, String checkPointValue,String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementText(element, checkPointValue);
			
			test.log(Status.PASS, "Expected value: " + checkPointValue + "," + " is present on element: " +   element.toString() + "'s text"); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element has a specified text.
		  * @param element		Web element to verify
		  * @param checkPoint 	The expected text of the attribute
		  * @return				True if Text is present on element
		  * @author Justice Mohuba
		  */
		public boolean verifyElementText(WebElement element,String checkPoint) {
			String elementText = "";
			try {
				elementText = element.getText();

				Assert.assertEquals(elementText, checkPoint);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return elementText.equals(checkPoint);
		}
		
		/**
		  * Verifies if a web element found by a given name has a specified text.
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPoint 		The expected value of the attribute
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if text is present on element
		  * @author Justice Mohuba
		  */
		public boolean verifyElementText(String fieldName, String checkPointValue,String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementText(fieldName, checkPointValue);
			
			test.log(Status.PASS, "Expected value: " + checkPointValue + "," + " is present on element: " +   fieldName + "'s text"); 
			
			return result;
		}
		/**
		  * Verifies if a web element found by a given name has a specified text.
		  * @param fieldName 	The name attribute of the element to verify
		  * @param checkPoint 	The expected value of the attribute
		  * @return				True if text is present on element
		  * @author Justice Mohuba
		  */
		public boolean verifyElementText(String fieldName,String checkPoint) {
			waitForElement(fieldName, explicitWait);
		
			String elementText = "";
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				elementText = element.getText();
			
				Assert.assertEquals(elementText, checkPoint);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return elementText.equals(checkPoint);
		}
		
		/**
		  * Verifies if specified element is present on page.
		  * @param element			Web element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementPresent(WebElement element, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementPresent(element);
			
			test.log(Status.PASS, "Expected value: Element: " +   element.toString() + " is present"); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element is present on page.
		  * @param element	Web element to verify
		  * @return			True if element is present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementPresent(WebElement element) {
			boolean elementPresent = false;
			try {
				elementPresent = element.isDisplayed();
		
				Assert.assertTrue(elementPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return elementPresent;
		}
		
		/**
		  * Verifies if a web element found by a given name is present on the page.
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementPresent(String fieldName, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementPresent(fieldName);
			
			test.log(Status.PASS, "Expected value: Element: " + fieldName + " is present"); 
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name is present on the page.
		  * @param fieldName 	The name attribute of the element to verify
		  * @return				True if element is present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementPresent(String fieldName) {
			boolean elementPresent = false;
			
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				elementPresent = element.isDisplayed();
				
				Assert.assertTrue(elementPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return elementPresent;
		}
		
		/**
		  * Verifies if specified element is not present on page.
		  * @param element			Web element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotPresent(WebElement element, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyElementNotPresent(element);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: Element: " + element.toString() + " is not present"); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element is not present on page.
		  * @param element	Web element to verify
		  * @return			True if element is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotPresent(WebElement element) {
			boolean elementPresent = false;
			try {
				elementPresent = element.isDisplayed();
			}catch(NoSuchElementException e) {
				//element not present -do nothing
			}
			try {
				Assert.assertFalse(elementPresent);
			}catch(AssertionError ex) {
				throw new AssertionError(messageAssertionError);
			}
			return !elementPresent;
		}
		
		/**
		  * Verifies if a web element found by a given name is not present on the page.
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotPresent(String fieldName, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyElementNotPresent(fieldName);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: Element: " + fieldName + " is not present"); 
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name is not present on the page.
		  * @param fieldName 	The name attribute of the element to verify
		  * @return				True if element is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotPresent(String fieldName) {
			boolean elementPresent = false;
			
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				elementPresent = element.isDisplayed();
			}catch(NoSuchElementException e) {
				//element not present -do nothing
			}
			try {
				Assert.assertFalse(elementPresent);
			}catch(AssertionError ex) {
				throw new AssertionError(messageAssertionError);
			}
			return !elementPresent;
		}
		
		/**
		  * Verifies if specified element is enabled
		  * @param element			Web element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementClickable(WebElement element, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementClickable(element);
			
			test.log(Status.PASS, "Expected value: Element: " + element.toString() + " is clickable");  
			
			return result;
		}
		
		/**
		  * Verifies if specified element is enabled
		  * @param element	Web element to verify
		  * @return			True if element is enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementClickable(WebElement element) {
			boolean elementClickable = false;
			try {
				elementClickable = element.isEnabled();
		
				Assert.assertTrue(elementClickable);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return elementClickable;
		}
		
		/**
		  * Verifies if a web element found by a given name is enabled.
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementClickable(String fieldName, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementClickable(fieldName);
			
			test.log(Status.PASS, "Expected value: Element: " + fieldName + " is clickable");  
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name is enabled.
		  * @param fieldName 	The name attribute of the element to verify
		  * @return				True if element is enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementClickable(String fieldName) {
			boolean elementClickable = false;
			
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				elementClickable = element.isEnabled();
				
				Assert.assertTrue(elementClickable);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return elementClickable;
		}
		
		/**
		  * Verifies if specified element is not enabled.
		  * @param element			Web element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is not enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotClickable(WebElement element, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			result =  verifyElementNotClickable(element);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: Element: " + element.toString() + " is not clickable"); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element is not enabled.
		  * @param element	Web element to verify
		  * @return			True if element is not enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotClickable(WebElement element) {
			boolean elementClickable = false;
			try {
				elementClickable = element.isEnabled();
				
				Assert.assertFalse(elementClickable);
			}catch(AssertionError ex) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return !elementClickable;
		}
		
		/**
		  * Verifies if a web element found by a given name is not enabled.
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is not enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotClickable(String fieldName, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyElementNotClickable(fieldName);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: Element: " + fieldName + " is not clickable"); 
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name is not enabled.
		  * @param fieldName 	The name attribute of the element to verify
		  * @return				True if element is not enabled
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotClickable(String fieldName) {
			boolean elementClickable = false;
			
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				elementClickable = element.isEnabled();

				Assert.assertFalse(elementClickable);
			}catch(AssertionError ex) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return !elementClickable;
		}
		
		/**
		  * Verifies if specified element is checked(must be a checkbox).
		  * @param element			Web element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementChecked(WebElement element, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementChecked(element);
			
			test.log(Status.PASS, "Expected value: Element: " + element.toString() + " is checked"); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element is checked(must be a checkbox).
		  * @param element	Web element to verify
		  * @return			True if element is checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementChecked(WebElement element) {
			boolean isChecked =false;
			try {
				isChecked = element.isSelected();

				Assert.assertEquals(true, isChecked);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return isChecked;
		}
		
		/**
		  * Verifies if a web element found by a given name is checked(must be a checkbox).
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementChecked(String fieldName, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyElementChecked(fieldName);
			
			test.log(Status.PASS, "Expected value: Element: " + fieldName + " is checked"); 
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name is checked(must be a checkbox).
		  * @param fieldName 	The name attribute of the element to verify
		  * @return				True if element is checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementChecked(String fieldName) {
			waitForElement(fieldName, explicitWait);
			
			boolean isChecked =false;
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				isChecked = element.isSelected();

				Assert.assertEquals(true, isChecked);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return isChecked;
		}
		
		/**
		  * Verifies if specified element is not checked(must be a checkbox).
		  * @param element			Web element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is not checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotChecked(WebElement element, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyElementNotChecked(element);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: Element: " + element.toString() + " is not checked"); 
			
			return result;
		}
		
		/**
		  * Verifies if specified element is not checked(must be a checkbox).
		  * @param element	Web element to verify
		  * @return			True if element is not checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotChecked(WebElement element) {
			boolean isChecked =false;
			try {
				isChecked = element.isSelected();

				Assert.assertEquals(false, isChecked);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return !isChecked;
		}
		
		/**
		  * Verifies if a web element found by a given name is not checked(must be a checkbox).
		  * @param fieldName 		The name attribute of the element to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if element is not checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotChecked(String fieldName, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyElementNotChecked(fieldName);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: Element: " + fieldName + " is not checked"); 
			
			return result;
		}
		
		/**
		  * Verifies if a web element found by a given name is not checked(must be a checkbox).
		  * @param fieldName 	The name attribute of the element to verify
		  * @return				True if element is not checked
		  * @author Justice Mohuba
		  */
		public boolean verifyElementNotChecked(String fieldName) {
			waitForElement(fieldName, explicitWait);
			
			boolean isChecked =false;
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				isChecked = element.isSelected();

				Assert.assertEquals(false, isChecked);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return !isChecked;
		}
		
		/**
		  * Verifies if specified drop-down menu contains the specified option
		  * @param element			Web element to verify
		  * @param option			The option to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if option is present
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionPresent(WebElement element, String option, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyOptionPresent(element, option);
			
			test.log(Status.PASS, "Expected value: " + option + "," + " is an option on element: " + element.toString()); 
			
			return result;
		}
		
		/**
		  * Verifies if specified drop-down menu contains the specified option
		  * @param element	Web element to verify
		  * @param option	The option to verify
		  * @return			True if option is present
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionPresent(WebElement element, String option) {
			boolean isPresent = false;
			try {
				Select select = new Select(element);
				List<WebElement> allOptions = select.getOptions();
				for(WebElement optionValue: allOptions) {
				    if(optionValue.getText().equals(option)) {
				    	isPresent = true;
				        break;
				    }
				}
				Assert.assertTrue(isPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return isPresent;
		}
		
		/**
		  * Verifies if specified drop-down menu contains the specified option
		  * @param fieldName 		The name attribute of the element to verify
		  * @param option			The option to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return 		 		True if option is present false otherwise
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionPresent(String fieldName, String option, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyOptionPresent(fieldName, option);
			
			test.log(Status.PASS, "Expected value: " + option + "," + " is an option on element: " + fieldName);  
			
			return result;
		}
		
		/**
		  * Verifies if specified drop-down menu contains the specified option
		  * @param fieldName 	The name attribute of the element to verify
		  * @param option		The option to verify
		  * @return 		 	True if option is present false otherwise
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionPresent(String fieldName, String option) {
			waitForElement(fieldName, explicitWait);
			
			boolean isPresent = false;
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select select = new Select(element);
				
				List<WebElement> allOptions = select.getOptions();
				
				for(WebElement optionValue: allOptions) {
				    if(optionValue.getText().equals(option)) {
				    	isPresent = true;
				        break;
				    }
				}
				Assert.assertTrue(isPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return isPresent;
		}
		
		/**
		  * Verifies if specified drop-down menu does not contain the specified option
		  * @param element			Web element to verify
		  * @param option			The option to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if option is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionNotPresent(WebElement element, String option, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyOptionNotPresent(element, option);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: " + option + "," + " is not an option on element: " + element.toString()); 
			
			return result;
		}
		
		/**
		  * Verifies if specified drop-down menu does not contain the specified option
		  * @param element	Web element to verify
		  * @param option	The option to verify
		  * @return			True if option is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionNotPresent(WebElement element, String option) {
			boolean isPresent = false;
			try {
				Select select = new Select(element);
				List<WebElement> allOptions = select.getOptions();
				for(WebElement optionValue: allOptions) {
				    if(optionValue.getText().equals(option)) {
				    	isPresent = true;
				        break;
				    }
				}
				Assert.assertFalse(isPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + element.toString());
			}
			return !isPresent;
		}
		
		/**
		  * Verifies if specified drop-down menu does not contain the specified option
		  * @param fieldName		The name attribute of the element to verify
		  * @param option			The option to verify
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if option is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionNotPresent(String fieldName,String option,String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			result =  verifyOptionNotPresent(fieldName, option);
			
			WebDriverFactoryStaticThreadLocal.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			test.log(Status.PASS, "Expected value: " + option + "," + " is not an option on element: " + fieldName); 
			
			return result;
		}
		
		/**
		  * Verifies if specified drop-down menu does not contain the specified option
		  * @param fieldName	The name attribute of the element to verify
		  * @param option		The option to verify
		  * @return				True if option is not present
		  * @author Justice Mohuba
		  */
		public boolean verifyOptionNotPresent(String fieldName, String option) {
			waitForElement(fieldName, explicitWait);
			
			
			
			boolean isPresent = false;
			try {
				WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(fieldName));
				
				Select select = new Select(element);
				
				List<WebElement> allOptions = select.getOptions();
				
				for(WebElement optionValue: allOptions) {
				    if(optionValue.getText().equals(option)) {
				    	isPresent = true;
				        break;
				    }
				}
				Assert.assertFalse(isPresent);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementException + fieldName);
			}
			return !isPresent;
		}
		
		/**
		  * Verifies that two strings are equal).
		  * @param str1 			First string to compare
		  * @param str2 			Second string to compare
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if the strings are equal
		  * @author Justice Mohuba
		  */
		public boolean verifyEqual(String str1, String str2, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyEqual(str1, str2);
			
			test.log(Status.PASS, "Expected value: " + str1 + " is equal to " + str2); 
			
			return result;
		}
		
		/**
		  * Verifies that two strings are equal).
		  * @param str1 	First string to compare
		  * @param str2 	Second string to compare
		  * @return			True if the strings are equal
		  * @author Justice Mohuba
		  */
		public boolean verifyEqual(String str1, String str2) {
			try {
				Assert.assertEquals(str1, str2);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}
			return str1 == str2;
		}
		/**
		  * Verifies that two strings are not equal).
		  * @param str1 			First string to compare
		  * @param str2 			Second string to compare
		  * @param checkPointType	The scenario being tested
		  * @param test				The ExtendBase Object
		  * @return					True if the strings are equal
		  * @author Justice Mohuba
		  */
		public boolean verifyNotEqual(String str1, String str2, String checkPointType, ExtentTest test) {
			
			boolean result = false;
			
			if(checkPointType.equals(""))
			{
				checkPointType = "Validations";
			}
			
			test.log(Status.INFO, "Scenario: " + checkPointType); 
			
			result =  verifyNotEqual(str1, str2);
			
			test.log(Status.PASS, "Expected value: " + str1 + " is not equal to " + str2); 
			
			return result;
		}
		
		/**
		  * Verifies that two strings are not equal).
		  * @param str1 	First string to compare
		  * @param str2 	Second string to compare
		  * @return			True if the strings are equal
		  * @author Justice Mohuba
		  */
		public boolean verifyNotEqual(String str1, String str2) {
			try {
				Assert.assertNotEquals(str1, str2);
			}catch(AssertionError e) {
				throw new AssertionError(messageAssertionError);
			}
			return str1 == str2;
		}
		/**
		  * Navigate the menu items by clicking on the Elements in the list
		  * @param list - A comma delimited list of names for menu items in the order they should be clicked
		  * @author Justice Mohuba
		  */
		public void navigateMenu(String menu) {
			
			try {
				new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
				.ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("divMenuShow")));
			} catch (TimeoutException e) {
				throw new TimeoutException (MenuNotAvailable);
			} 
			
			JavascriptExecutor js = (JavascriptExecutor) WebDriverFactoryStaticThreadLocal.getDriver();
			curPage = (String) js.executeScript("menuAttribute = TREES[0].find_item_by_key('id', '" + menu + "')[0].a_config[1];" + 
			"start = TREES[0].find_item_by_key('id', '" + menu + "')[0].a_config[1].indexOf('?') + 5;" + 
			"next = start;" + 
			"curChar = menuAttribute.charAt(start);" + 
			"while (curChar != '\\'') {" + 
			"	next += 1;" + 
			"	curChar = menuAttribute.charAt(next);" + 
			"}" + 
			"curPage = menuAttribute.substring(start,next);" +
			"return curPage");
			
			//System.out.println(curPage);
			
			((JavascriptExecutor) WebDriverFactoryStaticThreadLocal.getDriver()).executeScript("eval(TREES[0].find_item_by_key('id', '" + menu + "')[0].a_config[1]);");
			
		}
		
		public void checkOpenWindows() {
			try {
				for (int i = 0; i < 5; i++) {
					int windows = WebDriverFactoryStaticThreadLocal.getDriver().getWindowHandles().size();
					if(windows > 1) {
						try {
							Thread.sleep(1000);
							System.out.println("waited" + i);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        } else {
			            break;
			        }
				}
			} catch (Exception e) {
				throw new TimeoutException (messageTimeoutWindows);
			}
		}
		
		public void navigateTemplateMenu(String menu) {
			
			try {
				new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
				.ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("AppArea")));
			} catch (TimeoutException e) {
				throw new TimeoutException (TempMenuNotAvailable);
			} 
			
			((JavascriptExecutor) WebDriverFactoryStaticThreadLocal.getDriver()).executeScript("eval(TREES[1].find_item_by_key('id', '" + menu + "')[0].a_config[1]);");
		}
		/**
		  * Wait a number of seconds specified or until the element found by given name is present
		  * @param fieldName - name of Element which must be waited for
		  * @param delay - Number of seconds to wait before timeout
		  * @author Justice Mohuba
		  */
		public void waitForElement(String fieldName, int delay) {
			
			/*WebElement element = WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name("run"));
			elementVal = element.getAttribute("value");
			
			System.out.println("elementVal " + elementVal);
			System.out.println("curPage " + curPage);
			
			if(curPage.equals(elementVal)) {*/
				try {
					WebDriverWait wait = new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(),  Duration.ofSeconds(delay));
					wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated (By.name(fieldName)));
				} catch (TimeoutException e) {
					throw new TimeoutException (messageTimeoutException + fieldName);
				} 
			/*} else {
				throw new AssertionError("Page " + curPage + " expected but navigated to page " + elementVal);
			}
			
			System.out.println("curPage " + curPage);*/
		}
		/**
		  * Wait a number of seconds specified or until an alert is present on screen
		  * @param delay - Number of seconds to wait before timeout
		  * @author Justice Mohuba
		  */
		public void waitForAlert(int delay) {
			try {
				WebDriverWait wait = new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(),  Duration.ofSeconds(delay));
				wait.until(ExpectedConditions.alertIsPresent());
				System.out.print("present");
			} catch (TimeoutException e) {
				throw new TimeoutException (messageTimeoutException + "Alert");
			}
		}
		
		/**
		  *Returns a web element found using a provided Selector of the type specified.
		  * @param selectorType - The selector to be used to find e.g. id
		  * @param selectorValue - Value of selector which will be used to find the element
		  * @return element - The web Element which was found using provided selectors
		  * @author Justice Mohuba, Justice Mohuba
		  */
		public WebElement getWebElement(String selectorType, String selectorValue) {
			WebElement element = null;
			try {
				if(selectorType.equalsIgnoreCase("id")) {
					
					new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.id(selectorValue)));
					element =  WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.id(selectorValue));
					
				}else if(selectorType.equalsIgnoreCase("xpath")) { 
					
					new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorValue)));
					element =  WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.xpath(selectorValue));
					
				}else if(selectorType.equalsIgnoreCase("linkText")) { 
					
					new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(selectorValue)));
					element =  WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.linkText(selectorValue));
					
				}else if(selectorType.equalsIgnoreCase("className")) { 
					
					new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.className(selectorValue)));
					element =  WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.className(selectorValue));
					
				}else if(selectorType.equalsIgnoreCase("name")) { 
					new WebDriverWait(WebDriverFactoryStaticThreadLocal.getDriver(), Duration.ofSeconds(explicitWait))
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.name(selectorValue)));
					element =  WebDriverFactoryStaticThreadLocal.getDriver().findElement(By.name(selectorValue));
					
				}else {
					System.out.println(selectorType +" is not a valid selector please enter a correct selector");
				}
				
				return element;
			
			} catch (TimeoutException e) {
				throw new TimeoutException (messageTimeoutException + selectorValue);
			}
			catch(NoSuchElementException e) {
				throw new NoSuchElementException(messageNoSuchElementExceptionSelector + selectorType + ": " + selectorValue);
			}
		}
		/**
		  * Opens the options menu and selects the required option
		  * @param option - The xpath for the option which must me selected
		  * @author Justice Mohuba
		  */
		/*public void navigateOptions(String option) {
			click(getWebElement("id", Buttons.options));

			click(getWebElement("xpath", option));
		}*/
		
		/**
		 * do a lookup for the lookup text and switch contexts back and forth
		 * 
		 * @param LookupTextBox 	The code to search for 
		 * @param searchText 		The name of the search text box
		 * @param lookupButton 		The name of the lookup button
		 * @author Justice Mohuba
		 */
		public void lookup(String LookupTextBox, String searchText, String lookupButton) {
			
			if(!searchText.equals("")&&!searchText.equals(null))
			{
				setText(LookupTextBox, searchText);
		
				click(lookupButton);
		
				switchToWindowIndex(1);
		
				//select the first lookup record
				click(getWebElement("xpath", "//*[@id=\"lkpResultsTable\"]/tbody/tr[2]/td[2]"));
		
				switchToDefaultContent();
			}	
		}
		
		/**
		 * Click a button using the buttons's name 
		 * not the button's web element
		 * 
		 * @param button 	The button's name text 
		 * @author Justice Mohuba
		 */
		public void clickButton(String button) 
		{

			WebElement btnButton = getWebElement("linktext", button);
			scrollToElement(btnButton, 0);

			click(btnButton);
		}


}
