@from:cucumber
@ghActions:run_on_executor6
Feature: Related Documents SOTrx routing
  Verify that related documents resolve correctly for Sales and Purchase orders,
  including the presence of expected candidate groups.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-15T13:30:00+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Sales Order has expected related document candidates
    Given metasfresh contains M_PricingSystems
      | Identifier | Name              | Value             |
      | ps_soRel   | ps_soRel_pricing  | ps_soRel_pricing  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_soRel   | ps_soRel           | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_soRel  | pl_soRel       | plv_soRel  | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier     | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_soRel | Y          | N        | ps_soRel           |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID  | DocBaseType | DateOrdered |
      | order_so_rel  | true    | bpartner_soRel | SOO         | 2022-06-15  |
    When related documents are retrieved for the C_Order identified by "order_so_rel"
    Then the related documents include at least 3 candidate groups
    And the related documents include a candidate group targeting window "Invoice (Customer)"
    And the related documents include a candidate group targeting window "Shipment (Customer)"

  @from:cucumber
  Scenario: Purchase Order has expected related document candidates
    Given metasfresh contains M_PricingSystems
      | Identifier | Name              | Value             |
      | ps_poRel   | ps_poRel_pricing  | ps_poRel_pricing  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_poRel   | ps_poRel           | DE                    | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_poRel  | pl_poRel       | plv_poRel  | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier     | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_poRel | N          | Y        | ps_poRel           |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID  | DocBaseType | DateOrdered |
      | order_po_rel  | false   | bpartner_poRel | POO         | 2022-06-15  |
    When related documents are retrieved for the C_Order identified by "order_po_rel"
    Then the related documents include at least 3 candidate groups
    And the related documents include a candidate group targeting window "Invoice (Vendor)"
    And the related documents include a candidate group targeting window "Material Receipt"
