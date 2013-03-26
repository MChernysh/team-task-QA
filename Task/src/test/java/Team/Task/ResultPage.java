package Team.Task;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResultPage {
	
	private WebDriver driver;
	
	ResultPage(WebDriver driver) {
		
		this.driver = driver;
	}
	
	public String getElementText(By locator) {
		
		String elementText = driver.findElement(locator).getText(); 
		
		return elementText;
	}
	
	public void clickElement(By locator) {
		
		driver.findElement(locator).click();
	}
	
	public void sendText(By locator, String text) {
		
		driver.findElement(locator).sendKeys(text);
	}

//	public boolean isElementPresent(By by) {
//		List<WebElement> act = driver.findElements(by);
//	
//		if (!act.isEmpty()) 
//			return true; 
//		else 
//			return false;
//	}
	
	public void clearTextBox(By by) {
		
		driver.findElement(by).clear();
	}
	
	public void makeScreenshot(String filename) {
		
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try {
			FileUtils.copyFile(file, new File(filename));
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		}
		Reporter.log("Saving a screenshot ... " + filename);

	}
	
	public int getNumberOfElements(By by) {
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count = driver.findElements(by).size();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return count;
	}
}
