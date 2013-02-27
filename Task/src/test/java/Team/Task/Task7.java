package Team.Task;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
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
	
	HomePage home = new HomePage(driver);

	
	@BeforeClass
	public void setUp(){
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		
		//sign in and close the annoying social media pop up
		home.SignIN("testatqc@gmail.com", "IF-025.ATQC");
		home.clickElement(".//a[@name=\"close\"]");		
	}
	
	@AfterClass
	public void tearDown(){
		
		driver.close();
		
	}
	
	@Test
	public void Task7_Test(){
		
		// clear the text box and search for the searchTerm
		home.clearTextBox(".//div//input[@class=\"text\"]");
		home.sendText(".//div//input[@class=\"text\"]", searchTerm); 						
		
		// click "Submit" button and verify whether search results contain the searchTerm
		ResultPage result = home.clickElement(".//button[@type=\"submit\"]"); 				
		AssertJUnit.assertTrue(searchTerm.equals(result.getElementText(".//h1/span"))); 	 

		// get the element's color and verify it
		String color = driver.findElement(By.xpath(".//h1/span")).getCssValue("color");	
		AssertJUnit.assertTrue(color.equals("rgba(50, 154, 28, 1)"));						
			
		// get 3..5 search results and store them to the wish list
		for(int i = 3; i <= 5; i++) {														
																							 
			result.clickElement(".//table["+i+"]//a[@name=\"towishlist\"]"); 				
			result.clearTextBox(".//input[@name=\"wishlist_title\"]");
			result.sendText(".//input[@name=\"wishlist_title\"]", "Список желаний "+ (i-2));
			result.clickElement(".//div[@class=\"submit\"]/button[@type=\"submit\"]");
	
		  if(i < 5) 
			result.clickElement(".//a[@name=\"close\"]");
		}
		
		// visit the wish list page from the pop up
		result.clickElement(".//div[@class=\"comment\"]/a[@class=\"underline\"]");			

		//make a screenshot and save it to the project's directory
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try {
			FileUtils.copyFile(file, new File("Task_7 - Screenshot.png"));
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
	}	
}

