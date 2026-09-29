@from:cucumber
@allure.label.epic:E0240_Project_Management
@allure.label.feature:F68020_C_Project_propagation_through_Purchase_Sales_flow
@ghActions:run_on_executor2
Feature: shipment packing material lines split per project
## F68020: C_Project propagation through Purchase ↔ Sales flow

  # One goods product packed in crates (TUs) of 10; each crate is one piece of packing material.
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList  | pricingSystem      | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | pricingSystem      |
    And metasfresh contains C_Projects:
      | Identifier |
      | P1         |
      | P2         |
      | P3         |
    And metasfresh contains M_Product_Categories:
      | Identifier  |
      | pm_category |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | goods      |                       |
      | crate      | pm_category           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | priceListVersion       | goods        | 10.0     | PCE      |
      | priceListVersion       | crate        | 1.0      | PCE      |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_crate |
    And metasfresh contains M_HU_PI_Version:
      | Identifier      | M_HU_PI_ID | HU_UnitType |
      | huVersion_crate | huPI_crate | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_crate                | crate        |
    And metasfresh contains M_HU_PI_Item:
      | Identifier     | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem_crate | huVersion_crate    | 10  | PM       | pm_crate                |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID | Qty | M_Product_ID |
      | goods_crate | huPIItem_crate  | 10  | goods        |
    # The shipment report prints the products of this category in its packing section.
    And temporarily set AD_SysConfig to M_Product_Category_ID:
      | Name                             | M_Product_Category_ID |
      | PackingMaterialProductCategoryID | pm_category           |

  Scenario: order lines of three projects ship and invoice with one packing material line per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | salesOrder | true    | customer      | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_P1     | salesOrder | goods        | 20         | P1           | goods_crate             |
      | sol_P2     | salesOrder | goods        | 30         | P2           | goods_crate             |
      | sol_P3     | salesOrder | goods        | 40         | P3           | goods_crate             |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_P1      | sol_P1         | N             |
      | ss_P2      | sol_P2         | N             |
      | ss_P3      | sol_P3         | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_P1                 |
      | ss_P2                 |
      | ss_P3                 |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_P1                 | shipment   |
      | ss_P2                 | shipment   |
      | ss_P3                 | shipment   |
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      |                | shipment   | goods        | 20          | P1           |
      |                | shipment   | goods        | 30          | P2           |
      |                | shipment   | goods        | 40          | P3           |
      | crate_P1       | shipment   | crate        | 2           | P1           |
      | crate_P2       | shipment   | crate        | 3           | P2           |
      | crate_P3       | shipment   | crate        | 4           | P3           |

    # The shipment report prints the packing material once per product.
    And the shipment report packing section of shipment in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | crate        | 9           |

    # Each packing invoice line keeps its project.
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID |
      | ic_crate_P1            | crate_P1       |
      | ic_crate_P2            | crate_P2       |
      | ic_crate_P3            | crate_P3       |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_crate_P1            | P1           |
      | ic_crate_P2            | P2           |
      | ic_crate_P3            | P3           |
    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_crate_P1            |
      | ic_crate_P2            |
      | ic_crate_P3            |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice      | ic_crate_P1            |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | C_Project_ID |
      | invoice      | crate        | 2           | crate_P1       | P1           |
      | invoice      | crate        | 3           | crate_P2       | P2           |
      | invoice      | crate        | 4           | crate_P3       | P3           |

  Scenario: order lines of the same project ship with one packing material line for that project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | salesOrder | true    | customer      | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_P1_a   | salesOrder | goods        | 20         | P1           | goods_crate             |
      | sol_P1_b   | salesOrder | goods        | 30         | P1           | goods_crate             |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_P1_a    | sol_P1_a       | N             |
      | ss_P1_b    | sol_P1_b       | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_P1_a               |
      | ss_P1_b               |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_P1_a               | shipment   |
      | ss_P1_b               | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment   | crate        | 5           | P1           |
    And the shipment report packing section of shipment in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | crate        | 5           |

  Scenario: order lines without project ship with one packing material line without project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | salesOrder | true    | customer      | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | sol_noProject_a | salesOrder | goods        | 20         | goods_crate             |
      | sol_noProject_b | salesOrder | goods        | 30         | goods_crate             |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | IsToRecompute |
      | ss_noProject_a | sol_noProject_a | N             |
      | ss_noProject_b | sol_noProject_b | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_noProject_a        |
      | ss_noProject_b        |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_noProject_a        | shipment   |
      | ss_noProject_b        | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment   | crate        | 5           | null         |
    And the shipment report packing section of shipment in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | crate        | 5           |

  Scenario: with the split switched off, order lines of three projects ship with one packing material line without project
    Given temporarily set sys config boolean value false for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | salesOrder | true    | customer      | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_P1     | salesOrder | goods        | 20         | P1           | goods_crate             |
      | sol_P2     | salesOrder | goods        | 30         | P2           | goods_crate             |
      | sol_P3     | salesOrder | goods        | 40         | P3           | goods_crate             |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_P1      | sol_P1         | N             |
      | ss_P2      | sol_P2         | N             |
      | ss_P3      | sol_P3         | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_P1                 |
      | ss_P2                 |
      | ss_P3                 |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_P1                 | shipment   |
      | ss_P2                 | shipment   |
      | ss_P3                 | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment   | goods        | 20          | P1           |
      | shipment   | goods        | 30          | P2           |
      | shipment   | goods        | 40          | P3           |
      | shipment   | crate        | 9           | null         |
    And the shipment report packing section of shipment in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | crate        | 9           |

  Scenario: packing material lines recreated on a draft shipment are split per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | salesOrder | true    | customer      | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_P1     | salesOrder | goods        | 20         | P1           | goods_crate             |
      | sol_P2     | salesOrder | goods        | 30         | P2           | goods_crate             |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_P1      | sol_P1         | N             |
      | ss_P2      | sol_P2         | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=false and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_P1                 |
      | ss_P2                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_P1                 | shipment   |
      | ss_P2                 | shipment   |
    And reset M_InOut packing lines for shipment shipment
    And the shipment identified by shipment is completed

    Then validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment   | crate        | 2           | P1           |
      | shipment   | crate        | 3           | P2           |
    And the shipment report packing section of shipment in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | crate        | 5           |

  Scenario: picked HUs of order lines with and without project ship with one packing material line per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | salesOrder | true    | customer      | 2021-04-17  | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_noProject | salesOrder | goods        | 10         |              | goods_crate             |
      | sol_P1        | salesOrder | goods        | 20         | P1           | goods_crate             |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | ss_noProject | sol_noProject  | N             |
      | ss_P1        | sol_P1         | N             |

    # one TU for the line without project, two TUs for the P1 line
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID      |
      | inv_noProject  | warehouseStd   | 2021-04-17   | goods        | 0 PCE   | 10 PCE   | goods_crate             | hu_noProject |
      | inv_P1_1       | warehouseStd   | 2021-04-17   | goods        | 0 PCE   | 10 PCE   | goods_crate             | hu_P1_1      |
      | inv_P1_2       | warehouseStd   | 2021-04-17   | goods        | 0 PCE   | 10 PCE   | goods_crate             | hu_P1_2      |
    And create M_PickingCandidate for M_HU
      | M_HU_ID      | M_ShipmentSchedule_ID | QtyPicked | Status | PickStatus | ApprovalStatus |
      | hu_noProject | ss_noProject          | 10        | IP     | P          | ?              |
      | hu_P1_1      | ss_P1                 | 10        | IP     | P          | ?              |
      | hu_P1_2      | ss_P1                 | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID      | M_ShipmentSchedule_ID |
      | hu_noProject | ss_noProject          |
      | hu_P1_1      | ss_P1                 |
      | hu_P1_2      | ss_P1                 |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | ss_noProject          |
      | ss_P1                 |

    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_noProject          |
      | ss_P1                 |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_noProject          | shipment   |
      | ss_P1                 | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment   | crate        | 1           | null         |
      | shipment   | crate        | 2           | P1           |
    And the shipment report packing section of shipment in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | crate        | 3           |
