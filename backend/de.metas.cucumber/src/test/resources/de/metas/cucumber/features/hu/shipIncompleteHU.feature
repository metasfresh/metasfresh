@ghActions:run_on_executor5
Feature: Ship incomplete HUs
# This feature was created to investigate a scenario in which multiple M_InOutLines with seemingly same attributes are created when picking on the flyfor a shipment schedule.
# However, I was not yet able to reproduce the scenario in which said duplication is happening.
# But decided to keep the feature for future usage & documentation of expected behavior.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID      | M_Warehouse_ID | Value      | REST.Context.QRCode |
      | locatorHauptlager | warehouseStd   | Hauptlager | locatorQRCode       |
    And metasfresh contains M_Products:
      | Identifier |
      | huProduct  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID         | Name            |
      | huPackingTU        | huPackingTU     |
      | huPackingVirtualPI | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID         | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU   | huPackingTU        | packingVersionTU | TU          | Y         |
      | packingVersionCU   | huPackingVirtualPI | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemTU      | packingVersionTU   | 0   | MI       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | huProductTU             | huPiItemTU      | huProduct    | 10  | 2022-01-01 |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID | M_HU_PI_Item_Product_ID |
      | plv_1                  | huProduct    | 1.0      | PCE      | Normal           |                         |
      | plv_1                  | huProduct    | 10.0     | PCE      | Normal           | huProductTU             |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2022-01-02   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_PI_Item_Product_ID |
      | inventory      | inventoryLine      | huProduct    | 0       | 30       | PCE          | huProductTU             |
    And the inventory identified by inventory is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | inventoryLine      | createdTU1 |
      | inventoryLine      | createdTU2 |
      | inventoryLine      | createdTU3 |
    And validate M_HU_Storage:
      | M_HU_ID    | M_Product_ID | Qty |
      | createdTU1 | huProduct    | 10  |
      | createdTU2 | huProduct    | 10  |
      | createdTU3 | huProduct    | 10  |

    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | InvoiceRule |
      | endcustomer_1 | Y          | ps_1               | D           |

  Scenario: Incomplete HUs picked for shipping. A single M_InOutLine is created for it

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | o1         | true    | endcustomer_1 | 2022-01-01  |
      | o2         | true    | endcustomer_1 | 2022-01-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol1        | o1         | huProduct    | 4          |                         |
      | ol2        | o2         | huProduct    | 20         | huProductTU             |
# This order is only used to partially deplete the first TU
    And the order identified by o1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s_s_1      | ol1            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                 | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s_s_1                 | inOut1     |
# Validate that the qty is taken from the first TU
    And validate M_HU_Storage:
      | M_HU_ID    | M_Product_ID | Qty |
      | createdTU1 | huProduct    | 6   |
      | createdTU2 | huProduct    | 10  |
      | createdTU3 | huProduct    | 10  |

    And the order identified by o2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s_s_2      | ol2            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                 | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s_s_2                 | inOut2     |

    #Validate a single inoutline was created for the whole qty
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | movementqty | processed | OPT.C_OrderLine_ID |
      | sl1            | inOut2     | huProduct    | 20          | true      | ol2                |

    # Validate the shipment took:
    # - All the remaining qty from createdTU1
    # - All the qty from createdTU2
    # - A remainder of 4 from createdTU3
    And validate M_HU_Storage:
      | M_HU_ID    | M_Product_ID | Qty |
      | createdTU1 | huProduct    | 0   |
      | createdTU2 | huProduct    | 0   |
      | createdTU3 | huProduct    | 6   |