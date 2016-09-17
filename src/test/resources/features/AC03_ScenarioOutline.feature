#Sample feature file for Behavior-Driven Testing and Development training
#Author: Fernanda Menks - fernanda.menks@accenture.com
#Creation date: September 16, 2016
@ScenarioOutline
Feature: Search for definitions
  In order to be able to make purchases
  As a user
  I want to be able to filter by category before browsing

  @CategoryBrowsing
  Scenario Outline: Browse Category
    Given the user is browsing ebay home page
    When the user navigates to "<category>" category
    Then they should that "<category_title>" is displayed in the page title
    Examples:
      | category    | category_title    |
      | Motors      | New & used cars   |
      | Electronics | Electronics Store |
      | Toys        | Toys & Hobbies    |