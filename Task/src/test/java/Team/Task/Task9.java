package Team.Task;

import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.WebDriver;



public class Task9 extends DDT{
	
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private WebDriver driver;
	private HomePage homePage;
	
	@DataProvider(name = "data")
	
    public Object[][] createData1() throws Exception{
       Object[][] retObjArr=getTableArray(".\\src\\test\\data\\test.xls", "Data", "Test9");
        return(retObjArr);
    }
	
	@Test(dataProvider = "data", groups = { "Roma" })
	public void VerifySorting(String title, String item) {

		
		
		//move to some main menu item
		Sort sort = homePage.gotoMenuItem(title,item);
		
		//validate sorting by some parameters
		Assert.assertTrue(sort.sortBy("от дешевых к дорогим")) ;
		Assert.assertTrue(sort.sortBy("от дорогих к дешевым")) ;
		Assert.assertTrue(sort.sortBy("последние добавленные")) ;
		Assert.assertTrue(sort.sortBy("популярные")) ;
		Assert.assertTrue(sort.sortBy("акции")) ;
		Assert.assertTrue(sort.sortBy("новинки")) ;
		
	}


	@BeforeTest
	public void setUP() {
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		homePage = new HomePage(driver);
		homePage.SignIN(login, password);

	}

	@AfterTest
	public void turnDown() {
		homePage.signOut();
		driver.quit();
	}

}
