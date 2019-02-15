import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.PrintWriter;
import java.util.List;

public class WebDriver {

	public WebDriver() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.londonstockexchange.com/exchange/prices-and-markets/stocks/indices/summary/summary-indices-constituents.html?index=ASX");
		for (int j = 0; j < 32; j++) {
			for (int i = 0; i < 20; i++) {
				try {
					List<WebElement> elementList = driver.findElement(By.id("pi-colonna1-display")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
					String ticker = elementList.get(i).getText().split(" ")[0];
					if (ticker.contains(".")) {
						ticker = ticker.split("\\.")[0];
					}
					System.out.println(ticker);
					WebElement link = elementList.get(i).findElements(By.tagName("td")).get(1).findElement(By.tagName("a"));

					link.click();

					PrintWriter out = new PrintWriter("ftseallshare/" + ticker + ".html");
					String htmlText = driver.getPageSource();
					out.print(htmlText);
					out.close();

					driver.executeScript("window.history.go(-1)");

				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			driver.findElement(By.className("aligndx")).findElement(By.linkText("Next")).click();
		}
	}
}
