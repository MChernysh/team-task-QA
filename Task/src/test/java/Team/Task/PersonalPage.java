package Team.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PersonalPage {
	private WebDriver driver;
	public PersonalPage(WebDriver driver) {
		this.driver = driver;
	}
	public void goToWishList(){
		
		driver.findElement(By.linkText("Списки желаний")).click();
	//	driver.get("http://my.rozetka.com.ua/profile/wishlists/");
	}
	public void signOut(){
		 isElementPresent(By.name("signout"));
		 driver.findElement(By.name("signout")).click();
	}
	private void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()){
			System.out.println("Element was not found in class 'RozetkaHomePage' - "+by.toString()); 
			driver.quit();
		}
	}
}
