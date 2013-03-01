package Team.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPage {
	
	private WebDriver driver;
	
	ResultPage(WebDriver driver){
		
		this.driver = driver;
	}
	
	String getElementText(By by){
		
		return driver.findElement(by).getText();
	}
	
	public void clickElement(By by){
		
		driver.findElement(by).click();
	}
	
	public void sendText(By by, String text) {
		
		driver.findElement(by).sendKeys(text);
	}
	
	public void clearTextBox(By by){
		
		driver.findElement(by).clear();
	}
}
