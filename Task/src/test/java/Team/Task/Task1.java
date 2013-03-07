package Team.Task;

import java.util.concurrent.TimeUnit;
import java.io.IOException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.WebDriver;



public class Task1 {
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private WebDriver driver;
	@Test(groups = { "Roma" })
	public void verifySignIn() throws IOException {

		HomePage homePage = new HomePage(driver);
		homePage.SignIN(login, password);
		homePage.goToPersonalPage();
		homePage.goToWishList();
		if(homePage.checkWishList()){
			homePage.serch("HTC");
			homePage.addtoWishList(1, "WishListFor_1_Test");
			homePage.goToPersonalPage();
			homePage.goToWishList();
			if(homePage.checkWishList())Reporter.log("Some problem with Wich Lists");
		}
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
