@from:cucumber
@ghActions:run_on_executor2
Feature: accounting-purchase-harvesting-feature

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  @from:cucumber
  @Id:S0308_100
  Scenario: Harvesting calendar and year shall be propagated from purchase order to purchase invoice and then to fact_acct
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_06082023_1 |
      | p_2        | purchaseProduct_06082023_2 |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |
    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | T_Credit_Acct                | element_1               | 90014 |
      | V_Liability_Acct             | element_1               | 2000  |
      | P_InventoryClearing_Acct     | element_1               | 1105  |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

    And metasfresh contains C_AcctSchema_Element:
      | C_AcctSchema_Element_ID.Identifier | Name                | ElementType | C_AcctSchema_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | cae_1                              | Harvesting Calendar | HC          | acctSchema_1               | harvesting_calendar                     |                                   |
      | cae_2                              | Harvesting Year     | HY          | acctSchema_1               |                                         | y2022                             |

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
      | ps_1       | pricing_system_name_06082023_1 | pricing_system_value_06082023_1 | pricing_system_description_06082023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_06082023_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_06022023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 8.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_06082023_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              | harvesting_calendar                     | y2022                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_06082023_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 10         | warehouseStd              |
      | receiptSchedule_06082023_2      | o_1                   | ol_2                      | endvendor_1              | l_1                               | p_2                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_1   | receiptSchedule_06082023_1      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
      | huLuTuConfig                          | processedTopHU_2   | receiptSchedule_06082023_2      | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU_1   | receiptSchedule_06082023_1      | inOut_210320222_1     |
      | processedTopHU_2   | receiptSchedule_06082023_2      | inOut_210320222_2     |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoice_candidate_1               | ol_1                      | 10           | harvesting_calendar                     | y2022                             |
      | invoice_candidate_2               | ol_2                      | 10           | harvesting_calendar                     | y2022                             |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier       |
      | invoice_candidate_1,invoice_candidate_2 |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    #   The Invoice document shall contain the the calendar and the year from invoice candidates
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      | harvesting_calendar                     | y2022                             |
    #   The Invoice lines shall contain the the calendar and the year from invoice header
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            | harvesting_calendar                     | y2022                             |
      | invoiceLine1_2              | p_2                     | 10          | true      | 8                | 8               | 80             | 0            | harvesting_calendar                     | y2022                             |


#   The Fact_Acct records shall contain the the calendar and the year from invoice document
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                  | DR    | CR     | C_Currency_ID.Identifier | OPT.AccountConceptualName | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | factAcct_1              | P_InventoryClearing_Acct | 100   | 0      | eur                      | P_InventoryClearing_Acct  | harvesting_calendar                     | y2022                             |
      | factAcct_2              | P_InventoryClearing_Acct | 80    | 0      | eur                      | P_InventoryClearing_Acct  | harvesting_calendar                     | y2022                             |
      | factAcct_3              | T_Credit_Acct            | 34.20 | 0      | eur                      | T_Credit_Acct             | harvesting_calendar                     | y2022                             |
      | factAcct_4              | V_Liability_Acct         | 0     | 214.20 | eur                      | V_Liability_Acct          | harvesting_calendar                     | y2022                             |


  @from:cucumber
  @Id:S0308_200
  Scenario: Harvesting calendar and year shall be propagated from order to  receipt document and then to fact_acct

    Given metasfresh contains M_Products:
      | Identifier            | Name                  |
      | product_PO_05082023_1 | product_PO_05082023_1 |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_06082023_1 | pricing_system_value_06082023_1 | pricing_system_description_06082023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_06082023_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_06022023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | plv_1                             | product_PO_05082023_1   | 2.00     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPOProduct                    | huPiItemTU                 | product_PO_05082023_1   | 10  | 2022-02-01 |
    And metasfresh contains C_BPartners:
      | Identifier | Name          | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_po      | bp_05082023_1 | Y            | ps_1                          | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogMR_Location | 5823098505483 | bp_po                    | true                | true                |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_05072023_1 | product_PO_05082023_1       | harvesting_calendar      | y2022                | ps_1                              |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | ModularContractHandlerType |
      | modCntr_type_1             | poLine_05072023_1 | poLine_05072023_1 | Receipt                    |
      | modCntr_type_2             | mrLine_05072023_1 | mrLine_05072023_1 | Receipt                    |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_05072023_1 | product_PO_05082023_1   | Costs          | modCntr_settings_1             | modCntr_type_1             |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier          | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions | moduleLogConditions_po_05072023_1 | ModularContract | ps_1                              | Ex                       | modCntr_settings_1                 |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | coa_1                   | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | P_Asset_Acct                 | coa_1                   | 90000 |
      | NotInvoicedReceipts_Acct     | coa_1                   | 2060  |


    And metasfresh contains C_AcctSchema_Element:
      | C_AcctSchema_Element_ID.Identifier | Name                | ElementType | C_AcctSchema_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | cae_1                              | Harvesting Calendar | HC          | acctSchema_1               | harvesting_calendar                     |                                   |
      | cae_2                              | Harvesting Year     | HY          | acctSchema_1               |                                         | y2022                             |
    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType |
      | po_order   | false   | bp_po                    | 2022-03-03  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine_1 | po_order              | product_PO_05082023_1   | 1000       | moduleLogConditions                     |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions                 | product_PO_05082023_1   | po_order                       | po_orderLine_1                     |

    And after not more than 120s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_05072023_1      | po_order              | po_orderLine_1            | bp_po                    | bp_moduleLogMR_Location           | product_PO_05082023_1   | 1000       | warehouseStd              | moduleLogContract_1               |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_1                        | hu_1               | receiptSchedule_05072023_1      | N               | 1     | N               | 1     | N               | 1000        | huItemPOProduct                    | huPackingLU                  |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_1               | receiptSchedule_05072023_1      | material_receipt_1    |

    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | inoutLine_1               | material_receipt_1    | product_PO_05082023_1   | 1000        | true      | po_orderLine_1                | moduleLogContract_1               | harvesting_calendar                     | y2022                             |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

#   The Fact_Acct records shall contain the the calendar and the year from material receipt document
    And after not more than 30s, the inout document with identifier material_receipt_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | record_id          | Account                  | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | factAcct_10             | material_receipt_1 | P_Asset_Acct             | 0  | 0  | chf                      | P_Asset_Acct              | harvesting_calendar                     | y2022                             |
      | factAcct_20             | material_receipt_1 | NotInvoicedReceipts_Acct | 0  | 0  | chf                      | NotInvoicedReceipts_Acct  | harvesting_calendar                     | y2022                             |

