@from:cucumber
@ghActions:run_on_executor7
Feature: Inventory Value report / Lagerwert

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
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | M             |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @Id:S0171.300
  Scenario: Happy flow
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inv1           | 2024-03-05   | wh             | P1           | 0       | 100      | PCE          | 10        | hu1     |
      | inv2           | 2024-03-10   | wh             | P1           | 100     | 90       | PCE          |           | hu1     |
      | inv3           | 2024-03-15   | wh             | P1           | 90      | 190      | PCE          | 11        | hu2     |
      | inv4           | 2024-03-20   | wh             | P1           | 190     | 180      | PCE          |           | hu1     |
      | inv5           | 2024-03-25   | wh             | P1           | 180     | 100      | PCE          |           | hu1     |
      | inv6           | 2024-03-30   | wh             | P1           | 100     | 0        | PCE          |           | hu2     |

    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | TotalAmt | CostPrice |
      | 2024-03-07 | P1           | wh             | 100 | 1000.00  | 10.0000   |
      | 2024-03-12 | P1           | wh             | 90  | 900.00   | 10.0000   |
      | 2024-03-17 | P1           | wh             | 190 | 2000.00  | 10.5263   |
      | 2024-03-22 | P1           | wh             | 180 | 1894.74  | 10.5263   |
      | 2024-03-27 | P1           | wh             | 100 | 1052.64  | 10.5263   |
      | 2024-04-01 | P1           | wh             | 0   | 0.01     | 10.5263   |
    