@from:cucumber
@allure.label.epic:E0260_Pricing
@allure.label.feature:F32090_Derivative_Pricelists
@F32090
@ghActions:run_on_executor7
Feature: Derivative price lists
## F32090: Derivative Pricelists

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  # The DB allows one active price list per pricing system, sales/purchase flag and country,
  # so the second list with the same country is inactive.
  Scenario: setup - several versions and lists stay separate
    Given metasfresh contains M_PricingSystems
      | Identifier   |
      | dpl_setup_ps |
    And metasfresh contains new M_PriceLists:
      | Identifier  | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsActive | Name               |
      | dpl_setup_1 | dpl_setup_ps       | DE                    | EUR                 | true  | true     | dpl_setup_list_eur |
      | dpl_setup_2 | dpl_setup_ps       | DE                    | CHF                 | true  | false    | dpl_setup_list_chf |
    And metasfresh contains new M_PriceLists:
      | Identifier  | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | Name                  |
      | dpl_setup_3 | dpl_setup_ps       | EUR                 | true  | dpl_setup_list_nocntr |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier   | M_PriceList_ID | ValidFrom  | Description        |
      | dpl_setup_v1 | dpl_setup_1    | 2091-01-10 | dpl setup version1 |
      | dpl_setup_v2 | dpl_setup_1    | 2091-02-20 | dpl setup version2 |
    Then the M_PriceLists exist:
      | Identifier  | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | IsActive | Name               |
      | dpl_setup_1 | dpl_setup_ps       | DE                    | EUR                 | true     | dpl_setup_list_eur |
      | dpl_setup_2 | dpl_setup_ps       | DE                    | CHF                 | false    | dpl_setup_list_chf |
    And the M_PriceLists exist:
      | Identifier  | M_PricingSystem_ID | C_Currency.ISO_Code | Name                  |
      | dpl_setup_3 | dpl_setup_ps       | EUR                 | dpl_setup_list_nocntr |
    And the M_PriceList_Versions exist:
      | Identifier   | M_PriceList_ID | ValidFrom  | Description        |
      | dpl_setup_v1 | dpl_setup_1    | 2091-01-10 | dpl setup version1 |
      | dpl_setup_v2 | dpl_setup_1    | 2091-02-20 | dpl setup version2 |

  # Reference case: base 5.00, customer 2.00, new base version with a schema line "standard price +1.50" -> 3.50
  @Id:S32970_TC1
  Scenario: TC1 reference case - the new customer version gets the previous customer price plus the surcharge
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc1_prod |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | dpl_tc1_ps_b |
      | dpl_tc1_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc1_base | dpl_tc1_ps_b       | DE                    | EUR                 | true  | dpl_tc1_base |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         | BasePriceList_ID |
      | dpl_tc1_cust | dpl_tc1_ps_c       | DE                    | EUR                 | true  | dpl_tc1_cust | dpl_tc1_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  |
      | dpl_tc1_base_v1 | dpl_tc1_base   | 2092-01-10 |
      | dpl_tc1_cust_v1 | dpl_tc1_cust   | 2092-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc1_pp_base | dpl_tc1_base_v1        | dpl_tc1_prod | 5.00     | PCE               |
      | dpl_tc1_pp_cust | dpl_tc1_cust_v1        | dpl_tc1_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc1_bp | dpl_tc1_bp | Y          | dpl_tc1_ps_c       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc1_ds | P            | dpl_tc1_ds | 2092-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc1_ds          | 10    | dpl_tc1_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc1_base_v2 | dpl_tc1_base   | 2092-03-01 | dpl_tc1_ds          |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc1_pp_base_v2 | dpl_tc1_base_v2        | dpl_tc1_prod | 5.00     | PCE               |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc1_base_v2

    Then M_PriceList dpl_tc1_cust has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc1_cust is:
      | Identifier      | ValidFrom  | Name                    | M_DiscountSchema_ID | M_Pricelist_Version_Base_ID |
      | dpl_tc1_cust_v2 | 2092-03-01 | dpl_tc1_cust 2092-03-01 | dpl_tc1_ds          | dpl_tc1_cust_v1             |
    And M_ProductPrice is found:
      | M_Product_ID | M_PriceList_Version_ID | InvoicableQtyBasedOn | PriceStd |
      | dpl_tc1_prod | dpl_tc1_cust_v2        | Nominal              | 3.50     |

  # Customer price 2.40 was overwritten by hand (the base price is 5.00 / 6.00): the new price starts from it.
  # Product b has a price on the new base version and a schema line, but none on the previous customer version.
  @Id:S32970_TC2
  Scenario: TC2 manual override is the starting point and a product missing on the customer version is not added
    Given metasfresh contains M_Products:
      | Identifier     |
      | dpl_tc2_prod_a |
      | dpl_tc2_prod_b |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | dpl_tc2_ps_b |
      | dpl_tc2_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc2_base | dpl_tc2_ps_b       | DE                    | EUR                 | true  | dpl_tc2_base |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         | BasePriceList_ID |
      | dpl_tc2_cust | dpl_tc2_ps_c       | DE                    | EUR                 | true  | dpl_tc2_cust | dpl_tc2_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  |
      | dpl_tc2_base_v1 | dpl_tc2_base   | 2093-01-10 |
      | dpl_tc2_cust_v1 | dpl_tc2_cust   | 2093-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc2_pp_base_a | dpl_tc2_base_v1        | dpl_tc2_prod_a | 5.00     | PCE               |
      | dpl_tc2_pp_cust_a | dpl_tc2_cust_v1        | dpl_tc2_prod_a | 2.40     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc2_bp | dpl_tc2_bp | Y          | dpl_tc2_ps_c       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc2_ds | P            | dpl_tc2_ds | 2093-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID   | Std_AddAmt |
      | dpl_tc2_ds          | 10    | dpl_tc2_prod_a | 1.50       |
      | dpl_tc2_ds          | 20    | dpl_tc2_prod_b | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc2_base_v2 | dpl_tc2_base   | 2093-03-01 | dpl_tc2_ds          |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc2_pp_base_v2_a | dpl_tc2_base_v2        | dpl_tc2_prod_a | 6.00     | PCE               |
      | dpl_tc2_pp_base_v2_b | dpl_tc2_base_v2        | dpl_tc2_prod_b | 7.00     | PCE               |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc2_base_v2

    Then M_PriceList dpl_tc2_cust has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc2_cust is:
      | Identifier      | ValidFrom  |
      | dpl_tc2_cust_v2 | 2093-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc2_cust_v2 are:
      | M_Product_ID   | PriceStd |
      | dpl_tc2_prod_a | 3.90     |

  # Category lines: +2.00 for category up, -3.00 for category down; the product of a third category has no line.
  @Id:S32970_TC3
  Scenario: TC3 schema lines by product category, a negative surcharge and a product without matching line
    Given metasfresh contains M_Product_Categories:
      | Identifier       |
      | dpl_tc3_cat_up   |
      | dpl_tc3_cat_down |
      | dpl_tc3_cat_none |
    And metasfresh contains M_Products:
      | Identifier        | OPT.M_Product_Category_ID.Identifier |
      | dpl_tc3_prod_up   | dpl_tc3_cat_up                       |
      | dpl_tc3_prod_down | dpl_tc3_cat_down                     |
      | dpl_tc3_prod_none | dpl_tc3_cat_none                     |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | dpl_tc3_ps_b |
      | dpl_tc3_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc3_base | dpl_tc3_ps_b       | DE                    | EUR                 | true  | dpl_tc3_base |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         | BasePriceList_ID |
      | dpl_tc3_cust | dpl_tc3_ps_c       | DE                    | EUR                 | true  | dpl_tc3_cust | dpl_tc3_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  |
      | dpl_tc3_base_v1 | dpl_tc3_base   | 2094-01-10 |
      | dpl_tc3_cust_v1 | dpl_tc3_cust   | 2094-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc3_pp_up   | dpl_tc3_cust_v1        | dpl_tc3_prod_up   | 10.00    | PCE               |
      | dpl_tc3_pp_down | dpl_tc3_cust_v1        | dpl_tc3_prod_down | 10.00    | PCE               |
      | dpl_tc3_pp_none | dpl_tc3_cust_v1        | dpl_tc3_prod_none | 10.00    | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc3_bp | dpl_tc3_bp | Y          | dpl_tc3_ps_c       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc3_ds | P            | dpl_tc3_ds | 2094-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_Category_ID | Std_AddAmt |
      | dpl_tc3_ds          | 10    | dpl_tc3_cat_up        | 2.00       |
      | dpl_tc3_ds          | 20    | dpl_tc3_cat_down      | -3.00      |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc3_base_v2 | dpl_tc3_base   | 2094-03-01 | dpl_tc3_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc3_base_v2

    Then the latest M_PriceList_Version of M_PriceList dpl_tc3_cust is:
      | Identifier      | ValidFrom  |
      | dpl_tc3_cust_v2 | 2094-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc3_cust_v2 are:
      | M_Product_ID      | PriceStd |
      | dpl_tc3_prod_up   | 12.00    |
      | dpl_tc3_prod_down | 7.00     |

  # Derivatives without any partner, with a non-customer partner and with a customer that has the flag N get no
  # new version; the one with a flagged customer does. All have the base's country and currency.
  @Id:S32970_TC4
  Scenario: TC4 derivatives without a flagged customer are not updated
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc4_prod |
    And metasfresh contains M_PricingSystems
      | Identifier        |
      | dpl_tc4_ps_b      |
      | dpl_tc4_ps_nopart |
      | dpl_tc4_ps_nocust |
      | dpl_tc4_ps_flagn  |
      | dpl_tc4_ps_ok     |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc4_base | dpl_tc4_ps_b       | DE                    | EUR                 | true  | dpl_tc4_base |
    And metasfresh contains new M_PriceLists:
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name           | BasePriceList_ID |
      | dpl_tc4_nopart | dpl_tc4_ps_nopart  | DE                    | EUR                 | true  | dpl_tc4_nopart | dpl_tc4_base     |
      | dpl_tc4_nocust | dpl_tc4_ps_nocust  | DE                    | EUR                 | true  | dpl_tc4_nocust | dpl_tc4_base     |
      | dpl_tc4_flagn  | dpl_tc4_ps_flagn   | DE                    | EUR                 | true  | dpl_tc4_flagn  | dpl_tc4_base     |
      | dpl_tc4_ok     | dpl_tc4_ps_ok      | DE                    | EUR                 | true  | dpl_tc4_ok     | dpl_tc4_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier        | M_PriceList_ID | ValidFrom  |
      | dpl_tc4_base_v1   | dpl_tc4_base   | 2095-01-10 |
      | dpl_tc4_nopart_v1 | dpl_tc4_nopart | 2095-01-10 |
      | dpl_tc4_nocust_v1 | dpl_tc4_nocust | 2095-01-10 |
      | dpl_tc4_flagn_v1  | dpl_tc4_flagn  | 2095-01-10 |
      | dpl_tc4_ok_v1     | dpl_tc4_ok     | 2095-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc4_pp_nopart | dpl_tc4_nopart_v1      | dpl_tc4_prod | 2.00     | PCE               |
      | dpl_tc4_pp_nocust | dpl_tc4_nocust_v1      | dpl_tc4_prod | 2.00     | PCE               |
      | dpl_tc4_pp_flagn  | dpl_tc4_flagn_v1       | dpl_tc4_prod | 2.00     | PCE               |
      | dpl_tc4_pp_ok     | dpl_tc4_ok_v1          | dpl_tc4_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier        | Name              | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc4_bp_nocust | dpl_tc4_bp_nocust | N          | dpl_tc4_ps_nocust  | Y                    |
      | dpl_tc4_bp_flagn  | dpl_tc4_bp_flagn  | Y          | dpl_tc4_ps_flagn   | N                    |
      | dpl_tc4_bp_ok     | dpl_tc4_bp_ok     | Y          | dpl_tc4_ps_ok      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc4_ds | P            | dpl_tc4_ds | 2095-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc4_ds          | 10    | dpl_tc4_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc4_base_v2 | dpl_tc4_base   | 2095-03-01 | dpl_tc4_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc4_base_v2

    Then M_PriceList dpl_tc4_ok has 2 M_PriceList_Versions
    And M_PriceList dpl_tc4_nopart has 1 M_PriceList_Versions
    And M_PriceList dpl_tc4_nocust has 1 M_PriceList_Versions
    And M_PriceList dpl_tc4_flagn has 1 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc4_ok is:
      | Identifier    | ValidFrom  |
      | dpl_tc4_ok_v2 | 2095-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc4_ok_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc4_prod | 3.50     |

  # The pricing system has two customers, one with the flag Y and one with N: it is eligible.
  @Id:S32970_TC5
  Scenario: TC5 eligibility is per pricing system - one customer with flag Y and one with N
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc5_prod |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | dpl_tc5_ps_b |
      | dpl_tc5_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc5_base | dpl_tc5_ps_b       | DE                    | EUR                 | true  | dpl_tc5_base |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         | BasePriceList_ID |
      | dpl_tc5_cust | dpl_tc5_ps_c       | DE                    | EUR                 | true  | dpl_tc5_cust | dpl_tc5_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  |
      | dpl_tc5_base_v1 | dpl_tc5_base   | 2096-01-10 |
      | dpl_tc5_cust_v1 | dpl_tc5_cust   | 2096-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc5_pp_cust | dpl_tc5_cust_v1        | dpl_tc5_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc5_bp_n | dpl_tc5_bp_n | Y          | dpl_tc5_ps_c       | N                    |
      | dpl_tc5_bp_y | dpl_tc5_bp_y | Y          | dpl_tc5_ps_c       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc5_ds | P            | dpl_tc5_ds | 2096-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc5_ds          | 10    | dpl_tc5_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc5_base_v2 | dpl_tc5_base   | 2096-03-01 | dpl_tc5_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc5_base_v2

    Then M_PriceList dpl_tc5_cust has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc5_cust is:
      | Identifier      | ValidFrom  |
      | dpl_tc5_cust_v2 | 2096-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc5_cust_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc5_prod | 3.50     |

  # Saving a derivative checks that it has the country and currency of its base list, but nothing re-checks the
  # derivatives when the base list is changed later. So the mismatching derivatives are created while the base list
  # still has their country/currency, and the base list is then changed step by step to DE/EUR:
  # other currency (DE/CHF), other country (FR/EUR), no country (-/EUR); the matching one (DE/EUR) comes last.
  # The DB allows one active list per pricing system, sales flag and country, so the lists with another country
  # share one pricing system (with a flagged customer) and the other-currency list has its own.
  @Id:S32970_TC6
  Scenario: TC6 derivatives with another country, no country or another currency are not updated
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc6_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc6_ps_b  |
      | dpl_tc6_ps_s  |
      | dpl_tc6_ps_c2 |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc6_base | dpl_tc6_ps_b       | DE                    | CHF                 | true  | dpl_tc6_base |
    And metasfresh contains new M_PriceLists:
      | Identifier  | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name        | BasePriceList_ID |
      | dpl_tc6_chf | dpl_tc6_ps_c2      | DE                    | CHF                 | true  | dpl_tc6_chf | dpl_tc6_base     |
    And update M_PriceLists:
      | Identifier   | C_Country.CountryCode | C_Currency.ISO_Code |
      | dpl_tc6_base | FR                    | EUR                 |
    And metasfresh contains new M_PriceLists:
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name       | BasePriceList_ID |
      | dpl_tc6_fr | dpl_tc6_ps_s       | FR                    | EUR                 | true  | dpl_tc6_fr | dpl_tc6_base     |
    And update M_PriceLists:
      | Identifier   | C_Country.CountryCode |
      | dpl_tc6_base | none                  |
    And metasfresh contains new M_PriceLists:
      | Identifier     | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | Name           | BasePriceList_ID |
      | dpl_tc6_nocntr | dpl_tc6_ps_s       | EUR                 | true  | dpl_tc6_nocntr | dpl_tc6_base     |
    And update M_PriceLists:
      | Identifier   | C_Country.CountryCode |
      | dpl_tc6_base | DE                    |
    And metasfresh contains new M_PriceLists:
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name       | BasePriceList_ID |
      | dpl_tc6_ok | dpl_tc6_ps_s       | DE                    | EUR                 | true  | dpl_tc6_ok | dpl_tc6_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier        | M_PriceList_ID | ValidFrom  |
      | dpl_tc6_base_v1   | dpl_tc6_base   | 2097-01-10 |
      | dpl_tc6_ok_v1     | dpl_tc6_ok     | 2097-01-10 |
      | dpl_tc6_fr_v1     | dpl_tc6_fr     | 2097-01-10 |
      | dpl_tc6_chf_v1    | dpl_tc6_chf    | 2097-01-10 |
      | dpl_tc6_nocntr_v1 | dpl_tc6_nocntr | 2097-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc6_pp_ok     | dpl_tc6_ok_v1          | dpl_tc6_prod | 2.00     | PCE               |
      | dpl_tc6_pp_fr     | dpl_tc6_fr_v1          | dpl_tc6_prod | 2.00     | PCE               |
      | dpl_tc6_pp_chf    | dpl_tc6_chf_v1         | dpl_tc6_prod | 2.00     | PCE               |
      | dpl_tc6_pp_nocntr | dpl_tc6_nocntr_v1      | dpl_tc6_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc6_bp_s  | dpl_tc6_bp_s  | Y          | dpl_tc6_ps_s       | Y                    |
      | dpl_tc6_bp_c2 | dpl_tc6_bp_c2 | Y          | dpl_tc6_ps_c2      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc6_ds | P            | dpl_tc6_ds | 2097-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc6_ds          | 10    | dpl_tc6_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc6_base_v2 | dpl_tc6_base   | 2097-03-01 | dpl_tc6_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc6_base_v2

    Then M_PriceList dpl_tc6_ok has 2 M_PriceList_Versions
    And M_PriceList dpl_tc6_fr has 1 M_PriceList_Versions
    And M_PriceList dpl_tc6_chf has 1 M_PriceList_Versions
    And M_PriceList dpl_tc6_nocntr has 1 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc6_ok is:
      | Identifier    | ValidFrom  |
      | dpl_tc6_ok_v2 | 2097-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc6_ok_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc6_prod | 3.50     |

  # Real-world setup: the derivatives are created while the base list still has a country (the list validation
  # rejects a derivative whose country differs from its base list), then the country is removed from the base list.
  # A base list without country restricts nothing: the derivatives with DE, FR and no country are all updated.
  @Id:S32970_TC7
  Scenario: TC7 a base list without country updates its derivatives whatever their country
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc7_prod |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | dpl_tc7_ps_b |
      | dpl_tc7_ps_s |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc7_base | dpl_tc7_ps_b       | DE                    | EUR                 | true  | dpl_tc7_base |
    And metasfresh contains new M_PriceLists:
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name       | BasePriceList_ID |
      | dpl_tc7_de | dpl_tc7_ps_s       | DE                    | EUR                 | true  | dpl_tc7_de | dpl_tc7_base     |
    And update M_PriceLists:
      | Identifier   | C_Country.CountryCode |
      | dpl_tc7_base | FR                    |
    And metasfresh contains new M_PriceLists:
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name       | BasePriceList_ID |
      | dpl_tc7_fr | dpl_tc7_ps_s       | FR                    | EUR                 | true  | dpl_tc7_fr | dpl_tc7_base     |
    And update M_PriceLists:
      | Identifier   | C_Country.CountryCode |
      | dpl_tc7_base | none                  |
    And metasfresh contains new M_PriceLists:
      | Identifier     | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | Name           | BasePriceList_ID |
      | dpl_tc7_nocntr | dpl_tc7_ps_s       | EUR                 | true  | dpl_tc7_nocntr | dpl_tc7_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier        | M_PriceList_ID | ValidFrom  |
      | dpl_tc7_base_v1   | dpl_tc7_base   | 2103-01-10 |
      | dpl_tc7_de_v1     | dpl_tc7_de     | 2103-01-10 |
      | dpl_tc7_fr_v1     | dpl_tc7_fr     | 2103-01-10 |
      | dpl_tc7_nocntr_v1 | dpl_tc7_nocntr | 2103-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc7_pp_de     | dpl_tc7_de_v1          | dpl_tc7_prod | 2.00     | PCE               |
      | dpl_tc7_pp_fr     | dpl_tc7_fr_v1          | dpl_tc7_prod | 2.00     | PCE               |
      | dpl_tc7_pp_nocntr | dpl_tc7_nocntr_v1      | dpl_tc7_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc7_bp_s | dpl_tc7_bp_s | Y          | dpl_tc7_ps_s       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc7_ds | P            | dpl_tc7_ds | 2103-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc7_ds          | 10    | dpl_tc7_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc7_base_v2 | dpl_tc7_base   | 2103-03-01 | dpl_tc7_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc7_base_v2

    Then M_PriceList dpl_tc7_de has 2 M_PriceList_Versions
    And M_PriceList dpl_tc7_fr has 2 M_PriceList_Versions
    And M_PriceList dpl_tc7_nocntr has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc7_de is:
      | Identifier    | ValidFrom  | Name                  | M_Pricelist_Version_Base_ID |
      | dpl_tc7_de_v2 | 2103-03-01 | dpl_tc7_de 2103-03-01 | dpl_tc7_de_v1               |
    And the M_ProductPrices of M_PriceList_Version dpl_tc7_de_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc7_prod | 3.50     |
    And the latest M_PriceList_Version of M_PriceList dpl_tc7_fr is:
      | Identifier    | ValidFrom  |
      | dpl_tc7_fr_v2 | 2103-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc7_fr_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc7_prod | 3.50     |
    And the latest M_PriceList_Version of M_PriceList dpl_tc7_nocntr is:
      | Identifier        | ValidFrom  |
      | dpl_tc7_nocntr_v2 | 2103-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc7_nocntr_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc7_prod | 3.50     |

  # The base list has its own flagged customer, yet gets no new version (it has no base list itself); a list linked
  # to some other base list stays unchanged, while a derivative of this base list is updated as positive control.
  @Id:S32970_TC8
  Scenario: TC8 the base list and a customer list linked to another base list stay unchanged
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc8_prod |
    And metasfresh contains M_PricingSystems
      | Identifier         |
      | dpl_tc8_ps_b       |
      | dpl_tc8_ps_other_b |
      | dpl_tc8_ps_unl     |
      | dpl_tc8_ps_c       |
    And metasfresh contains new M_PriceLists:
      | Identifier         | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name               |
      | dpl_tc8_base       | dpl_tc8_ps_b       | DE                    | EUR                 | true  | dpl_tc8_base       |
      | dpl_tc8_other_base | dpl_tc8_ps_other_b | DE                    | EUR                 | true  | dpl_tc8_other_base |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         | BasePriceList_ID   |
      | dpl_tc8_unl  | dpl_tc8_ps_unl     | DE                    | EUR                 | true  | dpl_tc8_unl  | dpl_tc8_other_base |
      | dpl_tc8_cust | dpl_tc8_ps_c       | DE                    | EUR                 | true  | dpl_tc8_cust | dpl_tc8_base       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  |
      | dpl_tc8_base_v1 | dpl_tc8_base   | 2098-01-10 |
      | dpl_tc8_unl_v1  | dpl_tc8_unl    | 2098-01-10 |
      | dpl_tc8_cust_v1 | dpl_tc8_cust   | 2098-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc8_pp_unl  | dpl_tc8_unl_v1         | dpl_tc8_prod | 2.00     | PCE               |
      | dpl_tc8_pp_cust | dpl_tc8_cust_v1        | dpl_tc8_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier      | Name            | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc8_bp_b    | dpl_tc8_bp_b    | Y          | dpl_tc8_ps_b       | Y                    |
      | dpl_tc8_bp_unl  | dpl_tc8_bp_unl  | Y          | dpl_tc8_ps_unl     | Y                    |
      | dpl_tc8_bp_cust | dpl_tc8_bp_cust | Y          | dpl_tc8_ps_c       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name       | ValidFrom  |
      | dpl_tc8_ds | P            | dpl_tc8_ds | 2098-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc8_ds          | 10    | dpl_tc8_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc8_base_v2 | dpl_tc8_base   | 2098-03-01 | dpl_tc8_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc8_base_v2

    Then M_PriceList dpl_tc8_cust has 2 M_PriceList_Versions
    And M_PriceList dpl_tc8_base has 2 M_PriceList_Versions
    And M_PriceList dpl_tc8_unl has 1 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc8_base is:
      | Identifier      | ValidFrom  |
      | dpl_tc8_base_v2 | 2098-03-01 |

  # Two derivatives, each with its own pricing system, own previous schema and own previous price.
  @Id:S32970_TC9
  Scenario: TC9 several derivatives each get their own new version from their own previous version
    Given metasfresh contains M_Products:
      | Identifier   |
      | dpl_tc9_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc9_ps_b  |
      | dpl_tc9_ps_c1 |
      | dpl_tc9_ps_c2 |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name         |
      | dpl_tc9_base | dpl_tc9_ps_b       | DE                    | EUR                 | true  | dpl_tc9_base |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          | BasePriceList_ID |
      | dpl_tc9_cust1 | dpl_tc9_ps_c1      | DE                    | EUR                 | true  | dpl_tc9_cust1 | dpl_tc9_base     |
      | dpl_tc9_cust2 | dpl_tc9_ps_c2      | DE                    | EUR                 | true  | dpl_tc9_cust2 | dpl_tc9_base     |
    And metasfresh contains M_DiscountSchemas:
      | Identifier    | DiscountType | Name          | ValidFrom  |
      | dpl_tc9_ds_o1 | P            | dpl_tc9_ds_o1 | 2099-01-01 |
      | dpl_tc9_ds_o2 | P            | dpl_tc9_ds_o2 | 2099-01-01 |
      | dpl_tc9_ds    | P            | dpl_tc9_ds    | 2099-01-01 |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc9_base_v1  | dpl_tc9_base   | 2099-01-10 |                     |
      | dpl_tc9_cust1_v1 | dpl_tc9_cust1  | 2099-01-10 | dpl_tc9_ds_o1       |
      | dpl_tc9_cust2_v1 | dpl_tc9_cust2  | 2099-01-10 | dpl_tc9_ds_o2       |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc9_pp_c1 | dpl_tc9_cust1_v1       | dpl_tc9_prod | 2.00     | PCE               |
      | dpl_tc9_pp_c2 | dpl_tc9_cust2_v1       | dpl_tc9_prod | 4.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc9_bp_1 | dpl_tc9_bp_1 | Y          | dpl_tc9_ps_c1      | Y                    |
      | dpl_tc9_bp_2 | dpl_tc9_bp_2 | Y          | dpl_tc9_ps_c2      | Y                    |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc9_ds          | 10    | dpl_tc9_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc9_base_v2 | dpl_tc9_base   | 2099-03-01 | dpl_tc9_ds          |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc9_base_v2

    Then M_PriceList dpl_tc9_cust1 has 2 M_PriceList_Versions
    And M_PriceList dpl_tc9_cust2 has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc9_cust1 is:
      | Identifier       | ValidFrom  | M_DiscountSchema_ID | M_Pricelist_Version_Base_ID |
      | dpl_tc9_cust1_v2 | 2099-03-01 | dpl_tc9_ds          | dpl_tc9_cust1_v1            |
    And the latest M_PriceList_Version of M_PriceList dpl_tc9_cust2 is:
      | Identifier       | ValidFrom  | M_DiscountSchema_ID | M_Pricelist_Version_Base_ID |
      | dpl_tc9_cust2_v2 | 2099-03-01 | dpl_tc9_ds          | dpl_tc9_cust2_v1            |
    And the M_ProductPrices of M_PriceList_Version dpl_tc9_cust1_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc9_prod | 3.50     |
    And the M_ProductPrices of M_PriceList_Version dpl_tc9_cust2_v2 are:
      | M_Product_ID | PriceStd |
      | dpl_tc9_prod | 5.50     |

  # Latest customer version (2100-06-01, price 4.00) is dated after the new base version (2100-03-01): it is the
  # copy source, and the new version is placed between the two older ones by date, so it is not the latest.
  @Id:S32970_TC10
  Scenario: TC10 the previous version is the customer's latest by date, even if later than the new base version
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc10_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc10_ps_b |
      | dpl_tc10_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc10_base | dpl_tc10_ps_b      | DE                    | EUR                 | true  | dpl_tc10_base |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          | BasePriceList_ID |
      | dpl_tc10_cust | dpl_tc10_ps_c      | DE                    | EUR                 | true  | dpl_tc10_cust | dpl_tc10_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier         | M_PriceList_ID | ValidFrom  |
      | dpl_tc10_base_v1   | dpl_tc10_base  | 2100-01-10 |
      | dpl_tc10_cust_v1   | dpl_tc10_cust  | 2100-01-10 |
      | dpl_tc10_cust_late | dpl_tc10_cust  | 2100-06-01 |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc10_pp_v1   | dpl_tc10_cust_v1       | dpl_tc10_prod | 2.00     | PCE               |
      | dpl_tc10_pp_late | dpl_tc10_cust_late     | dpl_tc10_prod | 4.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc10_bp | dpl_tc10_bp | Y          | dpl_tc10_ps_c      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc10_ds | P            | dpl_tc10_ds | 2100-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc10_ds         | 10    | dpl_tc10_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc10_base_v2 | dpl_tc10_base  | 2100-03-01 | dpl_tc10_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc10_base_v2

    Then M_PriceList dpl_tc10_cust has 3 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc10_cust is:
      | Identifier         | ValidFrom  |
      | dpl_tc10_cust_late | 2100-06-01 |
    And the M_PriceList_Version of M_PriceList dpl_tc10_cust valid from 2100-03-01 is:
      | Identifier      | Name                     | M_DiscountSchema_ID | M_Pricelist_Version_Base_ID |
      | dpl_tc10_cust_n | dpl_tc10_cust 2100-03-01 | dpl_tc10_ds         | dpl_tc10_cust_late          |
    And the M_ProductPrices of M_PriceList_Version dpl_tc10_cust_n are:
      | M_Product_ID  | PriceStd |
      | dpl_tc10_prod | 5.50     |

  # The attribute-dependent customer price is carried over with the same attribute values, on its own ASI.
  @Id:S32970_TC11
  Scenario: TC11 an attribute-dependent price is carried over with its attribute values on its own attribute set instance
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc11_prod |
    And metasfresh contains M_AttributeSetInstance with identifier "dpl_tc11_asi":
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
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc11_ps_b |
      | dpl_tc11_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc11_base | dpl_tc11_ps_b      | DE                    | EUR                 | true  | dpl_tc11_base |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          | BasePriceList_ID |
      | dpl_tc11_cust | dpl_tc11_ps_c      | DE                    | EUR                 | true  | dpl_tc11_cust | dpl_tc11_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  |
      | dpl_tc11_base_v1 | dpl_tc11_base  | 2101-01-10 |
      | dpl_tc11_cust_v1 | dpl_tc11_cust  | 2101-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice |
      | dpl_tc11_pp_v1 | dpl_tc11_cust_v1       | dpl_tc11_prod | 2.00     | PCE               | true                     | dpl_tc11_asi                             | N                 |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc11_bp | dpl_tc11_bp | Y          | dpl_tc11_ps_c      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc11_ds | P            | dpl_tc11_ds | 2101-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc11_ds         | 10    | dpl_tc11_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc11_base_v2 | dpl_tc11_base  | 2101-03-01 | dpl_tc11_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc11_base_v2

    Then the latest M_PriceList_Version of M_PriceList dpl_tc11_cust is:
      | Identifier       | ValidFrom  |
      | dpl_tc11_cust_v2 | 2101-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc11_cust_v2 are:
      | M_Product_ID  | PriceStd | M_AttributeSetInstance_ID | CopyOf_M_ProductPrice_ID |
      | dpl_tc11_prod | 3.50     | dpl_tc11_asi              | dpl_tc11_pp_v1           |

  # Two runs with successive base versions: the second one starts from the result of the first (2.00 + 1.50 + 1.00).
  @Id:S32970_TC12
  Scenario: TC12 a further run with a further base version builds on the then-latest customer version
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc12_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc12_ps_b |
      | dpl_tc12_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc12_base | dpl_tc12_ps_b      | DE                    | EUR                 | true  | dpl_tc12_base |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          | BasePriceList_ID |
      | dpl_tc12_cust | dpl_tc12_ps_c      | DE                    | EUR                 | true  | dpl_tc12_cust | dpl_tc12_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  |
      | dpl_tc12_base_v1 | dpl_tc12_base  | 2102-01-10 |
      | dpl_tc12_cust_v1 | dpl_tc12_cust  | 2102-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc12_pp_v1 | dpl_tc12_cust_v1       | dpl_tc12_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc12_bp | dpl_tc12_bp | Y          | dpl_tc12_ps_c      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier   | DiscountType | Name         | ValidFrom  |
      | dpl_tc12_ds1 | P            | dpl_tc12_ds1 | 2102-01-01 |
      | dpl_tc12_ds2 | P            | dpl_tc12_ds2 | 2102-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc12_ds1        | 10    | dpl_tc12_prod | 1.50       |
      | dpl_tc12_ds2        | 10    | dpl_tc12_prod | 1.00       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc12_base_v2 | dpl_tc12_base  | 2102-03-01 | dpl_tc12_ds1        |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc12_base_v2

    Then the latest M_PriceList_Version of M_PriceList dpl_tc12_cust is:
      | Identifier       | ValidFrom  | M_Pricelist_Version_Base_ID |
      | dpl_tc12_cust_v2 | 2102-03-01 | dpl_tc12_cust_v1            |
    And the M_ProductPrices of M_PriceList_Version dpl_tc12_cust_v2 are:
      | M_Product_ID  | PriceStd |
      | dpl_tc12_prod | 3.50     |

    Given metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc12_base_v3 | dpl_tc12_base  | 2102-06-01 | dpl_tc12_ds2        |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc12_base_v3

    Then M_PriceList dpl_tc12_cust has 3 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc12_cust is:
      | Identifier       | ValidFrom  | Name                     | M_DiscountSchema_ID | M_Pricelist_Version_Base_ID |
      | dpl_tc12_cust_v3 | 2102-06-01 | dpl_tc12_cust 2102-06-01 | dpl_tc12_ds2        | dpl_tc12_cust_v2            |
    And the M_ProductPrices of M_PriceList_Version dpl_tc12_cust_v3 are:
      | M_Product_ID  | PriceStd |
      | dpl_tc12_prod | 4.50     |

  # Both the inactive and the purchase derivative stay unchanged; the active sales derivative of the same pricing
  # system is the positive control.
  @Id:S32970_TC13
  Scenario: TC13 an inactive derivative list and a purchase derivative list are not updated
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc13_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc13_ps_b |
      | dpl_tc13_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc13_base | dpl_tc13_ps_b      | DE                    | EUR                 | true  | dpl_tc13_base |
    And metasfresh contains new M_PriceLists:
      | Identifier      | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsActive | Name            | BasePriceList_ID |
      | dpl_tc13_active | dpl_tc13_ps_c      | DE                    | EUR                 | true  | true     | dpl_tc13_active | dpl_tc13_base    |
      | dpl_tc13_inact  | dpl_tc13_ps_c      | DE                    | EUR                 | true  | false    | dpl_tc13_inact  | dpl_tc13_base    |
      | dpl_tc13_purch  | dpl_tc13_ps_c      | DE                    | EUR                 | false | true     | dpl_tc13_purch  | dpl_tc13_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier         | M_PriceList_ID  | ValidFrom  |
      | dpl_tc13_base_v1   | dpl_tc13_base   | 2104-01-10 |
      | dpl_tc13_active_v1 | dpl_tc13_active | 2104-01-10 |
      | dpl_tc13_inact_v1  | dpl_tc13_inact  | 2104-01-10 |
      | dpl_tc13_purch_v1  | dpl_tc13_purch  | 2104-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc13_pp_active | dpl_tc13_active_v1     | dpl_tc13_prod | 2.00     | PCE               |
      | dpl_tc13_pp_inact  | dpl_tc13_inact_v1      | dpl_tc13_prod | 2.00     | PCE               |
      | dpl_tc13_pp_purch  | dpl_tc13_purch_v1      | dpl_tc13_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc13_bp | dpl_tc13_bp | Y          | dpl_tc13_ps_c      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc13_ds | P            | dpl_tc13_ds | 2104-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc13_ds         | 10    | dpl_tc13_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc13_base_v2 | dpl_tc13_base  | 2104-03-01 | dpl_tc13_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc13_base_v2

    Then M_PriceList dpl_tc13_active has 2 M_PriceList_Versions
    And the M_ProductPrices of M_PriceList_Version dpl_tc13_active_v1 are:
      | M_Product_ID  | PriceStd |
      | dpl_tc13_prod | 2.00     |
    And M_PriceList dpl_tc13_inact has 1 M_PriceList_Versions
    And M_PriceList dpl_tc13_purch has 1 M_PriceList_Versions

  # The first run creates the version (2.00 + 1.50 = 3.50); a second run on the same base version creates nothing
  # and reports the derivative as skipped.
  @Id:S32970_TC14
  Scenario: TC14 a second run on the same base version creates no further version and reports it as skipped
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc14_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc14_ps_b |
      | dpl_tc14_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc14_base | dpl_tc14_ps_b      | DE                    | EUR                 | true  | dpl_tc14_base |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          | BasePriceList_ID |
      | dpl_tc14_cust | dpl_tc14_ps_c      | DE                    | EUR                 | true  | dpl_tc14_cust | dpl_tc14_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  |
      | dpl_tc14_base_v1 | dpl_tc14_base  | 2105-01-10 |
      | dpl_tc14_cust_v1 | dpl_tc14_cust  | 2105-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc14_pp_v1 | dpl_tc14_cust_v1       | dpl_tc14_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc14_bp | dpl_tc14_bp | Y          | dpl_tc14_ps_c      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc14_ds | P            | dpl_tc14_ds | 2105-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc14_ds         | 10    | dpl_tc14_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc14_base_v2 | dpl_tc14_base  | 2105-03-01 | dpl_tc14_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc14_base_v2

    Then M_PriceList dpl_tc14_cust has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc14_cust is:
      | Identifier       | ValidFrom  | M_Pricelist_Version_Base_ID |
      | dpl_tc14_cust_v2 | 2105-03-01 | dpl_tc14_cust_v1            |
    And the M_ProductPrices of M_PriceList_Version dpl_tc14_cust_v2 are:
      | M_Product_ID  | PriceStd |
      | dpl_tc14_prod | 3.50     |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc14_base_v2

    Then M_PriceList dpl_tc14_cust has 2 M_PriceList_Versions
    And the M_ProductPrices of M_PriceList_Version dpl_tc14_cust_v2 are:
      | M_Product_ID  | PriceStd |
      | dpl_tc14_prod | 3.50     |
    And the 'apply to derivative price lists' result text contains "skipped"

  # The latest version by date (2106-02-01, price 9.00) is inactive: the latest ACTIVE version (2106-01-10, 2.00) is
  # the copy source and its base version. A derivative whose versions are all inactive is skipped.
  @Id:S32970_TC15
  Scenario: TC15 the previous version is the latest active one; a derivative without active version is skipped
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc15_prod |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | dpl_tc15_ps_b  |
      | dpl_tc15_ps_c1 |
      | dpl_tc15_ps_c2 |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc15_base | dpl_tc15_ps_b      | DE                    | EUR                 | true  | dpl_tc15_base |
    And metasfresh contains new M_PriceLists:
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name           | BasePriceList_ID |
      | dpl_tc15_cust  | dpl_tc15_ps_c1     | DE                    | EUR                 | true  | dpl_tc15_cust  | dpl_tc15_base    |
      | dpl_tc15_cust2 | dpl_tc15_ps_c2     | DE                    | EUR                 | true  | dpl_tc15_cust2 | dpl_tc15_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier          | M_PriceList_ID | ValidFrom  | IsActive |
      | dpl_tc15_base_v1    | dpl_tc15_base  | 2106-01-10 | true     |
      | dpl_tc15_cust_v1    | dpl_tc15_cust  | 2106-01-10 | true     |
      | dpl_tc15_cust_inact | dpl_tc15_cust  | 2106-02-01 | false    |
      | dpl_tc15_cust2_v1   | dpl_tc15_cust2 | 2106-01-10 | false    |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc15_pp_v1    | dpl_tc15_cust_v1       | dpl_tc15_prod | 2.00     | PCE               |
      | dpl_tc15_pp_inact | dpl_tc15_cust_inact    | dpl_tc15_prod | 9.00     | PCE               |
      | dpl_tc15_pp_c2    | dpl_tc15_cust2_v1      | dpl_tc15_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc15_bp1 | dpl_tc15_bp1 | Y          | dpl_tc15_ps_c1     | Y                    |
      | dpl_tc15_bp2 | dpl_tc15_bp2 | Y          | dpl_tc15_ps_c2     | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc15_ds | P            | dpl_tc15_ds | 2106-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc15_ds         | 10    | dpl_tc15_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc15_base_v2 | dpl_tc15_base  | 2106-03-01 | dpl_tc15_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc15_base_v2

    Then M_PriceList dpl_tc15_cust has 3 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc15_cust is:
      | Identifier       | ValidFrom  | Name                     | M_DiscountSchema_ID | M_Pricelist_Version_Base_ID |
      | dpl_tc15_cust_v2 | 2106-03-01 | dpl_tc15_cust 2106-03-01 | dpl_tc15_ds         | dpl_tc15_cust_v1            |
    And the M_ProductPrices of M_PriceList_Version dpl_tc15_cust_v2 are:
      | M_Product_ID  | PriceStd |
      | dpl_tc15_prod | 3.50     |
    And M_PriceList dpl_tc15_cust2 has 1 M_PriceList_Versions

  # First run: one derivative gets a new version, the other (all versions inactive) is skipped; the text names both.
  # Second run on another base list without any derivative: "no derivative price list qualifies".
  @Id:S32970_TC17
  Scenario: TC17 the result text names the created and the skipped derivatives, or that none qualifies
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc17_prod |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | dpl_tc17_ps_b  |
      | dpl_tc17_ps_b2 |
      | dpl_tc17_ps_c1 |
      | dpl_tc17_ps_c2 |
    And metasfresh contains new M_PriceLists:
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name           |
      | dpl_tc17_base  | dpl_tc17_ps_b      | DE                    | EUR                 | true  | dpl_tc17_base  |
      | dpl_tc17_base2 | dpl_tc17_ps_b2     | DE                    | EUR                 | true  | dpl_tc17_base2 |
    And metasfresh contains new M_PriceLists:
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name           | BasePriceList_ID |
      | dpl_tc17_cust1 | dpl_tc17_ps_c1     | DE                    | EUR                 | true  | dpl_tc17_cust1 | dpl_tc17_base    |
      | dpl_tc17_cust2 | dpl_tc17_ps_c2     | DE                    | EUR                 | true  | dpl_tc17_cust2 | dpl_tc17_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier        | M_PriceList_ID | ValidFrom  | IsActive |
      | dpl_tc17_base_v1  | dpl_tc17_base  | 2108-01-10 | true     |
      | dpl_tc17_base2_v1 | dpl_tc17_base2 | 2108-01-10 | true     |
      | dpl_tc17_cust1_v1 | dpl_tc17_cust1 | 2108-01-10 | true     |
      | dpl_tc17_cust2_v1 | dpl_tc17_cust2 | 2108-01-10 | false    |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc17_pp_cust1  | dpl_tc17_cust1_v1      | dpl_tc17_prod | 2.00     | PCE               |
      | dpl_tc17_pp_cust2  | dpl_tc17_cust2_v1      | dpl_tc17_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc17_bp1 | dpl_tc17_bp1 | Y          | dpl_tc17_ps_c1     | Y                    |
      | dpl_tc17_bp2 | dpl_tc17_bp2 | Y          | dpl_tc17_ps_c2     | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc17_ds | P            | dpl_tc17_ds | 2108-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc17_ds         | 10    | dpl_tc17_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier        | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc17_base_v2  | dpl_tc17_base  | 2108-03-01 | dpl_tc17_ds         |
      | dpl_tc17_base2_v2 | dpl_tc17_base2 | 2108-03-01 | dpl_tc17_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc17_base_v2

    Then the 'apply to derivative price lists' result text contains "1 new version"
    And the 'apply to derivative price lists' result text contains "dpl_tc17_cust1"
    And the 'apply to derivative price lists' result text contains "dpl_tc17_cust2"
    And the 'apply to derivative price lists' result text contains "skipped:"

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc17_base2_v2

    Then the 'apply to derivative price lists' result text contains "no derivative price list qualifies"

  # A season-fixed customer price is not transformed by the matching schema line (2.00 stays) and keeps its flag.
  @Id:S32970_TC18
  Scenario: TC18 a season-fixed customer price keeps its price and flag although a schema line matches
    Given metasfresh contains M_Products:
      | Identifier    |
      | dpl_tc18_prod |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | dpl_tc18_ps_b |
      | dpl_tc18_ps_c |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          |
      | dpl_tc18_base | dpl_tc18_ps_b      | DE                    | EUR                 | true  | dpl_tc18_base |
    And metasfresh contains new M_PriceLists:
      | Identifier    | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name          | BasePriceList_ID |
      | dpl_tc18_cust | dpl_tc18_ps_c      | DE                    | EUR                 | true  | dpl_tc18_cust | dpl_tc18_base    |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  |
      | dpl_tc18_base_v1 | dpl_tc18_base  | 2109-01-10 |
      | dpl_tc18_cust_v1 | dpl_tc18_cust  | 2109-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | IsSeasonFixedPrice |
      | dpl_tc18_pp_v1 | dpl_tc18_cust_v1       | dpl_tc18_prod | 2.00     | PCE               | true               |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc18_bp | dpl_tc18_bp | Y          | dpl_tc18_ps_c      | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier  | DiscountType | Name        | ValidFrom  |
      | dpl_tc18_ds | P            | dpl_tc18_ds | 2109-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID  | Std_AddAmt |
      | dpl_tc18_ds         | 10    | dpl_tc18_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier       | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc18_base_v2 | dpl_tc18_base  | 2109-03-01 | dpl_tc18_ds         |

    When the 'apply to derivative price lists' process is invoked for M_PriceList_Version dpl_tc18_base_v2

    Then M_PriceList dpl_tc18_cust has 2 M_PriceList_Versions
    And the latest M_PriceList_Version of M_PriceList dpl_tc18_cust is:
      | Identifier       | ValidFrom  |
      | dpl_tc18_cust_v2 | 2109-03-01 |
    And the M_ProductPrices of M_PriceList_Version dpl_tc18_cust_v2 are:
      | M_Product_ID  | PriceStd | IsSeasonFixedPrice |
      | dpl_tc18_prod | 2.00     | true               |
