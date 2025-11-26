@from:cucumber
@ghActions:run_on_executor6
Feature: Reversal of material receipt

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2021-04-14T11:30:13Z
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_1       | plv_1                  | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | CH           | Y               | Y               |
 
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Check Material Dispo
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        |
      | po1        | N       | vendor        | 2021-04-16  | 1000012          | POO         | ps_1               | 2021-04-15T15:00:00 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po1_l1     | po1        | product      | 10         |
    And the order identified by po1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected       | Qty | ATP |
      | c_1        | SUPPLY            | PURCHASE                  | product      | 2021-04-15T15:00:00 | 10  | 10  |
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs1                             | po1                   | po1_l1                    | vendor                   | vendorLocation                    | product                 | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu1                | rs1                             | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu1                | rs1                             | receipt1              |
    # the receipt with qty=10 came before it was expected; c_1 now has Qty=0; c_2 has the value that was set in the timesource
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_1        | SUPPLY              | PURCHASE                  | product      | 2021-04-15T15:00:00  | 0   | 10  |
      | c_2        | UNEXPECTED_INCREASE | PURCHASE                  | product      | 2021-04-14T11:30:13Z | 10  | 10  |
    And the material receipt identified by receipt1 is reversed
    # i'm not sure it's right that c_1 did not get its Qty=10 back. So if you think i's wrong => maybe it is.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_1        | SUPPLY              | PURCHASE                  | product      | 2021-04-15T15:00:00  | 0   | 0   |
      | c_2        | UNEXPECTED_INCREASE | PURCHASE                  | product      | 2021-04-14T11:30:13Z | 10  | 10  |
      | c_3        | UNEXPECTED_DECREASE | PURCHASE                  | product      | 2021-04-14T11:30:13Z | 10  | 0   |
# cleanup
    And metasfresh has current date and time
    
























