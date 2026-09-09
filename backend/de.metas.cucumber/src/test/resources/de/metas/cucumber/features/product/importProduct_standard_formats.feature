@from:cucumber
@ghActions:run_on_executor5
Feature: Product Import via standard AD_ImpFormat configurations
  Validates product import through the real data pipeline using column layouts
  matching the standard metasfresh AD_ImpFormat configurations:
  - Format 531097 "Produkte Standard" (tab-separated, 11 columns)
  - Format 540062 "Product Prices Standard" (tab-separated, 9 columns, header row)

  Exercises IsStocked/ProductType default logic (gh#27540) through the formats
  that customers actually use.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And all I_Product staging records are deleted

  @from:cucumber
  Scenario: Standard format "Produkte Standard" (531097) creates products with correct IsStocked defaults
    Given no product with value 'stdProd001' exists
    And no product with value 'stdProd002' exists
    And no product with value 'stdProd003' exists
    And AD_ImpFormat "ProdStd" for table "I_Product" with format "T" and skipFirstNRows 0 and columns:
      | ColumnName               | DataType |
      | Value                    | S        |
      | Name                     | S        |
      | ProductType              | S        |
      | ProductCategory_Value    | S        |
      | X12DE355                 | S        |
      | Weight                   | N        |
      | UPC                      | S        |
      | Description              | S        |
      | C_TaxCategory_Name       | S        |
      | M_PriceList_Version_Name | S        |
      | PriceStd                 | N        |
    And C_DataImport config "ProdStd" using AD_ImpFormat "ProdStd"
    When the following data is imported via data import config "ProdStd":
      """
      stdProd001	Widget Alpha	I	Standard	PCE	1.5		Alpha widget
      stdProd002	Consulting Svc	S	Standard	PCE
      stdProd003	Basic Product		Standard	PCE
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | stdProd001 |
      | iProd_2    | stdProd002 |
      | iProd_3    | stdProd003 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name           | IsStocked | ProductType | IsActive |
      | iProd_1              | stdProd001 | Widget Alpha   | Y         | I           | Y        |
      | iProd_2              | stdProd002 | Consulting Svc | N         | S           | Y        |
      | iProd_3              | stdProd003 | Basic Product  | Y         | I           | Y        |

  @from:cucumber
  Scenario: Standard format "Product Prices Standard" (540062) with header row skip and product reactivation
    Given no product with value 'prcProd001' exists
    And no product with value 'prcProd002' exists
    And metasfresh contains M_Products:
      | Identifier | Value      | Name            |
      | prod_1     | prcProd001 | Price Product 1 |
      | prod_2     | prcProd002 | Price Product 2 |
    And update M_Product:
      | M_Product_ID.Identifier | IsActive |
      | prod_2                  | N        |
    And AD_ImpFormat "ProdPrices" for table "I_Product" with format "T" and skipFirstNRows 1 and columns:
      | ColumnName | DataType |
      | Value      | S        |
      | Name       | S        |
    And C_DataImport config "ProdPrices" using AD_ImpFormat "ProdPrices"
    When the following data is imported via data import config "ProdPrices":
      """
      Value	Name
      prcProd001	Price Product 1
      prcProd002	Price Product 2
      """
    Then load imported I_Product records by Value:
      | Identifier | Value      |
      | iProd_1    | prcProd001 |
      | iProd_2    | prcProd002 |
    And validate M_Product for I_Product:
      | I_Product_Identifier | Value      | Name            | IsStocked | ProductType | IsActive |
      | iProd_1              | prcProd001 | Price Product 1 | Y         | I           | Y        |
      | iProd_2              | prcProd002 | Price Product 2 | Y         | I           | Y        |
