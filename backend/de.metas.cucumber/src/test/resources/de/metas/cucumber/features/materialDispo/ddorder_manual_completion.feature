@from:cucumber
@ghActions:run_on_executor7
Feature: Manual DD_Order completion creates MD_Candidates

  Manual DD_Orders (created in the UI, not via the planning pipeline) should also produce
  MD_Candidates when completed. Previously, completing a manual DD_Order crashed material dispo
  because no DDOrderCreatedEvent had been fired.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+02:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier |
      | p_manual   |
    And metasfresh contains C_BPartners:
      | Identifier     | IsVendor | IsCustomer |
      | bp_manual_dd   | N        | Y          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | GLN             | C_BPartner_ID |
      | bpLoc_manual   | manualDDOrderBP | bp_manual_dd  |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID   | C_BPartner_ID  | C_BPartner_Location_ID | IsInTransit |
      | inTransit_manual | bp_manual_dd   | bpLoc_manual           | true        |
      | sourceWH_manual  | bp_manual_dd   | bpLoc_manual           | false       |
      | targetWH_manual  | bp_manual_dd   | bpLoc_manual           | false       |
    And metasfresh contains M_Locator:
      | Identifier       | M_Warehouse_ID  |
      | fromLocator_man  | sourceWH_manual |
      | toLocator_man    | targetWH_manual |

  @from:cucumber
  Scenario: Complete a manually created DD_Order — MD_Candidates created with distribution details
    # Create DD_Order directly (NOT via the planning pipeline / DD_Order_Candidate)
    Given metasfresh contains DD_Orders:
      | Identifier  | M_Warehouse_ID.From | M_Warehouse_ID.To | M_Warehouse_ID.Transit | DatePromised             |
      | ddOrder_man | sourceWH_manual     | targetWH_manual   | inTransit_manual       | 2022-05-20T00:00:00Z |
    And metasfresh contains DD_OrderLines:
      | Identifier   | DD_Order_ID | M_Product_ID | QtyEntered | M_Locator_ID    | M_LocatorTo_ID  |
      | ddLine_man   | ddOrder_man | p_manual     | 10         | fromLocator_man | toLocator_man   |

    # Complete -> triggers DDOrderDocStatusChangedEvent -> handler creates candidates
    When the dd_order identified by ddOrder_man is completed

    # Wait for material dispo to process the event
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Verify SUPPLY + DEMAND candidates were created
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected_LocalTimeZone | Qty | Qty_AvailableToPromise | M_Warehouse_ID  | DD_Order_ID |
      | supply_1   | SUPPLY            | DISTRIBUTION              | p_manual     | 2022-05-20T00:00            | 10  | 10                     | targetWH_manual | ddOrder_man |
      | demand_1   | DEMAND            | DISTRIBUTION              | p_manual     | 2022-05-20T00:00            | -10 | -10                    | sourceWH_manual | ddOrder_man |
