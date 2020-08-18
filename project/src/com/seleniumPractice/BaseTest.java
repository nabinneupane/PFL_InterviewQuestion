package com.seleniumPractice;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;  


/**
 * @author : Nabin Neupane
 *
 *
 * @date:	 Aug 16, 2020 
 **/

public class BaseTest {
	
	public  WebDriver driver; 
	
	
	
	/** 
	 * This method finds and return the text that is displayed after 
	 * search button is clicked
	 * 
	 * @return a string
	 */
	
	public String derivePTagFunction()
	{
		
		/*
		 * Since the text displayed after click does not have any id or class so matched with P tag. 
		 *  There are two P tag on page  and the second one is the needed one. 
		 *  Thus storing on array and retrieving by index 
		 * */
		
		List<WebElement> pTag = driver.findElements(By.tagName("p"));
		
		String a=pTag.get(1).getText(); 
		
		return a ;	
	}
	
	
	/** 
	 * This method compares if the expected text match with the actual text in UI
	 * Calls derivePTagFunction() that returns actual string
	 * calls checkOnClicked() that compares if two text are same. 
	 * 
	 * @param text an input that is entered on the input field in the UI
	 * 
	 * @throws InterruptedException caused by thread sleep.
	 */
	
	public void checkEnteredString(String text) throws InterruptedException
	{
		String expectedText="", actualText; 
		
		
		
		int count =0; 
	
		String checkText = text.replaceAll("[^\\d]+", " ");
		
		
		if(checkText.length()==0)
			expectedText="Please provide a string.";
		else 
		{
			String[] arr= checkText.split(" "); 
		
			while(count <= (arr.length-2))
			{
				if (arr[count].equals("1"))
				{
					if(arr[count+1].equals("2")) {
					
						System.out.println(arr[count+1]);
						if(arr[count+2].equals("3"))
						{
							expectedText = "True: The text does contain the integers 1 2 3 in this order.";
							break;
						}
						else 
							count +=3;
						
					}
					else 
						count+=2;
					
				}
				else 
					count +=1;
				
			
				expectedText= "False: The text does not contain the integers 1 2 3 in this order.";
				
			}
			
		}
		
	
		
		Thread.sleep(1000);
		
		Assert.assertTrue(driver.findElement(By.id("searchtext")).getText().isEmpty());
		
		System.out.println("=====================");
		
		System.out.println("\n\t The text field is empty now ... sending test case ....");
		
		driver.findElement(By.id("searchtext")).sendKeys(text);
		
		Thread.sleep(500);
		
		driver.findElement(By.id("searchbutton")).click();
		
		Thread.sleep(500);
		
		
		actualText = derivePTagFunction();
		
		
		System.out.println("\n\t Testing for input: " + text + "\n");
		System.out.println("\n\t Result got: " + "\t " + expectedText);
		System.out.println("\n\t Output on UI: " + "\t " + actualText);
		
		checkOnClicked(expectedText,actualText);
		
		Thread.sleep(1000);
	
		
	}
	
	
	/**
	 * This method checks for the test case. 
	 * Assert statement is used for testing 
	 * 
	 * @param expected string generated by logic given.
	 * @param actual string generated in UI after search button is clicked.
	 */
	public void checkOnClicked(String expected, String actual)
	{
		Assert.assertEquals(expected, actual);
		
		System.out.println("\n\t Assert Passed.");
		System.out.println("\n\t The output is rightly displayed.....\n ");
		
	
		driver.findElement(By.id("searchtext")).clear();
		
		driver.findElement(By.id("searchbutton")).click();
	}
	
	
	/**
	 * 
	 * @throws InterruptedException caused by thread sleep 
	 */
	
	@Before
	public void launchBrowserAndNavigateToURL() throws InterruptedException
	{
		System.out.println("\n\t Opening Web Browser.......");	
		
		System.setProperty("webdriver.gecko.driver", "/Users/coolnabinn/Desktop/Project/Driver/geckodriver");
		
		driver = new FirefoxDriver();
			
		driver.get("https://sdetassessment.azurewebsites.net/");
		
		System.out.println("\n\t Success.....");
		
		Thread.sleep(2000);
		
		//click on "Find '1 2 3'" present in navigation bar
		 driver.findElement(By.linkText("Find '1 2 3'")).click(); 
	}
	
	
	/**
	 * Test case to check if test case can be reached or not
	 */
	
	@Test
	public void checkIfURLIsTrue() {
		
		 String expectedURL = "https://sdetassessment.azurewebsites.net/greeting";
		 
		 String actualURL = driver.getCurrentUrl();
		 
		 System.out.println("\n\t checking URL.......");
		 
		 System.out.println("\n\t Assert check.....");
		 Assert.assertEquals(expectedURL, actualURL);
		
		Assert.assertTrue(driver.findElement(By.className("input-group-text")).getText().equals("Text to search"));
		
		Assert.assertTrue(	driver.findElement(By.id("searchtext")).getText().isEmpty());
		
		System.out.println("\n\t Assert passed"); 
	}
	
	
	
	

	
	
	/**
	 * 
	 * Test case to check if The test page functions as specified on the UI
	 * 
	 * @throws InterruptedException  caused by thread sleep
	 */
	
	@Test
	public void checkCorrectTextEnteredInTheField() throws InterruptedException
	{
		String testcase;
		
		/* If the text contains the integers 1 2 3 in this order, 
		 * the text right under the box will display 
		 * “True: The text does contain the integers 1 2 3 in this order."
		 * */
		
		//1. 
		 		testcase = "1 2,3"; 
		 		checkEnteredString(testcase);
		
		//2. 
				testcase= "1,2,3";
				checkEnteredString(testcase); 
		
		//3. 
				testcase= "1 2 3";
				checkEnteredString(testcase); 
		
		//4.
				testcase ="1 1 1 2 2 3 3 3 1 2 3";
				checkEnteredString(testcase);
				
		//5. 
				testcase= "1, 2,  3, a b c d";
				checkEnteredString(testcase); 
		
		//5. 
				testcase= "8, 9 , 10, 1, 2,  3, 2";
				checkEnteredString(testcase); 
		//6. 
				testcase ="1 1  1 2 3"; 
				checkEnteredString(testcase);
				
		
		/* If the text does not contain 1 2 3 as specified above,
		 * the text should read 
		 * “False: The text does not contain the integers 1 2 3 in this order.” 
		 * */
		
		//6. 
				testcase ="1 1 1 2 2 3 3 3";
				checkEnteredString(testcase);
		
		//7. 
				testcase ="4 5 6 7 s";
				checkEnteredString(testcase);
				
		//8. 
				testcase ="1 2 1 2 1 3 2 1"; 
				checkEnteredString(testcase);
				
		//9. 
				testcase ="1, a, b , , 3 "; 
				checkEnteredString(testcase);
				
		//10. 
				testcase =",,, 123"; 
				checkEnteredString(testcase);
				
		//10. 
				testcase ="1 1 2 3"; 
				checkEnteredString(testcase);
		//11. 
				testcase ="1 2 1 2 3 3 2 1";
				checkEnteredString(testcase);
				
				
		//11. 
				testcase ="4 5 6 1222 --- 123";
				checkEnteredString(testcase);
				
				
		/* And a logically empty input should yield a “Please provide a string.”
		 * 
		 * */
				
		//15. 
			testcase =""; 
				checkEnteredString(testcase);
	}
	
	
	
	/**
	 * Method to close the driver after all test case is checked. 
	 * 
	 * @throws InterruptedException caused by thread sleep
	 */
	
	@After
	public  void closeDriver() throws InterruptedException
	{
		
		Thread.sleep(1500);
		
		driver.close();
		
		System.out.println(" \n\n\t  ########## Driver Closed... #################");
	}

}
