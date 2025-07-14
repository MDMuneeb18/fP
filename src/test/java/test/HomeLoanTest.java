package test;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import factory.BaseClass;
import pageObjects.HomeLoanTab;
import utilities.ExcelUtilities;

public class HomeLoanTest extends BaseTest{
	//WebDriver driver;
	HomeLoanTab homeLoanObject;
	BaseClass baseObject;
	WebDriverWait wait;
	
//	@BeforeClass
//	public void initializeDriver() throws IOException {
//		driver = BaseClass.initilizeBrowser();
//		driver.get(BaseClass.getKeyValue("url"));
//	}
	
	@Test(priority = 1, dataProvider="homeLoanData", dependsOnMethods= {"test.CarLoanTest.carLoanTest"})
	public void homeLoanTest(String homeValue, String downPayment, String percentOrRupee, String loanInsurance, String loanAmount, String interestRate, String loanTenure, String loanFee, String loanStart, String oneTimeExpenses, String propertyTaxes, String homeInsurance, String maintenanceExpenses) throws AWTException  {
		homeLoanObject = new HomeLoanTab(driver);
		
		homeLoanObject.clickLoanCalciTab();
		homeLoanObject.clickHomeLoanCalci();
		homeLoanObject.inputHomeValue(homeValue);
		homeLoanObject.inputDownPayment(downPayment, percentOrRupee);
		homeLoanObject.inputLoanInsurance(loanInsurance);
		homeLoanObject.inputLoanAmount(loanAmount);
		homeLoanObject.inputInterestRate(interestRate);
		homeLoanObject.inputLoanTenure(loanTenure);
		homeLoanObject.inputLoanFee(loanFee);
		homeLoanObject.inputLoanStart(loanStart);
		homeLoanObject.inputOneTimeExpenses(oneTimeExpenses);
		homeLoanObject.inputPropertyTaxes(propertyTaxes);
		homeLoanObject.inputHomeInsurance(homeInsurance);
		homeLoanObject.inputMaintenanceExpenses(maintenanceExpenses);
		
		Assert.assertEquals(homeValue, homeLoanObject.getLoanAmountEntered().replace(",",""));
	}
	
	@Test(priority = 2, dependsOnMethods= {"homeLoanTest"})
	public void homeLoanData1() throws IOException, InterruptedException{
		
	    boolean isJobDone = homeLoanObject.storeInstallmentsDataInExcel();
	    Assert.assertTrue(isJobDone);
	}
	
	@DataProvider(name = "homeLoanData")
	public Object[][] homeLoanData() throws IOException{
		String[][] array = ExcelUtilities.returnData(BaseClass.getKeyValue("homeLoanData"), BaseClass.getKeyValue("homeFileSheetName"));
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array[0].length;j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
		return array;
	}
}
