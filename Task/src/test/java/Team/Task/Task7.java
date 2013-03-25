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
	private String wishlistName = "Список желаний ";
	private int productCount = 3;	// Goods number to be added to the wish list

	HomePage homePage = new HomePage(driver);
	WebDriverWait wait = new WebDriverWait(driver, 10);
	
	public boolean isElementIn(By by) {
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean present = driver.findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return present;
	}
	
	public ResultPage doSearch(String searchTerm) {
		
		homePage.sendText(By.xpath("//div//input[@class='text']"), searchTerm);
		Reporter.log("Searching for ... " + searchTerm);
		
		return homePage.clickElement(By.xpath("//div[@class='header']//button[@type='submit']"));
	}
	
	public void goToWishlistPage() {
		
		homePage.clickElement(By.xpath("//a[@name='profile']"));
		homePage.clickElement(By.xpath("//ul[@class='menu-profile']/li[2]//a")); 
		
		Reporter.log("Entering the wish list page ... ");
	}
	
	public void createWishlist(String wishlistName, int wishlistNum) {
	
		for (int i = 1; i <= wishlistNum; i++) {
			homePage.clickElement(By.xpath(".//*[@id='create_wishlist_button']"));
			homePage.sendText(By.xpath(".//*[@id='wishlist_create_input']"), wishlistName + i);
			homePage.clickElement(By.xpath(".//*[@id='create_wishlist_block']//input[@class='submit']"));
			
			Reporter.log("Creating wish list ... " + (wishlistName + i));
		}
	}
		
	public void checkAndDeleteWishlists() {
		
		if (isElementIn(By.xpath("//div[@class='cell wishlist-i-delete']/a"))) { 
			homePage.deleteWishLists(By.xpath("//div[@class='cell wishlist-i-delete']/a"));
			
			Reporter.log("Deleting wish lists ... ");
		}
	}
	
	public void addGoodsToWishlist(int goodsCount, int offset) {

		for (int i = 1; i <= goodsCount; i++) {														
			if (isElementIn(By.xpath("//table[" + (i + offset) + "]//table//a[@name='towishlist']"))) {
				homePage.clickElement(By.xpath("//table[" + (i + offset) + "]//table//a[@name='towishlist']")); 
				homePage.clickElement(By.xpath(".//label[contains(text(),'" + (wishlistName + i) + "')]"));
				homePage.clickElement(By.xpath("//div[@class='submit']//button"));	
				homePage.clickElement(By.xpath("//a[@name='close']"));	
			
				Reporter.log("Adding to the wish list ... " + (wishlistName + i));
			}
		}
	}
	
	public String[] readWishlistGoodsTitles(int count) {
		
		ResultPage resultPage = new ResultPage(driver);
		String[] goodsNames = new String[count];
		
		for (int i = 0; i < count; i++) {
			goodsNames[i] = resultPage.getElementText(By.xpath("(.//div[@class='goods tile']//div[@class='title'])[" + (i+1) + "]"));
		}
		Reporter.log("Reading the wish list goods titles ... ");

		return goodsNames;
	}
	
	public void addGoodsToCart(int count) {
		
		for (int i = 1; i <= count; i++) {
			
			homePage.clickElement(By.xpath("//button[@name='topurchasesfromprofilewishlist']"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='cart-popup']//h2")));
			
			homePage.clickElement(By.xpath(".//*[@id='cart-popup']//div[@class='close']/a"));
			driver.navigate().back();
			
			if(i < count) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name='topurchasesfromprofilewishlist']")));	
			}
		}
		Reporter.log("Adding goods to the purchase cart ... ");
	}
	
	@DataProvider(name = "data")
    public Object[][] createData() throws Exception {
        
		Object[][] retObjArr = getTableArray(".\\src\\test\\data\\test.xls", "Data", "test7Data");
        
		return retObjArr;
    }
		
	@BeforeClass
	public void setUp() {
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		
		// sign in, go to the wish list page, check whether there are some and delete them 
		homePage.signIN();
		goToWishlistPage();
		checkAndDeleteWishlists();
		createWishlist(wishlistName, productCount);
		
	}
	
	@AfterClass
	public void tearDown() {
	
		checkAndDeleteWishlists();
		homePage.signOut();
		driver.close();
	}
	
	@Test(dataProvider = "data") 
	public void Test_01 (String searchTerm) {
		
		// search for the searchTerm and verify the search results
		ResultPage resultPage = doSearch(searchTerm);
		Assert.assertTrue(searchTerm.equals(resultPage.getElementText(By.xpath(".//h1/span"))));
		Reporter.log("Validating ... " + searchTerm);

		// get the element's color and verify it
		String color = driver.findElement(By.xpath("//h1/span")).getCssValue("color");	
		Assert.assertTrue(color.equals("rgba(50, 154, 28, 1)"));						
		Reporter.log("Validating color ... " + color);

		// get 3 search results beginning with the third position and store them to the wish list
		addGoodsToWishlist(productCount, 2);
		
		goToWishlistPage();
		resultPage.makeScreenshot(".\\target\\screenshots\\task7_test01.png");
	}	
		
	@Test
	public void Test_02() {
		
		ResultPage resultPage = new ResultPage(driver);
		goToWishlistPage();
		
		// get wish lists goods number 
		int productCount = resultPage.getElementCount(By.xpath(".//div[@class='goods tile']//div[@class='title']"));
		Reporter.log(productCount + " products in the wish list");
		
		// verify whether the wish list goods have UAH prices and if they can be bought
		for (int i = 1; i <= productCount; i++) {
			if (isElementIn(By.xpath("//table[" + (i + 1) + "]//div[@class='status']/span[@class='available']"))) {
				
				Assert.assertTrue(isElementIn(By.xpath("(//div[@class='price']/div[@class='uah'])[" + i +"]")));
				Assert.assertTrue(isElementIn(By.xpath("(//button[@name='topurchasesfromprofilewishlist'])[" + i + "]")));
				Reporter.log("Asserting UAH prices and purchase buttons ... ");
			}
		}	
	}
	
	@Test
	public void Test_03() {
		
		ResultPage resultPage = new ResultPage(driver);
		goToWishlistPage();

		// get wish lists goods number 
		int wishlistGoodsCount = resultPage.getElementCount(By.xpath(".//div[@class='goods tile']//div[@class='title']"));
		
		// read wish lists goods titles and add them to the purchase cart 
		String[] wishlistGoodsTitles = readWishlistGoodsTitles(wishlistGoodsCount);
		addGoodsToCart(wishlistGoodsCount);

		// go to the purchase cart
		resultPage.clickElement(By.xpath(".//div[@class='header']//a[@name='open_cart']"));
		
		// get cart goods number, verify cart goods and wish list goods titles
		String cartGoodsTitle;
		int cartGoodsCount = resultPage.getElementCount(By.xpath("//div[@id='cart-popup']//div[@class='title']/a[@name='goods-link']"));
		
		if(wishlistGoodsCount == cartGoodsCount) {
			for (int i = 0; i < cartGoodsCount; i++) {
				cartGoodsTitle = resultPage.getElementText(By.xpath("(//div[@id='cart-popup']//div[@class='title']/a[@name='goods-link'])[" + (i+1) + "]"));
				Assert.assertTrue( wishlistGoodsTitles[i].equals(cartGoodsTitle) );
			}
			Reporter.log("Verifying purchase cart goods titles ... ");
		}
		resultPage.makeScreenshot(".\\target\\screenshots\\task7_test03.png");
	}
}