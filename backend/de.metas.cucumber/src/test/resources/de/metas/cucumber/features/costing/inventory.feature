@from:cucumber
@ghActions:run_on_executor7
Feature: Inventory Costing

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00
    And documents are accounted immediately
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | P1         | PCE      |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And load C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: Outbound transaction is using provided fixed cost price
    When update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | A             |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inv1           | inv1_l1            | 2024-03-05   | wh             | P1           | 0       | 100      | PCE          | 10        | hu1     |
      | inv2           | inv2_l1            | 2024-03-10   | wh             | P1           | 100     | 90       | PCE          | 8         | hu1     |

    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | TotalAmt | CostPrice |
      | 2024-03-07 | P1           | wh             | 100 | 1000.00  | 10.0000   |
      | 2024-03-12 | P1           | wh             | 90  | 920.00   | 10.2222   |
    And after not more than 10s, M_CostDetails are found for product P1 and cost element AveragePO,MovingAverageInvoice
      | TableName       | Record_ID | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1   | N       | 1000 CHF | 100 PCE | Y               |
      | M_InventoryLine | inv2_l1   | Y       | -80 CHF  | -10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID               | CurrentCostPrice | CurrentQty |
      | acctSchema      | P1           | AveragePO,MovingAverageInvoice | 10.2222 CHF      | 90 PCE     |

    
    
    
    
    
    
    
    
    
