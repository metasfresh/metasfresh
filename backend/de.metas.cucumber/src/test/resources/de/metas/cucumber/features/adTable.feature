@from:cucumber
@ghActions:run_on_executor1
Feature: AD_Table consistency checks

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  Scenario: All defined AD_Tables are valid, all columns are present in database and they can be selected
    And validate all AD_Tables, except:
      | TableName                              | Reason                                                                                                                           |
      | AD_Table_Document_Template             | template table                                                                                                                   |
      | C_Async_Batch_Milestone                |                                                                                                                                  |
      | C_Dunning_Header_v                     | legacy, missing                                                                                                                  |
      | C_Invoice_Header_v                     | legacy, missing                                                                                                                  |
      | C_Order_Header_v                       | legacy, missing                                                                                                                  |
      | C_PaySelection_Check_v                 | legacy, missing                                                                                                                  |
      | C_Project_Header_v                     | legacy, missing                                                                                                                  |
      | C_RfQResponse_v                        | legacy, missing                                                                                                                  |
      | DD_Order_Header_v                      | legacy, missing                                                                                                                  |
      | MD_Available_For_Sales_QueryResult     |                                                                                                                                  |
      | MD_Candidate_ATP_QueryResult           |                                                                                                                                  |
      | M_InOut_Header_v                       | legacy, missing                                                                                                                  |
      | PP_Order_BOM_Header_v                  | legacy, missing                                                                                                                  |
      | PP_Order_Header_v                      | legacy, missing                                                                                                                  |
      | PP_Order_Node_v                        | legacy, missing                                                                                                                  |
      | PP_Order_Workflow_Header_v             | legacy, missing                                                                                                                  |
      | RV_CommissionRunDetail                 | legacy, missing                                                                                                                  |
      | RV_C_RfQResponse                       | legacy, missing                                                                                                                  |
      | RV_C_RfQ_UnAnswered                    | legacy, missing                                                                                                                  |
      | RV_PP_Product_Costing                  | legacy, missing                                                                                                                  |
      | RV_PP_WIP                              | legacy, missing                                                                                                                  |
      | RV_UserAccountLock                     | legacy, missing                                                                                                                  |
      | X_AcctDocTableTemplate                 | template table                                                                                                                   |
      | X_EventStoreTemplate                   | template table                                                                                                                   |
      | X_ImportTableTemplate                  | template table                                                                                                                   |
      | X_TableTemplate                        | template table                                                                                                                   |
      | C_Payment_v                            | ERROR: column "oprocessing" does not exist Hint: Perhaps you meant to reference the column "c_payment_v.processing".             |
      | C_Recurring                            | ERROR: column "datedoc" does not exist                                                                                           |
      | DLM_Partition_Record_V                 | ERROR: column "dlm_level" does not exist                                                                                         |
      | DLM_Partition_Size_Per_Table_V         | ERROR: column "dlm_level" does not exist                                                                                         |
      | I_Payment                              | ERROR: column "c_bp_bankaccount_id" does not exist Hint: Perhaps you meant to reference the column "i_payment.c_bankaccount_id". |
      | M_InOut_LineConfirm_v                  | ERROR: column "guaranteedate" does not exist                                                                                     |
      | M_Product_Stock_V                      | ERROR: column "name" does not exist                                                                                              |
      | MSV3_Tour                              | ERROR: column "msv3_bestellunganteil_id" does not exist                                                                          |
      | RV_Allocation                          | ERROR: column "c_order_id" does not exist                                                                                        |
      | RV_PP_Cost_BOMLine                     | ERROR: column "costingmethod" does not exist                                                                                     |
      | RV_PP_Order_Transactions               | ERROR: column "ad_user_id" does not exist                                                                                        |
      | RV_Printing_Bericht_List_Per_Print_Job | ERROR: column "document" does not exist                                                                                          |
      | RV_Prt_Bericht_Statistik_List_Per_Org  | ERROR: column "address1" does not exist                                                                                          |
      | RV_Storage                             | ERROR: column "guaranteedate" does not exist Hint: Perhaps you meant to reference the column "rv_storage.guaranteedays".         |


