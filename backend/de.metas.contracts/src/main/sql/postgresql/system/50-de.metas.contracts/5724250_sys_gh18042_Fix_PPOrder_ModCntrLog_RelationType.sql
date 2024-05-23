-- Run mode: SWING_CLIENT

-- Name: PP_Order -> ModCntr_Log
-- Source Reference: PP_Order
-- Target Reference: ModCntr_Log_Target_PP_Order
-- 2024-05-23T08:16:42.082Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2024-05-23 11:16:42.082','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540416
;

-- Run mode: SWING_CLIENT

-- Reference: ModCntr_Log_Target_PP_Order
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2024-05-23T08:23:21.821Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1 from ModCntr_Log log where log.ad_table_id = get_table_id(''pp_order'') AND log.Record_ID = @PP_Order_ID / -1@ AND log.modcntr_log_id = modcntr_log.modcntr_log_id)',Updated=TO_TIMESTAMP('2024-05-23 11:23:21.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541807
;

