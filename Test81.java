package autotest;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.collect.ImmutableMap;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test81 {

	public static void main(String[] args) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enetr a word");
		String word=sc.nextLine();
		sc.close();
		WebDriverManager.chromedriver().setup();
		RemoteWebDriver driver=new ChromeDriver();
		//create DevTools session and set latitude and longitude values of desired location
		DevTools dt=((ChromiumDriver) driver).getDevTools();
		dt.createSession();
		((ChromiumDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride",ImmutableMap.of("lattitude",14.417340,"longitude",77.711430,"accuracy",1));
		driver.get("http://www.google.com");
		Thread.sleep(5000);
		driver.findElement(By.name("q")).sendKeys(word);
		Thread.sleep(5000);
		WebElement  cache=driver.findElement(By.xpath("(//div[@role='presentation']/ul[@role='listbox'])[1]"));
		Thread.sleep(5000);
		//get count of  suggestion items
		CacheUtilityClass obj=new CacheUtilityClass();
		System.out.println(obj.getItemsCount(cache));
		//get all items text
		List<String> suggestions=obj.getAllItemsText(cache);
		for(String suggestion:suggestions)
		{
			System.out.println(suggestion);
			
		}
		//verify cache
		if(obj.isValidCache(cache, word))
		{
			System.out.println("Test passed");
		}
		else
		{
			System.out.println("Test failed");
			List<String> mismatches=obj.getMismatchedSuggetions(cache,word);
			for(String mismatche:mismatches)
			{
				System.out.println(mismatche);
			}
		}
		driver.close();
	}



	}


