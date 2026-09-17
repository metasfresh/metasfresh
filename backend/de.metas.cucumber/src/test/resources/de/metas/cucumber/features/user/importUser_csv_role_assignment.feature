@from:cucumber
@ghActions:run_on_executor5
Feature: User Import via CSV — role assignment
  Validates that user import does NOT assign the System Administrator role
  when the role column is left empty in the import CSV.
  See https://github.com/metasfresh/mf15/issues/3948

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And all I_User staging records are deleted

  @from:cucumber
  Scenario: CSV import without role column does not assign any role
    Given metasfresh contains C_BPartners:
      | Identifier | Value           | Name              | IsCustomer |
      | bp_noRole  | test_bp_noRole1 | BP Import NoRole  | Y          |
    And AD_ImpFormat "UserNoRole" for table "I_User" with columns:
      | ColumnName | DataType |
      | BPValue    | S        |
      | Firstname  | S        |
      | Lastname   | S        |
      | Login      | S        |
      | EMail      | S        |
    And C_DataImport config "UserNoRole" using AD_ImpFormat "UserNoRole"
    When the following CSV is imported via data import config "UserNoRole":
      """
      test_bp_noRole1,John,NoRole,john_norole_test,john_norole@test.com
      """
    Then the imported AD_User with login 'john_norole_test' has no role assignments

  @from:cucumber
  Scenario: CSV import with explicit role assigns the correct role
    Given metasfresh contains C_BPartners:
      | Identifier  | Value             | Name               | IsCustomer |
      | bp_withRole | test_bp_withRole1 | BP Import WithRole | Y          |
    And AD_ImpFormat "UserWithRole" for table "I_User" with columns:
      | ColumnName | DataType |
      | BPValue    | S        |
      | Firstname  | S        |
      | Lastname   | S        |
      | Login      | S        |
      | EMail      | S        |
      | RoleName   | S        |
    And C_DataImport config "UserWithRole" using AD_ImpFormat "UserWithRole"
    When the following CSV is imported via data import config "UserWithRole":
      """
      test_bp_withRole1,Jane,WithRole,jane_withrole_test,jane_withrole@test.com,WebUI
      """
    Then the imported AD_User with login 'jane_withrole_test' has role 'WebUI'
