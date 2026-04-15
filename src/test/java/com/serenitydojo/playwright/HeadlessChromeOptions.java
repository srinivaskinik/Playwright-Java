package com.serenitydojo.playwright;

import org.assertj.core.util.Arrays;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class HeadlessChromeOptions implements OptionsFactory{

	@Override
	public Options getOptions() {
		return new Options()
				.setLaunchOptions(new BrowserType.LaunchOptions()
						.setArgs(java.util.Arrays.asList("--no-sandbox","--disable-extensions"))).setHeadless(true)
				.setTestIdAttribute("data-test");
		
		
	}

}
