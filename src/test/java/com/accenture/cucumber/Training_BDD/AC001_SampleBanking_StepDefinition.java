package com.accenture.cucumber.Training_BDD;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;


public class AC001_SampleBanking_StepDefinition {

	WebDriver driver;
	WebElement dummyElement;
	double dBeforeBalance;

	//-------------------------------------------------------------------------
	//-------------------------------------------------------------- Background
	//-------------------------------------------------------------------------
	
	@Given("^a user access the bank web app$")
	public void a_user_access_the_bank_web_app() throws Throwable {
		
		String Myurl = "http://www.mykidsbank.org";
		
		//String Myurl = System.getenv("application_url");
	    //System.out.println(Myurl);


		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("no-sandbox");
		options.addArguments("start-maximized");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);


		//driver = new FirefoxDriver();
		//driver.navigate().to(Myurl);
		driver.get("http://www.mykidsbank.org");
	}

	@And("^logs using the credentials$")
	public void logs_using_the_credentials(DataTable table) throws Throwable {
		// populates a list with the data from table
		List<List<String>> data = table.raw();

		driver.findElement(By.name("bank_id")).sendKeys(data.get(1).get(0));
		driver.findElement(By.name("username")).sendKeys(data.get(1).get(1));
		driver.findElement(By.name("password")).sendKeys(data.get(1).get(2));
		driver.findElement(By.className("login_submit_button_class")).click();
		Assert.assertTrue("Not logged in", driver.getTitle().equals("Bank of BDD-land"));
	}

	
	
	
	//-------------------------------------------------------------------------
	//---------------------------------------------------------------- @TC_001
	//-------------------------------------------------------------------------
	
	@Given("^my checking account has balance equal or greater than zero$")
	public void my_checking_account_has_balance_equal_or_greater_than_zero() throws Throwable {
		// Identify value from 2nd row in the grid
		String sBeforeBalance = driver.findElement(By.id("2")).getText();

		// Convert value into double type
		dBeforeBalance = 0.0;
		sBeforeBalance = sBeforeBalance.replace("$", "");
		sBeforeBalance = sBeforeBalance.replace(",", "");
		sBeforeBalance = sBeforeBalance.trim();
		if (sBeforeBalance.contains("(")) {
			sBeforeBalance.replace("(", "");
			sBeforeBalance.replace(")", "");
			dBeforeBalance = Double.valueOf(sBeforeBalance) * -1;
		} else
			dBeforeBalance = Double.valueOf(sBeforeBalance);

		// Compare if value is >= 0
		boolean bValidBalance = false;
		if (dBeforeBalance >= 0) {
			bValidBalance = true;
		} else
			bValidBalance = false;
		Assert.assertTrue(bValidBalance);
	}

	@When("^I deposit (\\d+) to my checking account$")
	public void i_deposit_to_my_checking_account(int deposit_value) throws Throwable {
		// click on deposit transaction
		dummyElement = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"document.new_group_transaction_form.is_deposit.value='1'; document.new_group_transaction_form.submit();");
		
		// fill the form with valid data
		driver.findElement(By.name("desc")).sendKeys("Weekly salary deposit");
		driver.findElement(By.name("amount")).clear();
		driver.findElement(By.name("amount")).sendKeys(String.valueOf(deposit_value));
		driver.findElement(By.id("a0")).click();
		
		// submit deposit
		dummyElement = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"submit_checkboxes_update('accounts_group_transaction_form'); if (document.accounts_group_transaction_form.onsubmit()) document.accounts_group_transaction_form.submit();");
		
		// confirm deposit
		dummyElement = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"submit_confirm_element_update('group_transaction_commit_form'); document.group_transaction_commit_form.submit();");
		dummyElement = null;
	}

	@Then("^I should have additional (\\d+) as balance$")
	public void i_should_have_additional_as_balance(int arg1) throws Throwable {
		// Identify value from 2nd row in the grid
		String sAfterBalance = driver.findElement(By.id("2")).getText();
		driver.close();

		// Convert value into double type
		double dAfterBalance = 0.0;
		sAfterBalance = sAfterBalance.replace("$", "");
		sAfterBalance = sAfterBalance.replace(",", "");
		sAfterBalance = sAfterBalance.trim();
		if (sAfterBalance.contains("(")) {
			sAfterBalance.replace("(", "");
			sAfterBalance.replace(")", "");
			dAfterBalance = Double.valueOf(sAfterBalance) * -1;
		} else
			dAfterBalance = Double.valueOf(sAfterBalance);

		Assert.assertEquals((dBeforeBalance + arg1), dAfterBalance, 0);
	}

	

	
	//-------------------------------------------------------------------------
	//---------------------------------------------------------------- @TC_002
	//-------------------------------------------------------------------------
	
	@Given("^my checking account has a balance greater than (\\d+) before withdraw$")
	public void my_checking_account_has_a_balance_greater_than_before_withdraw(int arg1) throws Throwable {
		// Identify value from 2nd row in the grid
		String sBeforeBalance = driver.findElement(By.id("2")).getText();

		// Convert value into double type
		dBeforeBalance = 0.0;
		sBeforeBalance = sBeforeBalance.replace("$", "");
		sBeforeBalance = sBeforeBalance.replace(",", "");
		sBeforeBalance = sBeforeBalance.trim();
		if (sBeforeBalance.contains("(")) {
			sBeforeBalance.replace("(", "");
			sBeforeBalance.replace(")", "");
			dBeforeBalance = Double.valueOf(sBeforeBalance) * -1;
		} else
			dBeforeBalance = Double.valueOf(sBeforeBalance);

		// Compare if value is greater than parameter "arg1"
		boolean bValidBalance = false;
		if (dBeforeBalance >= arg1) {
			bValidBalance = true;
		} else
			bValidBalance = false;
		Assert.assertTrue(bValidBalance);
	}
	
	@When("^I withdraw (\\d+) from my checking account$")
	public void i_withdrawn_from_my_checking_account(int withdrawn_amount) throws Throwable {
		// click on withdraw transaction
		dummyElement = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"document.new_group_transaction_form.is_deposit.value='0'; document.new_group_transaction_form.submit();");
		
		// fill the form with valid data
		driver.findElement(By.name("desc")).sendKeys("Scheduled expense");
		driver.findElement(By.name("amount")).clear();
		driver.findElement(By.name("amount")).sendKeys(String.valueOf(withdrawn_amount));
		driver.findElement(By.id("a0")).click();
		
		// submit withdraw
		dummyElement = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"submit_checkboxes_update('accounts_group_transaction_form'); if (document.accounts_group_transaction_form.onsubmit()) document.accounts_group_transaction_form.submit();");
		
		// confirm withdraw
		dummyElement = (WebElement) ((JavascriptExecutor) driver).executeScript(
				"submit_confirm_element_update('group_transaction_commit_form'); document.group_transaction_commit_form.submit();");
		dummyElement = null;
	}


	@Then("^I should have less (\\d+) as balance$")
	public void i_should_have_less_as_balance(int arg1) throws Throwable {
		// Identify value from 2nd row in the grid
		String sAfterBalance = driver.findElement(By.id("2")).getText();
		driver.close();

		// Convert value into double type
		double dAfterBalance = 0.0;
		sAfterBalance = sAfterBalance.replace("$", "");
		sAfterBalance = sAfterBalance.replace(",", "");
		sAfterBalance = sAfterBalance.trim();
		if (sAfterBalance.contains("(")) {
			sAfterBalance.replace("(", "");
			sAfterBalance.replace(")", "");
			dAfterBalance = Double.valueOf(sAfterBalance) * -1;
		} else
			dAfterBalance = Double.valueOf(sAfterBalance);

		Assert.assertEquals((dBeforeBalance - arg1), dAfterBalance, 0);
	}

	@After
	public void SyncResultsToAALM() {
		System.out.println("@AfterMethod:start ##################################################################");

		try{
			String strTestId = "216";
			String strTestSetFolderPath = "Root\\Tools_Integration\\Selenium";
			String strTestSetName = "Selenium";
			String strTestInstance = "1";
			String strEnvironment = "";
			String strRunStatus = "Passed";
			String strDuration = "10";
			String strJenkinsBuildNumber = "999";

			new ALMUpdater().almUpdateTestStatus(strTestId, strTestSetFolderPath, strTestSetName, strTestInstance, strEnvironment, strRunStatus, strDuration, strJenkinsBuildNumber);

		} catch (Exception e) {
			System.out.println("@AfterMethod: Exception = " + e.toString());
			//e.printStackTrace();
		} finally {
			System.out.println("\n@AfterMethod:finally ##################################################################");
		}
	}
}
