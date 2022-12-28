@from:cucumber
Feature: Shipping HUs interaction with material schedule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh initially has no MD_Candidate data

  @from:cucumber
  Scenario: Validate the way shipping HUs interacts with material schedule (complete, reactivate, reverse shipment)
    Given load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | p_1                     | 2005577          |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-09   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 100      | 0       |
    And the inventory identified by i_1 is completed
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |               | 100 | 100                    | 2021-04-09T00:00:00             |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | 2156425                  | 2021-04-17  | 2021-04-11T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 15         |
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 100 | 100                    | 2021-04-09T00:00:00             |
      | c_2        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-11T21:00:00Z | -15 | 85                     |                                 |
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_2        | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-11T21:00:00Z | 0   | 85                     |                                 |
      | c_3        | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     |                      | -15 | 85                     | 2021-04-11T00:00:00             |
    When the shipment identified by s_1 is reactivated
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_2        | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-11T21:00:00Z | -15 | 85                     |                                 |
      | c_3        | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     |                      | 0   | 100                    | 2021-04-11T00:00:00             |

    And the shipment identified by s_1 is completed

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_2        | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-11T21:00:00Z | 0   | 85                     |                                 |
      | c_3        | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     |                      | -15 | 85                     | 2021-04-11T00:00:00             |

    And the shipment identified by s_1 is reversed

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_2        | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-11T21:00:00Z | -15 | 85                     |                                 |
      | c_3        | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     |                      | 0   | 100                    | 2021-04-11T00:00:00             |
