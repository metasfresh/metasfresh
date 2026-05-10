@from:cucumber
@ghActions:run_on_executor6
Feature: Zoom-Into metadata scan
  Verify that every table registered in ad_table_windows_v resolves to a valid window
  via RecordWindowFinder. This is a bulk AD metadata consistency check.

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  Scenario: Every table in ad_table_windows_v resolves to a window
    When RecordWindowFinder is asked to resolve a window for each table listed in ad_table_windows_v, excluding:
      | TableName                          |
      | AD_PInstance_Log                   |
      | T_Query_Selection                  |
      | T_Query_Selection_Pagination       |
      | T_WEBUI_ViewSelection              |
      | T_WEBUI_ViewSelectionLine          |
      | T_Alter_Column                     |
      | T_Selection                        |
      | T_Selection2                       |
      | I_Inventory                        |
      | I_BPartner                         |
      | I_Product                          |
      | I_Replenish                        |
      | I_BankStatement                    |
      | I_Flatrate_Term                    |
      | I_Invoice                          |
      | I_DiscountSchema                   |
      | I_GLJournal                        |
      | I_FAJournal                        |
      | I_Pharma_BPartner                  |
      | I_Pharma_Product                   |
      | I_DataEntry_Record                 |
      | I_Campaign_Price                   |
      | I_Package                          |
      | I_Postal                           |
      | I_Request                          |
      | I_ESR_Import                       |
      | I_Order                            |
      | I_BPartner_GlobalID               |
      | C_BPartner_Adv_Search             |
      | C_BPartner_Location_QuickInput    |
      | C_BPartner_QuickInput             |
      | ServiceRepair_Old_Shipped_HU      |
    Then every table in ad_table_windows_v resolves to a non-null window
