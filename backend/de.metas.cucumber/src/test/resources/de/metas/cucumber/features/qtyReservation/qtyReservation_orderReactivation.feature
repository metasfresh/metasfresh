@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F_QtyReservation
@ghActions:run_on_executor5
Feature: Qty Reservation — reconcile reservation to ordered qty on order reactivation
# Validates that a qty reservation is reconciled (shrunk) to the order line's ordered
# quantity when a completed sales order is reactivated, its line quantity reduced, and
# the order re-completed.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-03-15T13:30:13+01:00[Europe/Berlin]
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
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | bp_1       | true       | ps_1               | A            |


  @from:cucumber
  @Id:S_QtyRes_300
  Scenario: Reservation is reconciled to reduced ordered qty after order reactivation
  # Given a product with 100 PCE (10 TUs) on-hand stock and a sales order for 100 PCE
  # And an M_QtyReservation for 100 PCE
  # When shipment is generated for the full 100, then reversed (reservation restored)
  # And the order is reactivated, the order line reduced to 75 PCE, and the order re-completed
  # Then the reservation is reconciled down to 75 PCE (shrink-on-complete)

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    # 100 PCE on-hand stock (10 complete TUs of 10 PCE each → 10 HUs)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID | M_HU_ID2 | M_HU_ID3 | M_HU_ID4 | M_HU_ID5 | M_HU_ID6 | M_HU_ID7 | M_HU_ID8 | M_HU_ID9 | M_HU_ID10 |
      | inventory      | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 100 PCE  | huPIP_10PCE             | hu1     | hu2      | hu3      | hu4      | hu5      | hu6      | hu7      | hu8      | hu9      | hu10      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 100        | huPIP_10PCE             |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier  | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty     | QtyTU |
      | reservation | orderLine      | product      | warehouse_1    | 100 PCE | 10    |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |

    # Reservation is fully delivered after the shipment
    And validate M_QtyReservations:
      | Identifier  | Qty     | QtyDelivered | Processed |
      | reservation | 100 PCE | 100 PCE      | true      |

    # Reverse the shipment — reservation is restored (QtyDelivered back to 0, Processed=false)
    And the shipment identified by shipment is reversed as shipmentReversal
    And validate M_QtyReservations:
      | Identifier  | Qty     | QtyDelivered | Processed |
      | reservation | 100 PCE | 0 PCE        | false     |

    # Customer reduces the order: reactivate, cut the line from 100 to 75 PCE, re-complete
    And the order identified by order is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | orderLine                 | 75             | 75             |
    And the order identified by order is completed

    # Reconcile assertion: the reservation is shrunk to the new ordered qty
    And validate M_QtyReservations:
      | Identifier  | Qty    |
      | reservation | 75 PCE |
