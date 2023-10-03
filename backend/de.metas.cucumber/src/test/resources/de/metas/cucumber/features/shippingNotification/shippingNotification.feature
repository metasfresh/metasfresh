@from:cucumber
Feature: Shipping Notifications

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |
    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_1                | 001   |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value                 |
      | locator                 | warehouseStd              | LocatorValue_21092023 |
    And metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_21092023_1 |
      | p_2        | salesProduct_21092023_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.Description                     | OPT.IsActive |
      | ps_1       | pricing_system_name_21092023 | pricing_system_value_21092023 | pricing_system_description_21092023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15112022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_21092023 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                 | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | endcustomer_1 | Endcustomer_21092023 | ps_1                          | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault |
      | bpLocationDefault | 0123456789012 | endcustomer_1            | Y                   |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                      | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsBillToContact_Default |
      | bpContactDefault      | bpContactDefault_21092023 | endcustomer_1                | bpLocationDefault                     | Y                           |
    And metasfresh contains C_Auction:
      | Identifier | Name     | Date       |
      | Auction_1  | Auction1 | 2023-09-21 |
    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |
    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | elementValue_1               | element_1               | 90000 |
      | elementValue_2               | element_1               | 90055 |
    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | chf                      | 318               |


  @from:cucumber
  Scenario: we can generate shipping notifications for sales order and data is passed correctly to the notification and lines
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | endcustomer_1            | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
      | s_ol_2     | ol_2                      | N             |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | endcustomer_1                | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
      | shippingNotificationLine_21092023_2       | shippingNotification_21092023         | s_ol_2                           | p_2                     | 10          |
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference          | processed | docStatus | OPT.PhysicalClearanceDate |
      | o_1                   | endcustomer_1            | bpLocationDefault                 | SOO         | EUR          | F            | P               | POReference_21092023 | true      | CO        | 2021-04-20                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed | OPT.PhysicalClearanceDate |
      | s_ol_1                           | 10           | 0            | 10         | false     | 2021-04-20                |
      | s_ol_2                           | 10           | 0            | 10         | false     | 2021-04-20                |
    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_3              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_2                         | locator                     |
      | factAcct_4              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_2                         | locator                     |

  @from:cucumber
  Scenario: we can generate 2 shipping notifications for sales order and the previous one is reversed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | endcustomer_1            | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
      | s_ol_2     | ol_2                      | N             |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | endcustomer_1                | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
      | shippingNotificationLine_21092023_2       | shippingNotification_21092023         | s_ol_2                           | p_2                     | 10          |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-19            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023_2       | o_1                   | CO        | endcustomer_1                | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1_2     | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
      | shippingNotificationLine_21092023_2_2     | shippingNotification_21092023         | s_ol_2                           | p_2                     | 10          |
    And after not more than 30s, locate reversal M_Shipping_Notifications
      | M_Shipping_Notification_ID.Identifier | Reversal_ID.Identifier        |
      | reversalShippingNotification_21092023 | shippingNotification_21092023 |
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference          | processed | docStatus | OPT.PhysicalClearanceDate |
      | o_1                   | endcustomer_1            | bpLocationDefault                 | SOO         | EUR          | F            | P               | POReference_21092023 | true      | CO        | 2021-04-19                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed | OPT.PhysicalClearanceDate |
      | s_ol_1                           | 10           | 0            | 10         | false     | 2021-04-19                |
      | s_ol_2                           | 10           | 0            | 10         | false     | 2021-04-19                |
    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_3              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_2                         | locator                     |
      | factAcct_4              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_2                         | locator                     |
    And after not more than 30s, the shippingNotification document with identifier reversalShippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_5              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_6              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_7              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | -10     | bpLocationDefault                     | p_2                         | locator                     |
      | factAcct_8              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | 10      | bpLocationDefault                     | p_2                         | locator                     |

  @from:cucumber
  Scenario: we can generate shipping notifications for sales order and after the order is voided, the notification is reversed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | endcustomer_1            | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
      | s_ol_2     | ol_2                      | N             |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | endcustomer_1                | bpLocationDefault                     | bpContactDefault          | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
      | shippingNotificationLine_21092023_2       | shippingNotification_21092023         | s_ol_2                           | p_2                     | 10          |
    When the order identified by o_1 is voided
    And after not more than 30s, locate reversal M_Shipping_Notifications
      | M_Shipping_Notification_ID.Identifier | Reversal_ID.Identifier        |
      | reversalShippingNotification_21092023 | shippingNotification_21092023 |
    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_2              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_3              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDefault                     | p_2                         | locator                     |
      | factAcct_4              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDefault                     | p_2                         | locator                     |
    And after not more than 30s, the shippingNotification document with identifier reversalShippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_5              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | -10     | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_6              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | 10      | bpLocationDefault                     | p_1                         | locator                     |
      | factAcct_7              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | -10     | bpLocationDefault                     | p_2                         | locator                     |
      | factAcct_8              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | 10      | bpLocationDefault                     | p_2                         | locator                     |

  @from:cucumber
  Scenario: we can generate shipping notifications for sales order with dropship partner data and it will be passed to the notification
    And metasfresh contains C_BPartners without locations:
      | Identifier             | Name                          | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | endcustomer_dropship_1 | Endcustomer_Dropship_21092023 | ps_1                          | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault |
      | bpLocationDropship | 0123456789013 | endcustomer_dropship_1   | Y                   |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsBillToContact_Default |
      | bpContactDropship     | bpContactDropship_21092023 | endcustomer_dropship_1       | bpLocationDropship                    | Y                           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | DateOrdered | OPT.AD_Org_ID.Identifier | OPT.POReference      | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.Description            | OPT.C_Auction_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier | OPT.IsDropShip | OPT.DropShip_BPartner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.DropShip_User_ID.Identifier | OPT.DeliveryRule |
      | o_1        | true    | endcustomer_1            | bpLocationDefault                     | bpContactDefault          | 2021-04-17  | org_1                    | POReference_21092023 | harvesting_calendar                     | year_2023                         | order description 21092023 | Auction_1                   | warehouseStd                  | locator                     | Y              | endcustomer_dropship_1              | bpLocationDropship                  | bpContactDropship               | F                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
      | s_ol_2     | ol_2                      | N             |
    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | o_1                   | 2021-04-20            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.DropShip_BPartner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.DropShip_User_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | OPT.C_Auction_ID.Identifier | OPT.POReference      | OPT.Description            |
      | shippingNotification_21092023         | o_1                   | CO        | endcustomer_dropship_1              | bpLocationDropship                  | bpContactDropship               | org_1                | locator                 | harvesting_calendar                 | year_2023                     | Auction_1                   | POReference_21092023 | order description 21092023 |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_21092023_1       | shippingNotification_21092023         | s_ol_1                           | p_1                     | 10          |
      | shippingNotificationLine_21092023_2       | shippingNotification_21092023         | s_ol_2                           | p_2                     | 10          |
    And after not more than 30s, the shippingNotification document with identifier shippingNotification_21092023 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName   | OPT.Qty | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | factAcct_1              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDropship                    | p_1                         | locator                     |
      | factAcct_2              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDropship                    | p_1                         | locator                     |
      | factAcct_3              | elementValue_2 | 0  | 0  | chf                      | P_ExternallyOwnedStock_Acct | 10      | bpLocationDropship                    | p_2                         | locator                     |
      | factAcct_4              | elementValue_1 | 0  | 0  | chf                      | P_Asset_Acct                | -10     | bpLocationDropship                    | p_2                         | locator                     |