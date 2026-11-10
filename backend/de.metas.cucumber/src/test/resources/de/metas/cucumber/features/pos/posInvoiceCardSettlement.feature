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
  # Card payment charges exactly the open amount on the terminal's default reader; the settlement waits (PENDING).
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
  # A definite rejection ends the settlement as FAILED; nothing is booked.
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
  # No charge is sent, and no settlement is created, when the invoice is no longer open or there is no journal.
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
  # While a card payment of the invoice is unfinished, no card and no cash payment of it can be started.
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
  # A timeout / connection / server error while sending leaves the result unknown, the settlement unfinished.
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

  # SumUp accepted the checkout (the reader may charge the card), but metasfresh failed right after, before it saved
  # the SumUp transaction: the result is unknown, not failed, and the AD_Issue names SumUp's client_transaction_id,
  # since metasfresh has no SumUp transaction to find the charge by.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC12_3
  Scenario: SumUp accepts the checkout but saving its transaction fails, so the result is unknown, cash is refused and the AD_Issue names the SumUp transaction
    Given POS terminal till is configured with SumUp and a default card reader
    And saving a SumUp transaction fails with a database error

    When card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    Then card settlement settlement1 has status UNKNOWN
    And the SumUp stub received 1 checkout with amount 16.07 on reader reader-till
    And the AD_Issue of card settlement settlement1 names the client transaction id of the last checkout the SumUp stub accepted
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    # the reader may still run the accepted checkout: the release cancels it, although metasfresh has no SumUp transaction
    When card settlement settlement1 was created 200 seconds ago
    And card settlement settlement1 is released by cashier metasfresh

    Then the SumUp stub received 1 terminate on reader reader-till
    And the release of card settlement settlement1 returned status UNKNOWN with confirmationRequired true and checkFailed false

  # ##########################################################################
  # The reader approves; the poller learns it and the approval is booked: a credit-card receipt allocated to
  # the invoice, and a card line on the open cash journal that raises the card total, not the cash total.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC1_2
  Scenario: The reader approves the card payment, so it is booked as a credit card receipt allocated to the invoice and as a card line of the cash journal
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the closing summary of the cash journal of POS terminal till shows card total 0 and cash total 100

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | true        |
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And the card payment line of card settlement settlement1 in the cash journal of POS terminal till has description 'Rechnung {DocumentNo} (Karte)' of invoice invoice1
    And the closing summary of the cash journal of POS terminal till shows card total 16.07 and cash total 100
    And card settlement settlement1 has 0 AD_Issues

  # Every SumUp status event follows a SumUp lookup (the poller's, the SumUp callback's, the till's check): the booking
  # it triggers does not ask SumUp again before allocating.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC1_3
  Scenario: SumUp's callback reports the approval, so the card payment is booked with that one SumUp lookup
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL

    When the SumUp transaction of card settlement settlement1 is updated from remote

    Then card settlement settlement1 has status SUCCESSFUL
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And the SumUp stub received 1 status lookup for card settlement settlement1

  # ##########################################################################
  # The reader declines or the customer cancels: nothing is booked, and the invoice can be paid again by card or cash.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC2_1
  Scenario: The reader declines the card, so nothing is booked, and a second card payment of the invoice is approved and booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh

    When SumUp API stub reports transaction of card settlement settlement1 as FAILED
    And metasfresh polls SumUp

    Then card settlement settlement1 has status FAILED
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When card settlement settlement2 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement2 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement2 has status SUCCESSFUL
    And card settlement settlement1 has status FAILED
    And card settlement settlement2 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | true        |
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement2
    And card settlement settlement1 has 0 AD_Issues
    And card settlement settlement2 has 0 AD_Issues

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC2_2
  Scenario: The reader declines the card, so nothing is booked, and the customer pays the invoice in cash instead
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh

    When SumUp API stub reports transaction of card settlement settlement1 as FAILED
    And metasfresh polls SumUp

    Then card settlement settlement1 has status FAILED
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the partner of invoice invoice1 has 1 payment
    And card settlement settlement1 has status FAILED

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC2_3
  Scenario: The card payment is cancelled on the reader, so the settlement is cancelled and nothing is booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh

    When SumUp API stub reports transaction of card settlement settlement1 as CANCELLED
    And metasfresh polls SumUp

    Then card settlement settlement1 has status CANCELLED
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
    And card settlement settlement1 has 0 AD_Issues

  # ##########################################################################
  # The same approval reported more than once books exactly one payment, one allocation and one journal line.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC11_1
  Scenario: The same approval is reported twice one after the other, and is booked once
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event

    When the SumUp status event of card settlement settlement1 is delivered 2 times one after the other

    Then card settlement settlement1 has status SUCCESSFUL
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And card settlement settlement1 has 0 AD_Issues

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC11_2
  Scenario: The same approval is reported twice at the same moment, and is booked once
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event

    When the SumUp status event of card settlement settlement1 is delivered 2 times at once

    Then card settlement settlement1 has status SUCCESSFUL
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And card settlement settlement1 has 0 AD_Issues

  # ##########################################################################
  # A charge refunded at SumUp before metasfresh booked it is not booked; the invoice can be paid again.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC22_1
  Scenario Outline: A charge refunded at SumUp before booking ends a <status> settlement as failed, and the invoice is paid in cash
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 is set to status <status> with isAbandoned false

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL refunded 16.07
    And metasfresh polls SumUp

    Then card settlement settlement1 has status FAILED
    And card settlement settlement1 has error message 'refunded at SumUp'
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

    Examples:
      | status |
      | PE     |
      | UN     |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC22_2
  Scenario: A charge refunded at SumUp before booking leaves an abandoned settlement abandoned, and the invoice is paid in cash
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 is set to status AB with isAbandoned true

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL refunded 16.07
    And metasfresh polls SumUp

    Then card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement1 has 0 AD_Issues
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When metasfresh polls SumUp

    Then card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement1 has 0 AD_Issues
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

  # SumUp reports a refunded charge also with the transaction status REFUNDED ("refunded in full or in part", with a
  # REFUND event): it is not booked either, and the settlement does not stay pending.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC22_3
  Scenario Outline: SumUp reports the charge of a <status> settlement as REFUNDED before booking, so the settlement ends as failed, and the invoice is paid in cash
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 is set to status <status> with isAbandoned false

    When SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07
    And metasfresh polls SumUp

    Then card settlement settlement1 has status FAILED
    And card settlement settlement1 has error message 'refunded at SumUp'
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

    Examples:
      | status |
      | PE     |
      | UN     |

  # ##########################################################################
  # The invoice was paid or reversed elsewhere while the card payment waited: the approved card payment is booked as
  # customer credit (an unallocated credit-card receipt) and the back office is notified, so it can refund it.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC7_1
  Scenario: The invoice is paid by a bank payment meanwhile, so the late card approval is booked as customer credit and the back office is notified
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains AD_Roles including the WebUI role:
      | AD_Role_ID |
      | backOffice |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | cashier               | metasfresh |
    And user has role
      | AD_User_ID | AD_Role_ID |
      | cashier    | backOffice |
    And role backOffice is the only role notified of group 'de.metas.pos.InvoiceCardSettlement'
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID   | PayAmt    | IsReceipt |
      | bankPayment | invoiceCustomer | 16.07 EUR | true      |
    And the payment identified by bankPayment is completed
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | bankPayment  | invoice1     |
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status CREDIT_REFUND_PENDING
    And card settlement settlement1 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | false       |
    And the partner of invoice invoice1 has 2 payments
    And invoice invoice1 has 1 allocation line
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And after not more than 30s, card settlement settlement1 has 1 notification with message 'de.metas.pos.InvoiceCardSettlement.CreditRefundPending'
    And card settlement settlement1 has 0 AD_Issues

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC7_2
  Scenario: The invoice is reversed meanwhile, so the late card approval is booked as customer credit and nothing is allocated to the reversed invoice
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains AD_Roles including the WebUI role:
      | AD_Role_ID |
      | backOffice |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | cashier               | metasfresh |
    And user has role
      | AD_User_ID | AD_Role_ID |
      | cashier    | backOffice |
    And role backOffice is the only role notified of group 'de.metas.pos.InvoiceCardSettlement'
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the invoice identified by invoice1 is reversed

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status CREDIT_REFUND_PENDING
    And card settlement settlement1 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | false       |
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And after not more than 30s, card settlement settlement1 has 1 notification with message 'de.metas.pos.InvoiceCardSettlement.CreditRefundPending'
    And card settlement settlement1 has 0 AD_Issues

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC7_3
  Scenario: The invoice is partly paid by a bank payment meanwhile, so the late card payment pays the rest and the surplus is booked as customer credit
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains AD_Roles including the WebUI role:
      | AD_Role_ID |
      | backOffice |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | cashier               | metasfresh |
    And user has role
      | AD_User_ID | AD_Role_ID |
      | cashier    | backOffice |
    And role backOffice is the only role notified of group 'de.metas.pos.InvoiceCardSettlement'
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID   | PayAmt   | IsReceipt |
      | bankPayment | invoiceCustomer | 6.07 EUR | true      |
    And the payment identified by bankPayment is completed
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | bankPayment  | invoice1     |

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status CREDIT_REFUND_PENDING
    And card settlement settlement1 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | false       |
    And the card payment of card settlement settlement1 is allocated with 10.00 to its invoice
    And invoice invoice1 has 2 allocation lines
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And after not more than 30s, card settlement settlement1 has 1 notification with message 'de.metas.pos.InvoiceCardSettlement.CreditRefundPending'
    And card settlement settlement1 has 0 AD_Issues

  # The card payment is visible in the payment allocation as soon as it is created, so the back office can allocate it
  # to another invoice of the customer before the booking is completed. The invoice then stays open although the payment
  # is used up: that is a booking error (not retryable, so BOOKING_ERROR at once), never customer credit.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC7_4
  Scenario: The card payment is allocated to another invoice before the booking is completed, so the booking fails and the invoice stays blocked
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains AD_Roles including the WebUI role:
      | AD_Role_ID |
      | backOffice |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | cashier               | metasfresh |
    And user has role
      | AD_User_ID | AD_Role_ID |
      | cashier    | backOffice |
    And role backOffice is the only role notified of group 'de.metas.pos.InvoiceCardSettlement'
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice2   | invoiceCustomer | 2026-09-24   | Spot                     | true    | EUR           | term1000         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice2L1 | invoice2     | product1     | 1 PCE       |
      | invoice2L2 | invoice2     | product2     | 1 PCE       |
    And the invoice identified by invoice2 is completed
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the payment of card settlement settlement1 is booked without being allocated
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | cardPayment  | invoice2     |

    When the SumUp status event of card settlement settlement1 is delivered 1 times one after the other

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has 1 AD_Issue
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | false  |
      | invoice2     | true   |
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
    And after not more than 1s, card settlement settlement1 has 0 notifications with message 'de.metas.pos.InvoiceCardSettlement.CreditRefundPending'

  # ##########################################################################
  # A card sale at the till is booked as a credit-card receipt on the till's cashbook (a cash bank); as it is not cash,
  # it gets no line in the cashbook's cash statement.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC10_1
  Scenario: A card sale at the till is booked as a credit card receipt on the cashbook, without a cash statement line
    Given metasfresh contains M_PricingSystems
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
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID  | C_BPartner_Location_ID | M_Warehouse_ID |
      | shopTill   | cashbook            | grossPricingSystem | grossPrice     | walkInCustomer | walkInCustomer         | warehouse      |
    And the cash journal of POS terminal shopTill is opened with 100 by metasfresh
    And POS terminal shopTill is configured with SumUp and a default card reader

    When a POS card sale is made at shopTill and approved on the card reader:
      | M_Product_ID | Price | C_TaxCategory_ID | Identifier |
      | product1     | 2.50  | taxCategory07    | cardSale   |

    Then validate payments
      | C_Payment_ID    | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | IsAllocated | IsOnBankStatement |
      | cardSaleReceipt | true      | CO        | C          | 2.50   | cashbook            | true        | false             |
    And the cash journal of POS terminal shopTill contains lines:
      | Type     | Amount |
      | CARD_PAY | 2.50   |

  # ##########################################################################
  # Booking errors after the reader approved. A non-retryable error ends the settlement as BOOKING_ERROR with
  # exactly one AD_Issue for back office; the invoice stays blocked, so it cannot be paid twice. The error is forced by
  # closing the accounting period (the payment, resp. the allocation, cannot be completed in it).
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_1
  Scenario: Completing the booking of an approved card payment fails, so the settlement is a booking error with its payment and the invoice stays blocked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the payment of card settlement settlement1 is booked without being allocated
    And card settlement settlement1 has status APPROVED
    And the accounting period is closed for today

    When the SumUp status event of card settlement settlement1 is delivered 1 times one after the other

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has 1 AD_Issue
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    When the SumUp status event of card settlement settlement1 is delivered 2 times one after the other

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has 1 AD_Issue
    And the partner of invoice invoice1 has 1 payment

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_2
  Scenario: Creating the payment of an approved card payment fails, so the settlement is a booking error without a payment and the invoice stays blocked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the accounting period is closed for today

    When the SumUp status event of card settlement settlement1 is delivered 1 times one after the other

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no payment
    And card settlement settlement1 has 1 AD_Issue
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    When the SumUp status event of card settlement settlement1 is delivered 2 times one after the other

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no payment
    And card settlement settlement1 has 1 AD_Issue

  # A retryable error (here: the invoice row stays locked by another transaction beyond the booking's lock timeout) is
  # retried by the background check of the next SumUp polls without an AD_Issue; the 5th failed attempt ends the
  # settlement as BOOKING_ERROR.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_3
  Scenario: The invoice stays locked by another transaction on 5 booking attempts, so the settlement ends as a booking error after the 5th
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the invoice row of invoice invoice1 is locked by another transaction

    When metasfresh polls SumUp 4 times

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has 4 booking attempts
    And card settlement settlement1 has 0 AD_Issues

    When metasfresh polls SumUp

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has 5 booking attempts
    And card settlement settlement1 has 1 AD_Issue
    And card settlement settlement1 has payment cardPayment
    And the other transaction releases its row lock
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  # The reader took the money for a settlement metasfresh had already given up on: back office gets exactly one AD_Issue,
  # however often the approval is reported, and nothing is booked.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_4
  Scenario: An approval reported repeatedly for a settlement that already failed is recorded once for back office and nothing is booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as FAILED
    And metasfresh polls SumUp
    And card settlement settlement1 has status FAILED
    And card settlement settlement1 has 0 AD_Issues
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event

    When the SumUp status event of card settlement settlement1 is delivered 3 times one after the other

    Then card settlement settlement1 has status FAILED
    And card settlement settlement1 has 1 AD_Issue
    And nothing is booked for invoice invoice1 and its open amount is 16.07

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_5
  Scenario: An approval reported repeatedly for a settlement that was already cancelled is recorded once for back office and nothing is booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as CANCELLED
    And metasfresh polls SumUp
    And card settlement settlement1 has status CANCELLED
    And card settlement settlement1 has 0 AD_Issues
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event

    When the SumUp status event of card settlement settlement1 is delivered 3 times one after the other

    Then card settlement settlement1 has status CANCELLED
    And card settlement settlement1 has 1 AD_Issue
    And nothing is booked for invoice invoice1 and its open amount is 16.07

  # ##########################################################################
  # The till's cash journal was closed between the approval and the booking: the booking waits, blocking,
  # with one AD_Issue however often it is retried, and completes on the next opened journal.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_1
  Scenario: The cash journal is closed when the approval arrives, so the booking waits and completes on the next opened journal
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has error message 'no open cash journal'
    And card settlement settlement1 has 1 AD_Issue
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    When metasfresh polls SumUp 2 times

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has 1 AD_Issue
    And card settlement settlement1 has 0 booking attempts

    When the cash journal of POS terminal till is opened with 50 by metasfresh
    And metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has no error message
    And card settlement settlement1 has 1 AD_Issue
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And the closing summary of the cash journal of POS terminal till shows card total 16.07 and cash total 50

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_2
  Scenario: The cash journal is closed when the approval arrives and the invoice is paid meanwhile, so the card payment becomes customer credit on the next opened journal
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'no open cash journal'
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID   | PayAmt    | IsReceipt |
      | bankPayment | invoiceCustomer | 16.07 EUR | true      |
    And the payment identified by bankPayment is completed
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | bankPayment  | invoice1     |

    When the cash journal of POS terminal till is opened with 50 by metasfresh
    And metasfresh polls SumUp

    Then card settlement settlement1 has status CREDIT_REFUND_PENDING
    And card settlement settlement1 has no error message
    And card settlement settlement1 has 1 AD_Issue
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And the closing summary of the cash journal of POS terminal till shows card total 16.07 and cash total 50

  # The charge is refunded at SumUp while the approved card payment waits for a journal: when a journal is opened, the
  # booking asks SumUp again and allocates and journals nothing. The card payment stays as it is (unallocated) and the
  # settlement is a booking error, so back office reconciles the payment with the refund.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_3
  Scenario: The card payment is approved while no cash journal is open and then refunded at SumUp, so after a journal is opened nothing is allocated or journaled and back office gets a booking error
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has error message 'no open cash journal'
    And SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07

    When the cash journal of POS terminal till is opened with 50 by metasfresh
    And metasfresh polls SumUp

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has error message 'refunded at SumUp'
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has 2 AD_Issues
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
    And the closing summary of the cash journal of POS terminal till shows card total 0 and cash total 50
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    # back office closes the case: it reverses the card payment of the refunded charge, then marks the settlement resolved
    When the payment identified by cardPayment is reversed
    And back office runs process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note 'charge refunded at SumUp, card payment reversed'

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has note 'charge refunded at SumUp, card payment reversed'
    And invoice invoice1 is not blocked by a card settlement

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

  # The refund is learned while the approved card payment still waits for a journal (here: the till's check asks SumUp):
  # it is reported at once as refunded, not only after a journal is opened.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_4
  Scenario: The card payment is approved while no cash journal is open and the till's check learns that it was refunded at SumUp, so it is a booking error at once and nothing is allocated or journaled
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has error message 'no open cash journal'
    And SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07

    When card settlement settlement1 is checked

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has error message 'refunded at SumUp'
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has 2 AD_Issues
    And invoice invoice1 has 0 allocation lines

  # Whether the charge was refunded is asked before allocating; while SumUp cannot be asked, nothing is allocated or
  # journaled: the approved card payment waits (APPROVED, "SumUp not reachable"), and a later check books or rejects it.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_5
  Scenario: A journal is opened for an approved card payment that was refunded at SumUp while SumUp cannot be asked, so a SumUp poll books nothing until SumUp answers again and then reports the refund
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07
    And SumUp API stub fails the status lookup
    And the cash journal of POS terminal till is opened with 50 by metasfresh

    When metasfresh polls SumUp

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'SumUp not reachable'
    And card settlement settlement1 has payment cardPayment
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When SumUp API stub answers the status lookup again
    And metasfresh polls SumUp

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has error message 'refunded at SumUp'
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_6
  Scenario: Back office updates an approved card payment that was refunded at SumUp from SumUp while SumUp cannot be asked, so nothing is booked until SumUp answers again and then the refund is reported
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07
    And SumUp API stub fails the status lookup
    And the cash journal of POS terminal till is opened with 50 by metasfresh

    When back office updates the SumUp transaction of card settlement settlement1 from SumUp

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'SumUp not reachable'
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When SumUp API stub answers the status lookup again
    And back office updates the SumUp transaction of card settlement settlement1 from SumUp

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has error message 'refunded at SumUp'
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_7
  Scenario: The till checks an approved card payment that was refunded at SumUp while SumUp cannot be asked, so nothing is booked and the check reports the failure
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And SumUp API stub reports transaction of card settlement settlement1 as REFUNDED refunded 16.07
    And SumUp API stub fails the status lookup
    And the cash journal of POS terminal till is opened with 50 by metasfresh

    When card settlement settlement1 is checked

    Then the check of card settlement settlement1 returned status APPROVED with checkFailed true
    And card settlement settlement1 has error message 'SumUp not reachable'
    And invoice invoice1 has 0 allocation lines
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  # While SumUp cannot be asked, each SumUp poll asks again before allocating; back office gets one AD_Issue for the
  # outage (when the settlement starts waiting with "SumUp not reachable"), not one per poll.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC20_8
  Scenario: SumUp cannot be asked in two consecutive SumUp polls before an approved card payment is booked, so back office gets one AD_Issue for the outage and the payment is booked once SumUp answers again
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'no open cash journal'
    And card settlement settlement1 has 1 AD_Issue
    And SumUp API stub fails the status lookup
    And the cash journal of POS terminal till is opened with 50 by metasfresh

    When metasfresh polls SumUp

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'SumUp not reachable'
    And card settlement settlement1 has 2 AD_Issues

    When metasfresh polls SumUp

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'SumUp not reachable'
    And card settlement settlement1 has 2 AD_Issues
    And invoice invoice1 has 0 allocation lines

    When SumUp API stub answers the status lookup again
    And metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has 2 AD_Issues

  # ##########################################################################
  # The system stopped between creating the card payment and completing the booking: the settlement waits as
  # APPROVED with its unallocated payment, still blocking, and the background check of the next SumUp poll completes it
  # with no second payment.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC17_1
  Scenario: The booking stopped after the card payment was created, so the next booking attempt completes it with the same payment
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the payment of card settlement settlement1 is booked without being allocated

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has payment cardPayment
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    When metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And card settlement settlement1 has 0 AD_Issues

  # ##########################################################################
  # Reconcile without the till: the background check that runs in every SumUp poll books an approval although
  # nobody reopens the dialog, also one whose status notification never reached the settlement.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC6_1
  Scenario: The reader approves after the dialog was closed and nobody reopens it, so the next SumUp poll books the card payment
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 has status PENDING

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has payment cardPayment
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And card settlement settlement1 has 0 AD_Issues

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC16_1
  Scenario: SumUp approved the card payment but no status notification reached the settlement, so the next SumUp poll books it
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And card settlement settlement1 has status PENDING

    When metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has payment cardPayment
    And the payment of card settlement settlement1 belongs to the client and organization of POS terminal till
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And card settlement settlement1 has 0 AD_Issues

  # A settlement whose checkout never got an answer has no SumUp transaction. Older than 150 s, it is an orphan: the
  # background check sets it UNKNOWN, so it still blocks the invoice and can be released. A definite
  # rejection of the checkout that still arrives later ends it as FAILED. The checkout is held by the stub, so the send is
  # still in flight while the settlement ages.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC18_1
  Scenario: A card settlement has no SumUp transaction for more than 150 s, so a SumUp poll sets it unknown, and the checkout rejected later fails it
    Given POS terminal till is configured with SumUp and a default card reader
    And SumUp API stub holds the next checkout
    And card settlement settlement1 is being started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 149 seconds ago

    When metasfresh polls SumUp

    Then card settlement settlement1 has status PENDING

    When card settlement settlement1 was created 151 seconds ago
    And metasfresh polls SumUp

    Then card settlement settlement1 has status UNKNOWN
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

    When SumUp API stub answers the held checkout with HTTP 4xx

    Then card settlement settlement1 has status FAILED
    And nothing is booked for invoice invoice1 and its open amount is 16.07

  # The till checks a settlement: the check asks SumUp for the current status first; if SumUp cannot be asked,
  # the status stays as it was and the till is told that the check failed.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC19_1
  Scenario: The SumUp status lookup fails when the till checks a card settlement, so its status is unchanged and the check reports the failure
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And SumUp API stub fails the status lookup

    When card settlement settlement1 is checked

    Then the check of card settlement settlement1 returned status PENDING with checkFailed true
    And card settlement settlement1 has status PENDING
    And the SumUp transaction of card settlement settlement1 has status PENDING
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When SumUp API stub answers the status lookup again
    And card settlement settlement1 is checked

    Then the check of card settlement settlement1 returned status SUCCESSFUL with checkFailed false
    And card settlement settlement1 has payment cardPayment
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1

  # The background check handles every settlement on its own: the booking of one settlement fails (its invoice row is
  # locked by another transaction beyond the booking's lock timeout), and another settlement is still booked in the
  # same poll; the next poll books the first one.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_SWEEP1
  Scenario: The booking of one approved card payment fails in a SumUp poll, and another approved card payment is still booked in the same poll
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice2   | invoiceCustomer | 2026-09-24   | Spot                     | true    | EUR           | term1000         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice2L1 | invoice2     | product1     | 1 PCE       |
      | invoice2L2 | invoice2     | product2     | 1 PCE       |
    And the invoice identified by invoice2 is completed
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement2 is started for invoice invoice2 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And SumUp API stub reports transaction of card settlement settlement2 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement2 is set to SUCCESSFUL locally without a status event
    And the invoice row of invoice invoice1 is locked by another transaction

    When metasfresh polls SumUp

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has 1 booking attempt
    And card settlement settlement2 has status SUCCESSFUL
    And the card payment of card settlement settlement2 is allocated with 16.07 to its invoice
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement2

    When the other transaction releases its row lock
    And metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
      | invoice2     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
      | CARD_PAY | 16.07  |

  # ##########################################################################
  # Release after the configured wait (AD_SysConfig de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds, default
  # 180 s, values below 150 s raised to 150 s). Earlier, the release is refused and nothing is sent to SumUp.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_1
  Scenario: A configured wait below 150 s is raised to 150 s, so a card payment started 100 s ago cannot be released yet
    Given temporarily set sys config int value 60 for sys config 'de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds'
    And POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 100 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed' for 150 seconds
    And card settlement settlement1 has status PENDING and isAbandoned false
    And the SumUp stub received 0 terminates

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_2
  Scenario: A configured wait of 300 s applies, so a card payment started 200 s ago cannot be released yet
    Given temporarily set sys config int value 300 for sys config 'de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds'
    And POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed' for 300 seconds
    And card settlement settlement1 has status PENDING and isAbandoned false
    And the SumUp stub received 0 terminates

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_3
  Scenario: Without a configured wait the default of 180 s applies: refused after 160 s, allowed after 181 s
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 160 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed' for 180 seconds
    And card settlement settlement1 has status PENDING and isAbandoned false

    When card settlement settlement1 was created 181 seconds ago
    And card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true
    And card settlement settlement1 has status PENDING and isAbandoned false

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_4
  Scenario: A configured wait below 150 s is raised to 150 s, so a card payment started 151 s ago can be released
    Given temporarily set sys config int value 60 for sys config 'de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds'
    And POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 151 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true

  # ##########################################################################
  # The release re-checks SumUp and acts on the result: approved → booked, failed → nothing to release, still pending
  # → released only after the cashier confirms that the card was not charged, with an AD_Issue for back office.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_5
  Scenario: SumUp approved the card payment meanwhile, so the release books it instead of releasing it
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 returned status SUCCESSFUL with confirmationRequired false
    And the SumUp stub received 0 terminates
    And card settlement settlement1 has status SUCCESSFUL and isAbandoned false
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And the partner of invoice invoice1 has 1 payment
    And card settlement settlement1 has 0 AD_Issues with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_6
  Scenario: SumUp declined the card payment meanwhile, so the release ends it as failed and the invoice can be paid in cash
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub reports transaction of card settlement settlement1 as FAILED

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 returned status FAILED with confirmationRequired false
    And the SumUp stub received 0 terminates
    And card settlement settlement1 has status FAILED and isAbandoned false
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

  # A terminate rejected by SumUp (4xx) is only logged: the release goes on.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_7
  Scenario: The card payment is still pending at SumUp, so it is released as abandoned only after the cashier confirms, and the invoice can be paid in cash
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub answers terminate with HTTP 4xx

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true and checkFailed false
    And card settlement settlement1 has status PENDING and isAbandoned false
    And card settlement settlement1 has 0 AD_Issues with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

    When the release of card settlement settlement1 is confirmed by cashier metasfresh

    Then the release of card settlement settlement1 returned status ABANDONED with confirmationRequired false
    And card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

  # The confirmation waits for the settlement lock at most the booking lock timeout: while another transaction (e.g. a
  # booking) holds it, nothing is released and the till gets the unchanged state, so it can ask again.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_8
  Scenario: The card settlement is locked by another transaction when the cashier confirms the release, so nothing is released until the lock is gone
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 returned status PENDING with confirmationRequired true
    And the row of card settlement settlement1 is locked by another transaction

    When the release of card settlement settlement1 is confirmed by cashier metasfresh

    Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true
    And card settlement settlement1 has status PENDING and isAbandoned false
    And card settlement settlement1 has 0 AD_Issues with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

    When the other transaction releases its row lock
    And the release of card settlement settlement1 is confirmed by cashier metasfresh

    Then the release of card settlement settlement1 returned status ABANDONED with confirmationRequired false
    And card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC13_9
  Scenario: The release cancels the checkout on the terminal's card reader before it re-checks SumUp
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the SumUp stub received 1 terminate on reader reader-till
    And the release of card settlement settlement1 returned status PENDING with confirmationRequired true

  # The confirmation releases only if the status is still the one the prompt showed; otherwise it returns the new state.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC14
  Scenario: SumUp approves the card payment between the release prompt and the confirmation, so the confirmation returns the booked payment and releases nothing
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 returned status PENDING with confirmationRequired true
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    When the release of card settlement settlement1 is confirmed by cashier metasfresh

    Then the release of card settlement settlement1 returned status SUCCESSFUL with confirmationRequired false
    And card settlement settlement1 has status SUCCESSFUL and isAbandoned false
    And card settlement settlement1 has 0 AD_Issues with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC19_2
  Scenario: The SumUp status lookup fails during the release, so the status is unchanged and the release still needs the cashier's confirmation
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub fails the status lookup

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 returned status PENDING with confirmationRequired true and checkFailed true
    And card settlement settlement1 has status PENDING and isAbandoned false

  # An orphan (the system stopped between saving and sending, so no SumUp transaction exists) is releasable: the
  # terminate still goes to the terminal's default reader, the re-check has nothing to ask SumUp about.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC18_2
  Scenario: A card settlement without a SumUp transaction is unknown, and the cashier releases it as abandoned after confirming, so the invoice can be paid in cash
    Given POS terminal till is configured with SumUp and a default card reader
    And SumUp API stub holds the next checkout
    And card settlement settlement1 is being started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 151 seconds ago
    And metasfresh polls SumUp
    And card settlement settlement1 has status UNKNOWN
    And card settlement settlement1 was created 200 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the SumUp stub received 1 terminate on reader reader-till
    And the release of card settlement settlement1 returned status UNKNOWN with confirmationRequired true and checkFailed false

    When the release of card settlement settlement1 is confirmed by cashier metasfresh

    Then the release of card settlement settlement1 returned status ABANDONED with confirmationRequired false
    And card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

    When SumUp API stub answers the held checkout with HTTP 4xx

    Then card settlement settlement1 has status ABANDONED and isAbandoned true

  # Release is refused once SumUp approved the card payment: the money was taken and the booking is pending.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_6
  Scenario: SumUp approved the card payment but its booking cannot complete yet, so the release is refused
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the invoice row of invoice invoice1 is locked by another transaction

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseRefusedApproved'
    And card settlement settlement1 has status APPROVED and isAbandoned false
    And card settlement settlement1 has 0 AD_Issues with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'
    And the other transaction releases its row lock

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC8_7
  Scenario: The booking of an approved card payment failed, so the release is refused and the settlement stays a booking error
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 was created 200 seconds ago

    When card settlement settlement1 is released by cashier metasfresh

    Then the release of card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleaseRefusedApproved'
    And card settlement settlement1 has status BOOKING_ERROR and isAbandoned false
    And card settlement settlement1 has no payment
    And card settlement settlement1 has 1 AD_Issue
    And the SumUp stub received 0 terminates

  # ##########################################################################
  # Late approval of a card payment the cashier released as abandoned: SumUp approves it after the release, so the
  # money was taken and the approval is still booked. The settlement keeps IsAbandoned, so it never blocks the invoice
  # again, whatever its booking ends in.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC15_1
  Scenario: SumUp approves a card payment released as abandoned while the invoice is still open, so the card payment is allocated to the invoice
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub answers terminate with HTTP 4xx
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 is confirmed by cashier metasfresh
    And card settlement settlement1 has status ABANDONED and isAbandoned true

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status SUCCESSFUL and isAbandoned true
    And card settlement settlement1 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | true        |
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And card settlement settlement1 has 1 AD_Issue
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC15_2
  Scenario: SumUp approves a card payment released as abandoned after the invoice was paid in cash, so the card payment is booked as customer credit and the back office is notified
    Given POS terminal till is configured with SumUp and a default card reader
    And metasfresh contains AD_Roles including the WebUI role:
      | AD_Role_ID |
      | backOffice |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | cashier               | metasfresh |
    And user has role
      | AD_User_ID | AD_Role_ID |
      | cashier    | backOffice |
    And role backOffice is the only role notified of group 'de.metas.pos.InvoiceCardSettlement'
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub answers terminate with HTTP 4xx
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 is confirmed by cashier metasfresh
    And card settlement settlement1 has status ABANDONED and isAbandoned true
    And the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status CREDIT_REFUND_PENDING and isAbandoned true
    And card settlement settlement1 has payment cardPayment
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | false       |
    And the partner of invoice invoice1 has 2 payments
    And invoice invoice1 has 1 allocation line
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount |
      | CASH_INOUT | 16.07  |
      | CARD_PAY   | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And after not more than 30s, card settlement settlement1 has 1 notification with message 'de.metas.pos.InvoiceCardSettlement.CreditRefundPending'
    And card settlement settlement1 has 1 AD_Issue
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'

  # A newer card payment of the same invoice was started after the release and is still waiting when SumUp approves
  # both at the same moment. Both bookings take the invoice lock before allocating, so they run one after the other:
  # the first to get the lock pays the invoice, the second finds it paid and books customer credit. Both payments are
  # booked; the invoice is paid exactly once.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC15_3
  Scenario: SumUp approves a card payment released as abandoned and a newer card payment of the same invoice at once, so both are booked and the second to finish as customer credit
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub answers terminate with HTTP 4xx
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 is confirmed by cashier metasfresh
    And card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement2 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement2 has status PENDING and isAbandoned false
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And SumUp API stub reports transaction of card settlement settlement2 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement2 is set to SUCCESSFUL locally without a status event

    When the SumUp status events of card settlements settlement1 and settlement2 are delivered at once

    Then after not more than 30s of SumUp polls, of card settlements settlement1 and settlement2, one has status SUCCESSFUL and the other one status CREDIT_REFUND_PENDING
    And card settlement settlement1 has payment cardPayment1
    And card settlement settlement2 has payment cardPayment2
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   |
      | cardPayment1 | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer |
      | cardPayment2 | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer |
    And the partner of invoice invoice1 has 2 payments
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement2
    And card settlement settlement1 has 1 AD_Issue
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'
    And card settlement settlement2 has 0 AD_Issues

    And card settlement settlement1 has isAbandoned true
    And card settlement settlement2 has isAbandoned false

    When metasfresh polls SumUp

    Then of card settlements settlement1 and settlement2, one has status SUCCESSFUL and the other one status CREDIT_REFUND_PENDING
    And the partner of invoice invoice1 has 2 payments
    And invoice invoice1 has 1 allocation line
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
      | CARD_PAY | 16.07  |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC15_4
  Scenario: The booking of a card payment released as abandoned and approved later fails, so it is a booking error for back office and the invoice stays free for another payment
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub answers terminate with HTTP 4xx
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 is confirmed by cashier metasfresh
    And card settlement settlement1 has status ABANDONED and isAbandoned true
    And the accounting period is closed for today

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp

    Then card settlement settlement1 has status BOOKING_ERROR and isAbandoned true
    And card settlement settlement1 has no payment
    And card settlement settlement1 has 2 AD_Issues
    And card settlement settlement1 has 1 AD_Issue with AD_Message 'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned'
    And nothing is booked for invoice invoice1 and its open amount is 16.07

    When metasfresh polls SumUp

    Then card settlement settlement1 has status BOOKING_ERROR and isAbandoned true
    And card settlement settlement1 has 2 AD_Issues

    When card settlement settlement2 is started for invoice invoice1 at POS terminal till by cashier metasfresh

    Then card settlement settlement2 has status PENDING and isAbandoned false
    And invoice invoice1 has 2 card settlements
    And the open invoice list of POS terminal till for invoice invoice1 shows blocking card settlement settlement2
    And card settlement settlement1 has status BOOKING_ERROR and isAbandoned true

  # ##########################################################################
  # Back office resolves card settlements in the card settlement window: "retry booking" re-runs the booking of a
  # booking error (or of an approved settlement that waits for a journal) with a fresh attempt budget, "mark resolved
  # manually" ends a booking error that back office fixed by hand, "mark refunded" ends a customer credit that back
  # office refunded. Each process is offered only for the statuses it applies to.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_1
  Scenario: The booking of an approved card payment failed after 5 attempts, and back office retries it with a fresh attempt budget, so it is booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the invoice row of invoice invoice1 is locked by another transaction
    And metasfresh polls SumUp 5 times
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has 5 booking attempts
    And card settlement settlement1 has 1 AD_Issue
    And card settlement settlement1 has payment cardPayment
    And process C_POS_InvoiceCardSettlement_RetryBooking is available for card settlement settlement1
    And process C_POS_InvoiceCardSettlement_MarkResolvedManually is available for card settlement settlement1
    And process C_POS_InvoiceCardSettlement_MarkRefunded is not available for card settlement settlement1

    # the invoice is still locked: the retry fails retryably, and with a fresh budget that is its 1st attempt, not a booking error
    When back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1

    Then the back-office process reports AD_Message 'de.metas.pos.InvoiceCardSettlement.RetryBookingWillRetry'
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has 1 booking attempt
    And card settlement settlement1 has 1 AD_Issue

    When the other transaction releases its row lock
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |
    And back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has no error message
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And card settlement settlement1 has 1 AD_Issue
    And process C_POS_InvoiceCardSettlement_RetryBooking is not available for card settlement settlement1
    And process C_POS_InvoiceCardSettlement_MarkResolvedManually is not available for card settlement settlement1

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_2
  Scenario: Creating the payment of an approved card payment failed, and back office retries the booking, so the payment is created and booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no payment
    And card settlement settlement1 has 1 AD_Issue

    # the cause is not fixed yet: the retry fails again, is recorded for back office once more, and back office is told
    When back office running process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.RetryBookingNotCompleted'

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no payment
    And card settlement settlement1 has 2 AD_Issues

    When the accounting period is reopened
    And back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has payment cardPayment
    And card settlement settlement1 has 0 booking attempts
    And validate payments
      | C_Payment_ID | IsReceipt | DocStatus | TenderType | PayAmt | C_BP_BankAccount_ID | C_BPartner_ID   | IsAllocated |
      | cardPayment  | true      | CO        | C          | 16.07  | cashbook            | invoiceCustomer | true        |
    And the partner of invoice invoice1 has 1 payment
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And card settlement settlement1 has 2 AD_Issues

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_3
  Scenario: Creating the payment of an approved card payment failed and back office refunded the charge at SumUp, so the retry fails the settlement without booking and the invoice can be paid again
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no payment
    And the accounting period is reopened
    # back office refunded the charge in the SumUp dashboard; the retry asks SumUp before it creates a payment
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL refunded 16.07

    When back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1

    Then card settlement settlement1 has status FAILED
    And card settlement settlement1 has error message 'refunded at SumUp'
    And card settlement settlement1 has no payment
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_4
  Scenario: Creating the payment of a card payment released as abandoned and approved later failed, and back office refunded the charge at SumUp, so the retry leaves it abandoned without booking
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And card settlement settlement1 was created 200 seconds ago
    And SumUp API stub answers terminate with HTTP 4xx
    And card settlement settlement1 is released by cashier metasfresh
    And the release of card settlement settlement1 is confirmed by cashier metasfresh
    And the accounting period is closed for today
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status BOOKING_ERROR and isAbandoned true
    And card settlement settlement1 has no payment
    And the accounting period is reopened
    # back office refunded the charge in the SumUp dashboard
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL refunded 16.07

    When back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1

    Then card settlement settlement1 has status ABANDONED and isAbandoned true
    And card settlement settlement1 has no payment
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID |
      | invoice1     |

    Then validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_5
  Scenario: Completing the booking of an approved card payment failed and back office allocated the payment by hand, so it marks the settlement resolved with a note and nothing more is booked
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the payment of card settlement settlement1 is booked without being allocated
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has payment cardPayment
    And the accounting period is reopened
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | cardPayment  | invoice1     |

    When back office runs process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note 'card payment allocated by hand'

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has note 'card payment allocated by hand'
    And invoice invoice1 is not blocked by a card settlement
    And the partner of invoice invoice1 has 1 payment
    And invoice invoice1 has 1 allocation line
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice1     | true   |
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
    And card settlement settlement1 has 1 AD_Issue
    And process C_POS_InvoiceCardSettlement_MarkResolvedManually is not available for card settlement settlement1

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_6
  Scenario: Completing the booking of an approved card payment failed and the payment is still unallocated, so marking the settlement resolved is refused while the invoice is open
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the payment of card settlement settlement1 is booked without being allocated
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has payment cardPayment
    And the accounting period is reopened

    When back office running process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note 'resolved' is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused'

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no note
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_7
  Scenario: Creating the payment of an approved card payment failed, so marking the settlement resolved is refused while the invoice is open, and retry booking stays available
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no payment

    When back office running process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note 'resolved' is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused'

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no note
    And nothing is booked for invoice invoice1 and its open amount is 16.07
    And settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice1     | 16.07              |
    And process C_POS_InvoiceCardSettlement_RetryBooking is available for card settlement settlement1

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_8
  Scenario: An approved card payment waits for an open cash journal with its payment unallocated, so marking it resolved is refused while the invoice is open
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'no open cash journal'
    And process C_POS_InvoiceCardSettlement_MarkResolvedManually is available for card settlement settlement1

    When back office running process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note 'resolved' is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused'

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has no note
    And the card payment of card settlement settlement1 is allocated with 0 to its invoice

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_9
  Scenario: An approved card payment waits for an open cash journal, a journal is opened, and back office retries the booking, so it is booked at once on the new journal
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And the cash journal of POS terminal till is closed with 100 by metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'no open cash journal'
    And card settlement settlement1 has 1 AD_Issue
    And process C_POS_InvoiceCardSettlement_RetryBooking is available for card settlement settlement1

    # no journal is open yet: the retry cannot complete the booking, and back office is told
    When back office running process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1 is refused with AD_Message 'de.metas.pos.InvoiceCardSettlement.RetryBookingNotCompleted'

    Then card settlement settlement1 has status APPROVED
    And card settlement settlement1 has error message 'no open cash journal'
    And card settlement settlement1 has 1 AD_Issue

    When the cash journal of POS terminal till is opened with 50 by metasfresh
    And back office runs process C_POS_InvoiceCardSettlement_RetryBooking on card settlement settlement1

    Then card settlement settlement1 has status SUCCESSFUL
    And card settlement settlement1 has no error message
    And card settlement settlement1 has 1 AD_Issue
    And the partner of invoice invoice1 has 1 payment
    And the card payment of card settlement settlement1 is allocated with 16.07 to its invoice
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CARD_PAY | 16.07  |
    And the cash journal of POS terminal till has 1 card payment line referencing card settlement settlement1
    And the closing summary of the cash journal of POS terminal till shows card total 16.07 and cash total 50

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC21_10
  Scenario: Back office marks a booking error resolved without a note, so it is refused
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is set to SUCCESSFUL locally without a status event
    And the payment of card settlement settlement1 is booked without being allocated
    And the accounting period is closed for today
    And the SumUp status event of card settlement settlement1 is delivered 1 times one after the other
    And card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has payment cardPayment
    And the accounting period is reopened
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | cardPayment  | invoice1     |

    When back office running process C_POS_InvoiceCardSettlement_MarkResolvedManually on card settlement settlement1 with note '  ' is refused because the note is missing

    Then card settlement settlement1 has status BOOKING_ERROR
    And card settlement settlement1 has no note

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC15_5
  Scenario: A late card approval was booked as customer credit and back office refunded it, so it marks the settlement refunded with a note and the settlement leaves the refund worklist
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID   | PayAmt    | IsReceipt |
      | bankPayment | invoiceCustomer | 16.07 EUR | true      |
    And the payment identified by bankPayment is completed
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | bankPayment  | invoice1     |
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status CREDIT_REFUND_PENDING
    And card settlement settlement1 is listed under status CREDIT_REFUND_PENDING
    And process C_POS_InvoiceCardSettlement_MarkRefunded is available for card settlement settlement1
    And process C_POS_InvoiceCardSettlement_RetryBooking is not available for card settlement settlement1
    And process C_POS_InvoiceCardSettlement_MarkResolvedManually is not available for card settlement settlement1

    When back office running process C_POS_InvoiceCardSettlement_MarkRefunded on card settlement settlement1 with note '' is refused because the note is missing

    Then card settlement settlement1 has status CREDIT_REFUND_PENDING
    And card settlement settlement1 has no note

    When back office runs process C_POS_InvoiceCardSettlement_MarkRefunded on card settlement settlement1 with note 'refunded to the customer in cash'

    Then card settlement settlement1 has status REFUNDED
    And card settlement settlement1 has note 'refunded to the customer in cash'
    And card settlement settlement1 is not listed under status CREDIT_REFUND_PENDING
    And card settlement settlement1 is listed under status REFUNDED
    And process C_POS_InvoiceCardSettlement_MarkRefunded is not available for card settlement settlement1

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_TC15_6
  Scenario: A late card payment partly paid the invoice and the surplus is customer credit, and back office refunded the surplus, so it marks the settlement refunded with a note
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 is started for invoice invoice1 at POS terminal till by cashier metasfresh
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID   | PayAmt   | IsReceipt |
      | bankPayment | invoiceCustomer | 6.07 EUR | true      |
    And the payment identified by bankPayment is completed
    And allocate payments to invoices
      | C_Payment_ID | C_Invoice_ID |
      | bankPayment  | invoice1     |
    And SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And metasfresh polls SumUp
    And card settlement settlement1 has status CREDIT_REFUND_PENDING
    And the card payment of card settlement settlement1 is allocated with 10.00 to its invoice
    And card settlement settlement1 is listed under status CREDIT_REFUND_PENDING
    And process C_POS_InvoiceCardSettlement_MarkRefunded is available for card settlement settlement1

    When back office runs process C_POS_InvoiceCardSettlement_MarkRefunded on card settlement settlement1 with note 'surplus of 6.07 refunded at SumUp'

    Then card settlement settlement1 has status REFUNDED
    And card settlement settlement1 has note 'surplus of 6.07 refunded at SumUp'
    And card settlement settlement1 is not listed under status CREDIT_REFUND_PENDING
    And the card payment of card settlement settlement1 is allocated with 10.00 to its invoice
