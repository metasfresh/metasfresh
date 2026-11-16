@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29080_Transport_Order
@ghActions:run_on_executor6
Feature: Order header currency is taken from its price list

  When an order is saved, its header currency (C_Currency_ID) is taken from the
  currency of the order's price list. This keeps the order header consistent with
  the price list its lines are priced against.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

  Scenario: A purchase order's header currency follows the price list currency
    Given metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx | C_Country_ID |
      | priceList  | pricingSystem      | USD           | false | DE           |
    And metasfresh contains C_BPartners:
      | Identifier |
      | bpartner   |
    When metasfresh contains C_Orders:
      | Identifier | C_BPartner_ID | M_PricingSystem_ID | IsSOTrx | DateOrdered |
      | order      | bpartner      | pricingSystem      | false   | 2022-05-17  |
    Then validate the created orders
      | Identifier | C_Currency_ID |
      | order      | USD           |
