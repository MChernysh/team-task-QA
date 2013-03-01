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


