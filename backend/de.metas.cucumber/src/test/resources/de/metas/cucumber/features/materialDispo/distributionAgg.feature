@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor3
Feature: create distribution order based on aggregation sysconfig
## F5100: Material Disposition

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | bpartner_1 | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID |
      | location_1 | bPLocation_12 | bpartner_1    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransit      | bpartner_1    | location_1             | true        |
      | sourceWH       | bpartner_1    | location_1             | false       |
      | targetWH       | bpartner_1    | location_1             | false       |
    And metasfresh contains M_Locator:
      | Identifier       | M_Warehouse_ID | Value           |
      | sourceWHLocator  | sourceWH       | sourceWH_12345  |
      | targetWHLocator  | targetWH       | targetWH_12345  |
      | inTransitLocator | inTransit      | inTransit_12345 |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | ddNetwork_1               |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | ddNetwork_1               | targetWH       | sourceWH             | shipper      |
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID | IsCreatePlan | DD_NetworkDistribution_ID | M_Warehouse_ID |
      | productPlanning | p_1          | false        | ddNetwork_1               | targetWH       |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i1         | sourceWH       | 2021-04-10   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | UOM.X12DE355 | QtyCount | QtyBook |
      | il_2       | i1             | p_1          | PCE          | 200      | 0       |
    And the inventory identified by i1 is completed
    


# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
  Scenario: Aggregate by both orderId and orderLineId
    When set sys config boolean value true for sys config DDOrderAggregation.header.bySalesOrderId
    And set sys config boolean value true for sys config DDOrderAggregation.line.bySalesOrderLineId
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO1        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
      | SO2        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | SO1        | p_1          | 2          |
      | ol_2       | SO1        | p_1          | 3          |
      | ol_3       | SO2        | p_1          | 4          |
      | ol_4       | SO2        | p_1          | 5          |
    And the order identified by SO1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And the order identified by SO2 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 30s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty   | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 2 PCE | N         |
      | c2         | p_1          | sourceWH            | targetWH         | 3 PCE | N         |
      | c3         | p_1          | sourceWH            | targetWH         | 4 PCE | N         |
      | c4         | p_1          | sourceWH            | targetWH         | 5 PCE | N         |

    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c1                    |
      | c2                    |
      | c3                    |
      | c4                    |

    Then metasfresh contains DD_Orders:
      | Identifier | C_BPartner_ID | M_Warehouse_ID.From | M_Warehouse_ID.To | M_Warehouse_ID.Transit | C_DocType_ID.Name    |
      | ddo1       | bpartner_1    | sourceWH            | targetWH          | inTransit              | Distributionsauftrag |
      | ddo2       | bpartner_1    | sourceWH            | targetWH          | inTransit              | Distributionsauftrag |
    And metasfresh contains DD_OrderLines:
      | Identifier | M_Product_ID | DD_Order_ID | QtyEntered | M_Locator_ID    | M_LocatorTo_ID  |
      | ddol1      | p_1          | ddo1        | 2          | sourceWHLocator | targetWHLocator |
      | ddol2      | p_1          | ddo1        | 3          | sourceWHLocator | targetWHLocator |
      | ddol3      | p_1          | ddo2        | 4          | sourceWHLocator | targetWHLocator |
      | ddol4      | p_1          | ddo2        | 5          | sourceWHLocator | targetWHLocator |


# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
  Scenario: Aggregate by orderId
    When set sys config boolean value true for sys config DDOrderAggregation.header.bySalesOrderId
    And set sys config boolean value false for sys config DDOrderAggregation.line.bySalesOrderLineId
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO1        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
      | SO2        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | SO1        | p_1          | 2          |
      | ol_2       | SO1        | p_1          | 3          |
      | ol_3       | SO2        | p_1          | 4          |
      | ol_4       | SO2        | p_1          | 5          |
    And the order identified by SO1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And the order identified by SO2 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 30s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty   | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 2 PCE | N         |
      | c2         | p_1          | sourceWH            | targetWH         | 3 PCE | N         |
      | c3         | p_1          | sourceWH            | targetWH         | 4 PCE | N         |
      | c4         | p_1          | sourceWH            | targetWH         | 5 PCE | N         |

    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c1                    |
      | c2                    |
      | c3                    |
      | c4                    |

    Then metasfresh contains DD_Orders:
      | Identifier | C_BPartner_ID | M_Warehouse_ID.From | M_Warehouse_ID.To | M_Warehouse_ID.Transit | C_DocType_ID.Name    |
      | ddo1       | bpartner_1    | sourceWH            | targetWH          | inTransit              | Distributionsauftrag |
      | ddo2       | bpartner_1    | sourceWH            | targetWH          | inTransit              | Distributionsauftrag |
    And metasfresh contains DD_OrderLines:
      | Identifier | M_Product_ID | DD_Order_ID | QtyEntered | M_Locator_ID    | M_LocatorTo_ID  |
      | ddol1      | p_1          | ddo1        | 5          | sourceWHLocator | targetWHLocator |
      | ddol3      | p_1          | ddo2        | 9          | sourceWHLocator | targetWHLocator |



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
  Scenario: Aggregate by orderLineId
    When set sys config boolean value false for sys config DDOrderAggregation.header.bySalesOrderId
    And set sys config boolean value true for sys config DDOrderAggregation.line.bySalesOrderLineId
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO1        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
      | SO2        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | SO1        | p_1          | 2          |
      | ol_2       | SO1        | p_1          | 3          |
      | ol_3       | SO2        | p_1          | 4          |
      | ol_4       | SO2        | p_1          | 5          |
    And the order identified by SO1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And the order identified by SO2 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 30s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty   | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 2 PCE | N         |
      | c2         | p_1          | sourceWH            | targetWH         | 3 PCE | N         |
      | c3         | p_1          | sourceWH            | targetWH         | 4 PCE | N         |
      | c4         | p_1          | sourceWH            | targetWH         | 5 PCE | N         |

    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c1                    |
      | c2                    |
      | c3                    |
      | c4                    |

    Then metasfresh contains DD_Orders:
      | Identifier | C_BPartner_ID | M_Warehouse_ID.From | M_Warehouse_ID.To | M_Warehouse_ID.Transit | C_DocType_ID.Name    |
      | ddo1       | bpartner_1    | sourceWH            | targetWH          | inTransit              | Distributionsauftrag |
    And metasfresh contains DD_OrderLines:
      | Identifier | M_Product_ID | DD_Order_ID | QtyEntered | M_Locator_ID    | M_LocatorTo_ID  |
      | ddol1      | p_1          | ddo1        | 2          | sourceWHLocator | targetWHLocator |
      | ddol2      | p_1          | ddo1        | 3          | sourceWHLocator | targetWHLocator |
      | ddol3      | p_1          | ddo1        | 4          | sourceWHLocator | targetWHLocator |
      | ddol4      | p_1          | ddo1        | 5          | sourceWHLocator | targetWHLocator |

# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
  Scenario: No aggregation by SalesOrderId or SalesOrderLineId
    When set sys config boolean value false for sys config DDOrderAggregation.header.bySalesOrderId
    And set sys config boolean value false for sys config DDOrderAggregation.line.bySalesOrderLineId
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO1        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
      | SO2        | true    | bpartner_1    | 2022-07-20  | 2022-07-17T00:00:00Z | targetWH       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | SO1        | p_1          | 2          |
      | ol_2       | SO1        | p_1          | 3          |
      | ol_3       | SO2        | p_1          | 4          |
      | ol_4       | SO2        | p_1          | 5          |
    And the order identified by SO1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And the order identified by SO2 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 30s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty   | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 2 PCE | N         |
      | c2         | p_1          | sourceWH            | targetWH         | 3 PCE | N         |
      | c3         | p_1          | sourceWH            | targetWH         | 4 PCE | N         |
      | c4         | p_1          | sourceWH            | targetWH         | 5 PCE | N         |

    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c1                    |
      | c2                    |
      | c3                    |
      | c4                    |

    Then metasfresh contains DD_Orders:
      | Identifier | C_BPartner_ID | M_Warehouse_ID.From | M_Warehouse_ID.To | M_Warehouse_ID.Transit | C_DocType_ID.Name    |
      | ddo1       | bpartner_1    | sourceWH            | targetWH          | inTransit              | Distributionsauftrag |
    And metasfresh contains DD_OrderLines:
      | Identifier | M_Product_ID | DD_Order_ID | QtyEntered | M_Locator_ID    | M_LocatorTo_ID  |
      | ddol1      | p_1          | ddo1        | 14         | sourceWHLocator | targetWHLocator |


