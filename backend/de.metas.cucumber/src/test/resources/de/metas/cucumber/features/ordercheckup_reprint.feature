@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8008_Order_Checkup_Report
@ghActions:run_on_executor2
Feature: Bestellkontrolle reprint after reactivate
## F8008: Order Checkup Report

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-12T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | PP_Plant_ID |
      | warehouse      | plant       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | M_Warehouse_ID | S_Resource_ID |
      | product      | warehouse      | plant         |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | priceListVersion       | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | bpartner   | Y          | pricingSystem      |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC1
  Scenario: First completion always generates and prints a Bestellkontrolle
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |

    When the order identified by order is completed

    Then C_Order_MFGWarehouse_Report is located:
      | Identifier   | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | warehouseRpt | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | plantRpt   | order      | PL           | plant       | true     |
    And C_Order_MFGWarehouse_Report doc-outbound enqueue status is:
      | C_Order_MFGWarehouse_Report_ID | IsEnqueued |
      | warehouseRpt                   | true       |
      | plantRpt                       | true       |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC2
  Scenario: Reprint not set - splitting a line on reactivate keeps the previous Bestellkontrolle without reprinting
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And C_Order_MFGWarehouse_Report is located:
      | Identifier   | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | warehouseRpt | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | plantRpt   | order      | PL           | plant       | true     |

    When the order identified by order is reactivated
    # Split the line into two, total quantity per product unchanged - the customer's own correction pattern
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine                 | 2              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineB | order      | product      | 3          |
    And the order identified by order is completed

    Then C_Order_MFGWarehouse_Report active and inactive counts are:
      | C_Order_ID | ActiveCount | InactiveCount |
      | order      | 2           | 0             |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | restoredWhRpt | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | restoredPlRpt | order      | PL           | plant       | true     |
    And C_Order_MFGWarehouse_Report doc-outbound enqueue status is:
      | C_Order_MFGWarehouse_Report_ID | IsEnqueued |
      | restoredWhRpt                  | false      |
      | restoredPlRpt                  | false      |
    # Known limitation (REQUIREMENTS §3): the restored record still points at the original order line only.
    And C_Order_MFGWarehouse_Report references order lines:
      | Identifier    | C_OrderLine_ID |
      | restoredWhRpt | orderLine      |
      | restoredPlRpt | orderLine      |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC3
  Scenario: Reprint set - splitting a line on reactivate rebuilds and reprints the Bestellkontrolle
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | Y                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | firstWhRpt | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | firstPlRpt | order      | PL           | plant       | true     |

    When the order identified by order is reactivated
    # Split the line into two, total quantity per product unchanged - same correction as TC2
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine                 | 2              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineB | order      | product      | 3          |
    And the order identified by order is completed

    Then C_Order_MFGWarehouse_Report active and inactive counts are:
      | C_Order_ID | ActiveCount | InactiveCount |
      | order      | 2           | 2             |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | newWhRpt   | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | newPlRpt   | order      | PL           | plant       | true     |
    And C_Order_MFGWarehouse_Report doc-outbound enqueue status is:
      | C_Order_MFGWarehouse_Report_ID | IsEnqueued |
      | newWhRpt                       | true       |
      | newPlRpt                       | true       |
    And C_Order_MFGWarehouse_Report references order lines:
      | Identifier | C_OrderLine_ID |
      | newWhRpt   | orderLine      |
      | newWhRpt   | orderLineB     |
      | newPlRpt   | orderLine      |
      | newPlRpt   | orderLineB     |


# ####################################################################################################################
# ####################################################################################################################
  # Generation-mechanics scenario, not one of the REQUIREMENTS TC1-TC10 table entries -- next free TC number.
  @Id:S30709_TC11
  Scenario: Two successive completions stamp consecutive generation numbers on every report
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |

    When the order identified by order is completed

    Then C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | OrderCheckupGeneration |
      | whGen1     | order      | WH           | warehouse      | plant       | true     | 1                      |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive | OrderCheckupGeneration |
      | plGen1     | order      | PL           | plant       | true     | 1                      |

    When the order identified by order is reactivated
    And the order identified by order is completed

    Then C_Order_MFGWarehouse_Report active and inactive counts are:
      | C_Order_ID | ActiveCount | InactiveCount |
      | order      | 2           | 2             |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | OrderCheckupGeneration |
      | whGen2     | order      | WH           | warehouse      | plant       | true     | 2                      |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive | OrderCheckupGeneration |
      | plGen2     | order      | PL           | plant       | true     | 2                      |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | OrderCheckupGeneration |
      | whGen1Void | order      | WH           | warehouse      | plant       | false    | 1                      |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive | OrderCheckupGeneration |
      | plGen1Void | order      | PL           | plant       | false    | 1                      |
