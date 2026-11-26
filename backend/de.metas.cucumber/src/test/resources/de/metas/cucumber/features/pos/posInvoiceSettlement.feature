@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS Invoice Settlement

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory19 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax19      | taxCategory19    | 19   | DE                       | DE                        | false           |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | PCE      |
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
      | priceListVersion       | product      | 100.00   | PCE      | taxCategory19    |
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

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC23
  Scenario: An open sales invoice is found by keying its numeric document number and settled in cash at the till
    # The till keypad enters digits only, so the search term is the trailing numeric part of the invoice number.
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DocumentNo          | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | invoice1   | invoiceCustomer | INV-POS-TC23-990123 | 2026-09-24   | Spot                     | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoiceL1  | invoice1     | product      | 1 PCE       |
    And the invoice identified by invoice1 is completed

    Then find open invoices at POS terminal till by document number '990123' returns:
      | C_Invoice_ID | C_BPartner_ID   | GrandTotal | OpenAmt |
      | invoice1     | invoiceCustomer | 119.00     | 119.00  |

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID | C_Payment_ID      |
      | invoice1     | settlementPayment |

    Then validate created invoices
      | C_Invoice_ID | DocStatus | IsPaid |
      | invoice1     | CO        | true   |
    And validate payments
      | C_Payment_ID      | IsReceipt | IsAllocated | C_BPartner_ID   | C_BP_BankAccount_ID | PayAmt | DocStatus | TenderType |
      | settlementPayment | true      | true        | invoiceCustomer | cashbook            | 119.00 | CO        | X          |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID      | Amount |
      | invoice1     | settlementPayment | 119.00 |
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount | Description                  |
      | CASH_INOUT | 119.00 | Rechnung INV-POS-TC23-990123 |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC24
  Scenario: A paid invoice, a credit memo and a drafted invoice sharing the searched document number are not offered
    Given metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID   | DocumentNo     | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | paidInvoice | invoiceCustomer | INV-NOTOFFERED | 2026-09-24   | Spot                     | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | paidInvoiceL1 | paidInvoice  | product      | 1 PCE       |
    And the invoice identified by paidInvoice is completed
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID   | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | fullPayment | invoiceCustomer | 119.00 EUR | true      | cashbook            |
    And the payment identified by fullPayment is completed
    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | paidInvoice  | fullPayment  |

    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DocumentNo     | DateInvoiced | C_ConversionType_ID.Name | C_DocTypeTarget_ID.Name | IsSOTrx | C_Currency_ID |
      | creditMemo | invoiceCustomer | INV-NOTOFFERED | 2026-09-24   | Spot                     | Gutschrift              | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | creditMemoL1 | creditMemo   | product      | 1 PCE       |
    And the invoice identified by creditMemo is completed

    # otherInvoiceCustomer is created here, not in the shared Background, because only this scenario uses it:
    # its per-run-unique C_BPartner.Value is truncated to whole-second precision (the 20-char identifier leaves
    # too few of the 40 chars for the sub-second part), so creating it once here avoids a same-second duplicate
    # Value across scenarios that all run the Background.
    Given metasfresh contains C_BPartners:
      | Identifier           | OPT.M_PricingSystem_ID |
      | otherInvoiceCustomer | pricingSystem          |
    Given metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID        | DocumentNo     | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | draftedInvoice | otherInvoiceCustomer | INV-NOTOFFERED | 2026-09-24   | Spot                     | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID   | M_Product_ID | QtyInvoiced |
      | draftedInvoiceL1 | draftedInvoice | product      | 1 PCE       |

    Then find open invoices at POS terminal till by document number 'INV-NOTOFFERED' returns no invoices

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC25
  Scenario: A cash-tendered amount below the open amount is rejected, leaving no payment behind
    # No hard-coded DocumentNo: completion assigns a unique sequence number, so the open invoice this scenario
    # leaves behind (the tender is rejected) never collides with an earlier run's leftover on a non-reset DB.
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | invoice2   | invoiceCustomer | 2026-09-24   | Spot                     | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoiceL2  | invoice2     | product      | 1 PCE       |
    And the invoice identified by invoice2 is completed

    Then find open invoices at POS terminal till by the document number of invoice2 returns:
      | C_Invoice_ID | C_BPartner_ID   | GrandTotal | OpenAmt |
      | invoice2     | invoiceCustomer | 119.00     | 119.00  |

    When settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceSettlement.TenderedTooLow':
      | C_Invoice_ID | CashTenderedAmount |
      | invoice2     | 50.00              |

    Then validate created invoices
      | C_Invoice_ID | DocStatus | IsPaid |
      | invoice2     | CO        | false  |
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC26
  Scenario: An already fully-paid invoice is rejected at settle time, leaving no payment behind
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | paidInv    | invoiceCustomer | 2026-09-24   | Spot                     | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | paidInvL   | paidInv      | product      | 1 PCE       |
    And the invoice identified by paidInv is completed
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID   | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payFull    | invoiceCustomer | 119.00 EUR | true      | cashbook            |
    And the payment identified by payFull is completed
    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | paidInv      | payFull      |

    # The invoice is fully paid, so the settle-time re-check (open amount <= 0) rejects it. This is distinct from
    # TC24's search-time filter: here the cashier settles a specific invoice by id (e.g. it was paid on another
    # till between the search and the confirmation), so only the re-read inside settleInCash can catch it.
    When settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceSettlement.NoLongerOpen':
      | C_Invoice_ID | CashTenderedAmount |
      | paidInv      | 119.00             |

    Then validate created invoices
      | C_Invoice_ID | DocStatus | IsPaid |
      | paidInv      | CO        | true   |
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC27
  Scenario: An invoice whose document number has a non-numeric prefix is found by keying only its numeric part
    # Real invoice numbers carry a fixed alphanumeric prefix (e.g. 'AR-'), but the till keypad enters only
    # digits — so the search matches the trailing keyed digits (DocumentNo ending with the entered number).
    # The invoice is settled here, not left open, so this scenario leaves no open leftover that a later run's
    # suffix search could collide with on the non-reset cucumber DB.
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID   | DocumentNo         | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | prefixedInvoice | invoiceCustomer | AR-POS-TC27-770245 | 2026-09-24   | Spot                     | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID    | M_Product_ID | QtyInvoiced |
      | prefixedInvL | prefixedInvoice | product      | 1 PCE       |
    And the invoice identified by prefixedInvoice is completed

    Then find open invoices at POS terminal till by document number '770245' returns:
      | C_Invoice_ID    | C_BPartner_ID   | GrandTotal | OpenAmt |
      | prefixedInvoice | invoiceCustomer | 119.00     | 119.00  |

    # A space-padded digit term is trimmed, then still matches the keyed suffix.
    And find open invoices at POS terminal till by document number ' 770245 ' returns:
      | C_Invoice_ID    | C_BPartner_ID   | GrandTotal | OpenAmt |
      | prefixedInvoice | invoiceCustomer | 119.00     | 119.00  |

    # A wildcard or any non-digit term is rejected (digits-only keypad), so it never LIKE-dumps the org.
    And find open invoices at POS terminal till by document number '%' returns no invoices
    And find open invoices at POS terminal till by document number 'POS-TC27' returns no invoices

    When the following invoices are settled in cash at POS terminal till by cashier metasfresh:
      | C_Invoice_ID    | C_Payment_ID          |
      | prefixedInvoice | settlementPaymentTC27 |

    Then validate created invoices
      | C_Invoice_ID    | DocStatus | IsPaid |
      | prefixedInvoice | CO        | true   |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC28
  Scenario: A completed PURCHASE invoice settled by id is rejected, leaving no payment and no journal line
    # The settle endpoint takes a client-supplied invoiceId that the org-scoped search would never have offered.
    # A same-org completed PURCHASE invoice (IsSOTrx=N) has an open amount, but auto-allocating an inbound receipt
    # to it would silently no-op (receipt vs purchase mismatch) — so the settle path must reject it up front,
    # before any cash is booked.
    # A purchase price list is needed so the AP invoice can complete (the Background only sets up a sales one).
    Given metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Currency_ID | SOTrx |
      | priceListPO | pricingSystem      | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier         | M_PriceList_ID |
      | priceListVersionPO | priceListPO    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersionPO     | product      | 100.00   | PCE      | taxCategory19    |
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID |
      | purchaseInvoice | invoiceCustomer | 2026-09-24   | Spot                     | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID    | M_Product_ID | QtyInvoiced |
      | purchaseInvoiceL | purchaseInvoice | product      | 1 PCE       |
    And the invoice identified by purchaseInvoice is completed

    When settling the following invoices in cash at POS terminal till by cashier metasfresh fails with AD_Message 'de.metas.pos.InvoiceSettlement.NotEligibleForSettlement':
      | C_Invoice_ID    | CashTenderedAmount |
      | purchaseInvoice | 119.00             |

    Then validate created invoices
      | C_Invoice_ID    | DocStatus | IsPaid |
      | purchaseInvoice | CO        | false  |
    And the cash journal of POS terminal till contains lines:
      | Type | Amount |
