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
      | credit_memo_220       | inv_220                 | 2.00                    | Gutschrift (Lieferant)       |
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
  Scenario: (Sales) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 payments to a sales invoice
  - allocate 1st payment to sales invoice for partial amount, paymentTerm discount is not applied
  - allocate 2nd payment to sales invoice for remaining amount, paymentTerm discount is applied

    And metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_27102022_1 | paymentAllocPricingSystem_27102022_1 | paymentAllocPricingSystem_27102022_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_27102022_1 | paymentAllocPricingSystem_27102022_1 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_27102022_1 | paymentAllocPriceList_27102022_1 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_27102022_1 | BPartnerTest_27102022_1 | Y              | paymentAllocPricingSystem_27102022_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_27102022_1 | bpartner_27102022_1      | Y                   | Y                   |

    And metasfresh contains C_BP_BankAccount
      | Identifier                 | C_BPartner_ID.Identifier | C_Currency.ISO_Code |
      | bp_bank_account_27102022_1 | bpartner_27102022_1      | EUR                 |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_27102022_1 | product_27102022_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_27102022_1 | paymentAllocPLV_27102022_1        | product_27102022_1      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_27102022_1 | bpartner_27102022_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_27102022_1 | inv_27102022_1          | product_27102022_1      | 10          | PCE               |
    And the invoice identified by inv_27102022_1 is completed

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx | OPT.DateAcct |
      | payment_27102022_1 | bpartner_27102022_1      | 1      | EUR                 | Zahlungseingang   | true      | bp_bank_account1            | 2022-05-11  | 2022-05-11   |
    And the payment identified by payment_27102022_1 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_27102022_1              | payment_27102022_1          |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_27102022_1          | bpartner_27102022_1      | bpartner_location_27102022_1      | 10 Tage 1 % | true      | CO        | false      |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_27102022_1      | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_27102022_1              | payment_27102022_1          | 1          | 22.8             | 0               | 0               |

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx | OPT.DateAcct |
      | payment_27102022_2 | bpartner_27102022_1      | 22.56  | EUR                 | Zahlungseingang   | true      | bp_bank_account1            | 2022-05-11  | 2022-05-11   |
    And the payment identified by payment_27102022_2 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_27102022_1              | payment_27102022_2          |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_27102022_1          | bpartner_27102022_1      | bpartner_location_27102022_1      | 10 Tage 1 % | true      | CO        | true       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_27102022_2      | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_27102022_1              | payment_27102022_2          | 22.56      | 0                | 0               | 0.24            |

  @Id:S0132_260
  @from:cucumber
  Scenario: (Purchase) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 payments to a purchase invoice
  - allocate 1st payment to purchase invoice for partial amount, paymentTerm discount is not applied
  - allocate 2nd payment to purchase invoice for remaining amount, paymentTerm discount is applied

    And metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_27102022_2 | paymentAllocPricingSystem_27102022_2 | paymentAllocPricingSystem_27102022_2 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_27102022_2 | paymentAllocPricingSystem_27102022_2 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_27102022_2 | paymentAllocPriceList_27102022_2 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_27102022_2 | BPartnerTest_27102022_2 | Y            | paymentAllocPricingSystem_27102022_2 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_27102022_2 | bpartner_27102022_2      | Y                   | Y                   |

    And metasfresh contains C_BP_BankAccount
      | Identifier                 | C_BPartner_ID.Identifier | C_Currency.ISO_Code |
      | bp_bank_account_27102022_2 | bpartner_27102022_2      | EUR                 |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_27102022_2 | product_27102022_2 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_27102022_2 | paymentAllocPLV_27102022_2        | product_27102022_2      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_27102022_2 | bpartner_27102022_2      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_27102022_2 | inv_27102022_2          | product_27102022_2      | 10          | PCE               |
    And the invoice identified by inv_27102022_2 is completed

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx | OPT.DateAcct |
      | payment_27102022_3 | bpartner_27102022_2      | 1      | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            | 2022-05-11  | 2022-05-11   |
    And the payment identified by payment_27102022_3 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_27102022_2              | payment_27102022_3          |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_27102022_2          | bpartner_27102022_2      | bpartner_location_27102022_2      | 10 Tage 1 % | true      | CO        | false      |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_27102022_3      | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_27102022_2              | payment_27102022_3          | -1         | -22.8            | 0               | 0               |

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx | OPT.DateAcct |
      | payment_27102022_4 | bpartner_27102022_2      | 22.56  | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            | 2022-05-11  | 2022-05-11   |
    And the payment identified by payment_27102022_4 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_27102022_2              | payment_27102022_4          |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_27102022_2          | bpartner_27102022_2      | bpartner_location_27102022_2      | 10 Tage 1 % | true      | CO        | true       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_27102022_4      | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_27102022_2              | payment_27102022_4          | -22.56     | 0                | 0               | -0.24           |

  @Id:S0132_270
  @from:cucumber
  Scenario: (Sales) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 payments to a sales invoice (allocation happens only once)
  - allocate 1st payment to sales invoice for partial amount, paymentTerm discount is not applied
  - allocate 2nd payment to sales invoice for remaining amount, paymentTerm discount is applied

    And metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_28102022_1 | paymentAllocPricingSystem_28102022_1 | paymentAllocPricingSystem_28102022_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_28102022_1 | paymentAllocPricingSystem_28102022_1 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_28102022_1 | paymentAllocPriceList_28102022_1 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_28102022_1 | BPartnerTest_28102022_1 | Y              | paymentAllocPricingSystem_28102022_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_28102022_1 | bpartner_28102022_1      | Y                   | Y                   |

    And metasfresh contains C_BP_BankAccount
      | Identifier                 | C_BPartner_ID.Identifier | C_Currency.ISO_Code |
      | bp_bank_account_28102022_1 | bpartner_28102022_1      | EUR                 |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_28102022_1 | product_28102022_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_28102022_1 | paymentAllocPLV_28102022_1        | product_28102022_1      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_28102022_1 | bpartner_28102022_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_28102022_1 | inv_28102022_1          | product_28102022_1      | 10          | PCE               |
    And the invoice identified by inv_28102022_1 is completed

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx | OPT.DateAcct |
      | payment_28102022_1 | bpartner_28102022_1      | 1      | EUR                 | Zahlungseingang   | true      | bp_bank_account1            | 2022-05-11  | 2022-05-11   |
    And the payment identified by payment_28102022_1 is completed

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.DateTrx | OPT.DateAcct |
      | payment_28102022_2 | bpartner_28102022_1      | 22.56  | EUR                 | Zahlungseingang   | true      | bp_bank_account1            | 2022-05-11  | 2022-05-11   |
    And the payment identified by payment_28102022_2 is completed

    And allocate payments to invoices
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier |
      | inv_28102022_1              | payment_28102022_1          |
      |                             | payment_28102022_2          |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_28102022_1          | bpartner_28102022_1      | bpartner_location_28102022_1      | 10 Tage 1 % | true      | CO        | true       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_28102022_1      | true                     |
      | payment_28102022_2      | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_28102022_1              | payment_28102022_1          | 1          | 22.8             | 0               | 0               |
      | inv_28102022_1              | payment_28102022_2          | 22.56      | 0                | 0               | 0.24            |

  @Id:S0132_280
  @from:cucumber
  Scenario: (Sales) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 credit memos to a sales invoice
  - allocate 1st credit memo to sales invoice for partial amount, invoice's paymentTerm discount is not applied, credit memo's paymentTerm discount is applied
  - allocate 2nd credit memo to sales invoice for remaining amount, paymentTerm discount is applied for both invoice, but not for credit memo (because it is not fully allocated)

    And metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_31102022_1 | paymentAllocPricingSystem_31102022_1 | paymentAllocPricingSystem_31102022_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_31102022_1 | paymentAllocPricingSystem_31102022_1 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_31102022_1 | paymentAllocPriceList_31102022_1 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_31102022_1 | BPartnerTest_31102022_1 | Y              | paymentAllocPricingSystem_31102022_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_31102022_1 | bpartner_31102022_1      | Y                   | Y                   |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_31102022_1 | product_31102022_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_31102022_1 | paymentAllocPLV_31102022_1        | product_31102022_1      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_31102022_1 | bpartner_31102022_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_31102022_1 | inv_31102022_1          | product_31102022_1      | 10          | PCE               |
    And the invoice identified by inv_31102022_1 is completed

    And metasfresh contains C_Invoice:
      | Identifier             | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | credit_memo_31102022_1 | bpartner_31102022_1      | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier                  | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | credit_memo_line_31102022_1 | credit_memo_31102022_1  | product_31102022_1      | 1           | PCE               |
    And the invoice identified by credit_memo_31102022_1 is completed

    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.CreditMemo.C_Invoice_ID.Identifier |
      | inv_31102022_1          | credit_memo_31102022_1                 |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_31102022_1          | bpartner_31102022_1      | bpartner_location_31102022_1      | 10 Tage 1 % | true      | CO        | false      |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | credit_memo_31102022_1  | bpartner_31102022_1      | bpartner_location_31102022_1      | 10 Tage 1 % | true      | CO        | true       |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_31102022_1              | 2.4        | 21.4             | 0               | 0               |
      | credit_memo_31102022_1      | -2.4       | 0                | 0               | 0.02            |

    And metasfresh contains C_Invoice:
      | Identifier             | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | credit_memo_31102022_2 | bpartner_31102022_1      | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier                  | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | credit_memo_line_31102022_2 | credit_memo_31102022_2  | product_31102022_1      | 9           | PCE               |
    And the invoice identified by credit_memo_31102022_2 is completed

    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.CreditMemo.C_Invoice_ID.Identifier |
      | inv_31102022_1          | credit_memo_31102022_2                 |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_31102022_1          | bpartner_31102022_1      | bpartner_location_31102022_1      | 10 Tage 1 % | true      | CO        | true       |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | credit_memo_31102022_2  | bpartner_31102022_1      | bpartner_location_31102022_1      | 10 Tage 1 % | true      | CO        | false      |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | credit_memo_31102022_2      | -21.16     | -0.47            | 0               | 0               |
    And validate C_AllocationLines for invoice inv_31102022_1
      | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | 2.4        | 21.4             | 0               | 0               |
      | 21.16      | 0                | 0               | 0.24            |

  @Id:S0132_290
  @from:cucumber
  Scenario: (Purchase) check the paymentTerm discount is applied only once per invoice (i.e. when the invoice is fully paid) allocate 2 credit memos to a purchase invoice
  - allocate 1st credit memo to purchase invoice for partial amount, invoice's paymentTerm discount is not applied, credit memo's paymentTerm discount is applied
  - allocate 2nd credit memo to purchase invoice for remaining amount, paymentTerm discount is applied for invoice, but not for credit memo (because it is not fully allocated)

    And metasfresh contains M_PricingSystems
      | Identifier                            | Name                                  | Value                                 |
      | paymentAllocPricingSystem_31102022_10 | paymentAllocPricingSystem_31102022_10 | paymentAllocPricingSystem_31102022_10 |

    And metasfresh contains M_PriceLists
      | Identifier                        | M_PricingSystem_ID.Identifier         | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_31102022_10 | paymentAllocPricingSystem_31102022_10 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                  | M_PriceList_ID.Identifier         | ValidFrom  |
      | paymentAllocPLV_31102022_10 | paymentAllocPriceList_31102022_10 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier           | Name                     | OPT.IsVendor | M_PricingSystem_ID.Identifier         | OPT.C_PaymentTerm_ID |
      | bpartner_31102022_10 | BPartnerTest_31102022_10 | Y            | paymentAllocPricingSystem_31102022_10 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_31102022_10 | bpartner_31102022_10     | Y                   | Y                   |

    And metasfresh contains M_Products:
      | Identifier          | Name                |
      | product_31102022_10 | product_31102022_10 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_31102022_10 | paymentAllocPLV_31102022_10       | product_31102022_10     | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_31102022_10 | bpartner_31102022_10     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_31102022_10 | inv_31102022_10         | product_31102022_10     | 10          | PCE               |
    And the invoice identified by inv_31102022_10 is completed

    And metasfresh contains C_Invoice:
      | Identifier              | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | credit_memo_31102022_10 | bpartner_31102022_10     | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier                   | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | credit_memo_line_31102022_10 | credit_memo_31102022_10 | product_31102022_10     | 1           | PCE               |
    And the invoice identified by credit_memo_31102022_10 is completed

    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.CreditMemo.C_Invoice_ID.Identifier |
      | inv_31102022_10         | credit_memo_31102022_10                |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_31102022_10         | bpartner_31102022_10     | bpartner_location_31102022_10     | 10 Tage 1 % | true      | CO        | false      |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | credit_memo_31102022_10 | bpartner_31102022_10     | bpartner_location_31102022_10     | 10 Tage 1 % | true      | CO        | true       |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_31102022_10             | -2.4       | -21.4            | 0               | 0               |
      | credit_memo_31102022_10     | 2.4        | 0                | 0               | -0.02           |

    And metasfresh contains C_Invoice:
      | Identifier              | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | credit_memo_31102022_11 | bpartner_31102022_10     | Gutschrift (Lieferant)  | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier                   | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | credit_memo_line_31102022_11 | credit_memo_31102022_11 | product_31102022_10     | 9           | PCE               |
    And the invoice identified by credit_memo_31102022_11 is completed

    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.CreditMemo.C_Invoice_ID.Identifier |
      | inv_31102022_10         | credit_memo_31102022_11                |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_31102022_10         | bpartner_31102022_10     | bpartner_location_31102022_10     | 10 Tage 1 % | true      | CO        | true       |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | credit_memo_31102022_11 | bpartner_31102022_10     | bpartner_location_31102022_10     | 10 Tage 1 % | true      | CO        | false      |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | credit_memo_31102022_11     | 21.16      | 0.47             | 0               | 0               |
    And validate C_AllocationLines for invoice inv_31102022_10
      | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | -2.4       | -21.4            | 0               | 0               |
      | -21.16     | 0                | 0               | -0.24           |

  @Id:S0132_300
  @from:cucumber
  Scenario: Two purchase invoices allocated to a sales invoice
  - allocate 1st purchase invoice to sales invoice for partial amount, sales invoice's paymentTerm discount is not applied, purchase invoice's paymentTerm discount is applied
  - allocate 2nd purchase invoice to sales invoice for remaining amount, paymentTerm discount is applied for sales invoice, but not for purchase invoice (because it is not fully allocated)

    And metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_01112022_1 | paymentAllocPricingSystem_01112022_1 | paymentAllocPricingSystem_01112022_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_01112022_1 | paymentAllocPricingSystem_01112022_1 | DE                        | EUR                 | PriceListName_01112022_1 | false | false         | 2              |
      | paymentAllocPriceList_01112022_2 | paymentAllocPricingSystem_01112022_1 | DE                        | EUR                 | PriceListName_01112022_2 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_01112022_1 | paymentAllocPriceList_01112022_1 | 2022-05-01 |
      | paymentAllocPLV_01112022_2 | paymentAllocPriceList_01112022_2 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_01112022_1 | BPartnerTest_01112022_1 | Y            | Y              | paymentAllocPricingSystem_01112022_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_01112022_1 | bpartner_01112022_1      | Y                   | Y                   |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_01112022_1 | product_01112022_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_01112022_1 | paymentAllocPLV_01112022_1        | product_01112022_1      | 2.00     | PCE               | Normal                        |
      | pp_01112022_2 | paymentAllocPLV_01112022_2        | product_01112022_1      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_01112022_1 | bpartner_01112022_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_01112022_1 | inv_01112022_1          | product_01112022_1      | 10          | PCE               |
    And the invoice identified by inv_01112022_1 is completed
      
    #    GrandTotal = 2.38, Discount = 0.02
    And metasfresh contains C_Invoice:
      | Identifier                  | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchase_invoice_01112022_1 | bpartner_01112022_1      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier                       | C_Invoice_ID.Identifier     | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | purchase_invoice_line_01112022_1 | purchase_invoice_01112022_1 | product_01112022_1      | 1           | PCE               |
    And the invoice identified by purchase_invoice_01112022_1 is completed

    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.Purchase.C_Invoice_ID.Identifier |
      | inv_01112022_1          | purchase_invoice_01112022_1          |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_01112022_1          | bpartner_01112022_1      | bpartner_location_01112022_1      | 10 Tage 1 % | true      | CO        | false      |
    Then validate created invoices
      | C_Invoice_ID.Identifier     | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | purchase_invoice_01112022_1 | bpartner_01112022_1      | bpartner_location_01112022_1      | 10 Tage 1 % | true      | CO        | true       |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_01112022_1              | 2.36       | 21.44            | 0               | 0               |
      | purchase_invoice_01112022_1 | -2.36      | 0                | 0               | -0.02           |

    #    GrandTotal = 21.42, Discount = 0.21
    And metasfresh contains C_Invoice:
      | Identifier                  | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | purchase_invoice_01112022_2 | bpartner_01112022_1      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier                       | C_Invoice_ID.Identifier     | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | purchase_invoice_line_01112022_1 | purchase_invoice_01112022_2 | product_01112022_1      | 9           | PCE               |
    And the invoice identified by purchase_invoice_01112022_2 is completed

    And allocate invoices (credit memo/purchase) to invoices
      | C_Invoice_ID.Identifier | OPT.Purchase.C_Invoice_ID.Identifier |
      | inv_01112022_1          | purchase_invoice_01112022_2          |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_01112022_1          | bpartner_01112022_1      | bpartner_location_01112022_1      | 10 Tage 1 % | true      | CO        | true       |
    Then validate created invoices
      | C_Invoice_ID.Identifier     | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | purchase_invoice_01112022_2 | bpartner_01112022_1      | bpartner_location_01112022_1      | 10 Tage 1 % | true      | CO        | false      |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | purchase_invoice_01112022_2 | -21.2      | -0.01            | 0               | 0               |
    And validate C_AllocationLines for invoice inv_01112022_1
      | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | 2.36       | 21.44            | 0               | 0               |
      | 21.2       | 0                | 0               | 0.24            |

  @Id:S0132_310
  @from:cucumber
  Scenario: allocate payment to purchase invoice with overpayment and negative discount

    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | product_10012025_1 | product_10012025_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_10012025_1 | paymentAllocPLVNotSO              | product_10012025_1      | 1        | PCE               | Normal                        |

    # GrandTotal = 20.23
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_10012025_1 | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_10012025_1 | inv_10012025_1          | product_10012025_1      | 17          | PCE               |
    And the invoice identified by inv_10012025_1 is completed

    And metasfresh contains C_Payment
      | Identifier         | C_BPartner_ID.Identifier | PayAmt | OPT.DiscountAmt | C_Currency.ISO_Code | C_DocType_ID.Name | IsReceipt | C_BP_BankAccount.Identifier | OPT.C_Invoice_ID.Identifier |
      | payment_10012025_1 | bpartner_1               | 20.25  | -0.02           | EUR                 | Zahlungsausgang   | false     | bp_bank_account1            | inv_10012025_1              |
    And the payment identified by payment_10012025_1 is completed

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_10012025_1          | bpartner_1               | bpartner_location_1               | 30 Tage netto | true      | CO        | true       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated |
      | payment_10012025_1      | true                     |
    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.DiscountAmt |
      | inv_10012025_1              | payment_10012025_1          | -20.25     | 0.02           |

