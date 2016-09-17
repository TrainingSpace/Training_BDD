package com.accenture.cucumber.Training_BDD.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.accenture.cucumber.Training_BDD.CommonMethods.StartWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Juliano on 9/16/2016.
 */
public class AC03_ScenarioOutline_StepDefinitions {

    WebDriver driver;

    @Given("^the user is browsing ebay home page$")
    public void theUserIsBrowsingEbayHomePage() throws Throwable {
        driver = StartWebDriver();
        driver.get("http://www.ebay.com");
    }

    @When("^the user navigates to \"([^\"]*)\" category$")
    public void theUserNavigatesToCategory(String category) throws Throwable {
        driver.findElement(By.linkText(category)).click();
    }

    @Then("^they should that \"([^\"]*)\" is displayed in the page title$")
    public void theyShouldThatIsDisplayedInThePageTitle(String pageTitle) throws Throwable {
        assertThat(driver.getTitle().contains(pageTitle));
        driver.quit();
    }
}
