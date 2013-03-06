package Team.Task;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestCase10P2 {

	private FirefoxDriver driver;
	private String priceGeneralPage;
	private String codeGeneralPage;
	private String priceReferencePage;
	private String codeReferencePage;
	private String starFinall;
	
	
	public TestCase10P2(FirefoxDriver driver) {	
		this.driver = driver;
	}
	
	public boolean userExist() {
		try		{
		driver.findElementByXPath(".//*[@name='profile']");
				return true;	}
		catch (NoSuchElementException e) {
			return false;
		}		
	}
	
	public String accountName() {
		return driver.findElementByXPath(".//*[@name='profile']").getText();
	}
	
			//chose needed finding condition at the page 
	public void makeSortCondition() {
			//phone item is chosen
//		driver.findElementByXPath(".//*[@id='phones-mp3-gps']/a/span").click();
//		
//			//left item mobile phone is chosen
//		driver.findElementByXPath
//		(".//*[@id='head_banner_container']/div[2]/div/div/div[2]/div/div/div/div/div[3]/ul/li[1]/ul/li[1]/a").click();
			//chose mobile phone item
		driver.findElementByXPath(".//*[@href='http://rozetka.com.ua/ru/products/procategory/92/index.html']").click();
		
			//price from 1600 to 2799 item is chosen
		driver.findElementByXPath
       		 (".//*[@id='head_banner_container']/div[3]/div/div/div[2]/div/div/div/div/div[3]/ul/li[4]/ul/li[4]/a").click();
		
			//phone name HTC is marked
		driver.findElementByXPath(".//*[@href='http://rozetka.com.ua/mobile-phones/c80003/filter/price=1600-2799;producer=htc/']").click();
		
			//take phone price from general page
		priceGeneralPage = driver.findElementByXPath
				(".//*[@id='head_banner_container']/div[2]/div/div/div[2]/div/div[4]/table[3]/tbody/tr/td[2]/table[1]/tbody/tr/td[1]/div[2]/div[2]").getText();
		
			//take phone code from general page
		codeGeneralPage = driver.findElementByXPath(".//*[@id='head_banner_container']/div[2]/div/div/div[2]/div/div[4]/table[3]/tbody/tr/td[2]/div[1]/span").getText();        		
	}
	
	public void thirdPhoneReference() {
			//came to 3 phone reference in the list of phones
		driver.findElementByXPath
       		 (".//*[@id='head_banner_container']/div[2]/div/div/div[2]/div/div[4]/table[3]/tbody/tr/td[2]/div[1]/a").click();
		
			//take phone price from reference page
		priceReferencePage = driver.findElementByXPath(".//*[@class='pp-usd']").getText();
		
			//take phone code from reference page
		codeReferencePage = driver.findElementByXPath(".//*[@class='pp-code']").getText();

	}
	
	
			//checking or phone on main page and phone on reference page are equals
	public boolean checkIfPhonesAreSame()	{
		if ((priceGeneralPage.equals(priceReferencePage)) && (codeGeneralPage.equals(codeReferencePage)))
			return true;
		else		
			return false;
	}
	
	//method that check number of phone star
		public int starsNumber()	{

			String star = driver.findElementByXPath(".//*[@typeof='v:Rating']").getAttribute("style");
			
				starFinall = star.replaceAll("\\D", ""); 
				
			int starPersent = Integer.parseInt(starFinall);
			int starNumber = starPersent/20;
			return starNumber;	
		}

	
			//save phone in wish list
	public void saveWishList()		{
		try		{
		if (driver.findElementByXPath(".//*[@href='#Wishlist']").isDisplayed())
			driver.findElementByXPath(".//*[@href='#Wishlist']").click();
			driver.findElementByXPath(".//*[@class='button-css-green']").click();
			
		}
		catch (NoSuchElementException e)	{
			System.out.println("You have this phone in your wish list");
		}
	}
	
			//checking item which are in wish list
	public void checkWishList()		{
		driver.findElementByXPath(".//*[@name='profile']").click();
		driver.findElementByXPath(".//*[@href='http://my.rozetka.com.ua/profile/wishlists/']").click();		
	}
	
	
			//making screenshot from list screen
	public void makeScreenshot()		{
		File screenFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try		{
		FileUtils.copyFile(screenFile, new File("TestCase10.png"));
		}		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
