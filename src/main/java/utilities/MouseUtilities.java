package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseUtilities {
	public void clickElement(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}
}
