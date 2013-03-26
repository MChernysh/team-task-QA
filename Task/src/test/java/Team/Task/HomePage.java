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
	
	//login in site
	public void signIN(String login, String password) {

		clickElement(By.name("signin"));
		sendText(By.name("login"), login);
		sendText(By.name("password"), password);
		clickElement(By.xpath("//*[text()='Войти']"));
		if (validateElement(By.xpath(".//a[@name=\"close\"]"))) {
			clickElement(By.xpath(".//a[@name=\"close\"]"));
		}
                Reporter.log("Log in: " + login + " Password: " + password);
	}
	public void signIN() {

		clickElement(By.name("signin"));
		sendText(By.name("login"), "testatqc@gmail.com");
		sendText(By.name("password"), "IF-025.ATQC");
		clickElement(By.xpath("//*[text()='Войти']"));
		if (validateElement(By.xpath(".//a[@name=\"close\"]"))) {
			clickElement(By.xpath(".//a[@name=\"close\"]"));
		}
                Reporter.log("Log in:  testatqc@gmail.com // Password: IF-025.ATQC"); 
	}
	
	//move to personal cabinet
	public void goToPersonalPage() {
		clickElement(By.name("profile"));		
	}
	
	//move to wishlist  
	public void goToWishList(){
		clickElement(By.linkText("Списки желаний"));
	}
	
	//count category, in main menu, marked as "New"
	public int countNewCategory(){
		return driver.findElements(By.xpath(".//*[@id='main_menu']//span[@class='new']")).size();
	}
	
	//move to specified main menu item 
	public Sort gotoMenuItem(String title, String item) throws NoSuchMethodException, SecurityException{
		
		clickElement(By.xpath(".//*[text()='"+title+"']"));
		clickElement(By.xpath(".//*[text()='"+item+"']"));
		return new Sort(driver);
	}
	
	//search some item in shop 
	public void search(String text){
		
		sendText(By.xpath("//*[@hint='Поиск']"),text);
		clickElement(By.xpath("//*[text()='Найти']"));
	}

	//add some item to wishlist
	public void addtoWishList(int numberOfElement, String wishListName){
		
		clickElement(By.xpath(".//*[@class='item available']["+numberOfElement+"]/descendant::a[5]"));
		sendText(By.xpath(".//*[@name='wishlist_title']"),wishListName);
		clickElement(By.xpath(".//*[@class='submit']/button[text()='Сохранить']"));
	}

	//logout in site
	public void signOut(){
		clickElement(By.name("signout"));
	}
	
	//check if wishlist is empty
	public boolean checkWishList(){
		
		return validateElement(By.xpath(".//*[@class='container']/p[contains(.,'У вас пока нет')]"));	
	}
	
	
	public int countTitle(){
		
		int count1 = driver.findElements(By.xpath("//div[@class='title']/a")).size();
		return count1;
	}
	
	//get CSS value of main menu title
	public String getTitleStyle(){
		
		String fontweight = "Not bold";
		String fontsize  = driver.findElement(By.xpath(".//*[@class='popup-css popup-css-green m-m-fatmenu']//h6"))
				.getCssValue("font-size");
		String element = driver.findElement(By.xpath(".//*[@class='popup-css popup-css-green m-m-fatmenu']//h6"))
				.getCssValue("font-weight");
		if(Integer.parseInt(element)>=700){
			fontweight = "Bold";}
		String result = "Menu Font-size is " + fontsize + "\n Font-weight is "  + fontweight;
		return result;	
	}

	public void deleteWishLists(By by){
		int count = driver.findElements(by).size();
		for(int j = 0; j < count; j++) 
			driver.findElement(by).click();
	}
	
	public void deleteWishLists(){
		int count = driver.findElements(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")).size();
		for(int j = 0; j < count; j++) 
			driver.findElement(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")).click();
	}
	
	
	public boolean validateElement(By by) {

		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()) {
			return false;
		} else
			return true;
	}

	public void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()) {
			Reporter.log("Element was not found in class 'HomePage' - "
					+ by.toString());
			driver.quit();
		}

	}

	public ResultPage clickElement(By by) {
		isElementPresent(by);
		driver.findElement(by).click();
		return new ResultPage(driver);
	}

	public void sendText(By by, String text) {
		isElementPresent(by);
		clearTextBox(by);
		driver.findElement(by).sendKeys(text);
	}

	public void clearTextBox(By by) {
		isElementPresent(by);
		driver.findElement(by).clear();
	}
	
	public String getTextFrom(By by) {
		isElementPresent(by);
		return driver.findElement(by).getText();
	}
	public String getTheAttribute(By by, String attribute) {		
		String recivedAttribute = driver.findElement(by).getAttribute(attribute);
		return recivedAttribute;
	}
}


