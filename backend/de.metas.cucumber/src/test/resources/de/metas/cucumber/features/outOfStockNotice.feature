@ghActions:run_on_executor6
Feature: warehouse out of stock notice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  Scenario: warehouse out of stock notice api test
    Given metasfresh contains M_Products:
      | Identifier | Name            | Value           |
      | p_3        | salesProduct_46 | salesProduct_46 |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | ol_2       | i_2                       | p_3                     | PCE          | 10       | 0       |
    And the inventory identified by i_2 is completed
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                   | OPT.Description              | OPT.IsActive |
      | ps_1       | pricing_system_name_8 | pricing_system_value_8 | pricing_system_description_8 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_4 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_3                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_3 | Endcustomer_3 | N            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_Warehouse_ID.Identifier |
      | o_3        | true    | endcustomer_3            | 2021-04-17  | 540008                        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_3       | o_3                   | p_3                     | 10         |
    And the order identified by o_3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute | OPT.Warehouse_ID |
      | s_sched_1  | ol_3                      | N             | 540008           |
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/warehouses/540008/outOfStockNotice' and fulfills with '200' status code
  """
  {
    "closePendingShipmentSchedules": true,
    "createInventory": true,
    "orgCode": "001",
    "productIdentifier": "val-salesProduct_46"
  }
  """
    Then the shipment-schedule is closed
      | M_ShipmentSchedule_ID.Identifier |
      | s_sched_1                        |
    And there is a new completed inventory for the issued out of stock notice
      | M_Warehouse_ID | QtyCount | QtyBook |
      | 540008         | 0        | 10      |
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE