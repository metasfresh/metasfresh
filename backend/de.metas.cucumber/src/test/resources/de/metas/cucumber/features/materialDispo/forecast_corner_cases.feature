@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: forecast corner cases for material dispo
## F5100: Material Disposition — Forecast edge cases and complex interactions

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |

  @Id:S0464_10
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Two forecasts for same product and warehouse create independent STOCK_UP candidates
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouseStd   |
    And metasfresh contains M_Forecasts:
      | Identifier | Name   | DatePromised | M_Warehouse_ID |
      | f_1        | test_1 | 2021-04-17   | warehouseStd   |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 10  | warehouseStd   | PCE               |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  |
    # Create and complete a second forecast for the same product/warehouse
    Given metasfresh contains M_Forecasts:
      | Identifier | Name   | DatePromised | M_Warehouse_ID |
      | f_2        | test_2 | 2021-04-17   | warehouseStd   |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_2           | p_1          | 5   | warehouseStd   | PCE               |
    When the forecast identified by f_2 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty |
      | c_1        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 10  |
      | c_2        | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 5   |

  @Id:S0464_20
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Forecast Complete then SO Complete — both coexist, forecast reactivate only removes forecast candidate
    # First create a forecast, then a sales order for the same product.
    # Both should create independent candidates.
    # Reactivating the forecast should only remove the STOCK_UP, not the SO's DEMAND.
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
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
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_1 | N        | Y          | ps_1               |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouseStd   |
    # Step 1: Create and complete forecast
    And metasfresh contains M_Forecasts:
      | Identifier | Name | DatePromised | M_Warehouse_ID |
      | f_1        | test | 2021-04-17   | warehouseStd   |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 20  | warehouseStd   | PCE               |
    When the forecast identified by f_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_forecast | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 20  | 0   |
    # Step 2: Create and complete sales order for the same product
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1        | true    | customer_1    | 2021-04-11  | 2021-04-17T21:00:00Z | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 8          |
    When the order identified by o_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_forecast | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 20  | 0   |
      | c_demand   | DEMAND            | SHIPMENT                  | p_1          | 2021-04-17T21:00:00Z | 8   | -8  |
    # Step 3: Reactivate the forecast — only STOCK_UP should be removed, DEMAND stays
    When the forecast identified by f_1 is reactivated
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_demand   | DEMAND            | SHIPMENT                  | p_1          | 2021-04-17T21:00:00Z | 8   | -8  |

  @Id:S0464_30
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Forecast with existing stock (StockEstimateCreatedEvent) reduces supply required qty
    # Post a stock estimate event before completing the forecast.
    # The SupplyRequired qty should account for existing stock.
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_1        | standard_category                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouseStd   |
    # Post initial stock
    And metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc              | Qty |
      | p_1          | 1                  | 1                       | 2021-04-16T22:00:00Z | 30  |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_stock    | INVENTORY_UP      | STOCK_CHANGE              | p_1          | 2021-04-16T22:00:00Z | 30  | 30  |
    # Now create and complete a forecast for the same product
    And metasfresh contains M_Forecasts:
      | Identifier | Name | DatePromised | M_Warehouse_ID |
      | f_1        | test | 2021-04-17   | warehouseStd   |
    And metasfresh contains M_ForecastLines:
      | M_Forecast_ID | M_Product_ID | Qty | M_Warehouse_ID | C_UOM_ID.X12DE355 |
      | f_1           | p_1          | 20  | warehouseStd   | PCE               |
    When the forecast identified by f_1 is completed
    # The STOCK_UP candidate should be created with forecast qty (20),
    # but SupplyRequired should be reduced by existing stock
    Then after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_stock     | INVENTORY_UP      | STOCK_CHANGE              | p_1          | 2021-04-16T22:00:00Z | 30  | 30  |
      | c_forecast  | STOCK_UP          | FORECAST                  | p_1          | 2021-04-16T22:00:00Z | 20  | 0   |
