package com.serenitydojo.playwright.toolshop.fixtures;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.AfterEach;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;

public interface TakesFinalScreenshot {
	@AfterEach
	default void takeScreenshot(Page page) {
		System.out.println("TAKING FINAL SCREENSHOT");
    	ScreenshotManager.takeScreenshot(page, "Final Screenshot");
    }
}
