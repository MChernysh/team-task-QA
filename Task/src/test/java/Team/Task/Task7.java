package Team.Task;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.Reporter;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task7 extends DDT {

	private FirefoxDriver driver = new FirefoxDriver();
	private String url = "http://rozetka.com.ua";
	private String wishlistName = "Test Wishlist ";
	
	// Number of goods to be added to the wish list
	private int numberOfGoods = 3;	

	HomePage homePage = new HomePage(driver);
	WebDriverWait wait = new WebDriverWait(driver, 10);
	
	public boolean isElementPresent(By locator) {
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		boolean isElementPresent = driver.findElements(locator).size() != 0;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return isElementPresent;
	}
	
	public ResultPage searchForCriteria(String searchCriteria) {
		
		homePage.sendText(By.xpath("//div//input[@class='text']"), searchCriteria);
		Reporter.log("Searching for ... " + searchCriteria);
		
		return homePage.clickElement(By.xpath("//div[@class='header']//button[@type='submit']"));
	}
	
	public void goToWishlistPage() {
		
		homePage.clickElement(By.xpath("//a[@name='profile']"));
		homePage.clickElement(By.xpath("//ul[@class='menu-profile']/li[2]//a")); 
		
		Reporter.log("Entering the wish list page ... ");
	}
	
	public void createWishlists(String wishlistName, int numberOfWishlists) {
	
		for (int i = 1; i <= numberOfWishlists; i++) {
			homePage.clickElement(By.xpath(".//*[@id='create_wishlist_button']"));
			homePage.sendText(By.xpath(".//*[@id='wishlist_create_input']"), wishlistName + i);
			homePage.clickElement(By.xpath(".//*[@id='create_wishlist_block']//input[@class='submit']"));
			
			Reporter.log("Creating wish list ... " + (wishlistName + i));
		}
	}
		
	public void checkAndDeleteWishlists() {
		
		if (isElementPresent(By.xpath("//div[@class='cell wishlist-i-delete']/a"))) { 
			homePage.deleteWishLists(By.xpath("//div[@class='cell wishlist-i-delete']/a"));
			
			Reporter.log("Deleting wish lists ... ");
		}
	}
	
	public void addGoodsToWishlist(int numberOfGoods, int offset) {
		
		for (int i = 1; i <= numberOfGoods; i++) {														
			if (isElementPresent(By.xpath("//table[" + (i + offset) + "]//table//a[@name='towishlist']"))) {
				homePage.clickElement(By.xpath("//table[" + (i + offset) + "]//table//a[@name='towishlist']")); 
				homePage.clickElement(By.xpath(".//label[contains(text(),'" + (wishlistName + i) + "')]"));
				homePage.clickElement(By.xpath("//div[@class='submit']//button"));	
				homePage.clickElement(By.xpath("//a[@name='close']"));	
			
				Reporter.log("Adding to the wish list ... " + (wishlistName + i));
			}
		}
	}
	
	public String[] readWishlistGoodsTitles(int numberOfGoodsTitles) {
		
		ResultPage resultPage = new ResultPage(driver);
		String[] arrayOfGoodsTitles = new String[numberOfGoodsTitles];
		
		for (int i = 0; i < numberOfGoodsTitles; i++) {
			arrayOfGoodsTitles[i] = resultPage.getElementText(By.xpath("(.//div[@class='goods tile']//div[@class='title'])[" + (i+1) + "]"));
		}
		Reporter.log("Reading the wish list goods titles ... ");

		return arrayOfGoodsTitles;
	}
	
	public void addGoodsToCart(int numberOfGoods) {
		
		for (int i = 1; i <= numberOfGoods; i++) {
			
			homePage.clickElement(By.xpath("//button[@name='topurchasesfromprofilewishlist']"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='cart-popup']//h2")));
			
			homePage.clickElement(By.xpath(".//*[@id='cart-popup']//div[@class='close']/a"));
			driver.navigate().back();
			
			if(i < numberOfGoods) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name='topurchasesfromprofilewishlist']")));	
			}
		}
		Reporter.log("Adding goods to the purchase cart ... ");
	}
	
	@DataProvider(name = "data")
    public Object[][] createExcelData() throws Exception {
        
		Object[][] arrayOfExcelData = getTableArray(".\\src\\test\\data\\test.xls", "Data", "test7Data");
        
		return arrayOfExcelData;
    }
		
	@BeforeClass
	public void setUp() {
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		
		// sign in, go to the wish list page, check whether there are some and delete them 
		homePage.signIN();
		goToWishlistPage();
		checkAndDeleteWishlists();
		createWishlists(wishlistName, numberOfGoods);
		
	}
	
	@Test(dataProvider = "data") 
	public void Test_01(String searchCriteria) {
		
		// search for the searchTerm and verify the search results
		ResultPage resultPage = searchForCriteria(searchCriteria);
		Assert.assertTrue(searchCriteria.equals(resultPage.getElementText(By.xpath(".//h1/span"))));
		Reporter.log("Validating ... " + searchCriteria);

		// get the element's color and verify it
		String colorOfSearchResultsTitle = driver.findElement(By.xpath("//h1/span")).getCssValue("color");	
		Assert.assertTrue(colorOfSearchResultsTitle.equals("rgba(50, 154, 28, 1)"));						
		Reporter.log("Validating color ... " + colorOfSearchResultsTitle);

		// get 3 search results beginning with the third position and store them to the wish list
		addGoodsToWishlist(numberOfGoods, 2);
		
		goToWishlistPage();
		resultPage.makeScreenshot(".\\target\\screenshots\\task7_test01.png");
	}	
		
	@Test
	public void Test_02() {
		
		ResultPage resultPage = new ResultPage(driver);
		goToWishlistPage();
		
		// get wish lists goods number 
		int numberOfGoodsTitles = resultPage.getNumberOfElements(By.xpath(".//div[@class='goods tile']//div[@class='title']"));
		Reporter.log(numberOfGoodsTitles + " products in the wish list");
		
		// verify whether the wish list goods have UAH prices and if they can be bought
		for (int i = 1; i <= numberOfGoodsTitles; i++) {
			if (isElementPresent(By.xpath("//table[" + (i + 1) + "]//div[@class='status']/span[@class='available']"))) {
				
				Assert.assertTrue(isElementPresent(By.xpath("(//div[@class='price']/div[@class='uah'])[" + i +"]")));
				Assert.assertTrue(isElementPresent(By.xpath("(//button[@name='topurchasesfromprofilewishlist'])[" + i + "]")));
				Reporter.log("Asserting UAH prices and purchase buttons ... ");
			}
		}	
	}
	
	@Test
	public void Test_03() {
		
		ResultPage resultPage = new ResultPage(driver);
		goToWishlistPage();

		// get wish lists goods number 
		int numberOfGoodsInWishlist = resultPage.getNumberOfElements(By.xpath(".//div[@class='goods tile']//div[@class='title']"));
		
		// read wish lists goods titles and add them to the purchase cart 
		String[] arrayOfGoodsTitlesInWishlist = readWishlistGoodsTitles(numberOfGoodsInWishlist);
		addGoodsToCart(numberOfGoodsInWishlist);

		// go to the purchase cart
		resultPage.clickElement(By.xpath(".//div[@class='header']//a[@name='open_cart']"));
		
		// get cart goods number, verify cart goods and wish list goods titles
		String goodsTitleInTheCart;
		int numberOfGoodsInCart = resultPage.getNumberOfElements(By.xpath("//div[@id='cart-popup']//div[@class='title']/a[@name='goods-link']"));
		
		if(numberOfGoodsInWishlist == numberOfGoodsInCart) {
			for (int i = 0; i < numberOfGoodsInCart; i++) {
				goodsTitleInTheCart = resultPage.getElementText(By.xpath("(//div[@id='cart-popup']//div[@class='title']/a[@name='goods-link'])[" + (i+1) + "]"));
				Assert.assertTrue( arrayOfGoodsTitlesInWishlist[i].equals(goodsTitleInTheCart) );
			}
			Reporter.log("Verifying purchase cart goods titles ... ");
		}
		resultPage.makeScreenshot(".\\target\\screenshots\\task7_test03.png");
	}
	
	@AfterClass
	public void tearDown() {
		if (driver != null) {
			checkAndDeleteWishlists();
			homePage.signOut();
			driver.close();
		}
	}
}