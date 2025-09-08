@from:cucumber
@ghActions:run_on_executor6
Feature: material dispo reacts to forecast docactions

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier   | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_convenienceSalate   |
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 0        |

  @from:cucumber
  Scenario: Forecast is correctly considered in Material Dispo when the product is Manufactured
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
      | p_2        | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | SOTrx | C_Country_ID | C_Currency_ID |
      | pl_1       | ps_1               | true  | DE           | EUR           |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_1          | 10.0     | PCE      | Normal           |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | 2021-04-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_2          | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_HU_PI:
      | Identifier         |
      | huPackingLU        |
      | huPackingTU        |
      | huPackingVirtualPI |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID         | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU        | LU          | Y         |
      | packingVersionTU              | huPackingTU        | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 10  | 2020-02-01 |
    And metasfresh contains M_Forecasts:
      | Identifier | Name            | DatePromised | M_Warehouse_ID |
      | f_1        | test_03032025_6 | 2021-04-17   | warehouseStd   |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 10  | warehouseStd   | PCE               |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  | 0                      |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T22:00:00Z | 10  | 10                     |
      | c_3        | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T22:00:00Z | 100 | -100                   |
    When the forecast identified by f_1 is reactivated
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T22:00:00Z | 10  | 10                     |
      | c_3        | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T22:00:00Z | 100 | -100                   |
