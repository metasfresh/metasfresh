@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS Product Return

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]
    And metasfresh contains M_Warehouse:
      | Identifier       | IsQualityReturnWarehouse |
      | warehouse        |                          |
      | qualityWarehouse | Y                        |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | KGM      |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax7       | taxCategory      | 7    | DE                       | DE                        | false           |
    And metasfresh contains M_PricingSystems
      | Identifier          |
      | pricingSystem       |
      | walkInPricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID  | C_Currency_ID | SOTrx | IsTaxIncluded |
      | priceList       | pricingSystem       | EUR           | true  | true          |
      | walkInPriceList | walkInPricingSystem | EUR           | true  | true          |
    And metasfresh contains M_PriceList_Versions
      | Identifier             | M_PriceList_ID  |
      | priceListVersion       | priceList       |
      | walkInPriceListVersion | walkInPriceList |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_Product_ID | M_PriceList_Version_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | productPrice       | product      | priceListVersion       | 15.50    | KGM      | taxCategory      |
      | walkInProductPrice | product      | walkInPriceListVersion | 20.00    | KGM      | taxCategory      |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID |
      | cashbook   | EUR           |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.M_PricingSystem_ID |
      | customer   | walkInPricingSystem    |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | cashbook            | pricingSystem      | priceList      | customer      | customer               | warehouse      |
    And the cash journal of POS terminal till is opened with 100 by metasfresh

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC11
  Scenario: A returned product is received and its credit priced at the till price
    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.C_Invoice_ID |
      | product      | 0.3 | KGM | return_1       | creditMemo       |

    Then validate the created material receipt
      | M_InOut_ID | DocStatus | M_Warehouse_ID   | MovementType | OPT.C_Order_ID |
      | return_1   | CO        | qualityWarehouse | C+           | null           |
    And validate the created material receipt lines
      | M_InOut_ID | M_Product_ID | movementqty | OPT.Return_Origin_InOutLine_ID |
      | return_1   | product      | 0.3         | null                           |
    And load HUs assigned to M_InOut
      | M_InOut_ID | M_HU_ID     |
      | return_1   | return_1_hu |
    And validate M_HUs:
      | Identifier  | HUStatus | Qty       |
      | return_1_hu | A        | 0.300 KGM |
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_1   | creditCand             |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | InvoiceRule_Override | IsError | C_Tax_Effective_ID |
      | creditCand             | I                    | false   | tax7               |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | PriceEntered | C_Tax_ID |
      | creditMemo   | product      | 0.300       | 15.50 EUR    | tax7     |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC12
  Scenario: The same product returned on two lines of one request is priced on each line separately
    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.C_Invoice_ID |
      | product      | 0.3 | KGM | return_2       | creditMemo       |
      | product      | 0.2 | KGM |                |                  |

    Then validate the created material receipt lines
      | M_InOut_ID | M_Product_ID | MovementQty | movementqty | M_InOutLine_ID |
      | return_2   | product      | 0.3         | 0.3         | line1          |
    And validate the created material receipt lines
      | M_InOut_ID | M_Product_ID | MovementQty | movementqty | M_InOutLine_ID |
      | return_2   | product      | 0.2         | 0.2         | line2          |
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | OPT.M_InOutLine_ID | C_Invoice_Candidate_ID |
      | return_2   | line1              | creditCand1            |
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | OPT.M_InOutLine_ID | C_Invoice_Candidate_ID |
      | return_2   | line2              | creditCand2            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | creditCand1            | false   |
      | creditCand2            | false   |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | PriceEntered |
      | creditMemo   | product      | 0.300       | 15.50 EUR    |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | PriceEntered |
      | creditMemo   | product      | 0.200       | 15.50 EUR    |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC13
  Scenario: Retrying a POS return request with the same idempotency key does not create a second document
    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.ExternalId |
      | product      | 0.3 | KGM | return_3       | retryToken     |
    And a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.ExternalId |
      | product      | 0.3 | KGM | retryToken     |

    Then there is exactly one POS return for retry token retryToken
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_3   | retryCreditCand        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | retryCreditCand        | false   |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC14
  Scenario: A return whose product has no applicable tax is rejected and leaves no partial document
    Given metasfresh contains C_TaxCategory
      | Identifier       |
      | taxCategoryNoTax |
    And metasfresh contains M_Products:
      | Identifier   | X12DE355 |
      | productNoTax | KGM      |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_Product_ID | M_PriceList_Version_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | productNoTaxPrice | productNoTax | priceListVersion       | 9.90     | KGM      | taxCategoryNoTax |

    When a product return at POS terminal till by metasfresh fails with AD_Message 'de.metas.pos.Return.NoTaxFound':
      | M_Product_ID | Qty | UOM | OPT.ExternalId |
      | productNoTax | 0.3 | KGM | noTaxToken     |

    Then there is no POS return for retry token noTaxToken

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC15
  Scenario: A concurrent request for the same terminal blocks on the terminal lock instead of racing
    When a product return at POS terminal till by metasfresh blocks while the terminal is locked by a concurrent transaction:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID |
      | product      | 0.3 | KGM | return_4       |

    Then after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_4   | lockedCreditCand       |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | lockedCreditCand       | false   |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC16
  Scenario: A completed return settles a credit memo with a cash refund posted against the till clearing account
    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.C_Invoice_ID | OPT.C_Payment_ID |
      | product      | 0.3 | KGM | return_5       | creditMemo       | refundPayment    |

    Then after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_5   | creditCand             |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | creditCand             | false   |
    And validate created invoices
      | C_Invoice_ID | DocBaseType | GrandTotal | IsPaid |
      | creditMemo   | ARC         | 4.65 EUR   | true   |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | creditMemo   | product      | 0.300       | tax7     |
    And Wait until documents refundPayment is posted
    And validate payments
      | C_Payment_ID  | IsReceipt | C_Invoice_ID | PayAmt | DocStatus | C_BP_BankAccount_ID | Posted |
      | refundPayment | false     | creditMemo   | 4.65   | CO        | cashbook            | Y      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID     |
      | B_PaymentSelect_Acct  | 4.65 EUR    |             | refundPayment |
      | B_InTransit_Acct      |             | 4.65 EUR    | refundPayment |
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount |
      | CASH_INOUT | -4.65  |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC17
  Scenario: A return of a 19%-rate product credits its own tax rate on the credit memo line
    Given metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory19 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax19      | taxCategory19    | 19   | DE                       | DE                        | false           |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product19  | KGM      |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_Product_ID | M_PriceList_Version_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | product19Price       | product19    | priceListVersion       | 15.50    | KGM      | taxCategory19    |
      | walkInProduct19Price | product19    | walkInPriceListVersion | 20.00    | KGM      | taxCategory19    |

    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.C_Invoice_ID |
      | product19    | 0.3 | KGM | return_6       | creditMemo19     |

    Then after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_6   | creditCand19           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | creditCand19           | false   |
    And validate created invoices
      | C_Invoice_ID | DocBaseType | GrandTotal | IsPaid |
      | creditMemo19 | ARC         | 4.65 EUR   | true   |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | creditMemo19 | product19    | 0.300       | tax19    |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC18
  Scenario: Retrying a settled POS return creates no second credit memo, payment or journal line
    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.ExternalId    |
      | product      | 0.3 | KGM | return_7       | settlementRetryId |
    And a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.ExternalId    |
      | product      | 0.3 | KGM | settlementRetryId |

    Then there is exactly one POS return for retry token settlementRetryId
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_7   | retrySettleCand        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | retrySettleCand        | false   |
    And there is exactly one credit memo and settlement payment for retry token settlementRetryId
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount |
      | CASH_INOUT | -4.65  |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC19
  Scenario: A concurrent request for the same terminal blocks on the cross-transaction lock across all three phases
    When a product return at POS terminal till by metasfresh blocks while the terminal is locked by a concurrent cross-transaction lock:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID | OPT.C_Invoice_ID | OPT.C_Payment_ID |
      | product      | 0.3 | KGM | return_8       | creditMemo8      | refundPayment8   |

    Then after not more than 60s, credit memo candidates are found:
      | M_InOut_ID | C_Invoice_Candidate_ID |
      | return_8   | lockedCreditCand       |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError |
      | lockedCreditCand       | false   |
    And validate created invoices
      | C_Invoice_ID | DocBaseType | GrandTotal | IsPaid |
      | creditMemo8  | ARC         | 4.65 EUR   | true   |
    And validate payments
      | C_Payment_ID   | IsReceipt | C_Invoice_ID | PayAmt | DocStatus |
      | refundPayment8 | false     | creditMemo8  | 4.65   | CO        |
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount |
      | CASH_INOUT | -4.65  |
