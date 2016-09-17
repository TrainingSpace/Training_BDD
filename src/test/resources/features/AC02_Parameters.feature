#Sample feature file for Behavior-Driven Testing and Development training
#Author: Fernanda Menks - fernanda.menks@accenture.com
#Creation date: September 16, 2016
@Parameters
Feature: Search for definitions
  In order to be able to make purchases
  As a user
  I want to be able to filter by category before browsing

  @MotorsBrowsing
  Scenario: Browse Motors
    Given the user navigates to ebay home page
    When the user selects "Motors" category
    Then they should see "New & used cars" in the page title

  @ElectronicsBrowsing
  Scenario: Browse Electronics
    Given the user navigates to ebay home page
    When the user selects "Electronics" category
    Then they should see "Electronics Store" in the page title

  @ToysBrowsing
  Scenario: Browse Toys
    Given the user navigates to ebay home page
    When the user selects "Toys" category
    Then they should see "Toys & Hobbies" in the page title