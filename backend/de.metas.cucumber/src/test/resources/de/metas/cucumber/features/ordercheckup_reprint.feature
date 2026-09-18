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

    # Reactivate deactivates the records but never touches Processed -- Processed staying true is direct
    # evidence these are the SAME records that were enqueued at the first completion, not a placeholder
    # state pending a rebuild.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | WH           | warehouse      | plant       | false    | true      |
      | PL           |                | plant       | false    | true      |

    # Split the line into two, total quantity per product unchanged - the customer's own correction pattern
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine                 | 2              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineB | order      | product      | 3          |
    And the order identified by order is completed

    # The very same 2 records came back active, still Processed -- no new record was built. The work
    # package count below is unchanged from the first completion, proving nothing was (re-)enqueued:
    # the print trigger keys on Processed changing, and it never did.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | WH           | warehouse      | plant       | true     | true      |
      | PL           |                | plant       | true     | true      |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | restoredWhRpt | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | restoredPlRpt | order      | PL           | plant       | true     |
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

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
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
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | Y                     |
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

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |


# ####################################################################################################################
# ####################################################################################################################
  # Restore mechanics scenario, not one of the REQUIREMENTS TC1-TC10 table entries -- next free TC number.
  @Id:S30709_TC12
  Scenario: Restoring the most recent generation reactivates only the higher-generation reports
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | Y                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed

    When the order identified by order is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine                 | 2              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineB | order      | product      | 3          |
    And the order identified by order is completed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 4                |

    When the Bestellkontrolle reports for the order identified by order are voided

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | false    |
      | PL           |                | plant       | 2                      | false    |

    When the most recent Bestellkontrolle generation for the order identified by order is restored

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
    # The restore only reactivated headers -- it did not build anything new, so the work package count from
    # before the void/restore cycle (4, asserted above) is unchanged: no work package was (re-)enqueued.
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 4                |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC4
  Scenario: Flag not set - repeated reactivate/complete cycles keep the whole original record set active
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed

    When the order identified by order is reactivated
    And the order identified by order is completed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |

    When the order identified by order is reactivated
    And the order identified by order is completed

    # Two reactivate/complete cycles, flag never set: the same original generation keeps coming back whole
    # (a warehouse AND a plant record, both active) -- a naive "exactly one active record" assertion would
    # wrongly pass a state missing one of the two.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC5
  Scenario: Flag not set - voiding the order still deactivates the Bestellkontrolle
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |

    When the order identified by order is voided

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC6
  Scenario: Flag not set - restoring a previous Bestellkontrolle enqueues no new print
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |

    When the order identified by order is reactivated
    And the order identified by order is completed

    # Same generation restored, not rebuilt -- the work package count is unchanged from the first
    # completion, proving nothing was (re-)enqueued for the restored records.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC7
  Scenario: Flag not set - the manual regeneration process still generates and prints a fresh Bestellkontrolle
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And the order identified by order is reactivated
    And the order identified by order is completed
    And the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | true     |
      | PL           |                | plant       | 1                      | true     |

    # The manual regeneration process (AC11): runs the unconditional rebuild regardless of the flag, unlike
    # the completion path which restored the existing generation above instead of rebuilding.
    When the AD_Process with value 'C_Order_MFGWarehouse_Report_Generate' is run for the record identified by 'order'

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | freshWhRpt | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | freshPlRpt | order      | PL           | plant       | true     |
    And C_Order_MFGWarehouse_Report doc-outbound enqueue status is:
      | C_Order_MFGWarehouse_Report_ID | IsEnqueued |
      | freshWhRpt                     | true       |
      | freshPlRpt                     | true       |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC8
  Scenario: Flag not set - reverse-correcting the order still deactivates the Bestellkontrolle
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |

    When the order identified by order is reversed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |
