package Team.Task;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import java.io.File;
import java.util.concurrent.TimeUnit;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Task7 {

	private FirefoxDriver driver = new FirefoxDriver();
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	
	HomePage homePage = new HomePage(driver);
	
	public boolean isElementIn(By by) {
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean present = driver.findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return present;
	}
	
	@BeforeClass
	public void setUp() {
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		
		// sign in and close the annoying social media pop up
		homePage.SignIN(login, password);
		homePage.clickElement(By.xpath(".//a[@name='close']"));		
		Reporter.log("Log in: " + login + " Password: " + password);

		// go to the wish list page, check whether there are some and delete them
		homePage.clickElement(By.xpath(".//a[@name='profile']"));
		homePage.clickElement(By.xpath("//div[@class='title']/a[contains(text(), 'Списки желаний')]"));
		
		if (isElementIn(By.xpath("//div[@class='cell wishlist-i-delete']/a[@name='wishlist-delete']"))) {
			homePage.deleteWishLists(By.xpath("//div[@class='cell wishlist-i-delete']/a[@name='wishlist-delete']"));
		}
	}
	
	@AfterClass
	public void tearDown() {
		
		homePage.deleteWishLists(By.xpath("//div[@class='cell wishlist-i-delete']/a[@name='wishlist-delete']"));
		Reporter.log("Deleting wish lists");
		driver.close();
	}
	
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) throws Exception {
        	
		String[][] tabArray = null;
        
        Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
        Sheet sheet = workbook.getSheet(sheetName); 
        int startRow,startCol, endRow, endCol,ci, cj;
       
        Cell tableStart = sheet.findCell(tableName);
        
        startRow = tableStart.getRow();
        startCol = tableStart.getColumn();

        Cell tableEnd = sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                

        endRow = tableEnd.getRow();
        endCol = tableEnd.getColumn();
        
            
        tabArray = new String[endRow-startRow-1][endCol-startCol-1];
        
        ci = 0;

            for (int i = startRow + 1; i < endRow; i++, ci++) {
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    tabArray[ci][cj] = sheet.getCell(j,i).getContents();
                }
            }
        
        return(tabArray);
    }
	
	@DataProvider(name = "data")
    public Object[][] createData() throws Exception {
        
		Object[][] retObjArr = getTableArray("./src/test/data/test.xls", "Data", "test7Data");
        
		return(retObjArr);
    }
	
	@Test(dataProvider = "data") 
	public void Test(String searchTerm) {
		
		// clear the text box and search for the searchTerm
		homePage.clearTextBox(By.xpath(".//div//input[@class='text']"));
		homePage.sendText(By.xpath(".//div//input[@class='text']"), searchTerm);
		Reporter.log("Searching for: " + searchTerm);
		
		// click "Submit" button and verify whether search results contain the searchTerm
		ResultPage resultPage = homePage.clickElement(By.xpath(".//button[@type='submit']")); 				
		AssertJUnit.assertTrue(searchTerm.equals(resultPage.getElementText(By.xpath(".//h1/span")))); 	 
		Reporter.log("Validating: " + searchTerm);

		// get the element's color and verify it
		String color = driver.findElement(By.xpath(".//h1/span")).getCssValue("color");	
		AssertJUnit.assertTrue(color.equals("rgba(50, 154, 28, 1)"));						
		Reporter.log("Validating color: " + color);

		String wishlistName;
		int elemNum = 3; // number of products to be added to the wish list 
		int offset = 2;
		
		// get 3 search results beginning with the third position and store them to the wish list
		for (int i = 1; i <= elemNum; i++) {														
																							 
			if (isElementIn(By.xpath(".//table[" + (i + offset) + "]//a[@name='towishlist']"))) {

				wishlistName = "Список желаний " + i;
				resultPage.clickElement(By.xpath(".//table[" + (i + offset) + "]//a[@name='towishlist']")); 				
				int wishlistCount = resultPage.getElementCount(By.xpath(".//label/input"));
				
				// check whether an appropriate number of wish lists exists, if not, create them
				if (wishlistCount == elemNum) {
					resultPage.clickElement(By.xpath(".//ul/li[" + i + "]/label/input")); // select the existing wish list
				} else {
					resultPage.clearTextBox(By.xpath(".//input[@name='wishlist_title']"));
					resultPage.sendText(By.xpath(".//input[@name='wishlist_title']"), wishlistName);	// create new wish list
				}
				
				// add product to the selected wish list
				resultPage.clickElement(By.xpath(".//div[@class='submit']/button[@type='submit']"));	
				Reporter.log("Creating wish list: " + wishlistName);
							
				if (i < elemNum) {
					resultPage.clickElement(By.xpath(".//a[@name='close']"));
				}
			}
		}
		
		// go to the wish list page from the pop up
		resultPage.clickElement(By.xpath(".//div[@class='comment']/a[@class='underline']"));			

		// make a screenshot and save it to the project's directory
		resultPage.makeScreenshot("target/screenshots/task_7.png");
		Reporter.log("Taking a screenshot");
	}	
}

