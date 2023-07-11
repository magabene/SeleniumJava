package genericMethods;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@SuppressWarnings("deprecation")
public class ExtentReportNG {
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	
public ExtentReports extentReportGenerator(){
		
		String dateName   = new SimpleDateFormat ("yyyyMMddhhmmss").format(new Date());
		String reportPath = "C:\\Selenium_Reports\\SeleniumJavaBDD\\Extent Reports\\";

		//Instantiate ExtentHtmlReporter - Look and feel
		htmlReporter = new ExtentHtmlReporter(reportPath + "extentreport" + dateName + ".html");

		htmlReporter.config().setDocumentTitle("SeleniumJavaBDD Batch Run Results");
		htmlReporter.config().setReportName("Regression Tests");
		htmlReporter.config().setTheme(Theme.STANDARD);

		//Attach reports
		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("HostName", "LocalHost");
		extent.setSystemInfo("OS", "Windows Server");
		extent.setSystemInfo("Browser", "Chrome");
		return extent;

	}
}
