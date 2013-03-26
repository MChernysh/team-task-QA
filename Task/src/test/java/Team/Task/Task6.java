package Team.Task;



import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Task6 {
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private WebDriver driver;


@Test(groups = { "Orest" })
public void Test6(){
HomePage home=new HomePage(driver);
home.SignIN(login, password);
driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
String color = driver.findElement(By.xpath(".//*[@id='head_banner']/a")).getCssValue("background-color");
Assert.assertTrue(color.equals("rgba(50, 154, 28, 1)"));
String toolbarcolor = driver.findElement(By.className("hdr-tools")).getCssValue("background-color");
Assert.assertTrue(toolbarcolor.equals("rgba(17, 73, 137, 1)"));
home.signOut();
}
	@BeforeTest
	public void setUp(){
	driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  driver.get("http://rozetka.com.ua");
	}

@AfterTest
	  private void tearDown() {
        File screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
       String path = "./target/screenshots/Task6.png" ;
        try {
           FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e) {

        }
        driver.quit();
}
}



	
	

