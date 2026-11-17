@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor2
Feature: Plant order checkup (PL) copies from the shipper (Lieferweg) of the order
## F8005: Order Checkup
  The ship-to address references a shipper (Lieferweg) that carries a copy count; the sales order takes the shipper over.
  - Copy count N > 0: the Plant order checkup (PL) prints N copies, automatically and on a manual reprint.
  - Copy count 0: the Plant order checkup (PL) is archived but not printed automatically; a manual reprint prints one copy.
  - The Plant order checkup (PL) always prints as many copies as the delivery note of its order: shipper count, else the ship-to address
    print format, else the shipment document type. The Warehouse order checkup (WH) is never affected.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-11-17T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value false for sys config InterceptorEnabled_de.metas.edi.model.validator.C_BPartner#validate
    # the plant's responsible user is who the Plant order checkup (PL) is printed for; without one its queue item is cancelled
    And metasfresh contains AD_Users:
      | Identifier |
      | plantUser  |
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon | AD_User_ID |
      | plant      | 1000000           | Y                       | PT                        | 999             | plantUser  |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | PP_Plant_ID |
      | warehouse      | plant       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID | M_Warehouse_ID | S_Resource_ID | IsManufactured | IsTraded |
      | productPlanning | product      | warehouse      | plant         | true           | false    |
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
  @Id:S33610_TC1
  Scenario: Reference case - shipper with 2 copies: the Plant order checkup (PL) is enqueued with 2 copies, the shipment still with 2
    # on-hand stock, so the shipment can be generated
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyCount | QtyBook |
      | stock1         | warehouse      | 2026-11-17   | product      | 20 PCE   | 0 PCE   |
    And contains M_Shippers
      | Identifier       | DocumentCopies |
      | shipperTwoCopies1 | 2              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | GLN           | IsShipTo | IsBillTo | M_Shipper_ID     |
      | location1  | bpartner      | 4008123433101 | true     | true     | shipperTwoCopies1 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | order1     | true    | bpartner      | 2026-11-17  | location1              | warehouse      |
    And the order identified by order1 takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine1 | order1     | product      | 5          |

    When the order identified by order1 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt1 | order1     | WH           | warehouse      | plant       |
      | plantRpt1     | order1     | PL           |                | plant       |
    And C_Printing_Queue item is located:
      | Identifier      | C_Order_MFGWarehouse_Report_ID |
      | warehouseQueue1 | warehouseRpt1                  |
      | plantQueue1     | plantRpt1                      |

    Then C_Printing_Queue has Copies:
      | C_Printing_Queue_ID | Copies |
      | plantQueue1         | 2      |
      | warehouseQueue1     | 1      |
    And C_Printing_Queue has IsActive:
      | C_Printing_Queue_ID | IsActive |
      | plantQueue1         | true     |

    # the same Lieferweg count still drives the delivery note exactly as before: 2 copies, not more
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | M_Warehouse_ID |
      | schedule1  | orderLine1     | N             | warehouse      |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule1             | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | schedule1             | shipment1  |
    When the shipment identified by shipment1 is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | shipmentLog1                     | shipment1            | M_InOut       | MMS             | CO            |
    And C_Printing_Queue item is located:
      | Identifier     | Record_ID |
      | shipmentQueue1 | shipment1 |
    And C_Printing_Queue has Copies:
      | C_Printing_Queue_ID | Copies |
      | shipmentQueue1      | 2      |
    And C_Printing_Queue contains 1 items for the record identified by "shipment1"


# ####################################################################################################################
# ####################################################################################################################
  @Id:S33610_TC1b
  Scenario: The shipper's copies replace the sysconfig copies (sysconfig 2, shipper 2 -> 2, not 4)
    Given temporarily set sys config int value 2 for sys config 'de.metas.fresh.ordercheckup.Copies'
    And contains M_Shippers
      | Identifier       | DocumentCopies |
      | shipperTwoCopies2 | 2              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | GLN           | IsShipTo | IsBillTo | M_Shipper_ID     |
      | location2  | bpartner      | 4008123433102 | true     | true     | shipperTwoCopies2 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | order2     | true    | bpartner      | 2026-11-17  | location2              | warehouse      |
    And the order identified by order2 takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine2 | order2     | product      | 5          |

    When the order identified by order2 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | plantRpt2  | order2     | PL           |                | plant       |
    And C_Printing_Queue item is located:
      | Identifier  | C_Order_MFGWarehouse_Report_ID |
      | plantQueue2 | plantRpt2                      |

    Then C_Printing_Queue has Copies:
      | C_Printing_Queue_ID | Copies |
      | plantQueue2         | 2      |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S33610_TC1c
  Scenario: A partner print-format copy count does not multiply the shipper's copies (partner 2, shipper 2 -> 2, not 4)
    Given metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID |
      | bpartnerWithBPF | Y          | pricingSystem      |
    And metasfresh contains C_BP_PrintFormat:
      | C_BPartner_ID   | DocumentCopies_Override |
      | bpartnerWithBPF | 2                       |
    And contains M_Shippers
      | Identifier       | DocumentCopies |
      | shipperTwoCopies3 | 2              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID   | GLN           | IsShipTo | IsBillTo | M_Shipper_ID     |
      | location3  | bpartnerWithBPF | 4008123433103 | true     | true     | shipperTwoCopies3 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID   | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | order3     | true    | bpartnerWithBPF | 2026-11-17  | location3              | warehouse      |
    And the order identified by order3 takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine3 | order3     | product      | 5          |

    When the order identified by order3 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | plantRpt3  | order3     | PL           |                | plant       |
    And C_Printing_Queue item is located:
      | Identifier  | C_Order_MFGWarehouse_Report_ID |
      | plantQueue3 | plantRpt3                      |

    Then C_Printing_Queue has Copies:
      | C_Printing_Queue_ID | Copies |
      | plantQueue3         | 2      |


# ####################################################################################################################
# ####################################################################################################################
  @Id:S33610_TC2_TC3
  Scenario: Shipper with 0 copies: archived, not enqueued automatically; a manual reprint enqueues it with one copy
    Given contains M_Shippers
      | Identifier | DocumentCopies |
      | noPrint    | 0              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | GLN           | IsShipTo | IsBillTo | M_Shipper_ID |
      | location4  | bpartner      | 4008123433104 | true     | true     | noPrint      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | order4     | true    | bpartner      | 2026-11-17  | location4              | warehouse      |
    And the order identified by order4 takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine4 | order4     | product      | 5          |

    When the order identified by order4 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier    | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | warehouseRpt4 | order4     | WH           | warehouse      | plant       |
      | plantRpt4     | order4     | PL           |                | plant       |

    # the Warehouse order checkup (WH) is still enqueued (not affected by the shipper)
    Then C_Printing_Queue item is located:
      | Identifier      | C_Order_MFGWarehouse_Report_ID |
      | warehouseQueue4 | warehouseRpt4                  |
    # the Plant order checkup (PL) is archived, but nothing is enqueued automatically
    And after not more than 60s, an AD_Archive exists for the record identified by "plantRpt4"
    And C_Printing_Queue contains 0 items for the record identified by "plantRpt4"

    # manual reprint (fresh archive, through the auto-print gate): enqueued, with one copy
    When after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name               |
      | plantLog4                        | plantRpt4            | C_Order_MFGWarehouse_Report |
    And the doc outbound log identified by "plantLog4" is reprinted
    Then C_Printing_Queue contains 1 items for the record identified by "plantRpt4"
    And all C_Printing_Queue items for the record identified by "plantRpt4" have Copies 1


# ####################################################################################################################
# ####################################################################################################################
  @Id:S33610_TC3b
  Scenario: Shipper with 2 copies: a manual reprint prints 2 copies as well
    Given contains M_Shippers
      | Identifier       | DocumentCopies |
      | shipperTwoCopies5 | 2              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | GLN           | IsShipTo | IsBillTo | M_Shipper_ID     |
      | location5  | bpartner      | 4008123433105 | true     | true     | shipperTwoCopies5 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | order5     | true    | bpartner      | 2026-11-17  | location5              | warehouse      |
    And the order identified by order5 takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine5 | order5     | product      | 5          |

    When the order identified by order5 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | plantRpt5  | order5     | PL           |                | plant       |
    And C_Printing_Queue item is located:
      | Identifier  | C_Order_MFGWarehouse_Report_ID |
      | plantQueue5 | plantRpt5                      |
    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name               |
      | plantLog5                        | plantRpt5            | C_Order_MFGWarehouse_Report |

    When the doc outbound log identified by "plantLog5" is reprinted

    Then C_Printing_Queue contains 2 items for the record identified by "plantRpt5"
    And all C_Printing_Queue items for the record identified by "plantRpt5" have Copies 2


# ####################################################################################################################
# ####################################################################################################################
  @Id:S33610_TC4
  Scenario: Shipper without copy count: the customer's own delivery-note copies (address print format 3 -> 3; nothing configured -> shipment doc type copies)
    Given contains M_Shippers
      | Identifier | DocumentCopies |
      | noCount    |                |
    And metasfresh contains C_BPartners:
      | Identifier        | IsCustomer | M_PricingSystem_ID |
      | bpartnerAddress3  | Y          | pricingSystem      |
      | bpartnerNoShipper | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID     | GLN           | IsShipTo | IsBillTo | M_Shipper_ID |
      | location6  | bpartnerAddress3  | 4008123433106 | true     | true     | noCount      |
      | location7  | bpartnerNoShipper | 4008123433107 | true     | true     |              |
    # the customer's delivery-note copies on its ship-to address (as the per-address copies migrated from the legacy system)
    And metasfresh contains C_BP_PrintFormat:
      | C_BPartner_ID    | C_BPartner_Location_ID | DocumentCopies_Override |
      | bpartnerAddress3 | location6              | 3                       |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID     | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | order6     | true    | bpartnerAddress3  | 2026-11-17  | location6              | warehouse      |
      | order7     | true    | bpartnerNoShipper | 2026-11-17  | location7              | warehouse      |
    And the order identified by order6 takes over the delivery defaults of its ship-to location
    And the order identified by order7 takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine6 | order6     | product      | 5          |
      | orderLine7 | order7     | product      | 5          |

    When the order identified by order6 is completed
    And the order identified by order7 is completed

    And C_Order_MFGWarehouse_Report is located:
      | Identifier | C_Order_ID | DocumentType | M_Warehouse_ID | PP_Plant_ID |
      | plantRpt6  | order6     | PL           |                | plant       |
      | plantRpt7  | order7     | PL           |                | plant       |
    And C_Printing_Queue item is located:
      | Identifier  | C_Order_MFGWarehouse_Report_ID |
      | plantQueue6 | plantRpt6                      |
      | plantQueue7 | plantRpt7                      |

    Then C_Printing_Queue has Copies:
      | C_Printing_Queue_ID | Copies |
      | plantQueue6         | 3      |
      | plantQueue7         | 1      |
