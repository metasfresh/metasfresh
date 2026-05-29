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
      | Identifier          |
      | product_rolling52w  |
    And metasfresh contains C_BPartners:
      | Identifier           | IsCustomer | IsVendor |
      | customer_rolling52w  | Y          | N        |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_rolling52w   |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_rolling52w  | ps_rolling52w      | DE                       | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_rolling52w  | pl_rolling52w  |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_rolling52w  | plv_rolling52w         | product_rolling52w | 10.0     | PCE                | Normal                        |

    # Create historical sales orders (52 weeks back from reference date 2026-03-07)
    # Total qty across these orders: 520 (4 orders x 130 each)
    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID       | DateOrdered | M_PricingSystem_ID |
      | so_rolling52w_Q2 | true    | customer_rolling52w | 2025-04-01  | ps_rolling52w      |
      | so_rolling52w_Q3 | true    | customer_rolling52w | 2025-07-01  | ps_rolling52w      |
      | so_rolling52w_Q4 | true    | customer_rolling52w | 2025-10-01  | ps_rolling52w      |
      | so_rolling52w_Q1 | true    | customer_rolling52w | 2026-01-15  | ps_rolling52w      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID       | M_Product_ID       | QtyEntered |
      | sol_rolling52w_Q2 | so_rolling52w_Q2 | product_rolling52w | 130        |
      | sol_rolling52w_Q3 | so_rolling52w_Q3 | product_rolling52w | 130        |
      | sol_rolling52w_Q4 | so_rolling52w_Q4 | product_rolling52w | 130        |
      | sol_rolling52w_Q1 | so_rolling52w_Q1 | product_rolling52w | 130        |
    And the order identified by so_rolling52w_Q2 is completed
    And the order identified by so_rolling52w_Q3 is completed
    And the order identified by so_rolling52w_Q4 is completed
    And the order identified by so_rolling52w_Q1 is completed

    # Product planning with forecast parameters
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID       | IsCreatePlan | IsManufactured |
      | product_rolling52w | false        | false          |
    And update PP_Product_Planning forecast columns for product 'product_rolling52w':
      | Forecast_CalculationMethod | Forecast_PrecisionUnit | Forecast_Frequency | Forecast_BufferTime | IsExcludeFromForecast |
      | 0                         | W                      | 8                  | 4                   | N                     |

    # Create empty forecast
    And metasfresh contains M_Forecasts:
      | Identifier          | DatePromised | M_Warehouse_ID |
      | forecast_rolling52w | 2026-03-07   | warehouseStd   |

    # Run forecast generator process
    When the M_Forecast_GenerateLines process is invoked for forecast 'forecast_rolling52w'

    # Verify: 520 total sales / 52 weeks * 12 weeks (8 freq + 4 buffer) = 120
    Then the forecast identified by 'forecast_rolling52w' has forecast lines:
      | M_Product_ID       | Qty    |
      | product_rolling52w | 120.00 |

  @from:cucumber
  Scenario: Phase-aligned same period previous year forecast
    Given metasfresh contains M_Products:
      | Identifier              |
      | product_phaseAligned    |
    And metasfresh contains C_BPartners:
      | Identifier               | IsCustomer | IsVendor |
      | customer_phaseAligned    | Y          | N        |
    And metasfresh contains M_PricingSystems
      | Identifier          |
      | ps_phaseAligned     |
    And metasfresh contains M_PriceLists
      | Identifier         | M_PricingSystem_ID  | C_Country_ID.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_phaseAligned    | ps_phaseAligned     | DE                       | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID  |
      | plv_phaseAligned    | pl_phaseAligned |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_phaseAligned    | plv_phaseAligned       | product_phaseAligned | 5.0      | PCE                | Normal                        |

    # Sales in the same 8-week window last year (2025-03-07 to 2025-05-02)
    And metasfresh contains C_Orders:
      | Identifier              | IsSOTrx | C_BPartner_ID         | DateOrdered | M_PricingSystem_ID |
      | so_phaseAligned_march   | true    | customer_phaseAligned | 2025-03-15  | ps_phaseAligned    |
      | so_phaseAligned_april   | true    | customer_phaseAligned | 2025-04-10  | ps_phaseAligned    |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID            | M_Product_ID         | QtyEntered |
      | sol_phaseAligned_march   | so_phaseAligned_march | product_phaseAligned | 80         |
      | sol_phaseAligned_april   | so_phaseAligned_april | product_phaseAligned | 83         |
    And the order identified by so_phaseAligned_march is completed
    And the order identified by so_phaseAligned_april is completed

    # Product planning: method 4 (phase-aligned), 8 week horizon, no buffer
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID         | IsCreatePlan | IsManufactured |
      | product_phaseAligned | false        | false          |
    And update PP_Product_Planning forecast columns for product 'product_phaseAligned':
      | Forecast_CalculationMethod | Forecast_PrecisionUnit | Forecast_Frequency | Forecast_BufferTime | IsExcludeFromForecast |
      | 4                         | W                      | 8                  | 0                   | N                     |

    # Create empty forecast
    And metasfresh contains M_Forecasts:
      | Identifier              | DatePromised | M_Warehouse_ID |
      | forecast_phaseAligned   | 2026-03-07   | warehouseStd   |

    # Run forecast generator process
    When the M_Forecast_GenerateLines process is invoked for forecast 'forecast_phaseAligned'

    # Verify: phase-aligned returns raw qty from same period last year = 80 + 83 = 163
    Then the forecast identified by 'forecast_phaseAligned' has forecast lines:
      | M_Product_ID         | Qty    |
      | product_phaseAligned | 163.00 |

  @from:cucumber
  Scenario: Product excluded from forecast by exclusion flag
    Given metasfresh contains M_Products:
      | Identifier         |
      | product_excluded   |
    And metasfresh contains C_BPartners:
      | Identifier          | IsCustomer | IsVendor |
      | customer_excluded   | Y          | N        |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_excluded    |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_excluded   | ps_excluded        | DE                       | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_excluded   | pl_excluded    |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID     | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_excluded   | plv_excluded           | product_excluded | 10.0     | PCE                | Normal                        |

    # Some sales data
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID     | DateOrdered | M_PricingSystem_ID |
      | so_excluded   | true    | customer_excluded | 2025-06-15  | ps_excluded        |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID  | M_Product_ID     | QtyEntered |
      | sol_excluded   | so_excluded | product_excluded | 200        |
    And the order identified by so_excluded is completed

    # Product planning with forecast params but product excluded
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID     | IsCreatePlan | IsManufactured |
      | product_excluded | false        | false          |
    And update PP_Product_Planning forecast columns for product 'product_excluded':
      | Forecast_CalculationMethod | Forecast_PrecisionUnit | Forecast_Frequency | Forecast_BufferTime | IsExcludeFromForecast |
      | 0                         | W                      | 12                 | 0                   | Y                     |

    # Create empty forecast
    And metasfresh contains M_Forecasts:
      | Identifier         | DatePromised | M_Warehouse_ID |
      | forecast_excluded  | 2026-03-07   | warehouseStd   |

    # Run forecast generator process
    When the M_Forecast_GenerateLines process is invoked for forecast 'forecast_excluded'

    # Verify: no forecast line for the excluded product
    Then the forecast identified by 'forecast_excluded' has no forecast line for product 'product_excluded'
