package Team.Task;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	
	private WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	public void SignIN(String login, String password) {
			isElementPresent(By.xpath(".//*[@name='signin']"));
	        driver.findElement(By.xpath(".//*[@name='signin']")).click();
	        isElementPresent(By.xpath(".//input[@name='login']"));
	        driver.findElement(By.xpath(".//input[@name='login']")).sendKeys(login);
	        isElementPresent(By.xpath(".//input[@name='password']"));
	        driver.findElement(By.xpath(".//input[@name='password']")).sendKeys(password);
	        isElementPresent(By.cssSelector(".button-css-blue"));
	        driver.findElement(By.cssSelector(".button-css-blue")).click();
		}
	private void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		  if (act.isEmpty()){
			 System.out.println("Element was not found in class 'RozetkaHomePage' - "+by.toString()); 
			 driver.quit();
		  }
	
		
	}
	
	public void sendText(String xpath, String text) {
		
		isElementPresent(By.xpath(xpath));
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	public ResultPage clickElement(String xpath){
		
		isElementPresent(By.xpath(xpath));
		driver.findElement(By.xpath(xpath)).click();
		return new ResultPage(driver);
	}
	
	public void clearTextBox(String xpath){
		
		isElementPresent(By.xpath(xpath));
		driver.findElement(By.xpath(xpath)).clear();
	}
}
	
	
