package test;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import factory.BaseClass;
import pageObjects.CarLoanTab;
import pageObjects.HomeLoanTab;
import utilities.ExcelUtilities;
import org.openqa.selenium.interactions.Actions;
public class CarLoanTest extends BaseTest {
	//WebDriver driver;
	CarLoanTab carLoanObject;
	HomeLoanTab homeLoanObject;
	BaseClass baseObject;
	String emiAmount;
	String principalAmount;
	String interestAmount;
	WebDriverWait wait;
	ExcelUtilities excelUtilitiesObject;
	
//	@BeforeTest
//	public void initializeDriver() throws IOException {
//		driver = BaseClass.initilizeBrowser();
//		driver.get(BaseClass.getKeyValue("url"));
//	}
	
	@Test(priority = 1, dataProvider = "carLoanData")
	public void carLoanDataPassing(String carLoanAmount, String interestRate, String loanTenure, String yearOrMonth) throws AWTException, InterruptedException {
		carLoanObject = new CarLoanTab(driver);	
		carLoanObject.clickCarLoanTab();
		
		carLoanObject.inputLoanAmount(carLoanAmount);
		carLoanObject.inputInterestRate(interestRate);
		carLoanObject.clickYearOrMonth(yearOrMonth);
		carLoanObject.inputLoanTenure(loanTenure);
		carLoanObject.clickYearOrMonth(yearOrMonth);
		
		Assert.assertEquals(carLoanAmount, carLoanObject.getLoanAmountEntered().replace(",",""));
		Assert.assertEquals(interestRate, carLoanObject.getInterestRateEntered().replaceAll(",", ""));
		Assert.assertEquals(loanTenure, carLoanObject.getLoanTenureEntered().replaceAll(",", ""));
	 
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		carLoanObject.clickFirstYearInstallment();
		//driver.findElement(By.xpath("//*[@class='col-2 col-lg-1 paymentyear toggle']")).click();
		principalAmount = carLoanObject.calculatePrincipal();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		interestAmount = carLoanObject.calculateInterest();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		emiAmount = carLoanObject.getEMIAmount();
		Assert.assertFalse(principalAmount.equals("N/A"));
		Assert.assertFalse(interestAmount.equals("N/A"));
		Assert.assertFalse(emiAmount.equals("N/A"));
		System.out.println("EMI Amount for First Month : "+emiAmount);
		System.out.println("Principal Amount : "+principalAmount);
		System.out.println("Interest Amount : "+interestAmount);
	}
//	@Test(priority = 3, dependsOnMethods = {"carLoanTest"})
//	public void storeRetrievedData() {
//		
//	}
	
	@DataProvider(name = "carLoanData")
	public Object[][] carLoanData() throws IOException{
		String[][] array = ExcelUtilities.returnData(BaseClass.getKeyValue("carLoanData"), BaseClass.getKeyValue("carFileSheetName"));
		return array;
	}
//	@AfterTest
//	public void closeBrowser() {
//		driver.quit();
//	}
}
