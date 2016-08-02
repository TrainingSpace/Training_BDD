package com.accenture.cucumber.Training_BDD;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AC001_SampleBanking_StepDefinition {

	@Given("^a user account owned by generic user$")
	public void a_user_account_owned_by_generic_user() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@Given("^bank url is <<<URL>>>$")
	public void bank_url_is_URL() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@Given("^username is <<<username>>>$")
	public void username_is_username() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@Given("^password is <<<password>>>$")
	public void password_is_password(DataTable arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)

	}

	@Given("^my checking account has a balance of (\\d+)$")
	public void my_checking_account_has_a_balance_of(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@When("^I deposit (\\d+) to my checking account$")
	public void i_deposit_to_my_checking_account(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@Then("^I should have (\\d+) as balance$")
	public void i_should_have_as_balance(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}


	@When("^I withdrawn <<(\\d+)>> from my checking account$")
	public void i_withdrawn_from_my_checking_account(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	
}
