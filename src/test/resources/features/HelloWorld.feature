#Sample feature file for Behavior-Driven Testing and Development training
#Author: Fernanda Menks - fernanda.menks@accenture.com
#Creation date: September 16, 2016
@HelloWorld
Feature: Search for definitions
  In order to understand a word that I don't know
  As a user
  I want to be able to look up the meaning of the word

  @GoogleSearch
  Scenario: Make a default Google search
    Given the user navigates to Google home page
    When the user performs a search for 'Cucumber BDD'
    Then they should see that the search was performed