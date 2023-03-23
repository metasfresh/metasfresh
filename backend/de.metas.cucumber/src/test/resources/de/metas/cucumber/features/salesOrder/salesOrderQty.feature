@from:cucumber
Feature: Sales order

  Background:
    Given infrastructure and metasfresh are running
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier          | Value               | Name                |
      | product_SO_20062022 | product_SO_20062022 | product_SO_20062022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value |
      | ps_SO      | ps_SO | ps_SO |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO_20062022     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | Value                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_20062022 | customer_SO_20062022 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |


  @from:cucumber
  @Id:S0156_600
  Scenario: Create a new sales order, make a partial shipment and close the shipment schedule. Validate that `QtyOrdered` from order is not overridden and there is no qty allocated for the order in question
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_S0156_600 | true    | customer_SO              | 2022-06-17  | po_ref_S0156_600 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_S0156_600 | order_SO_S0156_600    | product_SO_20062022     | 26         |

    When the order identified by order_SO_S0156_600 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_600    | order_SO_S0156_600    | product_SO_20062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_600 | orderLine_SO_S0156_600    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO_S0156_600            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_600        | orderLine_SO_S0156_600        | 26             | 0                | 26              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_600          | orderLine_SO_S0156_600    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_600          | order_SO_S0156_600        | orderLine_SO_S0156_600        | 0            | 26             | 0                | false                |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | schedule_SO_S0156_600            | 24                        |

    Then 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_S0156_600            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO_S0156_600            | shipment_SO_60        |

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_S0156_600 | orderLine_SO_S0156_600    | N             | 24               |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | Order.Identifier   | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_600    | order_SO_S0156_600 | product_SO_20062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_600            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_600        | orderLine_SO_S0156_600        | 26             | 24               | 2               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_600          | orderLine_SO_S0156_600    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_600          | order_SO_S0156_600        | orderLine_SO_S0156_600        | 24           | 26             | 24               | false                |


  @from:cucumber
  @Id:S0156_700
  Scenario: Create a new sales order, make a partial shipment, close the shipment schedule and the reactivate it. Validate that `QtyOrdered` from order is not overridden and the unshipped qty is allocated for the order in question
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_S0156_700 | true    | customer_SO              | 2022-06-17  | po_ref_S0156_700 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_S0156_700 | order_SO_S0156_700    | product_SO_20062022     | 26         |

    When the order identified by order_SO_S0156_700 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_700    | order_SO_S0156_700    | product_SO_20062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_700 | orderLine_SO_S0156_700    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_700            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_700        | orderLine_SO_S0156_700        | 26             | 0                | 26              | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_700          | orderLine_SO_S0156_700    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_700          | order_SO_S0156_700        | orderLine_SO_S0156_700        | 0            | 26             | 0                | false                |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | schedule_SO_S0156_700            | 24                        |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_S0156_700            | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO_S0156_700            | shipment_SO_S0156_700 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_S0156_700 | orderLine_SO_S0156_700    | N             | 24               |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | Order.Identifier   | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_700    | order_SO_S0156_700 | product_SO_20062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_700            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_700        | orderLine_SO_S0156_700        | 26             | 24               | 2               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_700          | orderLine_SO_S0156_700    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_700          | order_SO_S0156_700        | orderLine_SO_S0156_700        | 24           | 26             | 24               | false                |

    When the M_ShipmentSchedule identified by schedule_SO_S0156_700 is closed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_700    | order_SO_S0156_700    | product_SO_20062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_700 | orderLine_SO_S0156_700    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_700            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_700        | orderLine_SO_S0156_700        | 26             | 24               | 0               | true         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_700          | orderLine_SO_S0156_700    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_700          | order_SO_S0156_700        | orderLine_SO_S0156_700        | 24           | 26             | 24               | true                 |

    And the M_ShipmentSchedule identified by schedule_SO_S0156_700 is reactivated

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_700    | order_SO_S0156_700    | product_SO_20062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_700 | orderLine_SO_S0156_700    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_700            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_700        | orderLine_SO_S0156_700        | 26             | 24               | 2               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_700          | orderLine_SO_S0156_700    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_700          | order_SO_S0156_700        | orderLine_SO_S0156_700        | 24           | 26             | 24               | false                |


  @from:cucumber
  @Id:S0156_800
  Scenario: Create a new sales order and ship all the ordered quantity. Validate that `QtyOrdered` is propagated accordingly
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_S0156_800 | true    | customer_SO              | 2022-06-17  | po_ref_S0156_800 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_S0156_800 | order_SO_S0156_800    | product_SO_20062022     | 26         |

    When the order identified by order_SO_S0156_800 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_800    | order_SO_S0156_800    | product_SO_20062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_800 | orderLine_SO_S0156_800    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_800            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_800        | orderLine_SO_S0156_800        | 26             | 0                | 26              | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_800          | orderLine_SO_S0156_800    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_800          | order_SO_S0156_800        | orderLine_SO_S0156_800        | 0            | 26             | 0                | false                |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_S0156_800            | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO_S0156_800            | shipment_SO_S0156_800 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_S0156_800 | orderLine_SO_S0156_800    | N             | 26               |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | Order.Identifier   | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_800    | order_SO_S0156_800 | product_SO_20062022     | 26         | 26           | 0           | 10    | 0        | EUR          | true      | 0               |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_800            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_800        | orderLine_SO_S0156_800        | 26             | 26               | 0               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_800          | orderLine_SO_S0156_800    | 26           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_800          | order_SO_S0156_800        | orderLine_SO_S0156_800        | 26           | 26             | 26               | false                |


  @from:cucumber
  @Id:S0156_900
  Scenario: Create a new sales order and ship the ordered quantity is two shipments. Validate that `QtyOrdered` is propagated accordingly and there is no qty allocated for the order in question
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_S0156_900 | true    | customer_SO              | 2022-06-17  | po_ref_S0156_900 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_S0156_900 | order_SO_S0156_900    | product_SO_20062022     | 26         |

    When the order identified by order_SO_S0156_900 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_900    | order_SO_S0156_900    | product_SO_20062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_900 | orderLine_SO_S0156_900    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_900            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_900        | orderLine_SO_S0156_900        | 26             | 0                | 26              | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_900          | orderLine_SO_S0156_900    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_900          | order_SO_S0156_900        | orderLine_SO_S0156_900        | 0            | 26             | 0                | false                |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | schedule_SO_S0156_900            | 24                        |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_S0156_900            | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier   |
      | schedule_SO_S0156_900            | shipment_SO_S0156_900_1 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_S0156_900 | orderLine_SO_S0156_900    | N             | 24               |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | Order.Identifier   | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_900    | order_SO_S0156_900 | product_SO_20062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_900            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_900        | orderLine_SO_S0156_900        | 26             | 24               | 2               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_900          | orderLine_SO_S0156_900    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_900          | order_SO_S0156_900        | orderLine_SO_S0156_900        | 24           | 26             | 24               | false                |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | schedule_SO_S0156_900            | 2                         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_S0156_900            | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier   | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | schedule_SO_S0156_900            | shipment_SO_S0156_900_2 | shipment_SO_S0156_900_1                 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_S0156_900 | orderLine_SO_S0156_900    | N             | 26               |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | Order.Identifier   | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_900    | order_SO_S0156_900 | product_SO_20062022     | 26         | 26           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_900 | orderLine_SO_S0156_900    | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_900            | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_900        | orderLine_SO_S0156_900        | 26             | 26               | 0               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_900          | orderLine_SO_S0156_900    | 26           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_900          | order_SO_S0156_900        | orderLine_SO_S0156_900        | 26           | 26             | 26               | false                |


  @from:cucumber
  @Id:S0156_1000
  Scenario: Create a new sales order and ship more than the ordered quantity. Validate that `QtyOrdered` is propagated accordingly
    Given metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_S0156_1000 | true    | customer_SO              | 2022-06-17  | po_ref_S0156_1000 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier              | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_S0156_1000 | order_SO_S0156_1000   | product_SO_20062022     | 26         |

    When the order identified by order_SO_S0156_1000 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_1000   | order_SO_S0156_1000   | product_SO_20062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_S0156_1000 | orderLine_SO_S0156_1000   | N             |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_1000           | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_1000       | orderLine_SO_S0156_1000       | 26             | 0                | 26              | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_1000         | orderLine_SO_S0156_1000   | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_1000         | order_SO_S0156_1000       | orderLine_SO_S0156_1000       | 0            | 26             | 0                | false                |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | schedule_SO_S0156_1000           | 28                        |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_S0156_1000           | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier  |
      | schedule_SO_S0156_1000           | shipment_SO_S0156_1000 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_S0156_1000 | orderLine_SO_S0156_1000   | N             | 28               |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | Order.Identifier    | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_SO_S0156_1000   | order_SO_S0156_1000 | product_SO_20062022     | 26         | 28           | 0           | 10    | 0        | EUR          | true      | 0               |
    And validate M_ShipmentSchedule:
      | M_ShipmentSchedule_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed |
      | schedule_SO_S0156_1000           | customer_SO              | customerLocation_SO               | customer_SO                 | customerLocation_SO         | product_SO_20062022     | PENDING      | order_SO_S0156_1000       | orderLine_SO_S0156_1000       | 26             | 28               | 0               | false        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_S0156_1000         | orderLine_SO_S0156_1000   | 28           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_S0156_1000         | order_SO_S0156_1000       | orderLine_SO_S0156_1000       | 28           | 26             | 28               | false                |
