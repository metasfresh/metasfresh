@from:cucumber
@topic:stock
@ghActions:run_on_executor7
Feature: stock changes accordingly
  As a user
  I want stock to be updated properly if inventory is changed
  So that the QTY is always correct

  Background:
    Given infrastructure and metasfresh are running
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And no product with value 'product_value222' exists
    And metasfresh contains M_Products:
      | Identifier | Name               |
      | 222        | automateProduct222 |

  Scenario: Changes stock by adding inventory
    # create two inventories, each one with one inventory-line
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
      | 12                        | 540008         | 2021-07-11   | 2222           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | 222                     | PCE          | 10       | 0       |
      | 22                            | 12                        | 222                     | PCE          | 16       | 10      |
    When the inventory identified by 11 is completed
    Then after not more than 120 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | 222                     | 10        |

    And the inventory identified by 12 is completed
    And after not more than 120 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | 222                     | 16        |
