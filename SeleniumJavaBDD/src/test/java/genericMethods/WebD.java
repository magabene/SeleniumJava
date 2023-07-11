package genericMethods;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebD {

	public static WebDriver driver;

	   public static WebDriver getInstance() {
		   if (driver == null)
	        {
			   System.setProperty("webdriver.chrome.driver", "C:\\\\Selenium\\\\chromedriver.exe");
		 	   driver=new ChromeDriver();
	        }
	        else
	        {
	            try
	            {
	                driver.getPageSource();
	            }
	            catch(Exception e)
	            {
	            	System.setProperty("webdriver.chrome.driver", "C:\\\\Selenium\\\\chromedriver.exe");
	 		 	   	driver=new ChromeDriver();
	            }
	        }
		   return driver;
	}
}
