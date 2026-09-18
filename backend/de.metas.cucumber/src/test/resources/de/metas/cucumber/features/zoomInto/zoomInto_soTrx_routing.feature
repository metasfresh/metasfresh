@from:cucumber
@ghActions:run_on_executor6
Feature: Zoom-Into SOTrx routing
  Verify that RecordWindowFinder correctly routes to Sales or Purchase windows
  based on the IsSOTrx flag for key document tables.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-15T13:30:00+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: SOTrx routing resolves correct windows without records
    Then zoom-into for the following tables resolves to the expected windows by SOTrx:
      | TableName  | IsSOTrx | ExpectedWindowName             |
      | C_Order    | true    | Sales Order                    |
      | C_Order    | false   | Purchase Order                 |
      | C_Invoice  | true    | Invoice (Customer)             |
      | C_Invoice  | false   | Invoice (Vendor)               |
      | M_InOut    | true    | Shipment (Customer)            |
      | M_InOut    | false   | Material Receipt               |

  @from:cucumber
  Scenario: Zoom-into resolves to Sales Order window for SO record
    Given metasfresh contains M_PricingSystems
      | Identifier | Name            | Value           |
      | ps_soZI    | ps_soZI_pricing | ps_soZI_pricing |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_soZI    | ps_soZI            | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_soZI   | pl_soZI        | plv_soZI   | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_soZI | Y          | N        | ps_soZI            |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered |
      | order_so_zi  | true    | bpartner_soZI | SOO         | 2022-06-15  |
    When RecordWindowFinder resolves zoom-into for the C_Order identified by "order_so_zi"
    Then the resolved window is "Sales Order"

  @from:cucumber
  Scenario: Zoom-into resolves to Purchase Order window for PO record
    Given metasfresh contains M_PricingSystems
      | Identifier | Name            | Value           |
      | ps_poZI    | ps_poZI_pricing | ps_poZI_pricing |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_poZI    | ps_poZI            | DE                    | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_poZI   | pl_poZI        | plv_poZI   | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_poZI | N          | Y        | ps_poZI            |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered |
      | order_po_zi  | false   | bpartner_poZI | POO         | 2022-06-15  |
    When RecordWindowFinder resolves zoom-into for the C_Order identified by "order_po_zi"
    Then the resolved window is "Purchase Order"
