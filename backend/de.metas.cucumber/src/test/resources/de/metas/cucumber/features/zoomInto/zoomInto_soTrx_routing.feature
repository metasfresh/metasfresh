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
    Given zoom-into for the following tables resolves to the expected windows by SOTrx:
      | TableName  | IsSOTrx | ExpectedWindowName             |
      | C_Order    | true    | Sales Order                    |
      | C_Order    | false   | Purchase Order                 |
      | C_Invoice  | true    | Sales Invoice (Customer)       |
      | C_Invoice  | false   | Vendor Invoice (Vendor)        |
      | M_InOut    | true    | Shipment                       |
      | M_InOut    | false   | Material Receipt               |

  @from:cucumber
  Scenario: Zoom-into resolves to Sales Order window for SO record
    Given metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | IsVendor |
      | bpartner_soZI | Y          | N        |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DocBaseType |
      | order_so_zi  | true    | bpartner_soZI | SOO         |
    When RecordWindowFinder resolves zoom-into for the C_Order identified by "order_so_zi"
    Then the resolved window is "Sales Order"

  @from:cucumber
  Scenario: Zoom-into resolves to Purchase Order window for PO record
    Given metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | IsVendor |
      | bpartner_poZI | N          | Y        |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DocBaseType |
      | order_po_zi  | false   | bpartner_poZI | POO         |
    When RecordWindowFinder resolves zoom-into for the C_Order identified by "order_po_zi"
    Then the resolved window is "Purchase Order"
