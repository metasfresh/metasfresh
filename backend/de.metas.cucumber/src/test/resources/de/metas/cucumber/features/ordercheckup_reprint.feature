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

    # With the flag unset the reactivate leaves the records completely alone -- they stay active and stay
    # Processed. Processed staying true is what proves no reprint: the print trigger keys on it changing.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
      | WH           | warehouse      | plant       | true     | true      |
      | PL           |                | plant       | true     | true      |

    # Split the line into two, total quantity per product unchanged - the customer's own correction pattern
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine                 | 2              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineB | order      | product      | 3          |
    And the order identified by order is completed

    # Still exactly the same 2 records, still active, still Processed -- nothing was built. The work
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
    # Known limitation: the kept record still points at the original order line only.
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
  Scenario: Flag switched off after the reactivate already deactivated the Bestellkontrolle - completing generates a fresh one
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | Y                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 2                |

    # Flag still set at reactivate time, so the reports ARE deactivated here.
    When the order identified by order is reactivated

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |

    # The clerk switches the flag off before completing again. There is no active report left to keep,
    # so the completion still generates -- the same fall-back that gives an order its first printout.
    When update order
      | C_Order_ID | IsReprintOrderCheckup |
      | order      | N                     |
    And the order identified by order is completed

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | false    |
      | PL           |                | plant       | false    |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
    # 2 more work packages than before the cycle -- the fresh reports were enqueued and printed.
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

    # The reports were never touched, let alone rebuilt -- the work package count is unchanged from the
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
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |

    # The manual regeneration process runs the unconditional rebuild regardless of the flag, unlike
    # the completion path above, which left the existing reports alone instead of rebuilding.
    When the AD_Process with value 'C_Order_MFGWarehouse_Report_Generate' is run for the record identified by 'order'

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
  @Id:S30709_TC9
  Scenario: Flag not set - reverse-accruing the order is refused, so the Bestellkontrolle can never be deactivated this way
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

    # C_Order never actually supports reverse-accrual (MOrder#reverseAccrualIt() unconditionally returns
    # false), so the interceptor's TIMING_AFTER_REVERSEACCRUAL binding can never fire for an order in
    # practice. This pins that AC8's third document action is a defensive, unreachable wiring for
    # Orders, and that the reports stay untouched because the action itself never succeeds.
    Then the order identified by order cannot be reverseAccrued

    And the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive |
      | WH           | warehouse      | plant       | true     |
      | PL           |                | plant       | true     |
