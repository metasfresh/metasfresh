@from:cucumber
@ghActions:run_on_executor6
Feature: Zoom-Into record-level verification
  Verify that for actual business records, every non-null reference field
  resolves to a valid zoom-to window with the target record present.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-15T13:30:00+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Sales Order record — all FK fields zoom to valid windows
    Given metasfresh contains M_PricingSystems
      | Identifier | Name               | Value              |
      | ps_soFld   | ps_soFld_pricing   | ps_soFld_pricing   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_soFld   | ps_soFld           | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_soFld  | pl_soFld       | plv_soFld  | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier     | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_soFld | Y          | N        | ps_soFld           |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DocBaseType | DateOrdered |
      | order_so_fld | true    | bpartner_soFld | SOO         | 2022-06-15  |
    When all reference fields of the C_Order identified by "order_so_fld" are verified for zoom-to, excluding columns:
      | ColumnName              |
      | Bill_Location_ID        |
      | C_BPartner_Location_ID  |
      | DropShip_Location_ID    |
      | HandOver_Location_ID    |
    Then every non-null reference field resolves to a valid zoom-to window for the record

  @from:cucumber
  Scenario: Purchase Order record — all FK fields zoom to valid windows
    Given metasfresh contains M_PricingSystems
      | Identifier | Name               | Value              |
      | ps_poFld   | ps_poFld_pricing   | ps_poFld_pricing   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_poFld   | ps_poFld           | DE                    | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_poFld  | pl_poFld       | plv_poFld  | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier     | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_poFld | N          | Y        | ps_poFld           |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DocBaseType | DateOrdered |
      | order_po_fld | false   | bpartner_poFld | POO         | 2022-06-15  |
    When all reference fields of the C_Order identified by "order_po_fld" are verified for zoom-to, excluding columns:
      | ColumnName              |
      | Bill_Location_ID        |
      | C_BPartner_Location_ID  |
      | DropShip_Location_ID    |
      | HandOver_Location_ID    |
    Then every non-null reference field resolves to a valid zoom-to window for the record

  @from:cucumber
  Scenario: BPartner record — all FK fields zoom to valid windows
    Given metasfresh contains M_PricingSystems
      | Identifier | Name               | Value              |
      | ps_bpFld   | ps_bpFld_pricing   | ps_bpFld_pricing   |
    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | IsVendor | M_PricingSystem_ID |
      | bpartner_fld | Y          | N        | ps_bpFld           |
    When all reference fields of the C_BPartner identified by "bpartner_fld" are verified for zoom-to, excluding columns:
      | ColumnName                |
      | Default_Bill_Location_ID  |
      | Default_Ship_Location_ID  |
    Then every non-null reference field resolves to a valid zoom-to window for the record
