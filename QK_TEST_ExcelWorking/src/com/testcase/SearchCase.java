package com.testcase;

import org.testng.annotations.Test;

import com.google.common.collect.Table.Cell;

import org.testng.annotations.BeforeMethod;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;

public class SearchCase {

	WebDriver driver = null;
	File file = null;
	FileInputStream fis = null;
	FileOutputStream fos = null;
	XSSFWorkbook Workbook = null;
	XSSFSheet Sheet = null;
	XSSFRow row = null;
	XSSFCell Cell = null;

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", "H:\\Software\\JARS\\driver\\chromedriver_win32_2.46.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://www.google.com");
	}

	@Test
	public void mySearch() throws InterruptedException, IOException {

		String pathname = "C:\\Users\\Shankar\\eclipse-workspace-oxygen\\QK_TEST\\src\\com\\excel\\data.xlsx";
		file = new File(pathname);
		fis = new FileInputStream(file);
		Workbook = new XSSFWorkbook(fis);
		Sheet = Workbook.getSheet("input");

		//number of rows
		int rows, cols;
		rows = Sheet.getLastRowNum() + 1;
		System.out.println("Number of Rows : " + rows);

		//Number of col
		row = Sheet.getRow(1);
		cols = row.getLastCellNum();
		System.out.println("Number of Cell : " + cols);

		int cellType;

		String result;
		List<String> listResult = new ArrayList<String>();

		WebElement search = null;
		//fetch the data and pass it to the driver for execution
		for (int i = 1; i < (rows); i++) {
			row = Sheet.getRow(i);
			for (int j = 1; j < (cols); j++) {
				Cell = row.getCell(j);
				//cellType = Cell.getCellType();
				//System.out.println(Cell.getStringCellValue());
				search.sendKeys(Cell.getStringCellValue(), Keys.ENTER);
				Thread.sleep(3000);
				search = driver.findElement(By.name("q"));
				result = driver.findElement(By.id("resultStats")).getText();
				System.out.println("My Result : " + result);
				//listResult.add(result);
				//driver.get("http://www.google.com");
				//Thread.sleep(2000);

			}
			System.out.println();

		}

		/*String result = driver.findElement(By.id("resultStats")).getText();
		System.out.println("Execuiton Time is : " + result);*/

	}

	@AfterMethod
	public void afterMethod() {
		//driver.close();
	}

}
