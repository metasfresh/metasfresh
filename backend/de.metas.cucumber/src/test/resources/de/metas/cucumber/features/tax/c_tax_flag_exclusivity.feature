@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F2200
@ghActions:run_on_executor7
Feature: C_Tax exclusive flags — at most one of IsTaxExempt / IsReverseCharge / IsWholeTax = 'Y'

  Enforced by three layers:
    - Model interceptor (glue) → TaxBL.enforceExclusiveFlags (business logic + WholeTax cascade)
    - DB CHECK constraint c_tax_exclusive_tax_flags (last-mile defence against raw SQL / REST bypass)
    - Unit coverage for the full algorithm matrix is in TaxBLTest; this feature covers integration end-to-end.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-04-22T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |

  @Id:c_tax_flag_exclusivity_TC_S1
  Scenario: TC-S1 Single-flag change — setting IsReverseCharge=Y leaves the other two flags N
    Given metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | IsReverseCharge |
      | tax19      | taxCategory      | 19   | true            |
    Then reload C_Tax and assert:
      | Identifier | IsTaxExempt | IsReverseCharge | IsWholeTax | Rate |
      | tax19      | false       | true            | false      | 19   |

  @Id:c_tax_flag_exclusivity_TC_S2
  Scenario: TC-S2 Multi-flag conflict, user just changed one — the changed flag wins
    Given metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | IsTaxExempt |
      | tax19      | taxCategory      | 19   | true        |
    When update C_Tax:
      | Identifier | IsReverseCharge |
      | tax19      | true            |
    Then reload C_Tax and assert:
      | Identifier | IsTaxExempt | IsReverseCharge | IsWholeTax |
      | tax19      | false       | true            | false      |

  @Id:c_tax_flag_exclusivity_TC_S3
  Scenario: TC-S3 Multi-flag conflict, multiple flags toggled in one save — static priority picks IsReverseCharge over IsTaxExempt
    Given metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate |
      | tax19      | taxCategory      | 19   |
    When update C_Tax:
      | Identifier | IsTaxExempt | IsReverseCharge |
      | tax19      | true        | true            |
    Then reload C_Tax and assert:
      | Identifier | IsTaxExempt | IsReverseCharge | IsWholeTax |
      | tax19      | false       | true            | false      |

  @Id:c_tax_flag_exclusivity_TC_S4
  Scenario: TC-S4 IsWholeTax wins and cascades — Rate=100, IsDocumentLevel=Y, other flags cleared
    Given metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | IsReverseCharge | IsDocumentLevel |
      | tax19      | taxCategory      | 19   | true            | false           |
    When update C_Tax:
      | Identifier | IsWholeTax |
      | tax19      | true       |
    Then reload C_Tax and assert:
      | Identifier | IsTaxExempt | IsReverseCharge | IsWholeTax | Rate | IsDocumentLevel |
      | tax19      | false       | false           | true       | 100  | true            |

  @Id:c_tax_flag_exclusivity_TC_S5
  Scenario: TC-S5 DB CHECK last-mile — raw SQL bypassing the interceptor is rejected by c_tax_exclusive_tax_flags
    Given metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | IsTaxExempt |
      | tax19      | taxCategory      | 19   | true        |
    Then raw SQL update to C_Tax is rejected by CHECK constraint:
      | Identifier | IsTaxExempt | IsReverseCharge |
      | tax19      | true        | true            |
    And reload C_Tax and assert:
      | Identifier | IsTaxExempt | IsReverseCharge | IsWholeTax |
      | tax19      | true        | false           | false      |
