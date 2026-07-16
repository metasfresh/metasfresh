@from:cucumber
@ghActions:run_on_executor3
Feature: Product import with InvoicableQtyBasedOn
  As a user importing products via CSV
  I want to specify InvoicableQtyBasedOn (CatchWeight or Nominal) during product import
  So that the imported product prices have the correct invoicing basis

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_import  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_import  | ps_import          | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_import | pl_import      |

    And metasfresh contains AD_ImpFormat:
      | Identifier | TableName |
      | impFmt     | I_Product |
    And AD_ImpFormat identified by "impFmt" has columns:
      | ColumnName             | DataType |
      | Value                  | S        |
      | Name                   | S        |
      | X12DE355               | S        |
      | M_PriceList_Version_ID | N        |
      | PriceStd               | N        |
      | InvoicableQtyBasedOn   | S        |
      | QtyCU_UOM_Code         | S        |
    And metasfresh contains C_DataImport:
      | Identifier | AD_ImpFormat_ID |
      | dataImp    | impFmt          |

  @from:cucumber
  Scenario: Product import sets InvoicableQtyBasedOn to CatchWeight on M_ProductPrice
    Given metasfresh contains M_Products:
      | Identifier      | Value                          | Name                           |
      | product_cw_test | ProductImportCW_InvQtyBasedOn  | ProductImportCW_InvQtyBasedOn  |
    When the following CSV data is imported using C_DataImport identified by "dataImp":
      | Value                         | Name                          | X12DE355 | M_PriceList_Version_ID | PriceStd | InvoicableQtyBasedOn | QtyCU_UOM_Code |
      | ProductImportCW_InvQtyBasedOn | ProductImportCW_InvQtyBasedOn | PCE      | plv_import             | 10.00    | CatchWeight          | Stk            |
    Then M_ProductPrice is found:
      | M_Product_ID    | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | product_cw_test | plv_import             | CatchWeight          | 10.00    |

  @from:cucumber
  Scenario: Product import sets InvoicableQtyBasedOn to Nominal on M_ProductPrice
    Given metasfresh contains M_Products:
      | Identifier       | Value                           | Name                            |
      | product_nom_test | ProductImportNom_InvQtyBasedOn  | ProductImportNom_InvQtyBasedOn  |
    When the following CSV data is imported using C_DataImport identified by "dataImp":
      | Value                          | Name                           | X12DE355 | M_PriceList_Version_ID | PriceStd | InvoicableQtyBasedOn |
      | ProductImportNom_InvQtyBasedOn | ProductImportNom_InvQtyBasedOn | PCE      | plv_import             | 25.00    | Nominal              |
    Then M_ProductPrice is found:
      | M_Product_ID     | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | product_nom_test | plv_import             | Nominal              | 25.00    |

  @from:cucumber
  Scenario: Product import defaults InvoicableQtyBasedOn to Nominal when not specified
    Given metasfresh contains M_Products:
      | Identifier       | Value                           | Name                            |
      | product_def_test | ProductImportDef_InvQtyBasedOn  | ProductImportDef_InvQtyBasedOn  |
    When the following CSV data is imported using C_DataImport identified by "dataImp":
      | Value                          | Name                           | X12DE355 | M_PriceList_Version_ID | PriceStd |
      | ProductImportDef_InvQtyBasedOn | ProductImportDef_InvQtyBasedOn | PCE      | plv_import             | 15.00    |
    Then M_ProductPrice is found:
      | M_Product_ID     | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | product_def_test | plv_import             | Nominal              | 15.00    |
