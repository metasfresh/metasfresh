@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F_QtyReservation
@ghActions:run_on_executor5
Feature: Qty Reservation delivery tracking
## Verifies that completing a shipment correctly updates QtyDelivered and Processed on M_QtyReservation records.
## The core logic under test is QtyDeliveredAllocateCommand, triggered by M_InOut_Shipment.updateQtyReservationDelivered.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-03-01T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_Warehouse:
      | Identifier  |
      | warehouse_1 |
    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product_1  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_TU    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_TU           | huPI_TU    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_TU     | huPIV_TU           | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_TU_10PCE          | huPIItem_TU     | product_1    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_1    | 10.00    | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | bp_1       | true       | ps_1               | A            |

  @from:cucumber
  @Id:S_QtyRes_10
  Scenario: Full shipment covers reservation — QtyDelivered=Qty and Processed=true
  ## _Given a sales order line for 10 PCE and a matching M_QtyReservation for 10 PCE
  ## _When the shipment is generated and completed
  ## _Then QtyDelivered on the reservation equals 10 and Processed is true

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_1    | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_1 | order_1    | product_1    | 10         |
    And the order identified by order_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_1 | orderLine_1    | N             |
    And metasfresh contains M_QtyReservations:
      | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_1 | orderLine_1    | product_1    | warehouse_1    | 10 PCE | 1     |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_1    | D            | true                | false       |

    Then validate M_QtyReservations:
      | Identifier       | Qty    | QtyDelivered | Processed |
      | qtyReservation_1 | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_20
  Scenario: Shipment qty less than reservation qty — partial delivery, Processed stays false
  ## _Given a sales order line for 5 PCE and an M_QtyReservation for 10 PCE
  ## _When the shipment for 5 PCE is completed
  ## _Then QtyDelivered on the reservation equals 5 and Processed stays false (5 PCE still outstanding)

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_2    | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_2 | order_2    | product_1    | 5          |
    And the order identified by order_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_2 | orderLine_2    | N             |
    And metasfresh contains M_QtyReservations:
      | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_2 | orderLine_2    | product_1    | warehouse_1    | 10 PCE | 2     |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_2    | D            | true                | false       |

    Then validate M_QtyReservations:
      | Identifier       | Qty    | QtyDelivered | Processed |
      | qtyReservation_2 | 10 PCE | 5 PCE        | false     |


  @from:cucumber
  @Id:S_QtyRes_30
  Scenario: Multiple reservations (OH then PS) for the same order line — allocated in supply-type order
  ## _Given a sales order line for 10 PCE and two M_QtyReservations: OH=6, PS=4
  ## _When the shipment for all 10 PCE is completed
  ## _Then OH reservation gets QtyDelivered=6 (Processed=true) and PS gets QtyDelivered=4 (Processed=true)
  ## _Allocation priority: OH before PS (alphabetical order by SupplyType code)

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_3    | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_3 | order_3    | product_1    | 10         |
    And the order identified by order_3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_3 | orderLine_3    | N             |
    And metasfresh contains M_QtyReservations:
      | Identifier          | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty   | QtyTU | SupplyType |
      | qtyReservation_3_OH | orderLine_3    | product_1    | warehouse_1    | 6 PCE | 1     | OH         |
      | qtyReservation_3_PS | orderLine_3    | product_1    | warehouse_1    | 4 PCE | 1     | PS         |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_3    | D            | true                | false       |

    Then validate M_QtyReservations:
      | Identifier          | Qty   | QtyDelivered | Processed |
      | qtyReservation_3_OH | 6 PCE | 6 PCE        | true      |
      | qtyReservation_3_PS | 4 PCE | 4 PCE        | true      |


  @from:cucumber
  @Id:S_QtyRes_40
  Scenario: Inventory-backed OH reservation — real TU stock, reserved portion tracked, shipment closes reservation
  ## _Given 3 TUs (30 PCE) of on-hand stock via inventory
  ## _And a sales order for 20 PCE
  ## _When an M_QtyReservation is created for 2 TU (20 PCE) against warehouse_1
  ## _Then completing the shipment sets QtyDelivered=20 and Processed=true

    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID |
      | inventory_40   | warehouse_1    | 2026-03-01   | product_1    | 0 PCE   | 30 PCE   | huPIP_TU_10PCE          |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyStockCurrent_AtDate |
      | cp_40      | product_1               | 2026-03-01  | 30                         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order_4    | true    | bp_1          | 2026-03-01  | warehouse_1    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_4 | order_4    | product_1    | 20         |
    And the order identified by order_4 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_4 | orderLine_4    | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | qtyReservation_4 | orderLine_4    | product_1    | warehouse_1    | 20 PCE | 2     | OH         |

    Then validate M_QtyReservations:
      | Identifier       | Qty    | QtyTU | Processed |
      | qtyReservation_4 | 20 PCE | 2     | false     |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_4    | D            | true                | false       |

    Then validate M_QtyReservations:
      | Identifier       | Qty    | QtyDelivered | Processed |
      | qtyReservation_4 | 20 PCE | 20 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_50
  Scenario: OH reservation for the later of two competing orders unlocks its QtyToDeliver
  ## _Given 10 PCE on-hand stock (1 TU) and two sales orders for the same product:
  ## _  order_5A: earlier DatePromised (tier-8 priority by PreparationDate)
  ## _  order_5B: later DatePromised (tier-8 priority, lower than 5A without reservation)
  ## _When an OH reservation (10 PCE, 1 TU) is created for order_5B
  ## _Then order_5B's QtyToDeliver becomes 10 because M_QtyReservation gives it tier-6 priority
  ## _which is higher than the tier-8 PreparationDate-based priority of order_5A

    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID |
      | inventory_50   | warehouse_1    | 2026-03-01   | product_1    | 0 PCE   | 10 PCE   | huPIP_TU_10PCE          |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised        | DeliveryRule | M_Warehouse_ID |
      | order_5A   | true    | bp_1          | 2026-03-01  | 2026-03-05T00:00:00 | A            | warehouse_1    |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_5A | order_5A   | product_1    | 10         |
    And the order identified by order_5A is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised        | DeliveryRule | M_Warehouse_ID |
      | order_5B   | true    | bp_1          | 2026-03-01  | 2026-03-15T00:00:00 | A            | warehouse_1    |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_5B | order_5B   | product_1    | 10         |
    And the order identified by order_5B is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_5A | orderLine_5A   | N             |
      | shipmentSchedule_5B | orderLine_5B   | N             |

    ## Before reservation: order_5A gets QtyToDeliver=10 (in the normal order of priorities, by Date in this case)
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver |
      | shipmentSchedule_5A              | 10               |
      | shipmentSchedule_5B              | 0                |


    ## Reserve the 10 PCE on-hand stock specifically for order_5B
    When metasfresh contains M_QtyReservations:
      | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | qtyReservation_5 | orderLine_5B   | product_1    | warehouse_1    | 10 PCE | 1     | OH         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_5A   |
      | shipmentSchedule_5B   |

    ## After reservation: order_5B gets QtyToDeliver=10 (reservation elevated its priority)
    Then after not more than 5s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver |
      | shipmentSchedule_5A              | 0                |
      | shipmentSchedule_5B              | 10               |


  @from:cucumber
  @Id:S_QtyRes_60
  Scenario: Project propagates from PO receipt to HU attribute; OH reservation from stock references same project
  ## _Given the ProjectValue attribute is storage-relevant and a vendor PO line has C_Project_ID set
  ## _When goods are received into a TU HU via the receipt schedule
  ## _Then the TU carries the ProjectValue attribute matching the project value
  ## _And an OH reservation with C_Project_ID propagates it to the sales order line

    ## 1. Make ProjectValue storage-relevant so updateASIFromProjectId propagates it to received HUs
    Given metasfresh contains M_Attributes:
      | Identifier          | Value        | AttributeValueType | IsStorageRelevant |
      | projectValueAttr_60 | ProjectValue | S                  | Y                 |

    ## 2. PO pricing chain (SOTrx=false) and vendor bpartner without auto-location
    And metasfresh contains M_PricingSystems
      | Identifier |
      | psP_60     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | plP_60     | psP_60             | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plvP_60    | plP_60         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plvP_60                | product_1    | 5.00     | PCE               |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | IsVendor | M_PricingSystem_ID |
      | bp_vendor_60 | true     | psP_60             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipTo | IsBillTo |
      | bp_vendor_60_loc | bp_vendor_60  | true     | true     |

    And metasfresh contains C_Projects:
      | Identifier | Value      |
      | project_60 | PROJECT-60 |

    ## 3. Purchase order with C_Project_ID on the line — triggers updateASIFromProjectId before save
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order_60po | false   | bp_vendor_60  | 2026-03-01  | warehouse_1    |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID |
      | orderLine_60po | order_60po | product_1    | 10         | project_60   |
    And the order identified by order_60po is completed

    ## 4. Receipt schedule is created automatically from the completed PO
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSched_60                 | order_60po            | orderLine_60po            | bp_vendor_60             | bp_vendor_60_loc                  | product_1               | 10         | warehouse_1               |

    ## 5. Generate 1 TU (10 PCE) and receive it — QtyLU=0 means TU-only, no pallet wrapper
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig_60                       | hu_60              | receiptSched_60                 | N               | 0     | N               | 1     | N               | 10          | huPIP_TU_10PCE                     |

    And create material receipt
      | M_ReceiptSchedule_ID.Identifier | M_HU_ID.Identifier | M_InOut_ID.Identifier |
      | receiptSched_60                 | hu_60              | inout_60              |

    ## 6. The received TU must carry the project value as a storage-relevant attribute
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | Value      |
      | hu_60   | ProjectValue         | PROJECT-60 |

    ## 7. Sales order — no project set initially
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_6    | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_6 | order_6    | product_1    | 10         |
    And the order identified by order_6 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_6 | orderLine_6    | N             |

    ## Order line has no project yet
    Then validate C_OrderLine:
      | Identifier  | C_Project_ID |
      | orderLine_6 | null         |

    ## 8. Create OH reservation from on-hand stock with project → propagates C_Project_ID to order line
    When metasfresh contains M_QtyReservations:
      | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType | C_Project_ID |
      | qtyReservation_6 | orderLine_6    | product_1    | warehouse_1    | 10 PCE | 1     | OH         | project_60   |

    ## The service must have propagated C_Project_ID to the order line
    Then validate C_OrderLine:
      | Identifier  | C_Project_ID |
      | orderLine_6 | project_60   |
