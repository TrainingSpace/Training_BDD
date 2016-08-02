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
  "comments": [
    {
      "line": 13,
      "value": "#Given I am in the bank web app"
    },
    {
      "line": 14,
      "value": "#And I am logged in"
    }
  ],
  "line": 15,
  "name": "a user access the bank web app",
  "keyword": "Given "
});
formatter.step({
  "line": 16,
  "name": "logs using the credentials",
  "rows": [
    {
      "cells": [
        "bank_id",
        "username",
        "password",
        "url"
      ],
      "line": 17
    },
    {
      "cells": [
        "25967",
        "banker",
        "training",
        "http://www.mykidsbank.org"
      ],
      "line": 18
    }
  ],
  "keyword": "And "
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.a_user_access_the_bank_web_app()"
});
formatter.result({
  "duration": 14134909001,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.logs_using_the_credentials(DataTable)"
});
formatter.result({
  "duration": 5652587065,
  "status": "passed"
});
formatter.scenario({
  "line": 21,
  "name": "Make a deposit",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-deposit",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 20,
      "name": "@TC_001"
    },
    {
      "line": 20,
      "name": "@in_progress"
    }
  ]
});
formatter.step({
  "line": 22,
  "name": "my checking account has a balance of 1000",
  "keyword": "Given "
});
formatter.step({
  "line": 23,
  "name": "I deposit 500 to my checking account",
  "keyword": "When "
});
formatter.step({
  "line": 24,
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
  "duration": 3438369,
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
  "duration": 3803920647,
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
  "duration": 924187424,
  "status": "passed"
});
formatter.scenarioOutline({
  "comments": [
    {
      "line": 26,
      "value": "# Include here the parametrization and data for positive and negative test"
    }
  ],
  "line": 28,
  "name": "Make a withdraw",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 27,
      "name": "@TC002"
    },
    {
      "line": 27,
      "name": "@signed-off"
    }
  ]
});
formatter.step({
  "line": 29,
  "name": "my checking account has a balance of \u003cinitial_balance\u003e before withdraw",
  "keyword": "Given "
});
formatter.step({
  "line": 30,
  "name": "I withdraw \u003cwithdrawn_amount\u003e from my checking account",
  "keyword": "When "
});
formatter.step({
  "line": 31,
  "name": "I should have \u003cfinal_balance\u003e as balance after withdraw",
  "keyword": "Then "
});
formatter.examples({
  "line": 33,
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
      "line": 34,
      "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;1"
    },
    {
      "cells": [
        "1500",
        "250",
        "1000"
      ],
      "line": 35,
      "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;2"
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
  "comments": [
    {
      "line": 13,
      "value": "#Given I am in the bank web app"
    },
    {
      "line": 14,
      "value": "#And I am logged in"
    }
  ],
  "line": 15,
  "name": "a user access the bank web app",
  "keyword": "Given "
});
formatter.step({
  "line": 16,
  "name": "logs using the credentials",
  "rows": [
    {
      "cells": [
        "bank_id",
        "username",
        "password",
        "url"
      ],
      "line": 17
    },
    {
      "cells": [
        "25967",
        "banker",
        "training",
        "http://www.mykidsbank.org"
      ],
      "line": 18
    }
  ],
  "keyword": "And "
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.a_user_access_the_bank_web_app()"
});
formatter.result({
  "duration": 11657333180,
  "status": "passed"
});
formatter.match({
  "location": "AC001_SampleBanking_StepDefinition.logs_using_the_credentials(DataTable)"
});
formatter.result({
  "duration": 4088937556,
  "status": "passed"
});
formatter.scenario({
  "line": 35,
  "name": "Make a withdraw",
  "description": "",
  "id": "manage-simple-transactions-in-a-banking-account;make-a-withdraw;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 27,
      "name": "@TC002"
    },
    {
      "line": 27,
      "name": "@signed-off"
    }
  ]
});
formatter.step({
  "line": 29,
  "name": "my checking account has a balance of 1500 before withdraw",
  "matchedColumns": [
    0
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 30,
  "name": "I withdraw 250 from my checking account",
  "matchedColumns": [
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 31,
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
  "duration": 191680,
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
  "duration": 3795900846,
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
  "duration": 668475482,
  "status": "passed"
});
});