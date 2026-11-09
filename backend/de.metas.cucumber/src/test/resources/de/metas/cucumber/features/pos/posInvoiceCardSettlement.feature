@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS invoice card settlement

  # Data shape: one sales invoice (ARI) mirroring the reference invoice used for the card-settlement test cases:
  # EUR, prices not tax-included, 2 lines at 7 % tax, grand total = open amount 16,07, payment term with value 1000,
  # no allocations. The reference probe returns only the total, so the line split 10,00 + 5,02 (net, tax 1,05) is
  # DERIVED from 16,07 at 7 %; it is not taken from the reference system.
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
      | product2   | PCE      |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product1     | 10.00    | PCE      | taxCategory07    |
      | priceListVersion       | product2     | 5.02     | PCE      | taxCategory07    |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID | IsCashBank |
      | cashbook   | EUR           | Y          |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.M_PricingSystem_ID |
      | walkInCustomer  |                        |
      | invoiceCustomer | pricingSystem          |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID  | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | cashbook            | pricingSystem      | priceList      | walkInCustomer | walkInCustomer         | warehouse      |
    And the cash journal of POS terminal till is opened with 100 by metasfresh
    And metasfresh contains C_PaymentTerm
      | Identifier | Value | Name      | NetDays |
      | term1000   | 1000  | Term 1000 | 0       |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice1   | invoiceCustomer | 2026-09-24   | Spot                     | true    | EUR           | term1000         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoiceL1  | invoice1     | product1     | 1 PCE       |
      | invoiceL2  | invoice1     | product2     | 1 PCE       |
    And the invoice identified by invoice1 is completed

  # ##########################################################################
  # Proves the cucumber SumUp API stub works end to end and that the settlement reference of a card checkout
  # survives a DB reload of the SumUp transaction.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_STUB1
  Scenario: The SumUp API stub answers a settlement checkout and the transaction keeps its settlement reference after a reload
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 exists for invoice invoice1 at POS terminal till by cashier metasfresh

    When a SumUp card checkout is sent for card settlement settlement1
    Then the SumUp stub received 1 checkout with amount 16.07 on reader reader-till
    And the SumUp transaction of card settlement settlement1 has status PENDING

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is updated from remote
    Then the SumUp transaction of card settlement settlement1 has status SUCCESSFUL

  # ##########################################################################
  # AC2: Kartenzahlung charges exactly the open amount on the terminal's default reader; the settlement waits (PENDING).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC1
  Scenario: Starting a card settlement sends the open amount to the default reader and waits for the result
    Given POS terminal till is configured with SumUp and a default card reader

    When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    Then card settlement settlement1 has status PENDING
    And the SumUp stub received 1 checkout with amount 16.07 on reader reader-till
    And the SumUp stub received a checkout described by card settlement settlement1
    And the SumUp transaction of card settlement settlement1 has status PENDING

  # ##########################################################################
  # AC5: a definite rejection ends the settlement as FAILED; nothing is booked.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC3_1
  Scenario: SumUp rejects the checkout with a 4xx error, so the settlement fails and nothing is booked
    Given POS terminal till is configured with SumUp and a default card reader
    And SumUp API stub answers the next checkout with HTTP 4xx

    When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    Then card settlement settlement1 has status FAILED
    And card settlement settlement1 has 1 AD_Issue
    And the SumUp stub received 1 checkout
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC3_2
  Scenario: The terminal has no default card reader, so the settlement fails before anything is sent
    Given POS terminal till is configured with SumUp and no default card reader

    When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    Then card settlement settlement1 has status FAILED
    And card settlement settlement1 has 1 AD_Issue
    And the SumUp stub received 0 checkouts
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  # ##########################################################################
  # AC6: no charge is sent, and no settlement is created, when the invoice is no longer open or there is no journal.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC4_1
  Scenario: The invoice was paid in cash meanwhile, so the card start is refused and nothing is sent
    Given POS terminal till is configured with SumUp and a default card reader
    And the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then starting a card settlement for invoice invoice1 at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceSettlement.NoLongerOpen'
    And the SumUp stub received 0 checkouts
    And invoice invoice1 has 0 card settlements

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC4_2
  Scenario: The terminal has no open cash journal, so the card start is refused and nothing is sent
    Given metasfresh contains C_POS:
      | Identifier    | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID  | C_BPartner_Location_ID | M_Warehouse_ID |
      | tillNoJournal | cashbook            | pricingSystem      | priceList      | walkInCustomer | walkInCustomer         | warehouse      |
    And POS terminal tillNoJournal is configured with SumUp and a default card reader

    Then starting a card settlement for invoice invoice1 at POS terminal tillNoJournal by cashier metasfresh fails with AD_Message 'de.metas.pos.CashJournalNotOpen'
    And the SumUp stub received 0 checkouts
    And invoice invoice1 has 0 card settlements

  # ##########################################################################
  # AC7: while a card payment of the invoice is unfinished, no card and no cash payment of it can be started.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC5_1
  Scenario: A pending card settlement blocks a second card start on another terminal and the cash path
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID  | C_BPartner_Location_ID | M_Warehouse_ID |
      | till2      | cashbook            | pricingSystem      | priceList      | walkInCustomer | walkInCustomer         | warehouse      |
    And the cash journal of POS terminal till2 is opened with 100 by metasfresh
    And POS terminal till2 is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh

    Then starting a card settlement for invoice invoice1 at POS terminal till2 by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen'
    And settling the following invoices in cash at POS terminal till2 by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |
    And the open invoice list of POS terminal till2 for invoice invoice1 shows blocking card settlement settlement1
    And the SumUp stub received 1 checkout
    And card settlement settlement1 has status PENDING

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC5_2
  Scenario Outline: A card settlement in status <status> that is not abandoned blocks a second card start
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 is set to status <status> with isAbandoned false

    Then starting a card settlement for invoice invoice1 at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen'
    And the SumUp stub received 1 checkout

    Examples:
      | status |
      | PE     |
      | UN     |
      | AP     |
      | BE     |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC5_3
  Scenario: Two card starts of the same invoice at once yield one pending settlement and one refusal
    Given POS terminal till is configured with SumUp and a default card reader

    When two card settlements are started at once for invoice invoice1 at POS terminal till by cashier metasfresh
    Then exactly one of them succeeded with status PENDING and the other failed with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen'
    And invoice invoice1 has 1 card settlements
    And the SumUp stub received 1 checkout

  # ##########################################################################
  # AC11: a timeout / connection / server error while sending leaves the result unknown, the settlement unfinished.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC12_1
  Scenario: SumUp answers the checkout with a server error, so the result is unknown and cash is refused
    Given POS terminal till is configured with SumUp and a default card reader
    And SumUp API stub answers the next checkout with HTTP 5xx

    When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    Then card settlement settlement1 has status UNKNOWN
    And card settlement settlement1 has 1 AD_Issue
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC12_2
  Scenario: The connection is reset during the checkout, so the result is unknown and cash is refused
    Given POS terminal till is configured with SumUp and a default card reader
    And SumUp API stub answers the next checkout with CONNECTION_RESET

    When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    Then card settlement settlement1 has status UNKNOWN
    And card settlement settlement1 has 1 AD_Issue
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |
