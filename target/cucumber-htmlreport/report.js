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
  "duration": 3246613270,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldLogin()"
});
formatter.result({
  "duration": 777078781,
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
  "duration": 2024810,
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
  "duration": 555067114,
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
  "duration": 205324380,
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
  "name": "my checking account has a balance of \u003cinitial_balance\u003e",
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "I withdraw \u003cwithdrawn_amount\u003e from my checking account",
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "I should have \u003cfinal_balance\u003e as balance",
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
        "200",
        "1300"
      ],
      "line": 31,
      "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;2"
    },
    {
      "cells": [
        "1300",
        "0",
        "1300"
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
  "duration": 2780029708,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldLogin()"
});
formatter.result({
  "duration": 564065967,
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
  "name": "my checking account has a balance of 1500",
  "matchedColumns": [
    0
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "I withdraw 200 from my checking account",
  "matchedColumns": [
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "I should have 1300 as balance",
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
  "location": "AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of(int)"
});
formatter.result({
  "duration": 449054,
  "error_message": "junit.framework.AssertionFailedError: expected:\u003c1500\u003e but was:\u003c1000\u003e\r\n\tat junit.framework.Assert.fail(Assert.java:57)\r\n\tat junit.framework.Assert.failNotEquals(Assert.java:329)\r\n\tat junit.framework.Assert.assertEquals(Assert.java:78)\r\n\tat junit.framework.Assert.assertEquals(Assert.java:234)\r\n\tat junit.framework.Assert.assertEquals(Assert.java:241)\r\n\tat com.accenture.cucumber.Training_BDD.AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of(AC001_SampleBanking_StepDefinition.java:37)\r\n\tat ✽.Given my checking account has a balance of 1500(Sample banking acceptance criteria.feature:25)\r\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "200",
      "offset": 11
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_withdrawn_from_my_checking_account(int)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "1300",
      "offset": 14
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_should_have_as_balance(int)"
});
formatter.result({
  "status": "skipped"
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
  "duration": 3056065515,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.shouldLogin()"
});
formatter.result({
  "duration": 571461658,
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
  "name": "my checking account has a balance of 1300",
  "matchedColumns": [
    0
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "I withdraw 0 from my checking account",
  "matchedColumns": [
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "I should have 1300 as balance",
  "matchedColumns": [
    2
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "1300",
      "offset": 37
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of(int)"
});
formatter.result({
  "duration": 243953,
  "error_message": "junit.framework.AssertionFailedError: expected:\u003c1300\u003e but was:\u003c1000\u003e\r\n\tat junit.framework.Assert.fail(Assert.java:57)\r\n\tat junit.framework.Assert.failNotEquals(Assert.java:329)\r\n\tat junit.framework.Assert.assertEquals(Assert.java:78)\r\n\tat junit.framework.Assert.assertEquals(Assert.java:234)\r\n\tat junit.framework.Assert.assertEquals(Assert.java:241)\r\n\tat com.accenture.cucumber.Training_BDD.AC001_SampleBanking_StepDefinition.my_checking_account_has_a_balance_of(AC001_SampleBanking_StepDefinition.java:37)\r\n\tat ✽.Given my checking account has a balance of 1300(Sample banking acceptance criteria.feature:25)\r\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "0",
      "offset": 11
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_withdrawn_from_my_checking_account(int)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "1300",
      "offset": 14
    }
  ],
  "location": "AC001_SampleBanking_StepDefinition.i_should_have_as_balance(int)"
});
formatter.result({
  "status": "skipped"
});
});