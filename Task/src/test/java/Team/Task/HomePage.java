package Team.Task;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

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
	public int countNewCategoty(){
		int count = driver.findElements(By.xpath(".//*[@id='main_menu']//span[@class='new']")).size();
		return count;


	}
	public int countNewCategoty1(){
		int count1 = driver.findElements(By.xpath("//div[@class='title']/a")).size();
		return count1;
	}

	public void signOut(){
		isElementPresent(By.name("signout"));
		driver.findElement(By.name("signout")).click();
	}


	private void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()){
			Reporter.log("Element was not found in class 'RozetkaHomePage' - "+by.toString()); 
			driver.quit();
		}

	}

	public void sendText(By by, String text) {
		
		isElementPresent(by);
		driver.findElement(by).sendKeys(text);
	}

	public ResultPage clickElement(By by){
		
		isElementPresent(by);
		driver.findElement(by).click();
		return new ResultPage(driver);
	}
	
	public void clearTextBox(By by){
		
		isElementPresent(by);
		driver.findElement(by).clear();
	}
	
	public void deleteWishLists(By by){
			
		int count = driver.findElements(by).size();
		
		for(int j = 0; j < count; j++) 
			driver.findElement(by).click();
	}
}



