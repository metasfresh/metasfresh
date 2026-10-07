@from:cucumber
@ghActions:run_on_executor5
Feature: Product Import via full CSV pipeline
  Validates end-to-end product import through the real CSV pipeline:
  CSV text -> AD_ImpFormat parsing -> I_Product staging -> ImportProduct process -> M_Product.
  This exercises the full path customers use when importing products via CSV files,
  including IsStocked/ProductType default logic (gh#27540).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And all I_Product staging records are deleted

  @from:cucumber
  Scenario: CSV import defaults IsStocked=Y and ProductType=Item
    Given no product with value 'defProd001' exists
    And AD_ImpFormat "DefCSV1" for table "I_Product" with columns:
      | ColumnName            | DataType |
      | Value                 | S        |
      | Name                  | S        |
      | ProductCategory_Value | S        |
      | X12DE355              | S        |
    And C_DataImport config "DefCSV1" using AD_ImpFormat "DefCSV1"
    When the following CSV is imported via data import config "DefCSV1":
      """
      defProd001,Default Widget,Standard,PCE
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | defProd001 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name           | IsStocked | ProductType | IsActive |
      | iProd_1              | defProd001 | Default Widget | Y         | I           | Y        |

  @from:cucumber
  Scenario: CSV import respects explicit IsStocked=N
    Given no product with value 'defProd002' exists
    And AD_ImpFormat "DefCSV2" for table "I_Product" with columns:
      | ColumnName            | DataType |
      | Value                 | S        |
      | Name                  | S        |
      | IsStocked             | S        |
      | ProductCategory_Value | S        |
      | X12DE355              | S        |
    And C_DataImport config "DefCSV2" using AD_ImpFormat "DefCSV2"
    When the following CSV is imported via data import config "DefCSV2":
      """
      defProd002,Unstocked Widget,N,Standard,PCE
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | defProd002 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name             | IsStocked | ProductType |
      | iProd_1              | defProd002 | Unstocked Widget | N         | I           |

  @from:cucumber
  Scenario: CSV import defaults IsStocked=N for Service products
    Given no product with value 'defProd003' exists
    And AD_ImpFormat "DefCSV3" for table "I_Product" with columns:
      | ColumnName            | DataType |
      | Value                 | S        |
      | Name                  | S        |
      | ProductType           | S        |
      | ProductCategory_Value | S        |
      | X12DE355              | S        |
    And C_DataImport config "DefCSV3" using AD_ImpFormat "DefCSV3"
    When the following CSV is imported via data import config "DefCSV3":
      """
      defProd003,Consulting Service,S,Standard,PCE
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | defProd003 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name                | IsStocked | ProductType |
      | iProd_1              | defProd003 | Consulting Service  | N         | S           |

  @from:cucumber
  Scenario: CSV import reactivates inactive product on upsert
    Given no product with value 'defProd004' exists
    And metasfresh contains M_Products:
      | Identifier | Value      | Name          |
      | prod_exist | defProd004 | Existing Prod |
    And update M_Product:
      | M_Product_ID.Identifier | IsActive |
      | prod_exist              | N        |
    And AD_ImpFormat "DefCSV4" for table "I_Product" with columns:
      | ColumnName | DataType |
      | Value      | S        |
      | Name       | S        |
    And C_DataImport config "DefCSV4" using AD_ImpFormat "DefCSV4"
    When the following CSV is imported via data import config "DefCSV4":
      """
      defProd004,Existing Prod
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | defProd004 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name          | IsActive | IsStocked |
      | iProd_1              | defProd004 | Existing Prod | Y        | Y         |

  @from:cucumber
  Scenario: CSV import creates multiple products with correct IsStocked/ProductType defaults
    Given no product with value 'csvProd001' exists
    And no product with value 'csvProd002' exists
    And no product with value 'csvProd003' exists
    And AD_ImpFormat "ProductCSV" for table "I_Product" with columns:
      | ColumnName            | DataType |
      | Value                 | S        |
      | Name                  | S        |
      | ProductType           | S        |
      | IsStocked             | S        |
      | ProductCategory_Value | S        |
      | X12DE355              | S        |
    And C_DataImport config "ProductCSV" using AD_ImpFormat "ProductCSV"
    When the following CSV is imported via data import config "ProductCSV":
      """
      csvProd001,Widget Alpha,I,Y,Standard,PCE
      csvProd002,Consulting Service,S,N,Standard,PCE
      csvProd003,Basic Item,,,Standard,PCE
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | csvProd001 |
      | iProd_2    | csvProd002 |
      | iProd_3    | csvProd003 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name                | IsStocked | ProductType | IsActive |
      | iProd_1              | csvProd001 | Widget Alpha        | Y         | I           | Y        |
      | iProd_2              | csvProd002 | Consulting Service  | N         | S           | Y        |
      | iProd_3              | csvProd003 | Basic Item          | Y         | I           | Y        |
