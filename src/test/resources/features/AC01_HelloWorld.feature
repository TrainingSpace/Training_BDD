#Sample feature file for Behavior-Driven Testing and Development training
#Author: Fernanda Menks - fernanda.menks@accenture.com
#Creation date: September 16, 2016
@HelloWorld
Feature: Search for definitions
  In order to understand what is Cucumber BDD
  As a user
  I want to be able to look up the meaning of Cucumber BDD

  @GoogleSearch
  Scenario: Make a Google search
    Given the user navigates to Google home page
    When the user performs a search for Cucumber BDD
    Then they should see that the search was performed