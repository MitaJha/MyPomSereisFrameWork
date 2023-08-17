package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This is used to initialize the driver.
	 * 
	 * @param browserName
	 * @return it returns driver
	 */

	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		//String browserName = System.getProperty("browser");
		System.out.println("browsername is : " + browserName);

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase()) {
		case "chrome":
			
//			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
//				//run tests on remote-grid
//				init_remoteDriver("chrome");
//			}else {
				//run tests on local
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				break;
		
			
		case "firefox":
			
//			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
//				//run tests on remote-grid
//				init_remoteDriver("firefox");
//			}else {
				//run tests on local
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				break;
			
			
			
		case "edge":
			
//			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
//				//run tests on remote-grid
//				init_remoteDriver("edge");
//			}else {
				//run tests on local			
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			
			break;
			
		case "safari":
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			System.out.println("Plz pass the right browser...." + browserName);
			break;
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
//	private void init_remoteDriver(String browserName) {
//		System.out.println("Running tests on GRID with browser: " + browserName);
//		try {
//			switch(browserName.toLowerCase()) {
//			case "chrome":
//				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
//				break;
//			case "firefox":
//				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
//				break;
//			case "edge":
//				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
//				break;
//			default:
//				System.out.println("Wrong browser info......can not run on remote machine...");
//				break;
//			}
//		}
//		catch(MalformedURLException e) {
//			e.printStackTrace();
//		}
//	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initialize the properties
	 * 
	 * @return
	 */

	public Properties initProp() {

		FileInputStream ip = null;
		prop = new Properties();

		// mvn clean install -Denv = "qa"
		// we can use any name for variable instead of env
		String envName = System.getProperty("env");
		System.out.println("env name is : " + envName);
		try {
			if (envName == null) {
				System.out.println("no env is given.....hence running it on QA env..by default");
				ip = new FileInputStream("./srcTestResources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./srcTestResources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./srcTestResources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./srcTestResources/config/dev.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./srcTestResources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./srcTestResources/config/config.properties");
					break;

				default:
					System.out.println("Plz pass the right env....." + envName);
					break;
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * take screenshot
	 * @return 
	 */
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}	
	
	
	
	
	
}
