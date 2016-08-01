#Sample feature file for Behavior-Driven Testing and Development training
#Author: Fernanda Menks - fernanda.menks@accenture.com
#Creation date: July 31, 2016

Feature: Manage simple transactions in a banking account
         In order to manage my money more efficiently
         As a bank client
         TC_001: I want to make a deposit and withdraw money whenever I need to
         TC_002: I want to make a transfer to another bank account whenever I need to
         TC_003: I want to make a transfer to an international bank account

#Login information for generic user so all scenarios will use the same starting point
Background:
		  Given a user account owned by generic user
          And bank url is <<<URL>>>
          And username is <<<username>>>
          And password is <<<password>>>
          
          |URL                         | username |  password  |
          |http://12345.mykidsbank.org | client   | pwd123     |

@TC_001 @in_progress
Scenario: Make a deposit
          Given my checking account has a balance of 1000
          When I deposit 500 to my checking account
          Then I should have 1500 as balance
		  # Include here the parametrization and data for positive and negative test

@TC002 @signed-off
Scenario Outline: Make a withdrawn
         Given my checking account has a balance of <<<initial_balance>>>
         When I withdrawn <<<withdrawn_amount>>> from my checking account
         Then I should have <<<final_balance>>> as balance

         Examples:
         | initial_balance | withdrawn_amount | final_balance|
         | 1500            | 200              | 1300         |
         | 1300            | 0                | 1300         |

