package Team.Task;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class HomePage {

	private WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	public void SignIN(String login, String password) {
		isElementPresent(By.name("signin"));
		driver.findElement(By.name("signin")).click();
		isElementPresent(By.name("login"));
		driver.findElement(By.name("login")).sendKeys(login);
		isElementPresent(By.name("password"));
		driver.findElement(By.name("password")).sendKeys(password);
		isElementPresent(By.xpath("//*[text()='Войти']"));
		driver.findElement(By.xpath("//*[text()='Войти']")).click();
	}
	public PersonalPage goToPersonalPage() {

		isElementPresent(By.name("profile"));
		driver.findElement(By.name("profile")).click();
		return new PersonalPage(driver);
	}

	public void goToWishList(){

		driver.findElement(By.linkText("Списки желаний")).click();

	}
	public int countNewCategoty(){
		int count = driver.findElements(By.xpath(".//*[@id='main_menu']//span[@class='new']")).size();
		return count;


	}
	public void serch(String text){
		clearTextBox("//*[@hint='Поиск']");
		sendText("//*[@hint='Поиск']",text);
		isElementPresent(By.xpath("//*[text()='Найти']"));
		driver.findElement(By.xpath("//*[text()='Найти']")).click();

	}

	public void addtoWishList(int numberOfElement, String wishListName){
		isElementPresent(By.xpath(".//*[@class='item available']["+numberOfElement+"]/descendant::a[5]"));
		driver.findElement(By.xpath(".//*[@class='item available']["+numberOfElement+"]/descendant::a[5]")).click();
		clearTextBox(".//*[@name='wishlist_title']");
		sendText(".//*[@name='wishlist_title']",wishListName);
		driver.findElement(By.xpath(".//*[@class='submit']/button[text()='Сохранить']")).click();
	}


	public void signOut(){
		isElementPresent(By.name("signout"));
		driver.findElement(By.name("signout")).click();
	}
	public boolean checkWishList(){
		if(ValidateElement(By.xpath(".//*[@class='container']/p[contains(.,'У вас пока нет')]"))){
			return true;
		}else return false;	
	}

	public String getTitleStyle(){
		String fontweight = "Not bold";
		String fontsize  = driver.findElement(By.xpath(".//*[@class='popup-css popup-css-green m-m-fatmenu']//h6"))
				.getCssValue("font-size");
		String el = driver.findElement(By.xpath(".//*[@class='popup-css popup-css-green m-m-fatmenu']//h6"))
				.getCssValue("font-weight");
		if(Integer.parseInt(el)>=700){
			fontweight = "Bold";}
		String result = "Menu Font-size is " + fontsize + "\n Font-weight is "  + fontweight;
		return result;	
	}



	private boolean ValidateElement(By by) {
		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()){
			return false; 
		}else return true;

	}

	private void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()){
			Reporter.log("Element was not found in class 'RozetkaHomePage' - "+by.toString()); 
			driver.quit();
		}

	}


	public void sendText(String xpath, String text) {

		isElementPresent(By.xpath(xpath));
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	public ResultPage clickElement(String xpath){

		isElementPresent(By.xpath(xpath));
		driver.findElement(By.xpath(xpath)).click();
		return new ResultPage(driver);
	}



	public void clearTextBox(String xpath){

		isElementPresent(By.xpath(xpath));
		driver.findElement(By.xpath(xpath)).clear();
	}

}


