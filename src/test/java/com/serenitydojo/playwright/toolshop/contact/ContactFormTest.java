package com.serenitydojo.playwright.toolshop.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.serenitydojo.playwright.toolshop.catalog.pageobjects.NavBar;
import com.serenitydojo.playwright.toolshop.fixtures.PlaywrightTestCase;

import io.qameta.allure.Story;


public class ContactFormTest extends PlaywrightTestCase{
	ContactForm contactForm;
	NavBar navigate;
	
    @BeforeEach
    void openContactFormPage() {
        
        contactForm=new ContactForm(page);
        navigate= new NavBar(page);
        navigate.toTheContactPage();
    }
    @DisplayName("Cusutomers can use the contact form to contact us")
    @Test
	public void completeForm() throws URISyntaxException {
		page.navigate("https://practicesoftwaretesting.com/contact");
		
		contactForm.setFirstName("Sara Jones");
		contactForm.setLastName("Smith");
		contactForm.setEmail("test@gmail.com");
		contactForm.setMessage("Hello");
		contactForm.setSubject("Warranty");
		
		Path fileToUpload = Paths.get(ClassLoader.getSystemResource("sample.txt").toURI());
        contactForm.setAttachment(fileToUpload);

        contactForm.submitForm();

        Assertions.assertThat(contactForm.getAlertMessage())
                .contains("Thanks for your message! We will contact you shortly.");
		
	}
    
    @Story("Contact form")
    @DisplayName("First name, last name, email and message are mandatory")
    @ParameterizedTest(name = "{arguments} is a mandatory field")
    @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
    void mandatoryFields(String fieldName) {
        // Fill in the field values
        contactForm.setFirstName("Sarah-Jane");
        contactForm.setLastName("Smith");
        contactForm.setEmail("sarah@example.com");
        contactForm.setMessage("A very long message to the warranty service about a warranty on a product!");
        contactForm.setSubject("Warranty");

        // Clear one of the fields
        contactForm.clearField(fieldName);

        contactForm.submitForm();

        // Check the error message for that field
        var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");

        PlaywrightAssertions.assertThat(errorMessage).isVisible();
    }

    @Story("Contact form")
    @DisplayName("The message must be at least 50 characters long")
    @Test
    void messageTooShort() {

        contactForm.setFirstName("Sarah-Jane");
        contactForm.setLastName("Smith");
        contactForm.setEmail("sarah@example.com");
        contactForm.setMessage("A short long message.");
        contactForm.setSubject("Warranty");

        contactForm.submitForm();

        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT).last()).hasText("Message must be minimal 50 characters");
    }

    @Story("Contact form")
    @DisplayName("The email address must be correctly formatted")
    @ParameterizedTest(name = "'{arguments}' should be rejected")
    @ValueSource(strings = {"not-an-email", "not-an.email.com", "notanemail"})
    void invalidEmailField(String invalidEmail) {
        contactForm.setFirstName("Sarah-Jane");
        contactForm.setLastName("Smith");
        contactForm.setEmail(invalidEmail);
        contactForm.setMessage("A very long message to the warranty service about a warranty on a product!");
        contactForm.setSubject("Warranty");

        contactForm.submitForm();

        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT).first()).hasText("Email format is invalid");
    }

}
