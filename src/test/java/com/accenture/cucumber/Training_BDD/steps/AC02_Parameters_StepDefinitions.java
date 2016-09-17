package com.accenture.cucumber.Training_BDD.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.accenture.cucumber.Training_BDD.CommonMethods.StartWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Fernanda on 9/16/2016.
 */
public class AC02_Parameters_StepDefinitions {

    WebDriver driver;

    @Given("^the user navigates to ebay home page$")
    public void theUserNavigatesToEbayHomePage() throws Throwable {
        driver = StartWebDriver();
        driver.get("http://www.ebay.com");
    }

    @When("the user selects \"([^\"]*)\" category")
    public void theUserSelectsCategory(String category) throws Throwable {
        driver.findElement(By.linkText(category)).click();
    }

    @Then("they should see \"([^\"]*)\" in the page title")
    public void theyShouldSeeInThePageTitle(String pageTitle) throws Throwable {
        assertThat(driver.getTitle().contains(pageTitle));
        driver.quit();
    }
}
