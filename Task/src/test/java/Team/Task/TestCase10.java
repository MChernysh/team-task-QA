package Team.Task;

//import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase10 {
	private FirefoxDriver driver;
	
	@BeforeTest
	public void StartUp()		{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
	}
	
	@Test(groups = { "Mykola" })
	public void testCase10()		{
		System.out.println("Start testing");
		TestCase10P2 logo = LogIn("mchernatqc@gmail.com", "IF-025ATQC");
		System.out.println("You're logined like user " +logo.accountName()+ "That user are registred" +logo.userExist());
		logo.makeSortCondition();
		logo.thirdPhoneReference();
		System.out.println("Phone on general page and phone on refernce page have the same prices and codes: " +logo.checkIfPhonesAreSame());
		System.out.println("Phone has " +logo.starsNumber()+ " star");
		logo.saveWishList();
		logo.checkWishList();
		logo.makeScreenshot();		
	}

	
	@AfterTest
	public void ShutDown()		{
		driver.quit();
	}
		
	
			//Login to the rozetka page
	public TestCase10P2 LogIn(String login, String password) {
	
		driver.findElementByXPath(".//*[@id='user_menu']/ul/li/a").click();
		driver.findElement(By.xpath(".//*[@name='login']")).clear();
		driver.findElement(By.xpath(".//*[@name='login']")).sendKeys(login);
		driver.findElementByXPath(".//*[@name='password']").sendKeys(password);
		driver.findElementByXPath(".//*[@class='button-css-blue']").click();
		
		return new TestCase10P2(driver);
		
		
	}

}
 