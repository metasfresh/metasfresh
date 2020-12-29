Feature: material-dispo shipment-schedule (sales order)
  As a user
  I want material dispo to be updated properly on sales orders and shipment-schedules
  So that the ATP is always correct

  Also see
  * https://medium.com/agile-vision/cucumber-bdd-part-2-creating-a-sample-java-project-with-cucumber-testng-and-maven-127a1053c180
  * https://javapointers.com/automation/cucumber/cucumber-scenario-outline-example/ with "table-examples"
  * https://automationrhapsody.com/introduction-to-cucumber-and-bdd-with-examples/ with maven-infos
  * https://javapointers.com/automation/cucumber/cucumber-data-tables-example-in-java/ with tables in "given"

  Scenario: shipment-schedule without stock or ATP
  belongs to metasfresh-story <URL>
    Given metasfresh initially has no MD_Candidate data
    When metasfresh receives a shipmentScheduleCreatedEvent
      | M_Product_ID | M_ShipmentSchedule_ID | PreparationDate         | Qty |
      | 2005577      | 123                   | 2020-12-12T23:59:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | RecordIdentifier | Type   | BusinessCase | M_Product_ID | Time                    | Qty | ATP |
      | c_1              | DEMAND | SHIPMENT     | 2005577      | 2020-12-12T23:59:00.00Z | -10 | -10 |
      | c_2              | SUPPLY |              | 2005577      | 2020-12-12T23:59:00.00Z | 10  | 0   |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate.RecordIdentifier | M_ShipmentSchedule.RecordIdentifier |
      | c_1                           | s_1                                 |

  Scenario: shipment-schedule with stock
    Given metasfresh initially has this MD_Candidate data
      | Type         | BusinessCase | M_Product_ID | Time                    | Qty | ATP |
      | INVENTORY_UP |              | 2005577      | 2020-12-12T10:00:00.00Z | 100 | 100 |
    When metasfresh receives a shipmentScheduleCreatedEvent
      | M_Product_ID | PreparationDate         | Qty |
      | 2005577      | 2020-12-12T23:59:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | RecordIdentifier | Type         | BusinessCase | M_Product_ID | Time                    | Qty | ATP |
      | c_1              | INVENTORY_UP |              | 2005577      | 2020-12-12T10:00:00.00Z | 100 | 100 |
      | c_2              | DEMAND       | SHIPMENT     | 2005577      | 2020-12-12T23:59:00.00Z | -10 | 90  |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate_ID.RecordIdentifier | M_ShipmentSchedule_ID |
      | c_2                              | 123                   |
