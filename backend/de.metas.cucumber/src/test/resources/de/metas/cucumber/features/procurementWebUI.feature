@from:cucumber
@ghActions:run_on_executor6
Feature: metasfresh communicates with the procurement-WebUI via RabbitMQ

  @from:cucumber
  Scenario: metasfresh provides bpartner data to the procurement-WebUI
    Given metasfresh has date and time 2020-02-28T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name             |
      | p_1        | contractProduct1 |
    And metasfresh contains PMM_Products:
      | Identifier | M_Product_ID.Identifier |
      | pmp_1      | p_1                     |
    And metasfresh contains C_BPartners:
      | Identifier | Name                   | OPT.IsVendor | OPT.IsCustomer | OPT.AD_Language |
      | bpartner_1 | ProcurementPartner1    | Y            | Y              | en_US           |
      | bpartner_2 | ProcurementPartner2    | Y            | N              | en_US           |
      | bpartner_3 | NotAProcurementPartner | N            | Y              | en_US           |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | OPT.C_BPartner_ID.Identifier | Name         | OPT.EMail     | OPT.Password | OPT.AD_Language | OPT.IsMFProcurementUser | OPT.ProcurementPassword |
      | user_1                | bpartner_1                   | user1_1_name | user1_1@email | password1_1  | de_DE           | Y                       | procurementPW1_1        |
      | user_2                | bpartner_1                   | user1_2_name | user1_2@email | password1_2  | de_DE           | Y                       | procurementPW1_2        |
      | user_3                | bpartner_2                   | user2_1_name | user2_1@email | password2_1  | de_DE           | Y                       | procurementPW2_1        |
      | user_4                | bpartner_3                   | user3_1_name | user3_1@email | password3_1  | de_DE           | Y                       | procurementPW3_1        |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name             | Type_Conditions |
      | procurement_1 | Procurement-Test | Procuremnt      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.PMM_Product_ID.Identifier | StartDate  | EndDate    |
      | c1         | procurement_1                       | bpartner_1                  | bpartner_1                          | p_1                         | pmp_1                         | 2020-01-01 | 2021-01-31 |
    And RabbitMQ's message queues are purged
    When metasfresh receives a GetAllBPartnersRequest via RabbitMQ
    Then metasfresh responds with a PutBPartnersRequest that contains these BPartners:
      | Identifier | Name                   | Deleted |
      | bpartner_1 | ProcurementPartner1    | false   |
      | bpartner_2 | ProcurementPartner2    | false   |
      | bpartner_3 | NotAProcurementPartner | true    |
    And the PutBPartnersRequest contains these Users:
      | BPartner.Identifier | Email         | Password         | Language |
      | bpartner_1          | user1_1@email | procurementPW1_1 | de_DE    |
      | bpartner_1          | user1_2@email | procurementPW1_2 | de_DE    |
      | bpartner_2          | user2_1@email | procurementPW2_1 | de_DE    |
    And the PutBPartnersRequest contains these Contracts:
      | Identifier | BPartner.Identifier | DateFrom   | DateTo     | Deleted |
      | c_1        | bpartner_1          | 2020-01-01 | 2021-01-31 | false   |
    And the PutBPartnersRequest contains these ContractLines:
      | Contract.Identifier | Product.Name     |
      | c_1                 | contractProduct1 |
