package com.serenitydojo.playwright.toolshop.login;

import java.util.function.IntPredicate;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.serenitydojo.playwright.toolshop.domain.User;

import io.qameta.allure.Step;

public class LoginPage {
	
	private final Page page;
	
	public LoginPage(Page page) {
		this.page=page;
	}

	@Step("Open Home Page")
	public void open() {
		page.navigate("https://practicesoftwaretesting.com/auth/login");
		
	}

	@Step("Login As user")
	public void loginAs(User user) {
		page.getByPlaceholder("Your email").fill(user.email());
		page.getByPlaceholder("Your password").fill(user.password());
		page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName("Login")).click();
		
	}

	@Step("Get the page title")
	public String title() {
		return page.getByTestId("page-title").textContent();
	}

	@Step("Get the login error content")
	public String loginErrorMessage() {
		return page.getByTestId("login-error").textContent();
	}

}
