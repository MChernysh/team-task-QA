package Team.Task;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Task12 {
	private String login = "testatqc@gmail.com";
	private String password = "IF-025.ATQC";
	private String search = "IPAD 16GB";
	private FirefoxDriver driver = new FirefoxDriver();
    HomePage home = new HomePage(driver);
	
	
	@BeforeClass
	public void setUp(){
	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    driver.get("http://rozetka.com.ua");
	home.SignIN(login, password);
	home.clickElement(By.xpath(".//a[@name=\"close\"]"));	

	}	

@Test(groups = { "Orest" })	
public void LoginAc(){
home.sendText(By.xpath("//div[@class='search-field']/input"),search);
ResultPage result= home.clickElement(By.xpath(".//button[@type=\"submit\"]"));
int count=home.countNewCategoty1();
//System.out.println(count);
//checking if 16GB is displayed in capture and technical 
for (int i = 1;i<count;i++){	
String org= driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["+i+"]/tbody/tr/td[2]/div/a")).getText();
//System.out.println(org);
Assert.assertTrue(org.contains(("16GB"))); 
String org1=driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["+i+"]/tbody/tr/td[2]/p")).getText();
Assert.assertTrue(org1.contains("16 ГБ")); 
}

//picking the product with max comments
int count1=home.countNewCategoty1();
//System.out.println(count1);
int[] array = new int[count1];
for(int i = 0; i < count1; i++) {	
	
	if(!result.isElementPresent(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["+ (i+1) +"]/tbody/tr/td[1]/table/tbody/tr/td[1]/a"))){
		array[i] = 0;
		i +=1;
	} 
	
	String str = result.getElementText(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["+ (i+1)+"]/tbody/tr/td[1]/table/tbody/tr/td[1]/a")); 																				 
	array[i] = result.stringToInt(str);
}

int maxReviewValue = array[0];
int maxReviewPos = 0;
for (int i = 1; i < array.length; i++) {
       
	if (array[i] > maxReviewValue) {
		maxReviewValue = array[i]; 
		maxReviewPos = i;
		
    }
}	
String org6=driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table[13]/tbody/tr/td[1]/table/tbody/tr/td[1]/a")).getText();
//clicking on product name
String max_review_prod = ".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div/div[3]/table["+(maxReviewPos+1)+"]/tbody/tr/td[2]/div/a";
result.clickElement(By.xpath(max_review_prod));
String org7=driver.findElement(By.xpath(".//*[@id='head_banner_container']/div[2]/div/div[1]/div/div[2]/div[2]/div[2]/div[2]/table/tbody/tr/td[3]/div/a")).getText();
//checking if comments are the same 
if(org6.equals(org7)){
	System.out.println("INCORRECT");
	//clicking on comments
	result.clickElement(By.xpath(".//*[@id='tabs']/li[5]/a"));
	home.signOut();
}

}
@AfterClass
public void ShutDown()		{
	driver.quit();
}

}

