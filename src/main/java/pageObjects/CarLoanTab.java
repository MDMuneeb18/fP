package pageObjects;

import java.awt.AWTException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import factory.BaseClass;
import utilities.KeyBoardUtilities;

public class CarLoanTab extends BasePage {
	String emiAmount;
	WebDriverWait wait;
	KeyBoardUtilities keyboard;
	public CarLoanTab(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@FindBy (linkText = "Car Loan") WebElement carLoanTab;
	By carLoanTab1 = By.linkText("Car Loan");
	@FindBy (id = "loanamount") WebElement carLoanAmount;
	By carLoanAmount1 = By.id("loanamount");
	@FindBy (id = "loaninterest") WebElement carLoanInterestRate;
	By carLoanInterestRate1 = By.id("loaninterest");
	@FindBy (id = "loanterm") WebElement carLoanTenure;
	By carLoanTenure1 = By.id("loanterm");
	@FindBy (xpath = "(//*[@class='btn-group btn-group-toggle']/descendant::label)[1]") WebElement carLoanTenureTypeYear;
	@FindBy (xpath = "(//*[@class='btn-group btn-group-toggle']/descendant::label)[2]") WebElement carLoanTenureTypeMonth;
	@FindBy (xpath = "//*[@class='col-2 col-lg-1 paymentyear toggle']") WebElement firstYearInstallment;
	@FindBy (xpath = "(//*[@class='monthlypaymentcontainer']/descendant::tr/descendant::td)[2]") WebElement principalAmount;
	@FindBy (xpath = "(//*[@class='monthlypaymentcontainer']/descendant::tr/descendant::td)[3]") WebElement interestAmount;
	@FindBy (xpath = "(//*[@class='monthlypaymentcontainer']/descendant::tr/descendant::td)[4]") WebElement totalEMIAmount;
	
	public void clickCarLoanTab() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(carLoanTab1));
		carLoanTab.click();
	}
	public void inputLoanAmount(String loanAmount) {
		carLoanAmount.clear();
		carLoanAmount.sendKeys(loanAmount);
	}
	public String getLoanAmountEntered() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript("return arguments[0].value;", carLoanAmount);
	}
	public void inputInterestRate(String interestRate) throws AWTException {
		carLoanInterestRate.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		carLoanInterestRate.sendKeys(interestRate);
	}
	public String getInterestRateEntered() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript("return arguments[0].value;", carLoanInterestRate);
	}
	public void clickYearOrMonth(String yearOrMonth) {
		if(yearOrMonth.equalsIgnoreCase("year")) {
			carLoanTenureTypeYear.click();
			return;
		}
		if(yearOrMonth.equalsIgnoreCase("month")) {
			//carLoanTenureTypeMonth.click();
			Actions actions = new Actions(driver);
			actions.doubleClick(carLoanTenureTypeMonth).build().perform();
			//return;
		}
	}
	public void inputLoanTenure(String loanTenure) throws AWTException {
		carLoanTenure.click();
		keyboard = new KeyBoardUtilities();
		keyboard.clearText();
		carLoanTenure.sendKeys(loanTenure);
	}
	public String getLoanTenureEntered() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript("return arguments[0].value;", carLoanTenure);
	}
	public void clickFirstYearInstallment() {
		wait.until(ExpectedConditions.elementToBeClickable(firstYearInstallment));
		//Actions actions = new Actions(driver);
		//actions.click(driver.findElement(firstYearInstallment));
		firstYearInstallment.click();
	}
	public String calculatePrincipal() {
		wait.until(ExpectedConditions.visibilityOf(firstYearInstallment));
		return principalAmount.getText();
	}
	public String calculateInterest() {
		wait.until(ExpectedConditions.visibilityOf(interestAmount));
		return interestAmount.getText();
		
	}
	public String getEMIAmount() {
		wait.until(ExpectedConditions.visibilityOf(totalEMIAmount));
		emiAmount =  totalEMIAmount.getText();
		//emiAmount = emiAmount.replace(",", "");
		return emiAmount;
	}
}