package Team.Task;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Test1 {
	private WebDriver driver;
	
  @Before
  public void setUp(){
	  driver = new FirefoxDriver();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.get("http://rozetka.com.ua/");
  }
  
  @After
  public void turnDown(){
	  driver.quit();
  }
  
  @Test
  public void validateFirstTab(){
	  HomePage1 HomePage1 = new HomePage1(driver);
	  HomePage1.SignIN("testatqc@gmail.com", "IF-025.ATQC");
	  
  }
  
  }
