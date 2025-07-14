package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import factory.BaseClass;

public class BaseTest {
	public static WebDriver driver;
	
	@BeforeSuite
	public void setUp() throws IOException {
		driver = BaseClass.initilizeBrowser();
		driver.get(BaseClass.getKeyValue("url"));
	}
	@AfterSuite
	public void tearDown() {
		if(driver != null) {
			driver.quit();	
		}
	}
}
