package pageObjects;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import factory.BaseClass;
import utilities.ExcelUtilities;
import utilities.KeyBoardUtilities;
import utilities.MouseUtilities;

public class HomeLoanTab extends BasePage{
	
	public HomeLoanTab(WebDriver driver) {
		super(driver);
	}
	
	WebDriverWait wait;
	KeyBoardUtilities keyboard;
	MouseUtilities action;
	@FindBy (id = "menu-item-dropdown-2696") WebElement loanCalciTab;
	@FindBy (partialLinkText = "Home Loan EMI Calculator") WebElement homeLoanCalci;
	@FindBy (id = "homeprice") WebElement homeValue;
	@FindBy (id = "downpayment") WebElement downPayment;
	@FindBy (xpath = "//*[@class='btn btn-secondary']") WebElement percentageOrRupee;
	@FindBy (id = "homeloaninsuranceamount") WebElement loanInsurance;
	@FindBy (id = "homeloanamount") WebElement loanAmount;
	@FindBy (id = "homeloaninterest") WebElement interestRate;
	@FindBy (id = "homeloanterm") WebElement loanTenure;
	@FindBy (id = "loanfees") WebElement loanFee;
	@FindBy (id = "startmonthyear") WebElement loanStart;
	@FindBy (id = "onetimeexpenses") WebElement oneTimeExpenses;
	@FindBy (id = "propertytaxes") WebElement propertyTaxes;
	@FindBy (id = "homeinsurance") WebElement homeInsurance;
	@FindBy (id = "maintenanceexpenses") WebElement maintenanceExpenses;
	By installmentTable = By.id("paymentschedule");
	@FindBy (xpath = "(//*[@id='paymentschedule']/descendant::tr/th)[1]") WebElement tableElement; 
	By rows = By.xpath("//*[@class='row no-margin yearlypaymentdetails']");
	By cols = By.xpath("//*[@id='paymentschedule']/descendant::tr/th");
	
	public void clickLoanCalciTab() {
		loanCalciTab.click();
	}
	public void clickHomeLoanCalci() {
		homeLoanCalci.click();
	}
	public void inputHomeValue(String homePrice) {
		homeValue.clear();
		homeValue.sendKeys(homePrice);
	}
	public String getLoanAmountEntered() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript("return arguments[0].value;", homeValue);
	}
	public void inputDownPayment(String downPaymentMoney, String percentOrRupee) throws AWTException {
		downPayment.clear();
		downPayment.sendKeys(downPaymentMoney);
//		percentageOrRupee.click();
//		keyboard = new KeyBoardUtilities();
//		keyboard.clearText();
//		downPayment.clear();
//		downPayment.sendKeys(downPaymentMoney);
//		if(percentOrRupee.equalsIgnoreCase("rupee")) {
//			downPayment.click();
//			keyboard.switchOptions();
//		}
	}
	public void inputLoanInsurance(String loanInsuranceMoney) throws AWTException {
//		keyboard = new KeyBoardUtilities();
//		keyboard.clearText();
		loanInsurance.clear();
		loanInsurance.sendKeys(loanInsuranceMoney);
	}
	public void inputLoanAmount(String loanAmountMoney) throws AWTException {
//		keyboard = new KeyBoardUtilities();
//		keyboard.clearText();
		loanAmount.clear();
		loanAmount.sendKeys(loanAmountMoney);
	}
	public void inputInterestRate(String interestRatePercentage) throws AWTException {
		interestRate.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		interestRate.sendKeys(interestRatePercentage);
	}
	public void inputLoanTenure(String loanTenureTime) throws AWTException{
		loanTenure.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		loanTenure.sendKeys(loanTenureTime);
	}
	public void inputLoanFee(String loanFeeMoney) throws AWTException{
		loanFee.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		loanFee.sendKeys(loanFeeMoney);
	}
	public void inputLoanStart(String startMonthYear) {
//		loanStart.sendKeys(startMonthYear);
	}
	public void inputOneTimeExpenses(String expenses) throws AWTException{
		oneTimeExpenses.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		oneTimeExpenses.sendKeys(expenses);
	}
	public void inputPropertyTaxes(String taxes) throws AWTException{
		propertyTaxes.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		propertyTaxes.sendKeys(taxes);
	}
	public void inputHomeInsurance(String homeInsurance1) throws AWTException {
		homeInsurance.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		homeInsurance.sendKeys(homeInsurance1);
	}
	public void inputMaintenanceExpenses(String maintenanceExpenses1) throws AWTException {
		maintenanceExpenses.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		maintenanceExpenses.sendKeys(maintenanceExpenses1);
	}
	public boolean storeInstallmentsDataInExcel() throws InterruptedException, IOException {
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(installmentTable));
			
		MouseUtilities action = new MouseUtilities();
		action.clickElement(driver, tableElement);
		Thread.sleep(5000);
			
		List<WebElement> rowList = driver.findElements(rows);
		System.out.println("No Of Rows are : "+(rowList.size()+1));
			
		List<WebElement> colList = driver.findElements(cols);
		System.out.println("No Of Columns are : "+colList.size());
		ExcelUtilities.deleteSheetAndCreateNewSheet(BaseClass.getKeyValue("homeDataToBeStored"),BaseClass.getKeyValue("homeFileSheetName"));
		int row = 0;
		for(int col=0; col< colList.size(); col++) {
			ExcelUtilities.setStyledCell(BaseClass.getKeyValue("homeDataToBeStored"),BaseClass.getKeyValue("homeFileSheetName"), row, col, colList.get(col).getText());
		}
		Thread.sleep(5000);
			
		for(WebElement rowElement : rowList ) {
			row++;
			List<WebElement> colList1 = rowElement.findElements(By.tagName("td"));
			int col = 0;
			for(WebElement colElement : colList1 ) {
				ExcelUtilities.setCellData(BaseClass.getKeyValue("homeDataToBeStored"),BaseClass.getKeyValue("homeFileSheetName"), row, col++, colElement.getText());
			}	
		}
		System.out.println("******************* JOB DONE ****************");
		return true;
	}
}
