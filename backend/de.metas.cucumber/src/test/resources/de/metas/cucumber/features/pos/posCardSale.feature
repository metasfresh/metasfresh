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
  # Cause (ii) for HTTP 5xx and CONNECTION_RESET, cause (i) for DB_SAVE_FAILS. The card payment is UNKNOWN and still counted as paid.
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
  # Cause (ii). The cashier removes the UNKNOWN payment from the till.
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
  # Cause (i). SumUp is unreachable during the release check.
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
  # Cause (i). The retry's new transaction stays PENDING; the first checkout's results are ignored.
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

  # ##########################################################################
  # Cause (i), and the database also fails twice while the checkout stores SumUp's answer on the card payment: the bounded
  # retry of that write stores it on its 3rd attempt.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_18
  Scenario: The card result is stored by the retry of its write while the database fails twice
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    And saving the card payment of a POS sale fails with a database error for the next 2 attempts
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId of the latest transaction
    And the till's card checkout of POS sale sale1 answered the card payment with status UNKNOWN and sumUpClientTransactionId set
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.CardResultNotStored'
    And saving a SumUp transaction works again

  # ##########################################################################
  # Cause (i), and the database keeps failing for all 3 attempts to store SumUp's answer (database-failure sub-case of the
  # known limit): the till gets the payment back as saved before the send (PENDING, no SumUp id), and back office gets a
  # record naming SumUp's id. Its continuation to UNKNOWN is the orphan rule.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_19
  Scenario: A card result that cannot be stored after 3 attempts is recorded for back office; the till still gets its order
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    And saving the card payment of a POS sale fails with a database error for the next 3 attempts
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the till's card checkout of POS sale sale1 answered the card payment with status PENDING and sumUpClientTransactionId empty
    And the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId empty
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.CardResultNotStored'
    And the AD_Issue 'de.metas.pos.POSPayment.CardResultNotStored' of the card payment of POS sale sale1 names the client transaction id the SumUp stub accepted
    And saving the card payment of a POS sale works again
    And saving a SumUp transaction works again

  # ##########################################################################
  # SumUp accepted the checkout and its transaction was saved: the payment is PENDING, as before, and names the SumUp
  # transaction of this attempt.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_20
  Scenario: An accepted card checkout leaves the payment PENDING, naming the SumUp transaction of this attempt
    Given SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId of the latest transaction
    And the SumUp transaction of POS sale sale1 is saved with status PENDING

  # ##########################################################################
  # A SumUp poll imports the approval (and its status event updates the payment) while the checkout still
  # stores SumUp's answer. Either order ends with one SUCCESSFUL payment, one receipt and one completion.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_21
  Scenario: A SumUp poll racing the card checkout completes the sale exactly once
    Given a POS card sale sale1 is prepared at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    When the card payment of POS sale sale1 is checked out while metasfresh polls SumUp, the stub approving the charge
    Then after not more than 30s, the card payment of POS sale sale1 has status SUCCESSFUL
    And after not more than 30s, POS sale sale1 is completed
    And the invoice of POS sale sale1 is paid
    And POS sale sale1 has 1 payment receipt
    And POS sale sale1 was completed 1 time

  # ##########################################################################
  # A card charge is sent only by the till's card checkout, never by completing the sale: completing a sale whose card
  # payment was not checked out yet (a status event or a direct completion request) sends nothing and leaves it waiting.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_22
  Scenario: Completing a sale does not send its card payment that was not checked out yet
    Given a POS card sale sale1 is prepared at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    When completing POS sale sale1 fails
    Then the card payment of POS sale sale1 has status NEW
    And the SumUp stub received 0 checkouts

  # ##########################################################################
  # The orphan rule is keyed on the current attempt: attempt 1 has a SumUp transaction (declined), the retry stops after
  # saving the payment. Attempt 1's transaction was created 1 s before the retry started, so it is not the retry's: the
  # retry becomes UNKNOWN after 150 s, and the check has nothing to ask SumUp (attempt 1's transaction is not looked up).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_23
  Scenario: A retried card checkout that stopped after saving the payment becomes UNKNOWN; the first attempt's transaction does not count
    Given a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    And SumUp API stub reports the transaction of POS sale sale1 as FAILED
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status FAILED
    When metasfresh's clock advances by 1 seconds
    And the till's card checkout of POS sale sale1 stops after saving the payment
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId empty
    When metasfresh's clock is 151 seconds after the card checkout of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    When the card payment of POS sale sale1 is checked
    Then the check of the card payment of POS sale sale1 returned checkFailed false
    And the card payment of POS sale sale1 has status UNKNOWN
    And the SumUp stub received 1 status lookup of the first transaction of POS sale sale1

  # ##########################################################################
  # Database-failure sub-case of the known limit: SumUp's answer could not be stored after 3 attempts, so the payment stays
  # PENDING without SumUp's id. Once the database works again, the orphan rule makes it UNKNOWN after 150 s.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_24
  Scenario: A card result that could not be stored becomes UNKNOWN after 150 seconds
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    And saving the card payment of a POS sale fails with a database error for the next 3 attempts
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId empty
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.CardResultNotStored'
    When saving the card payment of a POS sale works again
    And saving a SumUp transaction works again
    And metasfresh's clock is 151 seconds after the card checkout of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty

  # ##########################################################################
  # Cause (i) for two sales. The import of the first fails with an error (SumUp's answer cannot be read); the sweep still
  # imports the second one in the same poll (each card payment is swept on its own, oldest first).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_25
  Scenario: A failing import of one card payment does not stop the import of another in the same poll
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    Given SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale2 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale2 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    And the SumUp lookup of the first transaction of POS sale sale1 throws
    And SumUp API stub reports the transaction of POS sale sale2 as PENDING
    When metasfresh polls SumUp
    Then the SumUp transaction of POS sale sale2 is saved with status PENDING
    And the card payment of POS sale sale2 has status UNKNOWN
    And the card payment of POS sale sale1 has status UNKNOWN

  # ##########################################################################
  # Cause (i). SumUp is unreachable while the transaction is to be imported. One AD_Issue per outage: the
  # first failed lookup records it, the repeated ones do not; a successful lookup ends the outage, so the next outage (here
  # seen by the till's check, since the import already happened) records a new one.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_26
  Scenario: While SumUp cannot be asked, an UNKNOWN card payment stays UNKNOWN with one back-office record per outage
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    When SumUp API stub fails the status lookup
    And metasfresh polls SumUp 3 times
    Then the card payment of POS sale sale1 has status UNKNOWN
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.SumUpLookupFailed'
    And the card payment of POS sale sale1 is failing a SumUp lookup true
    When SumUp API stub answers the status lookup again
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    And metasfresh polls SumUp
    Then the SumUp transaction of POS sale sale1 is saved with status PENDING
    And the card payment of POS sale sale1 has status UNKNOWN
    And the card payment of POS sale sale1 is failing a SumUp lookup false
    When SumUp API stub fails the status lookup
    And the card payment of POS sale sale1 is checked
    And the card payment of POS sale sale1 is checked
    Then the check of the card payment of POS sale sale1 returned checkFailed true
    And the card payment of POS sale sale1 has status UNKNOWN
    And the card payment of POS sale sale1 has 2 AD_Issues with AD_Message 'de.metas.pos.POSPayment.SumUpLookupFailed'
    And the card payment of POS sale sale1 is failing a SumUp lookup true

  # ##########################################################################
  # SumUp accepted the checkout and its transaction was saved, but storing SumUp's answer on the card payment failed for all
  # 3 attempts: the payment stays PENDING without SumUp's id. It is not an orphan, because a SumUp transaction of this very
  # checkout exists (created since the checkout started): after 150 s it stays PENDING, and SumUp's later approval reaches
  # it through that transaction.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_27
  Scenario: A card payment whose SumUp transaction was saved is no orphan, even if SumUp's answer could not be stored on it
    Given SumUp API stub answers the next checkout with SUCCESS
    And saving the card payment of a POS sale fails with a database error for the next 3 attempts
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId empty
    And the SumUp transaction of POS sale sale1 is saved with status PENDING
    When saving the card payment of a POS sale works again
    And metasfresh's clock is 151 seconds after the card checkout of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status PENDING
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status SUCCESSFUL
    And POS sale sale1 is completed

  # ##########################################################################
  # Cause (i). SumUp answers that it does not know the transaction: that is an answer, not an outage. It ends an ongoing
  # outage, nothing is imported, the card payment stays UNKNOWN, and the check reports that SumUp could be asked.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_28
  Scenario: SumUp not knowing the transaction of an UNKNOWN card payment ends an outage and leaves the payment UNKNOWN
    Given saving a SumUp transaction fails with a database error
    And SumUp API stub answers the next checkout with SUCCESS
    When a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId set
    And saving a SumUp transaction works again
    When SumUp API stub fails the status lookup
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 is failing a SumUp lookup true
    When SumUp API stub answers the status lookup again
    And SumUp API stub does not know the first transaction of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status UNKNOWN
    And the card payment of POS sale sale1 is failing a SumUp lookup false
    And metasfresh has no SumUp transaction of POS sale sale1
    When the card payment of POS sale sale1 is checked
    Then the check of the card payment of POS sale sale1 returned checkFailed false
    And the card payment of POS sale sale1 has status UNKNOWN
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.SumUpLookupFailed'

  # ##########################################################################
  # A retried card checkout stopped after saving the payment (orphan): no SumUp transaction to refresh, so the reader is terminated.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_29
  Scenario: A retried card checkout that stopped and became UNKNOWN is released after the wait
    Given a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    And SumUp API stub reports the transaction of POS sale sale1 as FAILED
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status FAILED
    When metasfresh's clock advances by 1 seconds
    And the till's card checkout of POS sale sale1 stops after saving the payment
    And metasfresh's clock is 151 seconds after the card checkout of POS sale sale1
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    When metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And the card payment of POS sale sale1 is released by cashier metasfresh
    Then the SumUp stub received 1 terminate on reader reader-shopTill
    And the release of the card payment of POS sale sale1 returned status UNKNOWN with confirmationRequired true and checkFailed false
    When the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'

  # ##########################################################################
  # Cause (i). SumUp reports the charge FAILED (no poll): the release's refresh sees it final, so it does not terminate the
  # reader (it may run another till's checkout) and asks no confirmation. The status event is async.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_30
  Scenario: A release whose card payment already finished at SumUp neither terminates the reader nor releases anything
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
    When metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as FAILED
    And the card payment of POS sale sale1 is released by cashier metasfresh
    Then the SumUp stub received 0 terminates
    And the release of the card payment of POS sale sale1 returned confirmationRequired false and checkFailed false
    And metasfresh handled the SumUp events
    And the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'

  # ##########################################################################
  # Cause (i) twice: checkout A is released, the retry B is released too. A late approval of A is not booked, although the
  # payment remembers only B as released: a result of any checkout but the payment's current one is never applied.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_31
  Scenario: A late approval of an earlier released checkout is not booked after a second release
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
    When metasfresh's clock advances by 1 seconds
    And saving a SumUp transaction fails with a database error
    And the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId of the latest transaction
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 has 2 AD_Issues with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'
    When SumUp API stub reports the first transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 does not carry the card details of the first SumUp transaction
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    And the card payment of POS sale sale1 has no payment receipt

  # ##########################################################################
  # Cause (i) twice, then cause (ii): checkouts A and B are released, the third checkout C gets no answer (no SumUp id) and is
  # released too. A late approval of A is not booked: a released payment is never turned successful.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_32
  Scenario: A late approval of an earlier released checkout is not booked after a release of a checkout without SumUp id
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
    When metasfresh's clock advances by 1 seconds
    And saving a SumUp transaction fails with a database error
    And the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId of the latest transaction
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    When metasfresh's clock advances by 1 seconds
    And the card payment of POS sale sale1 is retried and the stub answers HTTP 5xx
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    When metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 has 3 AD_Issues with AD_Message 'de.metas.pos.POSPayment.ReleasedAsNotCharged'
    When SumUp API stub reports the first transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status FAILED
    And the card payment of POS sale sale1 does not carry the card details of the first SumUp transaction
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    And the card payment of POS sale sale1 has no payment receipt

  # ##########################################################################
  # Cause (i) twice, then cause (ii): checkouts A and B are released, the retry C gets no answer (no SumUp id) and awaits a
  # result. A late approval of A is not applied to C: SumUp created A long before C started.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_33
  Scenario: A late approval of an earlier released checkout is not applied to a later checkout still awaiting its result
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
    When metasfresh's clock advances by 1 seconds
    And saving a SumUp transaction fails with a database error
    And the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId of the latest transaction
    And saving a SumUp transaction works again
    And metasfresh's clock is 200 seconds after the card checkout of POS sale sale1
    And SumUp API stub reports the transaction of POS sale sale1 as PENDING
    When the card payment of POS sale sale1 is released by cashier metasfresh
    And the card payment of POS sale sale1 is released by cashier metasfresh with confirmation expecting UNKNOWN
    Then the card payment of POS sale sale1 has status FAILED
    When metasfresh's clock advances by 1 seconds
    And the card payment of POS sale sale1 is retried and the stub answers HTTP 5xx
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    When SumUp API stub reports the first transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status UNKNOWN and sumUpClientTransactionId empty
    And the card payment of POS sale sale1 does not carry the card details of the first SumUp transaction
    And the card payment of POS sale sale1 has 1 AD_Issue with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
    And the card payment of POS sale sale1 has no payment receipt

  # ##########################################################################
  # Cause (i), then the retry's own approval reaches the payment before the retry's SumUp id is stored on it (here: storing
  # SumUp's answer fails). The approval names the retry's SumUp transaction, so SumUp's refund of the retry's charge counts as
  # the retry's own result: it deletes the payment and is no late approval.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_34
  Scenario: After a release, a refund of the retry's charge approved before its SumUp id was stored deletes the card payment
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
    When metasfresh's clock advances by 1 seconds
    And saving the card payment of a POS sale fails with a database error for the next 3 attempts
    And the card payment of POS sale sale1 is retried and the stub answers SUCCESS
    And metasfresh handled the SumUp events
    And saving the card payment of a POS sale works again
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId empty
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status SUCCESSFUL
    When SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL refunded 2.50
    And SumUp calls back for the transaction of POS sale sale1
    Then the card payment of POS sale sale1 has status DELETED
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'

  # ##########################################################################
  # SumUp's clock runs more than 60 s behind metasfresh's, and SumUp's approval of the checkout reaches metasfresh before the
  # checkout stored SumUp's answer on the card payment (here: the first attempt of storing it failed). The approval then looks
  # older than the checkout, so it is not applied yet; storing SumUp's answer names the checkout's SumUp transaction, and its
  # approval, as metasfresh stores it, completes the sale. It is the sale's own charge, so it is no late approval.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_35
  Scenario: SumUp's approval reaching metasfresh before the checkout stored SumUp's answer completes the sale, also when SumUp's clock runs behind
    Given SumUp's clock runs 61 seconds behind metasfresh's
    And a POS card sale sale1 is prepared at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    And saving the card payment of a POS sale fails with a database error for the next 1 attempt
    When the card payment of POS sale sale1 is checked out, the checkout pausing before it retries storing SumUp's answer
    And SumUp API stub reports the transaction of POS sale sale1 as SUCCESSFUL
    And metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId empty
    When the till's card checkout of POS sale sale1 retries storing SumUp's answer
    Then the till's card checkout of POS sale sale1 answered the card payment with status SUCCESSFUL and sumUpClientTransactionId set
    And the card payment of POS sale sale1 has status SUCCESSFUL and sumUpClientTransactionId of the latest transaction
    And after not more than 30s, POS sale sale1 is completed
    And the invoice of POS sale sale1 is paid
    And POS sale sale1 has 1 payment receipt
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'

  # ##########################################################################
  # SumUp approves the charge and metasfresh saves the approval on its SumUp transaction, but the status event never reaches
  # the card payment (e.g. the system stopped right after saving it). The card payment still waits for the card reader; the
  # next SumUp poll applies the saved approval to it, so the sale completes. It is the sale's own charge: no late approval.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SALE_TC23_36
  Scenario: A saved SumUp approval whose status event never reached the card payment completes the sale with the next SumUp poll
    Given SumUp API stub answers the next checkout with SUCCESS
    And a POS card sale sale1 is checked out at shopTill for:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product1     | 2.50  | taxCategory07    |
    And the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId of the latest transaction
    When SumUp's approval of the transaction of POS sale sale1 is saved without its status event reaching the sale
    Then the card payment of POS sale sale1 has status PENDING and sumUpClientTransactionId of the latest transaction
    When metasfresh polls SumUp
    Then the card payment of POS sale sale1 has status SUCCESSFUL and sumUpClientTransactionId of the latest transaction
    And after not more than 30s, POS sale sale1 is completed
    And the invoice of POS sale sale1 is paid
    And POS sale sale1 has 1 payment receipt
    And the card payment of POS sale sale1 has 0 AD_Issues with AD_Message 'de.metas.pos.POSPayment.LateApprovalNotBooked'
