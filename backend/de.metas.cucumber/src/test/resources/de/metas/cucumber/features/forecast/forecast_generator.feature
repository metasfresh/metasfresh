@from:cucumber
@ghActions:run_on_executor5
Feature: Forecast Generator generates forecast lines from historical sales data

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-07T13:30:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

  @from:cucumber
  Scenario: Rolling 52-week average forecast
    Given metasfresh contains M_Products:
      | Identifier |
      | product_1  |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | IsVendor |
      | customer_fc | Y          | N        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_fc      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_fc      | ps_fc              | DE                       | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_fc     | pl_fc          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_fc      | plv_fc                 | product_1    | 10.0     | PCE                | Normal                        |

    # Create historical sales orders (52 weeks back from reference date 2026-03-07)
    # Total qty across these orders: 520 (4 orders x 130 each)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | so_1       | true    | customer_fc   | 2025-04-01  | ps_fc              |
      | so_2       | true    | customer_fc   | 2025-07-01  | ps_fc              |
      | so_3       | true    | customer_fc   | 2025-10-01  | ps_fc              |
      | so_4       | true    | customer_fc   | 2026-01-15  | ps_fc              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | product_1    | 130        |
      | sol_2      | so_2       | product_1    | 130        |
      | sol_3      | so_3       | product_1    | 130        |
      | sol_4      | so_4       | product_1    | 130        |
    And the order identified by so_1 is completed
    And the order identified by so_2 is completed
    And the order identified by so_3 is completed
    And the order identified by so_4 is completed

    # Product planning with forecast parameters
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | IsCreatePlan | IsManufactured |
      | product_1    | false        | false          |
    And update PP_Product_Planning forecast columns for product 'product_1':
      | Forecast_ComparisonPeriod | Forecast_PrecisionUnit | Forecast_Frequency | Forecast_BufferTime | IsExcludeFromForecast |
      | 0                         | W                      | 8                  | 4                   | N                     |

    # Create empty forecast
    And metasfresh contains M_Forecasts:
      | Identifier  | DatePromised | M_Warehouse_ID |
      | forecast_52 | 2026-03-07   | warehouseStd   |

    # Run forecast generator process
    When the M_Forecast_GenerateLines process is invoked for forecast 'forecast_52'

    # Verify: 520 total sales / 52 weeks * 12 weeks (8 freq + 4 buffer) = 120
    Then the forecast identified by 'forecast_52' has forecast lines:
      | M_Product_ID | Qty    |
      | product_1    | 120.00 |

  @from:cucumber
  Scenario: Phase-aligned same period previous year forecast
    Given metasfresh contains M_Products:
      | Identifier |
      | product_2  |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | IsVendor |
      | customer_pa | Y          | N        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_pa      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_pa      | ps_pa              | DE                       | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_pa     | pl_pa          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_pa      | plv_pa                 | product_2    | 5.0      | PCE                | Normal                        |

    # Sales in the same 8-week window last year (2025-03-07 to 2025-05-02)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | so_pa_1    | true    | customer_pa   | 2025-03-15  | ps_pa              |
      | so_pa_2    | true    | customer_pa   | 2025-04-10  | ps_pa              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_pa_1   | so_pa_1    | product_2    | 80         |
      | sol_pa_2   | so_pa_2    | product_2    | 83         |
    And the order identified by so_pa_1 is completed
    And the order identified by so_pa_2 is completed

    # Product planning: method 4 (phase-aligned), 8 week horizon, no buffer
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | IsCreatePlan | IsManufactured |
      | product_2    | false        | false          |
    And update PP_Product_Planning forecast columns for product 'product_2':
      | Forecast_ComparisonPeriod | Forecast_PrecisionUnit | Forecast_Frequency | Forecast_BufferTime | IsExcludeFromForecast |
      | 4                         | W                      | 8                  | 0                   | N                     |

    # Create empty forecast
    And metasfresh contains M_Forecasts:
      | Identifier  | DatePromised | M_Warehouse_ID |
      | forecast_pa | 2026-03-07   | warehouseStd   |

    # Run forecast generator process
    When the M_Forecast_GenerateLines process is invoked for forecast 'forecast_pa'

    # Verify: phase-aligned returns raw qty from same period last year = 80 + 83 = 163
    Then the forecast identified by 'forecast_pa' has forecast lines:
      | M_Product_ID | Qty    |
      | product_2    | 163.00 |

  @from:cucumber
  Scenario: Product excluded from forecast by category flag
    Given metasfresh contains M_Products:
      | Identifier |
      | product_3  |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | IsVendor |
      | customer_ex | Y          | N        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_ex      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_ex      | ps_ex              | DE                       | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_ex     | pl_ex          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ex      | plv_ex                 | product_3    | 10.0     | PCE                | Normal                        |

    # Some sales data
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | so_ex      | true    | customer_ex   | 2025-06-15  | ps_ex              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_ex     | so_ex      | product_3    | 200        |
    And the order identified by so_ex is completed

    # Product planning with forecast params but product excluded
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | IsCreatePlan | IsManufactured |
      | product_3    | false        | false          |
    And update PP_Product_Planning forecast columns for product 'product_3':
      | Forecast_ComparisonPeriod | Forecast_PrecisionUnit | Forecast_Frequency | Forecast_BufferTime | IsExcludeFromForecast |
      | 0                         | W                      | 12                 | 0                   | Y                     |

    # Create empty forecast
    And metasfresh contains M_Forecasts:
      | Identifier  | DatePromised | M_Warehouse_ID |
      | forecast_ex | 2026-03-07   | warehouseStd   |

    # Run forecast generator process
    When the M_Forecast_GenerateLines process is invoked for forecast 'forecast_ex'

    # Verify: no forecast line for the excluded product
    Then the forecast identified by 'forecast_ex' has no forecast line for product 'product_3'
