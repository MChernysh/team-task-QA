package Team.Task;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class Sort {
	private WebDriver driver;
	public Sort(WebDriver driver) {

		this.driver = driver;
	}
	public boolean sortBy(String sortParametr){

		int[] n;
		//check and click to sort type list 
		isElementPresent(By.xpath(".//*[@id='sort_view']/a"));
		driver.findElement(By.xpath(".//*[@id='sort_view']/a")).click();

		//select sort parameter
		isElementPresent(By.xpath(".//*[text()='"+sortParametr+"']"));
		driver.findElement(By.xpath(".//*[text()='"+sortParametr+"']")).click();

		String element;

		//wait for load of last element in list
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath(".//*[@class='goods list']/table[16]/descendant::div[@class='title']")));

		//get size of list
		int size = driver.findElements(By.xpath(".//*[@class='goods list']/table/descendant::div[@class='title']")).size();
		n = new int[size+1];

		//sorting by price
		if(sortParametr=="от дешевых к дорогим"||sortParametr=="от дорогих к дешевым"){
			for(int i = 1; i<=size;i++){
				isElementPresent(By.xpath(".//*[@class='goods list']/table["+i+"]/descendant::div[@class='uah']"));
				element = (String) driver.findElement(
						By.xpath(".//*[@class='goods list']/table["+i+"]/descendant::div[@class='uah']")).getText();
				String[] tokens;
				tokens = element.split(" ");
				for (int j = 0; j < tokens.length; j++) {

					if (Pattern.matches("\\d*\\d", tokens[j])) {
						n[i] = Integer.parseInt(tokens[j]);
					}
				}
			}
			//sorting by date
		}else if(sortParametr=="последние добавленные"){
			for(int i = 1; i<=size;i++){
				isElementPresent(By.xpath(".//*[@class='goods list']/table["+i+"]/descendant::div[@class='goods_id']"));
				element = (String) driver.findElement(
						By.xpath(".//*[@class='goods list']/table["+i+"]/descendant::span[@class='code']")).getText();
				String[] tokens;
				tokens = element.split(" ");
				for (int j = 0; j < tokens.length; j++) {
					if (Pattern.matches("\\d*\\d", tokens[j])) {
						n[i] = Integer.parseInt(tokens[j]);
					}
				}
			}
			//sorting in all other cases
		}else{ 
			String width = null;
			switch(sortParametr){
			case "популярные" : width = "100px";break;
			case "акции" : width = "59px";break;
			case "новинки" : width = "78px";break;
			}
			//System.out.println(width);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
			for(int i = 1; i<=size;i++){
				if(driver.findElements(
						By.xpath(".//*[@class='goods list']/table["+i+"]/descendant::i[@class='tag']")).size()==0 ||
						!driver.findElement(
								By.xpath(".//*[@class='goods list']/table["+i+"]/descendant::i[@class='tag']"))
								.getCssValue("width").equals(width))
					n[i]=0;
				else n[i]=1;

			}

		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//validate if sorting by price increases
		if(sortParametr=="от дешевых к дорогим"){
			for(int i= 1; i <size;i++){
				if(n[i]>n[i+1])return false;
			}
			return true;
		}
		//validate in all other cases
		else{
			for(int i= 1; i <size;i++){
				if(n[i]<n[i+1])return false;
			}
			return true;
		}

	}

	public boolean isElementPresentbl(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	private void isElementPresent(By by) {
		List<WebElement> act = driver.findElements(by);
		if (act.isEmpty()){
			Reporter.log("Element was not found in class 'RozetkaHomePage' - "+by.toString()); 
			driver.quit();
		}
	}


}
