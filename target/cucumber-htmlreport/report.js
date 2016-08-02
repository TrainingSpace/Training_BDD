$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Sample banking acceptance criteria.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Sample feature file for Behavior-Driven Testing and Development training"
    },
    {
      "line": 2,
      "value": "#Author: Fernanda Menks - fernanda.menks@accenture.com"
    },
    {
      "line": 3,
      "value": "#Creation date: July 31, 2016"
    }
  ],
  "line": 4,
  "name": "Manage simple transactions in a banking account",
  "description": "       In order to manage my money more efficiently\r\n       As a bank client\r\n       TC_001: I want to make a deposit and withdraw money whenever I need to\r\n       TC_002: I want to make a transfer to another bank account whenever I need to\r\n       TC_003: I want to make a transfer to an international bank account",
  "id": "manage-simple-transactions-in-a-banking-account",
  "keyword": "Feature"
});
formatter.background({
  "comments": [
    {
      "line": 11,
      "value": "#Login information for generic user so all scenarios will use the same starting point"
    }
  ],
  "line": 12,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 13,
  "name": "I am in the bank web app",
  "keyword": "Given "
});
formatter.step({
  "line": 14,
  "name": "I am logged in",
  "keyword": "And "
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldNavigateToBankWebsite()"
});
formatter.result({
  "duration": 3111564816,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldLogin()"
});
formatter.result({
  "duration": 636544397,
  "status": "passed"
});
formatter.scenario({
  "line": 17,
  "name": "Make a deposit",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-deposit",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 16,
      "name": "@TC_001"
    },
    {
      "line": 16,
      "name": "@in_progress"
    }
  ]
});
formatter.step({
  "line": 18,
  "name": "my checking account has a balance of 1000",
  "keyword": "Given "
});
formatter.step({
  "line": 19,
  "name": "I deposit 500 to my checking account",
  "keyword": "When "
});
formatter.step({
  "line": 20,
  "name": "I should have 1500 as balance",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "1000",
      "offset": 37
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of(int)"
});
formatter.result({
  "duration": 1071285,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "500",
      "offset": 10
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_deposit_to_my_checking_account(int)"
});
formatter.result({
  "duration": 453659174,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1500",
      "offset": 14
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_should_have_as_balance(int)"
});
formatter.result({
  "duration": 208738215,
  "status": "passed"
});
formatter.scenarioOutline({
  "comments": [
    {
      "line": 22,
      "value": "# Include here the parametrization and data for positive and negative test"
    }
  ],
  "line": 24,
  "name": "Make a withdraw",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 23,
      "name": "@TC002"
    },
    {
      "line": 23,
      "name": "@signed-off"
    }
  ]
});
formatter.step({
  "line": 25,
  "name": "my checking account has a balance of \u003cinitial_balance\u003e before withdraw",
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "I withdraw \u003cwithdrawn_amount\u003e from my checking account",
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "I should have \u003cfinal_balance\u003e as balance after withdraw",
  "keyword": "Then "
});
formatter.examples({
  "line": 29,
  "name": "",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;",
  "rows": [
    {
      "cells": [
        "initial_balance",
        "withdrawn_amount",
        "final_balance"
      ],
      "line": 30,
      "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;1"
    },
    {
      "cells": [
        "1500",
        "250",
        "1000"
      ],
      "line": 31,
      "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;2"
    },
    {
      "cells": [
        "1500",
        "250",
        "1000"
      ],
      "line": 32,
      "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;3"
    }
  ],
  "keyword": "Examples"
});
formatter.background({
  "comments": [
    {
      "line": 11,
      "value": "#Login information for generic user so all scenarios will use the same starting point"
    }
  ],
  "line": 12,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 13,
  "name": "I am in the bank web app",
  "keyword": "Given "
});
formatter.step({
  "line": 14,
  "name": "I am logged in",
  "keyword": "And "
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldNavigateToBankWebsite()"
});
formatter.result({
  "duration": 2775424117,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldLogin()"
});
formatter.result({
  "duration": 799394154,
  "status": "passed"
});
formatter.scenario({
  "line": 31,
  "name": "Make a withdraw",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 23,
      "name": "@TC002"
    },
    {
      "line": 23,
      "name": "@signed-off"
    }
  ]
});
formatter.step({
  "line": 25,
  "name": "my checking account has a balance of 1500 before withdraw",
  "matchedColumns": [
    0
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "I withdraw 250 from my checking account",
  "matchedColumns": [
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "I should have 1000 as balance after withdraw",
  "matchedColumns": [
    2
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "1500",
      "offset": 37
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of_before_withdraw(int)"
});
formatter.result({
  "duration": 315934,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "250",
      "offset": 11
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_withdrawn_from_my_checking_account(int)"
});
formatter.result({
  "duration": 539964919,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1000",
      "offset": 14
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_should_have_as_balance_after_withdraw(int)"
});
formatter.result({
  "duration": 207044398,
  "status": "passed"
});
formatter.background({
  "comments": [
    {
      "line": 11,
      "value": "#Login information for generic user so all scenarios will use the same starting point"
    }
  ],
  "line": 12,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 13,
  "name": "I am in the bank web app",
  "keyword": "Given "
});
formatter.step({
  "line": 14,
  "name": "I am logged in",
  "keyword": "And "
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldNavigateToBankWebsite()"
});
formatter.result({
  "duration": 2774530827,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldLogin()"
});
formatter.result({
  "duration": 568215879,
  "status": "passed"
});
formatter.scenario({
  "line": 32,
  "name": "Make a withdraw",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;3",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 23,
      "name": "@TC002"
    },
    {
      "line": 23,
      "name": "@signed-off"
    }
  ]
});
formatter.step({
  "line": 25,
  "name": "my checking account has a balance of 1500 before withdraw",
  "matchedColumns": [
    0
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "I withdraw 250 from my checking account",
  "matchedColumns": [
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "I should have 1000 as balance after withdraw",
  "matchedColumns": [
    2
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "1500",
      "offset": 37
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of_before_withdraw(int)"
});
formatter.result({
  "duration": 76800,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "250",
      "offset": 11
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_withdrawn_from_my_checking_account(int)"
});
formatter.result({
  "duration": 528032908,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1000",
      "offset": 14
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_should_have_as_balance_after_withdraw(int)"
});
formatter.result({
  "duration": 205700248,
  "status": "passed"
});
});