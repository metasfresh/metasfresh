@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5110_Distribution_Order_Candidate
@F5110
@ghActions:run_on_executor6
Feature: material cockpit reflects physical inventory operations
## F5110: Material Cockpit — Inventory scenarios

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU         |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | LU_version         | LU         | LU          |
      | TU_version         | TU         | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID |
      |                 | LU_version         | HU       | 40  | TU                |
      |                 | LU_version         | HU       | 1   | 101               |
      | TU_PI_Item      | TU_version         | MI       |     |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty     |
      | TUx10                   | TU_PI_Item      | product      | 10 PCE  |
      | CUx200                  | 101             | product      | 200 PCE |

  @Id:S0189_5000
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5110_Distribution_Order_Candidate
  @F5110
  Scenario: Physical inventory count up populates cockpit inventory and stock columns
    # Creating a positive inventory (QtyCount > QtyBook) should reflect in:
    # - QtyInventoryCount_AtDate showing the counted quantity
    # - QtyStockCurrent_AtDate showing updated stock
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_ID |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 100 PCE  | hu1     |
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyInventoryCount_AtDate | OPT.QtyStockCurrent_AtDate |
      | cp_1       | product                 | 2021-04-16  | 100                          | 100                        |

  @Id:S0189_5100
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5110_Distribution_Order_Candidate
  @F5110
  Scenario: Physical inventory count down reflects negative change in cockpit
    # First count up, then count down — cockpit should show net stock
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_ID |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 200 PCE  | hu1     |
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyStockCurrent_AtDate |
      | cp_1       | product                 | 2021-04-16  | 200                        |
    # Second inventory counting down (reuses same HU)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_ID |
      | inventory2     | warehouse      | 2021-04-16   | product      | 200 PCE | 50 PCE   | hu1     |
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyStockCurrent_AtDate |
      | cp_1       | product                 | 2021-04-16  | 50                         |
