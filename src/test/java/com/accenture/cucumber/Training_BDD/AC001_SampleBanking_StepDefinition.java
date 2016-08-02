package com.accenture.cucumber.Training_BDD;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class AC001_SampleBanking_StepDefinition {
	
	WebDriver driver;
	WebElement dummyElement;

	@Given("^a user access the bank web app$")
	public void a_user_access_the_bank_web_app() throws Throwable {
		driver = new FirefoxDriver();
		driver.navigate().to("http://www.mykidsbank.org");
	}

	
	@And("^logs using the credentials$")
	public void logs_using_the_credentials(DataTable table) throws Throwable {
		//populates a list with the data from table
		List<List<String>> data = table.raw();
				
		driver.findElement(By.name("bank_id")).sendKeys(data.get(1).get(0));
		driver.findElement(By.name("username")).sendKeys(data.get(1).get(1));
		driver.findElement(By.name("password")).sendKeys(data.get(1).get(2));
		driver.findElement(By.className("login_submit_button_class")).click();
		Assert.assertTrue("Not logged in", driver.getTitle().equals("Bank of BDD-land"));
	}
	

	@Given("^my checking account has a balance of (\\d+)$")
	public void my_checking_account_has_a_balance_of(int balance) throws Throwable {
		Assert.assertEquals(balance, 1000);
	}

	@When("^I deposit (\\d+) to my checking account$")
	public void i_deposit_to_my_checking_account(int deposit_value) throws Throwable {
		//click on deposit transaction
		dummyElement = (WebElement) ((JavascriptExecutor)driver).executeScript("document.new_group_transaction_form.is_deposit.value='1'; document.new_group_transaction_form.submit();");
		//fill the form with valid data
		driver.findElement(By.name("desc")).sendKeys("Weekly salary deposit");
		driver.findElement(By.name("amount")).clear();
		driver.findElement(By.name("amount")).sendKeys(String.valueOf(deposit_value));
		driver.findElement(By.id("a0")).click();
		//submit deposit
		dummyElement = (WebElement) ((JavascriptExecutor)driver).executeScript("submit_checkboxes_update('accounts_group_transaction_form'); if (document.accounts_group_transaction_form.onsubmit()) document.accounts_group_transaction_form.submit();");
		//confirm deposit
		dummyElement = (WebElement) ((JavascriptExecutor)driver).executeScript("submit_confirm_element_update('group_transaction_commit_form'); document.group_transaction_commit_form.submit();");
		dummyElement = null;
	}

	@Then("^I should have (\\d+) as balance$")
	public void i_should_have_as_balance(int balance) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertEquals(balance, 1500);
		driver.close();
	}


	@When("^I withdraw (\\d+) from my checking account$")
	public void i_withdrawn_from_my_checking_account(int withdrawn_amount) throws Throwable {
		//click on withdraw transaction
		dummyElement = (WebElement) ((JavascriptExecutor)driver).executeScript("document.new_group_transaction_form.is_deposit.value='0'; document.new_group_transaction_form.submit();");
		//fill the form with valid data
		driver.findElement(By.name("desc")).sendKeys("Scheduled expense");
		driver.findElement(By.name("amount")).clear();
		driver.findElement(By.name("amount")).sendKeys(String.valueOf(withdrawn_amount));
		driver.findElement(By.id("a0")).click();
		//submit withdraw
		dummyElement = (WebElement) ((JavascriptExecutor)driver).executeScript("submit_checkboxes_update('accounts_group_transaction_form'); if (document.accounts_group_transaction_form.onsubmit()) document.accounts_group_transaction_form.submit();");
		//confirm withdraw
		dummyElement = (WebElement) ((JavascriptExecutor)driver).executeScript("submit_confirm_element_update('group_transaction_commit_form'); document.group_transaction_commit_form.submit();");
		dummyElement = null;
	}

	@Given("^my checking account has a balance of (\\d+) before withdraw$")
	public void my_checking_account_has_a_balance_of_before_withdraw(int initial_balance) throws Throwable {
		Assert.assertEquals(initial_balance, 1500);
	}

	@Then("^I should have (\\d+) as balance after withdraw$")
	public void i_should_have_as_balance_after_withdraw(int final_balance) throws Throwable {
		Assert.assertEquals(final_balance, 1000);
		driver.close();
	}	
}
