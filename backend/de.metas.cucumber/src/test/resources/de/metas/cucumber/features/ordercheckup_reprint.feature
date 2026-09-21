@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor2
Feature: Bestellkontrolle reprint after reactivate
## F8008: Order Checkup Report

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-12T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    # The doc-outbound processor has to RUN: it is what turns the enqueued work package into an AD_Archive and
    # from there a C_Doc_Outbound_Log -- i.e. the actual print this feature is about. Skipping it would stop the
    # flow one step short of the printout. The Jasper itself is mocked, so no report server is needed.
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | PP_Plant_ID |
      | warehouse      | plant       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    # OrderCheckupBL reaches the planning via retrieveManufacturingOrTradingPlanning, which matches
    # IsManufactured='Y' OR IsTraded='Y'. Spelled out rather than left to the step-def default, because which of
    # the two is set is the difference between a manufacturing installation and a trading one (TC14).
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | M_Warehouse_ID | S_Resource_ID | IsManufactured | IsTraded |
      | product      | warehouse      | plant         | true           | false    |
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

    # The reactivate deactivates the records whatever the flag says, but leaves Processed alone.
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

    # Exactly the same 2 records, active again and still Processed -- nothing was built. The work
    # package count is unchanged from the first completion, proving nothing was (re-)enqueued.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | WH           | warehouse      | plant       | true     | true      |
      | PL           |                | plant       | true     | true      |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | keptWhRpt  | order      | WH           | warehouse      | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | keptPlRpt  | order      | PL           | plant       | true     |
    # Known limitation: the record that came back still points at the original order line only.
    And C_Order_MFGWarehouse_Report references order lines:
      | Identifier | C_OrderLine_ID |
      | keptWhRpt  | orderLine      |
      | keptPlRpt  | orderLine      |


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
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
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
  @Id:S30709_TC4
  Scenario: Reprint not set - completing brings the newest Bestellkontrolle back, not an earlier one
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | Y                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed

    # A second generation, told apart from the first by the order lines it covers: the flag is still set
    # here, so this reactivate-complete cycle rebuilds.
    And the order identified by order is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine                 | 2              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineB | order      | product      | 3          |
    And the order identified by order is completed
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 4                |

    When update order
      | C_Order_ID | IsReprintOrderCheckup |
      | order      | N                     |
    And the order identified by order is reactivated
    And the order identified by order is completed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier  | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | newestWhRpt | order      | WH           | warehouse      | plant       | true     |
      | olderWhRpt  | order      | WH           | warehouse      | plant       | false    |
    And C_Order_MFGWarehouse_Report is located:
      | Identifier  | C_Order_ID | DocumentType | PP_Plant_ID | IsActive |
      | newestPlRpt | order      | PL           | plant       | true     |
      | olderPlRpt  | order      | PL           | plant       | false    |
    # The records that came back cover both order lines, so they are the second generation's; the ones
    # left inactive cover the original line alone, so they are the first generation's.
    And C_Order_MFGWarehouse_Report references order lines:
      | Identifier  | C_OrderLine_ID |
      | newestWhRpt | orderLine      |
      | newestWhRpt | orderLineB     |
      | newestPlRpt | orderLine      |
      | newestPlRpt | orderLineB     |
      | olderWhRpt  | orderLine      |
      | olderPlRpt  | orderLine      |
    # Unchanged from before the last cycle -- bringing a report back prints nothing.
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 4                |


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
  Scenario: Flag not set - reactivating and completing again enqueues no new print
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

    # The reports came back instead of being rebuilt -- the work package count is unchanged from the
    # first completion, proving nothing was (re-)enqueued.
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
  Scenario: Flag not set - the manual regeneration process still rebuilds and prints a fresh Bestellkontrolle
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
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |

    # The process calls the same generation the completion path calls, and it rebuilds although the flag
    # is unset -- pinning that the flag is decided before that generation, not inside it. (EnableProcessGear
    # governs only whether the gear menu offers the process, not whether it runs, which is why this can run it.)
    When the AD_Process with value 'C_Order_MFGWarehouse_Report_Generate' is run on the records identified by 'order'

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
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


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC11
  Scenario: Reprint not set - a line that only becomes plannable after the reactivate gets its own Bestellkontrolle
    Given metasfresh contains M_Products:
      | Identifier       |
      | unplannedProduct |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID     | PriceStd | C_UOM_ID |
      | priceListVersion       | unplannedProduct | 10.0     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID     | QtyEntered |
      | orderLine  | order      | unplannedProduct | 5          |
    And the order identified by order is completed

    # The product has no manufacturing planning, so its line yields no warehouse report -- the plant report
    # is built anyway, and its existence is what used to stop the warehouse report from ever being built.
    And the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | PP_Plant_ID | IsActive | Processed |
      | PL           | plant       | true     | true      |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 1                |

    When the order identified by order is reactivated
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID     | M_Warehouse_ID | S_Resource_ID |
      | unplannedProduct | warehouse      | plant         |
    And the order identified by order is completed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | PL           |                | plant       | true     | true      |
      | WH           | warehouse      | plant       | true     | true      |
    # Exactly one more work package: the warehouse report is new and enqueues, the plant report only came back.
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC12
  Scenario: Reprint not set - a Bestellkontrolle whose work left the order stays inactive
    Given metasfresh contains M_Products:
      | Identifier       |
      | unplannedProduct |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID     | PriceStd | C_UOM_ID |
      | priceListVersion       | unplannedProduct | 10.0     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID     | QtyEntered |
      | plannedLine   | order      | product          | 5          |
      | unplannedLine | order      | unplannedProduct | 5          |
    And the order identified by order is completed
    And the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | WH           | warehouse      | plant       | true     | true      |
      | PL           |                | plant       | true     | true      |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |

    When the order identified by order is reactivated
    # The only line the warehouse report was built from leaves the order
    And delete C_OrderLine identified by plannedLine, but keep its id into identifierIds table
    And the order identified by order is completed

    # Nothing on the order calls for the warehouse report any more, so it stays down; the plant report
    # is still called for and comes back. Nothing was built, so nothing was enqueued.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | WH           | warehouse      | plant       | false    | true      |
      | PL           |                | plant       | true     | true      |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC13
  Scenario: Order checkups not enabled - completing generates nothing, whatever the reprint flag says
    # Back to the value a stock installation has: order checkups are opt-in, and the Background above is
    # what makes every other scenario in this file a customer that opted in. Deliberately not restored
    # afterwards -- the Background re-enables it per scenario, so the feature leaves the stock default behind.
    Given set sys config boolean value false for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    # The reprint flag at its column default, so this shows it changes nothing rather than sidestepping it
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | Y                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |

    When the order identified by order is completed

    # Nothing at all -- not even the plant report, which an order whose lines cannot be planned still gets.
    # The reprint flag is never reached: an installation that has not enabled order checkups keeps none.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 0                |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S30709_TC14
  Scenario: Traded instead of manufactured - the Bestellkontrolle behaves the same, reprint flag included
    # This customer trades its products rather than manufacturing them, so its plannings carry IsTraded='Y' and
    # IsManufactured='N' -- the other half of retrieveManufacturingOrTradingPlanning's OR. Every other scenario here
    # runs the manufactured half, so without this one the configuration actually in use is never exercised.
    Given metasfresh contains M_Products:
      | Identifier    |
      | tradedProduct |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID |
      | priceListVersion       | tradedProduct | 10.0     | PCE      |
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID  | M_Warehouse_ID | S_Resource_ID | IsManufactured | IsTraded |
      | tradedProduct | warehouse      | plant         | false          | true     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID  | QtyEntered |
      | orderLine  | order      | tradedProduct | 5          |
    And the order identified by order is completed
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |

    When the order identified by order is reactivated
    And the order identified by order is completed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |

# ####################################################################################################################
# ####################################################################################################################
  Scenario: reset settings to default
    # A separate scenario rather than a trailing step: a step only runs when every assertion above it passed, so it
    # would be skipped exactly when a scenario failed. No scenario in this feature may leave the shared stack in a
    # state the next feature does not expect: order checkups go back to their shipped 'N', and the doc-outbound
    # processor back to skipped, which is what the rest of the cucumber suite runs with. The Jasper mock is left on
    # deliberately -- mocking is the direction for the suite, and no feature relies on real report rendering.
    Given set sys config boolean value false for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
