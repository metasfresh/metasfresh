@from:cucumber
@topic:stock
Feature: stock changes accordingly
  As a user
  I want stock to be updated properly if inventory is changed
  So that the QTY is always correct

  Background:
    Given infrastructure and metasfresh are running
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no MD_Stock data
    And no product with value 'product_value222' exists
    And metasfresh contains M_Product with M_Product_ID '222'

  Scenario: Changes stock by adding inventory
    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo |
      | 11                        | 2021-07-12T00:00:00Z | 1111       |
      | 12                        | 2021-07-12T00:00:00Z | 2222       |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | OPT.M_Product_ID | M_Product_ID.Identifier | QtyBook | QtyCount |
      | 11                        | 21                            | 222              | 222                     | 0       | 10       |
      | 12                        | 22                            | 222              | 222                     | 10      | 16       |
    When complete inventory with inventoryIdentifier '11'
    Then after not more than 10 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | 222                     | 10        |

    And complete inventory with inventoryIdentifier '12'
    And after not more than 10 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | 222                     | 16        |
