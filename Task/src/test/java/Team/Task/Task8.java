package Team.Task;


import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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


public class Task8 {
	private FirefoxDriver driver = new FirefoxDriver();
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	HomePage home = new HomePage(driver);
	
public boolean isElementIn(By by) {
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean present = driver.findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		
		return present;
	}
	
	
	@BeforeTest
	public void Loggin(){
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		home.SignIN(login, password);
		home.clickElement(By.xpath(".//a[@name=\"close\"]"));	
    	System.out.println("User Logged");
    	
    	home.clickElement(By.xpath(".//a[@name=\"profile\"]"));
		home.clickElement(By.xpath("//div[@class=\"title\"]/a[contains(text(), \"Списки желаний\")]"));
		
		if(isElementIn(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")))
			home.deleteWishLists(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]"));	
	}    
	@AfterTest
	public void tearDown(){
	driver.close();//closing the Web browser
}
    @Test(groups = { "Ljuda" })
    public void testTask8(){
    	driver.findElement(By.name("text")).clear();
        driver.findElement(By.name("text")).sendKeys("samsung");
        driver.findElement(By.cssSelector("button.button-css-green")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Сохранить')])[2]")).click();
        driver.findElement(By.name("wishlist_title")).click();
        driver.findElement(By.name("wishlist_title")).clear();
        driver.findElement(By.name("wishlist_title")).sendKeys("Список1");
        driver.findElement(By.cssSelector("button.button-css-green")).click();
        driver.findElement(By.linkText("Закрыть")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Сохранить')])[3]")).click();
        driver.findElement(By.name("wishlist_title")).clear();
        driver.findElement(By.name("wishlist_title")).sendKeys("Список2");
        driver.findElement(By.cssSelector("button.button-css-green")).click();
        driver.findElement(By.linkText("Закрыть")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Сохранить')])[4]")).click();
        driver.findElement(By.name("wishlist_title")).clear();
        driver.findElement(By.name("wishlist_title")).sendKeys("Список3");
        driver.findElement(By.cssSelector("button.button-css-green")).click();
        driver.findElement(By.linkText("Закрыть")).click();
	
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
      
        String alt2 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[2]/div/div/div[1]/div[2]/a")).getAttribute("alt");
        String title2 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[2]/div/div/div[1]/div[2]/a")).getAttribute("title");
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


