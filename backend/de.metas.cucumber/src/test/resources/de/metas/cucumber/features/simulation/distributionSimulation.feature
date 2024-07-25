@from:cucumber
@ghActions:run_on_executor7
Feature: create distribution simulation

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00

  @from:cucumber
  @Id:S0171.300
  Scenario: create distribution simulation
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | bpartner_1 | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name             | Value            | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit | WarehouseTransit | bpartner_1               | location_1                        | true            |
      | warehouse_2               | WarehouseSource  | WarehouseSource  | bpartner_1               | location_1                        | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value    | M_Warehouse_ID.Identifier |
      | locator_1               | Standard | warehouse_2               |
    And contains M_Shippers
      | Identifier |
      | shipper_1  |
    And metasfresh contains DD_NetworkDistribution
      | Identifier  |
      | ddNetwork_1 |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | ddNetworkLine_1               | ddNetwork_1               | warehouseStd   | warehouse_2          | shipper_1    |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | DD_NetworkDistribution_ID | M_Warehouse_ID |
      | ppln_1     | p_1          | true         | ddNetwork_1               | warehouseStd   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.M_Warehouse_ID.Identifier |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z | warehouseStd                  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 14         |
    And create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    And after not more than 99960s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | simulated |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2022-07-04T00:00:00Z | -14 | -14                    | true      |
      | c_2        | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 14  | 0                      | true      |
      | c_3        | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -14 | -14                    | true      |
      | c_4        | SUPPLY            |                           | p_1          | 2022-07-04T00:00:00Z | 14  | 0                      | true      |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty | Processed | IsSimulated | C_OrderLineSO_ID |
      | c1         | p_1          | warehouse_2         | warehouseStd     | 14  | N         | Y           | ol_1             |
    And delete C_OrderLine identified by ol_1, but keep its id into identifierIds table
    And no DD_Order_Candidates found for product p_1
