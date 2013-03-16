package Team.Task;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Task12 {
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private FirefoxDriver driver = new FirefoxDriver();
	HomePage home = new HomePage(driver);

	@BeforeClass
	public void setUp() {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");
		home.SignIN(login, password);

	}
	public String[][] getTableArray(String xlFilePath, String sheetName,
			String tableName) throws Exception {
		String[][] tabArray = null;
		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol, ci, cj;
		Cell tableStart = sheet.findCell(tableName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();
		Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1,
				100, 64000, false);
		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();
		tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;
		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tabArray[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}
		return (tabArray);
	}
	@DataProvider(name = "data")
	public Object[][] createData1() throws Exception {
		Object[][] retObjArr = getTableArray(".\\src\\test\\data\\test.xls",
				"Data", "Test12");
		return (retObjArr);
	}
	@Test (groups = { "Orest" }, dataProvider = "data")
	public void LoginAc(String search, String search1, String search2) {
		
		home.sendText(By.xpath("//div[@class='search-field']/input"), search);
		driver.findElement(By.xpath(".//button[@type=\"submit\"]")).click();
		int count = home.countNewCategoty1();
		// System.out.println(count);
		// checking if 16GB is displayed in capture and technical
		for (int i = 1; i < count; i++) {
			String org = driver
					.findElement(
							By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["
									+ i + "]/tbody/tr/td[2]/div/a")).getText();
			// System.out.println(org);
			Assert.assertTrue(org.contains((search1)));
			String org1 = driver
					.findElement(
							By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["
									+ i + "]/tbody/tr/td[2]/p")).getText();
			Assert.assertTrue(org1.contains(search2));
		}
		int count1 = home.countNewCategoty1();
		System.out.println(count1);
		int[] array = new int[count1];
		for (int i = 0; i < count1; i++) {
			if (!home
					.isElementPresent1(By
							.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["
									+ (i + 1)
									+ "]/tbody/tr/td[1]/table/tbody/tr/td[1]/a"))) {
				array[i] = 0;
				continue;
			}
			String str = home
					.getElementText(By
							.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["
									+ (i + 1)
									+ "]/tbody/tr/td[1]/table/tbody/tr/td[1]/a"));
			array[i] = home.stringToInt(str);
		}
		int maxReviewValue = array[0];
		int maxReviewPos = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > maxReviewValue) {
				maxReviewValue = array[i];
				maxReviewPos = i;
			}
		}
		String org6 = driver
				.findElement(
						By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["
								+ (maxReviewPos + 1)
								+ "]/tbody/tr/td[1]/table/tbody/tr/td[1]/a"))
				.getText();
		int org8 = home.stringToInt(org6);
		// clicking on product name
		String max_review_prod = ".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["
				+ (maxReviewPos + 1) + "]/tbody/tr/td[2]/div/a";
		home.clickElement(By.xpath(max_review_prod));
		String org7 = driver.findElement(
				By.xpath(".//*[@itemprop='reviewCount']")).getText();
		int org9 = home.stringToInt(org7);
		// //checking if comments are the same
		if (org8 != org9) {
			System.out.println("INCORRECT");
		}
		driver.get("http://rozetka.com.ua");
	}

	@AfterClass
	public void ShutDown() {
		home.signOut();
		driver.close();
	}
}
