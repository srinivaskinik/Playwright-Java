package com.serenitydojo.playwright.toolshop.catalog;

import com.serenitydojo.playwright.toolshop.fixtures.PlaywrightTestCase;
import com.serenitydojo.playwright.toolshop.fixtures.TakesFinalScreenshot;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.serenitydojo.playwright.HeadlessChromeOptions;
import com.serenitydojo.playwright.toolshop.catalog.pageobjects.ProductList;
import com.serenitydojo.playwright.toolshop.catalog.pageobjects.SearchComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("Searching for products")

@Feature("Product Catalog")
@UsePlaywright(HeadlessChromeOptions.class)
public class SearchForProductsTest implements TakesFinalScreenshot{
	SearchComponent searchComponent;
	ProductList productList;
    @BeforeEach
    void openHomePage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        searchComponent = new SearchComponent(page);
        productList = new ProductList(page);
    }

    @Nested
    @DisplayName("Searching by keyword")
    @Story("Searching for products")
    class SearchingByKeyword {

        @Test
        @DisplayName("When there are matching results")
        void whenSearchingByKeyword() {


            searchComponent.searchBy("tape");

            var matchingProducts = productList.getProductNames();

            Assertions.assertThat(matchingProducts).contains("Tape Measure 7.5m", "Measuring Tape", "Tape Measure 5m");
        }

        @Test
        @DisplayName("When there are no matching results")
        void whenThereIsNoMatchingProduct() {
            
            searchComponent.searchBy("unknown");

            var matchingProducts = productList.getProductNames();

            Assertions.assertThat(matchingProducts).isEmpty();
            Assertions.assertThat(productList.getSearchCompletedMessage()).contains("There are no products found.");
        }

        @Test
        @DisplayName("When the user clears a previous search results")
        void clearingTheSearchResults() {
     
            searchComponent.searchBy("saw");

            var matchingFilteredProducts = productList.getProductNames();
            Assertions.assertThat(matchingFilteredProducts).hasSize(2);

            searchComponent.clearSearch();

            var matchingProducts = productList.getProductNames();
            Assertions.assertThat(matchingProducts).hasSize(9);
        }
    }
}