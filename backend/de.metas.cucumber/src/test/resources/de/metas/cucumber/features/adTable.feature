@from:cucumber
@ghActions:run_on_executor1
Feature: AD_Table consistency checks

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  Scenario: All defined AD_Tables are valid, all columns are present in database and they can be selected
    And validate all AD_Tables, except:
      | TableName                          | Reason          |
      | AD_Table_Document_Template         | template table  |
      | C_Async_Batch_Milestone            |                 |
      | C_Dunning_Header_v                 | legacy, missing |
      | C_Invoice_Header_v                 | legacy, missing |
      | C_Order_Header_v                   | legacy, missing |
      | C_PaySelection_Check_v             | legacy, missing |
      | C_Project_Header_v                 | legacy, missing |
      | C_RfQResponse_v                    | legacy, missing |
      | DD_Order_Header_v                  | legacy, missing |
      | MD_Available_For_Sales_QueryResult |                 |
      | MD_Candidate_ATP_QueryResult       |                 |
      | M_InOut_Header_v                   | legacy, missing |
      | PP_Order_BOM_Header_v              | legacy, missing |
      | PP_Order_Header_v                  | legacy, missing |
      | PP_Order_Node_v                    | legacy, missing |
      | PP_Order_Workflow_Header_v         | legacy, missing |
      | RV_CommissionRunDetail             | legacy, missing |
      | RV_C_RfQResponse                   | legacy, missing |
      | RV_C_RfQ_UnAnswered                | legacy, missing |
      | RV_PP_Product_Costing              | legacy, missing |
      | RV_PP_WIP                          | legacy, missing |
      | RV_UserAccountLock                 | legacy, missing |
      | X_AcctDocTableTemplate             | template table  |
      | X_EventStoreTemplate               | template table  |
      | X_ImportTableTemplate              | template table  |
      | X_TableTemplate                    | template table  |


