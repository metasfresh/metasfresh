@from:cucumber
@ghActions:run_on_executor2
Feature: accounting-override-feature

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  @from:cucumber
  @Id:S0250_100
  Scenario: we can override accounting info for an invoice with one line
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_04022023_1 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingLU           | huPackingLU |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 10  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_04022023_1 | pricing_system_value_04022023_1 | pricing_system_description_04022023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_04022023_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_04022023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_04022023_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_04022023_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_04022023_1      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_04022023_1      | inOut_210320222_1     |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoice_candidate_1               | ol_1                      | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | elementValue_1               | element_1               | 69100 |
      | elementValue_2               | element_1               | 90014 |
      | elementValue_3               | element_1               | 2000  |
      | elementValue_4               | element_1               | 2060  |
      | elementValue_5               | element_1               | 1105  |
      | elementValue_6               | element_1               | 90044 |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

#   Load conversion rates between CHF and EUR (from EUR to CHF) filtering them by both validFrom and validTo dates
#   When multiple rates are valid at the same time (i.e. matches a date), then they are ordered in a descending order based on validFrom and the rate with the most recent validFrom is used
    And load C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | C_Currency_ID.Identifier | C_Currency_ID_To.Identifier | ValidFrom  | ValidTo    |
      | currency_rate_1                 | eur                      | chf                         | 2015-05-21 | 2155-12-31 |
      | currency_rate_2                 | eur                      | chf                         | 2019-05-21 | 2056-12-31 |

    And validate C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | OPT.MultiplyRate | OPT.DivideRate |
      | currency_rate_1                 | 1.045236         | 0.95672173557  |
      | currency_rate_2                 | 1.13             | 0.884955752212 |

    And after not more than 30s, M_MatchInv are found:
      | M_MatchInv_ID.Identifier | M_Product_ID.Identifier | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | matchInv_1               | p_1                     | invoice_1               | invoiceLine1_1              |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_5 | 100 | 0   | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_2 | 19  | 0   | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_3              | elementValue_3 | 0   | 119 | eur                      | 1.13             | V_Liability_Acct          |

    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_4              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |
      | factAcct_5              | elementValue_5 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_6              | elementValue_6 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |

    And metasfresh contains C_Invoice_Acct:
      | C_Invoice_Acct_ID.Identifier | C_Invoice_ID.Identifier | C_AcctSchema_ID.Identifier | C_ElementValue_ID.Identifier |
      | invoiceAcct_1                | invoice_1               | acctSchema_1               | elementValue_1               |

    And fact account repost invoice document with identifier invoice_1:
      | IsEnforcePosting |
      | false            |

    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_1 | 100 | 0   | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_2 | 19  | 0   | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_3              | elementValue_3 | 0   | 119 | eur                      | 1.13             | V_Liability_Acct          |

    And fact account repost matchInvoice document with identifier matchInv_1:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_4              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |
      | factAcct_5              | elementValue_1 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_6              | elementValue_1 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |

    And update C_Invoice_Acct:
      | C_Invoice_Acct_ID.Identifier | OPT.IsActive |
      | invoiceAcct_1                | false        |

    And fact account repost invoice document with identifier invoice_1:
      | IsEnforcePosting |
      | false            |

    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_5 | 100 | 0   | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_2 | 19  | 0   | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_3              | elementValue_3 | 0   | 119 | eur                      | 1.13             | V_Liability_Acct          |

    And fact account repost matchInvoice document with identifier matchInv_1:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_4              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |
      | factAcct_5              | elementValue_5 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_6              | elementValue_6 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |

  @from:cucumber
  @Id:S0250_200
  Scenario: we can override accounting info for an invoice with two lines
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_07022023_1 |
      | p_2        | purchaseProduct_07022023_2 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingLU           | huPackingLU |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 10  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_07022023_1 | pricing_system_value_07022023_1 | pricing_system_description_07022023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_07022023_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_07022023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 8.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_07022023_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_07022023_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 10         | warehouseStd              |
      | receiptSchedule_07022023_2      | o_1                   | ol_2                      | endvendor_1              | l_1                               | p_2                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_1   | receiptSchedule_07022023_1      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
      | huLuTuConfig                          | processedTopHU_2   | receiptSchedule_07022023_2      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU_1   | receiptSchedule_07022023_1      | inOut_210320222_1     |
      | processedTopHU_2   | receiptSchedule_07022023_2      | inOut_210320222_2     |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoice_candidate_1               | ol_1                      | 10           |
      | invoice_candidate_2               | ol_2                      | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier       |
      | invoice_candidate_1,invoice_candidate_2 |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            |
      | invoiceLine1_2              | p_2                     | 10          | true      | 8                | 8               | 80             | 0            |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | elementValue_1               | element_1               | 69100 |
      | elementValue_2               | element_1               | 90014 |
      | elementValue_3               | element_1               | 2000  |
      | elementValue_4               | element_1               | 2060  |
      | elementValue_5               | element_1               | 1105  |
      | elementValue_6               | element_1               | 90044 |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

#   Load conversion rates between CHF and EUR (from EUR to CHF) filtering them by both validFrom and validTo dates
#   When multiple rates are valid at the same time (i.e. matches a date), then they are ordered in a descending order based on validFrom and the rate with the most recent validFrom is used
    And load C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | C_Currency_ID.Identifier | C_Currency_ID_To.Identifier | ValidFrom  | ValidTo    |
      | currency_rate_1                 | eur                      | chf                         | 2015-05-21 | 2155-12-31 |
      | currency_rate_2                 | eur                      | chf                         | 2019-05-21 | 2056-12-31 |

    And validate C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | OPT.MultiplyRate | OPT.DivideRate |
      | currency_rate_1                 | 1.045236         | 0.95672173557  |
      | currency_rate_2                 | 1.13             | 0.884955752212 |

    And after not more than 30s, M_MatchInv are found:
      | M_MatchInv_ID.Identifier | M_Product_ID.Identifier | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | matchInv_1               | p_1                     | invoice_1               | invoiceLine1_1              |
      | matchInv_2               | p_2                     | invoice_1               | invoiceLine1_2              |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_5 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_5 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          |

    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_5              | elementValue_6 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_6              | elementValue_5 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_7              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And after not more than 30s, the matchInvoice document with identifier matchInv_2 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_8              | elementValue_6 | 90.40 | 0  | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_9              | elementValue_5 | 0     | 80 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_10             | elementValue_4 | 0     | 0  | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And metasfresh contains C_Invoice_Acct:
      | C_Invoice_Acct_ID.Identifier | C_Invoice_ID.Identifier | C_AcctSchema_ID.Identifier | C_ElementValue_ID.Identifier |
      | invoiceAcct_1                | invoice_1               | acctSchema_1               | elementValue_1               |

    And fact account repost invoice document with identifier invoice_1:
      | IsEnforcePosting |
      | false            |

    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_1 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_1 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          |

    And fact account repost matchInvoice document with identifier matchInv_1:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_5              | elementValue_1 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_6              | elementValue_1 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_7              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And fact account repost matchInvoice document with identifier matchInv_2:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_2 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_8              | elementValue_1 | 90.40 | 0  | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_9              | elementValue_1 | 0     | 80 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_10             | elementValue_4 | 0     | 0  | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And update C_Invoice_Acct:
      | C_Invoice_Acct_ID.Identifier | OPT.IsActive |
      | invoiceAcct_1                | false        |

    And fact account repost invoice document with identifier invoice_1:
      | IsEnforcePosting |
      | false            |

    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_5 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_5 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          |

    And fact account repost matchInvoice document with identifier matchInv_1:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_5              | elementValue_6 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_6              | elementValue_5 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_7              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And fact account repost matchInvoice document with identifier matchInv_2:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_2 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_8              | elementValue_6 | 90.40 | 0  | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_9              | elementValue_5 | 0     | 80 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_10             | elementValue_4 | 0     | 0  | chf                      | 0                | NotInvoicedReceipts_Acct    |

  @from:cucumber
  @Id:S0250_300
  Scenario: we can override accounting info for only one line of an invoice with two lines
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_06022023_1 |
      | p_2        | purchaseProduct_06022023_2 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingLU           | huPackingLU |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 10  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_06022023_1 | pricing_system_value_06022023_1 | pricing_system_description_06022023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_06022023_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_06022023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 8.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_06022023_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_06022023_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 10         | warehouseStd              |
      | receiptSchedule_06022023_2      | o_1                   | ol_2                      | endvendor_1              | l_1                               | p_2                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_1   | receiptSchedule_06022023_1      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
      | huLuTuConfig                          | processedTopHU_2   | receiptSchedule_06022023_2      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU_1   | receiptSchedule_06022023_1      | inOut_210320222_1     |
      | processedTopHU_2   | receiptSchedule_06022023_2      | inOut_210320222_2     |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoice_candidate_1               | ol_1                      | 10           |
      | invoice_candidate_2               | ol_2                      | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier       |
      | invoice_candidate_1,invoice_candidate_2 |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            |
      | invoiceLine1_2              | p_2                     | 10          | true      | 8                | 8               | 80             | 0            |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | elementValue_1               | element_1               | 69100 |
      | elementValue_2               | element_1               | 90014 |
      | elementValue_3               | element_1               | 2000  |
      | elementValue_4               | element_1               | 2060  |
      | elementValue_5               | element_1               | 1105  |
      | elementValue_6               | element_1               | 90044 |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

#   Load conversion rates between CHF and EUR (from EUR to CHF) filtering them by both validFrom and validTo dates
#   When multiple rates are valid at the same time (i.e. matches a date), then they are ordered in a descending order based on validFrom and the rate with the most recent validFrom is used
    And load C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | C_Currency_ID.Identifier | C_Currency_ID_To.Identifier | ValidFrom  | ValidTo    |
      | currency_rate_1                 | eur                      | chf                         | 2015-05-21 | 2155-12-31 |
      | currency_rate_2                 | eur                      | chf                         | 2019-05-21 | 2056-12-31 |

    And validate C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | OPT.MultiplyRate | OPT.DivideRate |
      | currency_rate_1                 | 1.045236         | 0.95672173557  |
      | currency_rate_2                 | 1.13             | 0.884955752212 |

    And after not more than 30s, M_MatchInv are found:
      | M_MatchInv_ID.Identifier | M_Product_ID.Identifier | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | matchInv_1               | p_1                     | invoice_1               | invoiceLine1_1              |
      | matchInv_2               | p_2                     | invoice_1               | invoiceLine1_2              |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_5 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_5 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          |

    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_5              | elementValue_6 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_6              | elementValue_5 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_7              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And after not more than 30s, the matchInvoice document with identifier matchInv_2 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_8              | elementValue_6 | 90.40 | 0  | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_9              | elementValue_5 | 0     | 80 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_10             | elementValue_4 | 0     | 0  | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And metasfresh contains C_Invoice_Acct:
      | C_Invoice_Acct_ID.Identifier | C_Invoice_ID.Identifier | C_AcctSchema_ID.Identifier | C_ElementValue_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier |
      | invoiceAcct_1                | invoice_1               | acctSchema_1               | elementValue_1               | invoiceLine1_1                  |

    And fact account repost invoice document with identifier invoice_1:
      | IsEnforcePosting |
      | false            |

    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_1 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_5 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          |

    And fact account repost matchInvoice document with identifier matchInv_1:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_5              | elementValue_1 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_6              | elementValue_1 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_7              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And fact account repost matchInvoice document with identifier matchInv_2:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_2 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_8              | elementValue_6 | 90.40 | 0  | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_9              | elementValue_5 | 0     | 80 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_10             | elementValue_4 | 0     | 0  | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And update C_Invoice_Acct:
      | C_Invoice_Acct_ID.Identifier | OPT.IsActive |
      | invoiceAcct_1                | false        |

    And fact account repost invoice document with identifier invoice_1:
      | IsEnforcePosting |
      | false            |

    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName |
      | factAcct_1              | elementValue_5 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_2              | elementValue_5 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          |

    And fact account repost matchInvoice document with identifier matchInv_1:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR  | CR  | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_5              | elementValue_6 | 113 | 0   | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_6              | elementValue_5 | 0   | 100 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_7              | elementValue_4 | 0   | 0   | chf                      | 0                | NotInvoicedReceipts_Acct    |

    And fact account repost matchInvoice document with identifier matchInv_2:
      | IsEnforcePosting |
      | false            |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the matchInvoice document with identifier matchInv_2 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName   |
      | factAcct_8              | elementValue_6 | 90.40 | 0  | chf                      | 1                | P_InvoicePriceVariance_Acct |
      | factAcct_9              | elementValue_5 | 0     | 80 | eur                      | 1.13             | P_InventoryClearing_Acct    |
      | factAcct_10             | elementValue_4 | 0     | 0  | chf                      | 0                | NotInvoicedReceipts_Acct    |