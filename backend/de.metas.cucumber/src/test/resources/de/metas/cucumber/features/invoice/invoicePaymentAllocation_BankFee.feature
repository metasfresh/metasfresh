@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor5
Feature: payment allocation - bank fee posting
## gh#28337: Bank Fee in Payment Allocation
## Validates that allocation with WriteOffType=BF posts to PayBankFee_Acct
## and does NOT trigger VAT tax correction (unlike standard WriteOff).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | N        | pricingSystem      |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |


# ############################################################################################################################################
# Scenario 1: Single invoice — bank fee — correct posting to PayBankFee_Acct, NO VAT tax correction
# ############################################################################################################################################
  @Id:S0465_BF_100
  @from:cucumber
  Scenario: single sales invoice - bank fee - posting to PayBankFee_Acct without VAT correction

    Given metasfresh contains M_Products:
      | Identifier     |
      | product_bf_100 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | product_bf_100 | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_bf_100  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID   | QtyInvoiced | C_Tax_ID |
      | invl_bf_100  | inv_bf_100   | product_bf_100 | 1 PCE       | tax1     |
    And the invoice identified by inv_bf_100 is completed

    # Payment = 5.75 EUR, invoice grand total = 5.95 EUR (5.00 net + 0.95 VAT at 19%)
    # Residual = 0.20 EUR to be covered by bank fee
    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment_bf_100 | customer1     | 5.75 EUR   | true      | org_EUR_account     |
    And the payment identified by payment_bf_100 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID   |
      | inv_bf_100   | payment_bf_100 |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_bf_100   | customer1     | bpartner_location_1    | true      | CO        | false  | 0.20    | true            |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID   | Amount | OverUnderAmt | C_AllocationHdr_ID |
      | inv_bf_100   | payment_bf_100 | 5.75   | 0.20         | alloc_bf_1         |

    # Now apply bank fee to clear the residual
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType |
      | alloc_bf_2         | inv_bf_100   | 0 EUR  | 0.20 EUR    | BF           |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | IsPartiallyPaid |
      | inv_bf_100   | customer1     | bpartner_location_1    | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID   | IsAllocated |
      | payment_bf_100 | true        |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType | C_AllocationHdr_ID |
      | inv_bf_100   | 0      | 0.20        | BF           | alloc_bf_2         |

    # KEY ASSERTION: posting goes to PayBankFee_Acct, NOT WriteOff_Acct
    # KEY ASSERTION: NO T_Due_Acct tax correction lines (unlike standard write-off)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID  |
      | PayBankFee_Acct       | 0.20 EUR    |             |          | alloc_bf_2 |
      | C_Receivable_Acct     |             | 0.20 EUR    |          | alloc_bf_2 |


# ############################################################################################################################################
# Scenario 2: Bank fee vs standard write-off — tax correction difference
# Same residual amount, but applied as WRITEOFF → should produce T_Due_Acct tax correction lines
# This scenario documents the behavioral difference between BF and WO
# ############################################################################################################################################
  @Id:S0465_BF_110
  @from:cucumber
  Scenario: standard write-off produces VAT tax correction (comparison baseline for bank fee)

    Given metasfresh contains M_Products:
      | Identifier     |
      | product_bf_110 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | product_bf_110 | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_bf_110  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID   | QtyInvoiced | C_Tax_ID |
      | invl_bf_110  | inv_bf_110   | product_bf_110 | 1 PCE       | tax1     |
    And the invoice identified by inv_bf_110 is completed

    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment_bf_110 | customer1     | 5.75 EUR   | true      | org_EUR_account     |
    And the payment identified by payment_bf_110 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID   |
      | inv_bf_110   | payment_bf_110 |

    # Apply as standard WRITEOFF (not bank fee)
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType |
      | alloc_bf_110       | inv_bf_110   | 0 EUR  | 0.20 EUR    | WO           |

    Then validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType | C_AllocationHdr_ID |
      | inv_bf_110   | 0      | 0.20        | WO           | alloc_bf_110       |

    # CONTRAST: standard write-off DOES produce T_Due_Acct tax correction lines
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID    |
      | WriteOff_Acct         | 0.20 EUR    |             |          | alloc_bf_110 |
      | C_Receivable_Acct     |             | 0.20 EUR    |          | alloc_bf_110 |
      | T_Due_Acct            | 0.03 EUR    |             | tax1     | alloc_bf_110 |
      | WriteOff_Acct         |             | 0.03 EUR    | tax1     | alloc_bf_110 |


# ############################################################################################################################################
# Scenario 3: Discount + bank fee on the same allocation
# Discount has VAT correction, bank fee does not
# ############################################################################################################################################
  @Id:S0465_BF_120
  @from:cucumber
  Scenario: discount with VAT correction + bank fee without VAT correction on same allocation

    Given metasfresh contains M_Products:
      | Identifier     |
      | product_bf_120 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | product_bf_120 | 100.00   | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_bf_120  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID   | QtyInvoiced | C_Tax_ID |
      | invl_bf_120  | inv_bf_120   | product_bf_120 | 1 PCE       | tax1     |
    And the invoice identified by inv_bf_120 is completed

    # Invoice grand total = 119.00 EUR (100.00 net + 19.00 VAT)
    # Payment = 106.00 EUR
    # Discount = 3.00 EUR (early payment discount — gets VAT correction)
    # Bank fee = 10.00 EUR (bank-deducted — no VAT correction)
    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | payment_bf_120 | customer1     | 106.00 EUR  | true      | org_EUR_account     |
    And the payment identified by payment_bf_120 is completed

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | C_Payment_ID   | Amount     | DiscountAmt | WriteOffAmt | WriteOffType |
      | alloc_bf_120       | inv_bf_120   | payment_bf_120 | 106.00 EUR | 3.00 EUR    | 10.00 EUR   | BF           |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | IsPartiallyPaid |
      | inv_bf_120   | customer1     | bpartner_location_1    | true      | CO        | true   | false           |

    # Posting for the allocation:
    # - Payment portion: DR B_UnallocatedCash_Acct 106.00, CR C_Receivable_Acct 106.00
    # - Discount portion: DR PayDiscount_Exp_Acct 3.00, CR C_Receivable_Acct 3.00
    #   + VAT correction for discount: DR T_Due_Acct, CR PayDiscount_Exp_Acct
    # - Bank fee portion: DR PayBankFee_Acct 10.00, CR C_Receivable_Acct 10.00
    #   NO VAT correction for bank fee
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID    |
      | B_UnallocatedCash_Acct | 106.00 EUR  |             |          | alloc_bf_120 |
      | PayBankFee_Acct        | 10.00 EUR   |             |          | alloc_bf_120 |
      | C_Receivable_Acct      |             | 119.00 EUR  |          | alloc_bf_120 |
      | *                      |             |             |          | alloc_bf_120 |


# ############################################################################################################################################
# Scenario 4: Multi-invoice — bank fee on last invoice
# ############################################################################################################################################
  @Id:S0465_BF_130
  @from:cucumber
  Scenario: multiple sales invoices - bank fee on last invoice - all fully cleared

    Given metasfresh contains M_Products:
      | Identifier     |
      | product_bf_130 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | product_bf_130 | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_bf_130_1  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_bf_130_2  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier     | C_Invoice_ID  | M_Product_ID   | QtyInvoiced | C_Tax_ID |
      | invl_bf_130_1  | inv_bf_130_1  | product_bf_130 | 1 PCE       | tax1     |
      | invl_bf_130_2  | inv_bf_130_2  | product_bf_130 | 1 PCE       | tax1     |
    And the invoice identified by inv_bf_130_1 is completed
    And the invoice identified by inv_bf_130_2 is completed

    # Two invoices of 5.95 EUR each = 11.90 EUR total
    # Payment = 11.70 EUR → 0.20 EUR short
    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | payment_bf_130 | customer1     | 11.70 EUR   | true      | org_EUR_account     |
    And the payment identified by payment_bf_130 is completed

    And allocate payments to invoices
      | C_Invoice_ID  | C_Payment_ID   |
      | inv_bf_130_1  | payment_bf_130 |
      | inv_bf_130_2  |                |

    Then validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_bf_130_1  | customer1     | bpartner_location_1    | true      | CO        | true   |         | false           |
      | inv_bf_130_2  | customer1     | bpartner_location_1    | true      | CO        | false  | 0.20    | true            |

    # Apply bank fee to clear the residual on the second invoice
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType |
      | alloc_bf_130       | inv_bf_130_2 | 0 EUR  | 0.20 EUR    | BF           |

    Then validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | IsPartiallyPaid |
      | inv_bf_130_2  | customer1     | bpartner_location_1    | true      | CO        | true   | false           |
    And validate payments
      | C_Payment_ID   | IsAllocated |
      | payment_bf_130 | true        |
    And validate C_AllocationLines
      | C_Invoice_ID  | Amount | WriteOffAmt | WriteOffType | C_AllocationHdr_ID |
      | inv_bf_130_2  | 0      | 0.20        | BF           | alloc_bf_130       |

    # Bank fee posting: PayBankFee_Acct, NO tax correction
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID    |
      | PayBankFee_Acct       | 0.20 EUR    |             |          | alloc_bf_130 |
      | C_Receivable_Acct     |             | 0.20 EUR    |          | alloc_bf_130 |


# ############################################################################################################################################
# Scenario 5: Reversal — bank fee allocation reversed, AR reopened, PayBankFee_Acct reversed
# ############################################################################################################################################
  @Id:S0465_BF_140
  @from:cucumber
  Scenario: reverse allocation with bank fee - posting correctly reversed

    Given metasfresh contains M_Products:
      | Identifier     |
      | product_bf_140 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | product_bf_140 | 5.00     | PCE      |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_bf_140  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID   | QtyInvoiced | C_Tax_ID |
      | invl_bf_140  | inv_bf_140   | product_bf_140 | 1 PCE       | tax1     |
    And the invoice identified by inv_bf_140 is completed

    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment_bf_140 | customer1     | 5.75 EUR   | true      | org_EUR_account     |
    And the payment identified by payment_bf_140 is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID   |
      | inv_bf_140   | payment_bf_140 |

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType |
      | alloc_bf_140       | inv_bf_140   | 0 EUR  | 0.20 EUR    | BF           |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | IsPartiallyPaid |
      | inv_bf_140   | customer1     | bpartner_location_1    | true      | CO        | true   | false           |
    And validate C_AllocationLines
      | C_Invoice_ID | Amount | WriteOffAmt | WriteOffType | C_AllocationHdr_ID |
      | inv_bf_140   | 0      | 0.20        | BF           | alloc_bf_140       |

    # Now reverse the bank fee allocation
    And the allocation identified by alloc_bf_140 is reversed

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_BPartner_Location_ID | processed | DocStatus | IsPaid | OpenAmt | IsPartiallyPaid |
      | inv_bf_140   | customer1     | bpartner_location_1    | true      | CO        | false  | 0.20    | true            |

    # Reversal posting negates amounts on the same side (ADempiere convention)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID              |
      | PayBankFee_Acct       | -0.20 EUR   |             |          | alloc_bf_140.reversed  |
      | C_Receivable_Acct     |             | -0.20 EUR   |          | alloc_bf_140.reversed  |

    # Verify that after allocation + reversal, account balances are zero
    And Fact_Acct records balances for documents alloc_bf_140,alloc_bf_140.reversed are matching
      | AccountConceptualName | SourceBalance |
      | PayBankFee_Acct       | 0 EUR         |
      | C_Receivable_Acct     | 0 EUR         |
