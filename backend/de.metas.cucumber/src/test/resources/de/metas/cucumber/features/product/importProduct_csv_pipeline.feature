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

  @from:cucumber
  Scenario: CSV import creates products with correct IsStocked/ProductType defaults
    Given AD_ImpFormat "ProductCSV" for table "I_Product" with columns:
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
      csvProd002,Consulting Service,S,N,Standard,HUR
      csvProd003,Basic Item,,,Standard,PCE
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | csvProd001 |
      | iProd_2    | csvProd002 |
      | iProd_3    | csvProd003 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | IsStocked | ProductType | IsActive |
      | iProd_1              | Y         | I           | Y        |
      | iProd_2              | N         | S           | Y        |
      | iProd_3              | Y         | I           | Y        |
