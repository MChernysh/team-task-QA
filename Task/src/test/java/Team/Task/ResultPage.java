package Team.Task;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultPage {
	
	private WebDriver driver;
	
	ResultPage(WebDriver driver){
		
		this.driver = driver;
	}
	
	String getElementText(By by){
		
		return driver.findElement(by).getText();
	}
	
	public void clickElement(By by){
		
		driver.findElement(by).click();
	}
	
	public void sendText(By by, String text) {
		
		driver.findElement(by).sendKeys(text);
	}

	public int stringToInt(String str){
	
	Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
         int res = 0;
         if (m.find()) res = Integer.valueOf(m.group(1));
        return res;
	}

	public boolean isElementPresent(By by) {
	List<WebElement> act = driver.findElements(by);
	
	if (!act.isEmpty()) 
		return true; 
	else 
		return false;

	}
	
	public void clearTextBox(By by){
		
		driver.findElement(by).clear();
	}
	
	public void makeScreenshot(String filename){
		
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	
		try {
			FileUtils.copyFile(file, new File(filename));
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
	}
}
