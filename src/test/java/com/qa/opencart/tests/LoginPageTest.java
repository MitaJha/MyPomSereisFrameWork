package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100: Design of the login page for open cart app")
@Story("US - 200: implement login page feature for open cart app")
public class LoginPageTest extends BaseTest {

	@Description("login page title test....")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageTitleTest() {		
	String actTitle = loginPage.getLoginPageTitle();
	Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);	
	}	

//	@Description("login page URL test....")
//	@Severity(SeverityLevel.NORMAL)
//	@Test
//	    public void loginPageURLTest() {
//		String actURL = loginPage.getLoginPageURL();
//		System.out.println(actURL);
//		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
//	}
	
	@Description("Checking ForgotPassword Link available or not.")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());		
	}
	
	@Description("check user is able to login with valid credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password") );
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Description("checking test is getting failed or not")
	@Severity(SeverityLevel.MINOR)
	@Test(enabled = false)
	public void failureTest() {
		Assert.assertEquals(true, false);
	}
	
	
}
