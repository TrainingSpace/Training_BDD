package com.accenture.cucumber.Training_BDD.steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.accenture.cucumber.Training_BDD.CommonMethods.StartWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Fernanda on 9/17/2016.
 */
public class AC04_DataTables_StepDefinitions {

    WebDriver driver;
    WebElement bankIDInput;
    WebElement usernameInput;
    WebElement passwordInput;
    WebElement loginButton;
    WebElement logoffButton;

    @Given("^the user navigates to MyKidsBank login page$")
    public void theUserNavigatesToMyKidsBankLoginPage() throws Throwable {
        driver = StartWebDriver();
        driver.get("http://mykidsbank.org/");
    }

    @When("^the user logs using the credentials$")
    public void theUserLogsUsingTheCredentials(DataTable credentials) throws Throwable {
        // populates a list with the data from table
        List<List<String>> data = credentials.raw();

        bankIDInput = driver.findElement(By.name(data.get(0).get(0)));
        usernameInput = driver.findElement(By.name(data.get(0).get(1)));
        passwordInput = driver.findElement(By.name(data.get(0).get(2)));
        loginButton =  driver.findElement(By.className("login_submit_button_class"));

        bankIDInput.sendKeys(data.get(1).get(0));
        usernameInput.sendKeys(data.get(1).get(1));
        passwordInput.sendKeys(data.get(1).get(2));
        loginButton.click();
    }

    @Then("^they should be in their accounts home page$")
    public void theyShouldBeInTheirAccountsHomePage() throws Throwable {
        logoffButton = driver.findElement(By.className("logoff_submit_button_class"));

        assertThat(logoffButton.isDisplayed());
        driver.quit();
    }
}
