package com.testcase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.datatype.SetDataTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class NewTest {

	WebDriver driver = null;
	File file = null;
	FileInputStream fis = null;
	FileOutputStream fos = null;
	XSSFWorkbook Workbook = null;
	XSSFSheet Sheet = null;
	XSSFRow row = null;
	XSSFCell Cell = null;
	List<String> results = null;

	@BeforeTest
	public void init() {
		results = new ArrayList<String>();
	}

	@BeforeMethod
	@Parameters("browser")
	public void beforeMethod(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("Chrome");
			System.setProperty("webdriver.chrome.driver", "H:\\Software\\JARS\\driver\\chromedriver_win32_2.46.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.out.println("FireFox");
			System.setProperty("webdriver.gecko.driver", "H:\\Software\\JARS\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Not A Valid driver");
		}

		
		//driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://www.google.com");

	}

	@Test(dataProvider = "getData")
	public void f(String input, String time) {

		System.out.println("Data : " + input);
		WebElement search = driver.findElement(By.name("q"));
		search.sendKeys(input, Keys.ENTER);

		String result = driver.findElement(By.id("resultStats")).getText();
		System.out.println("My Result : " + result);

		//store the result in arraylist
		results.add(result);

	}

	@AfterTest
	public void SetDataTest() throws IOException {

		//prints the result
		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i));
		}

		int numberOfRows;
		String path = "C:\\Users\\Shankar\\eclipse-workspace-oxygen\\QK_TEST\\src\\com\\excel\\data.xlsx";

		file = new File(path);
		fis = new FileInputStream(file);
		Workbook = new XSSFWorkbook(fis);

		Sheet = Workbook.getSheet("input");
		numberOfRows = Sheet.getLastRowNum() + 1;
		for (int i = 1; i < numberOfRows; i++) {
			Sheet.getRow(i).getCell(1).setCellValue(results.get(i - 1));
		}

		fos = new FileOutputStream(path);
		Workbook.write(fos);

		fos.close();
		Workbook.close();
		fis.close();
	}

	@AfterMethod
	public void afterMethod() {
		driver.close();

	}

	@DataProvider
	public Object[][] getData() throws IOException {

		String pathname = "C:\\Users\\Shankar\\eclipse-workspace-oxygen\\QK_TEST\\src\\com\\excel\\data.xlsx";
		Object[][] arrayObject = getExcelData(pathname, "input");
		return arrayObject;
	}

	public Object[][] getExcelData(String path, String sheetName) throws IOException {

		String[][] arrayExcelData = null;
		file = new File(path);
		fis = new FileInputStream(file);
		Workbook = new XSSFWorkbook(fis);

		int numberOfRows, numberOfCols;
		Sheet = Workbook.getSheet(sheetName);
		numberOfRows = Sheet.getLastRowNum() + 1;

		row = Sheet.getRow(0);
		numberOfCols = row.getLastCellNum();

		arrayExcelData = new String[numberOfRows - 1][numberOfCols];

		for (int i = 1; i < numberOfRows; i++) {
			row = Sheet.getRow(i);
			for (int j = 0; j < numberOfCols; j++) {
				arrayExcelData[i - 1][j] = row.getCell(j).getStringCellValue();
			}
		}

		return arrayExcelData;
	}
}
