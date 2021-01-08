Feature: metasfresh communicates with the procurement-WebUI via RabbitMQ

  Scenario: metasfresh provides bpartner data to the procurement-WebUI
    Given metasfresh contains M_Products:
      | Identifier | Name             |
      | p_1        | contractProduct1 |
    And metasfresh contains PMM_Products:
      | Identifier | M_Product_ID.Identifier |
      | pmp_1      | p_1                     |
    And metasfresh contains C_BPartners:
      | Identifier | Name                   | OPT.IsVendor | OPT.IsCustomer | OPT.AD_Language |
      | bpartner_1 | ProcurementPartner1    | Y            | Y              | de_DE           |
      | bpartner_2 | ProcurementPartner2    | Y            | N              | de_DE           |
      | bpartner_3 | NotAProcurementPartner | N            | Y              | de_DE           |
    And metasfresh contains AD_Users:
      | C_BPartner_ID.Identifier | Name         | OPT.EMail     | OPT.Password | OPT.AD_Language | OPT.IsMFProcurementUser | OPT.ProcurementPassword |
      | bpartner_1               | user1_1_name | user1_1@email | password1_1  | en_US           | Y                       | procurementPW1_1        |
      | bpartner_1               | user1_2_name | user1_2@email | password1_2  | en_US           | Y                       | procurementPW1_2        |
      | bpartner_2               | user2_1_name | user2_1@email | password2_1  | en_US           | Y                       | procurementPW2_1        |
      | bpartner_3               | user3_1_name | user3_1@email | password3_1  | en_US           | Y                       | procurementPW3_1        |
    And metasfresh contains procurement C_Flatrate_Terms:
      | Identifier | Bill_BPartner_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.PMM_Product_ID.Identifier | StartDate  | EndDate    |
      | c1         | bpartner_1                  | bpartner_1                          | p_1                         | pmp_1                         | 2020-01-01 | 2021-01-31 |
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
      | Contract.Identifier | Product.Name |
      | c_1                 | contractProduct1  |
