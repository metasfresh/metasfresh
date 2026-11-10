@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS till card sale with an unknown card result

  # A card sale at the till whose result from SumUp is unknown (timeout, network error, SumUp 5xx, or SumUp accepted
  # the charge but metasfresh could not save its transaction) must not end as FAILED: the card payment becomes UNKNOWN,
  # cannot be retried, deleted or voided, and the sale cannot be completed, until a later SumUp result resolves it or
  # the cashier checks the card reader and releases it after the wait.
  #
  # Data shape: the cucumber data of the till card sale of @Id:POS_CARD_TC10_1 (posInvoiceCardSettlement.feature): EUR gross
  # price list (IsTaxIncluded=Y), product1 at 2,50 PCE, 7 % tax category, cash bank cashbook, terminal shopTill with its
  # cash journal opened at 100, SumUp and a default card reader.
  #
  # Cause (i): SumUp accepted the checkout but metasfresh could not save its transaction (SumUp's transaction id is known).
  # Cause (ii): timeout, network error or SumUp 5xx (no SumUp transaction id is known).
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]
    And temporarily set sys config boolean value true for sys config 'de.metas.report.jasper.IsMockReportService'
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory07 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax07      | taxCategory07    | 7    | DE                       | DE                        | false           |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product1   | PCE      |
    And metasfresh contains M_PricingSystems
      | Identifier         |
      | grossPricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | grossPrice | grossPricingSystem | EUR           | true  | true          |
    And metasfresh contains M_PriceList_Versions
      | Identifier        | M_PriceList_ID |
      | grossPriceVersion | grossPrice     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | grossPriceVersion      | product1     | 2.50     | PCE      | taxCategory07    |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID | IsCashBank |
      | cashbook   | EUR           | Y          |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |
    And metasfresh contains C_BPartners:
      | Identifier     |
      | walkInCustomer |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID  | C_BPartner_Location_ID | M_Warehouse_ID |
      | shopTill   | cashbook            | grossPricingSystem | grossPrice     | walkInCustomer | walkInCustomer         | warehouse      |
    And the cash journal of POS terminal shopTill is opened with 100 by metasfresh
    And POS terminal shopTill is configured with SumUp and a default card reader

  # ##########################################################################
  # Cause (ii) for HTTP 5xx and CONNECTION_RESET, cause (i) for DB_SAVE_FAILS. AC18: UNKNOWN, still counted as paid.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_1
  Scenario Outline: An unknown card result (<cause>) leaves the card payment UNKNOWN: no retry, no delete, no void, no completion
    Given the card checkout's result is made unknown by <cause>
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId <clientId>
    And retrying the card payment of POS sale sale1 fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
    And deleting the card payment of POS sale sale1 fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
    And voiding POS sale sale1 fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
    And completing POS sale sale1 fails
    And POS sale sale1 has open amount 0.00
    And POS sale sale1 is not completed

    Examples:
      | cause            | clientId |
      | HTTP 5xx         | empty    |
      | CONNECTION_RESET | empty    |
      | DB_SAVE_FAILS    | set      |

  # ##########################################################################
  # Cause (ii). Review focus 2: the cashier removes the UNKNOWN payment from the till.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_2
  Scenario: The till cannot remove the UNKNOWN card payment from its order
    Given SumUp API stub answers the next checkout with HTTP 5xx
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN
    And sending POS sale sale1 without its card payment fails with AD_Message 'de.metas.pos.POSPayment.CardPaymentResultUnknown'
    And the card payment of POS sale sale1 has status UNKNOWN

  # ##########################################################################
  # Cause (i).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_3
  Scenario: A later SUCCESSFUL result from SumUp resolves an UNKNOWN card payment and completes the sale
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status SUCCESSFUL
    And POS sale sale1 is completed
    And the invoice of POS sale sale1 is paid
    And the cash journal of POS terminal shopTill contains lines:
      | Type     | Amount |
      | CARD_PAY | 2.50   |

  # ##########################################################################
  # Cause (i).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_4
  Scenario Outline: A later <status> result from SumUp resolves an UNKNOWN card payment; a retry then completes the sale
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And SumUp API stub reports the transaction of POS sale sale1 as <status>
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status <status>
    When the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status SUCCESSFUL
    And POS sale sale1 is completed

    Examples:
      | status    |
      | FAILED    |
      | CANCELLED |

  # ##########################################################################
  # Cause (i).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_5
  Scenario: A PENDING result from SumUp imports the transaction, the card payment stays UNKNOWN
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    And metasfresh polls SumUp
    Then the SumUp transaction of POS sale sale1 is saved with status PENDING
    And the card payment of POS sale sale1 has status UNKNOWN

  # ##########################################################################
  # The system stopped after saving the PENDING payment (startCardCheckout alone); SumUp never saw the checkout.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_6
  Scenario: A card checkout that stopped after saving the payment becomes UNKNOWN after 150 seconds
    Given a POS card sale sale1 is prepared at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    When the till's card checkout of POS sale sale1 stops after saving the payment
    And metasfresh's clock is 149 seconds after the card checkout of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status PENDING
    When metasfresh's clock is 151 seconds after the card checkout of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty

  # ##########################################################################
  # Cause (ii). The wait is raised to its floor of 150 s, so 100 s is too early.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_7
  Scenario: A release before the configured wait is refused
    Given temporarily set sys config int value 60 for sys config 'de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds'
    And SumUp API stub answers the next checkout with HTTP 5xx
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    And metasfresh's clock is 100 seconds after the card checkout of POS sale sale1
    When the card payment of POS sale sale1 is released by cashier metasfresh
    Then the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired false and checkFailed false and refused with AD_Message 'de.metas.pos.POSPayment.ReleaseNotYetAllowed'

  # ##########################################################################
  # The stub accepts the checkout (SUCCESS), so the payment is PENDING, not UNKNOWN.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_8
  Scenario: Only a card payment whose result is unknown can be released
    Given a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status PENDING
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    When the card payment of POS sale sale1 is released by cashier metasfresh
    Then the release of the card payment of POS sale sale1 returned status PENDING with confirmationRequired false and checkFailed false and refused with AD_Message 'de.metas.pos.POSPayment.ReleaseOnlyIfResultUnknown'
    And the SumUp stub received 0 terminates

  # ##########################################################################
  # Cause (ii).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_9
  Scenario: A released card payment is FAILED with a back-office record, and a retry completes the sale
    Given SumUp API stub answers the next checkout with HTTP 5xx
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    When the card payment of POS sale sale1 is released by cashier metasfresh
    Then the SumUp stub received 1 terminate on reader reader-shopTill
    And the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired true and checkFailed false
    And the card payment of POS sale sale1 has status UNKNOWN
    When the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'
    When the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then POS sale sale1 is completed

  # ##########################################################################
  # Cause (ii).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_10
  Scenario: A released card payment can be deleted and the sale paid in cash
    Given SumUp API stub answers the next checkout with HTTP 5xx
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    When the card payment of POS sale sale1 is deleted
    And a cash payment of 2.50 completes POS sale sale1
    Then POS sale sale1 is completed

  # ##########################################################################
  # Cause (i). The stub reports SUCCESSFUL with no poll; the release check imports the transaction. The status event is async.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_11
  Scenario: The release check imports an approved charge and refuses the release; the sale completes
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    When the card payment of POS sale sale1 is released by cashier metasfresh
    Then the release of the card payment of POS sale sale1 returned confirmationRequired false and checkFailed false and refused with AD_Message 'de.metas.pos.POSPayment.ReleaseRefusedApproved'
    And metasfresh handled the SumUp events
    And after not more than 30s, the card payment of POS sale sale1 has status SUCCESSFUL
    And after not more than 30s, POS sale sale1 is completed
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'

  # ##########################################################################
  # Cause (i). Review focus 5: SumUp is unreachable during the release check.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_12
  Scenario: A release while SumUp cannot be asked still needs the cashier's confirmation
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub fails the status lookup
    When the card payment of POS sale sale1 is released by cashier metasfresh
    Then the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired true and checkFailed true
    And the card payment of POS sale sale1 has status UNKNOWN

  # ##########################################################################
  # Cause (i).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_13
  Scenario: A charge approved between the check and the confirmation is not released
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    Then the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired true and checkFailed false
    And the SumUp transaction of POS sale sale1 is saved with status PENDING
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status SUCCESSFUL
    When the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then POS sale sale1 is completed
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'

  # ##########################################################################
  # Cause (i). The poller re-reads only PENDING rows, so the callback is the path that re-reads the SUCCESSFUL row; the
  # refund event must not write a second record (once-rule).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_14
  Scenario: A late approval of a released card payment is not booked and is recorded once for back office
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    And the card payment of POS sale sale1 has no payment receipt
    When SumUp calls back for the transaction of POS sale sale1
    Then the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL refunded 2.50
    And SumUp calls back for the transaction of POS sale sale1
    And metasfresh handled the SumUp events
    Then the first SumUp transaction of POS sale sale1 has refund amount 2.50
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    And the card payment of POS sale sale1 has no payment receipt

  # ##########################################################################
  # Cause (i). Review focus 1: the retry's new transaction stays PENDING; the first checkout's results are ignored.
  # The CANCELLED leg uses a callback, not a poll: the first row is already SUCCESSFUL locally and the poller re-reads only PENDING rows.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_15
  Scenario: Late results of the released first checkout do not touch the retried card payment
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    When the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    And SumUp API stub reports the first transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId of the latest transaction
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    When SumUp API stub reports the first transaction of POS sale sale1 as CANCELLED
    And SumUp calls back for the first transaction of POS sale sale1
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId of the latest transaction

  # ##########################################################################
  # Cause (i). The released transaction is still PENDING at SumUp, so the poller still polls it. The released-checkout rule
  # matches here too; the DELETED rule on its own is isolated by a unit test.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_16
  Scenario: A late approval of a deleted card payment is not booked and is recorded for back office
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    When the card payment of POS sale sale1 is deleted
    Then the card payment of POS sale sale1 has status DELETED
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status DELETED
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    And the card payment of POS sale sale1 has no payment receipt

  # ##########################################################################
  # REGRESSION GUARD, green from the start: pins the code rule that changingStatusFromRemote accepts a later remote
  # status on a plain FAILED payment (POSPayment.changingStatusFromRemote), which the fix keeps. Not verified: whether real
  # SumUp ever flips a FAILED transaction to SUCCESSFUL; the flip is a stub lever, so this characterises code, not a known
  # customer flow.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_17
  Scenario: A plain FAILED card payment still accepts a later SUCCESSFUL result, as before
    Given a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    And SumUp API stub reports the transaction of POS sale sale1 as FAILED
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status FAILED
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And SumUp calls back for the transaction of POS sale sale1
    Then the card payment of POS sale sale1 has status SUCCESSFUL
    And POS sale sale1 is completed
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
