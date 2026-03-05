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
    Given metasfresh contains C_BPartners:
      | Identifier     | IsCustomer | IsVendor |
      | bpartner_soRel | Y          | N        |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID  | DocBaseType |
      | order_so_rel  | true    | bpartner_soRel | SOO         |
    When related documents are retrieved for the C_Order identified by "order_so_rel"
    Then the related documents include at least 3 candidate groups
    And the related documents include a candidate group targeting window "Sales Invoice (Customer)"
    And the related documents include a candidate group targeting window "Shipment"

  @from:cucumber
  Scenario: Purchase Order has expected related document candidates
    Given metasfresh contains C_BPartners:
      | Identifier     | IsCustomer | IsVendor |
      | bpartner_poRel | N          | Y        |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID  | DocBaseType |
      | order_po_rel  | false   | bpartner_poRel | POO         |
    When related documents are retrieved for the C_Order identified by "order_po_rel"
    Then the related documents include at least 3 candidate groups
    And the related documents include a candidate group targeting window "Vendor Invoice (Vendor)"
    And the related documents include a candidate group targeting window "Material Receipt"
