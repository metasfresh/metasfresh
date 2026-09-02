@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: material dispo reacts to PP_Order doc-actions
## F5100: Material Disposition — PP_Order lifecycle

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
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

  @Id:S0462_10
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: PP_Order Complete creates supply candidate for finished product and demand for components
    Given metasfresh contains M_Products:
      | Identifier  | OPT.M_Product_Category_ID.Identifier |
      | p_finished  | standard_category                    |
      | p_component | standard_category                    |
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
      | plv_1                  | p_finished   | 10.0     | PCE      | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_finished   | 2024-01-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_component  | 2024-01-01 | 3        |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_finished   | bomVersions_1             | false        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouseStd   |
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | resource_1 | 1000000           | Y                       | PT                        | 999             |
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered              | DatePromised             | DateStartSchedule        | completeDocument |
      | ppo_1                  | MOP         | p_finished              | 10         | resource_1               | 2024-09-20T07:00:00.00Z | 2024-09-22T07:00:00.00Z | 2024-09-20T07:00:00.00Z | N                |
    When the manufacturing order identified by ppo_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP |
      | c_supply   | SUPPLY            | PRODUCTION                | p_finished   | 2024-09-22T07:00:00.00Z | 10  | 10  |
      | c_demand   | DEMAND            | PRODUCTION                | p_component  | 2024-09-20T07:00:00.00Z | 30  | -30 |

  @Id:S0462_20
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: PP_Order Void deletes supply and demand candidates
    Given metasfresh contains M_Products:
      | Identifier  | OPT.M_Product_Category_ID.Identifier |
      | p_finished  | standard_category                    |
      | p_component | standard_category                    |
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
      | plv_1                  | p_finished   | 10.0     | PCE      | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_finished   | 2024-01-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_component  | 2024-01-01 | 3        |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_finished   | bomVersions_1             | false        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouseStd   |
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | resource_1 | 1000000           | Y                       | PT                        | 999             |
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered              | DatePromised             | DateStartSchedule        | completeDocument |
      | ppo_1                  | MOP         | p_finished              | 10         | resource_1               | 2024-09-20T07:00:00.00Z | 2024-09-22T07:00:00.00Z | 2024-09-20T07:00:00.00Z | N                |
    And the manufacturing order identified by ppo_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP |
      | c_supply   | SUPPLY            | PRODUCTION                | p_finished   | 2024-09-22T07:00:00.00Z | 10  | 10  |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP |
      | c_demand   | DEMAND            | PRODUCTION                | p_component  | 2024-09-20T07:00:00.00Z | 30  | -30 |
    When the PP_Order ppo_1 is voided
    Then after not more than 60s, metasfresh has no MD_Candidate for identifier c_supply
    And after not more than 60s, metasfresh has no MD_Candidate for identifier c_demand
