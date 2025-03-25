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
      | bpartner   | Y          | N        | pricingSystem      |
      | bpartner2  | N          | Y        | pricingSystem      |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | bpartner      | Y               | Y               |
      | bpartner_location_2 | bpartner2     | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    
    
    
    
    
    
    
    
    
    

    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_100
  @from:cucumber
  Scenario: allocate payment to sales invoice for the full amount

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_100 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_100  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_100    | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_100   | inv_100      | product_100  | 1 PCE       |
    And the invoice identified by inv_100 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_100 | bpartner      | 5.95 EUR | true      | org_EUR_account     |
    And the payment identified by payment_100 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_100      | payment_100  |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_100      | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_100  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_100      | payment_100  | 5.95   | 0            | alloc1             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |
    















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_110
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with payment open amount left
    Given metasfresh contains M_Products:
      | Identifier  |
      | product_110 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_110  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_110_1  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_110_2  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_110_1 | inv_110_1    | product_110  | 1 PCE       |
      | invl_110_2 | inv_110_2    | product_110  | 1 PCE       |
    And the invoice identified by inv_110_1 is completed
    And the invoice identified by inv_110_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment_110 | bpartner      | 14.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_110 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_110_1    | payment_110  |
      | inv_110_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_110_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
      | inv_110_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_110  | false       | 2.10    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_110_1    | payment_110  | 5.95   | 0            | alloc1             |
      | inv_110_2    | payment_110  | 5.95   | 0            | alloc2             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |

























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_120
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with invoice open amount left and then apply write off

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_120 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_120  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_120_1  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_120_2  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_120_1 | inv_120_1    | product_120  | 1 PCE       |
      | invl_120_2 | inv_120_2    | product_120  | 1 PCE       |
    And the invoice identified by inv_120_1 is completed
    And the invoice identified by inv_120_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_120 | bpartner      | 9.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_120 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_120_1    | payment_120  |
      | inv_120_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_120_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_120_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_120_1    | payment_120  | 5.95   | 0            | alloc1             |
      | inv_120_2    | payment_120  | 3.05   | 2.9          | alloc2             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 3.05 EUR    |             |
      | C_Receivable_Acct      |             | 3.05 EUR    |

    And apply WRITEOFF to invoices
      | C_Invoice_ID | Amount |
      | inv_120_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_120_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_120  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | C_AllocationHdr_ID |
      | inv_120_2    | 0      | 2.9         | alloc3             |
    And Fact_Acct records are found for payment allocation alloc3
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | WriteOff_Acct         | 2.90 EUR    |             |
      | C_Receivable_Acct     |             | 2.90 EUR    |
      | T_Due_Acct            | 0.46 EUR    |             |
      | WriteOff_Acct         |             | 0.46 EUR    |


























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_130
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with invoice open amount left and then apply discount

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_130 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_130  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_130_1  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_130_2  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_130_1 | inv_130_1    | product_130  | 1 PCE       |
      | invl_130_2 | inv_130_2    | product_130  | 1 PCE       |
    And the invoice identified by inv_130_1 is completed
    And the invoice identified by inv_130_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_130 | bpartner      | 9.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_130 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_130_1    | payment_130  |
      | inv_130_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_130_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_130_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_130_1    | payment_130  | 5.95   | 0            | alloc1             |
      | inv_130_2    | payment_130  | 3.05   | 2.9          | alloc2             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 3.05 EUR    |             |
      | C_Receivable_Acct      |             | 3.05 EUR    |

    And apply DISCOUNT to invoices
      | C_Invoice_ID | Amount |
      | inv_130_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_130_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | C_Payment_ID.IsAllocated |
      | payment_130  | true                     |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | DiscountAmt | C_AllocationHdr_ID |
      | inv_130_2    | 0      | 2.9         | alloc3             |
    And Fact_Acct records are found for payment allocation alloc3
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | PayDiscount_Exp_Acct  | 2.90 EUR    |             |
      | C_Receivable_Acct     |             | 2.90 EUR    |
      | T_Due_Acct            | 0.46 EUR    |             |
      | PayDiscount_Exp_Acct  |             | 0.46 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_140
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with payment open amount left and not matching on currency

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_140 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_140  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_140_1  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
      | inv_140_2  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_140_1 | inv_140_1    | product_140  | 1 PCE       |
      | invl_140_2 | inv_140_2    | product_140  | 1 PCE       |
    And the invoice identified by inv_140_1 is completed
    And the invoice identified by inv_140_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment_140 | bpartner      | 14.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_140 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_140_1    | payment_140  |
      | inv_140_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_140_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
      | inv_140_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_140  | false       | 2.10    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_140_1    | payment_140  | 5.95   | 0            | alloc1             |
      | inv_140_2    | payment_140  | 5.95   | 0            | alloc2             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             |
      | C_Receivable_Acct      |             | 5.95 EUR    |



























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_150
  @from:cucumber
  Scenario: allocate sales one invoice and one purchase invoice to each other

    Given metasfresh contains M_Products:
      | Identifier    |
      | product_150_1 |
      | product_150_2 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID |
      | salesPLV               | product_150_1 | 5.00     | PCE      |
      | purchasePLV            | product_150_2 | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_150_1  | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_150_2  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID  | QtyInvoiced |
      | invl_150_2 | inv_150_2    | product_150_2 | 2 PCE       |
      | invl_150_1 | inv_150_1    | product_150_1 | 1 PCE       |
    And the invoice identified by inv_150_1 is completed
    And the invoice identified by inv_150_2 is completed

    And allocate payments to invoices
      | C_Invoice_ID |
      | inv_150_1    |
      | inv_150_2    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_150_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_150_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 5.95    | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_150_1    | 5.95   | 0            | alloc1             |
      | inv_150_2    | -5.95  | -5.95        | alloc1             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |
      | C_Receivable_Acct     | 0 EUR       | 5.95 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_160
  @from:cucumber
  Scenario: allocate payment to sales invoice that has a credit memo created

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_160 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_160  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_160    | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_160   | inv_160      | product_160  | 1 PCE       |
    And the invoice identified by inv_160 is completed

    And create credit memo for C_Invoice
      | CreditMemo      | C_Invoice_ID | CreditMemo.PriceEntered |
      | credit_memo_160 | inv_160      | 2.00                    |
    And the invoice identified by credit_memo_160 is completed

    And validate C_AllocationLines
      | C_Invoice_ID    | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | credit_memo_160 | -2.38  | 0            | alloc1             |
      | inv_160         | 2.38   | 0            | alloc1             |

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_160 | bpartner      | 5.95 EUR | true      | org_EUR_account     |
    And the payment identified by payment_160 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_160      | payment_160  |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | credit_memo_160 | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
      | inv_160         | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_160  | false       | 2.38    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_160      | payment_160  | 3.57   | 0            | alloc2             |

    And no Fact_Acct records are found for payment allocation alloc1
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 3.57 EUR    | 0 EUR       |
      | C_Receivable_Acct      | 0 EUR       | 3.57 EUR    |



























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_170
  @from:cucumber
  Scenario: allocate outbound payment to sales invoice

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_170 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product_170  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_170    | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_170   | inv_170      | product_170  | 1 PCE       |
    And the invoice identified by inv_170 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_170 | bpartner      | 5.95 EUR | false     | org_EUR_account     |
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
  Scenario: allocate payment to purchase invoice for the full amount

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_180 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_180  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_180    | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_180   | inv_180      | product_180  | 1 PCE       |
    And the invoice identified by inv_180 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_180 | bpartner      | 5.95 EUR | false     | org_EUR_account     |
    And the payment identified by payment_180 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_180      | payment_180  |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_180      | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_180  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_180      | payment_180  | -5.95  | 0            | alloc1             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_190
  @from:cucumber
  Scenario: allocate payment to multiple purchase invoices with payment open amount left

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_190 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_190  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_190_1  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_190_2  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_190_1 | inv_190_1    | product_190  | 1 PCE       |
      | invl_190_2 | inv_190_2    | product_190  | 1 PCE       |
    And the invoice identified by inv_190_1 is completed
    And the invoice identified by inv_190_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment_190 | bpartner      | 14.00 EUR | false     | org_EUR_account     |
    And the payment identified by payment_190 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_190_1    | payment_190  |
      | inv_190_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_190_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
      | inv_190_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_190  | false       | 2.10    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_190_1    | payment_190  | -5.95  | 0            | alloc1             |
      | inv_190_2    | payment_190  | -5.95  | 0            | alloc2             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_200
  @from:cucumber
  Scenario: allocate payment to multiple purchase invoices with invoice open amount left and then apply write off

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_200 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_200  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_200_1  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_200_2  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_200_1 | inv_200_1    | product_200  | 1 PCE       |
      | invl_200_2 | inv_200_2    | product_200  | 1 PCE       |
    And the invoice identified by inv_200_1 is completed
    And the invoice identified by inv_200_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_200 | bpartner      | 9.00 EUR | false     | org_EUR_account     |
    And the payment identified by payment_200 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_200_1    | payment_200  |
      | inv_200_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_200_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_200_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_200_1    | payment_200  | -5.95  | 0            | alloc1             |
      | inv_200_2    | payment_200  | -3.05  | -2.9         | alloc2             |

    And apply WRITEOFF to invoices
      | C_Invoice_ID | Amount |
      | inv_200_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_200_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_200  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | C_AllocationHdr_ID |
      | inv_200_2    | 0      | -2.9        | alloc3             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 3.05 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.05 EUR    |
    And Fact_Acct records are found for payment allocation alloc3
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 2.9 EUR     | 0 EUR       |
      | WriteOff_Acct         | 0 EUR       | 2.9 EUR     |
      | WriteOff_Acct         | -0.46 EUR   | 0 EUR       |
      | T_Credit_Acct         | 0 EUR       | -0.46 EUR   |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_210
  @from:cucumber
  Scenario: allocate payment to multiple purchase invoices with invoice open amount left and then apply discount

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_210 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_210  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_210_1  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_210_2  | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_210_1 | inv_210_1    | product_210  | 1 PCE       |
      | invl_210_2 | inv_210_2    | product_210  | 1 PCE       |
    And the invoice identified by inv_210_1 is completed
    And the invoice identified by inv_210_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_210 | bpartner      | 9.00 EUR | false     | org_EUR_account     |
    And the payment identified by payment_210 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_210_1    | payment_210  |
      | inv_210_2    |              |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_210_1    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   |         | false           |
      | inv_210_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | false  | 2.9     | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_210_1    | payment_210  | -5.95  | 0            | alloc1             |
      | inv_210_2    | payment_210  | -3.05  | -2.9         | alloc2             |

    And apply DISCOUNT to invoices
      | C_Invoice_ID | Amount |
      | inv_210_2    | 2.9    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | IsPaid | IsPartiallyPaid |
      | inv_210_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_210  | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | DiscountAmt | C_AllocationHdr_ID |
      | inv_210_2    | 0      | -2.9        | alloc3             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 3.05 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.05 EUR    |
    And Fact_Acct records are found for payment allocation alloc3
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 2.9 EUR     | 0 EUR       |
      | PayDiscount_Rev_Acct  | 0 EUR       | 2.9 EUR     |
      | PayDiscount_Rev_Acct  | 0.46 EUR    | 0 EUR       |
      | T_Credit_Acct         | 0 EUR       | 0.46 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_220
  @from:cucumber
  Scenario: allocate payment to purchase invoice that has a credit memo created

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_220 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_220  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_220    | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
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
      | payment_220 | bpartner      | 5.95 EUR | false     | org_EUR_account     |
    And the payment identified by payment_220 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | inv_220      | payment_220  |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm   | processed | docStatus | OpenAmt | IsPaid | IsPartiallyPaid |
      | credit_memo_220 | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | 0       | true   | false           |
      | inv_220         | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | 0       | true   | false           |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt |
      | payment_220  | false       | 2.38    |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_220      | payment_220  | -3.57  | 0            | alloc2             |

    And no Fact_Acct records are found for payment allocation alloc1
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 3.57 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.57 EUR    |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_230
  @from:cucumber
  Scenario: allocate inbound payment to purchase invoice

    Given metasfresh contains M_Products:
      | Identifier  |
      | product_230 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product_230  | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_230    | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_230   | inv_230      | product_230  | 1 PCE       |
    And the invoice identified by inv_230 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_230 | bpartner      | 5.95 EUR | true      | org_EUR_account     |
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
  Scenario: allocate one inbound payment and one outbound payment to each other
    When metasfresh contains C_Payment
      | Identifier    | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment_210_1 | bpartner      | 9.00 EUR | false     | org_EUR_account     |
      | payment_210_2 | bpartner      | 5.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_210_1 is completed
    And the payment identified by payment_210_2 is completed
    And allocate payments to invoices
      | C_Payment_ID  |
      | payment_210_1 |
      | payment_210_2 |

    Then validate payments
      | C_Payment_ID  | IsAllocated | OpenAmt |
      | payment_210_1 | false       | 4.00    |
      | payment_210_2 | true        | 0.00    |
    And validate C_AllocationLines
      | C_Payment_ID  | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | payment_210_1 | -5     | -4           | alloc1             |
      | payment_210_2 | 5      | 0            | alloc1             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 5 EUR       | 0 EUR       |
      | B_PaymentSelect_Acct   | 0 EUR       | 5 EUR       |


















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################


  @Id:S0465_320
  @from:cucumber
  Scenario: allocate a sales invoice with a purchase credit memo => no allocations

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
      | salesInvoice1       | bpartner2     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | purchaseCreditMemo1 | bpartner      | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |



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
  Scenario: allocate a sales credit memo with outbound payment

    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesCreditMemo1 | bpartner      | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |



    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID | QtyInvoiced |
      | salesCreditMemo1 | product1     | 1 PCE       |


    When metasfresh contains C_Payment
      | Identifier       | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | outboundPayment1 | bpartner      | 5.00 EUR | false     | org_EUR_account     |

    And the invoice identified by salesCreditMemo1 is completed
    And the payment identified by outboundPayment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID     | C_Payment_ID     |
      | salesCreditMemo1 | outboundPayment1 |

    And validate C_AllocationLines
      | C_Invoice_ID     | C_Payment_ID     | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | salesCreditMemo1 | outboundPayment1 | -5     | -0.95        | alloc1             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | C_Receivable_Acct     | 5 EUR       | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 5 EUR       |



















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################


  @Id:S0465_340
  @from:cucumber
  Scenario: allocate a sales credit memo with inbound payment => no allocations

    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesCreditMemo1 | bpartner      | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |



    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID | QtyInvoiced |
      | salesCreditMemo1 | product1     | 1 PCE       |


    When metasfresh contains C_Payment
      | Identifier      | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | inboundPayment1 | bpartner      | 5.00 EUR | true      | org_EUR_account     |

    And the invoice identified by salesCreditMemo1 is completed
    And the payment identified by inboundPayment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID     | C_Payment_ID    |
      | salesCreditMemo1 | inboundPayment1 |

    And there are no allocation lines for invoice
      | C_Invoice_ID     |
      | salesCreditMemo1 |
































