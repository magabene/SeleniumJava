package genericMethods;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactoryStaticThreadLocal {

private static ThreadLocal<WebDriver> driverFinal = new ThreadLocal<WebDriver>();
	
	@SuppressWarnings("deprecation")
	public static  void setDriver() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\\\Selenium\\chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.setCapability("unhandledPromptBehavior", "accept");
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("proxy-server=direct://");
		//options.addArguments("headless");
		//options.addArguments("window-size=1920,1080");
		
		ChromeDriver driver = new ChromeDriver(options);
	 	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	 	driverFinal.set(driver);
	}
	
	public static WebDriver getDriver()
	{
		return driverFinal.get();
	}
	
	public static void closeBrowser()
	{
		driverFinal.get().close();
		driverFinal.get().quit();
		driverFinal.remove();
	}

}
