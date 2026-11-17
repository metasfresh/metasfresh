@from:cucumber
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01106_Re-Post_Documents
Feature: A completed bank statement is booked again after a payment was linked to one of its lines
##
## Linking payments to a line of a completed bank statement (action "Reconcile payments" of the bank statement reconciliation window)
## deletes the accounting entries (Fact_Acct) of the statement and sets C_BankStatement.Posted='N' while the statement stays completed.
## The statement must then be posted again.
##

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2027-03-15T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains organization bank accounts
      | Identifier  | C_Currency_ID |
      | bankAccount | CHF           |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer |
      | customer   | Y          |

  @Id:S33766_30
  Scenario: the completed bank statement is booked again after a payment was linked to its line
    Given metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt  | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customer      | 100 CHF | true      | bankAccount         |
    And the payment identified by payment is completed
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs         | bankAccount         | 2027-03-15    |
    And metasfresh contains C_BankStatementLines:
      | Identifier | C_BankStatement_ID | Line | StmtAmt | C_Currency.ISO_Code |
      | bsl        | bs                 | 10   | 100     | CHF                 |
    And the C_BankStatement identified by bs is completed
    And Wait until documents bs are posted
    # the line is not linked to a payment yet: the in-transit booking has no business partner
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID |
      | B_Asset_Acct          | 100 CHF     |             | -             | bs        |
      | B_InTransit_Acct      |             | 100 CHF     | -             | bs        |

    When the payments payment are linked to bank statement line bsl

    Then Wait until documents bs are posted
    # booked again, now with the business partner of the linked payment
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID |
      | B_Asset_Acct          | 100 CHF     |             | -             | bs        |
      | B_InTransit_Acct      |             | 100 CHF     | customer      | bs        |
