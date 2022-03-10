@from:cucumber
@topic:stock
Feature: material dispo and ATP changes in accordance to an inventory
  As a user
  I want the material dispo to be updated properly if inventory is changed
  So that the ATP is always correct

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And delete transactional data and stock data
    And metasfresh contains M_Products:
      | Identifier | Name            |
      | 20220309   | product20220309 |
    And metasfresh has date and time 2022-03-08T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @topic:materialdispo
  @dev:runThisOne
  Scenario: Changes stock by adding inventory
    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo |
      | 20220309_1                | 2022-03-09T06:00:00Z | 20220309_1 |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | OPT.M_Product_ID |
      | 20220309_1                | 21                            | 20220309                | 0       | 10       | null             |

    When complete inventory with inventoryIdentifier '20220309_1'

    # TODO
    #Then metasfresh has this MD_Candidate data
    #  | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
    #  | c_1        | INVENTORY_UP      |                               | 20220309                | 2022-03-08T23:00:00.00Z | 10  | 10                     |
    