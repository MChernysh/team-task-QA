package Team.Task;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Task7{

	private FirefoxDriver driver = new FirefoxDriver();
	private String searchTerm = "samsung";
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	
	HomePage home = new HomePage(driver);
	
	public boolean isElementIn(By by) {
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean present = driver.findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		
		return present;
	}
	
	@BeforeClass
	public void setUp(){
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		
		// sign in and close the annoying social media pop up
		home.SignIN(login, password);
		home.clickElement(By.xpath(".//a[@name=\"close\"]"));		
		
		// go to wish list page, check whether there are some and delete them
		home.clickElement(By.xpath(".//a[@name=\"profile\"]"));
		home.clickElement(By.xpath("//div[@class=\"title\"]/a[contains(text(), \"Списки желаний\")]"));
		
		if(isElementIn(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")))
			home.deleteWishLists(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]"));	
	}
	
	@AfterClass
	public void tearDown(){
		
		driver.close();
	}
	
	@Test(groups = { "Taras" })
	public void Task7_Test(){
		
		// clear the text box and search for the searchTerm
		home.clearTextBox(By.xpath(".//div//input[@class=\"text\"]"));
		home.sendText(By.xpath(".//div//input[@class=\"text\"]"), searchTerm); 						
		
		// click "Submit" button and verify whether search results contain the searchTerm
		ResultPage result = home.clickElement(By.xpath(".//button[@type=\"submit\"]")); 				
		Assert.assertTrue(searchTerm.equals(result.getElementText(By.xpath(".//h1/span")))); 	 

		// get the element's color and verify it
		String color = driver.findElement(By.xpath(".//h1/span")).getCssValue("color");	
		Assert.assertTrue(color.equals("rgba(50, 154, 28, 1)"));						
			
		// get 3..5 search results and store them to the wish list
		for(int i = 3; i <= 5; i++) {														
																							 
			result.clickElement(By.xpath(".//table["+i+"]//a[@name=\"towishlist\"]")); 				
			result.clearTextBox(By.xpath(".//input[@name=\"wishlist_title\"]"));
			result.sendText(By.xpath(".//input[@name=\"wishlist_title\"]"), "Список желаний "+ (i-2));
			result.clickElement(By.xpath(".//div[@class=\"submit\"]/button[@type=\"submit\"]"));
	
		  if(i < 5) 
			result.clickElement(By.xpath(".//a[@name=\"close\"]"));
		}
		
		// visit the wish list page from the pop up
		result.clickElement(By.xpath(".//div[@class=\"comment\"]/a[@class=\"underline\"]"));			

		//make a screenshot and save it to the project's directory
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try {
			FileUtils.copyFile(file, new File("test-output/Task_7 - Screenshot.png"));
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
	}	
}

