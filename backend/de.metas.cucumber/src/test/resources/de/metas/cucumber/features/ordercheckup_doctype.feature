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
  Scenario: reset settings to default
    # A separate scenario rather than a trailing step, so it still runs if an assertion above failed -- see
    # ordercheckup_reprint.feature for the same pattern and its rationale.
    Given set sys config boolean value false for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
