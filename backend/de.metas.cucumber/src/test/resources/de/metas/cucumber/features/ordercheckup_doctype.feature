@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor2
Feature: Bestellkontrolle document type
## F8005: Order Checkup

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-12T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And load C_DocType:
      | Name                        | C_DocType_ID.Identifier |
      | Bestellkontrolle Produktion | docTypeProduktion       |
      | Bestellkontrolle Büro       | docTypeBuero            |
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
  @Id:S32265_TC1
  Scenario: Completing the order assigns each kind its own Bestellkontrolle document type
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | bpartner      | 2026-01-12  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |

    When the order identified by order is completed

    # The Warehouse (production) sheet gets the Produktion doctype, the Plant (office) sheet gets the
    # Büro doctype -- each kind now resolves its own document type, which is what lets
    # DocOutboundConfigService/AD_PrinterRouting pick a different outbound configuration and printer per kind.
    Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
      | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | C_DocType_ID      |
      | WH           | warehouse      | plant       | true     | docTypeProduktion |
      | PL           |                | plant       | true     | docTypeBuero      |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S32265_TC2
  Scenario: Each kind resolves its own outbound configuration, never the generic fallback
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order2     | true    | bpartner      | 2026-01-12  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine2 | order2     | product      | 5          |

    When the order identified by order2 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier   | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt | order2     | WH           | warehouse      | plant       |
      | plantRpt     | order2     | PL           |                | plant       |

    # Crux assertion: each kind resolves ITS OWN C_Doc_Outbound_Config (540022 / 540023) -- never the generic
    # fallback (540002). Resolving the fallback is the exact silent failure this feature exists to prevent.
    Then C_Order_MFGWarehouse_Report resolves C_Doc_Outbound_Config:
      | C_Order_MFGWarehouse_Report_ID | C_Doc_Outbound_Config_ID |
      | warehouseRpt                   | 540022                   |
      | plantRpt                       | 540023                   |

    # Both configurations ship pointing at the same print format by design. Repointing just one of them proves
    # the customer's pending decision (splitting the two reports) is a single field edit, nothing more.
    When update C_Doc_Outbound_Config print format:
      | C_Doc_Outbound_Config_ID | AD_PrintFormat_ID |
      | 540022                   | 540097            |

    Then C_Order_MFGWarehouse_Report resolves C_Doc_Outbound_Config:
      | C_Order_MFGWarehouse_Report_ID | C_Doc_Outbound_Config_ID | AD_PrintFormat_ID |
      | warehouseRpt                   | 540022                   | 540097            |
      | plantRpt                       | 540023                   | 540068            |

    # The shipped configuration is restored by an @After hook (C_Doc_Outbound_Config_StepDef), not a trailing
    # step here -- Cucumber skips remaining steps once one fails, i.e. on exactly the runs that need the restore.


# ####################################################################################################################
# ####################################################################################################################
  @Id:S32265_TC3
  Scenario: Each kind's printing-queue item carries its own document type and resolves its own printer routing
    # Doctype-specific printer routings for the two Bestellkontrolle kinds. Printer 1000000 already exists on
    # the target instance (it also backs the pre-existing catch-all routing, AD_PrinterRouting_ID=1000003,
    # every dimension null) -- only the routing's document-type dimension differs here.
    Given metasfresh contains AD_PrinterRouting:
      | Identifier        | C_DocType_ID      | AD_Printer_ID |
      | routingProduktion | docTypeProduktion | 1000000       |
      | routingBuero      | docTypeBuero      | 1000000       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order3     | true    | bpartner      | 2026-01-12  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine3 | order3     | product      | 5          |

    When the order identified by order3 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt3 | order3     | WH           | warehouse      | plant       |
      | plantRpt3     | order3     | PL           |                | plant       |

    # Mirrors the real path: record -> archive -> printing-queue item. No queue item is fabricated -- this
    # locates the one the order-completion pipeline itself enqueued. The doc-outbound work package runs on a
    # background thread, so this step polls for the queue item rather than assuming it exists already.
    And C_Printing_Queue item is located:
      | Identifier      | C_Order_MFGWarehouse_Report_ID |
      | warehouseQueue3 | warehouseRpt3                  |
      | plantQueue3     | plantRpt3                      |

    # The printing-queue item -- not just the report -- carries its own C_DocType_ID. This is
    # DocumentPrintingQueueHandler reading the column generically off the archived record, the same mechanism
    # DocOutboundConfigService uses, now exercised on the real enqueued queue item rather than the report.
    Then C_Printing_Queue resolves C_DocType:
      | C_Printing_Queue_ID | C_DocType_ID      |
      | warehouseQueue3     | docTypeProduktion |
      | plantQueue3         | docTypeBuero      |

    # Each kind's queue item resolves ITS OWN doctype-specific AD_PrinterRouting -- never the
    # pre-existing catch-all (AD_PrinterRouting_ID=1000003, every dimension null), which PrinterRoutingDAO
    # also matches (PrinterRoutingDAO.java:81). Resolving the catch-all would pass a weaker "a routing was
    # found" assertion but is the exact silent failure this feature exists to prevent. Stops at routing
    # resolution -- the final hop to a physical printer (AD_Printer_Matching) needs real printers.
    Then C_Printing_Queue resolves AD_PrinterRouting:
      | C_Printing_Queue_ID | AD_PrinterRouting_ID |
      | warehouseQueue3     | routingProduktion    |
      | plantQueue3         | routingBuero         |

    # The AD_PrinterRouting fixtures are removed by an @After hook (AD_PrinterRouting_StepDef), not a trailing
    # step here -- Cucumber skips remaining steps once one fails, i.e. on exactly the runs that need cleanup.


# ####################################################################################################################
# ####################################################################################################################
  @Id:S32265_TC4
  Scenario: An unconfigured manufacturing routing silently cancels the Packzettel print job
    # The manufacturing routing every order in this feature completes against (the default routing behind
    # "metasfresh contains PP_Product_Plannings" in the Background) has no user in charge by default -- the
    # exact unconfigured state that made the Packzettel vanish for the customer, with no error anywhere.
    # That routing is shared master data referenced by many features on this executor, so pin the
    # unconfigured precondition explicitly rather than assuming whatever ran before left it unset.
    Given update AD_Workflow user in charge:
      | AD_Workflow_ID | AD_User_InCharge_ID |
      | 540075         |                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order4     | true    | bpartner      | 2026-01-12  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine4 | order4     | product      | 5          |

    When the order identified by order4 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt4 | order4     | WH           | warehouse      | plant       |

    And C_Printing_Queue item is located:
      | Identifier      | C_Order_MFGWarehouse_Report_ID |
      | warehouseQueue4 | warehouseRpt4                  |

    # No user in charge on the routing -> the Warehouse record carries no responsible user, and its
    # printing-queue item is cancelled (deactivated) -- this is the exact silent failure this scenario guards.
    Then C_Order_MFGWarehouse_Report resolves responsible user:
      | C_Order_MFGWarehouse_Report_ID |
      | warehouseRpt4                  |

    And C_Printing_Queue has IsActive:
      | C_Printing_Queue_ID | IsActive |
      | warehouseQueue4     | false    |

    # Configuring a user in charge on that SAME routing is the actual fix: the responsible user is carried
    # through to the next order's Warehouse record and its print job stays active.
    Given metasfresh contains AD_Users:
      | Identifier  |
      | routingUser |
    And update AD_Workflow user in charge:
      | AD_Workflow_ID | AD_User_InCharge_ID |
      | 540075         | routingUser         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order5     | true    | bpartner      | 2026-01-12  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine5 | order5     | product      | 5          |

    When the order identified by order5 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt5 | order5     | WH           | warehouse      | plant       |

    And C_Printing_Queue item is located:
      | Identifier      | C_Order_MFGWarehouse_Report_ID |
      | warehouseQueue5 | warehouseRpt5                  |

    Then C_Order_MFGWarehouse_Report resolves responsible user:
      | C_Order_MFGWarehouse_Report_ID | AD_User_ID  |
      | warehouseRpt5                  | routingUser |

    And C_Printing_Queue has IsActive:
      | C_Printing_Queue_ID | IsActive |
      | warehouseQueue5     | true     |

    # The routing's prior AD_User_InCharge_ID is restored by an @After hook (AD_Workflow_StepDef), not a
    # trailing step here -- Cucumber skips remaining steps once one fails, i.e. on exactly the runs that need
    # the restore, and this routing is shared master data every order in this feature completes against.


# ####################################################################################################################
# ####################################################################################################################
  @Id:S32265_TC5
  Scenario: Every existing Bestellkontrolle report has a document type consistent with its own kind
    # Guards what the backfill migration actually claims: every row's document type is consistent with its
    # own kind, in both directions, with at least one row of each kind checked -- not the weaker "no NULLs"
    # proxy, which a no-op backfill could satisfy if every row already happened to carry some doctype.
    Then every C_Order_MFGWarehouse_Report has a document type consistent with its DocumentType:
      | DocumentType | C_DocType_ID      |
      | WH           | docTypeProduktion |
      | PL           | docTypeBuero      |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S32265_TC6
  Scenario: The document type value list and its per-kind printed-copies count are unchanged
    # The DocumentType value list and the C_DocType_ID column represent the same kind. Values and
    # translations are unchanged.
    # Spot-checked in a language whose translation differs from the default, so a lost/reset translation is
    # caught, not just a lost value.
    Then C_Order_MFGWarehouse_Report DocumentType value list is unchanged:
      | Value | Name                       | AD_Language | TranslatedName        |
      | WH    | Bestellkontrolle           | fr_FR       | Contrôle de l’ordre   |
      | PL    | Bestellkontrolle spedition | fr_FR       | Transfert de commande |

    # A non-default value on just the Warehouse-kind sys config so a swapped or ignored per-kind branch shows
    # up as a mismatch instead of passing vacuously against the shared default of 1 for both kinds.
    Given temporarily set sys config int value 2 for sys config 'de.metas.fresh.ordercheckup_barcode.Copies'

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order6     | true    | bpartner      | 2026-01-12  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine6 | order6     | product      | 5          |

    When the order identified by order6 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt6 | order6     | WH           | warehouse      | plant       |
      | plantRpt6     | order6     | PL           |                | plant       |

    And C_Printing_Queue item is located:
      | Identifier      | C_Order_MFGWarehouse_Report_ID |
      | warehouseQueue6 | warehouseRpt6                  |
      | plantQueue6     | plantRpt6                      |

    # getNumberOfCopies still routes Warehouse-kind reports to the barcode-sheet sys config and Plant-kind
    # reports to the plain one -- the per-kind branch that reads the DocumentType value list.
    Then C_Printing_Queue resolves number of copies from sys config:
      | C_Printing_Queue_ID | SysConfigName                              |
      | warehouseQueue6     | de.metas.fresh.ordercheckup_barcode.Copies |
      | plantQueue6         | de.metas.fresh.ordercheckup.Copies         |

    # The sys config is restored to its prior value by an @After hook (AD_SysConfig_StepDef), not a trailing
    # step here -- Cucumber skips remaining steps once one fails, i.e. on exactly the runs that need the
    # restore.


# ####################################################################################################################
# ####################################################################################################################
  Scenario: reset settings to default
    # A separate scenario rather than a trailing step, so it still runs if an assertion above failed -- see
    # ordercheckup_reprint.feature for the same pattern and its rationale.
    Given set sys config boolean value false for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
