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
      | Identifier | Name                            |
      | p_1        | product_Distribution_@Date@     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_S0171_300 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | plv_1      | pl_1                      | plv_product | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartnerName_Dist_06-07_2022 | N            | Y              | ps_1                          |
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
    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name          | Value          | DocumentNo |
      | ddNetwork_1                          | DDNetworkName | DDNetworkValue | docNo1     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.simulated |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | true          |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 14  | 0                      | true          |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | true          |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 0                      | true          |
    And after not more than 60s, DD_OrderLine found for orderLine ol_1
      | Identifier |
      | ddol_1     |
    And delete C_OrderLine identified by ol_1, but keep its id into identifierIds table
    And no DD_OrderLine found for orderLine ol_1
