#Sample feature file for Behavior-Driven Testing and Development training
#Author: Fernanda Menks - fernanda.menks@accenture.com
#Creation date: September 17, 2016
@DataTables
Feature: Online Banking
  In order to be able to make bank transactions at home
  As a bank client
  I want to be able to make transactions using a web app

  @LoginAtMyKidsBank
  Scenario: Login
    Given the user navigates to MyKidsBank login page
    When the user logs using the credentials
      | bank_id | username | password |
      | 25967   | banker   | training |
    Then they should be in their accounts home page