@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: Forecast ASI enrichment from PP_Product_Planning
  When a forecast line has no ASI but PP_Product_Planning has one,
  the event creator enriches the MD_Candidate with the planning ASI.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_convenienceSalate   |

  @Id:S0463_100
  @from:cucumber
  Scenario: Forecast line with explicit ASI — MD_Candidate gets the forecast line's ASI
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
    And metasfresh contains M_AttributeSetInstance with identifier "planningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_AttributeSetInstance with identifier "forecastASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | M_AttributeSetInstance_ID |
      | ppln_1     | p_1          | false        | planningASI               |
    And metasfresh contains M_Forecasts:
      | Identifier | Name | DatePromised | M_Warehouse_ID |
      | f_1        | test | 2021-04-17   | warehouse      |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 | M_AttributeSetInstance_ID |
      | f_1           | p_1          | 10  | warehouse      | PCE                | forecastASI               |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | M_AttributeSetInstance_ID |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  | forecastASI               |

  @Id:S0463_110
  @from:cucumber
  Scenario: Forecast line WITHOUT ASI, PP_Product_Planning HAS ASI — enrichment from planning
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
    And metasfresh contains M_AttributeSetInstance with identifier "planningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | M_AttributeSetInstance_ID |
      | ppln_1     | p_1          | false        | planningASI               |
    And metasfresh contains M_Forecasts:
      | Identifier | Name | DatePromised | M_Warehouse_ID |
      | f_1        | test | 2021-04-17   | warehouse      |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 10  | warehouse      | PCE                |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | M_AttributeSetInstance_ID |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  | planningASI               |

  @Id:S0463_120
  @from:cucumber
  Scenario: Forecast line WITHOUT ASI, PP_Product_Planning also no ASI — storageAttributesKey is NONE
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan |
      | ppln_1     | p_1          | false        |
    And metasfresh contains M_Forecasts:
      | Identifier | Name | DatePromised | M_Warehouse_ID |
      | f_1        | test | 2021-04-17   | warehouse      |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 10  | warehouse      | PCE                |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  | 0                      |

  @Id:S0463_130
  @from:cucumber
  Scenario: Forecast line WITHOUT ASI, attribute-DEPENDENT planning — enrichment from planning
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
    And metasfresh contains M_AttributeSetInstance with identifier "planningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | M_AttributeSetInstance_ID | IsAttributeDependant |
      | ppln_1     | p_1          | false        | planningASI               | true                 |
    And metasfresh contains M_Forecasts:
      | Identifier | Name | DatePromised | M_Warehouse_ID |
      | f_1        | test | 2021-04-17   | warehouse      |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 10  | warehouse      | PCE                |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | M_AttributeSetInstance_ID |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  | planningASI               |
