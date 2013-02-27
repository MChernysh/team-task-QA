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
	
	String searchTerm = "samsung";
	
	HomePage home = new HomePage(driver);

	
	@BeforeClass
		public void setUp(){
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		
		home.SignIN("testatqc@gmail.com", "IF-025.ATQC");
		home.clickElement(".//a[@name=\"close\"]");											// close the annoying social media pop up
	}
	
	@AfterClass
		public void tearDown(){
		
		driver.close();
		
	}
	
	@Test
	public void Task7_Test(){
		
		home.clearTextBox(".//div//input[@class=\"text\"]"); 								// clear text box
		home.sendText(".//div//input[@class=\"text\"]", searchTerm); 						// search for "samsung"
		
		ResultPage result = home.clickElement(".//button[@type=\"submit\"]"); 				// click "Submit"
		AssertJUnit.assertTrue(searchTerm.equals(result.getElementText(".//h1/span"))); 	// check if "samsung" is in the search 
																							// results
		
		String color = driver.findElement(By.xpath(".//h1/span")).getCssValue("color");		// get element color
		AssertJUnit.assertTrue(color.equals("rgba(50, 154, 28, 1)"));						// check the color
			
		for(int i = 3; i <= 5; i++) {														// choose the search results
																							// and add to the wish list 
			result.clickElement(".//table["+i+"]//a[@name=\"towishlist\"]"); 				
			result.clearTextBox(".//input[@name=\"wishlist_title\"]");
			result.sendText(".//input[@name=\"wishlist_title\"]", "Список желаний "+ (i-2));
			result.clickElement(".//div[@class=\"submit\"]/button[@type=\"submit\"]");
	
			if(i < 5) result.clickElement(".//a[@name=\"close\"]");
		}
	
		result.clickElement(".//div[@class=\"comment\"]/a[@class=\"underline\"]");			// go to the wish list from the pop up

		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);				// make a screenshot
		try {
			FileUtils.copyFile(file, new File("Task_7 - Screenshot.png"));
		} catch (IOException e) { e.printStackTrace(); }
	}	
	
}

