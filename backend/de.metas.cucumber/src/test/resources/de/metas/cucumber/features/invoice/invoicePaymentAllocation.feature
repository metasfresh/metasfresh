@from:cucumber
@ghActions:run_on_executor5
Feature: invoice payment allocation

  Background:

    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

    And metasfresh contains M_PricingSystems
      | Identifier                | Name                      | Value                     |
      | paymentAllocPricingSystem | paymentAllocPricingSystem | paymentAllocPricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier                 | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList      | paymentAllocPricingSystem     | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
      | paymentAllocPriceListNotSO | paymentAllocPricingSystem     | DE                        | EUR                 | PriceListName2 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier           | M_PriceList_ID.Identifier  | ValidFrom  |
      | paymentAllocPLV      | paymentAllocPriceList      | 2022-05-01 |
      | paymentAllocPLVNotSO | paymentAllocPriceListNotSO | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                  | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartnerTest_06062022 | Y              | paymentAllocPricingSystem     |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_1 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_BP_BankAccount
      | Identifier       | C_BPartner_ID.Identifier | C_Currency.ISO_Code |
      | bp_bank_account1 | bpartner_1               | EUR                 |

  @Id:S0132_100
  @from:cucumber
  Scenario: allocate payment to sales invoice for the full amount

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_100 | product_100 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_100     | paymentAllocPLV                   | product_100             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_100    | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_100   | inv_100                 | product_100             | 1           | PCE               |
    And the invoice identified by inv_100 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_100 | bpartner_1               | 5.95   | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_100 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_100                     | payment_100                 |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_100                 | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_100             | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_100                     | payment_100                 | 5.95       | 0                |

  @Id:S0132_110
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with payment open amount left

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_110 | product_110 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_110     | paymentAllocPLV                   | product_110             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_110_1  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_110_2  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_110_1 | inv_110_1               | product_110             | 1           | PCE               |
      | invl_110_2 | inv_110_2               | product_110             | 1           | PCE               |
    And the invoice identified by inv_110_1 is completed
    And the invoice identified by inv_110_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_110 | bpartner_1               | 14.00  | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_110 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_110_1                   | payment_110                 |
      | inv_110_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_110_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
      | inv_110_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt |
      | payment_110             | false                    | 2.10        |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_110_1                   | payment_110                 | 5.95       | 0                |
      | inv_110_2                   | payment_110                 | 5.95       | 0                |

  @Id:S0132_120
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with invoice open amount left and then apply write off

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_120 | product_120 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_120     | paymentAllocPLV                   | product_120             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_120_1  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_120_2  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_120_1 | inv_120_1               | product_120             | 1           | PCE               |
      | invl_120_2 | inv_120_2               | product_120             | 1           | PCE               |
    And the invoice identified by inv_120_1 is completed
    And the invoice identified by inv_120_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_120 | bpartner_1               | 9.00   | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_120 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_120_1                   | payment_120                 |
      | inv_120_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.OpenAmt | OPT.IsPartiallyPaid |
      | inv_120_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       |             | false               |
      | inv_120_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | false      | 2.9         | true                |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_120_1                   | payment_120                 | 5.95       | 0                |
      | inv_120_2                   | payment_120                 | 3.05       | 2.9              |

    And apply WRITEOFF to invoices
      | C_Invoice_ID.Identifier | Amount |
      | inv_120_2               | 2.9    |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_120_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_120             | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.WriteOffAmt |
      | inv_120_2                   | 0          | 2.9             |

  @Id:S0132_130
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with invoice open amount left and then apply discount

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_130 | product_130 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_130     | paymentAllocPLV                   | product_130             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_130_1  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_130_2  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_130_1 | inv_130_1               | product_130             | 1           | PCE               |
      | invl_130_2 | inv_130_2               | product_130             | 1           | PCE               |
    And the invoice identified by inv_130_1 is completed
    And the invoice identified by inv_130_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_130 | bpartner_1               | 9.00   | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_130 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_130_1                   | payment_130                 |
      | inv_130_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.OpenAmt | OPT.IsPartiallyPaid |
      | inv_130_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       |             | false               |
      | inv_130_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | false      | 2.9         | true                |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_130_1                   | payment_130                 | 5.95       | 0                |
      | inv_130_2                   | payment_130                 | 3.05       | 2.9              |

    And apply DISCOUNT to invoices
      | C_Invoice_ID.Identifier | Amount |
      | inv_130_2               | 2.9    |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_130_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_130             | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.DiscountAmt |
      | inv_130_2                   | 0          | 2.9             |

  @Id:S0132_140
  @from:cucumber
  Scenario: allocate payment to multiple sales invoices with payment open amount left and not matching on currency

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_140 | product_140 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_140     | paymentAllocPLV                   | product_140             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_140_1  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
      | inv_140_2  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_140_1 | inv_140_1               | product_140             | 1           | PCE               |
      | invl_140_2 | inv_140_2               | product_140             | 1           | PCE               |
    And the invoice identified by inv_140_1 is completed
    And the invoice identified by inv_140_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_140 | bpartner_1               | 14.00  | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_140 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_140_1                   | payment_140                 |
      | inv_140_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_140_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
      | inv_140_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt |
      | payment_140             | false                    | 2.10        |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_140_1                   | payment_140                 | 5.95       | 0                |
      | inv_140_2                   | payment_140                 | 5.95       | 0                |

  @Id:S0132_150
  @from:cucumber
  Scenario: allocate sales one invoice and one purchase invoice to each other

    Given metasfresh contains M_Products:
      | Identifier    | Name          |
      | product_150_1 | product_150_1 |
      | product_150_2 | product_150_2 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_150_1   | paymentAllocPLV                   | product_150_1           | 5.00     | PCE               | Normal                        |
      | pp_150_2   | paymentAllocPLVNotSO              | product_150_2           | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_150_1  | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
      | inv_150_2  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_150_2 | inv_150_2               | product_150_2           | 2           | PCE               |
      | invl_150_1 | inv_150_1               | product_150_1           | 1           | PCE               |
    And the invoice identified by inv_150_1 is completed
    And the invoice identified by inv_150_2 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier |
      | inv_150_1                   |
      | inv_150_2                   |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.OpenAmt | OPT.IsPartiallyPaid |
      | inv_150_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       |             | false               |
      | inv_150_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | false      | 5.95        | true                |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_150_1                   | 5.95       | 0                |
      | inv_150_2                   | -5.95      | -5.95            |

  @Id:S0132_160
  @from:cucumber
  Scenario: allocate payment to sales invoice that has a credit memo created

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_160 | product_160 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_160     | paymentAllocPLV                   | product_160             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_160    | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_160   | inv_160                 | product_160             | 1           | PCE               |
    And the invoice identified by inv_160 is completed

    And create credit memo for C_Invoice
      | CreditMemo.Identifier | C_Invoice_ID.Identifier | CreditMemo.PriceEntered | CreditMemo.C_DocType_ID.Name |
      | credit_memo_160       | inv_160                 | 2.00                    | Gutschrift                   |
    And the invoice identified by credit_memo_160 is completed

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | credit_memo_160             | -2.38      | 0                |
      | inv_160                     | 2.38       | 0                |

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_160 | bpartner_1               | 5.95   | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_160 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_160                     | payment_160                 |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | credit_memo_160         | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
      | inv_160                 | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt |
      | payment_160             | false                    | 2.38        |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_160                     | payment_160                 | 3.57       | 0                |

    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | C_Receivable_Acct      | 2.38 EUR    | 0 EUR       | alloc1    |
      | C_Receivable_Acct      | 0 EUR       | 2.38 EUR    | alloc1    |
    And Fact_Acct records balances for documents alloc1 are matching
      | AccountConceptualName | SourceBalance |
      | C_Receivable_Acct     | 0 EUR         |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID |
      | B_UnallocatedCash_Acct | 3.57 EUR    | 0 EUR       | alloc2    |
      | C_Receivable_Acct      | 0 EUR       | 3.57 EUR    | alloc2    |

  @Id:S0132_170
  @from:cucumber
  Scenario: allocate outbound payment to sales invoice

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_170 | product_170 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_170     | paymentAllocPLV                   | product_170             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_170    | bpartner_1               | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_170   | inv_170                 | product_170             | 1           | PCE               |
    And the invoice identified by inv_170 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_170 | bpartner_1               | 5.95   | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
    And the payment identified by payment_170 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_170                     | payment_170                 |

    Then validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_170             | false                    |
    And there are no allocation lines for invoice
      | C_Invoice_ID.Identifier |
      | inv_170                 |

  @Id:S0132_180
  @from:cucumber
  Scenario: allocate payment to purchase invoice for the full amount

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_180 | product_180 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_180     | paymentAllocPLVNotSO              | product_180             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_180    | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_180   | inv_180                 | product_180             | 1           | PCE               |
    And the invoice identified by inv_180 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_180 | bpartner_1               | 5.95   | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
    And the payment identified by payment_180 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_180                     | payment_180                 |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_180                 | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_180             | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_180                     | payment_180                 | -5.95      | 0                |

  @Id:S0132_190
  @from:cucumber
  Scenario: allocate payment to multiple purchase invoices with payment open amount left

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_190 | product_190 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_190     | paymentAllocPLVNotSO              | product_190             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_190_1  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_190_2  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_190_1 | inv_190_1               | product_190             | 1           | PCE               |
      | invl_190_2 | inv_190_2               | product_190             | 1           | PCE               |
    And the invoice identified by inv_190_1 is completed
    And the invoice identified by inv_190_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_190 | bpartner_1               | 14.00  | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
    And the payment identified by payment_190 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_190_1                   | payment_190                 |
      | inv_190_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_190_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
      | inv_190_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt |
      | payment_190             | false                    | 2.10        |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_190_1                   | payment_190                 | -5.95      | 0                |
      | inv_190_2                   | payment_190                 | -5.95      | 0                |

  @Id:S0132_200
  @from:cucumber
  Scenario: allocate payment to multiple purchase invoices with invoice open amount left and then apply write off

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_200 | product_200 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_200     | paymentAllocPLVNotSO              | product_200             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_200_1  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_200_2  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_200_1 | inv_200_1               | product_200             | 1           | PCE               |
      | invl_200_2 | inv_200_2               | product_200             | 1           | PCE               |
    And the invoice identified by inv_200_1 is completed
    And the invoice identified by inv_200_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_200 | bpartner_1               | 9.00   | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
    And the payment identified by payment_200 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_200_1                   | payment_200                 |
      | inv_200_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.OpenAmt | OPT.IsPartiallyPaid |
      | inv_200_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       |             | false               |
      | inv_200_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | false      | 2.9         | true                |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_200_1                   | payment_200                 | -5.95      | 0                |
      | inv_200_2                   | payment_200                 | -3.05      | -2.9             |

    And apply WRITEOFF to invoices
      | C_Invoice_ID.Identifier | Amount |
      | inv_200_2               | 2.9    |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_200_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_200             | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.WriteOffAmt |
      | inv_200_2                   | 0          | -2.9            |

  @Id:S0132_210
  @from:cucumber
  Scenario: allocate payment to multiple purchase invoices with invoice open amount left and then apply discount

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_210 | product_210 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_210     | paymentAllocPLVNotSO              | product_210             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_210_1  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | inv_210_2  | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_210_1 | inv_210_1               | product_210             | 1           | PCE               |
      | invl_210_2 | inv_210_2               | product_210             | 1           | PCE               |
    And the invoice identified by inv_210_1 is completed
    And the invoice identified by inv_210_2 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_210 | bpartner_1               | 9.00   | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
    And the payment identified by payment_210 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_210_1                   | payment_210                 |
      | inv_210_2                   |                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.OpenAmt | OPT.IsPartiallyPaid |
      | inv_210_1               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       |             | false               |
      | inv_210_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | false      | 2.9         | true                |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_210_1                   | payment_210                 | -5.95      | 0                |
      | inv_210_2                   | payment_210                 | -3.05      | -2.9             |

    And apply DISCOUNT to invoices
      | C_Invoice_ID.Identifier | Amount |
      | inv_210_2               | 2.9    |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | inv_210_2               | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_210             | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.DiscountAmt |
      | inv_210_2                   | 0          | -2.9            |

  @Id:S0132_220
  @from:cucumber
  Scenario: allocate payment to purchase invoice that has a credit memo created

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_220 | product_220 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_220     | paymentAllocPLVNotSO              | product_220             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_220    | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_220   | inv_220                 | product_220             | 1           | PCE               |
    And the invoice identified by inv_220 is completed

    And create credit memo for C_Invoice
      | CreditMemo.Identifier | C_Invoice_ID.Identifier | CreditMemo.PriceEntered | CreditMemo.C_DocType_ID.Name |
      | credit_memo_220       | inv_220                 | 2.00                    | Gutschrift                   |
    And the invoice identified by credit_memo_220 is completed

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | credit_memo_220             | 2.38       | 0                |
      | inv_220                     | -2.38      | 0                |

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_220 | bpartner_1               | 5.95   | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
    And the payment identified by payment_220 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_220                     | payment_220                 |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid | OPT.IsPartiallyPaid |
      | credit_memo_220         | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
      | inv_220                 | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       | false               |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt |
      | payment_220             | false                    | 2.38        |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | inv_220                     | payment_220                 | -3.57      | 0                |

    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 0 EUR       | 2.38 EUR    | alloc1    |
      | V_Liability_Acct      | 2.38 EUR    | 0 EUR       | alloc1    |
    And Fact_Acct records balances for documents alloc1 are matching
      | AccountConceptualName | SourceBalance |
      | V_Liability_Acct      | 0 EUR         |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      | 3.57 EUR    | 0 EUR       | alloc2    |
      | B_PaymentSelect_Acct  | 0 EUR       | 3.57 EUR    | alloc2    |

  @Id:S0132_230
  @from:cucumber
  Scenario: allocate inbound payment to purchase invoice

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_230 | product_230 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_230     | paymentAllocPLVNotSO              | product_230             | 5.00     | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_230    | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_230   | inv_230                 | product_230             | 1           | PCE               |
    And the invoice identified by inv_230 is completed

    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_230 | bpartner_1               | 5.95   | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_230 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_230                     | payment_230                 |

    Then validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_230             | false                    |
    And there are no allocation lines for invoice
      | C_Invoice_ID.Identifier |
      | inv_230                 |

  @Id:S0132_240
  @from:cucumber
  Scenario: allocate one inbound payment and one outbound payment to each other

    And metasfresh contains C_Payment
      | Identifier    | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier |
      | payment_210_1 | bpartner_1               | 9.00   | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            |
      | payment_210_2 | bpartner_1               | 5.00   | EUR                 | Zahlungseingang   | true      | bp_bank_account1            |
    And the payment identified by payment_210_1 is completed
    And the payment identified by payment_210_2 is completed

    And allocate payments to invoices
      | OPT.C_Payment_ID.Identifier |
      | payment_210_1               |
      | payment_210_2               |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt |
      | payment_210_1           | false                    | 4.00        |
      | payment_210_2           | true                     |             |
    And validate C_AllocationLines
      | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt |
      | payment_210_1               | -5         | -4               |
      | payment_210_2               | 5          | 0                |

  @Id:S0132_250
  @from:cucumber
  Scenario: allocation DateAcct uses accounting dates, not document dates

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_250 | product_250 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_250     | paymentAllocPLV                   | product_250             | 100.00   | PCE               | Normal                        |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | OPT.DateAcct | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_250    | bpartner_1               | Ausgangsrechnung        | 2026-01-08   | 2025-12-31   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | il_250     | inv_250                 | product_250             | 1           | PCE               |
    And the invoice identified by inv_250 is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx   | OPT.DateAcct |
      | pay_250    | bpartner_1               | 100.00 | EUR                 | Zahlungseingang   | true      | bp_bank_account1            | 2025-12-31     | 2025-12-31   |
    And the payment identified by pay_250 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_250                     | pay_250                     |

    Then validate C_AllocationHdr for invoice and payment
      | C_Invoice_ID.Identifier | C_Payment_ID.Identifier | DateAcct   | DateTrx    |
      | inv_250                 | pay_250                 | 2025-12-31 | 2026-01-08 |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_CMA_100
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @F00700
  Scenario: reverse correction - credit memo reversed, allocation produces Fact_Acct
    Given metasfresh contains M_Products:
      | Identifier      |
      | product_cma_100 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID |
      | salesPLV               | product_cma_100 | 10.00    | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier        | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInv_cma_100  | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID      | M_Product_ID    | QtyInvoiced |
      | salesInv_cma_100  | product_cma_100 | 1 PCE       |
    And the invoice identified by salesInv_cma_100 is completed

    # Create credit memo against the invoice (auto-allocates CM against invoice)
    And create credit memo for C_Invoice
      | CreditMemo         | C_Invoice_ID     | CreditMemo.PriceEntered |
      | salesCM_cma_100    | salesInv_cma_100 | 10.00                   |
    And the invoice identified by salesCM_cma_100 is completed

    # At this point alloc_cm is the auto-created allocation of CM against invoice
    And validate C_AllocationLines
      | C_Invoice_ID     | Amount | C_AllocationHdr_ID |
      | salesCM_cma_100  | -11.90 | alloc_cm           |
      | salesInv_cma_100 | 11.90  | alloc_cm           |

    # Verify: the credit memo compensation allocation has Fact_Acct entries
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | C_Receivable_Acct     | 11.90 EUR   | 0 EUR       | alloc_cm  |
      | C_Receivable_Acct     | 0 EUR       | 11.90 EUR   | alloc_cm  |
    And Fact_Acct records balances for documents alloc_cm are matching
      | AccountConceptualName | SourceBalance |
      | C_Receivable_Acct     | 0 EUR         |

    # Now reverse the credit memo — this creates reversal allocations
    And the invoice identified by salesCM_cma_100 is reversed

    Then validate created invoices
      | C_Invoice_ID     | IsPaid |
      | salesInv_cma_100 | false  |
      | salesCM_cma_100  | true   |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_CMA_110
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @F00700
  Scenario: sales credit memo allocated against sales invoice - Fact_Acct entries (no payment)
    Given metasfresh contains M_Products:
      | Identifier      |
      | product_cma_110 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID |
      | salesPLV               | product_cma_110 | 100.00   | PCE      |
    # Create the sales invoice (ARI) — GrandTotal = 119.00 EUR (100 + 19% VAT)
    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInv_cma_110 | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID     | M_Product_ID    | QtyInvoiced |
      | salesInv_cma_110 | product_cma_110 | 1 PCE       |
    And the invoice identified by salesInv_cma_110 is completed

    # Create the sales credit memo (ARC) — GrandTotal = 59.50 EUR (50 + 19% VAT)
    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesCM_cma_110 | customer1     | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID    | M_Product_ID    | QtyInvoiced |
      | salesCM_cma_110 | product_cma_110 | 0.5 PCE     |
    And the invoice identified by salesCM_cma_110 is completed

    # Allocate credit memo against invoice (no payment)
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.CreditMemo.C_Invoice_ID.Identifier |
      | salesInv_cma_110        | salesCM_cma_110                        |

    Then validate created invoices
      | C_Invoice_ID.Identifier | IsPaid |
      | salesCM_cma_110         | true   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_AllocationHdr_ID.Identifier |
      | salesCM_cma_110             | alloc_cma_110                     |

    # Verify: allocation has Fact_Acct entries — DR for credit memo, CR for invoice
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID     |
      | C_Receivable_Acct     | 59.50 EUR   | 0 EUR       | alloc_cma_110 |
      | C_Receivable_Acct     | 0 EUR       | 59.50 EUR   | alloc_cma_110 |
    And Fact_Acct records balances for documents alloc_cma_110 are matching
      | AccountConceptualName | SourceBalance |
      | C_Receivable_Acct     | 0 EUR         |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0465_CMA_120
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @F00700
  Scenario: purchase credit memo allocated against purchase invoice - Fact_Acct entries (no payment)
    Given metasfresh contains M_Products:
      | Identifier      |
      | product_cma_120 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID |
      | purchasePLV            | product_cma_120 | 100.00   | PCE      |
    # Create the purchase invoice (API) — GrandTotal = 119.00 EUR (100 + 19% VAT)
    And metasfresh contains C_Invoice:
      | Identifier          | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInv_cma_120 | vendor1       | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID        | M_Product_ID    | QtyInvoiced |
      | purchaseInv_cma_120 | product_cma_120 | 1 PCE       |
    And the invoice identified by purchaseInv_cma_120 is completed

    # Create the purchase credit memo (APC) — GrandTotal = 59.50 EUR (50 + 19% VAT)
    And metasfresh contains C_Invoice:
      | Identifier         | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchaseCM_cma_120 | vendor1       | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID       | M_Product_ID    | QtyInvoiced |
      | purchaseCM_cma_120 | product_cma_120 | 0.5 PCE     |
    And the invoice identified by purchaseCM_cma_120 is completed

    # Allocate credit memo against invoice (no payment)
    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.CreditMemo.C_Invoice_ID.Identifier |
      | purchaseInv_cma_120     | purchaseCM_cma_120                     |

    Then validate created invoices
      | C_Invoice_ID.Identifier | IsPaid |
      | purchaseCM_cma_120      | true   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_AllocationHdr_ID.Identifier |
      | purchaseCM_cma_120          | alloc_cma_120                     |

    # Verify: allocation has Fact_Acct entries — CR for credit memo, DR for invoice
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID     |
      | V_Liability_Acct      | 0 EUR       | 59.50 EUR   | alloc_cma_120 |
      | V_Liability_Acct      | 59.50 EUR   | 0 EUR       | alloc_cma_120 |
    And Fact_Acct records balances for documents alloc_cma_120 are matching
      | AccountConceptualName | SourceBalance |
      | V_Liability_Acct      | 0 EUR         |