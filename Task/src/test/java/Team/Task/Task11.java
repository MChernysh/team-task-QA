package Team.Task;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook; 

public class Task11 {

	private WebDriver driver= new FirefoxDriver();
	HomePage homepage = new HomePage(driver);

	@BeforeClass 
	public void settingUp() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
	}

	
	@AfterClass
	public void turnDown() {
		driver.quit();
	}

	
	@DataProvider(name = "ExcelData")
    public Object[][] createData() throws Exception{
        Object[][] retObjArr=getTableArray(".\\src\\test\\data\\test.xls", "Data", "Test11");
        return(retObjArr);
    }
	
	
	@Test(dataProvider = "ExcelData") 
	public void LogIn(String Login, String Password) {
		driver.findElement(By.name("signin")).click();
	  	driver.findElement(By.name("login")).clear();
		driver.findElement(By.name("login")).sendKeys(Login);
		driver.findElement(By.name("password")).clear(); 
		driver.findElement(By.name("password")).sendKeys(Password);		
		driver.findElement(By.xpath("//*[text()='Войти']")).click();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		if (driver.findElements(By.name("signout")).size() != 0) {
			Reporter.log("Account with login  " + Login + "  and password  " + Password +"  exists!");			
			driver.findElement(By.name("signout")).click();
			}
			else if (driver.findElements(By.xpath("//div[@class='message code4']")).size() != 0) {
					Reporter.log("Account with login  " +  Login + "  is  not registered!");					
					driver.findElement(By.xpath("//*[text()='Отмена']")).click();
					}
					else if (driver.findElements(By.xpath("//div[@class='message code5']")).size() != 0) {
							Reporter.log("Password  " + Password + "  for login  " + Login + "  is incorrect!");							
							driver.findElement(By.xpath("//*[text()='Отмена']")).click();
					}
		
	}
	
	
	@Test(groups = { "Dima" })
	public void task11() {
		
		homepage.SignIN("testatqc@gmail.com", "IF-025.ATQC");
		//Select "Computers" from main menu
		driver.findElement(By.xpath(".//*[@id='computers-notebooks']")).click();

		
		//Select "Ultrabooks"
		driver.get("http://rozetka.com.ua/notebooks/c80004/filter/preset=light/");
		// driver.findElement(By.linkText("Ультрабуки")).click();
		// driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div/div[1]/div/div[4]/div/div[1]/div[1]/ul/li[4]/a")).click();

		
		//Check if product's name and tooltip ("alt") are equal
		String tooltip = driver.findElement(By.xpath(".//*[@id='image_item255352']/a/img")).getAttribute("alt");
		String name = driver.findElement(By.xpath(".//*[@id='image_item255352']/a/img")).getAttribute("title");
		compare(name, tooltip);

		
		//show products as a 'List'
		driver.findElement(By.linkText("списком")).click();
		// driver.get("http://rozetka.com.ua/notebooks/c80004/filter/preset=light;view=list/");
		// driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div/div[2]/div/div[3]/div[2]/a")).click();

		
		//Select the third notebook from top list
		String topthree = driver.findElement(By.xpath("//div[@class='item available popular3']//div[@class='title']")).getText();
		System.out.println("The third element in Top is: " + topthree);
		Reporter.log("The third element in Top is: " + topthree);
		
		//Move mouse to the third element in top list
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[@class='item available popular3']"))).build().perform();		
		
		//Copy its 'href'
		String href = driver.findElement(By.xpath("html/body/div[6]/div/div/div[1]/div/a")).getAttribute("href");
		
		//Check the popup and show its contents 
		driver.findElement(By.xpath("html/body/div[6]/div/div/div[1]/p")).getText();
//		System.out.println("Popup window contains such information: " + "\n" + "'" + popup + "'");		
		
		//Check the hyperlink in popup and show its content
		driver.findElement(By.xpath("html/body/div[6]/div/div/div[1]/div/a")).getText();
//		System.out.println("Hyperlink exists!");
		Reporter.log("Hyperlink exists!");
		
		//Make screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File("./target/screenshots/Screenshot_task11.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		
		//Load the page (the third element in top list)
		driver.get(href);
		//Compare product's names in previous and new page 
		String prdctname = driver.findElement(By.xpath(".//*[@id='head_banner_container']//h1")).getText();		
		compare(prdctname,topthree);
		

		//Check the USD price
		String usd = driver.findElement(By.xpath(".//*[@id='head_banner_container']//div/span/div")).getText();
//		System.out.println("The price is: " + usd);
		Reporter.log("The price is: " + usd);
	}
	
	
	
	private void compare(String frststr, String scndstr) {
		try {
			Assert.assertEquals(frststr, scndstr);
		} 
		catch (AssertionError e) {
//			System.out.println("Strings \n" + frststr + "\n and \n" + scndstr + "\n are not equal!");
			Reporter.log("Strings \n" + frststr + "\n and \n" + scndstr + "\n are not equal!");
		}
	}
	
	
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName){
        String[][] tabArray=null;
        try{
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow,startCol, endRow, endCol,ci,cj;
            Cell tableStart=sheet.findCell(tableName);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();

            Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                               

            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();
//          System.out.println("startRow=" + startRow + ", endRow=" + endRow + ", " + 
//                  "startCol=" + startCol + ", endCol=" + endCol);
            Reporter.log("startRow=" + startRow + ", endRow=" + endRow + ", " + 
                    "startCol=" + startCol + ", endCol=" + endCol);
            tabArray=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++){
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++){
                    tabArray[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
        }
        catch (Exception e) {
//          System.out.println("Error in getTableArray()!");
            Reporter.log("Error in getTableArray()!");
        }

        return(tabArray);
    }


		
}
