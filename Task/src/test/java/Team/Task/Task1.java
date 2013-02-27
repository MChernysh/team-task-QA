package Team.Task;

import java.util.concurrent.TimeUnit;
import java.lang.System;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.WebDriver;



public class Task1 {
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private WebDriver driver;
	@Test
	public void verifySignIn() {

		HomePage homePage = new HomePage(driver);
		homePage.SignIN(login, password);
		PersonalPage personalPage = homePage.goToPersonalPage();
		personalPage.goToWishList();
		//personalPage.signOut();

	}


	@BeforeTest
	public void beforeTest() {
		//System.setProperty("webdriver.firefox.bin",
			//s	"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");

	}

	@AfterTest
	public void afterTest() {
	}

}
