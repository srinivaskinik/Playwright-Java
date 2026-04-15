package com.serenitydojo.playwright.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import io.qameta.allure.Step;

public class SearchComponent{
	private final Page page;
	
	public SearchComponent(Page page){
		this.page=page;
		
	}
	
	@Step("Search by keyword")
	public void searchBy(String keyword) {
		page.waitForResponse("**/products/search?**", () ->{
			page.getByPlaceholder("Search").fill(keyword);
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
		});
	}

	@Step("Search by filtername")
	public void filterBy(String filterName) {
		page.waitForResponse("**/products?**by_category=**", () ->{
			page.getByLabel(filterName).click();
		});
		
	}

	@Step("sort by by filter name")
	public void sortBy(String sortFilter) {
		page.waitForResponse("**/products?**sort=**", () ->{
			page.getByTestId("sort").selectOption(sortFilter);
		});
		
	}
	
	@Step("clear search")
	public void clearSearch() {
        page.waitForResponse("**/products**", () -> {
            page.getByTestId("search-reset").click();
        });
    }
}
