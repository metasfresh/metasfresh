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
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | bpartner   | Y          | pricingSystem      |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | bpartner      | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    
    
    
    
    
    
    
    
    
    

    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_100
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
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID   |
      | *                      |             |             |               | payment_100 |
      | B_UnallocatedCash_Acct |             | 5.95 EUR    | bpartner      | payment_100 |
      | B_UnallocatedCash_Acct | 5.95 EUR    |             | bpartner      | alloc1      |
      | C_Receivable_Acct      |             | 5.95 EUR    | bpartner      | alloc1      |
      | C_Receivable_Acct      | 5.95 EUR    |             | bpartner      | inv_100     |
      | *                      |             |             |               | inv_100     |
    















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_110
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
  @Id:S0132_120
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
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_120_1 | inv_120_1    | product_120  | 1 PCE       | tax1     |
      | invl_120_2 | inv_120_2    | product_120  | 1 PCE       | tax1     |
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
      | inv_120_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
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
  @Id:S0132_130
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
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_130_1 | inv_130_1    | product_130  | 1 PCE       | tax1     |
      | invl_130_2 | inv_130_2    | product_130  | 1 PCE       | tax1     |
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
      | inv_130_2    | bpartner      | bpartner_location_1    | 30 Tage netto | true      | CO        | true   | false           |
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
  @Id:S0132_140
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
  @Id:S0132_150
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
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | *                     |             |             | inv_150_2 |
      | V_Liability_Acct      |             | 11.9 EUR    | inv_150_2 |
      | V_Liability_Acct      | 5.95 EUR    |             | alloc1    |
      | C_Receivable_Acct     |             | 5.95 EUR    | alloc1    |
      | C_Receivable_Acct     | 5.95 EUR    |             | inv_150_1 |
      | *                     |             |             | inv_150_1 |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_160
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

    And no Fact_Acct records are found for documents alloc1
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 3.57 EUR    | 0 EUR       | alloc2    |
      | C_Receivable_Acct      | 0 EUR       | 3.57 EUR    | alloc2    |



























# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_170
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
  @Id:S0132_180
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

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       | alloc1    |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    | alloc1    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_190
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

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       | alloc1    |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    | alloc1    |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 5.95 EUR    | 0 EUR       | alloc2    |
      | B_PaymentSelect_Acct  | 0 EUR       | 5.95 EUR    | alloc2    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_200
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
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_200_1 | inv_200_1    | product_200  | 1 PCE       | tax1     |
      | invl_200_2 | inv_200_2    | product_200  | 1 PCE       | tax1     |
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
      | WriteOff_Acct         | 0 EUR       | 2.9 EUR     |          | alloc3    |
      | WriteOff_Acct         | -0.46 EUR   | 0 EUR       | tax1     | alloc3    |
      | T_Credit_Acct         | 0 EUR       | -0.46 EUR   | tax1     | alloc3    |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_210
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
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_210_1 | inv_210_1    | product_210  | 1 PCE       | tax1     |
      | invl_210_2 | inv_210_2    | product_210  | 1 PCE       | tax1     |
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
  @Id:S0132_220
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

    And no Fact_Acct records are found for documents alloc1
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 3.57 EUR    | 0 EUR       | alloc2    |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.57 EUR    | alloc2    |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_230
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
  @Id:S0132_240
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

    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 5 EUR       | 0 EUR       | alloc1    |
      | B_PaymentSelect_Acct   | 0 EUR       | 5 EUR       | alloc1    |
























































# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_250
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: (Sales) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 payments to a sales invoice
  - allocate 1st payment to sales invoice for partial amount, paymentTerm discount is not applied
  - allocate 2nd payment to sales invoice for remaining amount, paymentTerm discount is applied

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_27102022_1 | salesPLV               | product      | 2.00     | PCE               |

#    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice1   | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice1     | product      | 10 PCE      |
    And the invoice identified by invoice1 is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt | IsReceipt | C_BP_BankAccount_ID | DateTrx    | DateAcct   |
      | payment1   | bpartner      | 1 EUR  | true      | org_EUR_account     | 2022-05-11 | 2022-05-11 |
    And the payment identified by payment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | invoice1     | payment1     |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | C_PaymentTerm_ID | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 %      | true      | CO        | false  |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment1     | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | payment1     | 1      | 22.8         | 0           | 0           | alloc1             |

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID | DateTrx    | DateAcct   |
      | payment2   | bpartner      | 22.56 EUR | true      | org_EUR_account     | 2022-05-11 | 2022-05-11 |
    And the payment identified by payment2 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | invoice1     | payment2     |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment2     | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | payment2     | 22.56  | 0            | 0           | 0.24        | alloc2             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 1 EUR       | 0 EUR       |
      | C_Receivable_Acct      | 0 EUR       | 1 EUR       |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | C_Receivable_Acct      | 0 EUR       | 22.8 EUR    |
      | B_UnallocatedCash_Acct | 22.56 EUR   | 0 EUR       |
      | PayDiscount_Exp_Acct   | 0.24 EUR    | 0 EUR       |
      # Tax correction:
      | T_Due_Acct             | 0.04 EUR    | 0 EUR       |
      | PayDiscount_Exp_Acct   | 0 EUR       | 0.04 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_260
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: (Purchase) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 payments to a purchase invoice
  - allocate 1st payment to purchase invoice for partial amount, paymentTerm discount is not applied
  - allocate 2nd payment to purchase invoice for remaining amount, paymentTerm discount is applied

    When metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | purchasePLV            | product      | 2.00     | PCE               |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice1   | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice1     | product      | 10 PCE      |
    And the invoice identified by invoice1 is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt | IsReceipt | C_BP_BankAccount_ID | DateTrx    | DateAcct   |
      | payment1   | bpartner      | 1 EUR  | false     | org_EUR_account     | 2022-05-11 | 2022-05-11 |
    And the payment identified by payment1 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | invoice1     | payment1     |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | C_PaymentTerm_ID | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 %      | true      | CO        | false  |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment1     | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | payment1     | -1     | -22.8        | 0           | 0           | alloc1             |

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID | DateTrx    | DateAcct   |
      | payment2   | bpartner      | 22.56 EUR | false     | org_EUR_account     | 2022-05-11 | 2022-05-11 |
    And the payment identified by payment2 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | invoice1     | payment2     |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | C_PaymentTerm_ID | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 %      | true      | CO        | true   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment2     | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | payment2     | -22.56 | 0            | 0           | -0.24       | alloc2             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 1 EUR       | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 1 EUR       |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 22.8 EUR    | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 22.56 EUR   |
      | PayDiscount_Rev_Acct  | 0 EUR       | 0.24 EUR    |
      # Tax Correction:
      | PayDiscount_Rev_Acct  | 0.04 EUR    | 0 EUR       |
      | T_Credit_Acct         | 0 EUR       | 0.04 EUR    |
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_270
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: (Sales) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 payments to a sales invoice (allocation happens only once)
  - allocate 1st payment to sales invoice for partial amount, paymentTerm discount is not applied
  - allocate 2nd payment to sales invoice for remaining amount, paymentTerm discount is applied

    When metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | salesPLV               | product      | 2.00     | PCE               |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice1   | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice1     | product      | 10 PCE      |
    And the invoice identified by invoice1 is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt | IsReceipt | C_BP_BankAccount_ID | DateTrx    | DateAcct   |
      | payment1   | bpartner      | 1 EUR  | true      | org_EUR_account     | 2022-05-11 | 2022-05-11 |
    And the payment identified by payment1 is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID | DateTrx    | DateAcct   |
      | payment2   | bpartner      | 22.56 EUR | true      | org_EUR_account     | 2022-05-11 | 2022-05-11 |
    And the payment identified by payment2 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | invoice1     | payment1     |
      |              | payment2     |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment1     | true        |
      | payment2     | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | payment1     | 1      | 22.8         | 0           | 0           | alloc1             |
      | invoice1     | payment2     | 22.56  | 0            | 0           | 0.24        | alloc2             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | B_UnallocatedCash_Acct | 1 EUR       | 0 EUR       |
      | C_Receivable_Acct      | 0 EUR       | 1 EUR       |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr |
      | C_Receivable_Acct      | 0 EUR       | 22.8 EUR    |
      | B_UnallocatedCash_Acct | 22.56 EUR   | 0 EUR       |
      | PayDiscount_Exp_Acct   | 0.24 EUR    | 0 EUR       |
      # Tax correction:
      | T_Due_Acct             | 0.04 EUR    | 0 EUR       |
      | PayDiscount_Exp_Acct   | 0 EUR       | 0.04 EUR    |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_280
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: (Sales) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 credit memos to a sales invoice
  - allocate 1st credit memo to sales invoice for partial amount, invoice's paymentTerm discount is not applied, credit memo's paymentTerm discount is applied
  - allocate 2nd credit memo to sales invoice for remaining amount, paymentTerm discount is applied for both invoice, but not for credit memo (because it is not fully allocated)

    When metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | salesPLV               | product      | 2.00     | PCE               |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice1   | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice1     | product      | 10 PCE      |
    And the invoice identified by invoice1 is completed

    #
    # First Credit Memo
    #
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | creditMemo1 | bpartner      | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | creditMemo1  | product      | 1 PCE       |
    And the invoice identified by creditMemo1 is completed
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID | CreditMemo.C_Invoice_ID |
      | invoice1     | creditMemo1             |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | false  |
      | creditMemo1  | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | 2.4    | 21.4         | 0           | 0           | alloc1             |
      | creditMemo1  | -2.4   | 0            | 0           | 0.02        | alloc1             |

    #
    # Second Credit Memo
    #
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | creditMemo2 | bpartner      | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | creditMemo2  | product      | 9 PCE       |
    And the invoice identified by creditMemo2 is completed
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID | CreditMemo.C_Invoice_ID |
      | invoice1     | creditMemo2             |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
      | creditMemo2  | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | false  |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | creditMemo2  | -21.16 | -0.47        | 0           | 0           | alloc2             |
    
    #
    # Final Validations
    #
    And validate C_AllocationLines for invoice invoice1
      | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | 2.4    | 21.4         | 0           | 0           | alloc1             |
      | 21.16  | 0            | 0           | 0.24        | alloc2             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | C_Receivable_Acct     | -0.02 EUR   | 0 EUR       |
      | PayDiscount_Rev_Acct  | 0 EUR       | -0.02 EUR   |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | PayDiscount_Exp_Acct  | 0.24 EUR    | 0 EUR       |
      | C_Receivable_Acct     | 0 EUR       | 0.24 EUR    |
      | T_Due_Acct            | 0.04 EUR    | 0 EUR       |
      | PayDiscount_Exp_Acct  | 0 EUR       | 0.04 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_290
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: (Purchase) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 credit memos to a purchase invoice
  - allocate 1st credit memo to purchase invoice for partial amount, invoice's paymentTerm discount is not applied, credit memo's paymentTerm discount is applied
  - allocate 2nd credit memo to purchase invoice for remaining amount, paymentTerm discount is applied for invoice, but not for credit memo (because it is not fully allocated)

    When metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | purchasePLV            | product      | 2.00     | PCE               |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice1   | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice1     | product      | 10 PCE      |
    And the invoice identified by invoice1 is completed
    
    
    #
    # First Credit Memo
    #
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | creditMemo1 | bpartner      | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | creditMemo1  | product      | 1 PCE       |
    And the invoice identified by creditMemo1 is completed
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID | CreditMemo.C_Invoice_ID |
      | invoice1     | creditMemo1             |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | false  |
      | creditMemo1  | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | -2.4   | -21.4        | 0           | 0           | alloc1             |
      | creditMemo1  | 2.4    | 0            | 0           | -0.02       | alloc1             |

    #
    # Second Credit Memo
    #
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | creditMemo2 | bpartner      | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | creditMemo2  | product      | 9 PCE       |
    And the invoice identified by creditMemo2 is completed
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID | CreditMemo.C_Invoice_ID |
      | invoice1     | creditMemo2             |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
      | creditMemo2  | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | false  |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | creditMemo2  | 21.16  | 0.47         | 0           | 0           | alloc2             |
    
    
    #
    # Final Validations
    #
    And validate C_AllocationLines for invoice invoice1
      | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | -2.4   | -21.4        | 0           | 0           | alloc1             |
      | -21.16 | 0            | 0           | -0.24       | alloc2             |
    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | PayDiscount_Exp_Acct  | -0.02 EUR   | 0 EUR       |
      | V_Liability_Acct      | 0 EUR       | -0.02 EUR   |
    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 0.24 EUR    | 0 EUR       |
      | PayDiscount_Rev_Acct  | 0 EUR       | 0.24 EUR    |
      # Tax correction:
      | PayDiscount_Rev_Acct  | 0.04 EUR    | 0 EUR       |
      | T_Credit_Acct         | 0 EUR       | 0.04 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_300
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: Two purchase invoices allocated to a sales invoice
  - allocate 1st purchase invoice to sales invoice for partial amount, sales invoice's paymentTerm discount is not applied, purchase invoice's paymentTerm discount is applied
  - allocate 2nd purchase invoice to sales invoice for remaining amount, paymentTerm discount is applied for sales invoice, but not for purchase invoice (because it is not fully allocated)

    When metasfresh contains M_Products:
      | Identifier         |
      | product_01112022_1 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID.X12DE355 |
      | purchasePLV            | product_01112022_1 | 2.00     | PCE               |
      | salesPLV               | product_01112022_1 | 2.00     | PCE               |

    #
    # Sales Invoice
    #
    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | salesInvoice1 | bpartner      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID  | M_Product_ID       | QtyInvoiced |
      | salesInvoice1 | product_01112022_1 | 10 PCE      |
    And the invoice identified by salesInvoice1 is completed
      
    #
    # Purchase Invoice 1
    #
    #    GrandTotal = 2.38, Discount = 0.02
    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | purchaseInvoice1 | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID       | QtyInvoiced |
      | purchaseInvoice1 | product_01112022_1 | 1 PCE       |
    And the invoice identified by purchaseInvoice1 is completed
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID  | Purchase.C_Invoice_ID |
      | salesInvoice1 | purchaseInvoice1      |
    Then validate created invoices
      | C_Invoice_ID     | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | salesInvoice1    | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | false  |
      | purchaseInvoice1 | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
    And validate C_AllocationLines
      | C_Invoice_ID     | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | salesInvoice1    | 2.36   | 21.44        | 0           | 0           | alloc1             |
      | purchaseInvoice1 | -2.36  | 0            | 0           | -0.02       | alloc1             |

    #
    # Purchase Invoice 2
    #
    #    GrandTotal = 21.42, Discount = 0.21
    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | purchaseInvoice2 | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | 10 Tage 1 %      |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID       | QtyInvoiced |
      | purchaseInvoice2 | product_01112022_1 | 9 PCE       |
    And the invoice identified by purchaseInvoice2 is completed
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID  | Purchase.C_Invoice_ID |
      | salesInvoice1 | purchaseInvoice2      |
    Then validate created invoices
      | C_Invoice_ID     | C_BPartner_ID | C_BPartner_Location_ID | paymentTerm | processed | docStatus | IsPaid |
      | salesInvoice1    | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | true   |
      | purchaseInvoice2 | bpartner      | bpartner_location_1    | 10 Tage 1 % | true      | CO        | false  |
    And validate C_AllocationLines
      | C_Invoice_ID     | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | purchaseInvoice2 | -21.2  | -0.01        | 0           | 0           | alloc2             |
    
    #
    # Final Validations
    #
    And validate C_AllocationLines for invoice salesInvoice1
      | Amount | OverUnderAmt | WriteOffAmt | DiscountAmt | C_AllocationHdr_ID |
      | 2.36   | 21.44        | 0           | 0           | alloc1             |
      | 21.2   | 0            | 0           | 0.24        | alloc2             |
    
    # FIXME: atm alloc1 posting has error
    #Document ref{C_AllocationHdr/1000733} has posting error: de.metas.acct.doc.PostingException: Invalid accounting operation structure (2 DR lines, 2 CR lines)
    #Additional parameters:
    #DR 1: FactLine=[ref{C_AllocationHdr/1000733},V_Liability_Acct/2000_Verbindlichkeiten aus L+L CHF gegenüber Dritten,Cur=CurrencyId(repoId=102), DR=2.36|2.67, CR=0|0.00, Line=1000939, currencyRate=1.130000000000]
    #DR 2: FactLine=[ref{C_AllocationHdr/1000733},V_Liability_Acct/2000_Verbindlichkeiten aus L+L CHF gegenüber Dritten,Cur=CurrencyId(repoId=102), DR=0.02|0.02, CR=0|0.00, Line=1000939, currencyRate=1.130000000000]
    #CR 1: FactLine=[ref{C_AllocationHdr/1000733},C_Receivable_Acct/1100_Forderungen aus L+L,Cur=CurrencyId(repoId=102), DR=0|0.00, CR=2.36|2.67, Line=1000938, currencyRate=1.130000000000]
    #CR 2: FactLine=[ref{C_AllocationHdr/1000733},PayDiscount_Rev_Acct/4900_Skonti und Rabatte,Cur=CurrencyId(repoId=102), DR=0|0.00, CR=0.02|0.02, Line=1000939, currencyRate=1.130000000000]
    #Buchungsstatus: Verbuchungs-Fehler (Error)
    #Belegnummer: MAllocationHdr[1000733-1007288: Freigabe-Betrag=-0.02 (#2)]
    #Preserve Posted status: false
    #
    #    And Fact_Acct records are found for payment allocation alloc1
    #      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
    #      | V_Liability_Acct      | 1 EUR       | 0 EUR       |
    #      | B_PaymentSelect_Acct  | 0 EUR       | 1 EUR       |

    And Fact_Acct records are found for payment allocation alloc2
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | C_Receivable_Acct     | 0 EUR       | 21.44 EUR   |
      | PayDiscount_Exp_Acct  | 0.24 EUR    | 0 EUR       |
      | V_Liability_Acct      | 21.2 EUR    | 0 EUR       |
      | T_Due_Acct            | 0.04 EUR    | 0 EUR       |
      | PayDiscount_Exp_Acct  | 0 EUR       | 0.04 EUR    |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0132_310
  @from:cucumber
  @ignore #Temporarily ignored by https://github.com/metasfresh/me03/issues/22535
  Scenario: allocate payment to purchase invoice with overpayment and negative discount

    Given metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | purchasePLV            | product      | 1        | PCE               |

    # GrandTotal = 20.23
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice1   | bpartner      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice1     | product      | 17 PCE      |
    And the invoice identified by invoice1 is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt    | DiscountAmt | IsReceipt | C_BP_BankAccount_ID | C_Invoice_ID |
      | payment1   | bpartner      | 20.25 EUR | -0.02 EUR   | false     | org_EUR_account     | invoice1     |
    And the payment identified by payment1 is completed

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | C_PaymentTerm_ID | processed | docStatus | IsPaid |
      | invoice1     | bpartner      | bpartner_location_1    | 30 Tage netto    | true      | CO        | true   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment1     | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | DiscountAmt | C_AllocationHdr_ID |
      | invoice1     | payment1     | -20.25 | 0.02        | alloc1             |

    And Fact_Acct records are found for payment allocation alloc1
      | AccountConceptualName | AmtSourceDr | AmtSourceCr |
      | V_Liability_Acct      | 20.23 EUR   | 0 EUR       |
      | B_PaymentSelect_Acct  | 0 EUR       | 20.25 EUR   |
      | PayDiscount_Rev_Acct  | 0 EUR       | -0.02 EUR   |
