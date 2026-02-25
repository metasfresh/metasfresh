@from:cucumber
@ghActions:run_on_executor5
Feature: Package Licensing Product Report (gh#28225)
  Verifies that the SQL function report.Package_Licensing_Product_Report()
  returns the correct product master data for packaging licensing.

  Background:
    Given infrastructure and metasfresh are running

  @Id:S28225_10
  Scenario: S28225_10 - Report returns products with packaging licensing data (no country filter)
    Given metasfresh contains M_Products:
      | Identifier | Name            |
      | p_plr_1    | PLR Test Prod 1 |
      | p_plr_2    | PLR Test Prod 2 |
    And package licensing test data is set up:
      | M_Product_ID.Identifier | C_Country_ID | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_plr_1                 | 101          | Lebensmittel     | Glas                       | 0.150                | Kunststoffe                | 0.030                |
      | p_plr_2                 | 101          | Tiernahrung      | PPK                        | 0.200                |                            |                      |
    When the Package Licensing Product Report is executed without country filter
    Then the Package Licensing Product Report result contains:
      | ProductName     | ProductGroup | SmallPackagingMaterial | SmallPackagingWeight | OverpackMaterial | OverpackWeight |
      | PLR Test Prod 1 | Lebensmittel | Glas                   | 0.150                | Kunststoffe      | 0.030          |
      | PLR Test Prod 2 | Tiernahrung  | PPK                    | 0.200                |                  |                |

  @Id:S28225_20
  Scenario: S28225_20 - Report filters by country
    Given metasfresh contains M_Products:
      | Identifier | Name            |
      | p_plr_3    | PLR Test Prod 3 |
      | p_plr_4    | PLR Test Prod 4 |
    And package licensing test data is set up:
      | M_Product_ID.Identifier | C_Country_ID | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_plr_3                 | 101          | Lebensmittel DE  | Glas DE                    | 0.100                | Kunststoffe DE             | 0.020                |
      | p_plr_4                 | 108          | Lebensmittel AT  | Glas AT                    | 0.100                | Kunststoffe AT             | 0.020                |
    When the Package Licensing Product Report is executed with C_Country_ID 101
    Then the Package Licensing Product Report result contains:
      | ProductName     | ProductGroup    | SmallPackagingMaterial | SmallPackagingWeight | OverpackMaterial | OverpackWeight |
      | PLR Test Prod 3 | Lebensmittel DE | Glas DE                | 0.100                | Kunststoffe DE   | 0.020          |
      | PLR Test Prod 4 |                 |                        | 0.100                |                  | 0.020          |
