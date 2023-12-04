@from:cucumber
@topic:materialdispo
@ghActions:run_on_executor6
Feature: material-dispo updates on shipment-schedule events
  As a user
  I want material dispo to be updated properly if shipment-schedules are created
  So that the ATP is always correct

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule without stock or ATP
    Given infrastructure and metasfresh are running
    And metasfresh initially has no MD_Candidate data
    When metasfresh receives a ShipmentScheduleCreatedEvent
      | M_Product_ID | M_ShipmentSchedule_ID | PreparationDate         | Qty |
      | 2005577      | 123                   | 2020-12-12T23:59:00.00Z | 10  |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | 2005577                 | 2020-12-12T23:59:00.00Z | -10 | -10                    |
      | c_2        | SUPPLY            |                               | 2005577                 | 2020-12-12T23:59:00.00Z | 10  | 0                      |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate_ID.Identifier | M_ShipmentSchedule_ID |
      | c_1                        | 123                   |

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with stock
    Given infrastructure and metasfresh are running
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | 2005577                 | 2020-12-12T10:00:00.00Z | 100 | 100                    |
    When metasfresh receives a ShipmentScheduleCreatedEvent
      | M_Product_ID | M_ShipmentSchedule_ID | PreparationDate         | Qty |
      | 2005577      | 123                   | 2020-12-12T23:59:00.00Z | 10  |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | 2005577                 | 2020-12-12T10:00:00.00Z | 100 | 100                    |
      | c_2        | DEMAND            | SHIPMENT                      | 2005577                 | 2020-12-12T23:59:00.00Z | -10 | 90                     |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate_ID.Identifier | M_ShipmentSchedule_ID |
      | c_2                        | 123                   |

  