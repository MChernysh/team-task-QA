package Team.Task;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class Task2 {
	private FirefoxDriver driver = new FirefoxDriver();
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	HomePage home = new HomePage(driver);
	
public boolean isElementIn(By by) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean present = driver.findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		return present;
	}
	
	
	@BeforeClass
	public void Loggin(){
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		home.SignIN(login, password);
		home.clickElement(By.xpath(".//a[@name=\"close\"]"));	
    	System.out.println("User Logged");
    	Reporter.log("User Logged");
    	isElementPresent(By.xpath(".//*[@id='user_menu']/ul/li[2]/a"));
    	driver.findElement(By.xpath(".//*[@id='user_menu']/ul/li[2]/a")).click();//open personal account
    	System.out.println("Personal account is open");
    	Reporter.log("Personal account is open");
    	driver.findElement(By.linkText("Списки желаний")).click();
    	
    	if(isElementIn(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")))
			home.deleteWishLists(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]"));	
    	
	}

    
	@AfterClass
	public void tearDown(){
		    
		if(isElementIn(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]")))
		home.deleteWishLists(By.xpath("//div[@class=\"cell wishlist-i-delete\"]/a[@name=\"wishlist-delete\"]"));	
	driver.close();//closing the Web browser
}
	@DataProvider(name = "data")
    public Object[][] createData1() throws Exception{
        Object[][] retObjArr=getTableArray("c:\\Users\\Людмила\\NewNew\\Task\\src\\test\\data\\test.xls", "Data", "Test2");
        return(retObjArr);
    }
	@Test(groups = { "Ljuda" })
    public void testTask2(String NameOfWishlist){
		for(int i = 1; i <= 10; i++) {	
        	driver.findElement(By.id("create_wishlist_button")).click();
            driver.findElement(By.id("wishlist_create_input")).clear();
            driver.findElement(By.id("wishlist_create_input")).sendKeys(NameOfWishlist +i);
            driver.findElement(By.cssSelector("input.submit")).click();	
            System.out.println("wishlist" + i + "created" );
	}
		
        String title11 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[1]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title12 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title13 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        driver.findElement(By.linkText("Сделать по умолчанию")).click();
        String title21 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[1]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title22 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title23 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        compare(title11, title21);
        compare(title12, title22);
        compare(title13, title23);
        driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[3]/div[1]/div[2]/div[3]/a")).click();
        String title31 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[1]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        String title32 = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[1]/div/h3")).getAttribute("title");
        compare(title11, title31);
        compare(title12, title32);
        Reporter.log("System installation Wishlist by default and deleting Wishlists work correctly");
        
      }
private void isElementPresent(By by) {
	List <WebElement> act = driver.findElements(by);//method search element on page
		  if (act.isEmpty()){
			System.out.println("Element was not found"+by.toString()); 
			Reporter.log("Element was not found"+by.toString());
		  }
}
    private void compare(String frststr, String scndstr) {
		try {
			Assert.assertEquals(frststr, scndstr);
		} 
		catch (AssertionError e) {
			System.out.println("Strings \n" + "'" + frststr + "'" + "\n and \n" + "'" + scndstr + "'" + "\n are not equal!");	
		}
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
}   
