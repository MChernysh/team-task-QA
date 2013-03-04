package Team.Task;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Task8 {
	private FirefoxDriver driver = new FirefoxDriver();
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	
	HomePage home = new HomePage(driver);
	
	
	@BeforeClass
	public void Loggin(){
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		home.SignIN(login, password);
		home.clickElement(By.xpath(".//a[@name=\"close\"]"));	
    	System.out.println("User Logged");
	
	}

    @AfterClass
	public void tearDown(){
	
	driver.close();//closing the Web browser
}
    @Test
    public void testGoToPersonalAccount(){
    	
    	isElementPresent(By.xpath(".//*[@id='user_menu']/ul/li[2]/a"));
    	driver.findElement(By.xpath(".//*[@id='user_menu']/ul/li[2]/a")).click();//open personal account
    	System.out.println("Personal account is open");
    }
    
    public void testGoToWishList(){
    	driver.findElement(By.linkText("Списки желаний")).click();
    	//	driver.get("http://my.rozetka.com.ua/profile/wishlists/");
    	System.out.println("Wishlist opened");
    }
    	
    public void testWishList(){
    	isElementPresent(By.xpath(".//*[@id='481167-1840614']/a"));
    	driver.findElement(By.xpath(".//*[@id='481167-1840614']/a")).click();//remove first product from the first Wishlist
    	File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try {
			FileUtils.copyFile(file, new File("test-output/Task_8 - Screenshot.png"));
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
    	isElementPresent(By.xpath("html/body/div[1]/div/div/div[2]/form/div[1]/ul/li[4]/label/input"));
    	driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/form/div[1]/ul/li[4]/label/input")).click();//goods are moving into the second Wishlist
    	isElementPresent(By.xpath(".//*[@id='user_menu']/ul/li[2]/a"));
    	driver.findElement(By.xpath(".//*[@id='user_menu']/ul/li[2]/a")).click();
    	isElementPresent(By.xpath("html/body/div[1]/div/div/div[2]/form/div[2]/button"));
    	driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/form/div[2]/button")).click();//remove goods from one wishlist to another
    	System.out.println("Goods moved to another wishlist");
    	isElementPresent(By.cssSelector("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[2]/div/div/div[2]/div[1]/table/tbody/tr/td/a/img"));
    	isElementPresent(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[2]/div/div/div[2]/div[2]/a"));//check the presence of the goods in this wishlist
    	System.out.println("Commodity present in  Wishlist2");
    	isElementPresent(By.cssSelector("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[2]/div"));//test for the presence of label 'goods moved'
    }
    private void isElementPresent(By by) {
	List <WebElement> act = driver.findElements(by);//method search element on page
		  if (act.isEmpty()){
			System.out.println("Element was not found"+by.toString()); 
		  }
		 
    }
}
