@from:cucumber
@ghActions:run_on_executor5
Feature: invoice payment allocation

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And documents are accounted immediately

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList    | pricingSystem      | DE           | EUR           | true  |
      | purchasePriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID    |
      | salesPLV    | salesPriceList    |
      | purchasePLV | purchasePriceList |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | N        | pricingSystem      |
      | vendor1    | N          | Y        | pricingSystem      |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
      | bpartner_location_2 | vendor1       | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    
    
    
    
    
    
    
    
    
    

    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_100
  @from:cucumber
  Scenario: sales invoice - inbound payment (full amount)
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_100 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_100  | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_100    | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_100   | inv_100      | product_100  | 1 PCE       |
    And the invoice identified by inv_100 is completed
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_100 | customer1     | 5.95 EUR | true      | org_EUR_account     |
    And the payment identified by payment_100 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_100      | payment_100  |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_100      | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_100  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_100      | payment_100  | 5.95   | 0            | alloc1             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID   |
      | *                      |             |             |               | payment_100 |
      | B_UnallocatedCash_Acct |             | 5.95 EUR    | customer1     | payment_100 |
      # ----------------------------------------------------------------------------------
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | customer1     | alloc1      |
      | C_Receivable_Acct      |             | 5.95 EUR    | customer1     | alloc1      |
      # ----------------------------------------------------------------------------------
      | C_Receivable_Acct      | 5.95 EUR    |             | customer1     | inv_100     |
      | *                      |             |             |               | inv_100     |
    















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_100_010
  @from:cucumber
  Scenario: sales invoice - inbound payment - discount (full amount)
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier   | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | salesInvoice | product1     | 100 PCE     | tax1     |
    And the invoice identified by salesInvoice is completed
    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt  | IsReceipt | C_BP_BankAccount_ID |
      | inboundPayment | customer1     | 495 EUR | true      | org_EUR_account     |
    And the payment identified by inboundPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID   | DiscountAmt |
      | salesInvoice | inboundPayment | 100 EUR     |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | IsPaid | IsPartiallyPaid |
      | salesInvoice | customer1     | true   | false           |
    And validate payments
      | C_Payment_ID   | IsAllocated |
      | inboundPayment | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID   | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      | salesInvoice | inboundPayment | 495    | 100         | 0            | alloc1             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_BPartner_ID | C_Tax_ID | Record_ID      |
      | B_InTransit_Acct       | 495 EUR     |             | customer1     | -        | inboundPayment |
      | B_UnallocatedCash_Acct |             | 495 EUR     | customer1     | -        | inboundPayment |
      # ----------------------------------------------------------------------------------
      | B_UnallocatedCash_Acct | 495 EUR     |             | customer1     | -        | alloc1         |
      | PayDiscount_Exp_Acct   | 100 EUR     |             | customer1     | tax1     | alloc1         |
      | C_Receivable_Acct      |             | 595 EUR     | customer1     | -        | alloc1         |
      | T_Due_Acct             | 15.97 EUR   |             | customer1     | tax1     | alloc1         |
      | PayDiscount_Exp_Acct   |             | 15.97 EUR   | customer1     | tax1     | alloc1         |
      # ----------------------------------------------------------------------------------
      | C_Receivable_Acct      | 595 EUR     |             | customer1     | -        | salesInvoice   |
      | T_Due_Acct             |             | 95 EUR      | customer1     | tax1     | salesInvoice   |
      | *                      |             |             |               |          | salesInvoice   |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_110
  @from:cucumber
  Scenario: multiple sales invoices - inbound payment (partial)
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_110 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_110  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_110_1  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_110_2  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_110_1 | inv_110_1    | product_110  | 1 PCE       |
      | invl_110_2 | inv_110_2    | product_110  | 1 PCE       |
    And the invoice identified by inv_110_1 is completed
    And the invoice identified by inv_110_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment_110 | customer1     | 14.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_110 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_110_1    | payment_110  |
      | inv_110_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_110_1    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
      | inv_110_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_110  | false       | 2.10    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_110_1    | payment_110  | 5.95   | 0            | alloc1             |
      | inv_110_2    | payment_110  | 5.95   | 0            | alloc2             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID   |
      | *                      |             |             | payment_110 |
      | B_UnallocatedCash_Acct |             | 14 EUR      | payment_110 |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | alloc1      |
      | C_Receivable_Acct      |             | 5.95 EUR    | alloc1      |
      | C_Receivable_Acct      | 5.95 EUR    |             | inv_110_1   |
      | *                      |             |             | inv_110_1   |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID   |
      | *                      |             |             | payment_110 |
      | B_UnallocatedCash_Acct |             | 14 EUR      | payment_110 |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | alloc2      |
      | C_Receivable_Acct      |             | 5.95 EUR    | alloc2      |
      | C_Receivable_Acct      | 5.95 EUR    |             | inv_110_2   |
      | *                      |             |             | inv_110_2   |

























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_120
  @from:cucumber
  Scenario: multiple sales invoices - inbound payment (partial) - then apply write off

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_120 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_120  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_120_1  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_120_2  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_120_1 | inv_120_1    | product_120  | 1 PCE       | tax1     |
      | invl_120_2 | inv_120_2    | product_120  | 1 PCE       | tax1     |
    And the invoice identified by inv_120_1 is completed
    And the invoice identified by inv_120_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_120 | customer1     | 9.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_120 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_120_1    | payment_120  |
      | inv_120_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_120_1    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_120_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_120_1    | payment_120  | 5.95   | 0            | alloc1             |
      | inv_120_2    | payment_120  | 3.05   | 2.9          | alloc2             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | alloc1    |
      | C_Receivable_Acct      |             | 5.95 EUR    | alloc1    |
      | C_Receivable_Acct      | 5.95 EUR    |             | inv_120_1 |
      | *                      |             |             | inv_120_1 |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 3.05 EUR    |             | alloc2    |
      | C_Receivable_Acct      |             | 3.05 EUR    | alloc2    |
      | C_Receivable_Acct      | 5.95 EUR    |             | inv_120_2 |
      | *                      |             |             | inv_120_2 |

    And apply WRITEOFF to invoices
      | C_Invoice_ID | Amount |
      | inv_120_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_120_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_120  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | C_AllocationHdr_ID |
      | inv_120_2    | 0      | 2.9         | alloc3             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID |
      | WriteOff_Acct         | 2.90 EUR    |             |          | alloc3    |
      | C_Receivable_Acct     |             | 2.90 EUR    |          | alloc3    |
      | T_Due_Acct            | 0.46 EUR    |             | tax1     | alloc3    |
      | WriteOff_Acct         |             | 0.46 EUR    | tax1     | alloc3    |


























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_130
  @from:cucumber
  Scenario: multiple sales invoices - inbound payment (partial) - then apply Discount

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_130 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_130  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_130_1  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_130_2  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_130_1 | inv_130_1    | product_130  | 1 PCE       | tax1     |
      | invl_130_2 | inv_130_2    | product_130  | 1 PCE       | tax1     |
    And the invoice identified by inv_130_1 is completed
    And the invoice identified by inv_130_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_130 | customer1     | 9.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_130 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_130_1    | payment_130  |
      | inv_130_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_130_1    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_130_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_130_1    | payment_130  | 5.95   | 0            | alloc1             |
      | inv_130_2    | payment_130  | 3.05   | 2.9          | alloc2             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | alloc1    |
      | C_Receivable_Acct      |             | 5.95 EUR    | alloc1    |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 3.05 EUR    |             | alloc2    |
      | C_Receivable_Acct      |             | 3.05 EUR    | alloc2    |

    And apply DISCOUNT to invoices
      | C_Invoice_ID | Amount |
      | inv_130_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_130_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | C_Payment_ID.IsAllocated |
      | payment_130  | true                     |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | DiscountAmt | C_AllocationHdr_ID |
      | inv_130_2    | 0      | 2.9         | alloc3             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID |
      | PayDiscount_Exp_Acct  | 2.90 EUR    |             |          | alloc3    |
      | C_Receivable_Acct     |             | 2.90 EUR    |          | alloc3    |
      | T_Due_Acct            | 0.46 EUR    |             | tax1     | alloc3    |
      | PayDiscount_Exp_Acct  |             | 0.46 EUR    | tax1     | alloc3    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_140
  @from:cucumber
  Scenario:multiple sales invoices in CHF - inbound payment in EUR (partial alloc)

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_140 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_140  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_140_1  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
      | inv_140_2  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_140_1 | inv_140_1    | product_140  | 1 PCE       |
      | invl_140_2 | inv_140_2    | product_140  | 1 PCE       |
    And the invoice identified by inv_140_1 is completed
    And the invoice identified by inv_140_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment_140 | customer1     | 14.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_140 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_140_1    | payment_140  |
      | inv_140_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_140_1    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
      | inv_140_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_140  | false       | 2.10    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_140_1    | payment_140  | 5.95   | 0            | alloc1             |
      | inv_140_2    | payment_140  | 5.95   | 0            | alloc2             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | alloc1    |
      | C_Receivable_Acct      |             | 5.95 EUR    | alloc1    |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | alloc2    |
      | C_Receivable_Acct      |             | 5.95 EUR    | alloc2    |



























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_150
  @from:cucumber
  Scenario: sales invoice - purchase invoice
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
      | product2   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |
      | purchasePLV            | product2     | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice    | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | purchaseInvoice | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced |
      | salesInvoice    | product1     | 1 PCE       |
      | purchaseInvoice | product2     | 2 PCE       |
    And the invoice identified by salesInvoice is completed
    And the invoice identified by purchaseInvoice is completed

    And allocate payments to invoices
      | C_Invoice_ID    |
      | salesInvoice    |
      | purchaseInvoice |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | salesInvoice    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | purchaseInvoice | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 5.95    | true            |
    And validate C_AllocationLines
      | C_Invoice_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | salesInvoice    | 5.95   | 0            | alloc1             |
      | purchaseInvoice | -5.95  | -5.95        | alloc1             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID       |
      | *                     |             |             | purchaseInvoice |
      | V_Liability_Acct      |             | 11.9 EUR    | purchaseInvoice |
      # ---------------------------------------------------------------------
      | V_Liability_Acct      | 5.95 EUR    |             | alloc1          |
      | C_Receivable_Acct     |             | 5.95 EUR    | alloc1          |
      # ---------------------------------------------------------------------
      | C_Receivable_Acct     | 5.95 EUR    |             | salesInvoice    |
      | *                     |             |             | salesInvoice    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_160
  @from:cucumber
  Scenario: sales invoice (with allocated sales credit memo) - inbound payment
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_160 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_160  | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice1 | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID  | M_Product_ID | QtyInvoiced |
      | salesInvoice1 | product_160  | 1 PCE       |
    And the invoice identified by salesInvoice1 is completed
    And create credit memo for C_Invoice
      | CreditMemo       | C_Invoice_ID  | CreditMemo.PriceEntered |
      | salesCreditMemo1 | salesInvoice1 | 2.00                    |
    And the invoice identified by salesCreditMemo1 is completed

    And validate C_AllocationLines
      | C_Invoice_ID     | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | salesCreditMemo1 | -2.38  | 0            | alloc1             |
      | salesInvoice1    | 2.38   | 0            | alloc1             |

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_160 | customer1     | 5.95 EUR | true      | org_EUR_account     |
    And the payment identified by payment_160 is completed
    And allocate payments to invoices
      | C_Invoice_ID  | C_Payment_ID |
      | salesInvoice1 | payment_160  |

    Then validate created invoices
      | C_Invoice_ID     | IsPaid | IsPartiallyPaid |
      | salesCreditMemo1 | true   | false           |
      | salesInvoice1    | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_160  | false       | 2.38    |
    And validate C_AllocationLines
      | C_Invoice_ID  | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | salesInvoice1 | payment_160  | 3.57   | 0            | alloc2             |

    And no Fact_Acct records are found for documents alloc1
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 3.57 EUR    | 0 EUR       | alloc2    |
      | C_Receivable_Acct      | 0 EUR       | 3.57 EUR    | alloc2    |



























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_170
  @from:cucumber
  Scenario: sales invoice - outbound payment => expect cannot be allocated
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_170 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_170  | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_170    | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_170   | inv_170      | product_170  | 1 PCE       |
    And the invoice identified by inv_170 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_170 | customer1     | 5.95 EUR | false     | org_EUR_account     |
    And the payment identified by payment_170 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_170      | payment_170  |

    Then validate payments
      | C_Payment_ID | IsAllocated |
      | payment_170  | false       |
    And there are no allocation lines for invoice
      | C_Invoice_ID |
      | inv_170      |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_180
  @from:cucumber
  Scenario: purchase invoice - outbound payment
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_180 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_180  | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInvoice | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced |
      | purchaseInvoice | product_180  | 1 PCE       |
    And the invoice identified by purchaseInvoice is completed

    And metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment | customer1     | 5.95 EUR | false     | org_EUR_account     |
    And the payment identified by outboundPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID    | C_Payment_ID    |
      | purchaseInvoice | outboundPayment |

    Then validate created invoices
      | C_Invoice_ID    | IsPaid | IsPartiallyPaid |
      | purchaseInvoice | true   | false           |
    And validate payments
      | C_Payment_ID    | IsAllocated |
      | outboundPayment | true        |
    And validate C_AllocationLines
      | C_Invoice_ID    | C_Payment_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | purchaseInvoice | outboundPayment | -5.95  | 0            | alloc1             |

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID       |
      | *                     |             |             | purchaseInvoice |
      | V_Liability_Acct      |             | 5.95 EUR    | purchaseInvoice |
      # ---------------------------------------------------------------------
      | V_Liability_Acct      | 5.95 EUR    |             | alloc1          |
      | B_PaymentSelect_Acct  |             | 5.95 EUR    | alloc1          |
      # ---------------------------------------------------------------------
      | B_PaymentSelect_Acct  | 5.95 EUR    |             | outboundPayment |
      | B_InTransit_Acct      |             | 5.95 EUR    | outboundPayment |



























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_180_010
  @from:cucumber
  Scenario: purchase invoice - outbound payment - discount (full amount)
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product1     | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInvoice | vendor1       | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | purchaseInvoice | product1     | 100 PCE     | tax1     |
    And the invoice identified by purchaseInvoice is completed
    And metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt  | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment | vendor1       | 495 EUR | false     | org_EUR_account     |
    And the payment identified by outboundPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID    | C_Payment_ID    | DiscountAmt |
      | purchaseInvoice | outboundPayment | 100 EUR     |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID | IsPaid | IsPartiallyPaid |
      | purchaseInvoice | vendor1       | true   | false           |
    And validate payments
      | C_Payment_ID    | IsAllocated |
      | outboundPayment | true        |
    And validate C_AllocationLines
      | C_Invoice_ID    | C_Payment_ID    | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      | purchaseInvoice | outboundPayment | -495   | -100        | 0            | alloc1             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | C_Tax_ID | Record_ID       |
      | B_InTransit_Acct      |             | 495 EUR     | vendor1       | -        | outboundPayment |
      | B_PaymentSelect_Acct  | 495 EUR     |             | vendor1       | -        | outboundPayment |
      # ----------------------------------------------------------------------------------
      | B_PaymentSelect_Acct  |             | 495 EUR     | vendor1       | -        | alloc1          |
      | PayDiscount_Rev_Acct  |             | 100 EUR     | vendor1       | tax1     | alloc1          |
      | V_Liability_Acct      | 595 EUR     |             | vendor1       | -        | alloc1          |
      | T_Credit_Acct         |             | 15.97 EUR   | vendor1       | tax1     | alloc1          |
      | PayDiscount_Rev_Acct  | 15.97 EUR   |             | vendor1       | tax1     | alloc1          |
      # ----------------------------------------------------------------------------------
      | V_Liability_Acct      |             | 595 EUR     | vendor1       | -        | purchaseInvoice |
      | T_Credit_Acct         | 95 EUR      |             | vendor1       | tax1     | purchaseInvoice |
      | *                     |             |             |               |          | purchaseInvoice |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_190
  @from:cucumber
  Scenario: multiple purchase invoices - outbound payment (partial)

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_190 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_190  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInvoice1 | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | purchaseInvoice2 | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID | QtyInvoiced |
      | purchaseInvoice1 | product_190  | 1 PCE       |
      | purchaseInvoice2 | product_190  | 1 PCE       |
    And the invoice identified by purchaseInvoice1 is completed
    And the invoice identified by purchaseInvoice2 is completed

    And metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment | customer1     | 14.00 EUR | false     | org_EUR_account     |
    And the payment identified by outboundPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID     | C_Payment_ID    |
      | purchaseInvoice1 | outboundPayment |
      | purchaseInvoice2 |                 |

    Then validate created invoices
      | C_Invoice_ID     | IsPaid | IsPartiallyPaid |
      | purchaseInvoice1 | true   | false           |
      | purchaseInvoice2 | true   | false           |
    And validate payments
      | C_Payment_ID    | IsAllocated | OpenAmt |
      | outboundPayment | false       | 2.10    |
    And validate C_AllocationLines
      | C_Invoice_ID     | C_Payment_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | purchaseInvoice1 | outboundPayment | -5.95  | 0            | alloc1             |
      | purchaseInvoice2 | outboundPayment | -5.95  | 0            | alloc2             |

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 5.95 EUR    |             | alloc1    |
      | B_PaymentSelect_Acct  |             | 5.95 EUR    | alloc1    |
      # ---------------------------------------------------------------
      | V_Liability_Acct      | 5.95 EUR    |             | alloc2    |
      | B_PaymentSelect_Acct  |             | 5.95 EUR    | alloc2    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_200
  @from:cucumber
  Scenario: multiple purchase invoices - outbound payment (partial) - apply write off
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_200 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_200  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_200_1  | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_200_2  | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_200_1 | inv_200_1    | product_200  | 1 PCE       | tax1     |
      | invl_200_2 | inv_200_2    | product_200  | 1 PCE       | tax1     |
    And the invoice identified by inv_200_1 is completed
    And the invoice identified by inv_200_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_200 | customer1     | 9.00 EUR | false     | org_EUR_account     |
    And the payment identified by payment_200 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_200_1    | payment_200  |
      | inv_200_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_200_1    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_200_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_200_1    | payment_200  | -5.95  | 0            | alloc1             |
      | inv_200_2    | payment_200  | -3.05  | -2.9         | alloc2             |

    And apply WRITEOFF to invoices
      | C_Invoice_ID | Amount |
      | inv_200_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_200_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_200  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | C_AllocationHdr_ID |
      | inv_200_2    | 0      | -2.9        | alloc3             |

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |          | alloc1    |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    |          | alloc1    |
      # --------------------------------------------------------------------------
      | V_Liability_Acct      | 3.05 EUR    | 0 EUR       |          | alloc2    |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.05 EUR    |          | alloc2    |
      # --------------------------------------------------------------------------
      | V_Liability_Acct      | 2.9 EUR     | 0 EUR       |          | alloc3    |
      | WriteOff_Acct         | 0 EUR       | 2.9 EUR     |          | alloc3    |
      | WriteOff_Acct         | -0.46 EUR   | 0 EUR       | tax1     | alloc3    |
      | T_Credit_Acct         | 0 EUR       | -0.46 EUR   | tax1     | alloc3    |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_210
  @from:cucumber
  Scenario: multiple purchase invoices - outbound payment (partial) - apply discount
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_210 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_210  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_210_1  | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_210_2  | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_210_1 | inv_210_1    | product_210  | 1 PCE       | tax1     |
      | invl_210_2 | inv_210_2    | product_210  | 1 PCE       | tax1     |
    And the invoice identified by inv_210_1 is completed
    And the invoice identified by inv_210_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_210 | customer1     | 9.00 EUR | false     | org_EUR_account     |
    And the payment identified by payment_210 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_210_1    | payment_210  |
      | inv_210_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_210_1    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_210_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_210_1    | payment_210  | -5.95  | 0            | alloc1             |
      | inv_210_2    | payment_210  | -3.05  | -2.9         | alloc2             |

    And apply DISCOUNT to invoices
      | C_Invoice_ID | Amount |
      | inv_210_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_210_2    | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_210  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | DiscountAmt | C_AllocationHdr_ID |
      | inv_210_2    | 0      | -2.9        | alloc3             |

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       | alloc1    |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    | alloc1    |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 3.05 EUR    | 0 EUR       | alloc2    |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.05 EUR    | alloc2    |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID |
      | V_Liability_Acct      | 2.9 EUR     | 0 EUR       |          | alloc3    |
      | PayDiscount_Rev_Acct  | 0 EUR       | 2.9 EUR     |          | alloc3    |
      | PayDiscount_Rev_Acct  | 0.46 EUR    | 0 EUR       | tax1     | alloc3    |
      | T_Credit_Acct         | 0 EUR       | 0.46 EUR    | tax1     | alloc3    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_220
  @from:cucumber
  Scenario: purchase invoice (with allocated purchase credit memo) - outbound payment
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_220 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_220  | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_220    | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_220   | inv_220      | product_220  | 1 PCE       |
    And the invoice identified by inv_220 is completed

    And create credit memo for C_Invoice
      | CreditMemo      | C_Invoice_ID | CreditMemo.PriceEntered |
      | credit_memo_220 | inv_220      | 2.00                    |
    And the invoice identified by credit_memo_220 is completed
    And validate C_AllocationLines
      | C_Invoice_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | credit_memo_220 | 2.38   | 0            | alloc1             |
      | inv_220         | -2.38  | 0            | alloc1             |

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_220 | customer1     | 5.95 EUR | false     | org_EUR_account     |
    And the payment identified by payment_220 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_220      | payment_220  |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | OpenAmt | IsPaid | IsPartiallyPaid |
      | credit_memo_220 | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | 0       | true   | false           |
      | inv_220         | customer1     | bpartner_location_1    | 30 Tage netto | true      | CO        | 0       | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_220  | false       | 2.38    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_220      | payment_220  | -3.57  | 0            | alloc2             |

    And no Fact_Acct records are found for documents alloc1
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 3.57 EUR    | 0 EUR       | alloc2    |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.57 EUR    | alloc2    |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_230
  @from:cucumber
  Scenario: purchase invoice - inbound payment => expect cannot be allocated

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_230 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_230  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_230    | customer1     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_230   | inv_230      | product_230  | 1 PCE       |
    And the invoice identified by inv_230 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_230 | customer1     | 5.95 EUR | true      | org_EUR_account     |
    And the payment identified by payment_230 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_230      | payment_230  |

    Then validate payments
      | C_Payment_ID | IsAllocated |
      | payment_230  | false       |
    And there are no allocation lines for invoice
      | C_Invoice_ID |
      | inv_230      |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_240
  @from:cucumber
  Scenario: inbound payment - outbound payment
    When metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment | customer1     | 9.00 EUR | false     | org_EUR_account     |
      | inboundPayment  | customer1     | 5.00 EUR | true      | org_EUR_account     |
    And the payment identified by outboundPayment is completed
    And the payment identified by inboundPayment is completed
    And allocate payments to invoices
      | C_Payment_ID    |
      | outboundPayment |
      | inboundPayment  |

    Then validate payments
      | C_Payment_ID    | IsAllocated | OpenAmt |
      | outboundPayment | false       | 4.00    |
      | inboundPayment  | true        | 0.00    |
    And validate C_AllocationLines
      | C_Payment_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | outboundPayment | -5     | -4           | alloc1             |
      | inboundPayment  | 5      | 0            | alloc1             |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID       |
      | B_InTransit_Acct       | 5 EUR       |             | inboundPayment  |
      | B_UnallocatedCash_Acct |             | 5 EUR       | inboundPayment  |
      # ----------------------------------------------------------------
      | B_UnallocatedCash_Acct | 5 EUR       |             | alloc1          |
      | B_PaymentSelect_Acct   |             | 5 EUR       | alloc1          |
      # ----------------------------------------------------------------
      | B_PaymentSelect_Acct   | 9 EUR       |             | outboundPayment |
      | B_InTransit_Acct       |             | 9 EUR       | outboundPayment |


















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_320
  @from:cucumber
  Scenario: sales invoice - purchase credit memo => expect cannot be allocated
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
      | product2   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |
      | purchasePLV            | product2     | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier          | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice1       | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | purchaseCreditMemo1 | vendor1       | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID        | M_Product_ID | QtyInvoiced |
      | purchaseCreditMemo1 | product2     | 1 PCE       |
      | salesInvoice1       | product1     | 1 PCE       |
    And the invoice identified by salesInvoice1 is completed
    And the invoice identified by purchaseCreditMemo1 is completed

    And allocate payments to invoices
      | C_Invoice_ID        |
      | salesInvoice1       |
      | purchaseCreditMemo1 |
    And there are no allocation lines for invoice
      | C_Invoice_ID        |
      | salesInvoice1       |
      | purchaseCreditMemo1 |



















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_330
  @from:cucumber
  Scenario: sales credit memo - outbound payment (partial)
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesCreditMemo1 | customer1     | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID | QtyInvoiced |
      | salesCreditMemo1 | product1     | 1 PCE       |
    And metasfresh contains C_Payment
      | Identifier       | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment1 | customer1     | 5.00 EUR | false     | org_EUR_account     |
    And the invoice identified by salesCreditMemo1 is completed
    And the payment identified by outboundPayment1 is completed

    When allocate payments to invoices
      | C_Invoice_ID     | C_Payment_ID     |
      | salesCreditMemo1 | outboundPayment1 |

    Then validate C_AllocationLines
      | C_Invoice_ID     | C_Payment_ID     | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | salesCreditMemo1 | outboundPayment1 | -5     | -0.95        | alloc1             |

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID        |
      | *                     |             |             | salesCreditMemo1 |
      | C_Receivable_Acct     |             | 5.95 EUR    | salesCreditMemo1 |
      # ----------------------------------------------------------------------
      | C_Receivable_Acct     | 5 EUR       |             | alloc1           |
      | B_PaymentSelect_Acct  |             | 5 EUR       | alloc1           |
      # ----------------------------------------------------------------------
      | B_PaymentSelect_Acct  | 5 EUR       |             | outboundPayment1 |
      | B_InTransit_Acct      |             | 5 EUR       | outboundPayment1 |



















    # ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_330
  @from:cucumber
  Scenario: sales credit memo - outbound payment - discount (full amount)
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 1.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesCreditMemo1 | customer1     | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | salesCreditMemo1 | product1     | 100 PCE     | tax19%   |
    And metasfresh contains C_Payment
      | Identifier       | C_BPartner_ID | PayAmt | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment1 | customer1     | 80 EUR | false     | org_EUR_account     |
    And the invoice identified by salesCreditMemo1 is completed
    And the payment identified by outboundPayment1 is completed

    When allocate payments to invoices
      | C_Invoice_ID     | C_Payment_ID     | DiscountAmt |
      | salesCreditMemo1 | outboundPayment1 | -39 EUR     |

    Then validate C_AllocationLines
      | C_Invoice_ID     | C_Payment_ID     | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      | salesCreditMemo1 | outboundPayment1 | -80    | -39         | -0           | alloc1             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | C_BPartner_ID | Record_ID        |
      | *                     |             |             |          |               | salesCreditMemo1 |
      | T_Due_Acct            | 19 EUR      |             | tax19%   | customer1     | salesCreditMemo1 |
      | C_Receivable_Acct     |             | 119 EUR     |          | customer1     | salesCreditMemo1 |
      # ----------------------------------------------------------------------
      | C_Receivable_Acct     | 119 EUR     |             |          | customer1     | alloc1           |
      | PayDiscount_Rev_Acct  |             | 39 EUR      | tax19%   | customer1     | alloc1           |
      | B_PaymentSelect_Acct  |             | 80 EUR      |          | customer1     | alloc1           |
      # tax correction:
      | PayDiscount_Rev_Acct  | 6.23 EUR    |             | tax19%   | customer1     | alloc1           |
      | T_Due_Acct            |             | 6.23 EUR    | tax19%   | customer1     | alloc1           |
      # ----------------------------------------------------------------------
      | B_PaymentSelect_Acct  | 80 EUR      |             |          | customer1     | outboundPayment1 |
      | B_InTransit_Acct      |             | 80 EUR      |          | customer1     | outboundPayment1 |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_340
  @from:cucumber
  Scenario: sales credit memo - inbound payment => no allocations

    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesCreditMemo1 | customer1     | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |



    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID | QtyInvoiced |
      | salesCreditMemo1 | product1     | 1 PCE       |


    When metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | inboundPayment1 | customer1     | 5.00 EUR | true      | org_EUR_account     |

    And the invoice identified by salesCreditMemo1 is completed
    And the payment identified by inboundPayment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID     | C_Payment_ID    |
      | salesCreditMemo1 | inboundPayment1 |

    And there are no allocation lines for invoice
      | C_Invoice_ID     |
      | salesCreditMemo1 |



















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_350
  @from:cucumber
  Scenario: purchase credit memo - inbound payment (partial)

    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product1     | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier          | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseCreditMemo1 | vendor1       | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID        | M_Product_ID | QtyInvoiced |
      | purchaseCreditMemo1 | product1     | 1 PCE       |
    When metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | inboundPayment1 | vendor1       | 5.00 EUR | true      | org_EUR_account     |
    And the invoice identified by purchaseCreditMemo1 is completed
    And the payment identified by inboundPayment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID        | C_Payment_ID    |
      | purchaseCreditMemo1 | inboundPayment1 |
    And validate C_AllocationLines
      | C_Invoice_ID        | C_Payment_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | purchaseCreditMemo1 | inboundPayment1 | 5      | 0.95         | alloc1             |
    Then validate created invoices
      | C_Invoice_ID        | IsPaid | IsPartiallyPaid | OpenAmt |
      | purchaseCreditMemo1 | false  | true            | 0.95    |
    And validate payments
      | C_Payment_ID    | IsAllocated |
      | inboundPayment1 | true        |

    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID           |
      | *                      |             |             | purchaseCreditMemo1 |
      | V_Liability_Acct       | 5.95 EUR    |             | purchaseCreditMemo1 |
      # ----------------------------------------------------------------------
      | V_Liability_Acct       |             | 5 EUR       | alloc1              |
      | B_UnallocatedCash_Acct | 5 EUR       |             | alloc1              |
      # ----------------------------------------------------------------------
      | B_UnallocatedCash_Acct |             | 5 EUR       | inboundPayment1     |
      | B_InTransit_Acct       | 5 EUR       |             | inboundPayment1     |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_350_010
  @from:cucumber
  Scenario: purchase credit memo - inbound payment - discount (full amount)

    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product1     | 1.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier          | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseCreditMemo1 | vendor1       | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID        | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | purchaseCreditMemo1 | product1     | 100 PCE     | tax19%   |
    When metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt | IsReceipt | C_BP_BankAccount_ID |
      | inboundPayment1 | vendor1       | 80 EUR | true      | org_EUR_account     |
    And the invoice identified by purchaseCreditMemo1 is completed
    And the payment identified by inboundPayment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID        | C_Payment_ID    | DiscountAmt |
      | purchaseCreditMemo1 | inboundPayment1 | -39 EUR     |
    And validate C_AllocationLines
      | C_Invoice_ID        | C_Payment_ID    | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      | purchaseCreditMemo1 | inboundPayment1 | 80     | 39          | 0            | alloc1             |
    Then validate created invoices
      | C_Invoice_ID        | IsPaid | IsPartiallyPaid | OpenAmt |
      | purchaseCreditMemo1 | true   | false           | 0       |
    And validate payments
      | C_Payment_ID    | IsAllocated |
      | inboundPayment1 | true        |

    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_Tax_ID | C_BPartner_ID | Record_ID           |
      | *                      |             |             |          |               | purchaseCreditMemo1 |
      | T_Credit_Acct          |             | 19 EUR      | tax19%   | vendor1       | purchaseCreditMemo1 |
      | V_Liability_Acct       | 119 EUR     |             | -        | vendor1       | purchaseCreditMemo1 |
      # ---------------------------------------------------------------------------------------------------------------
      | V_Liability_Acct       |             | 119 EUR     | -        | vendor1       | alloc1              |
      | B_UnallocatedCash_Acct | 80 EUR      |             | -        | vendor1       | alloc1              |
      | PayDiscount_Exp_Acct   | 39 EUR      |             | tax19%   | vendor1       | alloc1              |
      | PayDiscount_Exp_Acct   |             | 6.23 EUR    | tax19%   | vendor1       | alloc1              |
      | T_Credit_Acct          | 6.23 EUR    |             | tax19%   | vendor1       | alloc1              |
      # ---------------------------------------------------------------------------------------------------------------
      | B_UnallocatedCash_Acct |             | 80 EUR      | -        | vendor1       | inboundPayment1     |
      | B_InTransit_Acct       | 80 EUR      |             | -        | vendor1       | inboundPayment1     |










































# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_500
  @from:cucumber
  Scenario: sales credit memo - purchase invoice - inbound payment (REMADV case)
    # NOTE: this kind of allocation cannot be manualy done by user (because REMADV code is doing it), but the purpose of this test
    # is to make sure, that in case of such allocation the open amounts and accounting is correct
    Given metasfresh contains M_Products:
      | Identifier           |
      | creditMemoProduct    |
      | vendorServiceProduct |
    And update M_PriceLists:
      | Identifier        | IsTaxIncluded |
      | salesPriceList    | Y             |
      | purchasePriceList | Y             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID |
      | salesPLV               | creditMemoProduct    | 0        | PCE      |
      | purchasePLV            | vendorServiceProduct | 0        | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier           | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | customerCreditMemo   | customer1     | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
      | vendorServiceInvoice | vendor1       | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID         | M_Product_ID         | QtyInvoiced | Price  | C_Tax_ID |
      | customerCreditMemo   | creditMemoProduct    | 1 PCE       | 434.58 | tax19%   |
      | vendorServiceInvoice | vendorServiceProduct | 1 PCE       | 6.46   | tax19%   |
    And the invoice identified by customerCreditMemo is completed
    And the invoice identified by vendorServiceInvoice is completed

    When metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | inboundPayment | vendor1       | -434.97 EUR | true      | org_EUR_account     |
    And the payment identified by inboundPayment is completed

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID         | C_Payment_ID | Amount    | DiscountAmt | OverUnderAmt |
      | alloc1             | customerCreditMemo   |              | 6.46 EUR  | 0 EUR       | -441.04 EUR  |
      | alloc1             | vendorServiceInvoice |              | -6.46 EUR | 0 EUR       | 0 EUR        |

    Then validate created invoices
      | C_Invoice_ID         | IsPaid | IsPartiallyPaid | OpenAmt |
      | customerCreditMemo   | false  | true            | 441.04  |
      | vendorServiceInvoice | true   | false           | 0       |

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID       | C_Payment_ID   | Amount      | DiscountAmt | OverUnderAmt |
      | alloc2             | customerCreditMemo | inboundPayment | -434.97 EUR | -6.07 EUR   | 0 EUR        |

    Then validate created invoices
      | C_Invoice_ID       | IsPaid | IsPartiallyPaid | OpenAmt |
      | customerCreditMemo | true   | false           | 0       |

    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_BPartner_ID | C_Tax_ID | Record_ID            |
      | C_Receivable_Acct      |             | 434.58 EUR  | customer1     | -        | customerCreditMemo   |
      | T_Due_Acct             | 69.39 EUR   |             | customer1     | tax19%   | customerCreditMemo   |
      | *                      |             |             |               |          | customerCreditMemo   |
      # ------------------------------------------------------------------------------------------
      | V_Liability_Acct       |             | 6.46 EUR    | vendor1       | -        | vendorServiceInvoice |
      | *                      |             |             |               |          | vendorServiceInvoice |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      |             | 6.46 EUR    | customer1     | -        | alloc1               |
      | V_Liability_Acct       | 6.46 EUR    |             | vendor1       | -        | alloc1               |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      | 441.04 EUR  |             | customer1     | -        | alloc2               |
      | B_UnallocatedCash_Acct |             | 434.97 EUR  | vendor1       | -        | alloc2               |
      | PayDiscount_Rev_Acct   |             | 6.07 EUR    | vendor1       | tax19%   | alloc2               |
      # tax correction:
      | PayDiscount_Rev_Acct   | 0.97 EUR    |             | customer1     | tax19%   | alloc2               |
      | T_Due_Acct             |             | 0.97 EUR    | customer1     | tax19%   | alloc2               |
      # ------------------------------------------------------------------------------------------
      | B_UnallocatedCash_Acct |             | -434.97 EUR | vendor1       | -        | inboundPayment       |
      | B_InTransit_Acct       | -434.97 EUR |             | vendor1       | -        | inboundPayment       |

