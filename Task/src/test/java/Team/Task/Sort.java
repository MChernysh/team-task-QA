package Team.Task;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Sort  {
	private WebDriver driver;
	private String element;
	private int[] sortDataArray;
	private int size = 16;
	private boolean result;
	HomePage homePage;
	private HashMap<String, Action> sortArrayDataHashMap = new HashMap<String, Action>();
	public Sort(WebDriver driver) throws NoSuchMethodException, SecurityException {
		this.driver = driver;
		homePage = new HomePage(driver);
		sortArrayDataHashMap.put("от дешевых к дорогим", new Action() {
			public void callMethod() {
				getPriceArray("ask");
			}
		});
		sortArrayDataHashMap.put("от дорогих к дешевым", new Action() {
			public void callMethod() {
				getPriceArray("desc");
			}
		});

		sortArrayDataHashMap.put("последние добавленные", new Action() {
			public void callMethod() {
				getProductIdArray();
			}
		});
		sortArrayDataHashMap.put("популярные", new Action() {
			public void callMethod() {
				getTagArray("100px");
			}
		});
		sortArrayDataHashMap.put("акции", new Action() {
			public void callMethod() {
				getTagArray("59px");
			}
		});
		sortArrayDataHashMap.put("новинки", new Action() {
			public void callMethod() {
				getTagArray("78px");
			}
		});
	}

	public boolean checkSortBy(String sortParametr) throws Exception {
		// check and click to sort type list
		homePage.clickElement(By.xpath(".//*[@id='sort_view']/a"));
		// select sort parameter
		homePage.clickElement(By.xpath(".//*[text()='" + sortParametr + "']"));

		// wait for load of last element in list
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(".//*[@class='goods list']/table[" + size
						+ "]/descendant::div[@class='title']")));
		sortDataArray = new int[size + 1];
		sortArrayDataHashMap.get(sortParametr).callMethod();
		return result;
	}

	private boolean checkSortDataArray(String sortOrder) {
		for (int i = 1; i < size; i++) {
			if (sortOrder == "desc") {
				if (sortDataArray[i] < sortDataArray[i + 1])
					return false;

			} else if (sortDataArray[i] > sortDataArray[i + 1])
				return false;
		}
		return true;
	}

	public void getPriceArray(String sortOrder) {
		for (int i = 1; i <= size; i++) {
			element = (String)homePage.getTextFrom(By.xpath(".//*[@class='goods list']/table[" + i
					+ "]/descendant::div[@class='uah']"));
			String[] tokens;
			tokens = element.split(" ");
			for (int j = 0; j < tokens.length; j++) {
				if (Pattern.matches("\\d*\\d", tokens[j])) {
					sortDataArray[i] = Integer.parseInt(tokens[j]);
				}
			}
		}
		result = checkSortDataArray(sortOrder);
	}

	public void getProductIdArray() {
		for (int i = 1; i <= size; i++) {
			element = (String)homePage.getTextFrom(By.xpath(".//*[@class='goods list']/table[" + i
					+ "]/descendant::span[@class='code']"));
			String[] tokens;
			tokens = element.split(" ");
			for (int j = 0; j < tokens.length; j++) {
				if (Pattern.matches("\\d*\\d", tokens[j])) {
					sortDataArray[i] = Integer.parseInt(tokens[j]);
				}

			}
		}
		result = checkSortDataArray("desc");
	}

	public void getTagArray(String width) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		for (int i = 1; i <= size; i++) {
			if (driver.findElements(
					By.xpath(".//*[@class='goods list']/table[" + i + "]/descendant::i[@class='tag']")).size() == 0
					|| !driver.findElement(
							By.xpath(".//*[@class='goods list']/table[" + i + "]/descendant::i[@class='tag']"))
							.getCssValue("width").equals(width))
				sortDataArray[i] = 0;
			else
				sortDataArray[i] = 1;
		}
		result = checkSortDataArray("desc");
	}

}


