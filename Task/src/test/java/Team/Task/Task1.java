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

		HomePage1 homePage1 = new HomePage1(driver);
		homePage1.SignIN(login, password);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		PersonalPage personalPage = homePage1.goToPersonalPage();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		personalPage.goToWishList();
		//personalPage.signOut();

	}


	@BeforeTest
	public void beforeTest() {
		//System.setProperty("webdriver.firefox.bin",
			//s	"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
	  
		driver = new FirefoxDriver();
		driver.get("http://rozetka.com.ua");

	}

	@AfterTest
	public void afterTest() {
	}

}
