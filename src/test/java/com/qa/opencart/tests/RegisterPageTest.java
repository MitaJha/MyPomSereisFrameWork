package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regSetup() {
		regPage = loginPage.navigateToRegisterPage();
	}
	
	
	public String getRandomEmailId() {
		return "openauto"+System.currentTimeMillis()+"@open.com";
	}
	
	
	
	@DataProvider
	public Object[][] getUserRegData() {
		return new Object[][] {
			{"Pooja", "agrawal", "9090909090", "pooja@123", "yes"},
			{"Shubham", "gupta", "9090909011", "shubh@123", "yes"},
			{"Mita", "jha", "9895555552", "mitaj@123", "yes"},
		};
	}
	
	@DataProvider
	public Object[][] getUserRegSheetData() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}	
	
	
	@Test(dataProvider = "getUserRegData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertEquals(regPage.registerUser(firstName, lastName, getRandomEmailId(), telephone,  password,  subscribe), false);
		
	}
	
	
	

}
