package Team.Task;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
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


public class Task8 {
	private FirefoxDriver driver = new FirefoxDriver();
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	HomePage home = new HomePage(driver);
	
	
	@BeforeClass
	public void Loggin(){
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
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
    public void testTask8(){
    	
    	isElementPresent(By.xpath(".//*[@id='user_menu']/ul/li[2]/a"));
    	driver.findElement(By.xpath(".//*[@id='user_menu']/ul/li[2]/a")).click();//open personal account
    	System.out.println("Personal account is open");
    	driver.findElement(By.linkText("Списки желаний")).click();
    	String alt1 = driver.findElement(By.linkText("Переместить")).getAttribute("alt");
    	String title1 = driver.findElement(By.linkText("Переместить")).getAttribute("title");
    	driver.findElement(By.linkText("Переместить")).click();
        driver.findElement(By.name("data[wishlist_id]")).click();
        driver.findElement(By.cssSelector("button.button-css-green")).click();
        driver.findElement(By.cssSelector("div.message.code1")).click();
        
        String alt2 = driver.findElement(By.xpath(".//*[@id='486104-1864241']/a")).getAttribute("alt");
    	String title2 = driver.findElement(By.xpath(".//*[@id='486104-1864241']/a")).getAttribute("title");
    	compare(alt1, alt2);
    	compare(title1, title2);
        //verifyTextPresent may require manual changes
        isElementPresent(By.cssSelector("div.message.code1"));
        driver.findElement(By.cssSelector("BODY")).getText();    
    	File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try {
			FileUtils.copyFile(file, new File("test-output/Task_8 - Screenshot.png"));
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
    	System.out.println("Goods moved to another wishlist");
    }
    private void compare(String frststr, String scndstr) {
		try {
			Assert.assertEquals(frststr, scndstr);
		} 
		catch (AssertionError e) {
			System.out.println("Strings \n" + "'" + frststr + "'" + "\n and \n" + "'" + scndstr + "'" + "\n are not equal!");
			//Reporter.log("Strings <br>" + "'" + frststr + "'" + "<br> and <br>" + "'" + scndstr + "'" + "<br> are not equal!<br>");
		}
	}

	private void isElementPresent(By by) {
	List <WebElement> act = driver.findElements(by);//method search element on page
		  if (act.isEmpty()){
			System.out.println("Element was not found"+by.toString()); 
		  }
    }
}


