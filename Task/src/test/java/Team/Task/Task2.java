package Team.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = { "Ljuda" })
public class Task2 {
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
    	isElementPresent(By.xpath(".//*[@id='user_menu']/ul/li[2]/a"));
    	driver.findElement(By.xpath(".//*[@id='user_menu']/ul/li[2]/a")).click();//open personal account
    	System.out.println("Personal account is open");
    	driver.findElement(By.linkText("Списки желаний")).click();
    	
	}

    
	@AfterClass
	public void tearDown(){
		    
		    isElementPresent(By.linkText("Удалить"));
		    driver.findElement(By.linkText("Удалить")).click();
		    isElementPresent(By.linkText("Удалить"));
		    driver.findElement(By.linkText("Удалить")).click();
		    isElementPresent(By.cssSelector("div.message.code1"));
	driver.close();//closing the Web browser
}
    public void testTask2(){
        driver.findElement(By.id("create_wishlist_button")).click();
        driver.findElement(By.id("wishlist_create_input")).clear();
        driver.findElement(By.id("wishlist_create_input")).sendKeys("List1");
        driver.findElement(By.cssSelector("input.submit")).click();
        driver.findElement(By.id("create_wishlist_button")).click();
        driver.findElement(By.id("wishlist_create_input")).clear();
        driver.findElement(By.id("wishlist_create_input")).sendKeys("List2");
        driver.findElement(By.cssSelector("input.submit")).click();
        driver.findElement(By.id("create_wishlist_button")).click();
        driver.findElement(By.id("wishlist_create_input")).clear();
        driver.findElement(By.id("wishlist_create_input")).sendKeys("List3");
        driver.findElement(By.cssSelector("input.submit")).click();
        String title11 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[1]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title12 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title13 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        driver.findElement(By.linkText("Сделать по умолчанию")).click();
        String title21 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[1]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title22 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title23 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        compare(title11, title21);
        compare(title12, title22);
        compare(title13, title23);
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[1]/div[2]/div[3]/a")).click();
        String title31 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[1]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title32 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        compare(title11, title31);
        compare(title12, title32);
        
      }

private void isElementPresent(By by) {
	List <WebElement> act = driver.findElements(by);//method search element on page
		  if (act.isEmpty()){
			System.out.println("Element was not found"+by.toString()); 
		  }
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
}   
