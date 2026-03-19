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

  @Id:ATP_001
  @from:cucumber
  Scenario: Remove demand candidate from ATP after creating via sales order

    Given metasfresh contains M_Products:
      | Identifier    | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | product_atp_1 | standard_category_atp | PCE               |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_atp_001 |

    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_atp_001 | ps_atp_001         | DE           | EUR           | true  |

    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | plv_atp_001 | pl_atp_001     |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_atp_001 | plv_atp_001            | product_atp_1 | 10.0     | PCE               | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_atp_01 | N        | Y          | ps_atp_001         |

    # Create sales order to generate demand candidate
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID   | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_atp_001 | true    | customer_atp_01 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_ATP         |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID  | QtyEntered |
      | sol_atp_001 | so_atp_001 | product_atp_1 | 100        |

    And the order identified by so_atp_001 is completed

    # Verify demand candidate was created
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID  | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | demand_atp_001 | DEMAND            | SHIPMENT                  | product_atp_1 | 2024-09-22T21:00:00Z | 100 | -100 | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_atp_001  |

    # Verify the candidate qty is now 0 and ATP is 0
    Then after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID  | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | demand_atp_001 | DEMAND            | SHIPMENT                  | product_atp_1 | 2024-09-22T21:00:00Z | 0   | 0   | WH_ATP         |


  @Id:ATP_002
  @from:cucumber
  Scenario: Remove supply candidate from ATP after creating via purchase order

    Given metasfresh contains M_Products:
      | Identifier    | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | product_atp_2 | standard_category_atp | PCE               |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_atp_002 |

    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_atp_002 | ps_atp_002         | DE           | EUR           | false |

    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | plv_atp_002 | pl_atp_002     |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_atp_002 | plv_atp_002            | product_atp_2 | 10.0     | PCE               | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor_atp_02 | Y        | N          | ps_atp_002         |

    # Create purchase order to generate supply candidate
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised         | M_Warehouse_ID |
      | po_atp_002 | false   | vendor_atp_02 | 2024-09-20  | 2024-09-25T21:00:00Z | WH_ATP         |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID  | QtyEntered |
      | pol_atp_002 | po_atp_002 | product_atp_2 | 50         |

    And the order identified by po_atp_002 is completed

    # Verify supply candidate was created
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID  | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | supply_atp_002 | SUPPLY            | PURCHASE                  | product_atp_2 | 2024-09-25T21:00:00Z | 50  | 50  | WH_ATP         |

    # Run the MD_Candidate_Remove_From_ATP process
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | supply_atp_002  |

    # Verify the candidate qty is now 0 and ATP is 0
    Then after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID  | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | supply_atp_002 | SUPPLY            | PURCHASE                  | product_atp_2 | 2024-09-25T21:00:00Z | 0   | 0   | WH_ATP         |


  @Id:ATP_003
  @from:cucumber
  Scenario: Remove candidate from ATP and verify subsequent candidates are updated

    Given metasfresh contains M_Products:
      | Identifier    | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | product_atp_3 | standard_category_atp | PCE               |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_atp_003 |

    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_atp_003 | ps_atp_003         | DE           | EUR           | true  |
      | pl_po_atp_003 | ps_atp_003         | DE           | EUR           | false |

    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_so_atp_003 | pl_so_atp_003  |
      | plv_po_atp_003 | pl_po_atp_003  |

    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_atp_003 | plv_so_atp_003         | product_atp_3 | 10.0     | PCE               | Normal           |
      | pp_po_atp_003 | plv_po_atp_003         | product_atp_3 | 8.0      | PCE               | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_atp_03 | N        | Y          | ps_atp_003         |
      | vendor_atp_03   | Y        | N          | ps_atp_003         |

    # Create initial inventory
    And metasfresh contains M_Inventories:
      | Identifier        | M_Warehouse_ID | MovementDate |
      | inventory_atp_003 | WH_ATP         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID    | Identifier            | M_Product_ID  | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inventory_atp_003 | inventoryLine_atp_003 | product_atp_3 | 0       | 200      | WH_ATP         | PCE          |
    And the inventory identified by inventory_atp_003 is completed

    # Wait for inventory candidates
    And after not more than 10s, MD_Candidates are found
      | Identifier             | MD_Candidate_Type | M_Product_ID  | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | inventory_cand_atp_003 | INVENTORY_UP      | product_atp_3 | 2024-09-20T06:00:00Z | 200 | 200 | WH_ATP         |

    And metasfresh has date and time 2024-09-20T09:00:00+01:00[Europe/Berlin]
    # Create sales order (demand at T+2)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID   | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_atp_003 | true    | customer_atp_03 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID  | QtyEntered |
      | sol_atp_003 | so_atp_003 | product_atp_3 | 100        |
    And the order identified by so_atp_003 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh has date and time 2024-09-20T10:00:00+01:00[Europe/Berlin]
    # Create purchase order (supply at T+3)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised         | M_Warehouse_ID |
      | po_atp_003 | false   | vendor_atp_03 | 2024-09-20  | 2024-09-23T21:00:00Z | WH_ATP         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID  | QtyEntered |
      | pol_atp_003 | po_atp_003 | product_atp_3 | 150        |
    And the order identified by po_atp_003 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Verify all candidates
    And after not more than 10s, MD_Candidates are found
      | Identifier             | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID  | DateProjected        | Qty  | ATP | M_Warehouse_ID |
      | inventory_cand_atp_003 | INVENTORY_UP      |                           | product_atp_3 | 2024-09-20T06:00:00Z | 200  | 200 | WH_ATP         |
      | demand_atp_003         | DEMAND            | SHIPMENT                  | product_atp_3 | 2024-09-22T21:00:00Z | -100 | 100 | WH_ATP         |
      | supply_atp_003         | SUPPLY            | PURCHASE                  | product_atp_3 | 2024-09-23T21:00:00Z | 150  | 250 | WH_ATP         |

    # Remove the demand candidate from ATP
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | demand_atp_003  |

    # Verify the demand candidate is zeroed and subsequent supply candidate ATP is updated
    Then after not more than 10s, MD_Candidates are found
      | Identifier             | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID  | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | inventory_cand_atp_003 | INVENTORY_UP      |                           | product_atp_3 | 2024-09-20T06:00:00Z | 200 | 200 | WH_ATP         |
      | demand_atp_003         | DEMAND            | SHIPMENT                  | product_atp_3 | 2024-09-22T21:00:00Z | 0   | 200 | WH_ATP         |
      | supply_atp_003         | SUPPLY            | PURCHASE                  | product_atp_3 | 2024-09-23T21:00:00Z | 150 | 350 | WH_ATP         |
