@from:cucumber
@ghActions:run_on_executor1
Feature: Document Print Option defaults resolve @SQL= expressions

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: AD_Process_Para with static default Y resolves to true
    Given metasfresh contains AD_Processes:
      | Identifier | Value            | Name              |
      | process    | TestPrintProcess | Test Print Process |
    And metasfresh contains AD_Process_Paras:
      | Identifier | AD_Process_ID | ColumnName               | Name       | DefaultValue | AD_Reference_ID |
      | param      | process       | PRINTER_OPTS_IsPrintLogo | Print Logo | Y            | 20              |
    Then the print option descriptors for AD_Process "process" include:
      | OptionName               | DefaultValue |
      | PRINTER_OPTS_IsPrintLogo | true         |

  @from:cucumber
  Scenario: AD_Process_Para with @SQL= default resolves correctly
    Given metasfresh contains AD_Processes:
      | Identifier | Value              | Name                |
      | process    | TestPrintProcSQL   | Test Print Proc SQL |
    And metasfresh contains AD_Process_Paras:
      | Identifier | AD_Process_ID | ColumnName               | Name       | DefaultValue                                           | AD_Reference_ID |
      | param      | process       | PRINTER_OPTS_IsPrintLogo | Print Logo | @SQL=SELECT 'Y' FROM AD_System FETCH FIRST 1 ROW ONLY | 20              |
    Then the print option descriptors for AD_Process "process" include:
      | OptionName               | DefaultValue |
      | PRINTER_OPTS_IsPrintLogo | true         |
