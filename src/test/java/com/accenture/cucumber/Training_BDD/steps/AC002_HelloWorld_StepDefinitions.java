package com.accenture.cucumber.Training_BDD.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import static com.accenture.cucumber.Training_BDD.CommonMethods.StartWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Fernanda on 9/16/2016.
 */
public class AC002_HelloWorld_StepDefinitions {

    WebDriver driver;

    @Given("the user navigates to Google home page")
    public void theUserIsOnTheGoogleHomePage() throws Throwable {

        driver = StartWebDriver();
        driver.get("http://www.google.com");

    }

    @When("the user performs a search for '(.*)'")
    public void theUserPerformsASearchByKeyword(String keywords) {

        driver.findElement(By.name("q")).sendKeys(keywords, Keys.ENTER);

    }

    @Then("they should see that the search was performed")
    public void theyShouldSeeThatTheSearchWasPerformed() {

        assertThat(driver.getTitle().contains("Google Search"));
        driver.quit();

    }

}
