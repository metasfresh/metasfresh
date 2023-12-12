@from:cucumber
@Id:S0288
Feature: Process C_BPartner_MoveToAnotherOrg

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-07-11T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @Id:S0288_100
  Scenario: Process C_BPartner_MoveToAnotherOrg
    Given metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value          |
      | org_11072023_1       | Org1 | org_11072023_1 |
      | org_11072023_2       | Org2 | org_11072023_2 |
    And metasfresh contains C_BPartners without locations:
      | Identifier            | Name                 | Value                | OPT.CompanyName     | OPT.AD_Org_ID.Identifier |
      | bpartner_11072023_1   | bpartner_11072023_1  | bpartner_11072023_1  | bpartner_11072023_1 |  org_11072023_1          |
    And C_BPartner_MoveToAnotherOrg is invoked with parameters:
      | C_BPartner_ID.Identifier | AD_Org_ID.Identifier | Date_OrgChange |
      | bpartner_11072023_1      | org_11072023_2       | 2023-07-11     |
    Then after not more than 60s, C_BPartner are found:
      | C_BPartner_ID.Identifier | OPT.Name             | OPT.Value           | OPT.AD_Org_ID.Identifier |
      | bpartner_11072023_1      | bpartner_11072023_1  | bpartner_11072023_1 | org_11072023_1           |
      | bpartner_11072023_2      | bpartner_11072023_1  | bpartner_11072023_1 | org_11072023_2           |
    And after not more than 60s, AD_OrgChange_History are found:
      | AD_OrgChange_History_ID.Identifier | C_BPartner_From_ID.Identifier | C_BPartner_To_ID.Identifier | AD_Org_From_ID.Identifier | AD_OrgTo_ID.Identifier |
      | orgChange_11072023_1               | bpartner_11072023_1           | bpartner_11072023_2         | org_11072023_1            | org_11072023_2         |
    And after not more than 60s, R_Request are found:
      | R_RequestType_ID.Identifier | C_BPartner_ID.Identifier | AD_Org_ID.Identifier | R_RequestType_InternalName |
      | request_11072023_1          | bpartner_11072023_1      | org_11072023_1       | S_OrgSwitch                |
      | request_11072023_2          | bpartner_11072023_2      | org_11072023_2       | S_OrgSwitch                |
