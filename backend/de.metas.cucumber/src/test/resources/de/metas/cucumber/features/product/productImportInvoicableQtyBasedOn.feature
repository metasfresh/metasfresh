@from:cucumber
@ghActions:run_on_executor3
Feature: Product import with InvoicableQtyBasedOn
  As a user importing products
  I want to specify InvoicableQtyBasedOn (CatchWeight or Nominal) during product import
  So that the imported product prices have the correct invoicing basis

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier | Name                    | Value                    |
      | ps_import  | PS_ProductImportTest    | PS_ProductImportTest     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_import  | ps_import                     | DE                        | EUR                 | PL_ProductImportTest | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | plv_import  | pl_import                 | PLV_ProductImportTest | 2022-01-01 |

  @from:cucumber
  Scenario: Product import sets InvoicableQtyBasedOn to CatchWeight on M_ProductPrice
    Given metasfresh contains M_Products:
      | Identifier       | Name                    |
      | product_cw_test  | ProductImport_CW_Test   |
    And metasfresh contains I_Product:
      | Identifier | Value                 | Name                  | M_Product_ID    | M_PriceList_Version_ID | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 | QtyCU_UOM_ID.X12DE355 | PriceStd | InvoicableQtyBasedOn |
      | ip_cw      | ProductImport_CW_Test | ProductImport_CW_Test | product_cw_test | plv_import             | Normal                        | PCE                | PCE                   | 10.00    | CatchWeight          |
    When the ProductImportProcess is run
    Then M_ProductPrice is found:
      | M_Product_ID    | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | product_cw_test | plv_import             | CatchWeight          | 10.00    |

  @from:cucumber
  Scenario: Product import sets InvoicableQtyBasedOn to Nominal on M_ProductPrice
    Given metasfresh contains M_Products:
      | Identifier        | Name                     |
      | product_nom_test  | ProductImport_Nom_Test   |
    And metasfresh contains I_Product:
      | Identifier | Value                    | Name                     | M_Product_ID     | M_PriceList_Version_ID | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 | PriceStd | InvoicableQtyBasedOn |
      | ip_nom     | ProductImport_Nom_Test   | ProductImport_Nom_Test   | product_nom_test | plv_import             | Normal                        | PCE                | 25.00    | Nominal              |
    When the ProductImportProcess is run
    Then M_ProductPrice is found:
      | M_Product_ID     | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | product_nom_test | plv_import             | Nominal              | 25.00    |

  @from:cucumber
  Scenario: Product import defaults InvoicableQtyBasedOn to Nominal when not specified
    Given metasfresh contains M_Products:
      | Identifier        | Name                      |
      | product_def_test  | ProductImport_Def_Test    |
    And metasfresh contains I_Product:
      | Identifier | Value                     | Name                      | M_Product_ID     | M_PriceList_Version_ID | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 | PriceStd |
      | ip_def     | ProductImport_Def_Test    | ProductImport_Def_Test    | product_def_test | plv_import             | Normal                        | PCE                | 15.00    |
    When the ProductImportProcess is run
    Then M_ProductPrice is found:
      | M_Product_ID     | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | product_def_test | plv_import             | Nominal              | 15.00    |
