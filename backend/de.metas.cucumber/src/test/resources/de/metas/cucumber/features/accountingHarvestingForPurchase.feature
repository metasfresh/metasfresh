@from:cucumber
@ghActions:run_on_executor2
Feature: accounting-harvesting-feature

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE


  @dev:runThisOne
  @from:cucumber
  @Id:S0308_100
  Scenario: we shall have harvesting calendar and year propagated from document
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
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_1   | receiptSchedule_06082023_1      | N               | 1     | N               | 1     | N               | 10    | huItemPurchaseProduct              | huPackingLU                  |
      | huLuTuConfig                          | processedTopHU_2   | receiptSchedule_06082023_2      | N               | 1     | N               | 1     | N               | 10    | huItemPurchaseProduct              | huPackingLU                  |
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
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      | harvesting_calendar                     | y2022                             |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            | harvesting_calendar                     | y2022                             |
      | invoiceLine1_2              | p_2                     | 10          | true      | 8                | 8               | 80             | 0            | harvesting_calendar                     | y2022                             |

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

    And metasfresh contains C_AcctSchema_Element:
      | C_AcctSchema_Element_ID.Identifier | Name                | ElementType | C_AcctSchema_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | cae_1                              | Harvesting Calendar | HC          | acctSchema_1               | harvesting_calendar                     |                                   |
      | cae_2                              | Harvesting Year     | HY          | acctSchema_1               |                                         | y2022                             |

#   C_AcctSchema.C_Currency_ID is CHF,
#   Therefore the Fact_Acct records with C_Currency_ID EUR have the currencyRate 1.13 (C_Conversion_Rate.MultiplyRate with identifier "currency_rate_2"),
#   And ofc, those Fact_Acct records with C_Currency_ID CHF have the currencyRate 1 (same currency)
#   The CurrencyRate is the multiplyRate to be used to convert EUR to CHF if necessary (e.g. 100 EUR * 1.13 = 113 CHF)
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR    | CR     | C_Currency_ID.Identifier | OPT.CurrencyRate | OPT.AccountConceptualName | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | factAcct_1              | elementValue_5 | 100   | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  | harvesting_calendar                     |    y2022                          |
      | factAcct_2              | elementValue_5 | 80    | 0      | eur                      | 1.13             | P_InventoryClearing_Acct  | harvesting_calendar                     |    y2022                          |
      | factAcct_3              | elementValue_2 | 34.20 | 0      | eur                      | 1.13             | T_Credit_Acct             | harvesting_calendar                     |    y2022                          |
      | factAcct_4              | elementValue_3 | 0     | 214.20 | eur                      | 1.13             | V_Liability_Acct          | harvesting_calendar                     |    y2022                          |