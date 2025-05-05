@from:cucumber
@ghActions:run_on_executor2
Feature: accounting for shipping notification

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |
    And update C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.CostingMethod |
      | acctSchema_1               | M                 |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                     |
      | p_1        | purchaseProduct_101023_1 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                 |
      | huPackingLU_101023_1  | huPackingLU_101023_1 |
      | huPackingTU_101023_1  | huPackingTU_101023_1 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                      | HU_UnitType | IsCurrent |
      | packingVersionLU_101023_1     | huPackingLU_101023_1  | packingVersionLU_101023_1 | LU          | Y         |
      | packingVersionTU_101023_1     | huPackingTU_101023_1  | packingVersionTU_101023_1 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU_101023_1     | 20  | HU       | huPackingTU_101023_1             |
      | huPiItemTU                 | packingVersionTU_101023_1     | 20  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 20  | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                         | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_04022023_1 | pricing_system_value_101023_1 | pricing_system_description_04022023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_101023_1 | null            | false | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | price_list_name_101023_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_101023_1 | 2021-04-01 |
      | plv_2      | pl_2                      | salesOrder-PLV_101023_1    | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name              | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartner_101023_1 | Y            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpLocationDefault | 0123451010023 | bpartner_1               | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | bpartner_1               | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 20         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_04022023_1      | o_1                   | ol_1                      | bpartner_1               | bpLocationDefault                 | p_1                     | 20         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_04022023_1      | N               | 1     | N               | 1     | N               | 20          | huItemPurchaseProduct              | huPackingLU_101023_1         |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_04022023_1      | inOut_210320222_1     |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoice_candidate_1               | ol_1                      | 20           |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |
    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_1                | 001   |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value                 |
      | locator                 | warehouseStd              | LocatorValue_10102023 |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                      | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsBillToContact_Default |
      | bpContactDefault      | bpContactDefault_10102023 | bpartner_1                   | bpLocationDefault                     | Y                           |
    And metasfresh contains C_Auction:
      | Identifier | Name     | Date       |
      | Auction_1  | Auction1 | 2023-09-21 |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | P_Asset_Acct                 | element_1               | 90000 |
      | P_ExternallyOwnedStock_Acct  | element_1               | 90055 |
      | P_COGS_Acct                  | element_1               | 90056 |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |


  @from:cucumber
  @Id:S0322_100
  @Id:S0322_300
  Scenario: generate shipping notification -> validate accounts
  generate another shipping notification (first one gets reversed) -> validate accounts
  generate shipment -> validate fact accounts
  reverse shipment -> validate fact accounts
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | bpartner_1               | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | bpartner_1                   | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference          | processed | docStatus | OPT.PhysicalClearanceDate |
      | o_1                   | bpartner_1               | bpLocationDefault                 | SOO         | EUR          | F            | P               | POReference_21092023 | true      | CO        | 2021-04-20                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed | OPT.PhysicalClearanceDate |
      | s_ol_1                           | 10           | 0            | 10         | false     | 2021-04-20                |

    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR  | CR  | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | P_ExternallyOwnedStock_Acct | 113 | 0   | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | P_Asset_Acct                | 0   | 113 | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-19            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023_2       | o_1                   | CO        | bpartner_1                   | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1_2     | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
    And after not more than 30s, locate reversal M_Shipping_Notifications
      | M_Shipping_Notification_ID.Identifier | Reversal_ID.Identifier        |
      | reversalShippingNotification_21092023 | shippingNotification_21092023 |

    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023_1       | o_1                   | CO        | bpartner_1                   | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_2       | shippingNotification_21092023_1       | s_ol_1                           | p_1                     | 10          |

    And after not more than 30s, the shippingNotification document with identifier reversalShippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR   | CR   | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_3              | P_ExternallyOwnedStock_Acct | -113 | 0    | chf                      | P_ExternallyOwnedStock_Acct | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_4              | P_Asset_Acct                | 0    | -113 | chf                      | P_Asset_Acct                | 10      | bpLocationDefault                     | p_1                         | locator                     |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_ol_1                           | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |

    And after not more than 30s, the inout document with identifier shipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR  | CR  | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty |
      | factAcct_5              | P_Asset_Acct                | 0   | 0   | chf                      | P_Asset_Acct                | 0       |
      | factAcct_6              | P_COGS_Acct                 | 0   | 0   | chf                      | P_COGS_Acct                 | 0       |
      | factAcct_7              | P_ExternallyOwnedStock_Acct | 0   | 113 | chf                      | P_ExternallyOwnedStock_Acct | -10     |
      | factAcct_8              | P_COGS_Acct                 | 113 | 0   | chf                      | P_COGS_Acct                 | 10      |

    And the shipment identified by shipment_1 is reversed

    And after not more than 30s, locate reversal M_InOut
      | M_InOut_ID.Identifier | Reversal_ID.Identifier |
      | reversalShipment_1    | shipment_1             |

    And after not more than 30s, the inout document with identifier reversalShipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR   | CR   | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty |
      | factAcct_11             | P_Asset_Acct                | 0    | 0    | chf                      | P_Asset_Acct                | 0       |
      | factAcct_12             | P_COGS_Acct                 | 0    | 0    | chf                      | P_COGS_Acct                 | 0       |
      | factAcct_21             | P_ExternallyOwnedStock_Acct | 0    | -113 | chf                      | P_ExternallyOwnedStock_Acct | 10      |
      | factAcct_22             | P_COGS_Acct                 | -113 | 0    | chf                      | P_COGS_Acct                 | -10     |


  @from:cucumber
  @Id:S0322_200
  Scenario: generate shipping notification for sales order having fact accounts generated for it, but when order is voided, then notification is reversed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | bpartner_1               | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | bpartner_1                   | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference          | processed | docStatus | OPT.PhysicalClearanceDate |
      | o_1                   | bpartner_1               | bpLocationDefault                 | SOO         | EUR          | F            | P               | POReference_21092023 | true      | CO        | 2021-04-20                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed | OPT.PhysicalClearanceDate |
      | s_ol_1                           | 10           | 0            | 10         | false     | 2021-04-20                |
    When the order identified by o_1 is voided
    And after not more than 30s, locate reversal M_Shipping_Notifications
      | M_Shipping_Notification_ID.Identifier | Reversal_ID.Identifier        |
      | reversalShippingNotification_21092023 | shippingNotification_21092023 |
    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR  | CR  | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | P_ExternallyOwnedStock_Acct | 113 | 0   | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | P_Asset_Acct                | 0   | 113 | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |
    And after not more than 30s, the shippingNotification document with identifier reversalShippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR   | CR   | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_3              | P_ExternallyOwnedStock_Acct | -113 | 0    | chf                      | P_ExternallyOwnedStock_Acct | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_4              | P_Asset_Acct                | 0    | -113 | chf                      | P_Asset_Acct                | 10      | bpLocationDefault                     | p_1                         | locator                     |


  @from:cucumber
  @Id:S0322_400
  Scenario: generate shipping notification from sales order, ship more than notified and validate fact accounts generated for the shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | bpartner_1               | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | bpartner_1                   | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |

    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR  | CR  | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | P_ExternallyOwnedStock_Acct | 113 | 0   | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | P_Asset_Acct                | 0   | 113 | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_ol_1                           | 20                        |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_ol_1                           |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_ol_1                           | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference          | processed | docStatus | OPT.PhysicalClearanceDate |
      | o_1                   | bpartner_1               | bpLocationDefault                 | SOO         | EUR          | F            | P               | POReference_21092023 | true      | CO        | 2021-04-20                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed | OPT.PhysicalClearanceDate |
      | s_ol_1                           | 0            | 20           | 10         | true      | 2021-04-20                |

    And after not more than 30s, the inout document with identifier shipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR  | CR  | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.NoOfHits |
      | factAcct_11             | P_Asset_Acct                | 0   | 113 | chf                      | P_Asset_Acct                | -10     |              |
      | factAcct_12             | P_COGS_Acct                 | 113 | 0   | chf                      | P_COGS_Acct                 | 10      | 2            |
      | factAcct_21             | P_ExternallyOwnedStock_Acct | 0   | 113 | chf                      | P_ExternallyOwnedStock_Acct | -10     |              |

    And the shipment identified by shipment_1 is reversed

    And after not more than 30s, locate reversal M_InOut
      | M_InOut_ID.Identifier | Reversal_ID.Identifier |
      | reversalShipment_1    | shipment_1             |

    And after not more than 30s, the inout document with identifier reversalShipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR   | CR   | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.NoOfHits |
      | factAcct_11             | P_Asset_Acct                | 0    | -113 | chf                      | P_Asset_Acct                | 10      |              |
      | factAcct_21             | P_ExternallyOwnedStock_Acct | 0    | -113 | chf                      | P_ExternallyOwnedStock_Acct | 10      |              |
      | factAcct_21             | P_COGS_Acct                 | -113 | 0    | chf                      | P_COGS_Acct                 | -10     | 2            |

  @from:cucumber
  @Id:S0322_500
  Scenario: generate shipping notification from sales order, ship less than notified and validate fact accounts generated for the shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | bpartner_1               | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | bpartner_1                   | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR  | CR  | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | P_ExternallyOwnedStock_Acct | 113 | 0   | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | P_Asset_Acct                | 0   | 113 | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_ol_1                           | 5                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_ol_1                           |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_ol_1                           | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference          | processed | docStatus | OPT.PhysicalClearanceDate |
      | o_1                   | bpartner_1               | bpLocationDefault                 | SOO         | EUR          | F            | P               | POReference_21092023 | true      | CO        | 2021-04-20                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed | OPT.PhysicalClearanceDate |
      | s_ol_1                           | 5            | 5            | 10         | false     | 2021-04-20                |

    And after not more than 30s, the inout document with identifier shipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR   | CR   | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.NoOfHits |
      | factAcct_1              | P_Asset_Acct                | 0    | 0    | chf                      | P_Asset_Acct                | 0       |              |
      | factAcct_2              | P_COGS_Acct                 | 0    | 0    | chf                      | P_COGS_Acct                 | 0       |              |
      | factAcct_3              | P_ExternallyOwnedStock_Acct | 0    | 56.5 | chf                      | P_ExternallyOwnedStock_Acct | -5      | 2            |
      | factAcct_4              | P_COGS_Acct                 | 56.5 | 0    | chf                      | P_COGS_Acct                 | 5       |              |
      | factAcct_5              | P_Asset_Acct                | 56.5 | 0    | chf                      | P_Asset_Acct                | 5       |              |

    And the shipment identified by shipment_1 is reversed

    And after not more than 30s, locate reversal M_InOut
      | M_InOut_ID.Identifier | Reversal_ID.Identifier |
      | reversalShipment_1    | shipment_1             |

    And after not more than 30s, the inout document with identifier reversalShipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account                     | DR    | CR    | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.NoOfHits |
      | factAcct_r_1            | P_Asset_Acct                | 0     | 0     | chf                      | P_Asset_Acct                | 0       |              |
      | factAcct_r_2            | P_COGS_Acct                 | 0     | 0     | chf                      | P_COGS_Acct                 | 0       |              |
      | factAcct_r_3            | P_ExternallyOwnedStock_Acct | 0     | -56.5 | chf                      | P_ExternallyOwnedStock_Acct | 5       | 2            |
      | factAcct_r_4            | P_COGS_Acct                 | -56.5 | 0     | chf                      | P_COGS_Acct                 | -5      |              |
      | factAcct_r_5            | P_Asset_Acct                | -56.5 | 0     | chf                      | P_Asset_Acct                | -5      |              |


  Scenario: set accounting schema back to standard
    And update C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.CostingMethod |
      | acctSchema_1               | S                 |