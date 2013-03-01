package Team.Task;

//import static org.junit.Assert.*;

//import HomePage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
//import static org.testng.Assert.*;


public class Task11 {
	
	private WebDriver driver = new FirefoxDriver();;
	HomePage homepage = new HomePage(driver);
	
	
	@BeforeClass
	  public void settingUp(){
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		homepage.SignIN("testatqc@gmail.com", "IF-025.ATQC");
	  }
	
	
	@AfterClass
	  public void turnDown(){
		//  driver.quit();
	  }		
	

	@Test
	public void task11() {
		driver.findElement(By.xpath(".//*[@id='computers-notebooks']/a")).click();
				
		driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div/div[1]/div/div[4]/div/div[1]/div[1]/ul/li[4]/a")).click();
				
		String tooltip = driver.findElement(By.xpath(".//*[@id='image_item255352']/a/img")).getAttribute("alt");
		String name = driver.findElement(By.xpath(".//*[@id='image_item255352']/a/img")).getAttribute("title"); 
		
		System.out.println(tooltip);
		System.out.println(name);
		Assert.assertEquals(name, tooltip);
		
		driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div/div[2]/div/div[3]/div[2]/a")).click();
		
		//?? driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div/div[1]/div[6]/div/div[3]/div[1]")).getAttribute("d");
		
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "./target/screenshots/" + screenshot.getName();
        try {
           FileUtils.copyFile(screenshot, new File("Screenshot_task11.png"));
        } catch (IOException e) {

        }
		
		
        		
	}

}
