@from:cucumber
@topic:stock
Feature: stock changes accordingly
  As a user
  I want stock to be updated properly if inventory is changed
  So that the QTY is always correct

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And delete transactional data and stock data
    And no product with value 'product_value222' exists
    And metasfresh contains M_Products:
      | Identifier | Name               |
      | 222        | automateProduct222 |

  Scenario: Changes stock by adding inventory
    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo |
      | 11                        | 2021-07-12T00:00:00Z | 1111       |
      | 12                        | 2021-07-12T00:00:00Z | 2222       |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | OPT.M_Product_ID |
      | 11                        | 21                            | 222                     | 0       | 10       | null             |
      | 12                        | 22                            | 222                     | 10      | 16       | null             |
    When complete inventory with inventoryIdentifier '11'
    Then metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | 222                     | 10        |

    And complete inventory with inventoryIdentifier '12'
    And metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | 222                     | 16        |
