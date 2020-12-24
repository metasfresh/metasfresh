Feature: metasfresh communicates with the procurement-WebUI via RabbitMQ

  Scenario: metasfresh provides bpartner data to the procurement-WebUI
    Given metasfresh contains M_Products:
      | RecordIdentifier | Name             |
      | p_1              | contractProduct1 |
    And metasfresh contains C_BPartners:
      | RecordIdentifier | Name                |
      | bpartner_1       | ProcurementPartner1 |
      | bpartner_2       | ProcurementPartner2 |
      | bpartner_3       | ProcurementPartner3 |
    And metasfresh contains AD_Users:
      | C_BPartner.RecordIdentifier | Email         | Password    | Language |
      | bpartner_1                  | user1_1@email | password1_1 | en_US    |
      | bpartner_1                  | user1_2@email | password1_2 | de_DE    |
      | bpartner_2                  | user2_1@email | password2_1 | en_US    |
      | bpartner_3                  | user3_1@email | password3_1 | en_US    |
    And metasfresh contains procurement C_Flatrate_Terms:
      | RecordIdentifier | C_BPartner.RecordIdentifier | M_Product.RecordIdentifier | StartDate  | EndDate    |
      | c1               | bpartner_1                  | p_1                        | 2020-01-01 | 2021-01-31 |
    When metasfresh receives a GetAllBPartnersRequest via RabbitMQ
    Then metasfresh responds with a PutBPartnersRequest that contains these BPartners:
      | Identifier | Name      | Deleted |
      | bpartner_1 | BPartner1 | false   |
      | bpartner_3 | BPartner3 | false   |
    And the PutBPartnersRequest contains these Users:
      | BPartner.Identifier | Email         | Password    | Language |
      | bpartner_1          | user1_1@email | password1_1 | en_US    |
      | bpartner_1          | user1_1@email | password1_1 | en_US    |
      | bpartner_3          | user3_1@email | password1_1 | en_US    |
    And the PutBPartnersRequest contains these Contracts:
      | BPartner.Identifier | DateFrom   | DateTo     |
      | bpartner_1          | 2020-01-01 | 2021-01-31 |
    And the PutBPartnersRequest contains these ContractLines:
      | Identifier | Contract.Identifier | M_Product.Name   |
      | cl_1_1     | c_1                 | contractProduct1 |
      | cl_1_2     | c_1                 | contractProduct2 |