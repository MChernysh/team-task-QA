package Team.Task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPage {
	
	private WebDriver driver;
	
	ResultPage(WebDriver driver){
		
		this.driver = driver;
	}
	
	String getElementText(String xpath){
		
		return driver.findElement(By.xpath(xpath)).getText();
	}
	
	public void clickElement(String xpath){
		
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public void sendText(String xpath, String text) {
		
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}
	
	public void clearTextBox(String xpath){
		
		driver.findElement(By.xpath(xpath)).clear();
	}
}
