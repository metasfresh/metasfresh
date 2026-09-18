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
    # Known limitation: the restored record still points at the original order line only.
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
  # Generation-mechanics scenario -- next free TC number.
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
  @Id:S30709_TC4
  Scenario: Flag not set - reactivating and completing after a second generation exists restores only the most recent one, and stays stable across repeated cycles
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | IsReprintOrderCheckup |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      | N                     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed

    # The manual regeneration process ignores the flag and always rebuilds (mechanics pinned by TC7)
    # -- used here only to get a genuine second generation on the books, without ever setting the
    # flag, before the flag-off restore path below is exercised.
    When the AD_Process with value 'C_Order_MFGWarehouse_Report_Generate' is run for the record identified by 'order'

    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
    # Baseline for the work-package-count checks below: 2 generations x 2 reports each = 4.
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 4                |

    When the order identified by order is reactivated
    And the order identified by order is completed

    # Flag stays unset throughout: the completion restores generation 2 (the most recent) rather than
    # generation 1 or a fresh rebuild. Pinning OrderCheckupGeneration here is what actually
    # distinguishes "restore the most recent generation" from "restore everything" and from
    # "restore generation 1" -- generation 1 must stay deactivated while generation 2 comes back
    # active.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
    # The restore only reactivated generation 2's headers -- nothing new was built, so the work
    # package count from before this reactivate/complete cycle (4, asserted above) is unchanged.
    And C_Order_MFGWarehouse_Report doc-outbound work package count is:
      | C_Order_ID | WorkPackageCount |
      | order      | 4                |

    When the order identified by order is reactivated
    And the order identified by order is completed

    # Second flag-off cycle: still generation 2, still exactly the same 4 records -- the restore is
    # idempotent and never accumulates a third generation across repeated reactivate/complete cycles.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | OrderCheckupGeneration | IsActive |
      | WH           | warehouse      | plant       | 1                      | false    |
      | PL           |                | plant       | 1                      | false    |
      | WH           | warehouse      | plant       | 2                      | true     |
      | PL           |                | plant       | 2                      | true     |
    # Still unchanged after a second repeated cycle -- confirms the restore never (re-)enqueues, even
    # across repeated reactivate/complete cycles, not just once.
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

    # The manual regeneration process runs the unconditional rebuild regardless of the flag, unlike
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
