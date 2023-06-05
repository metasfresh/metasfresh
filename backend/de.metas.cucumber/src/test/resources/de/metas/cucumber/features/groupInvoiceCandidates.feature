@from:cucumber
Feature: Group invoices and credit memos into a single document

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @Id:S0242_100
  Scenario: 2 invoice candidates (both sales); 1 x credit memo; 1 x invoice candidate; invoicing pool setup active, credit memo amt > invoice amt => one invoice with 2 lines, DocType=CreditMemo
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_1 | product_SO_12012023_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_1 | ps_SO_12012023_1 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_1 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_1 | customer_SO_12012023_1 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                      | Name                       | OPT.IsIssueWarehouse | OPT.IsQuarantineWarehouse | OPT.IsQualityReturnWarehouse |
      | returnWarehouse           | returnWarehouse_12012023_1 | returnWarehouse_12012023_1 | true                 | true                      | true                         |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                    | M_Warehouse_ID.Identifier |
      | returnLocator           | returnLocator_12012023_1 | returnWarehouse           |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name            |
      | dt_si                   | Ausgangsrechnung    |
      | dt_cm                   | Gutschrift          |
      | dt_cr                   | Kundenwarenrückgabe |
      | dt_so                   | Standardauftrag     |

    And metasfresh contains C_DocType_Invoicing_Pool:
      | C_DocType_Invoicing_Pool_ID.Identifier | Name            | Positive_Amt_C_DocType_ID.Identifier | Negative_Amt_C_DocType_ID.Identifier | IsSOTrx | IsOnDistinctICTypes |
      | ip_1                                   | test_12012023_1 | dt_si                                | dt_cm                                | true    | true                |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | dt_si                   | ip_1                                       | null                               |
      | dt_cm                   | ip_1                                       | null                               |
      | dt_so                   | null                                       | dt_si                              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_1 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 8          |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 8              | 0                | 8               | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | order_SO                  | orderLine_SO                  | 0            | 8              | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 8                |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo      |
      | inventory                 | 2022-06-16T00:00:00Z | inventoryDocNo2 |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | inventory                 | inventoryLine                 | product_SO              | 0       | 10       |

    And complete inventory with inventoryIdentifier 'inventory'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine                 | createdCU          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_CM   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_1 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_CM | order_CM              | product_SO              | 10         |

    When the order identified by order_CM is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_CM | orderLine_CM              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_CM                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_CM                  | orderLine_CM                  | 10             | 0                | 10              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_CM                    | orderLine_CM              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_CM                    | dt_si                       | order_CM                  | orderLine_CM                  | 0            | 10             | 0                | false                |

    And validate M_HUs are available to pick for shipmentSchedule identified by schedule_CM
      | M_HU_ID.Identifier |
      | createdCU          |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdCU          | schedule_CM                      | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdCU          | schedule_CM                      |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CM                      | P            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_CM                      | shipment_CM           |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_CM | orderLine_CM              | N             | 10               |

    And return hu from customer
      | M_HU_ID.Identifier |
      | createdCU          |

    And after not more than 60s, Customer Return is found:
      | M_InOut_ID.Identifier | C_Order_ID.Identifier | C_DocType_ID.Identifier |
      | customerReturn        | order_CM              | dt_cr                   |

    And after not more than 60s, credit memo candidates are found:
      | C_Invoice_Candidate_ID.Identifier | M_InOut_ID.Identifier |
      | creditMemo                        | customerReturn        |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered |
      | creditMemo                        | dt_cm                       | order_CM                  | -10          | -10            | -10              |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO,creditMemo         |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | creditMemo                        |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_1 | 30 Tage netto | true      | CO        | 4.76           | dt_cm                       |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 8           | true      | 8              |
      | invoiceLine_2               | invoice_1               | product_SO              | -10         | true      | -10            |

    And update M_Warehouse:
      | M_Warehouse_ID.Identifier | OPT.IsQuarantineWarehouse | OPT.IsQualityReturnWarehouse | OPT.IsIssueWarehouse | OPT.IsInTransit |
      | returnWarehouse           | false                     | false                        | false                | false           |

  @from:cucumber
  @Id:S0242_200
  Scenario: 2 invoice candidates (both sales); 1 x credit memo; 1 x invoice candidate; invoicing pool setup active, credit memo < invoice => one invoice with 2 lines, DocType=SalesInvoice
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_2 | product_SO_12012023_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_2 | ps_SO_12012023_2 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_2 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_2 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_2 | customer_SO_12012023_2 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                      | Name                       | OPT.IsIssueWarehouse | OPT.IsQuarantineWarehouse | OPT.IsQualityReturnWarehouse |
      | returnWarehouse           | returnWarehouse_12012023_1 | returnWarehouse_12012023_1 | true                 | true                      | true                         |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                    | M_Warehouse_ID.Identifier |
      | returnLocator           | returnLocator_12012023_1 | returnWarehouse           |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name            |
      | dt_si                   | Ausgangsrechnung    |
      | dt_cm                   | Gutschrift          |
      | dt_cr                   | Kundenwarenrückgabe |
      | dt_so                   | Standardauftrag     |

    And metasfresh contains C_DocType_Invoicing_Pool:
      | C_DocType_Invoicing_Pool_ID.Identifier | Name            | Positive_Amt_C_DocType_ID.Identifier | Negative_Amt_C_DocType_ID.Identifier | IsSOTrx | IsOnDistinctICTypes |
      | ip_1                                   | test_12012023_2 | dt_si                                | dt_cm                                | true    | true                |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | dt_si                   | ip_1                                       | dt_cr                              |
      | dt_cm                   | ip_1                                       | dt_cr                              |
      | dt_so                   | null                                       | dt_si                              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_2 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 12         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 12             | 0                | 12              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | dt_si                       | order_SO                  | orderLine_SO                  | 0            | 12             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 12               |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo      |
      | inventory                 | 2022-06-16T00:00:00Z | inventoryDocNo2 |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | inventory                 | inventoryLine                 | product_SO              | 0       | 10       |

    And complete inventory with inventoryIdentifier 'inventory'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine                 | createdCU          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_CM   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_2 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_CM | order_CM              | product_SO              | 10         |

    When the order identified by order_CM is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_CM | orderLine_CM              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_CM                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_CM                  | orderLine_CM                  | 10             | 0                | 10              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_CM                    | orderLine_CM              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_CM                    | order_CM                  | orderLine_CM                  | 0            | 10             | 0                | false                |

    And validate M_HUs are available to pick for shipmentSchedule identified by schedule_CM
      | M_HU_ID.Identifier |
      | createdCU          |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdCU          | schedule_CM                      | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdCU          | schedule_CM                      |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CM                      | P            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_CM                      | shipment_CM           |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_CM | orderLine_CM              | N             | 10               |

    And return hu from customer
      | M_HU_ID.Identifier |
      | createdCU          |

    And after not more than 60s, Customer Return is found:
      | M_InOut_ID.Identifier | C_Order_ID.Identifier | C_DocType_ID.Identifier |
      | customerReturn        | order_CM              | dt_cr                   |

    And after not more than 60s, credit memo candidates are found:
      | C_Invoice_Candidate_ID.Identifier | M_InOut_ID.Identifier |
      | creditMemo                        | customerReturn        |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered |
      | creditMemo                        | dt_cm                       | order_CM                  | -10          | -10            | -10              |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO,creditMemo         |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | creditMemo                        |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_2 | 30 Tage netto | true      | CO        | 4.76           | dt_si                       |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 12          | true      | 12             |
      | invoiceLine_2               | invoice_1               | product_SO              | -10         | true      | -10            |

    And update M_Warehouse:
      | M_Warehouse_ID.Identifier | OPT.IsQuarantineWarehouse | OPT.IsQualityReturnWarehouse | OPT.IsIssueWarehouse | OPT.IsInTransit |
      | returnWarehouse           | false                     | false                        | false                | false           |

  @from:cucumber
  @Id:S0242_300
  Scenario: 2 invoice candidates(both sales); 1 x credit memo; 1 x invoice candidate; no invoicing pool setup => 2 invoices
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_3 | product_SO_12012023_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_3 | ps_SO_12012023_3 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_3 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_3 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_3 | customer_SO_12012023_3 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                      | Name                       | OPT.IsIssueWarehouse | OPT.IsQuarantineWarehouse | OPT.IsQualityReturnWarehouse |
      | returnWarehouse           | returnWarehouse_12012023_1 | returnWarehouse_12012023_1 | true                 | true                      | true                         |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                    | M_Warehouse_ID.Identifier |
      | returnLocator           | returnLocator_12012023_1 | returnWarehouse           |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name            |
      | dt_si                   | Ausgangsrechnung    |
      | dt_cm                   | Gutschrift          |
      | dt_cr                   | Kundenwarenrückgabe |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier |
      | dt_si                   | null                                       |
      | dt_cm                   | null                                       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_3 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 12         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 12             | 0                | 12              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | dt_si                       | order_SO                  | orderLine_SO                  | 0            | 12             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 12               |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo      |
      | inventory                 | 2022-06-16T00:00:00Z | inventoryDocNo2 |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | inventory                 | inventoryLine                 | product_SO              | 0       | 10       |

    And complete inventory with inventoryIdentifier 'inventory'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine                 | createdCU          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_CM   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_3 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_CM | order_CM              | product_SO              | 10         |

    When the order identified by order_CM is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_CM | orderLine_CM              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_CM                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_CM                  | orderLine_CM                  | 10             | 0                | 10              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_CM                    | orderLine_CM              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_CM                    | dt_si                       | order_CM                  | orderLine_CM                  | 0            | 10             | 0                | false                |

    And validate M_HUs are available to pick for shipmentSchedule identified by schedule_CM
      | M_HU_ID.Identifier |
      | createdCU          |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdCU          | schedule_CM                      | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdCU          | schedule_CM                      |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CM                      | P            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_CM                      | shipment_CM           |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_CM | orderLine_CM              | N             | 10               |

    And return hu from customer
      | M_HU_ID.Identifier |
      | createdCU          |

    And after not more than 60s, Customer Return is found:
      | M_InOut_ID.Identifier | C_Order_ID.Identifier | C_DocType_ID.Identifier |
      | customerReturn        | order_CM              | dt_cr                   |

    And after not more than 60s, credit memo candidates are found:
      | C_Invoice_Candidate_ID.Identifier | M_InOut_ID.Identifier |
      | creditMemo                        | customerReturn        |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.C_DocType_ID.Identifier |
      | creditMemo                        | order_CM                  | -10          | -10            | -10              | dt_cm                       |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO,creditMemo         |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO                    |
      | invoice_2               | creditMemo                        |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_3 | 30 Tage netto | true      | CO        | 28.56          | dt_si                       |
      | invoice_2               | customer_SO              | customerLocation_SO               | po_ref_12012023_3 | 30 Tage netto | true      | CO        | 23.8           | dt_cm                       |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 12          | true      | 12             |
      | invoiceLine_2               | invoice_2               | product_SO              | -10         | true      | -10            |

    And update M_Warehouse:
      | M_Warehouse_ID.Identifier | OPT.IsQuarantineWarehouse | OPT.IsQualityReturnWarehouse | OPT.IsIssueWarehouse | OPT.IsInTransit |
      | returnWarehouse           | false                     | false                        | false                | false           |

  @from:cucumber
  @Id:S0242_400
  Scenario: 2 invoice candidates(both sales); 2 x invoice candidate; no invoicing pool setup => 1 invoice with 2 lines, DocType=SalesInvoice
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_4 | product_SO_12012023_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_4 | ps_SO_12012023_4 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_4 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_4 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_4 | customer_SO_12012023_4 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name         |
      | dt_si                   | Ausgangsrechnung |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 10         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 10             | 0                | 10              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | dt_si                       | order_SO                  | orderLine_SO                  | 0            | 10             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 10               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_2 | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_2 | order_SO_2            | product_SO              | 12         |

    When the order identified by order_SO_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_2 | orderLine_SO_2            | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO_2                    | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO_2                | orderLine_SO_2                | 12             | 0                | 12              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_2                  | orderLine_SO_2            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_2                  | dt_si                       | order_SO_2                | orderLine_SO_2                | 0            | 12             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_2                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO_2                    | shipment_SO_2         |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_2 | orderLine_SO_2            | N             | 12               |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.C_DocType_ID.Identifier |
      | invoiceCand_SO_2                  | order_SO_2                | 12           | 12             | 12               | dt_si                       |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO,invoiceCand_SO_2   |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO_2                  |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_4 | 30 Tage netto | true      | CO        | 52.36          | dt_si                       |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 12          | true      | 12             |
      | invoiceLine_2               | invoice_1               | product_SO              | 10          | true      | 10             |

  @from:cucumber
  @Id:S0242_510
  Scenario: 1 invoice candidates(sales); 1 x invoice candidate; no invoicing pool setup, IC has docTypeInvoiceID = A => 1 invoice with 1 line, DocType=A
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_4 | product_SO_12012023_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_4 | ps_SO_12012023_4 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_4 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_4 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_4 | customer_SO_12012023_4 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name                    |
      | A                       | Ausgangsrechnung (indirekt) |
      | dt_so                   | Standardauftrag             |


    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | A                       | null                                       | null                               |
      | dt_so                   | null                                       | A                                  |


    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 10         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 10             | 0                | 10              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | A                           | order_SO                  | orderLine_SO                  | 0            | 10             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 10               |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.NetAmtToInvoice |
      | invoiceCand_SO                    | 20                  |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO                    |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO                    |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_4 | 30 Tage netto | true      | CO        | 23.80          | A                           |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 10          | true      | 10             |


  @from:cucumber
  @Id:S0242_520
  Scenario: 1 invoice candidates(sales); 1 x invoice candidate; invoice pool exists, ic and invoice pool have the same invoiceDocTypeId = A => 1 invoice with 1 line, DocType=A
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_4 | product_SO_12012023_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_4 | ps_SO_12012023_4 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_4 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_4 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_4 | customer_SO_12012023_4 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name                    |
      | A                       | Ausgangsrechnung (indirekt) |
      | dt_cm                   | Gutschrift                  |
      | dt_so                   | Standardauftrag             |

    And metasfresh contains C_DocType_Invoicing_Pool:
      | C_DocType_Invoicing_Pool_ID.Identifier | Name            | Positive_Amt_C_DocType_ID.Identifier | Negative_Amt_C_DocType_ID.Identifier | IsSOTrx | IsOnDistinctICTypes |
      | ip_1                                   | test_12012023_2 | A                                    | dt_cm                                | true    | true                |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | A                       | ip_1                                       | null                               |
      | dt_so                   | null                                       | A                                  |


    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 10         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 10             | 0                | 10              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | A                           | order_SO                  | orderLine_SO                  | 0            | 10             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 10               |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.NetAmtToInvoice |
      | invoiceCand_SO                    | 20                  |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO                    |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO                    |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_4 | 30 Tage netto | true      | CO        | 23.80          | A                           |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 10          | true      | 10             |


  @from:cucumber
  @Id:S0242_530
  Scenario: 1 invoice candidates(sales); 1 x invoice candidate;  IC has invoiceDocTypeID =C; invoicing pool is set, it has doctypeId = D, => 1 invoice with 1 line, DocType=C
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_4 | product_SO_12012023_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_4 | ps_SO_12012023_4 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_4 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_4 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_4 | customer_SO_12012023_4 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name                    |
      | C                       | Ausgangsrechnung (indirekt) |
      | D                       | Ausgangsrechnung            |
      | dt_cm                   | Gutschrift                  |
      | dt_so                   | Standardauftrag             |

    And metasfresh contains C_DocType_Invoicing_Pool:
      | C_DocType_Invoicing_Pool_ID.Identifier | Name            | Positive_Amt_C_DocType_ID.Identifier | Negative_Amt_C_DocType_ID.Identifier | IsSOTrx | IsOnDistinctICTypes |
      | Y                                      | test_12012023_2 | D                                    | dt_cm                                | true    | true                |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | C                       | Y                                          | null                               |
      | dt_so                   | null                                       | C                                  |


    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 10         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 10             | 0                | 10              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | C                           | order_SO                  | orderLine_SO                  | 0            | 10             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 10               |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.NetAmtToInvoice |
      | invoiceCand_SO                    | 20                  |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO                    |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO                    |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_4 | 30 Tage netto | true      | CO        | 23.80          | C                           |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 10          | true      | 10             |


  @from:cucumber
  @Id:S0242_540
  Scenario: 2 invoice candidates(both sales); 2 x invoice candidate; both ICs have invoiceDocType = A , A has pool with invoiceDocType = B, IsOnDistinctICTypes = true => 1 invoice with 2 lines, DocType=A
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_4 | product_SO_12012023_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_4 | ps_SO_12012023_4 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_4 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_4 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_4 | customer_SO_12012023_4 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name                    |
      | A                       | Ausgangsrechnung (indirekt) |
      | B                       | Ausgangsrechnung            |
      | dt_cm                   | Gutschrift                  |



    And metasfresh contains C_DocType_Invoicing_Pool:
      | C_DocType_Invoicing_Pool_ID.Identifier | Name            | Positive_Amt_C_DocType_ID.Identifier | Negative_Amt_C_DocType_ID.Identifier | IsSOTrx | IsOnDistinctICTypes |
      | X                                      | test_12012023_2 | B                                    | dt_cm                                | true    | true                |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | A                       | X                                          | null                               |
      | B                       | null                                       | null                               |



    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 10         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 10             | 0                | 10              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | A                           | order_SO                  | orderLine_SO                  | 0            | 10             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 10               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_2 | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_2 | order_SO_2            | product_SO              | 12         |

    When the order identified by order_SO_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_2 | orderLine_SO_2            | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO_2                    | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO_2                | orderLine_SO_2                | 12             | 0                | 12              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_2                  | orderLine_SO_2            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_2                  | A                           | order_SO_2                | orderLine_SO_2                | 0            | 12             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_2                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO_2                    | shipment_SO_2         |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_2 | orderLine_SO_2            | N             | 12               |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.C_DocType_ID.Identifier |
      | invoiceCand_SO_2                  | order_SO_2                | 12           | 12             | 12               | A                           |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO,invoiceCand_SO_2   |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO_2                  |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_4 | 30 Tage netto | true      | CO        | 52.36          | A                           |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 12          | true      | 12             |
      | invoiceLine_2               | invoice_1               | product_SO              | 10          | true      | 10             |


  @from:cucumber
  @Id:S0242_550
  Scenario: 2 invoice candidates(both sales); 2 x invoice candidate; both ICs have invoiceDocType = A , A has pool with invoiceDocType = B, IsOnDistinctICTypes = false => 1 invoice with 2 lines, DocType=B
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                  |
      | product_SO | product_SO_12012023_4 | product_SO_12012023_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_SO      | ps_SO_12012023_4 | ps_SO_12012023_4 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_name_12012023_4 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO_12012023_4 | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_SO              | 2.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12012023_4 | customer_SO_12012023_4 | N            | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2006202222221 | customer_SO              | Y                   | Y                   |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.Name                    |
      | A                       | Ausgangsrechnung (indirekt) |
      | B                       | Ausgangsrechnung            |
      | dt_cm                   | Gutschrift                  |



    And metasfresh contains C_DocType_Invoicing_Pool:
      | C_DocType_Invoicing_Pool_ID.Identifier | Name            | Positive_Amt_C_DocType_ID.Identifier | Negative_Amt_C_DocType_ID.Identifier | IsSOTrx | IsOnDistinctICTypes |
      | X                                      | test_12012023_2 | B                                    | dt_cm                                | true    | false               |

    And update C_DocType:
      | C_DocType_ID.Identifier | OPT.C_DocType_Invoicing_Pool_ID.Identifier | OPT.C_DocTypeInvoice_ID.Identifier |
      | A                       | X                                          | null                               |
      | B                       | null                                       | null                               |



    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO   | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_SO              | 10         |

    When the order identified by order_SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO | orderLine_SO              | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO                      | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO                  | orderLine_SO                  | 10             | 0                | 10              | false        | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO                    | orderLine_SO              | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO                    | A                           | order_SO                  | orderLine_SO                  | 0            | 10             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO                      | shipment_SO           |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO | orderLine_SO              | N             | 10               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_SO_2 | true    | customer_SO              | 2022-06-17  | po_ref_12012023_4 | ps_SO                             | customerLocation_SO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_2 | order_SO_2            | product_SO              | 12         |

    When the order identified by order_SO_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_SO_2 | orderLine_SO_2            | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyReserved | OPT.IsClosed | OPT.Processed |
      | schedule_SO_2                    | customer_SO                  | customerLocation_SO                   | customer_SO                     | customerLocation_SO             | product_SO                  | PENDING          | order_SO_2                | orderLine_SO_2                | 12             | 0                | 12              | false        | false         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_SO_2                  | orderLine_SO_2            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_SO_2                  | A                           | order_SO_2                | orderLine_SO_2                | 0            | 12             | 0                | false                |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_SO_2                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_SO_2                    | shipment_SO_2         |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute | OPT.QtyDelivered |
      | schedule_SO_2 | orderLine_SO_2            | N             | 12               |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.C_DocType_ID.Identifier |
      | invoiceCand_SO_2                  | order_SO_2                | 12           | 12             | 12               | A                           |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_SO,invoiceCand_SO_2   |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_SO_2                  |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | customer_SO              | customerLocation_SO               | po_ref_12012023_4 | 30 Tage netto | true      | CO        | 52.36          | B                           |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered |
      | invoiceLine_1               | invoice_1               | product_SO              | 12          | true      | 12             |
      | invoiceLine_2               | invoice_1               | product_SO              | 10          | true      | 10             |


