package Team.Task;


import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	
	private WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	public void SignIN(String login, String password) {
			isElementPresent(By.name("signin"));
	        driver.findElement(By.name("signin")).click();
	        isElementPresent(By.name("login"));
	        driver.findElement(By.name("login")).sendKeys(login);
	        isElementPresent(By.name("password"));
	        driver.findElement(By.name("password")).sendKeys(password);
	        isElementPresent(By.xpath("//*[text()='Войти']"));
	        driver.findElement(By.xpath("//*[text()='Войти']")).click();
		}
	public PersonalPage goToPersonalPage() {
		
		isElementPresent(By.name("profile"));
		driver.findElement(By.name("profile")).click();
		return new PersonalPage(driver);
	}
	
	private void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		  if (act.isEmpty()){
			 System.out.println("Element was not found in class 'RozetkaHomePage' - "+by.toString()); 
			 driver.quit();
		  }
	
		
	}
	
		
	}
	
	
