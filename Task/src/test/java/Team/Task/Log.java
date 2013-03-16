package Team.Task;





import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class Log {
	private WebDriver driver;
	public Log(WebDriver driver){
	this.driver = driver;
	
	}

public void Login(String login,String password){
	isElementPresent(By.name("signin"));
    driver.findElement(By.name("signin")).click();
isElementPresent(By.name("login"));
driver.findElement(By.name("login")).sendKeys(login);
isElementPresent(By.name("password"));
driver.findElement(By.name("password")).sendKeys(password);
isElementPresent(By.xpath("//*[text()='Войти']"));
driver.findElement(By.xpath("//*[text()='Войти']")).click();
}
public void LogOut(){
	 isElementPresent(By.name("signout"));
	 driver.findElement(By.name("signout")).click();
}




private void isElementPresent(By name) {
	// TODO Auto-generated method stub
	
}



}