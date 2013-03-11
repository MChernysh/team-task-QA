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

public class Task7{

	private FirefoxDriver driver = new FirefoxDriver();
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	
	HomePage home = new HomePage(driver);
	
	public boolean isElementIn(By by) {
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean present = driver.findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return present;
	}
	
	@BeforeClass
	public void setUp(){
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		
		// sign in and close the annoying social media pop up
		home.SignIN(login, password);
		home.clickElement(By.xpath(".//a[@name=\"close\"]"));		
		Reporter.log("Log in: " + login + " Password: " + password);

		// go to wish list page, check whether there are some and delete them
		home.clickElement(By.xpath(".//a[@name=\"profile\"]"));
		home.clickElement(By.xpath("//div[@class=\"title\"]/a[contains(text(), \"Списки желаний\")]"));
		
		if(isElementIn(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")))
			home.deleteWishLists(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]"));	
	}
	
	@AfterClass
	public void tearDown(){
		
		home.deleteWishLists(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]"));
		Reporter.log("Deleting wishlists");
		driver.close();
	}
	
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) throws Exception{
        	
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

            for (int i = startRow + 1; i < endRow; i++, ci++){
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++){
                    tabArray[ci][cj] = sheet.getCell(j,i).getContents();
                }
            }
        
        return(tabArray);
    }

	@DataProvider(name = "data")
    public Object[][] createData1() throws Exception{
        
		Object[][] retObjArr = getTableArray(".\\src\\test\\data\\test.xls", "Data", "test7Data");
        
		return(retObjArr);
    }
	
	@Test (dataProvider = "data")
	public void Test(String searchTerm){
		
		// clear the text box and search for the searchTerm
		home.clearTextBox(By.xpath(".//div//input[@class=\"text\"]"));
		home.sendText(By.xpath(".//div//input[@class=\"text\"]"), searchTerm);
		Reporter.log("Searching for: " + searchTerm);
		
		// click "Submit" button and verify whether search results contain the searchTerm
		ResultPage result = home.clickElement(By.xpath(".//button[@type=\"submit\"]")); 				
		AssertJUnit.assertTrue(searchTerm.equals(result.getElementText(By.xpath(".//h1/span")))); 	 
		Reporter.log("Validating: " + searchTerm);

		// get the element's color and verify it
		String color = driver.findElement(By.xpath(".//h1/span")).getCssValue("color");	
		AssertJUnit.assertTrue(color.equals("rgba(50, 154, 28, 1)"));						
		Reporter.log("Validating color: " + color);
			
		// get 3..5 search results and store them to the wish list
		for(int i = 3; i <= 5; i++) {														
																							 
			if(isElementIn(By.xpath(".//table[" + i + "]//a[@name=\"towishlist\"]"))){
				
				String wishlist = "Список желаний " + (i-2);
				
				result.clickElement(By.xpath(".//table[" + i + "]//a[@name=\"towishlist\"]")); 				
				result.clearTextBox(By.xpath(".//input[@name=\"wishlist_title\"]"));
				result.sendText(By.xpath(".//input[@name=\"wishlist_title\"]"), wishlist);
				result.clickElement(By.xpath(".//div[@class=\"submit\"]/button[@type=\"submit\"]"));
				Reporter.log("Creating wishlist: " + wishlist);
			
			if(i < 5) 
				result.clickElement(By.xpath(".//a[@name=\"close\"]"));
			}
		}
		
		// visit the wish list page from the pop up
		result.clickElement(By.xpath(".//div[@class=\"comment\"]/a[@class=\"underline\"]"));			

		// make a screenshot and save it to the project's directory
		result.makeScreenshot("test-output/Task_7 - Screenshot.png");
		Reporter.log("Taking a screenshot");
	}	
}

