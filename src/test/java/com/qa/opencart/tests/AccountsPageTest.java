package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("EPIC - 101: Design of the Accounts page for open cart app")
@Story("US - 201: implement Accounts page feature for open cart app")
public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password") );
	}
	
	
	@Test
	public void accPageTitleTest() {
		String actAccPageTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actAccPageTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void logOutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());		
	}
	
	@Test
	public void accPageHeadersCountTest() {
		int actAccPageHeadersCount = accPage.getAccountsPageHeaderCount();
		System.out.println("Actual Acc Page Headers Count = "+ actAccPageHeadersCount);
		Assert.assertEquals(actAccPageHeadersCount, AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
		
		}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actAccPageHeadersList = accPage.getAccountsPageHeader();
		Assert.assertEquals(actAccPageHeadersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getSearchKey(){
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2}
			
		};
	}
	
	@Test(dataProvider = "getSearchKey")
	public void searchTest(String searchKey, int productCount) {
		searchResPage = accPage.doSearch(searchKey);
		int acResultsCount = searchResPage.getSearchResultsCount();
		Assert.assertEquals(acResultsCount, productCount );
	}
	
	
	
	

}
