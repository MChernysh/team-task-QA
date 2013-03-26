package Team.Task;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.WebDriver;



public class Task5 {
	
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private WebDriver driver;
	
	@Test(groups = { "Roma" })
	public void contNewCategories() {

		HomePage homePage = new HomePage(driver);
		homePage.SignIN(login, password);
		
		//writing in log number of category from main menu, marked as "New"
		Reporter.log("Rozetka.com.ua has " + String.valueOf(homePage.countNewCategoty()) + " new cetegories");
		homePage.signOut();
	}


	@BeforeTest
	public void setUP() {
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");

	}

	@AfterTest
	public void turnDown() {
		driver.quit();
	}

}
