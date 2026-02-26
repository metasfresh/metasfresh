@from:cucumber
@ghActions:run_on_executor5
Feature: Product Import default values and inactive product reactivation
  Validates that the product import process applies sensible defaults
  and reactivates inactive products on upsert (gh#27540).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Product import defaults IsStocked=Y and ProductType=Item
    Given metasfresh contains I_Product:
      | Identifier | Value                   | Name              | X12DE355 |
      | iProd_1    | IMPORT_DEFAULTS_TEST_01 | Default Test Prod | PCE      |
    When the ImportProduct process is invoked
    Then validate M_Product for I_Product:
      | I_Product_Identifier | IsStocked | ProductType | IsActive |
      | iProd_1              | Y         | I           | Y        |

  @from:cucumber
  Scenario: Product import respects explicit IsStocked=N
    Given metasfresh contains I_Product:
      | Identifier | Value                   | Name                 | X12DE355 | IsStocked |
      | iProd_2    | IMPORT_DEFAULTS_TEST_02 | Not Stocked Product  | PCE      | N         |
    When the ImportProduct process is invoked
    Then validate M_Product for I_Product:
      | I_Product_Identifier | IsStocked | ProductType |
      | iProd_2              | N         | I           |

  @from:cucumber
  Scenario: Product import reactivates inactive product on upsert
    Given metasfresh contains M_Products:
      | Identifier | Value                   | Name                 |
      | prod_exist | IMPORT_DEFAULTS_TEST_03 | Existing Product     |
    And update M_Product:
      | M_Product_ID.Identifier | IsActive |
      | prod_exist              | N        |
    And metasfresh contains I_Product:
      | Identifier | Value                   | Name                      | X12DE355 |
      | iProd_3    | IMPORT_DEFAULTS_TEST_03 | Existing Product Reimport | PCE      |
    When the ImportProduct process is invoked
    Then validate M_Product for I_Product:
      | I_Product_Identifier | IsActive | IsStocked |
      | iProd_3              | Y        | Y         |
