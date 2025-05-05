@from:cucumber
@ghActions:run_on_executor6
Feature: Reversal of material receipt is correctly considered in Material Dispo

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2021-04-14T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario:
    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_16052022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                          | Value                          | OPT.Description                      | OPT.IsActive |
      | ps_1       | pricing_system_name16052022_1 | pricing_system_value16052022_1 | pricing_system_description16052022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name16052022_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                        | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV16052022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor16052022_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789111 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    And the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-15T21:00:00Z | 10  | 10                     |
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_16052022_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_16052022_1      | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_16052022_1      | inOut_16052022_1      |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | SUPPLY              | PURCHASE                      | p_1                     | 2021-04-15T21:00:00Z | 0   | 10                     |                                 |
      | c_2        | UNEXPECTED_INCREASE | PURCHASE                      | p_1                     |                      | 10  | 10                     | 2021-04-14T00:00:00             |
    When the material receipt identified by inOut_16052022_1 is reversed
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | SUPPLY              | PURCHASE                      | p_1                     | 2021-04-15T21:00:00Z | 0   | 0                      |                                 |
      | c_2        | UNEXPECTED_INCREASE | PURCHASE                      | p_1                     |                      | 10  | 10                     | 2021-04-14T00:00:00             |
      | c_3        | UNEXPECTED_DECREASE | PURCHASE                      | p_1                     |                      | 10  | -10                    | 2021-04-14T00:00:00             |

