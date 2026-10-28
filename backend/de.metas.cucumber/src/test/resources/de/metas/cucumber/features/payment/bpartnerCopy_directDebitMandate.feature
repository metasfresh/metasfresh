@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00982_Payment
@allure.label.feature:F00977_SEPA_Payment_Export
@allure.label.feature:F00978_SEPA_Direct_Debit_Lastschrift
@ghActions:run_on_executor4
Feature: Business partner copy does not copy direct debit mandates
## Regression guard: copying a business partner (the "copy record" action) must leave the C_BP_BankAccount_DirectDebitMandate records of the original behind,
## because a mandate is the debtor's signed authorization for exactly that bank account and cannot be inherited by a copy.
##
## Data notes:
## - The original BP gets a run-unique Value (@Date@) and its own bank account; no seed data is changed.
## - The IBAN has valid check digits.

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  @Id:S32708_TC11
  Scenario: TC11 copying a business partner with a bank account and an active CORE mandate leaves the mandate with the original only
    Given metasfresh contains C_BPartners:
      | Identifier | Name                | Value           | IsCustomer |
      | bp_orig    | Mandate copy origin | mandcopy_@Date@ | Y          |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                   | RoutingNo | SwiftCode   |
      | copyBank             | Mandate copy test bank | 30000000  | TESTDEC1XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | origAcct   | bp_orig       | EUR           | DE89370400440532013000 | copyBank             |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed |
      | origMandate                            | origAcct            | CORE        | MREF-COPY-001    | 2024-03-15  | Y           | 2025-09-04   |

    When the record "bp_orig" is copied as "bp_copy"

    Then C_BPartner "bp_copy" has no C_BP_BankAccount_DirectDebitMandate
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | MandateType | MandateDate | DateLastUsed | IsActive |
      | origMandate                            | CORE        | 2024-03-15  | 2025-09-04   | Y        |
