package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseClass {
	static WebDriver driver;
	static Properties propertiesObj;
	
	public static WebDriver initilizeBrowser() throws IOException {
		if(getProperties().getProperty("executionEnvironment").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os
			if (getProperties().getProperty("os").equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else if (getProperties().getProperty("os").equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("No matching OS..");
		    }
			
			//browser
			switch (getProperties().getProperty("browser").toLowerCase()) {
		    	case "chrome":
		    		capabilities.setBrowserName("chrome");
		    		break;
		    	case "edge":
		    		capabilities.setBrowserName("MicrosoftEdge");
		    		break;
		    	default:
		    		System.out.println("No matching browser");
			}       
			driver = new RemoteWebDriver(new URL("http://localhost:4444/ui/"),capabilities);
		}
		else if(getProperties().getProperty("executionEnvironment").equalsIgnoreCase("local")) {
			switch(getProperties().getProperty("browser").toLowerCase()) {
				case "chrome":
					driver=new ChromeDriver();
					
					//if want to execute using headless
//					ChromeOptions options1 = new ChromeOptions();
//					options1.addArguments("--headless");
//					options1.addArguments("--disable-gpu");
//					options1.addArguments("--no-sandbox");
//					options1.addArguments("--remote-allow-origins=*");
//					driver = new ChromeDriver(options1);
					break;
				case "edge":
					driver=new EdgeDriver();
					
					//if want to execute using headless
//					EdgeOptions options2 = new EdgeOptions();
//					options2.addArguments("--headless");
//					options2.addArguments("--disable-gpu");
//					options2.addArguments("--no-sandbox");
//					options2.addArguments("--remote-allow-origins=*");
//					driver = new EdgeDriver(options2);
					break;
				default:
					System.out.println("No matching browser");
					driver=null;
			}
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static Properties getProperties() throws FileNotFoundException, IOException{		
		propertiesObj = new Properties();
		propertiesObj.load(new FileInputStream("C:\\Users\\2408342\\eclipse-workspace\\finalProject\\data.properties"));
		return propertiesObj;
	}
	public static String getKeyValue(String url) {
		return propertiesObj.getProperty(url);
	}
}
