package test;

import java.util.Properties;

import org.testng.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import factory.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;  // UI of the report
	public ExtentReports extent;  //populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods

	public void onStart(ITestContext context) {
			
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/myReport.html");//specify location of the report
		
		sparkReporter.config().setDocumentTitle("Automation Testing Report"); // TiTle of report
		sparkReporter.config().setReportName("Automation Testing Project - Find the Interest Amount for current year"); // name of the report
		sparkReporter.config().setTheme(Theme.STANDARD);
		//Find the Interest Amount for current year
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Computer Name","localhost");
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("Testing Team Name","TEAM DELTA CODERS");
		extent.setSystemInfo("Operating System","Windows11");
		String browserName;
		try {
			Properties propertiesObject = BaseClass.getProperties();
			browserName = BaseClass.getKeyValue("browser");
			if( browserName.equalsIgnoreCase("edge") ) {
				browserName = "Microsoft Edge";
			}
			else if( browserName.equalsIgnoreCase("chrome") ) {
				browserName = "Google Chrome";
			}
			extent.setSystemInfo("Browser Name",browserName);
		}
		catch(Exception e) {
			
		}
	}

	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getName()); // create a new enty in the report
		test.log(Status.PASS, "Test case PASSED is:" + result.getName()); // update status p/f/s
		
	}

	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test case FAILED is:" + result.getName());
		test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable()); 
					
	}

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
		
	}

	
	public void onFinish(ITestContext context) {
		
		extent.flush();//everything gets updated in the report
	}
}

