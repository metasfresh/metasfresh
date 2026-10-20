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
      | Identifier    |
      | dpl_tc1_ps_b  |
      | dpl_tc1_ps_c  |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name           |
      | dpl_tc1_base | dpl_tc1_ps_b       | DE                    | EUR                 | true  | dpl_tc1_base   |
    And metasfresh contains new M_PriceLists:
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | Name           | BasePriceList_ID |
      | dpl_tc1_cust | dpl_tc1_ps_c       | DE                    | EUR                 | true  | dpl_tc1_cust   | dpl_tc1_base     |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier      | M_PriceList_ID | ValidFrom  |
      | dpl_tc1_base_v1 | dpl_tc1_base   | 2092-01-10 |
      | dpl_tc1_cust_v1 | dpl_tc1_cust   | 2092-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | dpl_tc1_pp_base | dpl_tc1_base_v1        | dpl_tc1_prod | 5.00     | PCE               |
      | dpl_tc1_pp_cust | dpl_tc1_cust_v1        | dpl_tc1_prod | 2.00     | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | IsCustomer | M_PricingSystem_ID | IsAllowPriceMutation |
      | dpl_tc1_bp   | dpl_tc1_bp   | Y          | dpl_tc1_ps_c       | Y                    |
    And metasfresh contains M_DiscountSchemas:
      | Identifier   | DiscountType | Name         | ValidFrom  |
      | dpl_tc1_ds   | P            | dpl_tc1_ds   | 2092-01-01 |
    And metasfresh contains M_DiscountSchemaLines:
      | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
      | dpl_tc1_ds          | 10    | dpl_tc1_prod | 1.50       |
    And metasfresh contains new M_PriceList_Versions:
      | Identifier          | M_PriceList_ID | ValidFrom  | M_DiscountSchema_ID |
      | dpl_tc1_base_v2     | dpl_tc1_base   | 2092-03-01 | dpl_tc1_ds          |
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
