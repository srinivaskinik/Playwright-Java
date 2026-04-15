package com.serenitydojo.playwright.toolshop.fixtures;

import java.io.ByteArrayInputStream;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;

public class ScreenshotManager {
	public static void takeScreenshot(Page page, String name) {
    	var screenshot=page.screenshot(
    			new Page.ScreenshotOptions().setFullPage(true));
    	Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }
}
