package Team.Task;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 

public class Task11 extends DDT{

	private String linkToRozetka = "http://rozetka.com.ua";
	private String linkToUltrabooks = "http://rozetka.com.ua/notebooks/c80004/filter/preset=light/";
	private WebDriver driver;
	
	@BeforeClass 
	public void settingUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(linkToRozetka);
	}


	@AfterClass
	public void turnDown() {
		driver.quit();
	}


	@DataProvider(name = "ExcelData")
	public Object[][] createData() throws Exception{
		Object[][] returnObjectArray = getTableArray(".\\src\\test\\data\\test.xls", "Data", "Test11");
		return(returnObjectArray);
	}


	@Test(dataProvider = "ExcelData") 
	public void ddtTask11(String Login, String Password) {
		HomePage homePage = new HomePage(driver);
		
		homePage.signIN(Login, Password);

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
				
		if (driver.findElements(By.name("signout")).size() != 0) {
			Reporter.log("Account with login  '" + Login + "'  and password  '" + Password +"'  exists!");			
			homePage.signOut();
		} else {
			String alertMessage = driver.findElement(By.xpath(".//*[starts-with(@id,'user-popup')]//form/div[1]/div")).getText();		
			Reporter.log("Login '" +  Login + "', password '" + Password + "':  " + alertMessage);					
			homePage.clickElement(By.xpath("//*[text()='Отмена']"));
		}
		
	}


	@Test
	public void task11() {
		
		ResultPage resultPage = new ResultPage(driver);
		HomePage homePage = new HomePage(driver);
		
		// Enter Login and Password
		homePage.signIN();
		
		//Select "Computers" from main menu
		homePage.clickElement(By.xpath(".//*[@id='computers-notebooks']"));		

		//Select "Ultrabooks"
		driver.get(linkToUltrabooks);


		//Select the first ultrabook and check if product's name and tooltip ("alt") are equal
		String ultrabookTooltip = homePage.getTheAttribute(By.xpath(".//*[@id='image_item255352']/a/img"), "alt");
		String ultrabookName = homePage.getTheAttribute(By.xpath(".//*[@id='image_item255352']/a/img"), "title");		
		compareStrings(ultrabookName, ultrabookTooltip);


		//show products as a 'List'
		homePage.clickElement(By.linkText("списком"));
		

		//Select the third notebook from top list
		String topThree = homePage.getTextFrom(By.xpath("//div[@class='item available popular3']//div[@class='title']"));
		Reporter.log("The third element in Top is: " + topThree);

		//Move mouse to the third element in top list
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[@class='item available popular3']"))).build().perform();		

		
		//Check the popup window and show its contents 
		String popupInformation = homePage.getTextFrom(By.xpath("html/body/div[6]//p"));        
		Reporter.log("Popup window contains such information: " + "\n" + popupInformation);
		
		//Check the hyperlink in popup window
		String hlinkInPopup = homePage.getTextFrom(By.xpath("html/body/div[6]//div/a")); 
		Reporter.log("Hyperlink '" + hlinkInPopup + "' in popup window exists!");
		
		//Copy the third top element's hyper referance from popup window
		String thirdElementReference = homePage.getTheAttribute(By.xpath("html/body/div[6]//div/a"), "href"); 
		
		
		//Make screenshot
		resultPage.makeScreenshot("./target/screenshots/Screenshot_task11.png");
		

		//Go to the third top element's page
		driver.get(thirdElementReference);

		//Compare product's names in previous and new page 
		String productName = homePage.getTextFrom(By.xpath(".//*[@id='head_banner_container']//h1"));   			
		compareStrings(productName,topThree);


		//Check the USD price
		String usd = homePage.getTextFrom(By.xpath(".//*[@id='head_banner_container']//div[@class='pp-usd']"));   		
		Reporter.log("The price is: " + usd);

		//Logging out
		homePage.signOut();
	}


	
	private void compareStrings(String firstString, String secondString) {
		try {
			Assert.assertEquals(firstString, secondString);
			Reporter.log("Strings \n" + firstString + "\n and \n" + secondString + "\n are equal!");
		} catch (AssertionError e) {
			Reporter.log("Strings \n" + firstString + "\n and \n" + secondString + "\n are not equal!");
		}
	}

}
