package Team.Task;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Task3 {

	private FirefoxDriver driver = new FirefoxDriver();

	HomePage home = new HomePage(driver);

	float f;
	float f2;
	

	@Test
	public void task3_Test() {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://rozetka.com.ua");

		String delims = "[ ]+";
		String[] tokens;
		String element = (String) driver.findElement(
				By.xpath("//div[@id='currencies'][1]")).getText();
		//System.out.println("TEST STRING" + element);

		tokens = element.split(delims);
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equalsIgnoreCase("8.2")) {
				//System.out.println("Tokens check - " + tokens[i]);
				f = Float.parseFloat(tokens[i]);
				System.out.println("Значення курсу долара США на сайті Розетки - " + f + " грн за долар США");
			}
		}
		   
		
		//make a screenshot and save it to the project's directory
		 File file =
				 ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 			try {
		 				FileUtils.copyFile(file, new File("Task_3 - Screenshot.png"));
		 			}
		 			catch (IOException e)
		 			{
		 				e.printStackTrace(); }
	

			{driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://bank.gov.ua/control/uk/index");

		String delims1 = "[ ]+";
		String[] tokens1;
		String element1 = (String) driver.findElement(
			By.xpath("html/body/table[1]/tbody/tr/td[2]/table/tbody/tr/td[3]/div[4]/table/tbody/tr/td/div[1]/table/tbody/tr[1]/td[2]")).getText();
		//System.out.println("TEST STRING" + element1);

	
		tokens1 = element1.split(delims1);
		for (int i = 0; i < tokens1.length; i++) {
			if (tokens1[i].equalsIgnoreCase("799.3000")) {
				//System.out.println("Tokens check - " + tokens1[i]);
				float f1 = Float.parseFloat(tokens1[i]);
				f2 = f1/100;
				System.out.println("Значення курсу долара США на сайті Нацбанку - " + f2 + " грн за долар США");
		
		}
	}
	
	}
		//make a screenshot and save it to the project's directory
		File file1 =
				((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);{
					try {
						FileUtils.copyFile(file1, new File("Task_3 - Screenshot1.png"));
					}
					catch (IOException e)
					{
					e.printStackTrace(); }
			
		
	if (f == f2){
	System.out.println("Значення курсів долара США на сайтах Розетки і Нацбанку рівні");
		  }
					else if(f > f2){
		            float f3 = f-f2;
		        	System.out.println("Курс на Розетці  вищий від курсу Нацбанку на " + f3 + " грн за долар США");
		        	}
				}		                	        
	}	 
	 
	
		 public void tearDown(){
		
		 driver.close();
	}
}


