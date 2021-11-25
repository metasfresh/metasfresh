@from:cucumber
@topic:materialdispo
Feature: material-dispo updates on shipment-schedule events
  As a user
  I want material dispo to be updated properly if shipment-schedules are created
  So that the ATP is always correct

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule without stock or ATP
    Given metasfresh initially has no MD_Candidate data
    When metasfresh receives a ShipmentScheduleCreatedEvent
      | M_Product_ID | M_ShipmentSchedule_ID | PreparationDate         | Qty |
      | 2005577      | 123                   | 2020-12-12T23:59:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | 2005577                 | 2020-12-12T23:59:00.00Z | -10 | -10                    |
      | c_2        | SUPPLY            |                           | 2005577                 | 2020-12-12T23:59:00.00Z | 10  | 0                      |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate_ID.Identifier | M_ShipmentSchedule_ID |
      | c_1                        | 123                   |

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with stock
    Given metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                           | 2005577                 | 2020-12-12T10:00:00.00Z | 100 | 100                    |
    When metasfresh receives a ShipmentScheduleCreatedEvent
      | M_Product_ID | M_ShipmentSchedule_ID | PreparationDate         | Qty |
      | 2005577      | 123                   | 2020-12-12T23:59:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | 2005577                 | 2020-12-12T10:00:00.00Z | 100 | 100                    |
      | c_2        | DEMAND            | SHIPMENT                  | 2005577                 | 2020-12-12T23:59:00.00Z | -10 | 90                     |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate_ID.Identifier | M_ShipmentSchedule_ID |
      | c_2                        | 123                   |

  