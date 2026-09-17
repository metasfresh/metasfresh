@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: MD_Candidate_Remove_From_ATP process
## Test the MD_Candidate_Remove_From_ATP process that removes candidates from ATP calculations

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | Identifier            | Name     | Value    |
      | standard_category_atp | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | WH_ATP     |

    And metasfresh contains M_Products:
      | Identifier  | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | product_atp | standard_category_atp | PCE               |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_atp     |

    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_atp  | ps_atp             | DE           | EUR           | true  |
      | pl_po_atp  | ps_atp             | DE           | EUR           | false |

    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so_atp | pl_so_atp      |
      | plv_po_atp | pl_po_atp      |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_atp  | plv_so_atp             | product_atp  | 10.0     | PCE               | Normal           |
      | pp_po_atp  | plv_po_atp             | product_atp  | 8.0      | PCE               | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_atp | N        | Y          | ps_atp             |
      | vendor_atp   | Y        | N          | ps_atp             |

  @Id:ATP_001
  @from:cucumber
  Scenario: Remove demand candidate from ATP after creating via sales order

    # Create sales order to generate demand candidate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_atp_001 | true    | customer_atp  | 2024-09-20  | 2024-09-22T21:00:00Z | WH_ATP         |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_atp_001 | so_atp_001 | product_atp  | 100        |

    And the order identified by so_atp_001 is completed

    # Verify demand candidate was created
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | demand_atp_001 | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-22T21:00:00Z | 100 | -100 | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_atp_001  |

    # Verify the candidate qty is now 0 and ATP is 0
    Then after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | demand_atp_001 | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-22T21:00:00Z | 0   | 0   | WH_ATP         |


  @Id:ATP_002
  @from:cucumber
  Scenario: Remove supply candidate from ATP after creating via purchase order

    # Create purchase order to generate supply candidate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised         | M_Warehouse_ID |
      | po_atp_002 | false   | vendor_atp    | 2024-09-20  | 2024-09-25T21:00:00Z | WH_ATP         |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | pol_atp_002 | po_atp_002 | product_atp  | 50         |

    And the order identified by po_atp_002 is completed

    # Verify supply candidate was created
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | supply_atp_002 | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-25T21:00:00Z | 50  | 50  | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | supply_atp_002  |

    # Verify the candidate qty is now 0 and ATP is 0
    Then after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | supply_atp_002 | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-25T21:00:00Z | 0   | 0   | WH_ATP         |


  @Id:ATP_003
  @from:cucumber
  Scenario: Remove candidate from ATP and verify subsequent candidates are updated

    # Create initial inventory
    Given metasfresh contains M_Inventories:
      | Identifier        | M_Warehouse_ID | MovementDate |
      | inventory_atp_003 | WH_ATP         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID    | Identifier            | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inventory_atp_003 | inventoryLine_atp_003 | product_atp  | 0       | 200      | WH_ATP         | PCE          |
    And the inventory identified by inventory_atp_003 is completed

    # Wait for inventory candidates
    And after not more than 10s, MD_Candidates are found
      | Identifier             | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | inventory_cand_atp_003 | INVENTORY_UP      | product_atp  | 2024-09-20T06:00:00Z | 200 | 200 | WH_ATP         |

    And metasfresh has date and time 2024-09-20T09:00:00+01:00[Europe/Berlin]
    # Create sales order (demand at T+2)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_atp_003 | true    | customer_atp  | 2024-09-20  | 2024-09-22T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_atp_003 | so_atp_003 | product_atp  | 100        |
    And the order identified by so_atp_003 is completed

    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    And metasfresh has date and time 2024-09-20T10:00:00+01:00[Europe/Berlin]
    # Create purchase order (supply at T+3)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised         | M_Warehouse_ID |
      | po_atp_003 | false   | vendor_atp    | 2024-09-20  | 2024-09-23T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | pol_atp_003 | po_atp_003 | product_atp  | 150        |
    And the order identified by po_atp_003 is completed

    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Verify all candidates
    And after not more than 10s, MD_Candidates are found
      | Identifier             | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP | M_Warehouse_ID |
      | inventory_cand_atp_003 | INVENTORY_UP      |                           | product_atp  | 2024-09-20T06:00:00Z | 200  | 200 | WH_ATP         |
      | demand_atp_003         | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-22T21:00:00Z | -100 | 100 | WH_ATP         |
      | supply_atp_003         | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-23T21:00:00Z | 150  | 250 | WH_ATP         |

    # Remove the demand candidate from ATP
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_atp_003  |

    # Verify the demand candidate is zeroed and subsequent supply candidate ATP is updated
    Then after not more than 10s, MD_Candidates are found
      | Identifier             | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | inventory_cand_atp_003 | INVENTORY_UP      |                           | product_atp  | 2024-09-20T06:00:00Z | 200 | 200 | WH_ATP         |
      | demand_atp_003         | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-22T21:00:00Z | 0   | 200 | WH_ATP         |
      | supply_atp_003         | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-23T21:00:00Z | 150 | 350 | WH_ATP         |


  @Id:ATP_004
  @from:cucumber
  Scenario: Idempotency - Running MD_Candidate_Remove_From_ATP multiple times on middle candidate in chain

    # Create initial inventory
    Given metasfresh contains M_Inventories:
      | Identifier        | M_Warehouse_ID | MovementDate |
      | inventory_atp_004 | WH_ATP         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID    | Identifier            | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inventory_atp_004 | inventoryLine_atp_004 | product_atp  | 0       | 100      | WH_ATP         | PCE          |
    And the inventory identified by inventory_atp_004 is completed

    # Wait for inventory candidate
    And after not more than 10s, MD_Candidates are found
      | Identifier      | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | initial_inv_004 | INVENTORY_UP      | product_atp  | 2024-09-20T06:00:00Z | 100 | 100 | WH_ATP         |

    # Create sales order 1 (demand_1_004 at T+1)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_004_1   | true    | customer_atp  | 2024-09-20  | 2024-09-21T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_004_1  | so_004_1   | product_atp  | 30         |
    And the order identified by so_004_1 is completed
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Create purchase order 1 (supply_1_004 at T+2)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised         | M_Warehouse_ID |
      | po_004_1   | false   | vendor_atp    | 2024-09-20  | 2024-09-22T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | pol_004_1  | po_004_1   | product_atp  | 50         |
    And the order identified by po_004_1 is completed
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Create sales order 2 (demand_2_004 at T+3) - this is the middle candidate we'll test
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_004_2   | true    | customer_atp  | 2024-09-20  | 2024-09-23T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_004_2  | so_004_2   | product_atp  | 40         |
    And the order identified by so_004_2 is completed
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Create purchase order 2 (supply_2_004 at T+4)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised         | M_Warehouse_ID |
      | po_004_2   | false   | vendor_atp    | 2024-09-20  | 2024-09-24T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | pol_004_2  | po_004_2   | product_atp  | 60         |
    And the order identified by po_004_2 is completed
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Create sales order 3 (demand_3_004 at T+5)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_004_3   | true    | customer_atp  | 2024-09-20  | 2024-09-25T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_004_3  | so_004_3   | product_atp  | 35         |
    And the order identified by so_004_3 is completed
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Verify all candidates are present with correct ATP chain
    And after not more than 10s, MD_Candidates are found
      | Identifier      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP | M_Warehouse_ID |
      | initial_inv_004 | INVENTORY_UP      |                           | product_atp  | 2024-09-20T06:00:00Z | 100  | 100 | WH_ATP         |
      | demand_1_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-21T21:00:00Z | -30  | 70  | WH_ATP         |
      | supply_1_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-22T21:00:00Z | 50   | 120 | WH_ATP         |
      | demand_2_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-23T21:00:00Z | -40  | 80  | WH_ATP         |
      | supply_2_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-24T21:00:00Z | 60   | 140 | WH_ATP         |
      | demand_3_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-25T21:00:00Z | -35  | 105 | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process on demand_2_004 (middle candidate) - first time
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_2_004    |

    # Verify the middle candidate is zeroed and subsequent candidates' ATP is updated
    Then after not more than 10s, MD_Candidates are found
      | Identifier      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | initial_inv_004 | INVENTORY_UP      |                           | product_atp  | 2024-09-20T06:00:00Z | 100 | 100 | WH_ATP         |
      | demand_1_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-21T21:00:00Z | -30 | 70  | WH_ATP         |
      | supply_1_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-22T21:00:00Z | 50  | 120 | WH_ATP         |
      | demand_2_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-23T21:00:00Z | 0   | 120 | WH_ATP         |
      | supply_2_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-24T21:00:00Z | 60  | 180 | WH_ATP         |
      | demand_3_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-25T21:00:00Z | -35 | 145 | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process on the same candidate - second time
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_2_004    |

    # Verify idempotent behavior - nothing changes
    Then after not more than 10s, MD_Candidates are found
      | Identifier      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | initial_inv_004 | INVENTORY_UP      |                           | product_atp  | 2024-09-20T06:00:00Z | 100 | 100 | WH_ATP         |
      | demand_1_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-21T21:00:00Z | -30 | 70  | WH_ATP         |
      | supply_1_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-22T21:00:00Z | 50  | 120 | WH_ATP         |
      | demand_2_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-23T21:00:00Z | 0   | 120 | WH_ATP         |
      | supply_2_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-24T21:00:00Z | 60  | 180 | WH_ATP         |
      | demand_3_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-25T21:00:00Z | -35 | 145 | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process on the same candidate - third time
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_2_004    |

    # Verify idempotent behavior - still nothing changes
    Then after not more than 10s, MD_Candidates are found
      | Identifier      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | initial_inv_004 | INVENTORY_UP      |                           | product_atp  | 2024-09-20T06:00:00Z | 100 | 100 | WH_ATP         |
      | demand_1_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-21T21:00:00Z | -30 | 70  | WH_ATP         |
      | supply_1_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-22T21:00:00Z | 50  | 120 | WH_ATP         |
      | demand_2_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-23T21:00:00Z | 0   | 120 | WH_ATP         |
      | supply_2_004    | SUPPLY            | PURCHASE                  | product_atp  | 2024-09-24T21:00:00Z | 60  | 180 | WH_ATP         |
      | demand_3_004    | DEMAND            | SHIPMENT                  | product_atp  | 2024-09-25T21:00:00Z | -35 | 145 | WH_ATP         |
