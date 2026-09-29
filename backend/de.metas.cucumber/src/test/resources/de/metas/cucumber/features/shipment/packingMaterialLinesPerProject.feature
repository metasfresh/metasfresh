@from:cucumber
@allure.label.epic:E0240_Project_Management
@allure.label.feature:F68020
@ghActions:run_on_executor2
Feature: shipment packing material lines split per project
## F68020: C_Project propagation through Purchase ↔ Sales flow

  # One goods product packed in TUs of 10; each TU carries one piece of packing material p_pm.
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer_1 | Y          | ps_1               |
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
      | p_goods    |                       |
      | p_pm       | pm_category           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_goods      | 10.0     | PCE      |
      | plv_1                  | p_pm         | 1.0      | PCE      |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_TU    |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID | HU_UnitType |
      | huVersion  | huPI_TU    | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_1                    | p_pm         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem   | huVersion          | 10  | PM       | pm_1                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier    | M_HU_PI_Item_ID | Qty | M_Product_ID |
      | huItemProduct | huPIItem        | 10  | p_goods      |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: order lines of three projects ship and invoice with one packing material line per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And temporarily set AD_SysConfig to M_Product_Category_ID:
      | Name                             | M_Product_Category_ID |
      | PackingMaterialProductCategoryID | pm_category           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_1      | so_1       | p_goods      | 20         | P1           | huItemProduct           |
      | sol_2      | so_1       | p_goods      | 30         | P2           | huItemProduct           |
      | sol_3      | so_1       | p_goods      | 40         | P3           | huItemProduct           |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
      | ss_2       | sol_2          | N             |
      | ss_3       | sol_3          | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_1                  |
      | ss_2                  |
      | ss_3                  |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |
      | ss_2                  | shipment_1 |
      | ss_3                  | shipment_1 |
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | goods_P1       | shipment_1 | p_goods      | 20          | P1           |
      | goods_P2       | shipment_1 | p_goods      | 30          | P2           |
      | goods_P3       | shipment_1 | p_goods      | 40          | P3           |
      | pm_P1          | shipment_1 | p_pm         | 2           | P1           |
      | pm_P2          | shipment_1 | p_pm         | 3           | P2           |
      | pm_P3          | shipment_1 | p_pm         | 4           | P3           |

    # The shipment report still prints the packing material once per product.
    And the shipment report packing section of shipment_1 in language de_DE has exactly:
      | M_Product_ID | MovementQty |
      | p_pm         | 9           |

    # Each packing invoice line keeps its project.
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID |
      | ic_pm_P1               | pm_P1          |
      | ic_pm_P2               | pm_P2          |
      | ic_pm_P3               | pm_P3          |
    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_pm_P1               |
      | ic_pm_P2               |
      | ic_pm_P3               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_1    | ic_pm_P1               |
    And validate created invoice lines
      | C_InvoiceLine_ID | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | C_Project_ID |
      | il_pm_P1         | invoice_1    | p_pm         | 2           | pm_P1          | P1           |
      | il_pm_P2         | invoice_1    | p_pm         | 3           | pm_P2          | P2           |
      | il_pm_P3         | invoice_1    | p_pm         | 4           | pm_P3          | P3           |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: order lines of the same project ship with one packing material line for that project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_1      | so_1       | p_goods      | 20         | P1           | huItemProduct           |
      | sol_2      | so_1       | p_goods      | 30         | P1           | huItemProduct           |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
      | ss_2       | sol_2          | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_1                  |
      | ss_2                  |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |
      | ss_2                  | shipment_1 |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment_1 | p_pm         | 5           | P1           |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: order lines without project ship with one packing material line without project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | sol_1      | so_1       | p_goods      | 20         | huItemProduct           |
      | sol_2      | so_1       | p_goods      | 30         | huItemProduct           |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
      | ss_2       | sol_2          | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_1                  |
      | ss_2                  |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |
      | ss_2                  | shipment_1 |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment_1 | p_pm         | 5           | null         |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: with the split switched off, order lines of three projects ship with one packing material line without project
    Given temporarily set sys config boolean value false for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_1      | so_1       | p_goods      | 20         | P1           | huItemProduct           |
      | sol_2      | so_1       | p_goods      | 30         | P2           | huItemProduct           |
      | sol_3      | so_1       | p_goods      | 40         | P3           | huItemProduct           |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
      | ss_2       | sol_2          | N             |
      | ss_3       | sol_3          | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_1                  |
      | ss_2                  |
      | ss_3                  |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |
      | ss_2                  | shipment_1 |
      | ss_3                  | shipment_1 |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment_1 | p_goods      | 20          | P1           |
      | shipment_1 | p_goods      | 30          | P2           |
      | shipment_1 | p_goods      | 40          | P3           |
      | shipment_1 | p_pm         | 9           | null         |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: packing material lines recreated on a draft shipment are split per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_1      | so_1       | p_goods      | 20         | P1           | huItemProduct           |
      | sol_2      | so_1       | p_goods      | 30         | P2           | huItemProduct           |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
      | ss_2       | sol_2          | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=false and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_1                  |
      | ss_2                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |
      | ss_2                  | shipment_1 |
    And reset M_InOut packing lines for shipment shipment_1
    And the shipment identified by shipment_1 is completed

    Then validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment_1 | p_pm         | 2           | P1           |
      | shipment_1 | p_pm         | 3           | P2           |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: picked HUs of order lines with and without project ship with one packing material line per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_1       | true    | customer_1    | 2021-04-17  | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | M_HU_PI_Item_Product_ID |
      | sol_noProject | so_1       | p_goods      | 10         |              | huItemProduct           |
      | sol_P1        | so_1       | p_goods      | 20         | P1           | huItemProduct           |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | ss_noProject | sol_noProject  | N             |
      | ss_P1        | sol_P1         | N             |

    # one TU for the line without project, two TUs for the P1 line
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID      |
      | inv_noProject  | warehouseStd   | 2021-04-17   | p_goods      | 0 PCE   | 10 PCE   | huItemProduct           | hu_noProject |
      | inv_P1_1       | warehouseStd   | 2021-04-17   | p_goods      | 0 PCE   | 10 PCE   | huItemProduct           | hu_P1_1      |
      | inv_P1_2       | warehouseStd   | 2021-04-17   | p_goods      | 0 PCE   | 10 PCE   | huItemProduct           | hu_P1_2      |
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
      | ss_noProject          | shipment_1 |
      | ss_P1                 | shipment_1 |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment_1 | p_pm         | 1           | null         |
      | shipment_1 | p_pm         | 2           | P1           |
