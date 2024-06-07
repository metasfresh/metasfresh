@ghActions:run_on_executor5
Feature: Extend Modular Contract Period

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |

  @Id:S0300_300
  @from:cucumber
  Scenario: Fail Extend - Contract Period with Contract Term of type Modular Contract & OnFlatrateTermExtend = Extension Not Allowed is OK
  - Contract Conditions of type Modular Contract & OnFlatrateTermExtend is Extension Not Allowed already exists
  - Contract Period of Modular Contract already exists
  - extend contract period for a given date
  - fail with message : Extension Not Allowed

    Given metasfresh contains M_PricingSystems
      | Identifier               | Name                                | Value                               |
      | modularCntrPricingSystem | modularCntrPricingSystem_23072023_1 | modularCntrPricingSystem_23072023_1 |


    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | modularCntrPricingSystem      | DE                        | EUR                 | pl_280822 | false | false         | 2              | true         |

    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  | Name                 |
      | plv_1      | pl_1                      | 2022-08-01 | PriceListVersionTest |



    And metasfresh contains M_Products:
      | Identifier         | Name                               |
      | module_log_product | module_log_product_test_07042023_1 |



    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | module_log_product      | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier           | Name                            | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_modularCntrPeriod | bp_modularCntrPeriod_23072023_1 | Y            | modularCntrPricingSystem      | 1000002                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_modularCntrPeriod_Location | 5823198505483 | bp_modularCntrPeriod     | true                | true                |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | settings_23072023_1 | module_log_product          | harvesting_calendar      | y2022                | modularCntrPricingSystem          |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier              | Name                             | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularCntrConditions_1 | modularCntrConditions_23072023_1 | ModularContract | modularCntrPricingSystem          | Ex                       | modCntr_settings_1                 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier        | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | modularCntrTerm_1 | modularCntrConditions_1             | bp_modularCntrPeriod        | 2021-10-31 | 2022-10-30 | module_log_product          |

    And load AD_Message:
      | Identifier            | Value                                         |
      | extension_not_allowed | MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED |

    And add I_AD_PInstance with id 240720231

    Then extend C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | StartDate  | AD_PInstance_ID | OPT.AD_Message_ID.Identifier |
      | modularCntrTerm_1             | 2022-10-31 | 240720231       | extension_not_allowed        |

  @Id:S0300_400
  @from:cucumber
  Scenario:  Regression - Extend Contract Period with any Contract Term had OnFlatrateTermExtend = Ex (Extension Not Allowed)
  - Contract Period with Contract Term (Subscription) had OnFlatrateTermExtend=Ca already exists in metasfresh
  - Extend contract period
  - Contract Period is extended

    Given metasfresh contains M_PricingSystems
      | Identifier | Name              | Value     | OPT.IsActive |
      | ps_1       | PricingSystemName | ps_280822 | true         |

    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | pl_280822 | true  | false         | 2              | true         |

    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  | Name                 |
      | plv_1      | pl_1                      | 2022-08-01 | PriceListVersionTest |

    And metasfresh contains M_Products:
      | Identifier | Name                 | Value                      |
      | product_1  | Product_25_08_2022_1 | Product_Value_25_08_2022_1 |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_1               | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier        | Name                       | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_abo_customer_1 | bp_abo_customer_23072023_1 | Y              | ps_1                          | 1000002                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                 | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_abo_customer_location_1 | 5823198505483 | bp_abo_customer_1        | true                | true                |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                  | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsBillToContact_Default | OPT.IsShipToContact_Default |
      | billBPUser_1          | BillBPartnerContact_1 | bp_abo_customer_1            | bp_abo_customer_location_1            | Y                           | Y                           |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier          | Name                | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.InvoiceRule
      | abo_test_23072023_1 | Abo_test_23072023_1 | Subscr          | ps_1                              | Ca                       | D

    And metasfresh contains C_Flatrate_Terms:
      | Identifier    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier |
      | aboCntrTerm_1 | abo_test_23072023_1                 | bp_abo_customer_1           | 2021-10-31 | 2022-10-30 | product_1                   | bp_abo_customer_1                   |

    And add I_AD_PInstance with id 240720232

    When extend C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | StartDate  | AD_PInstance_ID |
      | aboCntrTerm_1                 | 2022-10-31 | 240720232       |

    Then C_Flatrate_Term identified by aboCntrTerm_1 is extended
