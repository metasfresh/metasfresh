@from:cucumber
@ghActions:run_on_executor5
Feature: EPCIS JSON export via get_epcis_events_json_fn
  The SQL function builds EPCIS event JSON from the HU hierarchy.
  Tests validate function execution and JSON structure.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-06-10T10:00:00+02:00[Europe/Berlin]

  @from:cucumber
  @Id:EPCIS_010
  Scenario: EPCIS JSON function returns valid JSON for existing shipment
    # Use seed data shipment — validates function executes without errors
    # and returns the expected top-level fields
    Given load M_InOut by ID 1000000 as io_seed
    When the EPCIS JSON export function is called for M_InOut identified by io_seed
    Then the EPCIS JSON has:
      | warehouseValue | desadvReference |
      | StdWarehouse   | 1013010        |
