package com.serenitydojo.playwright.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ProductDetails{
	private final Page page;
	public ProductDetails(Page page){
		this.page=page;
	}
	public void addToCart() {
		//https://api.practicesoftwaretesting.com/carts/01knqzem990wvbnf61qr7yc72
		page.waitForResponse(response -> response.url().contains("/carts") &&
				response.request().method().equals("POST"), () ->{
					page.getByText("Add to cart").click();
					page.getByRole(AriaRole.ALERT).click();
				});
		 
		
	}
	public void increaseQuantityBy(int increment) {
		for(int i=1; i<=increment;i++) {
			page.getByTestId("increase-quantity").click();
		}
		 
          
		
	}
}