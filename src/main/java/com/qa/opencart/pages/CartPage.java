package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
	String product = "macbook";
	
	By product1 = By.id("laptop");
	
	public void selectProduct() {
		System.out.println("Click on macbook");
	}
			

}
