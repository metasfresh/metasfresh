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
