@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8010.1_Multi_level_BOM_Report
@ghActions:run_on_executor5
Feature: multi-level BOM report

  # The BOM report explodes a BOM over all levels and returns, per level, the cumulative quantity
  # per unit of the finished product and the cost of that row (cumulative qty x current unit cost).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'


  # ####################################################################################################
  # ####################################################################################################
  # Scenario 1: multi-level bill of materials; cumulative quantity per level and the cost report
  # ####################################################################################################
  # ####################################################################################################
  @from:cucumber
  Scenario: the recursive BOM report returns every level with its cumulative quantity
    # top (1 pc) = 2 x intermediate + 3 x componentA; intermediate = 4 x componentB + 5 x componentC
    Given metasfresh contains M_Products:
      | Identifier    | X12DE355 |
      | top           | PCE      |
      | intermediate  | PCE      |
      | componentA    | PCE      |
      | componentB    | PCE      |
      | componentC    | PCE      |
    And metasfresh contains PP_Product_BOM
      | Identifier      | M_Product_ID |
      | bomIntermediate | intermediate |
      | bomTop          | top          |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | line_B     | bomIntermediate   | componentB   | 4        |
      | line_C     | bomIntermediate   | componentC   | 5        |
      | line_I     | bomTop            | intermediate | 2        |
      | line_A     | bomTop            | componentA   | 3        |
    And the PP_Product_BOM identified by bomIntermediate is completed
    And the PP_Product_BOM identified by bomTop is completed
    # unit cost of the leaf components (cost element of the primary accounting schema, the one the report reads)
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | componentA   | 5                |
      | componentB   | 2                |
      | componentC   | 3                |

    Then the BOM recursive explosion of bomTop returns:
      | Line    | cumulative_qty |
      | 1.      | 1              |
      | 1.1.    | 2              |
      | 1.1.1.  | 8              |
      | 1.1.2.  | 10             |
      | 1.2.    | 3              |
    # leaf cost = cumulative qty x unit cost (B 8 x 2, C 10 x 3, A 3 x 5); the BOM-node lines 1. and 1.1. are not asserted
    And the BOM recursive report of bomTop returns:
      | Line    | QtyBOM | Cost |
      | 1.      | 1      |      |
      | 1.1.    | 2      |      |
      | 1.1.1.  | 4      | 16   |
      | 1.1.2.  | 5      | 30   |
      | 1.2.    | 3      | 15   |
